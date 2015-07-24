/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007  2015 Burntjam
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
 * AuditTrail.java
 */

// the package path
package com.rift.coad.lib.audit;

// coadunation imports 
import com.rift.coad.lib.security.SessionManager;

/**
 * This object is responsible for processing the audit trail requests.
 *
 * @author brett chaldecott
 */
public class AuditTrail {
    
    // private member variable
    private Class target = null;
    
    /**
     * Creates a new instance of AuditTrail
     *
     * @param target The target of this audit trail.
     */
    private AuditTrail(Class target) {
        this.target = target;
    }
    
    
    /**
     * This method constructs a new audit trail entry.
     *
     * @return The reference to the new audit trail.
     * @param target The target of the audit trail.
     */
    public static AuditTrail getAudit(Class target) {
        return new AuditTrail(target);
    }
    
    
    /**
     * This method logs a succesfull audit trail entry.
     *
     * @param method The name of the method to log the event for.
     */
    public void logEvent(String method) {
        AuditTrailManager.getInstance().addEntry(new AuditTrailEntry(getUsername(),
                target.getName(),method));
    }
    
    
    /**
     * This method logs an un-succesfull audit trail entry.
     *
     * @param method The name of the method to log the event for.
     * @param ex The exception that cause the error on the event.
     */
    public void logEvent(String method, Throwable ex) {
        AuditTrailManager.getInstance().addEntry(new AuditTrailEntry(getUsername(),
                target.getName(),method,ex));
    }
    
    
    /**
     * This method returns the user name of the current thread.
     *
     * @return The user for the current session or Unknown if not known.
     */
    private String getUsername() {
        try {
            // return the user for the current session.
            return SessionManager.getInstance().getSession().getUser().getName();
        } catch (Exception ex) {
            return "Unknown";
        }
    }
}
