/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * TypeCache.java
 */

package com.rift.coad.script.server.type;

// java imports
import java.util.ArrayList;
import java.util.List;

// gwt and smart gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.rift.coad.script.client.type.ResourceException;
import com.rift.coad.script.client.type.ResourceManager;
import com.rift.coad.type.TypeManagerDaemon;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object represents a resource
 *
 * @author brett chaldecott
 */
public class ResourceManagerImpl extends RemoteServiceServlet implements
        ResourceManager {

    // class singletons
    private static Logger log = Logger.getLogger(ResourceManagerImpl.class);

    
    /**
     * The default constructor for the resource manager.
     */
    public ResourceManagerImpl() {

    }

    
    /**
     * This method returns a list of types.
     *
     * @return The list of types.
     * @throws com.rift.coad.script.client.type.ResourceException
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
