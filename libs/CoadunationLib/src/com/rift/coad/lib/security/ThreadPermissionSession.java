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
 * ThreadPermissionSession.java
 *
 * This object stores the thread permissions for the current session.
 */

// package definition
package com.rift.coad.lib.security;

// the java imports
import java.util.Vector;
import java.util.HashSet;
import java.util.Set;

/**
 * This object stores the thread permissions for the current session.
 *
 * @author Brett Chaldecott
 */
public class ThreadPermissionSession {
    
    // The list of containers for which the principals are being stored.
    private Long threadId = null;
    private UserSession user = null;
    private Vector containerList = null;
    
    /** 
     * Creates a new instance of ThreadSecuritySession 
     *
     * @param threadId The id of the thread.
     * @param user The reference to the user object.
     */
    public ThreadPermissionSession(Long threadId, UserSession user) {
        this.threadId = threadId;
        this.user = user;
        containerList = new Vector();
        containerList.add(user);
    }
    
    
    /**
     * The getter method for the thread id information.
     *
     * @return The long object containing the thread id information.
     * @exception SecurityException
     */
    public Long getThreadId() throws SecurityException {
        try {
            user.touch();
        } catch (Exception ex) {
            throw new SecurityException("Failed to touch the users session : " +
                    ex.getMessage(),ex);
        }
        return threadId;
    }
    
    
    /**
     * This method retrieves the use information.
     *
     * @return The object containing the user information associated with this
     *          thread.
     * @exception SecurityException
     */
    public UserSession getUser() throws SecurityException {
        try {
            user.touch();
        } catch (Exception ex) {
            throw new SecurityException("Failed to touch the users session : " +
                    ex.getMessage(),ex);
        }
        return user;
    }
    
    
    /**
     * This method returns the list of principals.
     *
     * @return The set containing the list of principals.
     * @exception SecurityException
     */
    public Set getPrincipals() throws SecurityException {
        Set principals = new HashSet();
        for (int i = 0; i < containerList.size(); i++) {
            principals.addAll(((PrincipalContainer)containerList.get(i)).
                    getPrincipals());
        }
        try {
            user.touch();
        } catch (Exception ex) {
            throw new SecurityException("Failed to touch the users session : " +
                    ex.getMessage(),ex);
        }
        return principals;
    }
    
    
    /**
     * This method adds the specified role to the list principal containers.
     *
     * @param role The object containining the role information.
     * @exception SecurityException
     */
    public void addRole(Role role) throws SecurityException {
        try {
            user.touch();
        } catch (Exception ex) {
            throw new SecurityException("Failed to touch the users session : " +
                    ex.getMessage(),ex);
        }
        containerList.add(role);
    }
    
    
    /**
     * This method removes the role from the container list.
     *
     * @param role The object containing the role information.
     */
    public void removeRole(Role role) {
        for (int i = 0; i < containerList.size(); i++) {
            if (((PrincipalContainer)containerList.get(containerList.size() -
                    (i + 1))).getName().equals(role.getName())) {
                containerList.remove(containerList.size() - (i + 1));
                break;
            }
        }
    }
}
