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
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface defines the async version of the data mapper interface.
 *
 * @author brett chaldecott
 */
public interface DataMapperAsync extends Remote {
    /**
     * This is the asynchronous version of the data mapper executer.
     *
     * @param serviceId The service id that the execution is taking place on.
     * @param method The method to invoke.
     * @param rdfXML The xml.
     * @return The Message id.
     * @throws RemoteException
     */
    public String execute(String serviceId, String method, String rdfXML) throws RemoteException;
}
