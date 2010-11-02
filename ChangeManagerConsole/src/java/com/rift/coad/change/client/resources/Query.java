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
 * Query.java
 */


// package path
package com.rift.coad.change.client.resources;

// java import
import java.util.List;


// import path
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;


/**
 * This interface defines the resource query methods.
 *
 * @author brett chaldecott
 */
public interface Query extends RemoteService{
    /**
     * This method returns the list of types associated with base types.
     *
     * @param baseTypes The list of base types to retrieve.
     * @return The array of base types.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public List<ResourceBase> listTypes(String[] baseTypes) throws QueryException;
}
