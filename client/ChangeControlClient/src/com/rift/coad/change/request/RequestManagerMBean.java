/*
 * ChangeControlManager: The manager of the change control.
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
 * RequestManagerMBean.java
 */

// the package path
package com.rift.coad.change.request;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;

// the annotation import
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import java.util.List;


/**
 * The management interface for the request manager.
 * 
 * @author brett chaldecott
 */
public interface RequestManagerMBean extends Remote {

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
     * This method initiates a request.
     *
     * @param request The request to init.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Initialize a request using the XML as the source.")
    @Version(number="1.0")
    public void initRequestFromXML(
            @ParamInfo(name="id",
            description="The id of the request in the xml to spawn.")String id,
            @ParamInfo(name="XML",
            description="The contents of the request.")String xml) throws RequestException,
            RemoteException;


    /**
     * This method returns the request as xml.
     *
     * @param id The id of the request.
     * @return The request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns a string containing the XML for the specified request.")
    @Version(number="1.0")
    @Result(description="The string containing the xml definition of the request.")
    public String getRequestAsXML(
            @ParamInfo(name="id",
            description="The id identifiying the request.")String id) throws RequestException,
            RemoteException;


    /**
     * This method is responsible for removing the request identified by the id.
     *
     * @param id The id of the request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method removes the specified request.")
    @Version(number="1.0")
    public void removeRequest(
            @ParamInfo(name="id",
            description="The id identifiying the request.")String id) throws RequestException,
            RemoteException;
    

    /**
     * This method returns the list of active requests.
     *
     * @return The list of active requests.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns a list of active requests.")
    @Version(number="1.0")
    @Result(description="Returns a list of active requests.")
    public List<String> listRequests() throws RequestException, RemoteException ;
}
