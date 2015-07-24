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

// package path
package com.rift.coad.daemon.messageservice.message.state;

// imports
import com.rift.coad.daemon.messageservice.MessageHeader;
import java.util.Properties;

/**
 *
 * @author brettc
 */
public class MessageHeaderImpl extends MessageHeader {

    // private member variables
    private Properties properties;

    /**
     * The message header constructor
     */
    private MessageHeaderImpl() {
        properties = new Properties();
    }



    /**
     * This method is called to init the message header.
     *
     * @param message The message session
     */
    public static void initMessageHeader() {
        if (local.get() == null) {
            local.set(new MessageHeaderImpl());
        }
    }


    /**
     * This method returns the property value identified by the key.
     *
     * @param key The key to return
     * @return
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    /**
     * This method returns true if the
     *
     * @param key The string key to set.
     * @return TRUE if found, FALSE if not.
     */
    public boolean containsProperty(String key) {
        return properties.containsKey(key);
    }


    /**
     * This method sets the property value.
     *
     * @param key The key to set.
     * @param value The value to set.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }


    /**
     * This method returns the properties
     *
     * @return The list of properties
     */
    public Properties getProperties() {
        return properties;
    }



}
