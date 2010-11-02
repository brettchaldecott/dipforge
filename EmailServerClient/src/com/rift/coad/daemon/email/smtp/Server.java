/*
 * Email Server: The email server interface
 * Copyright (C) 2008  Rift IT Contracting
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
 * Server.java
 */

// package path
package com.rift.coad.daemon.email.smtp;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.daemon.messageservice.AsyncCallbackHandler;


/**
 * This interface is responsible for storing the smtp message for processing and
 * access later on.
 *
 * @author brett chaldecott
 */
public interface Server extends AsyncCallbackHandler {
    
    /**
     * This method sends a message.
     *
     * @param message The message to store.
     * @exception SMTPException
     * @exception RemoteExeption
     */
    public void sendMessage(Message message) throws SMTPException, 
            RemoteException;
    
    
    /**
     * This method returns the message based on the id.
     *
     * @return The message to return.
     * @param id The id of the message to retrieve.
     * @exception SMTPException
     * @exception RemoteException
     */
    public Message getMessage(String id) throws SMTPException, 
            RemoteException;
}
