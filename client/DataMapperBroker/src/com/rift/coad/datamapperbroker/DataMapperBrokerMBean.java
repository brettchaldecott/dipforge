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
 * DataMapperBrokerMBean.java
 */


// package path
package com.rift.coad.datamapperbroker;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coaduantion annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;


/**
 * This interface is responsible for providing a means to manager the data mapper broker
 *
 * @author brett chaldecott
 */
public interface DataMapperBrokerMBean extends Remote {
    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;


    /**
     * This method returns a list of services registered with the broker.
     *
     * @return The string list of services.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns a list of services registered with the broker.")
    @Version(number="1.0")
    @Result(description="The list of services registered with the broker.")
    public List<String> listServices() throws DataMapperBrokerException, RemoteException;


    /**
     * This method returns the list of methods registered against the given method id.
     *
     * @param serviceId The name of the service to retrieve the methods for.
     * @return The list of methods registered with the given service id.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns a list of method registered against a given service id.")
    @Version(number="1.0")
    @Result(description="The list of methods registered with the given service id.")
    public List<String> listMethods(
            @ParamInfo(name="serviceId",
            description="The id of the service to list the methods for.")String serviceId)
            throws DataMapperBrokerException, RemoteException;


    /**
     * This method describes the specified method.
     *
     * @param serviceId The id of the service.
     * @param method The name of the method.
     * @return The string containing the description of the method.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns a string description of the specified method.")
    @Version(number="1.0")
    @Result(description="The string description of the method.")
    public String describeMethodAsXML(@ParamInfo(name="serviceId",
            description="The id of the service to get the method description for.")String serviceId,
            @ParamInfo(name="method",
            description="The name of the service to get the method description for.")String method) throws
            DataMapperBrokerException, RemoteException;
}
