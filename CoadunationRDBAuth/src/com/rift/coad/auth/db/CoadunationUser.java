/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
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
 * CoadunationUser.java
 */

// package path
package com.rift.coad.auth.db;

// java imports
import java.util.Set;
import java.util.HashSet;

/**
 * This object represents an individual user.
 * 
 * @author brett chaldecott
 */
public class CoadunationUser {
    
    // private member variables
    private String username = null;
    private String password = null;
    private Set<CoadunationUserPrincipal> principals = 
            new HashSet<CoadunationUserPrincipal>();
    /**
     * The constructor for the user.
     */
    public CoadunationUser() {
        
    }
    
    
    /**
     * The constructor for the user.
     * 
     * @param username The username for this user.
     * @param password The password to authenticate against.
     */
    public CoadunationUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    /**
     * The constructor for the user.
     * 
     * @param username The username for this user.
     * @param password The password to authenticate against.
     * @param principals The list of principals attached to this user.
     */
    public CoadunationUser(String username, String password, 
            Set<CoadunationUserPrincipal> principals) {
        this.username = username;
        this.password = password;
        this.principals = principals;
    }
    
    
    /**
     * The getter for the username.
     * 
     * @return The string containing the username.
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * The getter for the username.
     * 
     * @param username The string containing the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * This method returns the password containied within this object.
     * 
     * @return The string containing the password value.
     */
    public String getPassword() {
        return password;
    }
    
    
    /**
     * This method returns the password containied within this object.
     * 
     * @param password The string containing the new password value.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * The getter for the principals.
     * 
     * @return The principals attached to this user.
     */
    public Set<CoadunationUserPrincipal> getPrincipals() {
        return principals;
    }
    
    
    /**
     * The setter for the principals.
     * 
     * @param principals The list of principals.
     */
    public void setPrincipals(Set<CoadunationUserPrincipal> principals) {
        this.principals = principals;
    }
}
