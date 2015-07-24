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
 * Producer.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The producer is responsible for creating and sending message for a single
 * client. It is a session based RMI object.
 *
 * @author Brett Chaldecott
 */
public interface Producer extends Remote{
    /**
     * This method is responsible for creating a new text message for the
     * message service.
     *
     * @return A newly created text message.
     * @param type The type of message.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public TextMessage createTextMessage(int type) throws RemoteException, 
            MessageServiceException;
    
    
    /**
     * This method is responsible for creating a new RPC message for the
     * message service.
     *
     * @return A newly created text message.
     * @param type The type of message.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public RPCMessage createRPCMessage(int type) throws RemoteException, 
            MessageServiceException;
    
    
    /**
     * This method is responsible for submitting a new message for processing by
     * the message service.
     *
     * @param newMessage The new message to be processed.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void submit(Message newMessage) throws RemoteException, 
            MessageServiceException;
}
