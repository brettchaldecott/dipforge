/*
 * MessageService: The message service daemon
 * Copyright (C) 2007  2015 Burntjam
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
 * MessageManager.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.util.Date;
import javax.transaction.xa.XAResource;

/**
 * This interface defines the methods responsible for managing a message within
 * the the message service.
 *
 * @author Brett Chaldecott
 */
public interface MessageManager extends Comparable,XAResource  {
    /**
     * This method returns the id of this messsage.
     *
     * @return The id of the message this object is managing.
     */
    public String getID();
    
    
    /**
     * This method returns the message object.
     *
     * @return The message object.
     * @exception MessageServiceException
     */
    public Message getMessage() throws MessageServiceException;
    
    
    /**
     * This method updates the message object.
     *
     * @param updatedMessage The updated message object.
     * @exception MessageServiceException
     */
    public void updateMessage(Message updatedMessage) throws 
            MessageServiceException;
    
    
    /**
     * This method returns the next process time for this message.
     *
     * @return The date message.
     * @exception MessageServiceException
     */
    public Date nextProcessTime();
    
    
    /**
     * This method returns the name of the messaqe queue to which this message
     * is assigned.
     *
     * @return The name of the message queue that this message is assigned to.
     */
    public String getMessageQueueName();
    
    
    /**
     * This method is responsible from removing this message from the db.
     *
     * @exception MessageServiceException
     */
    public void remove() throws MessageServiceException;
    
}
