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
 * ChangeManagerMBean.java
 */
package com.rift.coad.change;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;

/**
 * This interface defines the change manager funcationality.
 *
 * @author brett chaldecott
 */
public interface ChangeManagerMBean extends Remote {

    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description = "Returns the version of type manager")
    @Version(number = "1.0")
    @Result(description = "The string containing the version of this type manager")
    public String getVersion() throws RemoteException;

    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description = "Returns the name of type manager implementation")
    @Version(number = "1.0")
    @Result(description = "The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;

    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description = "Returns the description of type manager implementation.")
    @Version(number = "1.0")
    @Result(description = "The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;

    /**
     * This method adds a new action
     * 
     * @param action The action to add
     * @param project The project name the action is getting added to.
     * @param type The type associated with the project.
     * @param file The file name
     * @throws ChangeException
     * @throws RemoteException 
     */
    @MethodInfo(description = "This method addes a new action.")
    @Version(number = "1.0")
    public void addAction(
            @ParamInfo(name = "action",
            description = "The name of the action.") String action,
            @ParamInfo(name = "project",
            description = "The name of the project.") String project,
            @ParamInfo(name = "type",
            description = "The name of the type.") String type,
            @ParamInfo(name = "file",
            description = "The name of the file.") String file)
            throws ChangeException, RemoteException;
    
    
    /**
     * This method updates the action.
     * 
     * @param action The action information.
     * @param project The project information.
     * @param type The type information.
     * @param file The file information.
     * @throws ChangeException
     * @throws RemoteException 
     */
    @MethodInfo(description = "This method addes a new action.")
    @Version(number = "1.0")
    public void updateAction(
            @ParamInfo(name = "action",
            description = "The name of the action.") String action,
            @ParamInfo(name = "project",
            description = "The name of the project.") String project,
            @ParamInfo(name = "type",
            description = "The name of the type.") String type,
            @ParamInfo(name = "file",
            description = "The name of the file.") String file)
            throws ChangeException, RemoteException;

    
    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description = "The list of actions registered with the change manager..")
    @Version(number = "1.0")
    @Result(description = "The list of actions.")
    public List<String> listActions(
            @ParamInfo(name = "project",
            description = "The name of the project.") String project,
            @ParamInfo(name = "type",
            description = "The name of the type.") String type)
            throws ChangeException, RemoteException;

    
    /**
     * This method retrieves the action.
     * 
     * @param project The project information.
     * @param type The type information.
     * @param action The action.
     * @return The reference to the action
     * @throws ChangeException
     * @throws RemoteException 
     */
    @MethodInfo(description = "This method gets the action.")
    @Version(number = "1.0")
    public ActionInfo getAction(
            @ParamInfo(name = "project",
            description = "The name of the project.") String project,
            @ParamInfo(name = "type",
            description = "The name of the type.") String type,
            @ParamInfo(name = "action",
            description = "The name of the action.") String action)
            throws ChangeException, RemoteException;
    
    
    /**
     * This method removes the specified action name.
     *
     * @param name The name of the action to remove.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description = "This method removes the specified action.")
    @Version(number = "1.0")
    public void removeAction(
            @ParamInfo(name = "project",
            description = "The name of the project.") String project,
            @ParamInfo(name = "type",
            description = "The name of the type.") String type,
            @ParamInfo(name = "action",
            description = "The name of the action.") String action)
            throws ChangeException, RemoteException;
    
    
}
