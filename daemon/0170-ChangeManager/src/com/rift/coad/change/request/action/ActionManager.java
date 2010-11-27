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
 * ActionManager.java
 */


// package path
package com.rift.coad.change.request.action;

// java imports
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.change.rdf.objmapping.change.action.ActionStack;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import java.rmi.RemoteException;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// package path
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The implementation of the Action Manager MBean
 *
 * @author brett chaldecott
 */
public class ActionManager implements ActionManagerMBean {

    // class singletons
    private static Logger log = Logger.getLogger(ActionManager.class);

    /**
     * The default constructor
     */
    public ActionManager() {
    }


    /**
     * This method returns the version information.
     *
     * @return The version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the action manager.
     *
     * @return The string containing the name of the action manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of the action manager.
     *
     * @return The string containing the description of the action manager.
     */
    public String getDescription() {
        return "The action manager MBean.";
    }


    /**
     * This method is responsible for extracting from the XML the action to init.
     * @param id The id of the action to initialize.
     * @param xml The xml to extract the action from.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void initActionFromXML(String masterRequestId, String id, String xml) throws ActionException {
        try {
            Session session = Basic.initSessionManager().getSession();
            session.persist(xml);
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            daemon.createActionInstance(masterRequestId, 
                    session.get(Request.class, Request.class.getName(), id));
        } catch (Exception ex) {
            log.error("Failed to get an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to get an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the action instance as xml identified by the id.
     * @param id The id of the action.
     * @return The XML containing action stack.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getActionAsXML(String id) throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            Session session = Basic.initSessionManager().getSession();
            session.persist(daemon.getActionInstance(id).getStack());
            return session.dump(RDFFormats.XML_ABBREV);
        } catch (Exception ex) {
            log.error("Failed to get an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to get an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for returning the XML for the action identified by the
     * request.
     *
     * @param id The id of the request.
     * @return The string containing the XML for the request.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getActionAsXMLByRequestId(String id) throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            Session session = Basic.initSessionManager().getSession();
            String actionId = daemon.getActionIdForRequestId(id);
            session.persist(daemon.getActionInstance(actionId).getStack());
            return session.dump(RDFFormats.XML_ABBREV);
        } catch (Exception ex) {
            log.error("Failed to get an action by request id : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to get an action by request id : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for updating the Action identified in the XML.
     *
     * @param id The id of the action to update.
     * @param xml The XML containing the updated action.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void updateActionFromXML(String id, String xml) throws ActionException {
        try {
            Session session = Basic.initSessionManager().getSession();
            session.persist(xml);
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            ActionInstance instance = daemon.getActionInstance(id);
            instance.setStack(session.get(ActionStack.class, ActionStack.class.getName(), id));
        } catch (Exception ex) {
            log.error("Failed to update an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to update an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the action identified by the id.
     *
     * @param id The id of the action to remove.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void removeAction(String id) throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            daemon.removeActionInstance(id);
        } catch (Exception ex) {
            log.error("Failed to remove an action : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to remove an action : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the actions instances that are currently running.
     *
     * @return The id of the action.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listActions() throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            return daemon.listActionInstances();
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of actions : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to retrieve a list of actions : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the request id.
     * 
     * @return The list of requests that are currently executing.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listRequestId() throws ActionException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            return daemon.listRequestId();
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
            throw new ActionException
                ("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
        }
    }

}
