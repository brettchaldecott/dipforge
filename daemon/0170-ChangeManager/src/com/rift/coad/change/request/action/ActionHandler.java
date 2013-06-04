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
import java.rmi.RemoteException;

// the request.
import com.rift.coad.change.request.Request;
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;


/**
 * This interface describes the mechanisim that will be used to interact with the
 *
 *
 * @author brett chaldecott
 */
public interface ActionHandler extends AsyncCallbackHandler {

    /**
     * This method invokes the request identified by the id.
     *
     * @param request The id of the request that the action must be invoked on.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void invokeAction(String request) throws ActionException, RemoteException;

    
    /**
     * This method is used to invoke the request.
     *
     * @param masterRequestId The master request to invoke.
     * @param request The request to invoke.
     * @throws java.rmi.RemoteException
     */
    public void invokeAction(String masterRequestId, Request request) throws ActionException, RemoteException;
    
    
    /**
     * This method is called to reinvoke a paused action.
     *
     * @param actionId The id of the action to re-invoke
     * @throws java.rmi.RemoteException
     */
    public void resumeAction(String actionId) throws ActionException, RemoteException;
}
