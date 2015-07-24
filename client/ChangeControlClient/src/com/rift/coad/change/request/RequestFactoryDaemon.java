/*
 * ChangeControlClient: The client library for the change control client.
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
 * RequestFactoryDaemon.java
 */

package com.rift.coad.change.request;

// java import
import java.rmi.Remote;
import java.rmi.RemoteException;


// the request
import java.util.List;


/**
 * This interface defines the methods to interact with the request.
 *
 * @author brett chaldecott
 */
public interface RequestFactoryDaemon extends Remote {


    /**
     * This method initiates a request.
     *
     * @param request The request to init.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public RequestFactoryObject createRequest(Request request) throws RequestException,
            RemoteException;


    /**
     * This method returns the request.
     *
     * @param id The id of the request.
     * @return The request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public RequestFactoryObject getRequest(String id) throws RequestException,
            RemoteException;


    /**
     * This method is responsible for removing the request identified by the id.
     *
     * @param id The id of the request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public void removeRequest(String id) throws RequestException,
            RemoteException;


    /**
     * This method returns the list of active requests.
     * @return The list of requests.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public List<String> listRequests() throws RequestException, RemoteException;
}
