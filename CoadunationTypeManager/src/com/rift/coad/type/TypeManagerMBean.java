/*
 * CoadunationTypeManage: The client library for the type manager
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
 * TypeManagerMBean.java
 */

// package
package com.rift.coad.type;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;


/**
 * The management bean for type manager.
 * 
 * @author brett chaldecott
 */
public interface TypeManagerMBean extends Remote {
    
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
     * This method adds the adds XML defined type to the store.
     *
     * @param xml The string containing the XML information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method adds a new type from the xml supplied")
    @Version(number="1.0")
    public void addType(
            @ParamInfo(name="xml",
            description="The xml containing the new type.")String xml) throws TypeManagerException, RemoteException;


    /**
     * This method is called to update the type information.
     *
     * @param xml The xml containing the update information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method updates a type from the xml supplied")
    @Version(number="1.0")
    public void updateType(
            @ParamInfo(name="xml",
            description="The xml containing the updated type.")String xml)
            throws TypeManagerException, RemoteException;


    /**
     * This method is called to delete a type from the store.
     * @param xml The xmle containing the information to delete
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method deletes a type from the xml supplied")
    @Version(number="1.0")
    public void deleteType(
            @ParamInfo(name="xml",
            description="The xml containing the type to delete.")String xml)
            throws TypeManagerException, RemoteException;


    /**
     * This method returns the XML string containing all the types defined by this system.
     * @return The string containing all the registered types
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method dumps the complete xml.")
    @Version(number="1.0")
    @Result(description="The xml containing the type information.")
    public String getTypes() throws TypeManagerException, RemoteException;
}
