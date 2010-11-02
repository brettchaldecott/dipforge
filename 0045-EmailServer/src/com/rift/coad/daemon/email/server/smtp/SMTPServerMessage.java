/*
 * Email Server: The email server.
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
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.util.Date;

// email imports
import com.rift.coad.daemon.email.smtp.Message;
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.SMTPException;


/**
 * This method represents a new server message.
 *
 * @author brett chaldecott
 */
public class SMTPServerMessage {
    
    // private member variables
    private Message message = null;
    private String messageServiceId = null;
    private Date retryDate = null;
    private int retries = 0;
    private String routeName = null;
    
    /**
     * Creates a new instance of SMTPServerMessage
     *
     * @param message The message
     */
    public SMTPServerMessage(Message message) {
        this.message = message;
    }
    
    
    /**
     * Creates a new instance of SMTPServerMessage
     *
     * @param message The message.
     * @param messageServiceId The id of the message service.
     * @param retryDate The date of the retry.
     * @param retries The max number of retries.
     * @param routeName The name of the route.
     */
    public SMTPServerMessage(Message message, String messageServiceId,
            Date retryDate, int retries, String routeName) {
        this.message = message;
        this.messageServiceId = messageServiceId;
        this.retryDate = retryDate;
        this.retries = retries;
        this.routeName = routeName;
    }
    
    /**
     * This method returns the id of the message.
     */
    public String getId() {
        return message.getId();
    }
    
    /**
     * This method returns the message containied within the smtp message.
     *
     * @return The reference to the message object.
     */
    public Message getMessage() {
        return message;
    }
    
    
    /**
     * This method returns the message service id.
     *
     * @return The message id.
     */
    public String getMessageServiceId() {
        return messageServiceId;
    }
    
    
    /**
     * This method sets the message service id and increments the retry date.
     *
     * @param messageServiceId The id assigned to this message by the message
     * service.
     */
    public void setMessageServiceId(String messageServiceId) {
        this.messageServiceId = messageServiceId;
        retryDate = new Date();
    }
    
    
    /**
     * This method returns the retry date.
     */
    public Date getRetryDate() {
        return retryDate;
    }
    
    
    /**
     * This method is called to increment the retries.
     */
    public int incrementRetries() {
        return ++retries;
    }
    
    /**
     * This method resets the retry count to zero.
     */
    public void resetRetries() {
        retries = 0;
    }
    
    
    /**
     * This method returns the retries.
     *
     * @return The number of retries had by this message.
     */
    public int getRetries() {
        return retries;
    }
    
    
    /**
     * This method sets the route name that is currently handling this message.
     *
     * @param name The name that is handling this message.
     */
    public void setRouteName(String name) {
        this.routeName = name;
    }
    
    
    /**
     * This method returns the route name that is currently handling this
     * message.
     *
     * @return The name that is currently handling this message.
     */
    public String getRouteName() {
        return routeName;
    }
}
