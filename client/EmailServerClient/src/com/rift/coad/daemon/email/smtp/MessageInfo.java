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
 * MessageInfo.java
 */

// package path
package com.rift.coad.daemon.email.smtp;

// java imports
import com.rift.coad.daemon.email.types.Address;
import java.util.List;
import java.util.ArrayList;

/**
 * This class represents the message information.
 *
 * @author brett chaldecott
 */
public class MessageInfo implements java.io.Serializable {
    
    public enum STATUS {
        DELIVERED, // The message has been successfully delivered
        UNPROCESSED, // The message has to be processed
        PROCESSED // The message has been processed
    };
    
    // private member variables
    private String id = null;
    private int type = 0;
    private STATUS status = MessageInfo.STATUS.UNPROCESSED;
    private List from = new ArrayList();
    private List rcpts = new ArrayList();
    private List headers = new ArrayList();
    
    /**
     * Creates a new instance of MessageInfo
     */
    public MessageInfo() {
    }
    
    
    /** 
     * Creates a new instance of Message Information object.
     *
     * @param id The id of this message.
     * @param status The status of the message.
     * @param type The type of this message.
     * @param from The from address.
     * @param rcpt The list of to addresses.
     * @param headers The list of headers.
     */
    public MessageInfo(String id, int type, STATUS status, List from,
            List rcpts, List headers) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.from = from;
        this.rcpts = rcpts;
        this.headers = headers;
    }
    
    
    /**
     * This method returns the id of this message.
     *
     * @param id The id of this message.
     */
    public String getId() {
        return id;
    }
    
    
    /**
     * This method sets the id of this message.
     *
     * @param id The id of this message.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the type.
     *
     * @return The type of message.
     */
    public int getType() {
        return type;
    }
    
    
    /**
     * This method sets the type of this message.
     *
     * @param type The type of this message.
     */
    public void setType(int type) {
        this.type = type;
    }
    
    
    /**
     * This method returns the status.
     *
     * @return The status of the message
     */
    public STATUS getStatus() {
        return status;
    }
    
    
    /**
     * This method sets the status of this message.
     *
     * @param status The status of the message.
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }
    
    
    /**
     * This method returns the from address.
     *
     * @return The list of from addresses.
     */
    public List getFrom() {
        return from;
    }
    
    
    /**
     * This method sets the from address.
     *
     * @param from The list of from addresses.
     */
    public void setFrom(List from) {
        this.from = from;
    }
    
    
    /**
     * This method adds a new from address.
     */
    public void addFrom(Address from) {
        this.from.add(from);
    }
    
    
    /**
     * This method returns the list of recipients.
     *
     * @return The list of recipients
     */
    public List getRCPTs() {
        return rcpts;
    }
    
    
    /**
     * This method sets the recipients.
     *
     * @param rcpt The new list of receipents.
     */
    public void setRCPTs(List rcpts) {
        this.rcpts = rcpts;
    }
    
    
    /**
     * This method adds a new recipient.
     *
     * @param rcpt A new recipient.
     */
    public void addRCPT(Address rcpt) {
        this.rcpts.add(rcpt);
    }
    
    
    /**
     * This method returns the list of headers
     *
     * @return The list of headers
     */
    public List getHeaders() {
        return headers;
    }
    
    
    /**
     * This method sets the headers.
     *
     * @param headers The list of headers.
     */
    public void setHeaders(List headers) {
        this.headers = headers;
    }
    
    
    /**
     * This method adds a header
     *
     * @param header The header to add.
     */
    public void addHeader(Header header) {
        this.headers.add(header);
    }
    
}
