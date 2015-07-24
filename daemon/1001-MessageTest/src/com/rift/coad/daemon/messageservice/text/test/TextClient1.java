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
 * TextClient1.java
 */

// the package path
package com.rift.coad.daemon.messageservice.text.test;

// java imports
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.daemon.messageservice.MessageHandler;

/**
 * The interface responsible for 
 *
 * @author Brett Chaldecott
 */
public interface TextClient1 extends MessageHandler {
    
    // jndi name
    public final static String JNDI_NAME = "messagetest/TextClient1";
    
    /**
     * This method is called to init the tests.
     *
     * @param numTests The number of tests.
     * @exception TextTestException
     */
    public void startTests(long numTests) throws RemoteException,
            TextTestException;
}
