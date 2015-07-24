/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2011  2015 Burntjam
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
 * MessageHeader.java
 */


package com.rift.coad.daemon.messageservice;

/**
 * The message header is a class which enables the Async RPC based methods to
 * access the properties for a message.
 *
 * @author brett chaldecott
 */
public abstract class MessageHeader {

    // protected thread local
    protected static ThreadLocal local;

    static {
        local = new ThreadLocal();
    }

    
    /**
     * This method returns the message session.
     *
     * @return The message service.
     */
    public static MessageHeader getMessageHeader() {
        return (MessageHeader)local.get();
    }


    /**
     * This method returns the property value identified by the key.
     *
     * @param key The key to return
     * @return
     */
    public abstract String getProperty(String key);


    /**
     * This method returns true if the
     *
     * @param key The string key to set.
     * @return TRUE if found, FALSE if not.
     */
    public abstract boolean containsProperty(String key);


    /**
     * This method sets the property value.
     *
     * @param key The key to set.
     * @param value The value to set.
     */
    public abstract void setProperty(String key, String value);
}
