/*
 * ChangeControlClient: The client library for the change control client.
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
 * ChangeManagerDaemon.java
 */

package com.rift.coad.change;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// change imports
import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;


/**
 * This method is called to change the manager daemon.
 * 
 * @author brett chaldecott
 */
public interface ChangeManagerDaemon extends Remote {
    
    /**
     * This method is responsible for adding a new action.
     * 
     * @param name The name of the action.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void addAction(ActionInfo action) throws ChangeException, RemoteException;


    /**
     * This method is responsible for updating the specified action.
     *
     * @param action The action to update.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void updateAction(ActionInfo action) throws ChangeException, RemoteException;


    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public List<ActionInfo> listActions() throws ChangeException, RemoteException;


    /**
     * This method removes the specified action name.
     *
     * @param name The name of the action to remove.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void removeAction(String name) throws ChangeException, RemoteException;


    /**
     * This method lists the action definitions for the given object id.
     *
     * @param objectId The id of the object to return the list for.
     * @return The list of action definitions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public List<String> listActionDefinitions(String objectId) throws ChangeException, RemoteException;

    
    /**
     * This method adds an action definition.
     *
     * @param definition The action definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void addActionDefinition(ActionDefinition definition) throws ChangeException, RemoteException;


    /**
     * This method removes the updated action definition.
     *
     * @param definition The definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void updateActionDefinition(ActionDefinition definition) throws ChangeException, RemoteException;


    /**
     * This method returns the action definition for the supplied object id and action.
     *
     * @param objectId The id of the object.
     * @param action The name of the action.
     * @return The action definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public ActionDefinition getActionDefinition(String objectId, String action) throws ChangeException, RemoteException;

    /**
     * This method removes the actions associated with the given object id.
     *
     * @param objectId The object id to remove the association from.
     * @param action The action.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void removeActionDefinition(String objectId, String action) throws ChangeException, RemoteException;



}
