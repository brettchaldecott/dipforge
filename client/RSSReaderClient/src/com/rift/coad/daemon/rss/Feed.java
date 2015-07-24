/*
 * RSSReaderClient: The RSS Reader.
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
 * Feed.java
 */


package com.rift.coad.daemon.rss;

import java.io.Serializable;

/**
 * This object represents a feed entry.
 * 
 * @author brett chaldecott
 */
public class Feed implements Serializable {
    
    // priate member variables
    private String name;
    private String application;
    private String url;

    
    /**
     * The default constructor.
     */
    public Feed() {
    }
    
    
    /**
     * The default constructor  of the feed event
     * @param name The name of the feed.
     * @param application The application the feed is attached to.
     * @param url The url of the feed.
     */
    public Feed(String name, String application, String url) {
        this.name = name;
        this.application = application;
        this.url = url;
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
     * This method returns a string definition for this object.
     * 
     * @return The string value for this object.
     */
     public String toString() {
         return "[name=" + this.name + ",application=" + this.application + ",url=" + this.url + "]";
     }
}
