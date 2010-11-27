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
 * EmailForward.java
 */

// the package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Set;
import java.util.HashSet;



/**
 * The email forward.
 *
 * @author brett chaldecott
 */
public class EmailForward {
    
    // private member variables
    private Integer id = 0;
    private EmailDomain domain = null;
    private String address = null;
    private Set<EmailForwardEntry> entries = new HashSet<EmailForwardEntry>(0);
    
    
    /**
     * Creates a new instance of EmailAlias
     */
    public EmailForward() {
    }
    
    
    /**
     * Creates a new instance of EmailAlias
     *
     * @param domain The domain this object is associated with.
     * @param address The address for this alias.
     */
    public EmailForward(EmailDomain domain, String address) {
        this.domain = domain;
        this.address = address;
    }
    
    
    /**
     * Creates a new instance of EmailForward
     *
     * @param id The id of the email forward.
     * @param domain The domain this object is associated with.
     * @param address The address for this forward.
     * @param entries The list of entries.
     */
    public EmailForward(Integer id, EmailDomain domain, String address, 
            Set<EmailForwardEntry> entries){
        this.id = id;
        this.domain = domain;
        this.address = address;
        this.entries = entries;
    }
    
    
    /**
     * This method returns the id of the forward.
     *
     * @return The id of the entry.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of the forward.
     *
     * @param id The id of the entry.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * The getter for the email domain.
     *
     * @return The address of the email domain.
     */
    public EmailDomain getDomain() {
        return domain;
    }
    
    
    /**
     * The setter for the domain.
     *
     * @param domain The domain value.
     */
    public void setDomain(EmailDomain domain) {
        this.domain = domain;
    }
    
    
    /**
     * The getter for the address.
     *
     * @return The address value.
     */
    public String getAddress() {
        return address;
    }
    
    
    /**
     * The setter for the address.
     *
     * @param address The address value.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    /**
     * The getter for the entries.
     *
     * @return The list of entries.
     */
    public Set<EmailForwardEntry> getEntries() {
        return entries;
    }
    
    
    /**
     * The setter for the entries.
     *
     * @param entries The list of entries.
     */
    public void setEntries(Set<EmailForwardEntry> entries) {
        this.entries = entries;
    }
}
