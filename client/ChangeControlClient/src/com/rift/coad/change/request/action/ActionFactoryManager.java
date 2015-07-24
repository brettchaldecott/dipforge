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
 * ActionFactoryManager.java
 */

package com.rift.coad.change.request.action;

// java imports
import com.rift.coad.change.request.Request;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;



/**
 * This object is responsible for 
 *
 * @author brett chaldecott
 */
public interface ActionFactoryManager extends Remote {

    /**
     * This method returns a list of all the actions current being executed.
     * @return
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public List<String> listActionInstances() throws ActionException, RemoteException;

    /**
     * This method returns a list of all request ids that are current being executed by this daemon.
     *
     * @return A list of all active requests ids.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public List<String> listRequestId() throws ActionException, RemoteException;


    /**
     * This method is called to create a new action instance using the request as the source.
     *
     * @param masterRequestId The master request that the sub request is attached to.
     * @param request The request that is being processed.
     * @return A reference to the action instance managing the action.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public ActionInstance createActionInstance(String masterRequestId, Request request) throws ActionException, RemoteException;


    /**
     * This method returns a reference to the action using the action id.
     *
     * @param id The id of the action instance.
     * @return The reference to the action.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public ActionInstance getActionInstance(String id) throws ActionException, RemoteException;


    /**
     * This method returns action id for the request id.
     *
     * @param id The id of the request that this action instance is bound to.
     * @return The id of the action.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public String getActionIdForRequestId(String id) throws ActionException, RemoteException;

    
    /**
     * This method is called to remove the action instance identified by the id.
     *
     * @param id The id of the action instance to remove.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    public void removeActionInstance(String id) throws ActionException, RemoteException;
}
