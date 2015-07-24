/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * ResourceManagerConnector.java
 */

// package path
package com.rift.coad.change.server.action.workflow.piece.resource;

// imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.change.client.action.workflow.piece.resource.ResourceException;
import com.rift.coad.change.client.action.workflow.piece.resource.ResourceManager;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.rift.coad.type.TypeManagerDaemon;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implementation of the resource manager.
 *
 * @author brett chaldecott
 */
public class ResourceManagerImpl extends RemoteServiceServlet implements
        ResourceManager {

    // class singletons
    private static Logger log = Logger.getLogger(ResourceManagerImpl.class);
    
    /**
     * The default constructor.
     */
    public ResourceManagerImpl() {
    }


    /**
     * Returns a list of all the resources.
     *
     * @return The list of resources.
     * @throws com.rift.coad.change.client.action.workflow.piece.resource.ResourceException
     */
    public List<ResourceBase> listTypes() throws ResourceException {
        try {
            TypeManagerDaemon daemon = (TypeManagerDaemon)
                    ConnectionManager.getInstance().getConnection(TypeManagerDaemon.class,
                    "type/ManagementDaemon");
            List<ResourceBase> result = new ArrayList<ResourceBase>();
            List<com.rift.coad.rdf.objmapping.resource.ResourceBase> resources =
                    daemon.listTypesByBasicType(TypeManager.getTypesForGroup("Organisation"));
            resources.addAll(daemon.listTypesByBasicType(TypeManager.getTypesForGroup("Person")));
            resources.addAll(daemon.listTypesByBasicType(TypeManager.getTypesForGroup("Inventory")));
            resources.addAll(daemon.listTypesByBasicType(TypeManager.getTypesForGroup("Service")));
            resources.addAll(daemon.listTypesByBasicType(TypeManager.getTypesForGroup("Misc")));
            for (com.rift.coad.rdf.objmapping.resource.ResourceBase resource : resources) {
                result.add((ResourceBase)RDFCopy.copyToClient(resource));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the types : " + ex.getMessage(),ex);
            throw new ResourceException("Failed to list the types : " +
                    ex.getMessage());
        }
    }
}
