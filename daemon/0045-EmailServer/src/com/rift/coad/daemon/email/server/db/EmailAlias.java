/*
 * Email Server: The email server interface
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
 * EmailAlias.java
 */

// the package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Set;
import java.util.HashSet;



/**
 * The email alias.
 *
 * @author brett chaldecott
 */
public class EmailAlias {
    
    // private member variables
    private Integer id = 0;
    private Emailbox emailbox = null;
    private String address = null;
    
    /**
     * Creates a new instance of EmailAlias
     */
    public EmailAlias() {
    }
    
    
    /**
     * Creates a new instance of EmailAlias
     *
     * @param emailbox The reference to the email box this alias is tied to.
     * @param address The address for this alias.
     */
    public EmailAlias(Emailbox emailbox, String address) {
        this.emailbox = emailbox;
        this.address = address;
    }
    
    
    /**
     * Creates a new instance of EmailAlias
     *
     * @param id The id of the email alias.
     * @param emailbox The reference to the email box this alias is tied to.
     * @param address The address for this alias.
     */
    public EmailAlias(Integer id, Emailbox emailbox, String address){
        this.id = id;
        this.emailbox = emailbox;
        this.address = address;
    }
    
    
    /**
     * This method returns the id of the alias.
     *
     * @return The id of the entry.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of the alias.
     *
     * @param id The id of the entry.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the email box this alias is attached to.
     *
     * @return The id of the entry.
     */
    public Emailbox getEmailbox() {
        return emailbox;
    }
    
    
    /**
     * This method sets the the email box this alias is attache to.
     *
     * @param emailbox The email box this alias is attached to.
     */
    public void setEmailbox(Emailbox emailbox) {
        this.emailbox = emailbox;
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
    
    
}
