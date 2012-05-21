/*
 * DataMapperClient: The client information for the data mapper.
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
 * DataMapper.java
 */


// package path
package com.rift.coad.datamapper;

// java imports
import com.rift.coad.rdf.types.mapping.MethodMapping;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the async version of the data mapper interface.
 *
 * @author brett chaldecott
 */
public interface DataMapperAsync extends Remote {
    /**
     * This is the asynchronous version of the data mapper executer.
     *
     * @param methodId The id of the method.
     * @param rdfXML XML containing the rdf structures to deserialize on the target.
     * @return The Message id.
     * @throws RemoteException
     */
    public String execute(MethodMapping method, List<Object> parameters) throws RemoteException;
}
