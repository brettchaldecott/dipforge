/*
 * CoadunationTypeManagerConsole: The type management console.
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
 * ManageResources.java
 */

// package path
package com.rift.coad.type.manager.server;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// java imports
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.type.TypeManagerDaemon;
import com.rift.coad.type.manager.client.ManageResources;
import com.rift.coad.type.manager.client.ManageResourcesException;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The resources implementation.
 *
 * @author brett chaldecott
 */
public class ManageResourcesImpl extends RemoteServiceServlet implements
        ManageResources {

    // private member variables
    private static Logger log = Logger.getLogger(ManageResourcesImpl.class);

    /**
     * The default constructor
     */
    public ManageResourcesImpl() {
    }


    /**
     * This method is called to add a new type.
     *
     * @param base The base type.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void addType(ResourceBase base) throws ManageResourcesException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class, 
                    "type/ManagementDaemon");
            daemon.addType((com.rift.coad.rdf.objmapping.resource.ResourceBase)
                    RDFCopy.copyFromClient(base));
        } catch (Exception ex) {
            log.error("Failed to add type : " + ex.getMessage(),ex);
            throw new ManageResourcesException("Failed to add type : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to update a type.
     *
     * @param base The base type.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void updateType(ResourceBase base) throws ManageResourcesException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class,
                    "type/ManagementDaemon");
            daemon.updateType((com.rift.coad.rdf.objmapping.resource.ResourceBase)
                    RDFCopy.copyFromClient(base));
        } catch (Exception ex) {
            log.error("Failed to update the type : " + ex.getMessage(),ex);
            throw new ManageResourcesException("Failed to update the type : " +
                    ex.getMessage());
        }
    }


    /**
     * This method returns the type information for the resource manager.
     *
     * @param name The name of the type.
     * @return The resource base object.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public ResourceBase getType(String name) throws ManageResourcesException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class,
                    "type/ManagementDaemon");
            return (ResourceBase)RDFCopy.copyToClient(daemon.getType(name));
        } catch (Exception ex) {
            log.error("Failed to get the type : " + ex.getMessage(),ex);
            throw new ManageResourcesException("Failed to get the type : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to delete the given type.
     * @param name The name of the entry to delete.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void deleteType(String name) throws ManageResourcesException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class,
                    "type/ManagementDaemon");
            daemon.deleteType(daemon.getType(name));
        } catch (Exception ex) {
            log.error("Failed to delete the type : " + ex.getMessage(),ex);
            throw new ManageResourcesException("Failed to delete the type : " +
                    ex.getMessage());
        }
    }


    /**
     * This method returns the list of types.
     *
     * @param baseTypes The basic types to perform the query for.
     * @return This method returns the list of types.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public List<ResourceBase> listTypes(String[] baseTypes) throws ManageResourcesException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class,
                    "type/ManagementDaemon");
            List<ResourceBase> result = new ArrayList<ResourceBase>();
            List<com.rift.coad.rdf.objmapping.resource.ResourceBase> resources =
                    daemon.listTypesByBasicType(baseTypes);
            for (com.rift.coad.rdf.objmapping.resource.ResourceBase resource : resources) {
                result.add((ResourceBase)RDFCopy.copyToClient(resource));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the types : " + ex.getMessage(),ex);
            throw new ManageResourcesException("Failed to list the types : " +
                    ex.getMessage());
        }
    }


}
