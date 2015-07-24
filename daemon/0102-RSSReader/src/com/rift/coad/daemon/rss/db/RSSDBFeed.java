/*
 * RSSReader: The RSS reader server
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
 * RSSDBFeed.java
 */

// the package path
package com.rift.coad.daemon.rss.db;

// the io serializable path
import java.io.Serializable;
import java.util.Set;

/**
 * This object represents a feed entry.
 * 
 * @author brett chaldecott
 */
public class RSSDBFeed implements Serializable {
    
    // priate member variables
    private Integer id;
    private String name;
    private String application;
    private String url;
    private Set<RSSDBFeedEntry> feedEntries;

    
    
    /**
     * The default constructor.
     */
    public RSSDBFeed() {
    }
    
    
    /**
     * The default constructor  of the feed event
     * @param name The name of the feed.
     * @param application The application the feed is attached to.
     * @param url The url of the feed.
     */
    public RSSDBFeed(String name, String application, String url) {
        this.name = name;
        this.application = application;
        this.url = url;
    }
    
    
    /**
     * This constructor is responsible for setting all the values.
     * 
     * @param id The id of the database entry.
     * @param name The name of the database entry.
     * @param application The application.
     * @param url The url for the RSS feed.
     */
    public RSSDBFeed(Integer id, String name, String application, String url) {
        this.id = id;
        this.name = name;
        this.application = application;
        this.url = url;
    }
    
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Get the value of feedEntries
     *
     * @return the value of feedEntries
     */
    public Set<RSSDBFeedEntry> getFeedEntries() {
        return feedEntries;
    }

    /**
     * Set the value of feedEntries
     *
     * @param feedEntries new value of feedEntries
     */
    public void setFeedEntries(Set<RSSDBFeedEntry> feedEntries) {
        this.feedEntries = feedEntries;
    }

}
