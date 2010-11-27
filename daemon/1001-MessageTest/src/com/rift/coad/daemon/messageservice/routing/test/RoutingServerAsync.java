/*
 * MessageService: The message service daemon
 * Copyright (C) 2007 Rift IT Contracting
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
 * RoutingServerAsync.java
 */

// package path
package com.rift.coad.daemon.messageservice.routing.test;


// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface defines the routing test server interface.
 *
 * @author Brett Chaldecott
 */
public interface RoutingServerAsync extends Remote {
    /**
     * This method is responsible for testing the routing.
     *
     * @return The string containing the results of the routed message.
     * @param message The message to send to the server.
     * @exception RoutingTestException
     * @exception RemoteException
     */
    public String testRoutingMethod(String message) throws RoutingTestException,
            RemoteException;
}
