/*
 * EMail: The email server
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
 * ServerDomain.java
 */

// package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Set;
import java.util.HashSet;


/**
 * The email domain object that will be stored in the database.
 *
 * @author brett chaldecott
 */
public class EmailDomain {
    
    // private member variable
    private Integer id = null;
    private String domain = null;
    private String dropBox = null;
    private Set<Emailbox> emailboxes = new HashSet<Emailbox>(0);
    private Set<EmailForward> emailForwards = new HashSet<EmailForward>(0);
    
    /**
     * Creates a new instance of EmailDomain
     */
    public EmailDomain() {
    }
    
    
    /**
     * This constructor sets the domain name.
     *
     * @param domain The domain that identifies this object.
     */
    public EmailDomain(String domain, String dropBox) {
        this.domain = domain;
        this.dropBox = dropBox;
    }
    
    
    /**
     * This constructor sets the domain name.
     *
     * @param id The id of the domain.
     * @param domain The domain that identifies this object.
     * @param dropBox The drop box for the email domain
     * @param emailboxes The boxes of email.
     * @param emailForwards The list of forwards for a domain.
     */
    public EmailDomain(Integer id, String domain, String dropBox, 
            Set<Emailbox> emailboxes, Set<EmailForward> emailForwards) {
        this.id = id;
        this.domain = domain;
        this.dropBox = dropBox;
        this.emailboxes = emailboxes;
        this.emailForwards = emailForwards;
    }
    
    
    /**
     * This method returns the id of the domain.
     *
     * @return The id of the domain.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * This method sets the id for the email domain.
     *
     * @param id The id of the domain.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the 
     *
     * @return The domain name.
     */
    public String getDomain() {
        return domain;
    }
    
    
    /**
     * This method sets the domain.
     *
     * @param domain The domain name.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    
    /**
     * This method returns the drop box for the domain.
     *
     * @return The drop box for the domain.
     */
    public String getDropBox() {
        return dropBox;
    }
    
    
    /**
     * This method sets the drop box for the domain.
     *
     * @param domain The domain name.
     */
    public void setDropBox(String dropBox) {
        this.dropBox = dropBox;
    }
    
    
    /**
     * This method returns the email boxes for the domain.
     *
     * @return The email boxes for the domain.
     */
    public Set<Emailbox> getEmailboxes() {
        return emailboxes;
    }
    
    
    /**
     * This method sets the email boxes for the domain.
     *
     * @param emailboxes The list of boxes for the domain.
     */
    public void setEmailboxes(Set<Emailbox> emailboxes) {
        this.emailboxes = emailboxes;
    }
    
    
    /**
     * This method returns the email forwards for a domain.
     *
     * @return The email forwards for a domain.
     */
    public Set<EmailForward> getEmailForwards() {
        return emailForwards;
    }
    
    
    /**
     * This sets the email forwards for a domain.
     *
     * @param emailForwards The new email forwards for a domain.
     */
    public void setEmailForwards(Set<EmailForward> emailForwards) {
        this.emailForwards = emailForwards;
    }

}
