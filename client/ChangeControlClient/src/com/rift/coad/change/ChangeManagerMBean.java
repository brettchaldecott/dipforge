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
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;


    /**
     * This method adds a new action to the change manager.
     *
     * @param name The name of the action to add.
     * @param description The description of the action.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method addes a new action.")
    @Version(number="1.0")
    public void addAction(
            @ParamInfo(name="name",
            description="The name of the new type.")String name,
            @ParamInfo(name="description",
            description="The description of the new type.")String description)
            throws ChangeException, RemoteException;


    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The list of actions registered with the change manager..")
    @Version(number="1.0")
    @Result(description="The list of actions.")
    public List<String> listActions() throws ChangeException, RemoteException;


    /**
     * This method removes the specified action name.
     *
     * @param name The name of the action to remove.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method removes the specified action.")
    @Version(number="1.0")
    public void removeAction(
            @ParamInfo(name="name",
            description="The name of the type to remove.")String name) 
            throws ChangeException, RemoteException;


    /**
     * This method lists the action definitions for the given object id.
     *
     * @param objectId The id of the object to return the list for.
     * @return The list of action definitions.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The list of actions attached to the object id.")
    @Version(number="1.0")
    @Result(description="The list of actions.")
    public List<String> listActionDefinitions(
            @ParamInfo(name="objectId",
            description="The id of the object to list the actions for.")String objectId)
            throws ChangeException, RemoteException;
    

    /**
     * This method adds an action definition.
     *
     * @param definition The action definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Add an action definition using xml.")
    @Version(number="1.0")
    public void addActionDefinitionFromXML(
            @ParamInfo(name="objectId",
            description="An XML action definition to add.")String definition)
            throws ChangeException, RemoteException;


    /**
     * This method removes the updated action definition.
     *
     * @param definition The definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Add an action definition using xml.")
    @Version(number="1.0")
    public void updateActionDefinitionFromXML(
            @ParamInfo(name="definition",
            description="The xml definition for the action.")
            String definition) throws ChangeException, RemoteException;


    /**
     * This method returns the action definition for the supplied object id and action.
     *
     * @param objectId The id of the object.
     * @param action The name of the action.
     * @return The action definition.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the action defintion as xml.")
    @Version(number="1.0")
    @Result(description="An XML version of the action definition.")
    public String getActionDefinitionAsXML(
            @ParamInfo(name="objectId",
            description="The id of the object to retrieve the definition for.")String objectId,
            @ParamInfo(name="action",
            description="The action on the object id.")String action)
            throws ChangeException, RemoteException;

    /**
     * This method removes the actions associated with the given object id.
     *
     * @param objectId The object id to remove the association from.
     * @param action The action.
     * @throws com.rift.coad.change.ChangeException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method removes the given action definition.")
    @Version(number="1.0")
    public void removeActionDefinition(
            @ParamInfo(name="objectId",description="The id of the object to remove.")String objectId,
            @ParamInfo(name="action",
            description="The action on the object id to remove.")String action)
            throws ChangeException, RemoteException;


}
