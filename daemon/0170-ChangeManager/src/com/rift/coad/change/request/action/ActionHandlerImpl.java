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
 * ActionHandler.java
 */

package com.rift.coad.change.request.action;

// java imports
import com.rift.coad.change.ActionConstants;
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.RequestConstants;
import java.rmi.RemoteException;

// log4j import
import org.apache.log4j.Logger;

// request object
import com.rift.coad.change.request.RequestHandler;
import com.rift.coad.change.request.RequestHandlerAsync;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * The action handler.
 *
 * @author brett chaldecott
 */
public class ActionHandlerImpl implements ActionHandler {

    // class singletons
    private static Logger log = Logger.getLogger(ActionHandler.class);

    /**
     * The default constructor.
     */
    public ActionHandlerImpl() {
    }


    /**
     * This method invokes the request identified by the id.
     *
     * @param request The id of the request that the action must be invoked on.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void invokeAction(String request) throws ActionException, RemoteException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            ActionInstance instance = daemon.getActionInstance(request);
            instance.execute();
            handleCompletion(daemon,instance);
        } catch (Exception ex) {
            log.error("Failed to handle the invokation : " + ex.getMessage(),ex);
            // force a retry of this call.
            throw new RemoteException
                    ("Failed to handle the invokation : " + ex.getMessage(),ex);
        }
    }



    
    /**
     * This method is used to invoke the action.
     *
     * @param request The new request to create the action with.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void invokeAction(String masterRequestId, Request request) throws ActionException, RemoteException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            ActionInstance instance = daemon.createActionInstance(masterRequestId,request);
            instance.execute();
            handleCompletion(daemon,instance);
        } catch (Exception ex) {
            log.error("Failed to handle the invokation : " + ex.getMessage(),ex);
            // force a retry of this call.
            throw new RemoteException
                    ("Failed to handle the invokation : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method is called to deal with a successfull result.
     *
     * @param messageId The message id to invoke.
     * @param correllationId The id to correlate the messages on.
     * @param result The result object.
     * @throws java.rmi.RemoteException
     */
    public void onSuccess(String messageId, String correllationId,
            Object result) throws RemoteException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            ActionInstance instance = daemon.getActionInstance(correllationId);
            instance.execute(result);
            handleCompletion(daemon,instance);
        } catch (Exception ex) {
            log.error("Failed to handle the invokation : " + ex.getMessage(),ex);
            // force a retry of this call.
            throw new RemoteException
                    ("Failed to handle the invokation : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to deal with failures
     *
     * @param messageId
     * @param correllationId
     * @param caught
     * @throws java.rmi.RemoteException
     */
    public void onFailure(String messageId, String correllationId,
            Throwable caught) throws RemoteException {
        try {
            ActionFactoryManager daemon = (ActionFactoryManager)ConnectionManager.getInstance().
                    getConnection(ActionFactoryManager.class,
                    "java:comp/env/bean/change/request/action/ActionFactoryManager");
            ActionInstance instance = daemon.getActionInstance(correllationId);
            instance.execute((Exception)caught);
            handleCompletion(daemon,instance);
        } catch (Exception ex) {
            log.error("Failed to handle the invokation : " + ex.getMessage(),ex);
            // force a retry of this call.
            throw new RemoteException
                    ("Failed to handle the invokation : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to handle the completion of an action.
     *
     * @param daemon
     * @param instance
     * @throws com.rift.coad.change.request.action.ActionException
     */
    private void handleCompletion(ActionFactoryManager daemon,
            ActionInstance instance) throws ActionException {
        try {
            if (instance.getStatus().equals(ActionConstants.FINISHED)) {
                RequestHandlerAsync handler = (RequestHandlerAsync)RPCMessageClient.
                        createOneWay("change/request/action/ActionHandler", RequestHandler.class,
                    RequestHandlerAsync.class, "change/request/RequestHandler");
                handler.updateRequest(instance.getMasterRequestId(),
                        instance.getRequestId(), RequestConstants.COMPLETE, "");
                // remove the action
                daemon.removeActionInstance(instance.getId());
            } else if (instance.getStatus().equals(ActionConstants.ERROR)) {
                RequestHandlerAsync handler = (RequestHandlerAsync)RPCMessageClient.
                        createOneWay("change/request/action/ActionHandler", RequestHandler.class,
                    RequestHandlerAsync.class, "change/request/RequestHandler");
                handler.updateRequest(instance.getMasterRequestId(),
                        instance.getRequestId(), RequestConstants.ERROR,
                        instance.getLastEvent().getMessage());
            }
        } catch (Exception ex) {
            log.error("Failed to remove the request : " + ex.getMessage(),ex);
            throw new ActionException
                    ("Failed to remove the request : " + ex.getMessage(),ex);
        }
    }
}
