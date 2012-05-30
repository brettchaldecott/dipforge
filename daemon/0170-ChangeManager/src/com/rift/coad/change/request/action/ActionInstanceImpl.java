/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * ActionInstanceImpl.java
 */
// package path
package com.rift.coad.change.request.action;

// java import
import com.rift.coad.change.ActionInfo;
import java.util.Date;

// log4j import
import com.rift.coad.change.ChangeManagerDaemon;
import com.rift.coad.util.change.ChangeException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import org.apache.log4j.Logger;


// action import
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.change.Change;
import com.rift.coad.change.ChangeManagerDaemonImpl;
import com.rift.coad.change.rdf.ActionInfoRDF;
import com.rift.coad.change.rdf.ActionInstanceInfoRDF;
import com.rift.coad.change.rdf.RequestEventRDF;
import com.rift.coad.change.rdf.RequestRDF;
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.RequestEvent;
import com.rift.coad.change.request.RequestFactoryObjectImpl;
import com.rift.coad.change.request.action.leviathan.LeviathanLog;
import com.rift.coad.change.request.action.leviathan.LeviathanPrint;
import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFData;
import com.rift.coad.change.request.action.leviathan.requestdata.LsActionRDFProperty;
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.lib.common.CopyObject;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.dipforge.ls.engine.LeviathanConfig;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.LeviathanEngine;
import com.rift.dipforge.ls.engine.LeviathanProcessorManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * This object is the implementation of the action instance.
 *
 * @author brett chaldecott
 */
public class ActionInstanceImpl implements ActionInstance, ResourceIndex, Resource {

    /**
     * The enum defining the type of action to perform on the object.
     */
    public enum TYPE {

        ADD,
        UPDATE,
        DELETE
    };

    /**
     * The action enumerated values
     */
    public enum ACTION_STATUS {

        ACTION_RUNNING,
        ACTION_COMPLETE,
        ACTION_ERROR,
        ACTION_FINISHED
    }

    /**
     * This object represents a change.
     */
    public static class ActionChange implements Change {

        private TYPE changeType;
        private ActionInstanceInfoRDF action;

        /**
         * This constructor creates a new change using the supplied parameters.
         *
         * @param changeType The type of change that is being applied.
         * @param stack The stack containing the current state of this action.
         * @throws com.rift.coad.change.request.action.ActionException
         */
        public ActionChange(TYPE changeType, ActionInstanceInfoRDF action) throws ActionException {
            try {

                this.action = CopyObject.copy(ActionInstanceInfoRDF.class, action);
                this.changeType = changeType;
            } catch (Exception ex) {
                log.error("Failed to instanciate the change : " + ex.getMessage(), ex);
                throw new ActionException("Failed to instanciate the change : " + ex.getMessage(), ex);
            }
        }

