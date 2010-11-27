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
 * RoutingClient.java
 */

// packge path
package com.rift.coad.daemon.messageservice.routing.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;

/**
 * This interface defines the routing client interface.
 *
 * @author brett
 */
public interface RoutingClient extends AsyncCallbackHandler, Remote {
    
    // class constants
    public final static String JNDI_URL = "messagetest/RoutingClient";
    
    /**
     * This method is called to test the routing.
     *
     * @param target The target the tests will be routed to.
     * @param number The number of messages to send.
     * @exception RoutingTestException
     * @exception RemoteException
     */
    public void testRouting(String target, int number) throws 
            RoutingTestException, RemoteException;
    
}
