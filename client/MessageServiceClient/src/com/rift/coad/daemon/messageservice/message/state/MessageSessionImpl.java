/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2011  Rift IT Contracting
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
 * MessageSessionImpl.java
 */


package com.rift.coad.daemon.messageservice.message.state;

import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageSession;

/**
 * The implementation of the message session.
 *
 * @author brett chaldecott
 */
public class MessageSessionImpl extends MessageSession {

    // private member variables
    private Message message;


    /**
     * The constructor of the message header.
     *
     * @param message The message information.
     */
    private MessageSessionImpl(Message message) {
        this.message = message;
    }


    /**
     * This method is called to init the message session.
     *
     * @param message The message session
     */
    public static void initMessageSession(Message message) {
        if (local.get() == null) {
            local.set(new MessageSessionImpl(message));
        }
    }


    /**
     * This method is called to release the message from the session.
     */
    public static void resetMessageSession() {
        local.set(null);
    }


    /**
     * The message information.
     *
     * @return The message information.
     */
    public Message getMessage() {
        return message;
    }

}
