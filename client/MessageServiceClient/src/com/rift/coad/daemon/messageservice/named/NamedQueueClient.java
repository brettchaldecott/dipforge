/*
 * <Add library description here>
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
 * NamedQueueClient.java
 */

// package path
package com.rift.coad.daemon.messageservice.named;

// java imports
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.QueueManager;
import com.rift.coad.daemon.messageservice.NamedQueue;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object is responsible for managing the connection to the named queue
 * object.
 *
 * @author Brett Chaldecott
 */
public class NamedQueueClient {
    
    // class constants
    private static long MAX_TIMEOUT = 1000;
    
    // log refernce
    private Logger log = Logger.getLogger(NamedQueueClient.class.getName());
    
    // private member variables
    private Context context = null;
    private String jndiUrl = null;
    private String name = null;
    private NamedQueue namedQueue = null;
    
    
    /** 
     * Creates a new instance of NamedQueueClient
     *
     * @param name The name of the queue to make a connection to.
     * @exception MessageServiceException
     */
    private NamedQueueClient(String name) throws MessageServiceException {
        try {
            this.jndiUrl = QueueManager.JNDI_URL;
            this.name = name;
        } catch (Exception ex) {
            throw new MessageServiceException(
                    "Failed to instanciate the NamedQueueClient : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /** 
     * Creates a new instance of NamedQueueClient
     *
     * @param context The context that should be used to make a connection to
     *      the message service.
     * @param jndiUrl The url of the message queue manager.
     * @param name The name of the queue to connect to.
     * @exception MessageServiceException
     */
    private NamedQueueClient(Context context,String jndiUrl, String name) throws 
            MessageServiceException {
        try {
            context = new InitialContext();
            this.jndiUrl = jndiUrl;
            this.name = name;
        } catch (Exception ex) {
            throw new MessageServiceException(
                    "Failed to instanciate the NamedQueueClient : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns an instance of the NamedQueueClient object.
     *
     * @return The reference to the NamedQueueClient object.
     * @param name The name of the queue to return.
     * @exception MessageServiceException
     */
    public static NamedQueueClient create(String name) throws 
            MessageServiceException {
        return new NamedQueueClient(name);
    }
    
    
    /**
     * This method returns an instance of the NamedQueueClient object.
     *
     * @return The reference to the NamedQueueClient object.
     * @param context The context that should be used to make a connection to
     *      the message service.
     * @param jndiUrl The url of the message queue manager.
     * @param name The name of the queue to connect to.
     * @exception MessageServiceException
     */
    public static NamedQueueClient create(Context context,String jndiUrl, 
            String name) throws MessageServiceException {
        return new NamedQueueClient(context,jndiUrl,name);
    }
    
    
    /**
     * This method returns the reference to the received message from the
     * named queue, or null if not message is available.
     *
     * @return A reference to the retrieved message or NULL.
     * @param delay The delay in processing.
     * @exception MessageServiceException
     */
    public Message receive(long delay) throws MessageServiceException {
        Date startTime = new Date();
        Date currentTime = null;
        while((startTime.getTime() + delay) > 
                (currentTime = new Date()).getTime()) {
            try {
                NamedQueue namedQueue = getNamedQueue();
                Message result = null;
                if ((delay == 0) || (delay > MAX_TIMEOUT)) {
                    result = namedQueue.receive(MAX_TIMEOUT);
                } else {
                    result = namedQueue.receive(delay);
                }
                if (result != null) {
                    return result;
                }
            } catch (java.rmi.RemoteException ex) {
                log.error("Failed to retrieve the queue entry : " + 
                        ex.getMessage(),ex);
                this.namedQueue = null;
            } catch (MessageServiceException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new MessageServiceException(
                        "Failed to retrieve a message : " + ex.getMessage(),ex);
            }
        }
        return null;
    }
    
    
    /**
     * This method returns the named queue
     *
     * @return The reference to the named queue.
     * @exception MessageServiceException
     */
    private NamedQueue getNamedQueue() throws MessageServiceException {
        try {
            if (namedQueue != null) {
                return namedQueue;
            }
            QueueManager queueManager = null;
            if (context == null) {
                queueManager= (QueueManager)ConnectionManager
                        .getInstance().getConnection(QueueManager.class,
                        this.jndiUrl);
            } else {
                queueManager= (QueueManager)ConnectionManager
                        .getInstance(context).getConnection(QueueManager.class,
                        this.jndiUrl);
            }
            return this.namedQueue = queueManager.getNamedQueue(this.name);
        } catch (Exception ex) {
            throw new MessageServiceException(
                    "Failed to retrieve a named queue because : " + 
                    ex.getMessage(),ex);
        }
    }
}
