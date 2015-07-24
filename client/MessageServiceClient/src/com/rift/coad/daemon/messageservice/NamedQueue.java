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
 * Queue.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface supplies access to a queue within the message service.
 *
 * @author Brett Chaldecott
 */
public interface NamedQueue extends Remote {
    
    /**
     * This method returns a message for processing. It is designed to auto
     * acknowledge. This means that when the transaction is commited the message
     * will be removed from the named queue.
     *
     * @return The reference to the Message for processing.
     * @param delay The delay before returning a null reference.
     * @exception RemoteException
     * @exception MessageServiceException
     * @exception TimeoutException
     */
    public Message receive(long delay) throws RemoteException,
            MessageServiceException;
    
    
    /**
     * This method adds a service to the list of services used to identify this
     * queue, by the service broker.
     *
     * @param service The string containing the service name.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void addService(String service) throws RemoteException,
            MessageServiceException;
    
    
    /**
     * This method returns a list of services used to identify this queue to the
     * service broker.
     *
     * @return The list of service used to identify this queue to the service
     *      broker.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public List listServices() throws RemoteException, MessageServiceException;
    
    
    /**
     * This method removes a service from the list of services.
     *
     * @param service The name of the service to remove.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    public void removeService(String service) throws RemoteException,
            MessageServiceException;
}
