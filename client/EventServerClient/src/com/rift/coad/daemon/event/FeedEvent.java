/*
 * EventServerClient: The event server client libraries.
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
 * FeedEvent.java
 */


// package path
package com.rift.coad.daemon.event;

// The serializable object
import java.io.Serializable;


/**
 * This object represents a single feed event entry.
 * 
 * @author brett chaldecott
 */
public class FeedEvent implements Serializable{
    
    // private member variables
    private String name;
    private String externalId;
    private String username;
    private String application;
    private String description;
    private String url;
    private String role;

    
    
    /**
     * The default constructor.
     */
    public FeedEvent() {
    }

    /**
     * This constructor sets all the properties of this object.
     *  
     * @param name The name of this event
     * @param externalId The external identifier for this event.
     * @param username The name of the user.
     * @param application The appliation that this event is attached to.
     * @param description The description of this event.
     * @param url The url of the event.
     * @param role The user role.
     */
    public FeedEvent(String name, String externalId, String username, String application, 
            String description, String url, String role) {
        this.name = name;
        this.externalId = externalId;
        this.username = username;
        this.application = application;
        this.description = description;
        this.url = url;
        this.role = role;
    }
    
    
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Get the value of externalId
     *
     * @return the value of externalId
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Set the value of externalId
     *
     * @param externalId new value of externalId
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    
    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /**
     * Get the value of application
     *
     * @return the value of application
     */
    public String getApplication() {
        return application;
    }
    
    
    /**
     * Set the value of application
     *
     * @param application new value of application
     */
    public void setApplication(String application) {
        this.application = application;
    }

    
    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public String getUrl() {
        return url;
    }
    
    
    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    /**
     * Get the value of role
     *
     * @return the value of role
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the value of role
     *
     * @param role new value of role
     */
    public void setRole(String role) {
        this.role = role;
    }

}
