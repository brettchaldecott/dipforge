/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
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
 * CoadunationRole.java
 */

// private member variables
package com.rift.coad.auth.db;

// java imports
import java.util.Set;
import java.util.HashSet;

/**
 * This object represents a role for coadunation.
 * 
 * @author brett chaldecott
 */
public class CoadunationRole {
    // private member variables
    private String role = null;
    private Set<CoadunationRolePrincipal> principals = 
            new HashSet<CoadunationRolePrincipal>();
    
    /**
     * The default constructor of the coadunation role.
     */
    public CoadunationRole() {
        
    }
    
    
    /**
     * This constructor responsible for 
     * 
     * @param role The role name identifying this object.
     */
    public CoadunationRole(String role) {
        this.role = role;
    }
    
    
    /**
     * This constructor responsible for 
     * 
     * @param role The role name identifying this object.
     * @param principals The principals attached to the role.
     */
    public CoadunationRole(String role, 
            Set<CoadunationRolePrincipal> principals) {
        this.role = role;
        this.principals = principals;
    }
    
    /**
     * This object returns the role name.
     *
     * @return The string containing the role name.
     */
    public String getRole() {
        return role;
    }
    
    
    /**
     * This object sets the role name.
     *
     * @param role The string containing the role name.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
    /**
     * The getter for the principals attached to this role.
     * 
     * @return The list of principals attached to this object.
     */
    public Set<CoadunationRolePrincipal> getPrincipals() {
        return principals;
    }
    
    
    /**
     * The setter for the principals.
     * 
     * @param principals The new principals
     */
    public void setPrincipals(Set<CoadunationRolePrincipal> principals) {
        this.principals = principals;
    }
}
