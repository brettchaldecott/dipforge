/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 2015 Burntjam
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
 * NamedClient1.java
 */

// package path
package com.rift.coad.daemon.messageservice.named.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The name client test.
 *
 * @author Brett Chaldecott
 */
public interface NamedClient1 extends Remote {
    
    // jndi name
    public final static String JNDI_URL = "messagetest/NamedClient1";
    
    /**
     * This method is called to run a basic named message test.
     *
     * @param numMessages The number of messages to test the queues with.
     * @exception NamedTestException
     */
    public void runBasicTest(int numMessages) throws RemoteException,
            NamedTestException;
}
