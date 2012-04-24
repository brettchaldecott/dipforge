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
import com.rift.coad.type.dto.ResourceDefinition;
import com.rift.coad.type.dto.RDFDataType;


/**
 * This interface describes the methods exposed by the type manager daemon.
 *
 * @author brett chaldecott
 */
public interface TypeManagerDaemon extends Remote {
    /**
     * This method accepts a resource to the types definition.
     *
     * @param resource The resource definition to add to the store.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void addType(ResourceDefinition resource) throws TypeManagerException, RemoteException;


    /**
     * This method updates a resources definition.
     *
     * @param resource The updated resource.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void updateType(ResourceDefinition resource) throws TypeManagerException, RemoteException;


    /**
     * This method is responsible for deleting the type.
     *
     * @param resource The resource to delete.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void deleteType(ResourceDefinition resource) throws TypeManagerException, RemoteException;


    /**
     * This method returns the type information.
     *
     * @param project The name of the project.
     * @param uriStr The type identifier.
     * @return The resource base object.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public ResourceDefinition getType(String project, String uriStr) throws TypeManagerException, RemoteException;


    /**
     * This method returns the resources types identified by the
     *
     * @param projects The list of project to export.
     * @return The list of types.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFDataType> listTypes(String[] projects) throws TypeManagerException, RemoteException;



    /**
     * This method adds the adds XML defined type to the store.
     *
     * @param project The project to export.
     * @param xml The string containing the XML information.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void importTypes(String project,String xml) throws TypeManagerException, RemoteException;


    /**
     * The export method for the types
     *
     * @return The string containing the export.
     * @param project The project to export
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public String exportTypes(String project) throws TypeManagerException, RemoteException;


    /**
     * This method is called to drop the given name space.
     *
     * @param namespace The name space to drop.
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void dropTypes(String namespace) throws TypeManagerException, RemoteException;
    
}
