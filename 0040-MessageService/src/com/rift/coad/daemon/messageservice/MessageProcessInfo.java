/*
 * MessageService: The message service daemon
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
 * MessageInfo.java
 */

package com.rift.coad.daemon.messageservice;

/**
 * This object contains the message processing information. The queue that the
 * message was retrieved from, pluss the message manager to manipulate the
 * message.
 *
 * @author Brett Chaldecott
 */
public class MessageProcessInfo {
    
    // the classes member variables
    private MessageQueue messageQueue = null;
    private MessageManager messageManager = null;
    
    /** 
     * Creates a new instance of MessageInfo 
     *
     * @param messageQueue The queue the message was retrieve from.
     * @param messageManager The message manager responsible for manipulating
     *      this message.
     */
    public MessageProcessInfo(MessageQueue messageQueue, 
            MessageManager messageManager) {
        this.messageQueue = messageQueue;
        this.messageManager = messageManager;
    }
    
    
    /**
     * This method returns the message queue.
     *
     * @return The reference to the message queue.
     */
    public MessageQueue getMessageQueue() {
        return messageQueue;
    }
    
    
    /**
     * This method returns the message manager reference.
     *
     * @return The reference to the message manager.
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
