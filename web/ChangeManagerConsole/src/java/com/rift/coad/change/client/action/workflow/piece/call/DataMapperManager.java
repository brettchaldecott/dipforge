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
 * PieceCall.java
 */

// package path
package com.rift.coad.change.client.action.workflow.piece.call;

// the remove service
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;


/**
 * This interface defines the data mapper manager interface.
 *
 * @author brett chaldecott
 */
public interface DataMapperManager extends RemoteService{
    
    
    /**
     * This method returns a list of services.
     *
     * @return The list of services registered with the data mapper.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public String[] listServices() throws DataMapperManagerException;


    /**
     * This method returns a list of jndi references for the given service.
     *
     * @param service The string containing the service name.
     * @return The list of jndi references for the given service.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public String[] listJNDIForService(String service) throws DataMapperManagerException;


    /**
     * This method returns a list of all the methods attached to a given service.
     *
     * @param service The string that identifies the service.
     * @return The array containing the methods.
     * @throws com.rift.coad.change.client.action.workflow.piece.call.DataMapperManagerException
     */
    public DataMapperMethod[] listMethods(String service) throws DataMapperManagerException;
}
