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
 * Emailbox.java
 */

// package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Set;
import java.util.HashSet;


/**
 * This object represents and email box database entry.
 *
 * @author brett chaldecott
 */
public class Emailbox {
    
    // private member variables
    private Integer id = 0;
    private String address = null;
    private String password = null;
    private EmailDomain domain = null;
    private String path = null;
    private Long quota = null;
    private Set<EmailAlias> emailAliases = new HashSet<EmailAlias>(0);
    
    
    /** 
     * Creates a new instance of EmailAddress 
     */
    public Emailbox() {
    }
    
    
    /** 
     * Creates a new instance of EmailAddress 
     *
     * @param address The address for the object.
     * @param password The password to store.
     * @param domain The domain id of the object.
     * @param path The path for the object.
     */
    public Emailbox(String address, String password, EmailDomain domain, 
            String path) {
        this.address = address;
        this.password = password;
        this.domain = domain;
        this.path = path;
    }
    
    
    /** 
     * Creates a new instance of EmailAddress 
     *
     * @param address The address for the object.
     * @param password The password to store.
     * @param domain The domain of the object.
     * @param path The path for the object.
     * @param quota The quota for the given path.
     */
    public Emailbox(String address, String password, EmailDomain domain, 
            String path, Long quota) {
        this.address = address;
        this.password = password;
        this.domain = domain;
        this.path = path;
        this.quota = quota;
    }
    
    
    /** 
     * Creates a new instance of EmailAddress 
     *
     * @param id The id of this address.
     * @param address The address for the object.
     * @param password The password to store.
     * @param domain The domain of the object.
     * @param path The path for the object.
     * @param quota The quota for the given path.
     */
    public Emailbox(Integer id, String address, String password, 
            EmailDomain domain, String path, Long quota, 
            Set<EmailAlias> emailAliases) {
        this.id = id;
        this.address = address;
        this.password = password;
        this.domain = domain;
        this.path = path;
        this.quota = quota;
        this.emailAliases = emailAliases;
    }
    
    
    /**
     * This method returns the id of the email address
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of the email address.
     *
     * @param id The id of the email address.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the address of this object.
     *
     * @return The string containing the address.
     */
    public String getAddress() {
        return address;
    }
    
    
    /**
     * This method sets the address of this object.
     *
     * @param address The new string address.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    /**
     * This method returns the password of this object.
     *
     * @return The string containing the password.
     */
    public String getPassword() {
        return password;
    }
    
    
    /**
     * This method sets the password for this object.
     *
     * @param password The neew password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * This method returns the id of the domain.
     *
     * @return The integer containing the id of the domain.
     */
    public EmailDomain getDomain() {
        return domain;
    }
    
    
    /**
     * This method sets the domain reference
     *
     * @param domain The id of the domain.
     */
    public void setDomain(EmailDomain domain) {
        this.domain = domain;
    }
    
    
    /**
     * This method returns the string containing the path.
     *
     * @return The string containing the path.
     */
    public String getPath() {
        return path;
    }
    
    
    /**
     * This method sets the string containing the path.
     *
     * @param path The new path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
    /**
     * This method returns the quota value for this address.
     *
     * @return The long containing the quota value for this address.
     */
    public Long getQuota() {
        return quota;
    }
    
    
    /**
     * This method sets the quota value for this address.
     *
     * @param quota This method sets the quota for this object.
     */
    public void setQuota(Long quota) {
        this.quota = quota;
    }
    
    
    /**
     * This method returns the aliases for a mailbox.
     *
     * @return The list of aliases for a mailbox.
     */
    public Set<EmailAlias> getEmailAliases() {
        return emailAliases;
    }
    
    
    /**
     * This method sets the email aliases for a mail box.
     *
     * @param emailAliases The list of email aliases to set.
     */
    public void setEmailAliases(Set<EmailAlias> emailAliases) {
        this.emailAliases = emailAliases;
    }
}
