/*
 * MessageService: The message service daemon
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
 * MessageManagerFactory.java
 */

// the package path
package com.rift.coad.daemon.messageservice.message;

import com.rift.coad.daemon.messageservice.*;



/**
 * This object is responsible for creating the requested message managers.
 * Identified by id or Message reference.
 *
 * @author Brett Chaldecott
 */
public class MessageManagerFactory {
    
    // the classes singleton information
    private static MessageManagerFactory singleton = null;
    
    /** 
     * Creates a new instance of MessageManagerFactory 
     */
    private MessageManagerFactory() {
    }
    
    
    /**
     * This method returns the message manager factory instance.
     *
     * @return The reference to the message manager factory.
     */
    public static MessageManagerFactory getInstance() {
        if (singleton == null) {
            singleton = new MessageManagerFactory();
        }
        return singleton;
    }
    
    
    /**
     * This method returns a message for the given id.
     *
     * @return The reference to the message manager.
     * @param id The id of the message.
     * @exception MessageServiceException
     */
    public MessageManager getMessageManager(String id) throws 
            MessageServiceException {
        return new MessageManagerImpl(id);
    }
    
    
    /**
     * This method returns a message for the given id.
     *
     * @return The reference to the message manager.
     * @param id The id of the message.
     * @exception MessageServiceException
     */
    public MessageManager getMessageManager(Message message) throws
            MessageServiceException {
        return new MessageManagerImpl(message);
    }
}
