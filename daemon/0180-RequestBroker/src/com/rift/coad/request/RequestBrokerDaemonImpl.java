/*
 * RequestBroker: The request broker daemon.
 * Copyright (C) 2010  Rift IT Contracting
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
 * RequestBrokerManager.java
 */

package com.rift.coad.request;

// java import
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;

// log4j import
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.audit.client.rdf.AuditLogger;
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.change.request.CreateRequestHandler;
import com.rift.coad.change.request.CreateRequestHandlerAsync;
import com.rift.coad.change.request.RequestFactoryDaemon;
import com.rift.coad.change.request.ServiceConstants;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.request.rdf.RequestInfo;
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeLog;
import com.rift.coad.util.change.ChangeException;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.util.transaction.CoadunationHashMap;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.util.ArrayList;

/**
 * This object is responsible for managing the request 
 *
 * TODO: This is not very efficient as it requires a Change log gets implemented
 * to improve performance. This will happen in the next version.
 *
 * @author brett chaldecott
 */
public class RequestBrokerDaemonImpl implements RequestBrokerDaemon, BeanRunnable {

    /**
     * The enum defining the type of action to perform on the object.
     */
    public enum TYPE {
        ADD,
        UPDATE,
        DELETE
    };


    /**
     * This constructor reflects the request change.
     */
    public static class RequestChangeEntry implements Change {

        private TYPE changeType;
        private RequestInfo request;


        public RequestChangeEntry(TYPE changeType, RequestInfo request)
                throws RequestBrokerException {
            try {
                this.changeType = changeType;
                this.request = (RequestInfo)request.clone();
            } catch (Exception ex) {
                log.error("Failed to instanciate the change entry : " +
                        ex.getMessage(),ex);
                throw new RequestBrokerException
                        ("Failed to instanciate the change entry : " +
                        ex.getMessage(),ex);

            }
        }


