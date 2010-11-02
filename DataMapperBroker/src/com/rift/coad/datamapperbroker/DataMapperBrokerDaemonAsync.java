/*
 * DataMapperBrokerMBean: The data mapper broker client interface.
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
 * DataMapperBrokerDaemonAsync.java
 */


package com.rift.coad.datamapperbroker;

// rmi imports
import java.rmi.Remote;

// coadunation imports
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;
import java.rmi.RemoteException;

/**
 * This is the asynchronous version of the data mapper broker.
 *
 * @author brett chaldecott
 */
public interface DataMapperBrokerDaemonAsync extends Remote {

    /**
     * This method is responsible for registering the given methods against the specified service id.
     *
     * @param serviceId The service id.
     * @param methods The list of methods to register
     * @return The id of the message in the que.
     * @throws java.rmi.RemoteException
     */
    public String register(String serviceId, DataMapperMethod[] methods) throws RemoteException;

}
