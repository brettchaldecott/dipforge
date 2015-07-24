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
 * TextMessageImpl.java
 */

// package path
package com.rift.coad.daemon.messageservice.message;

// java imports
import java.util.Date;
import java.util.List;


// coadunation imports
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.TextMessage;

/**
 * The implementation of the text message object.
 *
 * @author Brett Chaldecott
 */
public class TextMessageImpl extends MessageImpl implements TextMessage {
    
    // private member variables
    private String txtMessage = null;
    
    /**
     * Creates a new instance of TextMessageImpl
     *
     * @param messageId The unique identifier for this message.
     * @param user The user of this message.
     * @param sessionId The id of this user session.
     * @param principals The list of principals assigned to this message.
     * @param status The status of this message.
     */
    public TextMessageImpl(String messageId,String user, String sessionId,
            List principals, int status) {
        super(messageId,user,sessionId,principals,status);
    }
    
    
    /**
     * Creates a new instance of MessageImpl.
     *
     * @param messageId The id of the message that was created.
     * @param create The created time stamp.
     * @param retries The number of retries of this message.
     * @param processedDate The last time this message was processed.
     * @param user The name of the user.
     * @param sessionId The id of this user session.
     * @param principals The list of principals.
     * @param from The from address of the message.
     * @param messageType The type of message being used.
     * @param status The status of this message.
     */
    public TextMessageImpl(String messageId, Date created, int retries, 
            Date processedDate,String user, String sessionId, List principals, 
            String from, int messageType, int status) {
        super(messageId,created,retries,processedDate,user,sessionId,principals,
                from,messageType, status);
    }
    
    
    /**
     * This clears the body of the message.
     *
     * @exception MessageServiceException
     */
    public void clearBody() throws MessageServiceException {
        txtMessage = null;
    }
    
    
    /**
     * This method sets the text body of a text message.
     *
     * @param text The text to set as the body of this message.
     * @exception MessageServiceException
     */
    public void setTextBody(String text) throws MessageServiceException {
        this.txtMessage = text;
    }
    
    
    /**
     * This method returns the text body of this message.
     *
     * @return The string containing the text body.
     * @exception MessageServiceException
     */
    public String getTextBody() throws MessageServiceException {
        return txtMessage;
    }
    
}
