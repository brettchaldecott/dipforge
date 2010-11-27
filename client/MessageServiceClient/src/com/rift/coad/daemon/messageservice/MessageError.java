/*
 * MessageQueueClient: The message queue client library
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
 * MessageError.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.util.Date;
import java.io.Serializable;

/**
 * This object describes a message error.
 *
 * @author Brett Chaldecott
 */
public class MessageError implements Serializable {
    
    // private member variables
    private Date errorDate = null;
    private int level = 0;
    private String msg = null;
    
    /** 
     * Creates a new instance of MessageError 
     *
     * @param errorDate The date the error occurred.
     * @param level The level of the error message.
     * @param msg The message content for the error.
     */
    public MessageError(Date errorDate, int level, String msg) {
        this.errorDate = errorDate;
        this.level = level;
        this.msg = msg;
    }
    
    
    /**
     * This method returns the error date.
     *
     * @return The error date object.
     */
    public Date getErrorDate() {
        return errorDate;
    }
    
    
    /**
     * This method returns the error level.
     *
     * @return An int containing the error level.
     */
    public int getLevel() {
        return level;
    }
    
    
    /**
     * This method returns the errorr message.
     *
     * @return The string containing the error message.
     */
    public String getMSG() {
        return msg;
    }
}
