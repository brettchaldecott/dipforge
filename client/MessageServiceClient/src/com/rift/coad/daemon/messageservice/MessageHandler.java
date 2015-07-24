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
 * MessageHandler.java
 */

package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The message handler interface is called by the message service to process a
 * message.
 *
 * @author Brett Chaldecott
 */
public interface MessageHandler extends Remote {
    /**
     * This method is called to process a message.
     *
     * @return The processed message. Cannot use IN/OUT as RMI does not support 
     *          it.
     * @param msg The message to perform the processing on.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public Message processMessage(Message msg) throws RemoteException, 
            MessageServiceException;
}