        /**
         * This method is responsible for applying the changes.
         *
         * @throws ChangeException
         */
        public void applyChanges() throws com.rift.coad.util.change.ChangeException {
            try {
                Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
                if ((changeType == TYPE.ADD) || (changeType == TYPE.UPDATE)) {
                    log.debug("###### persist the stack : " + action.getId());
                    session.persist(this.action);
                } else {
                    log.debug("###### remove the stack : " + action.getId());
                    session.remove(this.action);
                }
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(), ex);
            }
        }
    }
    // class singletons
    private static Logger log = Logger.getLogger(ActionInstanceImpl.class);
    // private member variables
    private ActionInstanceInfoRDF action = null;
    private LeviathanProcessorManager processor;

    /**
     * The constructor sets the action information.
     * 
     * @param action The action information reference.
     */
    public ActionInstanceImpl(ActionInstanceInfoRDF action) 
            throws ActionException {
        try {
            this.action = action;
            processor = 
                    LeviathanEngine.getInstance().getProcess(action.getFlowId());
        } catch (Exception ex) {
            throw new ActionException(
                    "Failed to instanciate the action instance because :" +
                    ex.getMessage(),ex);
        }
    }

    /**
     * This constructor sets up a new action instance request.
     *
     * @param request The reference to the request.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public ActionInstanceImpl(String masterRequestId, Request request) 
            throws ActionException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            ChangeManagerDaemon daemon =
                    (ChangeManagerDaemon) ConnectionManager.getInstance().
                    getConnection(ChangeManagerDaemon.class,
                    "java:comp/env/bean/change/ChangeManagerDaemon");
            ActionInfo actionInfo = daemon.getAction(request.getProject(),
                    request.getData().getDataType(), request.getAction());
            
            // retrieve the 
            String projectBase = config.getString(ActionConstants.PROJECT_BASE);
            
            File file = new File(projectBase + "/" +
                    actionInfo.getProject() + "/" + actionInfo.getFile());
            if (!file.isFile()) {
                throw new ActionException("The action file [" + 
                        file.getPath() + "] does not exist.");
            }
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream in = new FileInputStream(file);
            in.read(buffer);
            in.close();
            
            // properties
            Map properties = new HashMap();
            
            // process the request action data
            properties.put("out", new LeviathanPrint());
            properties.put("log", new LeviathanLog(actionInfo.getProject(),
                    actionInfo.getFile()));
            Session session = XMLSemanticUtil.getSession();
            LsActionRDFData rdfData = new LsActionRDFData();
            session.persist(request.getData().getData());
            properties.put(request.getData().getName(), 
                    new LsActionRDFProperty(request.getData().getName(), 
                    request.getData().getDataType() + "/" + request.getData().getId(),
                    request.getData().getDataType(),rdfData));
            for (RequestData data: request.getDependencies()) {
                session.persist(data.getData());
                properties.put(data.getName(), 
                    new LsActionRDFProperty(data.getName(), 
                    data.getDataType() + "/" + data.getId(),
                    data.getDataType(),rdfData));
            }
            rdfData.setData(session.dumpXML());
            
            // set up a global rdf variable
            properties.put(LsActionRDFProperty.RDF_GLOBAL_PROPERTY, rdfData);
            
            // instanciate the processor
            processor = LeviathanEngine.getInstance().
                    initProcess(new String(buffer), properties);
            
            action = new ActionInstanceInfoRDF(processor.getProcessor().getGUID(),
                    new ActionInfoRDF(
                    actionInfo), new RequestRDF(request), masterRequestId,
                    processor.getProcessor().getGUID());
            ChangeLog.getInstance().addChange(
                    new ActionInstanceImpl.ActionChange(TYPE.UPDATE, action));
        } catch (ActionException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to instanciate the action instance : "
                    + ex.getMessage(), ex);
            throw new ActionException(
                    "Failed to instanciate the action instance : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the id of the action instance.
     *
     * @return The string containing the id of the action instance.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getId() throws ActionException {
        return action.getId();
    }

    /**
     * This method returns the request information
     *
     * @return The string containing the request id
     * @throws ActionException
     * @throws RemoteException
     */
    public String getRequestId() throws ActionException, RemoteException {
        return action.getRequest().getId();
    }

    /**
     * This method returns the master request id.
     *
     * @return The string containing the id of the master request.
     * @throws ActionException
     * @throws RemoteException
     */
    public String getMasterRequestId() throws ActionException, RemoteException {
        return action.getMasterRequestId();
    }

    /**
     * This method returns the action string.
     *
     * @return The string containing the action name
     * @throws ActionException
     * @throws RemoteException
     */
    public String getAction() throws ActionException, RemoteException {
        return action.getActionInfo().getAction();
    }

    /**
     * This method gets the data type id.
     *
     * @return This method returns the data type id
     * @throws ActionException
     * @throws RemoteException
     */
    public String getDataType() throws ActionException, RemoteException {
        return action.getActionInfo().getType();
    }
    

    /**
     * This method returns the status.
     *
     * @return The status of this action instance
     * @throws ActionException
     * @throws RemoteException
     */
    public String getStatus() throws ActionException, RemoteException {
        log.info("#### Get the status value : " + this.action.getStatus());
        return this.action.getStatus();
    }

    
    /**
     * This method gets the last event.
     *
     * @return The last request event.
     * @throws ActionException
     * @throws RemoteException
     */
    public RequestEvent getLastEvent() throws ActionException, RemoteException {
        if (this.action.getRequest().getEvents().size() == 0) {
            throw new ActionException("There are no events for this request");
        }
        return this.action.getRequest().getEvents().get(
                this.action.getRequest().getEvents().size() - 1).toRequestEvent();
    }

    
    /**
     * This method returns the memory
     *
     * @return
     * @throws ActionException
     * @throws RemoteException
     */
    public com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager getStack() throws ActionException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method sets the stack information
     * 
     * @param stack The reference to the stack
     * @throws ActionException
     * @throws RemoteException 
     */
    public void setStack(com.rift.dipforge.ls.engine.internal.ProcessorMemoryManager stack) throws ActionException, RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method is called to execute the action instance.
     */
    public void execute() throws ActionException, RemoteException {
        try {
            processor.getProcessor().execute();
            handleStatus();
        } catch (Exception ex) {
            log.error("Failed to execute because : " + ex.getMessage(),ex);
            addEvent("ERROR", "Failed to execute because : " + ex.getMessage());
        }
    }

    /**
     * This method executes the action call.
     *
     * @param result The result of the call.
     * @throws ActionException
     * @throws RemoteException
     */
    public void execute(Object result) throws ActionException, RemoteException {
        try {
            processor.getProcessor().resume(result);
            handleStatus();
        } catch (Exception ex) {
            log.error("Failed to execute because : " + ex.getMessage(),ex);
            addEvent("ERROR", "Failed to execute because : " + ex.getMessage());
        }
    }

    
    /**
     * The execute method
     *
     * @param ex
     * @throws ActionException
     * @throws RemoteException
     */
    public void execute(Exception exception) throws ActionException, RemoteException {
        try {
            processor.getProcessor().resume(exception);
            handleStatus();
        } catch (Exception ex) {
            log.error("Failed to execute because : " + ex.getMessage(),ex);
            addEvent("ERROR", "Failed to execute because : " + ex.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public Object getPrimaryKey() {
        return this.action.getId();
    }

    /**
     * This method returns the resource name
     *
     * @return The name of the resource.
     */
    public String getResourceName() {
        return this.action.toString();
    }

    /**
     * This method is called to release the resource
     */
    public void releaseResource() {
        // do nothing
    }

    /**
     * This method is called to remove the action
     */
    public void remove() {
        try {
            ChangeLog.getInstance().addChange(
                    new ActionInstanceImpl.ActionChange(TYPE.DELETE, action));
        } catch (Exception ex) {
            log.error("Failed to remove the action instance : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to add an event identified by the messsage
     * @param message 
     */
    private void addEvent(String status, String message) {
        try {
            this.action.getRequest().getEvents().add(new RequestEventRDF(
                    new RequestEvent(status, message)));
            ChangeLog.getInstance().addChange(
                    new ActionInstanceImpl.ActionChange(TYPE.UPDATE, action));
        } catch (Exception ex) {
            log.error("Failed to add the event");
        }
    }
    
    /**
     * This method is called to set the status of this action
     */
    private void handleStatus() {
        try {
            log.info("########### current status is : " + processor.getProcessor().getStatus());
            if (processor.getProcessor().getStatus() == 
                    LeviathanConstants.Status.COMPLETED) {
                this.action.setStatus(com.rift.coad.change.ActionConstants.FINISHED);
                ChangeLog.getInstance().addChange(
                    new ActionInstanceImpl.ActionChange(TYPE.UPDATE, action));
            }
        } catch (Exception ex) {
            log.error("Failed to set the status : " + ex.getMessage());
            addEvent("ERROR", "Failed to set the status : " + ex.getMessage());
        }
    }
}
