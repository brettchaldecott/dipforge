/*
 * EventServerClient: The event server client libraries.
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
 * DBFeedEventMeta.java
 */

// package path
package com.rift.coad.daemon.event.db;

/**
 * This object is responsible for storing the meta data.
 * 
 * @author brett chaldecott
 */
public class DBFeedEventMeta {
    
    private Integer id;
    private String metaType;
    private String metaValue;
    private DBFeedEvent feedEvent;
    
    /**
     * The default constructor of the db feed event meta data.
     */
    public DBFeedEventMeta() {
        
    }
    
    /**
     * This constructor sets all the values.
     * 
     * @param metaType The meta type information.
     * @param metaValue The meta value information.
     * @param feedEvent The feed event.
     */
    public DBFeedEventMeta(String metaType, String metaValue, DBFeedEvent feedEvent) {
        this.metaType = metaType;
        this.metaValue = metaValue;
        this.feedEvent = feedEvent;
    }
    
    
    /**
     * This constructor sets all the values.
     * 
     * @param id The id of the event.
     * @param metaType The meta type information.
     * @param metaValue The meta value information.
     * @param feedEvent The feed event.
     */
    public DBFeedEventMeta(Integer id, String metaType, String metaValue, DBFeedEvent feedEvent) {
        this.id = id;
        this.metaType = metaType;
        this.metaValue = metaValue;
        this.feedEvent = feedEvent;
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
     * Get the value of feedEvent
     *
     * @return the value of feedEvent
     */
    public DBFeedEvent getFeedEvent() {
        return feedEvent;
    }

    /**
     * Set the value of feedEvent
     *
     * @param feedEvent new value of feedEvent
     */
    public void setFeedEvent(DBFeedEvent feedEvent) {
        this.feedEvent = feedEvent;
    }

    
    
    /**
     * Get the value of metaType
     *
     * @return the value of metaType
     */
    public String getMetaType() {
        return metaType;
    }

    /**
     * Set the value of metaType
     *
     * @param metaType new value of metaType
     */
    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }
    

    /**
     * Get the value of metaValue
     *
     * @return the value of metaValue
     */
    public String getMetaValue() {
        return metaValue;
    }

    /**
     * Set the value of metaValue
     *
     * @param metaValue new value of metaValue
     */
    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }
    
    
     
}
