/*
 * RequestBroker: The request broker.
 * Copyright (C) 2010  2015 Burntjam
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

package com.rift.coad.request;

// java imports
import com.rift.coad.audit.client.AuditLogger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

// apache log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.change.request.RequestCallbackHandler;
import com.rift.coad.change.request.RequestException;
import com.rift.coad.change.request.ServiceConstants;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.request.rdf.RequestInfoRDF;

/**
 * This object is the implementation of the request call back handler.
 *
 * @author brett chaldecott
 */
public class RequestCallbackHandlerImpl implements RequestCallbackHandler, BeanRunnable {

    // singleton member variables
    private static Logger log = Logger.getLogger(RequestCallbackHandlerImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            RequestBrokerDaemonImpl.class);


    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();

    /**
     * The default constructor
     */
    public RequestCallbackHandlerImpl() {
    }


    
    /**
     * This method is called to handle the completion of a request.
     *
     * @param requestId The id of the request to mark as complete.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void handleCompletion(String requestId) throws RequestException {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            daemon.removeRequestInfo(requestId);
        } catch (Exception ex) {
            log.error("Failed to list the requests : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to list the request : " + ex.getMessage());
        }
    }


    /**
     * This method is called to process the
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        // register the request factory daemon with the service broker
        try {
            ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                    getConnection(ServiceBroker.class, "ServiceBroker");
            List<String> services = new ArrayList<String>();
            services.add(ServiceConstants.CALLBACK_SERVICE);
            broker.registerService("request/handler/RequestCallbackHandler", services);
        } catch (Exception ex) {
            log.error("Failed to register the entries with the service broker : " +
                    ex.getMessage(),ex);
        }

        while(!state.isTerminated()) {

            // wait indefinitly
            state.monitor();
        }
    }


    /**
     * This method is call.
     */
    public void terminate() {
        state.terminate(true);
        
    }

}
