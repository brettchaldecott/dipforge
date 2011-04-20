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
import com.rift.coad.type.manager.client.dto.ResourceDefinition;
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
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

    public void addType(ResourceDefinition base) throws ManageResourcesException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateType(ResourceDefinition base) throws ManageResourcesException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceDefinition getType(String name) throws ManageResourcesException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteType(String name) throws ManageResourcesException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ResourceDefinition> listTypes(String[] baseTypes) throws ManageResourcesException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method is called to add a new type.
     *
     * @param base The base type.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    /*public void addType(ResourceBase base) throws ManageResourcesException {
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
    }*/


    


}
