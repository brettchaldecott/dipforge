/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2006  Rift IT Contracting
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
 * MessageService.java
 */

// package path
package com.rift.coad.daemon.messageservice;


// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;


/**
 * The management interface for the message service.
 *
 * @author Brett Chaldecott
 */
public interface MessageServiceManagerMBean extends Remote{
    
    /**
     * The jndi of the message service.
     */
    public final static String JNDI_URL = "message/MessageService";
    
    /**
     * This method returns the thread pool size.
     *
     * @return The size of the thread pool.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    @MethodInfo(description="Returns the current size of the thread pool")
    @Version(number="1.0")
    @Result(description="An integer containing the size of the thread pool.")
    public int getThreadPoolSize() throws RemoteException, 
            MessageServiceException;
    
    
    /**
     * This method sets the size of the thread pool.
     *
     * @param size The new size of the thread pool.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    @MethodInfo(description="Sets the size of the thread pool used by the message service")
    @Version(number="1.0")
    public void setThreadPoolSize(@ParamInfo(name="size",
            description="The new size of the thread pool")int size) 
            throws RemoteException, MessageServiceException;
    
    
    /**
     * This method lists the named queues.
     *
     * @return The list of named queues.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    @MethodInfo(description="Returns the list of named queues assigned to this message service")
    @Version(number="1.0")
    @Result(description="The list of named queues")
    public List listNamedQueues() throws RemoteException, 
            MessageServiceException;
    
    
    /**
     * This method returns the list of messages in the named queue.
     *
     * @return The list of messages for this queue.
     * @param queueName The name of the queue to list messages for.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    @MethodInfo(description="Returns the list of message for a named queue.")
    @Version(number="1.0")
    @Result(description="The list of messages in a named queue")
    public List listMessagesForNamedQueue(@ParamInfo(name="queueName",
            description="The name of the queue to retrieve the message for")
            String queueName) throws 
            RemoteException, MessageServiceException;
    
    
    /**
     * This purges the messages from the named queue
     *
     * @param queueName The name of the queue to purge.
     * @exception RemoteException
     * @exception MessageServiceException
     */
    @MethodInfo(description="Purge the messages from a name queue.")
    @Version(number="1.0")
    public void purgeNamedQueue(@ParamInfo(name="queueName",
            description="The name of the queue to purge the messages for.")
            String queueName) throws RemoteException,
            MessageServiceException;
}
