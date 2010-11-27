/*
 * ChangeControlConsole: The console for the change manager
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
 * QueryImpl.java
 */


// package path
package com.rift.coad.change.server.resources;

// java imports
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// gwt improts
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// change manager console imports
import com.rift.coad.change.client.resources.Query;
import com.rift.coad.change.client.resources.QueryException;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.type.TypeManagerDaemon;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * This
 *
 * @author brett chaldecott
 */
public class QueryImpl extends RemoteServiceServlet implements
        Query {

    // private member variables
    private static Logger log = Logger.getLogger(QueryImpl.class);

    /**
     * The constructor for the query implementation.
     */
    public QueryImpl() {
    }


    /**
     * This method returns the list of types.
     *
     * @param baseTypes 
     * @return
     * @throws com.rift.coad.change.client.resources.QueryException
     */
    public List<ResourceBase> listTypes(String[] baseTypes) throws QueryException {
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
            throw new QueryException("Failed to list the types : " +
                    ex.getMessage());
        }
    }
}
