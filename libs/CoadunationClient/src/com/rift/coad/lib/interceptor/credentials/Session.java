/*
 * CoadunationClient: The client libraries for Coadunation. (RMI/CORBA)
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
 * Session.java
 *
 * This credential object represents and active session for a user.
 */

// package path
package com.rift.coad.lib.interceptor.credentials;

// java imports
import java.util.Set;

/**
 * This credential object represents and active session for a user.
 *
 * @author Brett Chaldecott
 */
public class Session extends Credential {
    
    // private member variables
    private String sessionId = null;
    private Set principals = null;
    
    /**
     * Creates a new instance of Session 
     */
    public Session() {
    }
    
    
    /**
     * This constructor is responsible for setting the session id and principals
     * information.
     *
     * @param username The name of the user.
     * @param sessionId The id for this session.
     * @param principals The list of principals.
     */
    public Session(String username, String sessionId, Set principals) {
        super(username);
        this.sessionId = sessionId;
        this.principals = principals;
    }
    
    
    /**
     * This method returns the id of this session.
     *
     * @return The id of this session.
     */
    public String getSessionId() {
        return sessionId;
    }
    
    
    /**
     * This method sets the session id.
     *
     * @param sessionId The id of the session to set.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
    /**
     * This method returns a list of principals.
     *
     * @return The list of principals.
     */
    public Set getPrincipals() {
        return principals;
    }
    
    
    /**
     * This method sets the principals for a session
     *
     * @param principals The set of principals to apply
     */
    public void setPrincipals(Set principals) {
        this.principals = principals;
    }
}
