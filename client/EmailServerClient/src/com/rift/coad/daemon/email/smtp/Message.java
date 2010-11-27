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
 * Message.java
 */

// package path
package com.rift.coad.daemon.email.smtp;

// java imports
import com.rift.coad.daemon.email.types.Address;
import java.util.List;
import java.util.ArrayList;

/**
 * This object represents an SMTP message.
 *
 * @author brett chaldecott
 */
public class Message implements java.io.Serializable {
    
    // class constants
    public final static int UNKNOWN = 0;
    public final static int LOCAL = 1;
    public final static int REMOTE = 2;
    
    
    // private member variables
    private String id = null;
    private int type = 0;
    private List from = new ArrayList();
    private List rcpts = new ArrayList();
    private List headers = new ArrayList();
    private String data = null;
    
    
    /** 
     * Creates a new instance of Message 
     */
    public Message() {
    }
    
    
    /** 
     * Creates a new instance of Message 
     *
     * @param from The from address.
     * @param rcpt The list of to addresses.
     * @param headers The list of headers.
     * @param data The data block for the message
     */
    public Message(List from, List rcpts, List headers, String data) {
        this.from = from;
        this.rcpts = rcpts;
        this.headers = headers;
        this.data = data;
    }
    
    
    /** 
     * Creates a new instance of Message 
     *
     * @param id The id for this message.
     * @param type The type of message.
     * @param from The from address.
     * @param rcpt The list of to addresses.
     * @param headers The list of headers.
     * @param data The data block for the message
     */
    public Message(String id, int type, List from, List rcpts, List headers,
            String data) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.rcpts = rcpts;
        this.headers = headers;
        this.data = data;
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
     * This is the getter for the headers.
     * 
     * @return The list of headers.
     */
    public List getHeaders() {
        return headers;
    }
    
    
    /**
     * This method sets the headers for the message.
     *
     * @param headers The headers for the message.
     */
    public void setHeaders(List headers) {
        this.headers = headers;
    }
    
    
    /**
     * This method addes a new header
     *
     * @param header this method adds a header.
     */
    public void addHeader(Header header) {
        this.headers.add(header);
    }
    
    
    /**
     * This method returns true if the specified header is set.
     */
    public boolean hasHeader(String key) {
        for (int index = 0; index < headers.size(); index++) {
            Header header = (Header)headers.get(index);
            if (header.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method returns the data for this message.
     *
     * @return The string containing the data for this message.
     */
    public String getData() {
        return data;
    }
    
    
    /**
     * This method sets the data for the message.
     *
     * @param data The data for the SMTP message.
     */
    public void setData(String data) {
        this.data = data;
    }
    
    
    /**
     * This method returns the String value of this message
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(from.toString());
        buffer.append(rcpts.toString());
        buffer.append(headers.toString());
        buffer.append(data);
        return buffer.toString();
    }
    
    
    /**
     * This object returns the message information.
     */
    public MessageInfo getInfo() {
        MessageInfo info = new MessageInfo(id,type,
                MessageInfo.STATUS.UNPROCESSED,from,rcpts,headers);
        return info;
    }
    
    
    /**
     * This object returns the message information.
     */
    public void updateInfo(MessageInfo info) {
        from = info.getFrom();
        rcpts = info.getRCPTs();
        headers = info.getHeaders();
        type = info.getType();
    }
}
