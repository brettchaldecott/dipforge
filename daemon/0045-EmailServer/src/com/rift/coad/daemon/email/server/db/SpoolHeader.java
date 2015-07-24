/*
 * Email Server: The email server.
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
 * SpoolHeader.java
 */

package com.rift.coad.daemon.email.server.db;

/**
 * This object represents a spool header object.
 *
 * @author brett chaldecott
 */
public class SpoolHeader {
    
    private Integer id = 0;
    private SpoolMessage message = null;
    private String key = null;
    private String value = null;
    
    /** 
     * Creates a new instance of SpoolHeader
     */
    public SpoolHeader() {
    }
    
    /** 
     * Creates a new instance of SpoolHeader
     *
     * @param message The foreign key reference to the message.
     * @param key The key that is contained within this header.
     * @param value The value for this entry.
     */
    public SpoolHeader(SpoolMessage message, String key, String value) {
        this.message = message;
        this.key = key;
        this.value = value;
    }
    
    
    /** 
     * Creates a new instance of SpoolHeader
     *
     * @param id The id of this message.
     * @param message The foreign key reference to the message.
     * @param key The key that is contained within this header.
     * @param value The value for this entry.
     */
    public SpoolHeader(Integer id, SpoolMessage message, String key, 
            String value) {
        this.id = id;
        this.message = message;
        this.key = key;
        this.value = value;
    }
    
    
    /**
     * This method returns the id of the spool header.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of the header.
     *
     * @param id The id of the header.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method retrieves the message for this header.
     *
     * @return The message reference for this header.
     */
    public SpoolMessage getMessage() {
        return message;
    }
    
    
    /**
     * This method retrieves the message for this header.
     *
     * @return The message reference for this header.
     */
    public void setMessage(SpoolMessage message) {
        this.message = message;
    }
    
    
    /**
     * This method gets the key for this entry.
     *
     * @return The string containing the key value for this entry.
     */
    public String getKey () {
        return key;
    }
    
    
    /**
     * This method sets the key value for this object.
     *
     * @param key The key value foor the spool header.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    
    /**
     * This method retrieves the key value.
     *
     * @return The string containing the key value.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method is responsible for setting the value of the header.
     *
     * @param value The new value of the header.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
