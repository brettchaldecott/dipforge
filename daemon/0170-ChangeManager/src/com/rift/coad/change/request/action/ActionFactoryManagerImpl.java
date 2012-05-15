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
 * ActionFactoryManagerImpl.java
 */

package com.rift.coad.change.request.action;

// java imports
import com.rift.coad.audit.client.AuditLogger;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// log 4j import
import java.util.logging.Level;
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.change.ChangeManagerDaemonImpl;
import com.rift.coad.change.rdf.ActionInstanceInfoRDF;
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.action.leviathan.LsRDFTypeManager;
import com.rift.coad.change.request.action.leviathan.LsStoreTypeManager;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.transaction.CoadunationHashMap;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.dipforge.ls.engine.LeviathanConfig;
import com.rift.dipforge.ls.engine.LeviathanConstants;
import com.rift.dipforge.ls.engine.LeviathanEngine;
import java.rmi.RemoteException;


/**
 * This class is responsible managing the action instances.
 *
 * @author brett chaldecott
 */
public class ActionFactoryManagerImpl implements
        ActionFactoryManager, BeanRunnable {

    // class singletons
    private static Logger log = Logger.getLogger(ActionFactoryManagerImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            ActionFactoryManagerImpl.class);


    // Private member variables

    // NOTE: this is a thread safe map and not a transaction safe map
    // so this map could become dirty.
    private Map entries = new ConcurrentHashMap();
    private Map requests = new ConcurrentHashMap();


    // state monitor
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private String storeDirectory;

    /**
     * The default constructor for the action factory manager.
     */
    public ActionFactoryManagerImpl() throws ActionException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            storeDirectory = config.getString(ActionConstants.LEVIATHAN_STORAGE_DIR);
            
        } catch (Exception ex) {
            throw new ActionException(
                    "Failed to instanciate the new action factory : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of all the active requests.
     *
     * @return The action instances being processed.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listActionInstances() throws ActionException {
        try {
            Set keys = new HashSet();
            keys.addAll(entries.keySet());
            List<String> result = new ArrayList<String>();
            for (Object value : keys) {
                result.add(value.toString());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to return the list of active requests : " + ex.getMessage());
            throw new ActionException
                    ("Failed to return the list of active requests : " + ex.getMessage());
        }
    }


    /**
     * This method returns the list of active requests.
     *
     * @return The list of active requests.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public List<String> listRequestId() throws ActionException {
        try {
            Set keys = new HashSet();
            keys.addAll(this.requests.keySet());
            List<String> result = new ArrayList<String>();
            for (Object value : keys) {
                result.add(value.toString());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to return the list of action instances : " + ex.getMessage());
            throw new ActionException
                    ("Failed to return the list of action instances : " + ex.getMessage());
        }
    }


    /**
     * This method is called to create a new action instance.
     *
     * @param request The request that is the source of the action instance.
     * @return a reference to the action instance.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public ActionInstance createActionInstance(String masterRequestId, Request request) throws ActionException {
        try {
            ActionInstanceImpl instance = new ActionInstanceImpl(masterRequestId,request);
            this.entries.put(instance.getId(),instance);
            this.requests.put(instance.getRequestId(),instance);
            auditLog.complete("Add a new action instance for master request [%s] sub request [%s] instance id [%s]",
                    masterRequestId,request.getId(), instance.getId());
            return instance;
        } catch (Exception ex) {
            log.error("Failed to create the action instance : " + ex.getMessage(),ex);
            throw new ActionException
                    ("Failed to create the action instance : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the action instance information.
     *
     * @param id The id of the instance to retrieve.
     * @return The action instance.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public ActionInstance getActionInstance(String id) throws ActionException {
        if (this.entries.containsKey(id)) {
            return (ActionInstance)this.entries.get(id);
        }
        log.info("There is no instance with the id : " + id);
        throw new ActionException("There is no instance with the id : " + id);
    }


    /**
     * This method returns the id of the action instance.
     *
     * @param id The id of the instance to retreive the request for.
     * @return The action id for the request id.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public String getActionIdForRequestId(String id) throws ActionException {
        try {
            if (this.requests.containsKey(id)) {
                return ActionInstance.class.cast(this.requests.get(id)).getId();
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve the action id for the request id : " + ex.getMessage(),ex);
            throw new ActionException
                    ("Failed to retrieve the action id for the request id : " + ex.getMessage(),ex);
        }
        log.info("There is no instance with the id : " + id);
        throw new ActionException("There is no instance with the id : " + id);
    }

    
    /**
     * This method is responsible for removing the action instance.
     * @param id The id of the action to remove.
     * @throws com.rift.coad.change.request.action.ActionException
     */
    public void removeActionInstance(String id) throws ActionException, RemoteException {
        if (!this.entries.containsKey(id)) {
            log.info("The action instance [" +id +"]does not exist cannot remove");
            throw new ActionException
                    ("The action instance [" +id +"]does not exist cannot remove");
        }
        ActionInstanceImpl instance = (ActionInstanceImpl)entries.remove(id);
        requests.remove(instance.getRequestId());
        instance.remove();
        auditLog.complete("Remove action instance [%s]",id);
    }


    /**
     * This method is responsible for loading the actions.
     */
    private void loadActions() throws ActionException {
        UserTransactionWrapper utw = null;
        try {
            utw = new UserTransactionWrapper();
            utw.begin();
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/change.actioninstance#ActionInstanceInfoRDF> } ")
                    .execute();
            for (SPARQLResultRow entry : entries) {
                ActionInstanceInfoRDF actionInstance = 
                        entry.get(ActionInstanceInfoRDF.class,0);
                ActionInstanceImpl instance = new ActionInstanceImpl(
                        session.disconnect(ActionInstanceInfoRDF.class, actionInstance));
                this.entries.put(actionInstance.getId(),instance);
                this.requests.put(actionInstance.getRequest().getId(),instance);
            }
            utw.commit();
        } catch (Exception ex) {
            log.error("Failed to load the action instance from the RDF store : " + ex.getMessage(),ex);
            throw new ActionException
                    ("Failed to load the action instance from the RDF store : " + ex.getMessage(),ex);
        } finally {
            if (utw != null) {
                utw.release();
            }
        }
    }

    /**
     * Wait for the deployment to complete
     */
    public void process() {
        try {
            LeviathanConfig config = LeviathanConfig.createConfig();
            config.getProperties().setProperty(LeviathanConstants.STORAGE_PATH, 
                    storeDirectory);
            config.addTypeManager(new LsRDFTypeManager());
            config.addTypeManager(new LsStoreTypeManager());
            LeviathanEngine instance = LeviathanEngine.buildEngine(config);
            
        } catch (Exception ex) {
            log.error("Failed to initialize the leviathan environment because : "
                    + ex.getMessage(),ex);
        }
        
        // wait for the deployment process to stop.
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        
        // init the leviathan engine
        try {
            while (!state.isTerminated()) {
                try {
                    loadActions();
                    break;
                } catch (Exception ex) {
                    log.error("Failed to load the actions for the action factory : " +
                            ex.getMessage());
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException ex1) {
                        log.error("Failed to sleep");
                    }
                }
            }

            while(!state.isTerminated()) {

                // wait indefinitly
                state.monitor();
            }
        } catch (Exception ex) {
            log.error("Failed to process because : " + ex.getMessage(),ex);
        }

    }


    /**
     * The terminate method.
     */
    public void terminate() {
        state.terminate(true);
    }

}
