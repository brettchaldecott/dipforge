/*
 * RDBUserManagerClient: The client of the RDB User Manager
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
 * RDBUser.java
 */

// the package path
package com.rift.coad.daemon.rdbusermanager;

// the imports
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This object represents a user.
 * 
 * @author brett chaldecott
 */
public class RDBUser implements Serializable {
    // private member variables
    private String username = null;
    private List principals = new ArrayList();
    
    
    /**
     * The default constructor of the RDBUser.
     */
    public RDBUser() {
        
    }
    
    
    /**
     * The constructor responsible for setting the private member variables.
     * 
     * @param username The name of the user this object represents.
     * @param principals The list of principals attached to this user.
     */
    public RDBUser(String username, List principals) {
        this.username = username;
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
     * The setter for the username.
     * 
     * @param username The string containing the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * The getter for the principals.
     * 
     * @return The principals for the user.
     */
    public List getPrincipals() {
        return principals;
    }
    
    
    /**
     * The setter for the principals.
     * 
     * @param principals The list of principals for the user.
     */
    public void setPrincipals(List principals) {
        this.principals = principals;
    }
    
    
    /**
     * This method returns the string value for this object
     * 
     * @return a string representation of the internal value.
     */
    @Override
    public String toString() {
        return "Username:" + username + principals.toString();
    }
}
