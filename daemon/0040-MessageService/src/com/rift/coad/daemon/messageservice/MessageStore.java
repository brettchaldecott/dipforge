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
 * MessageStore.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface provides access to the message store.
 *
 * @author Brett Chaldecott
 */
public interface MessageStore extends Remote {
    
    /**
     * This is the url of the message store within an instance.
     */
    public final static String JNDI_URL = "message/MessageStore";
    
    /**
     * This method adds a message to the message store of a message service.
     *
     * @param message The message to add to the store.
     * @exception MessageServiceException
     * @exception RemoteException
     */
    public void addMessage(Message message) throws MessageServiceException, 
            RemoteException;
}
