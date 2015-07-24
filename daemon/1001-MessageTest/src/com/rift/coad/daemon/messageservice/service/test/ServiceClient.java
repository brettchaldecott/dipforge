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
 * ServiceClient.java
 */

// package path
package com.rift.coad.daemon.messageservice.service.test;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;


/**
 * This object is responsible for controlling the service client test.
 *
 * @author Brett Chaldecott
 */
public interface ServiceClient extends AsyncCallbackHandler {
    
    /**
     * The jndi url
     */
    public final static String JNDI_URL = "messagetest/ServiceClient";
    
    
    /**
     * This method is called to run the test.
     *
     * @param num The number of tests to run.
     * @exception ServiceException
     * @exception RemoteException
     */
    public void runTest(int num) throws ServiceException, RemoteException;
}
