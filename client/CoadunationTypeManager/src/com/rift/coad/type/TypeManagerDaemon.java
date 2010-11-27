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
 * TypeManagerDaemon.java
 */

// package path
package com.rift.coad.type;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation imports
import com.rift.coad.rdf.objmapping.resource.ResourceBase;


/**
 * This interface describes the methods exposed by the type manager daemon.
 *
 * @author brett chaldecott
 */
public interface TypeManagerDaemon extends Remote {
    /**
     * This method adds the adds XML defined type to the store.
     *
     * @param xml The string containing the XML information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void addType(String xml) throws TypeManagerException, RemoteException;


    /**
     * This method accepts a resource to the types definition.
     *
     * @param resource The resource definition to add to the store.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void addType(ResourceBase resource) throws TypeManagerException, RemoteException;


    /**
     * This method is called to update the type information.
     *
     * @param xml The xml containing the update information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void updateType(String xml) throws TypeManagerException, RemoteException;


    /**
     * This method updates a resources definition.
     *
     * @param resource The updated resource.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void updateType(ResourceBase resource) throws TypeManagerException, RemoteException;


    /**
     * This method is called to delete a type from the store.
     *
     * @param xml The xml containing the information to delete
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void deleteType(String xml) throws TypeManagerException, RemoteException;


    /**
     * This method is responsible for deleting the type.
     *
     * @param resource The resource to delete.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void deleteType(ResourceBase resource) throws TypeManagerException, RemoteException;


    /**
     * This method returns the XML string containing all the types defined by this system.
     * @return The string containing all the registered types
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public String getTypes() throws TypeManagerException, RemoteException;


    /**
     * This method returns the type information.
     *
     * @param typeId The id of the type to retrieve.
     * @return The resource base object.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public ResourceBase getType(String typeId) throws TypeManagerException, RemoteException;


    /**
     * This method returns the resources types identified by the
     *
     * @param uri The uri that identifies the type.
     * @return The list of types.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public List<ResourceBase> listTypes(String[] uri) throws TypeManagerException, RemoteException;


    /**
     * This method lists the types for the given list of basic types.
     *
     * @param basicTypes The list of basic types
     * @return The list of strings containing the type ids.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public List<ResourceBase> listTypesByBasicType(String[] basicTypes) throws TypeManagerException, RemoteException;
    
}
