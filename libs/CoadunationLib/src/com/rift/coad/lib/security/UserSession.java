/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * UserSession.java
 *
 * The user object identifying a user to the security system.
 */

// the package
package com.rift.coad.lib.security;

// java imports
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

// coadunation imports
import com.rift.coad.lib.common.RandomGuid;

/**
 * The user object identifying a user to the security system.
 *
 * @author Brett Chaldecott
 */
public class UserSession implements PrincipalContainer, Cloneable {
    
    // classes member variables
    private String name = null;
    private String sessionId = null;
    private Set principals = null;
    private Date touchTime = new Date();
    private long expiryTime = 0;
    private boolean valid = true;
    
    /**
     * The default constructor responsible for creating the default nobody user.
     *
     * @exception SecurityException
     */
    public UserSession() throws SecurityException {
        try {
            name = "nobody";
            sessionId = RandomGuid.getInstance().getGuid();
            this.principals = new HashSet();
        } catch (Exception ex) {
            throw new SecurityException(
                    "Failed to initialize the users session : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Creates a new instance of UserSession
     */
    public UserSession(String name, Set principals) throws SecurityException  {
        try {
            this.name = name;
            sessionId = RandomGuid.getInstance().getGuid();
            this.principals = principals;
        } catch (Exception ex) {
            throw new SecurityException(
                    "Failed to initialize the users session : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Creates a new instance of UserSession
     *
     * @param name The name associated with the new user session.
     * @param sessionId The id of the session.
     * @param principals The principals of this user.
     */
    public UserSession(String name, String sessionId, Set principals) {
        this.name = name;
        this.sessionId = sessionId;
        this.principals = principals;
    }
    
    
    /**
     * The getter method for the name of this role.
     *
     * @return The string containing the name of this role.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns the id of the users session id.
     *
     * @return The id of the users session.
     */
    public String getSessionId() {
        return sessionId;
    }
    
    
    /**
     * This method returns the id of the users session id.
     *
     * @return The id of the users session.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
    /**
     * This method returns the list of principals.
     *
     * @return The list of principals.
     */
    public Set getPrincipals() {
        return principals;
    }
    
    
    /**
     * This method set the list of principals.
     *
     * @param The list of principals.
     */
    public void setPrincipals(Set principals) {
        this.principals = principals;
    }
    
    
    /**
     * This method returns the touch time of the user session.
     *
     * @return The last time this object was touched.
     */
    public synchronized Date getTouchTime() {
        return touchTime;
    }
    
    
    /**
     * This method results in the users session being touched.
     *
     * @exception SecurityException
     */
    public synchronized void touch() throws SecurityException {
        if (isExpired() || valid == false) {
            throw new SecurityException(
                    "The object has expired or been invalidated.");
        }
        touchTime = new Date();
    }
    
    
    /**
     * The getter for the expiry time value.
     *
     * @return the expiry time of this object.
     */
    public synchronized long getExpiryTime() {
        return expiryTime;
    }
    
    
    /**
     * The setter for the expiry time value.
     *
     * @param expiryTime The new expiry time.
     */
    public synchronized void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
    
    
    /**
     * This method returns true if this object has expired in memory.
     *
     * @return TRUE if expired FALSE if not.
     */
    public synchronized boolean isExpired() {
        if (!valid) {
            return true;
        }
        else if (expiryTime == 0) {
            return false;
        }
        else if (new Date().getTime() > (touchTime.getTime() + expiryTime)) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method will mark this session as invalid.
     */
    public synchronized void invalidate() {
        valid = false;
    }
    
    
    /**
     * This method returns a clone of the original user object.
     *
     * @return A clone of the orinal object.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
