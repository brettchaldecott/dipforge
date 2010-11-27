/*
 * Email Server: The email server interface
 * Copyright (C) 2008  Rift IT Contracting
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
 * Header.java
 */

package com.rift.coad.daemon.email.smtp;

/**
 * This object represents a header request.
 *
 * @author brett chaldecott
 */
public class Header implements java.io.Serializable {
    
    // private member variables
    private String key = null;
    private String value = null;
    
    /**
     * Creates a new instance of Header
     */
    public Header() {
    }
    
    
    /**
     * This is the constructor of the header.
     *
     * @param key The key for the header.
     * @param value The value for the header.
     */
    public Header(String key, String value) {
        this.key = key;
        this.value = value.trim();
    }
    
    
    /**
     * This method returns the key.
     *
     * @return The string containing the key.
     */
    public String getKey() {
        return key;
    }
    
    
    /**
     * This method sets the key for the header.
     *
     * @param key The string containing the key for the header.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    
    /**
     * This method returns the value of the header.
     *
     * @return The string containing the value of the header.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value of the header.
     *
     * @param value The value of the header.
     */
    public void setValue(String value) {
        this.value = value.trim();
    }
    
    
    /**
     * This method returns the string value of this header.
     */
    public String toString() {
        return key + ": " + value;
    }
}
