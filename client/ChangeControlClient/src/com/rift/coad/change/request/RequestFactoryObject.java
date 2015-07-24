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
 * RequestFactoryObject.java
 */

// package path
package com.rift.coad.change.request;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface defines the methods that manage an individual request instance.
 *
 * @author brett chaldecott
 */
public interface RequestFactoryObject extends Remote {

    /**
     * This method returns the information on the request.
     *
     * @return The reference to the request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public Request getInfo() throws RequestException, RemoteException;


    /**
     * This method is responsible for updating the request information.
     *
     * @param request
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public void update(Request request) throws RequestException, RemoteException;


    /**
     * This method sets the status of the this request.
     *
     * @param requestId The id of this request.
     * @param status The status of the request.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public String setStatus(String requestId, String status, String message)
            throws RequestException, RemoteException;

}
