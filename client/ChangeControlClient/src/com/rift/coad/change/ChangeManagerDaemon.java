/*
 * ChangeControlClient: The client library for the change control client.
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
 * ChangeManagerDaemon.java
 */

package com.rift.coad.change;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This method is called to change the manager daemon.
 * 
 * @author brett chaldecott
 */
public interface ChangeManagerDaemon extends Remote {
    
    /**
     * This method is responsible for adding a new action.
     * 
     * @param action The name of the action.
     * @throws ChangeException The change exception
     * @throws RemoteException The remote exception
     */
    public void addAction(List<ActionInfo> action) throws ChangeException, RemoteException;


    /**
     * This method is responsible for updating the specified action.
     *
     * @param action The action to update.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public void updateAction(List<ActionInfo> action) throws ChangeException, RemoteException;


    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    public List<ActionInfo> listActions(String project,String type) 
            throws ChangeException, RemoteException;

    
    /**
     * This method gets the action
     * 
     * @param project The project the action is attached to.
     * @param type The type the action is running on.
     * @param action The action.
     * @return The action information.
     * @throws ChangeException
     * @throws RemoteException 
     */
    public ActionInfo getAction(String project, String type, String action)
            throws ChangeException, RemoteException;
    
    /**
     * This method removes the action identified by the information.
     * 
     * @param project The reference to the project.
     * @param type The type of action.
     * @param action The action
     * @throws ChangeException
     * @throws RemoteException 
     */
    public void removeAction(String project, String type, String action)
            throws ChangeException, RemoteException;
    
    
    
    
}
