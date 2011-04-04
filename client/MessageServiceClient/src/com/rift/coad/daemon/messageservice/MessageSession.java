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
 * MessageSession.java
 */

package com.rift.coad.daemon.messageservice;

/**
 * The message information local
 *
 * @author brett chaldecott
 */
public abstract class MessageSession {

    protected static ThreadLocal local;

    static {
        local = new ThreadLocal();
    }
    
    /**
     * This method returns the message session.
     *
     * @return The message service.
     */
    public static MessageSession getMessageSession() {
        return (MessageSession)local.get();
    }


    /**
     * The message information.
     *
     * @return The message information.
     */
    public abstract Message getMessage();
}
