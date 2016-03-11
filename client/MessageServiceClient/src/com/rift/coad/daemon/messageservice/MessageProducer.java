/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2006  2015 Burntjam
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
 * MessageProducer.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is responsible for managing the producers used to create and
 * submit messages to the message service.
 *
 * @author Brett Chaldecott
 */
public interface MessageProducer extends Remote {
    
    /**
     * This is the url of the message store within an instance.
     */
    public final static String JNDI_URL = "message/MessageProducer";
    
    
    /**
     * This method is responsible for creating the message producer session
     * object.
     *
     * @return The message producer object.
     * @param from The from url of the message.
     * @throws RemoteException
     * @throws MessageServiceException
     */
    public Producer createProducer(String from) throws RemoteException, 
            MessageServiceException;
    
}
