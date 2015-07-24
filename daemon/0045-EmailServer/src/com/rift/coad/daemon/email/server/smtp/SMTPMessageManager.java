/*
 * Email Server: The email server interface
 * Copyright (C) 2008  2015 Burntjam
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
 * SMTPMessageManager.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp;

// java imports
import java.util.Map;

// coadunation imports
import com.rift.coad.util.transaction.CoadunationHashMap;

// email imports
import com.rift.coad.daemon.email.smtp.Message;

/**
 * This object is responsible for managing the smtp messagers.
 *
 * @author brett chaldecott
 */
public class SMTPMessageManager {
    
    // private member variables
    private Map messageMap = new CoadunationHashMap();
    
    /**
     * Creates a new instance of SMTPMessageManager
     */
    public SMTPMessageManager() {
    }
    
    
    /**
     * This method adds a new message to the map.
     *
     * @param The message to add
     */
    public void addMessage(SMTPServerMessage message) {
        messageMap.put(message.getId(),message);
    }
    
    
    /**
     * This method is called to retrieve a message for the given id.
     *
     * @return The reference to the message.
     * @param id The id of the message.
     */
    public SMTPServerMessage getMessage(String id) {
        return (SMTPServerMessage)messageMap.get(id);
    }
    
    
    /**
     * This method removes the message from the map.
     *
     * @param id The id of the message to remove from the map.
     */
    public void removeMessage(String id) {
        messageMap.remove(id);
    }
    
    
    /**
     * This method returns the map containing all the messages.
     *
     * @return The map containing the messages
     */
    public Map getList() {
        return messageMap;
    }
}
