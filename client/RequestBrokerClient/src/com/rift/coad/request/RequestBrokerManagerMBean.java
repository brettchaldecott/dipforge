/*
 * RequestBrokerClient: The client libraries for the request broker.
 * Copyright (C) 2010  2015 Burntjam
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
 * RequestBrokerManagerMBean.java
 */

// package path
package com.rift.coad.request;

// imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This object manages the request broker.
 *
 * @author brett chaldecott
 */
public interface RequestBrokerManagerMBean extends Remote {

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
     * This method creates a new request.
     *
     * @param request The request to create.
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method creates the request.")
    @Version(number="1.0")
    public void createRequestFromXML(
            @ParamInfo(name="requestId",
            description="The id of the request within the xml to invoke.")String requestId,
            @ParamInfo(name="xmlRequest",
            description="The RDF request in xml format.")String xmlRequest)
            throws RequestBrokerException,
            RemoteException;


    /**
     * This method returns a list of all the requests.
     *
     * @return The string containing the list of requests.
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns a list of requests.")
    @Version(number="1.0")
    @Result(description="This method list the requests.")
    public List<String> listRequests() throws RequestBrokerException, RemoteException;


    /**
     * This method returns the request id.
     *
     * @param id The id of the request to retrieve the request for.
     * @return The request.
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the request identified in xml.")
    @Version(number="1.0")
    @Result(description="The XML encapsulating the request information.")
    public String getRequestAsXML(
            @ParamInfo(name="id",
            description="The id that identifies the request.")String id)
            throws RequestBrokerException, RemoteException;


    /**
     * This method returns the request information.
     *
     * @param id The id of the request.
     * @return The reference to the request.
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the request information as XML.")
    @Version(number="1.0")
    @Result(description="The string containing the request information as XML.")
    public String getRequestInfoAsXML(
            @ParamInfo(name="id",
            description="The id that identifies the request.")String id)
            throws RequestBrokerException, RemoteException;


    /**
     * This method is used to remove the request from the system.
     *
     * @param id The id of the request to remove.
     * @throws com.rift.coad.request.RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method is called to delete the request.")
    @Version(number="1.0")
    public void removeRequest(
            @ParamInfo(name="id",
            description="The id that identifies the request.")String id)
            throws RequestBrokerException, RemoteException;
}