        /**
         * This constructor is responsible for applying the changes request object.
         *
         * @throws com.rift.coad.util.change.ChangeException
         */
        public void applyChanges() throws ChangeException {
            try {
                Session session = SemanticUtil.getInstance(RequestBrokerDaemonImpl.class).getSession();
                if ((changeType == TYPE.ADD)|| (changeType == TYPE.UPDATE)) {
                    session.persist(request);
                } else {
                    session.remove(request);
                }
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(),ex);
            }
        }

    }


    // private member variables
    private static Logger log = Logger.getLogger(RequestBrokerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            RequestBrokerDaemonImpl.class);
    private int index = 0;
    private ThreadStateMonitor state = new ThreadStateMonitor();

    // NOTE: this is a thread safe map and not a transaction safe map
    // so this map could become dirty.
    private Map entries = new ConcurrentHashMap();

    /**
     * The default constructor for the request broker.
     */
    public RequestBrokerDaemonImpl() {
    }


    /**
     * This method creates a new request.
     *
     * @param request The request to create.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public void createRequest(Request request) throws RequestBrokerException {
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List services = new ArrayList();
            services.add(ServiceConstants.CREATE_REQUEST_HANDLER_SERVICE);
            List jndiUrls = broker.getServiceProviders(services);
            if (index >= jndiUrls.size()) {
                index = 0;
            }
            String jndi = (String)jndiUrls.get(index++);
            CreateRequestHandlerAsync handler = (CreateRequestHandlerAsync)RPCMessageClient.
                    createOneWay("request/RequestBrokerDaemon", CreateRequestHandler.class,
                    CreateRequestHandlerAsync.class, jndi);
            handler.createRequest(request);
            RequestInfo info = new RequestInfo(request.getId(), jndi);
            entries.put(request.getId(), info);
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.ADD,info));
            auditLog.create("Created a request id [%s] on jndi [%s]",request.getId(),jndi).
                    addData(info).setCorrelationId(request.getId()).complete();

        } catch (Exception ex) {
            log.error("Failed to create a request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to create a request : " + ex.getMessage());
        }
    }


    /**
     * This method returns the list of requests that are currently being processed.
     *
     * @return The list of requests.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public List<String> listRequests() throws RequestBrokerException {
        try {
            Set keys = entries.keySet();
            List<String> result = new ArrayList<String>();
            for (Object key : keys) {
                result.add(key.toString());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the requests : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to list the requests : " + ex.getMessage());
        }
    }


    /**
     * This method returns the specified request information.
     * @param id The id of the request.
     * @return The requested object.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public Request getRequest(String id) throws RequestBrokerException {
        try {
            if (!entries.containsKey(id)) {
                log.info("Attempt to retrieve a request that does not exist");
                throw new RequestBrokerException("No such request exists");
            }
            RequestInfo info = (RequestInfo)entries.get(id);
            
            String jndi = info.getJndi();
            CreateRequestHandler daemon = (CreateRequestHandler)ConnectionManager.getInstance().
                    getConnection(CreateRequestHandler.class, jndi);
            return daemon.getRequest(id);
        } catch (RequestBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to get the request : " + ex.getMessage());
        }
    }


    /**
     * This method returns the request information.
     *
     * @param id The id containing the request information.
     * @return The request information.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public RequestInfo getRequestInfo(String id) throws RequestBrokerException {
        try {
            log.info("Get the request info");
            RequestInfo result = (RequestInfo)entries.get(id);
            if (result == null) {
                log.info("Attempt to retrieve a request that does not exist");
                throw new RequestBrokerException("No such request exists");
            }
            return result;
        } catch (RequestBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the request info : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to get the request info : " + ex.getMessage());
        }
    }


    /**
     * This method removes the specified request.
     * @param id The id of the request to remove.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public void removeRequest(String id) throws RequestBrokerException {
        try {
            if (!entries.containsKey(id)) {
                log.info("Attempt to retrieve a request that does not exist");
                return;
            }
            RequestInfo info = (RequestInfo)entries.get(id);
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class, info.getJndi());
            daemon.removeRequest(id);
            entries.remove(id);
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.DELETE,info));
            auditLog.create("The request id [%s] is complete.",id).
                    addData(info).setCorrelationId(id).complete();
        } catch (RequestBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to remove the request : " + ex.getMessage());
        }
    }


    /**
     * This method is called to remove the request information.
     *
     * @param id The id of the requst to reove
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    public void removeRequestInfo(String id) throws RequestBrokerException, RemoteException {
        try {
            if (!entries.containsKey(id)) {
                log.info("Attempt to retrieve a request that does not exist");
                return;
            }
            RequestInfo info = (RequestInfo)entries.get(id);
            entries.remove(id);
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.DELETE,info));
            auditLog.create("The request id [%s] is complete.",id).
                    addData(info).setCorrelationId(id).complete();
        } catch (RequestBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                    ("Failed to remove the request : " + ex.getMessage());
        }
    }



    /**
     * This method is called to process in the background.
     */
    public void process() {
        // wait for the deployment process to stop.
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();

        try {
            ChangeLog.init(RequestBrokerDaemonImpl.class);
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

        while(!state.isTerminated()) {

            // wait indefinitly
            state.monitor();
        }
        try {
            SemanticUtil.closeInstance(RequestBrokerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to close the semantic session manager : " +
                    ex.getMessage(),ex);
        }

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
     * This method is called to terminate the background processing thread.
     */
    public void terminate() {
        state.terminate(true);
    }


    /**
     * This method loads all the requests from the RDF store.
     */
    private void loadRequests() {
        UserTransactionWrapper utw = null;
        try {
            utw = new UserTransactionWrapper();
            utw.begin();
            Session session = SemanticUtil.getInstance(RequestBrokerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/requestbroker#RequestInfo> } ")
                    .execute();
            List<String> result = new ArrayList<String>();
            for (SPARQLResultRow entry : entries) {
                RequestInfo info = entry.get(0).cast(RequestInfo.class);
                this.entries.put(info.getRequestId(), info);
            }
            utw.commit();
        } catch (Exception ex) {
            log.error("Failed to load the requests from the RDF store : " + ex.getMessage(),ex);
        } finally {
            if (utw != null) {
                utw.release();
            }
        }
    }

}
