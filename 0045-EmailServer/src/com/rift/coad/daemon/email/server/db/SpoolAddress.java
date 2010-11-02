/*
 * Email Server: The email server.
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
 * SpoolHeader.java
 */

// package path
package com.rift.coad.daemon.email.server.db;


/**
 * This object represents a spool address.
 *
 * @author brett chaldecott
 */
public class SpoolAddress {
    
    private Integer id = 0;
    private SpoolMessage message = null;
    private String value = null;
    
    /** 
     * Creates a new instance of SpoolAddress 
     */
    public SpoolAddress() {
    }
    
    
    /** 
     * Creates a new instance of SpoolAddress.
     *
     * @param message The foreign key reference to the message.
     * @param value The new value of this message.
     */
    public SpoolAddress(SpoolMessage message, String value) {
        this.message = message;
        this.value = value;
    }
    
    
    /** 
     * Creates a new instance of SpoolAddress.
     *
     * @param id The id of this value.
     * @param message The foreign key reference to the message.
     * @param value The new value of this message.
     */
    public SpoolAddress(Integer id, SpoolMessage message, String value) {
        this.id = id;
        this.message = message;
        this.value = value;
    }
    
    
    /**
     * This method returns the id of this address.
     *
     * @return An int containing the integer value of this id.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method returns the id of this address.
     *
     * @param id The id of this address.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the message reference.
     *
     * @return The object containing the reference to the foreign key message.
     */
    public SpoolMessage getMessage() {
        return message;
    }
    
    
    /**
     * This method sets the message reference.
     *
     * @param message The object containing the reference to the foreign key 
     * message.
     */
    public void setMessage(SpoolMessage message) {
        this.message = message;
    }
    
    
    /**
     * This method returns the value of the soap spool address.
     *
     * @return The string containing the value of the soap address.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method returns the value of the soap spool address.
     *
     * @return The string containing the value of the soap address.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
