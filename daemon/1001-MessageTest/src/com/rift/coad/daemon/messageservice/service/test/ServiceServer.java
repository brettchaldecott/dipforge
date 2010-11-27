/*
 * MessageTest: This is a test message service library.
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
 * ServiceServer.java
 */

// package path
package com.rift.coad.daemon.messageservice.service.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface is used to test the RPC service support.
 *
 * @author Brett Chaldecott
 */
public interface ServiceServer extends Remote {
    
    /**
     * The jndi url of the service server.
     */
    public final static String JNDI_URL = "messagetest/ServiceServer";
    
    /**
     * This method is called to test the service for this server.
     *
     * @param value The test value for this method
     * @exception RemoteException
     */
    public void callMethod(String value) throws RemoteException;
}
