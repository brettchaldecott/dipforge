/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  2015 Burntjam
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
 * ActionHandlerAsync.java
 */

package com.rift.coad.change.request.action;

// java imports
import java.rmi.RemoteException;

// request imports
import com.rift.coad.change.request.Request;


/**
 * The asynchronous version of the action handler.
 *
 * @author brett chaldecott
 */
public interface ActionHandlerAsync {
    /**
     * This method invokes the request identified by the id.
     *
     * @param requestId The id of the request that the action must be invoked on.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String invokeAction(String requestId) throws ActionException, RemoteException;


    /**
     * This method is used to invoke the action asynchronously.
     *
     * @param request The request to invoke.
     * @throws java.rmi.RemoteException
     */
    public String invokeAction(String masterRequestId, Request request) throws RemoteException;
    
    
    /**
     * This method is called to reinvoke a paused action.
     *
     * @param actionId The id of the action to re-invoke
     * @throws java.rmi.RemoteException
     */
    public String resumeAction(String actionId) throws RemoteException;
}
