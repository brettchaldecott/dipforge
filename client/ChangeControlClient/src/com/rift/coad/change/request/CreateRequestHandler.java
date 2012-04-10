/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * CreateRequestHandler.java
 */


// package path
package com.rift.coad.change.request;

import java.rmi.RemoteException;

/**
 * This interface exposes the methods to create a new request.
 *
 * @author brett chaldecott
 */
public interface CreateRequestHandler extends java.rmi.Remote {
    /**
     * This method initiates a request.
     *
     * @param request The request to init.
     * @throws com.rift.coad.change.request.RequestException
     * @throws java.rmi.RemoteException
     */
    public void createRequest(Request request) throws
            RequestException, RemoteException;


    /**
     * This method returns a reference to a request.
     *
     * @param id The id of the request.
     * @return The reference to the request.
     * @throws RequestBrokerException
     * @throws java.rmi.RemoteException
     */
    public Request getRequest(String id) throws
            RequestException, RemoteException;
}
