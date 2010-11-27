/*
 * ChangeControlManager: The manager for the change events.
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
 * RequestCallbackHandlerAsync.java
 */

package com.rift.coad.change.request;

// log4j methods
import java.util.logging.Level;
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.daemon.servicebroker.ServiceBroker;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;

/**
 * This object is the implementation of the create request interface.
 *
 * @author brett chaldecott
 */
public class CreateRequestHandlerImpl implements CreateRequestHandler, BeanRunnable {

    // class singetons
    private static Logger log = Logger.getLogger(CreateRequestHandlerImpl.class);

    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();


    /**
     * The default constructor
     */
    public CreateRequestHandlerImpl() {
    }


    /**
     * This method makes a call onto the request factory daemon to instanciate
     * a new request factory object.
     * 
     * @param request The request definition.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void createRequest(Request request) throws RequestException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            daemon.createRequest(request);
        } catch (Exception ex) {
            log.error("Failed to init a request : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to init a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a reference to a request.
     *
     * @param id The id of the request.
     * @return The reference to the request.
     * @throws RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    public Request getRequest(String id) throws RequestException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            return daemon.getRequest(id).getInfo();
        } catch (Exception ex) {
            log.error("Failed to init a request : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to init a request : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method processes the requests.
     */
    public void process() {
        // wait for the deployment process to stop.
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();

        // register the request factory daemon with the service broker
        while(!state.isTerminated()) {
            try {
                ServiceBroker broker = (ServiceBroker)ConnectionManager.getInstance().
                        getConnection(ServiceBroker.class, "ServiceBroker");
                List<String> services = new ArrayList<String>();
                services.add(ServiceConstants.CREATE_REQUEST_HANDLER_SERVICE);
                broker.registerService("change/request/CreateRequestHandler", services);
                break;
            } catch (Exception ex) {
                log.error("Failed to register the entries with the service broker : " +
                        ex.getMessage(),ex);
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex1) {
                    log.error("Sleep was broken because : " +
                        ex1.getMessage(),ex1);
                }
            }
        }

        

    }


    /**
     * This method terminates the request
     */
    public void terminate() {
        state.terminate(true);
    }

}
