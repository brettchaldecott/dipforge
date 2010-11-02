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
 * TextMessage.java
 */

// package path
package com.rift.coad.daemon.messageservice;



/**
 * This interface represents a text message.
 *
 * @author Brett Chaldecott
 */
public interface TextMessage extends Message {
    /**
     * This method sets the text body of a text message.
     *
     * @param text The text to set as the body of this message.
     * @exception MessageServiceException
     */
    public void setTextBody(String text) throws MessageServiceException;
    
    
    /**
     * This method returns the text body of this message.
     *
     * @return The string containing the text body.
     * @exception MessageServiceException
     */
    public String getTextBody() throws MessageServiceException;
}
