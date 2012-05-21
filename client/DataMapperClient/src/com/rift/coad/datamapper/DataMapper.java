/*
 * DataMapperClient: The client information for the data mapper.
 * Copyright (C) 2011  Rift IT Contracting
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
 * DataMapper.java
 */

package com.rift.coad.datamapper;

import com.rift.coad.rdf.types.mapping.MethodMapping;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface
 *
 * @author brett chaldecott
 */
public interface DataMapper extends Remote {
    /**
     * This method is responsible for executing the relevant method on the data mapper.
     *
     * @param methodId The id of the method.
     * @param parameters The list of parameters used by this object.
     * @return The result serialized in RDF xml.
     * @throws DataMapperException
     * @throws RemoteException
     */
    public Object execute(MethodMapping method, List<Object> parameters)
            throws DataMapperException, RemoteException;
}
