/*
 * EMailServer: The email server
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
 * Relay.java
 */

// package import path
package com.rift.coad.daemon.email.server.db;


/**
 * This object is responsible for representing a relay entry.
 *
 * @author brett chaldecott
 */
public class Relay {
    
    // private member variables
    private String address = null;
    
    /** 
     * The default constructor for the relay.
     */
    public Relay() {
    }
    
    
    /** 
     * Creates a new instance of for the relay.
     *
     * @param address The address for the relay.
     */
    public Relay(String address) {
        this.address = address;
    }
    
    
    /**
     * This method creates a new address.
     *
     * @return The string containing the address information.
     */
    public String getAddress() {
        return this.address;
    }
    
    
    /**
     * This method sets the address for the reply.
     *
     * @param address The address to store the information.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
