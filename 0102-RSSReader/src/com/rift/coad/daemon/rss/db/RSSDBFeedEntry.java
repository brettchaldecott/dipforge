/*
 * RSSReader: The RSS reader server
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
 * RSSDBFeed.java
 */

// package path
package com.rift.coad.daemon.rss.db;

/**
 *
 * @author brett chaldecott
 */
public class RSSDBFeedEntry {
    
    // private member variables
    private Integer id;
    private RSSDBFeed dbFeed;
    private String subject;
    
    
    /**
     * The default constructor of the RSS DB Feed entry.
     */
    public RSSDBFeedEntry() {
    }
    
    
    /**
     * The constructor that sets up the assigned values.
     * @param dbFeed The reference to the parent table
     * @param subject
     */
    public RSSDBFeedEntry(RSSDBFeed dbFeed, String subject) {
        this.dbFeed = dbFeed;
        this.subject = subject;
    }
    
    
    /**
     * The constructor that initializes all values. 
     * @param id The id of this feed
     * @param dbFeed
     * @param subject
     */
    public RSSDBFeedEntry(Integer id, RSSDBFeed dbFeed, String subject) {
        this.id = id;
        this.dbFeed = dbFeed;
        this.subject = subject;
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
     * Get the value of dbFeed
     *
     * @return the value of dbFeed
     */
    public RSSDBFeed getDbFeed() {
        return dbFeed;
    }

    /**
     * Set the value of dbFeed
     *
     * @param dbFeed new value of dbFeed
     */
    public void setDbFeed(RSSDBFeed dbFeed) {
        this.dbFeed = dbFeed;
    }
    
    
    /**
     * Get the value of subject
     *
     * @return the value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the value of subject
     *
     * @param subject new value of subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    
}
