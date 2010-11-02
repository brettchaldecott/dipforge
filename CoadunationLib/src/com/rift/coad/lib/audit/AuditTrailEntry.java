/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  Rift IT Contracting
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
 * AuditTrailEntry.java
 */

// package path
package com.rift.coad.lib.audit;

// imports
import java.util.Date;

/**
 * This object responsible for storing the information pertinant to a
 * single audit trail event.
 *
 * @author brett chaldecott
 */
public class AuditTrailEntry implements java.io.Serializable {
    
    // private member variables
    private Date recordTime = new Date();
    private String user = null;
    private String target = null;
    private String method = null;
    private Throwable cause = null;
    
    
    /**
     * Creates a new instance of AuditTrailEntry
     *
     * @param user The user of the entry.
     * @param target The target of the entry.
     * @param method The begin called.
     */
    public AuditTrailEntry(String user, String target, String method) {
        this.user = user;
        this.target = target;
        this.method = method;
    }
    
    
    /**
     * Creates a new instance of AuditTrailEntry
     *
     * @param user The user of the entry.
     * @param target The target of the entry.
     * @param method The begin called.
     * @param cause The cause of the error.
     */
    public AuditTrailEntry(String user, String target, String method,
            Throwable cause) {
        this.user = user;
        this.target = target;
        this.method = method;
        this.cause = cause;
    }
    
    
    /**
     * This method returns the record time.
     *
     * @return The record time for the audit trail entry.
     */
    public Date getRecordTime() {
        return recordTime;
    }
    
    
    /**
     * This method returns the user name.
     */
    public String getUser() {
        return user;
    }
    
    
    /**
     * This method returns the target object of the audit trail entry.
     *
     * @return The string containing the target information.
     */
    public String getTarget() {
        return target;
    }
    
    
    /**
     * This method returns the string name method information.
     *
     * @return The string containing the method information.
     */
    public String getMethod() {
        return method;
    }
    
    
    /**
     * This method returns the cause of the audit trail error.
     *
     * @return The cause of the audit trail entry.
     */
    public Throwable getCause() {
        return cause;
    }
}
