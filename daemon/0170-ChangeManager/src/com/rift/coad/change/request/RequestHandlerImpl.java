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
 * RequestHandler.java
 */

// package path
package com.rift.coad.change.request;

// java imports
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.util.connection.ConnectionManager;
import java.rmi.RemoteException;

// log4j imports
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This object implements the request handler
 *
 * @author brett chaldecott
 */
public class RequestHandlerImpl implements RequestHandler {

    // class singleton
    private static Logger log = Logger.getLogger(RequestHandlerImpl.class);

    /**
     * The default constructor
     */
    public RequestHandlerImpl() {
    }


    /**
     * This method is responsible for updating the request.
     * 
     * @param requestId The id of the request to update.
     * @param status The new status to set.
     * @param message The message to attache to the status.
     * @throws java.rmi.RemoteException
     */
    public void updateRequest(String masterRequestId, String requestId, String status, String message)
            throws RemoteException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            if (daemon.getRequest(masterRequestId).setStatus(requestId, status, message).
                    equals(RequestConstants.COMPLETE)) {
                daemon.removeRequest(masterRequestId);
                List<String> services = new ArrayList<String>();
                services.add(ServiceConstants.CALLBACK_SERVICE);
                RequestCallbackHandlerAsync handler = (RequestCallbackHandlerAsync)RPCMessageClient.
                        createOneWay("change/request/action/ActionHandler", RequestCallbackHandler.class,
                        RequestCallbackHandlerAsync.class, services, true);
                handler.handleCompletion(masterRequestId);
            }
        } catch (Exception ex) {
            log.error("Failed to handle the update request : " + ex.getMessage(),ex);
        }
    }

    
}
