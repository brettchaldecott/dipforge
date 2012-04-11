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
 * RequestFactoryDaemonImpl.java
 */


package com.rift.coad.change.request;

// java imports

// log4j imports
import com.rift.coad.audit.client.AuditLogger;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.util.transaction.CoadunationHashMap;

// change request
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.change.ChangeManagerDaemonImpl;
import com.rift.coad.change.rdf.MasterRequestRDF;
import com.rift.coad.change.rdf.RequestRDF;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This object is responsible for managing the factory objects.
 *
 * @author brett chaldecott
 */
public class RequestFactoryDaemonImpl implements RequestFactoryDaemon, BeanRunnable {

    // class singletons variables
    private static Logger log = Logger.getLogger(RequestFactoryDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            RequestFactoryDaemonImpl.class);

    // Private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();

    // NOTE: this is a thread safe map and not a transaction safe map
    // so this map could become dirty.
    private Map entries = new ConcurrentHashMap();
    

    /**
     * The default constructor.
     */
    public RequestFactoryDaemonImpl() throws RequestException {
        

    }
    
    
    /**
     * This method is responsible for creating a new request.
     * 
     * @param request The request that initializes this object.
     * @return The newly created requet object
     * @throws com.rift.coad.change.request.RequestException
     */
    public RequestFactoryObject createRequest(Request request) throws RequestException {
        RequestFactoryObjectImpl factoryObject = new RequestFactoryObjectImpl(
                new RequestRDF(request));
        entries.put(factoryObject.getId(), factoryObject);
        auditLog.complete("Create request [%s]", request.getId());
        return factoryObject;
        
    }


    /**
     * This object returns the request object identified by the id.
     *
     * @param id The id of the request object.
     * @return The reference to the request object.
     * @throws com.rift.coad.change.request.RequestException
     */
    public RequestFactoryObject getRequest(String id) throws RequestException {
        RequestFactoryObject result = (RequestFactoryObject)entries.get(id);
        if (result != null) {
            return result;
        }
        throw new RequestException("The request [" +id +"] does not exist");
    }


    /**
     * This method removes the request identified by the id.
     *
     * @param id The id of the request to remove.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void removeRequest(String id) throws RequestException {
        RequestFactoryObjectImpl instance =
                (RequestFactoryObjectImpl)entries.remove(id);
        if (instance == null) {
            throw new RequestException("The request [" +id +"] does not exist");
        }
        instance.remove();
        auditLog.complete("Removed request [%s]", instance.getId());
    }


    /**
     * This method lists the requests currently being processed.
     *
     * @return The list of requests being processed.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public List<String> listRequests() throws RequestException, RemoteException {
        List<String> keys = new ArrayList();
        Set keySet = new HashSet();
        keySet.addAll(entries.keySet());
        for (Object value : keySet) {
            keys.add(value.toString());
        }
        return keys;
    }


    /**
     * This method is called process information.
     */
    public void process() {
        // wait for the deployment process to stop.
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();

        try {
            ChangeLog.init(RequestFactoryDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to start the request factory : " + ex.getMessage(),ex);
        }

        // load the requests.
        try {
            loadRequests();
        } catch (Exception ex) {
            log.error("Failed to load the existing requests : " + ex.getMessage(),ex);
        }

        // start the change log processing.
        try {
            ChangeLog.getInstance().start();
        } catch (Exception ex) {
            log.error("Failed to start the change log processing : "
                    + ex.getMessage(),ex);
        }



        // register the request factory daemon with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(ServiceConstants.SERVICE);
            broker.registerService("change/request/RequestFactoryDaemon", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }

        while(!state.isTerminated()) {

            // wait indefinitly
            state.monitor();
        }
        try {
            SemanticUtil.closeInstance(ChangeManagerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to close the semantic session manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to terminate processing.
     */
    public void terminate() {
        state.terminate(true);
        try {
            log.info("Waiting for all uncommited changes to be dumped");
            ChangeLog.terminate();
            log.info("Changes have been dumped.");
        } catch (Exception ex) {
            log.error("Failed to shut down the change log : "
                    + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to load the active requests.
     */
    private void loadRequests() throws RequestException {
        UserTransactionWrapper utw = null;
        try {
            utw = new UserTransactionWrapper();
            utw.begin();
            Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/change.master#MasterRequest> } ")
                    .execute();
            for (SPARQLResultRow entry : entries) {
                MasterRequestRDF request = entry.get(MasterRequestRDF.class,0);
                // Disconnect the request from the session. This results in an
                // object to persists across multiple sessions and it is a
                // deap copy of the proxy object that is its source.
                this.entries.put(request.getId(),new RequestFactoryObjectImpl(
                        session.disconnect(MasterRequestRDF.class, request)));
            }
        } catch (Exception ex) {
            log.error("Failed to load the requests from the RDF store : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to load the requests from the RDF store : " + ex.getMessage(),ex);
        } finally {
            if (utw != null) {
                utw.release();
            }
        }
        
    }


}
