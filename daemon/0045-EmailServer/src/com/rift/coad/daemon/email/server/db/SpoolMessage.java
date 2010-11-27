/*
 * Email Server: The email server.
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
 * Server.java
 */

// package path
package com.rift.coad.daemon.email.server.db;

// java imports
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

/**
 * This database object represents a spool object.
 *
 * @author brett chaldecott
 */
public class SpoolMessage {
    
    private String id = null;
    private Integer type = 0;
    private Set<SpoolAddress> from = new HashSet<SpoolAddress>(0);
    private String rcpt = null;
    private Set<SpoolHeader> headers = new HashSet<SpoolHeader>(0);
    private String dataPath = null;
    private java.sql.Timestamp retryDate = null;
    private Integer retries = 0;
    private String routeName = null;
    
    
    /**
     * Creates a new instance of SpoolMessage
     */
    public SpoolMessage() {
        
    }
    
    
    /**
     * Creates a new instance of SpoolMessage
     *
     * @param id The id of this entry.
     * @param type The type that identifies this object.
     * @param rcpt The recipient of this message.
     * @param dataPath The path to the data for this object.
     * @param retryDate The last retry date.
     * @param retries The number of retries for this message.
     * @param routeName The route name This message is attached to.
     */
    public SpoolMessage(String id, Integer type, String rcpt, String dataPath, 
            java.sql.Timestamp retryDate, Integer retries, String routeName) {
        this.id = id;
        this.type = type;
        this.rcpt = rcpt;
        this.dataPath = dataPath;
        this.retryDate = retryDate;
        this.retries = retries;
        this.routeName = routeName;
    }
    
    
    /**
     * Creates a new instance of SpoolMessage
     *
     * @param id The id of this entry.
     * @param type The type that identifies this object.
     * @param from The address the message came from.
     * @param rcpt The recipient of this message.
     * @param headers The headers the message was receivied from.
     * @param dataPath The path to the data for this object.
     * @param retryDate The last retry date.
     * @param retries The number of retries for this message.
     * @param routeName The route name This message is attached to.
     */
    public SpoolMessage(String id, Integer type, Set<SpoolAddress> from, 
            String rcpt, Set<SpoolHeader> headers, String dataPath, 
            java.sql.Timestamp retryDate, Integer retries, String routeName) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.rcpt = rcpt;
        this.headers = headers;
        this.dataPath = dataPath;
        this.retryDate = retryDate;
        this.retries = retries;
        this.routeName = routeName;
    }
    
    
    /**
     * This method returns id of this message.
     *
     * @return The id of this message.
     */
    public String getId () {
        return id;
    }
    
    
    /**
     * This method returns id of this message.
     *
     * @return The id of this message.
     */
    public void setId (String id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the type of this object
     *
     * @return The type of this message.
     */
    public Integer getType() {
        return type;
    }
    
    
    /**
     * This method sets the type of message.
     *
     * @param type The integer value containing the type of this message.
     */
    public void setType(Integer type) {
        this.type = type;
    }
    
    
    /**
     * This method gets the from address list from this object.
     *
     * @return The set containing the list of address.
     */
    public Set<SpoolAddress> getFrom() {
        return from;
    }
    
    
    /**
     * This method sets the from address list from this object.
     *
     * @return The set containing the list of address.
     */
    public void setFrom(Set<SpoolAddress> from) {
        this.from = from;
    }
    
    
    /**
     * This method retrieves the rcpt.
     *
     * @return The string containing the recipient.
     */
    public String getRcpt() {
        return rcpt;
    }
    
    
    /**
     * This method sets the rcpt
     *
     * @param rcpt The recipient.
     */
    public void setRcpt(String rcpt) {
        this.rcpt = rcpt;
    }
    
    
    /**
     * This method returns the headers.
     *
     * @return The header list.
     */
    public Set<SpoolHeader> getHeaders() {
        return headers;
    }
    
    
    /**
     * This method sets the headers.
     *
     * @headers The new headers.
     */
    public void setHeaders(Set<SpoolHeader> headers) {
        this.headers = headers;
    }
    
    
    /**
     * This method returns the data path
     *
     * @return The string containing the data information.
     */
    public String getDataPath() {
        return dataPath;
    }
    
    
    /**
     * This method sets the data.
     */
    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }
    
    
    /**
     * This method returns the last retry date.
     *
     * @return This method returns the last retry date.
     */
    public java.sql.Timestamp getRetryDate() {
        return retryDate;
    }
    
    
    /**
     * This method returns the last retry date.
     *
     * @return This method returns the last retry date.
     */
    public void setRetryDate(java.sql.Timestamp retryDate) {
        this.retryDate = retryDate;
    }
    
    
    /**
     * This method returns the numbe of retries.
     *
     * @return The number for retries processed on the current route entry.
     */
    public Integer getRetries() {
        return retries;
    }
    
    
    /**
     * This method returns the numbe of retries.
     *
     * @return The number for retries processed on the current route entry.
     */
    public void setRetries(Integer retries) {
        this.retries = retries;
    }
    
    
    /**
     * This method gets the current route name.
     *
     * @return The current route name.
     */
    public String getRouteName() {
        return routeName;
    }
    
    
    /**
     * This method sets the route name for this entry.
     *
     * @param routeName The route name for this entry.
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
