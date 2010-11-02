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
 * BroadcastServer.java
 */

// package path
package com.rift.coad.daemon.messageservice.broadcast.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is used to test the broadcast functionality supplied with
 * coadunation.
 *
 * @author Brett Chaldecott
 */
public interface BroadcastServer extends Remote {
    
    /**
     * The definition of the test method.
     *
     * @return The string result.
     * @param message The message result.
     * @exception RemoteException
     */
    public String testMethod(String message) throws RemoteException;
}
