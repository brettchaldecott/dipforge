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


// package path
package com.rift.coad.change.request.action;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;

/**
 * This interface defines the methods used to interact with the action manager.
 *
 * @author brett chaldecott
 */
public interface ActionManagerMBean extends Remote {
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
     * This method is responsible for creating a new action instance using the supplied XML.
     *
     * @param masterRequestId The id of the request that this action is attached to.
     * @param id The id of the request to instanciate.
     * @param xml The XML content.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method initializes an action using the XML.")
    @Version(number="1.0")
    public void initActionFromXML(
            @ParamInfo(name="masterRequestId",
            description="The id of the request that this action is attached to.")String masterRequestId,
            @ParamInfo(name="id",
            description="The id of the action instance in the xml to spawn.")String id,
            @ParamInfo(name="XML",
            description="The contents of the action.")String xml) throws ActionException, RemoteException;


    /**
     * This method removes the action identified by the id.
     *
     * @param id The id of the action to remove.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Remove the action identified by the id.")
    @Version(number="1.0")
    public void removeAction(
            @ParamInfo(name="id",
            description="The id of the action to remove.")String id)
            throws ActionException, RemoteException;


    /**
     * This method returns a list of all the actions.
     *
     * @return The list of actions.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="A list of all the running actions.")
    @Version(number="1.0")
    @Result(description="The list of actions.")
    public List<String> listActions() throws ActionException, RemoteException;


    /**
     * This method returns the list of request ids for actions.
     *
     * @return This method returns a list of request ids.
     * @throws com.rift.coad.change.request.action.ActionException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="A list of all the running actions.")
    @Version(number="1.0")
    @Result(description="The list of actions.")
    public List<String> listRequestId() throws ActionException, RemoteException;
}
