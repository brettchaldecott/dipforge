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
 * QueueManager.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The QueueManager supplies access to the named queues.
 * @author Brett Chaldecott
 */
public interface QueueManager extends Remote {
    
    /**
     * The jndi url for the message queue manager.
     */
    public final static String JNDI_URL = "message/QueueManager";
    
    // class constants
    public final static String DEAD_LETTER = "DEAD_LETTER";
    
    /**
     * This method returns the queue specified by the name. If the queue does
     * not exist it gets created.
     *
     * @return The queue identified by the name.
     * @param name The name of the queue to retrieve.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public NamedQueue getNamedQueue(String name) throws RemoteException,
            MessageServiceException;
}
