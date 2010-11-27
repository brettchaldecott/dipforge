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
 * EmailAliasEntry.java
 */

// package paht
package com.rift.coad.daemon.email.server.db;

/**
 * The definition of an email entry entry.
 *
 * @author brett chaldecott
 */
public class EmailForwardEntry {
    
    // private member variables.
    private Integer id = null;
    private EmailForward forward = null;
    private String address = null;
    
    
    /** 
     * Creates a new instance of EmailAliasEntry
     */
    public EmailForwardEntry() {
    }
    
    
    /** 
     * Creates a new instance of EmailAliasEntry
     *
     * @param forward The forward this entry is attached to.
     * @param address The address for this alias.
     */
    public EmailForwardEntry(EmailForward forward, String address) {
        this.forward = forward;
        this.address = address;
    }
    
    
    /** 
     * Creates a new instance of EmailAliasEntry
     *
     * @param id The id of the entry.
     * @param forward The forward this entry is attached to.
     * @param address The address for this alias.
     */
    public EmailForwardEntry(Integer id, EmailForward forward, String address) {
        this.id = id;
        this.forward = forward;
        this.address = address;
    }
    
    
    /**
     * This method returns the id of the alias entry.
     *
     * @return The integer value for the id.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of the alias entry.
     *
     * @param id The integer value for the id.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the forward that this entry is attached to.
     *
     * @return This method returns the email forward.
     */
    public EmailForward getForward() {
        return forward;
    }
    
    
    /**
     * This method sets the forward that this entry is attached to.
     *
     * @param alias The forward reference for this object.
     */
    public void setForward(EmailForward forward) {
        this.forward = forward;
    }
    
    
    /**
     * This method returns the address of this entry.
     *
     * @return The string containing the address.
     */
    public String getAddress() {
        return address;
    }
    
    
    /**
     * This method sets the address of this entry.
     *
     * @param address The string containing the address.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
