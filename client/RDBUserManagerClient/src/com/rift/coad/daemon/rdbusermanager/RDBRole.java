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

// package path
package com.rift.coad.daemon.rdbusermanager;

// java imports
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This object represents an individual role.
 * 
 * @author brett chaldecott
 */
public class RDBRole {
    // private member variable
    private String role = null;
    private List principals = new ArrayList();
    
    
    /**
     * The default constructor of the rdb role.
     */
    public RDBRole() {
        
    }
    
    
    /**
     * The constructor responsible for instanciating the rdb role.
     * 
     * @param role The role name.
     * @param principals The list of principals.
     */
    public RDBRole(String role, List principals) {
        this.role = role;
        this.principals = principals;
    }
    
    
    /**
     * The getter for the role.
     * 
     * @return The string value for the role.
     */
    public String getRole() {
        return role;
    }
    
    
    /**
     * The setter for the role.
     * 
     * @param role The new role name.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    /**
     * The getter for the principals.
     * 
     * @return The list of principals.
     */
    public List getPrincipals() {
        return principals;
    }
    
    
    /**
     * The setter for the principals.
     * 
     * @param principals The list of principals
     */
    public void setPrincipals(List principals) {
        this.principals = principals;
    }
    
    
    /**
     * This method returns the string value for this object.
     */
    @Override
    public String toString() {
        return "Role:" + role + principals.toString();
    }
}
