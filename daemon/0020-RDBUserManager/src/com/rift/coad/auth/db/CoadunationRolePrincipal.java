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
 * CoadunationUserPrincipal.java
 */

// package path
package com.rift.coad.auth.db;

/**
 * The role principal object.
 * 
 * @author brett chaldecott
 */
public class CoadunationRolePrincipal {
    // private member variables
    private Integer id = null;
    private CoadunationRole role = null;
    private CoadunationPrincipal principal = null;
    
    /**
     * The default constructor of the coadunation role principal.
     */
    public CoadunationRolePrincipal() {
        
    }
    
    
    /**
     * This constructor is responsible for setting all the internal variables.
     */
    public CoadunationRolePrincipal(CoadunationRole role,
           CoadunationPrincipal principal) {
        this.role = role;
        this.principal = principal;
    }
    
    /**
     * This constructor is responsible for setting all the internal variables.
     * 
     * @param id The id for this row in the database.
     * @param role The role that this object is bound to.
     * @param principal The principal this object is bound to.
     */
    public CoadunationRolePrincipal(Integer id, CoadunationRole role,
           CoadunationPrincipal principal) {
        this.id = id;
        this.role = role;
        this.principal = principal;
    }
    
    
    /**
     * The getter for the id.
     * 
     * @return This method returns the id contained within.
     */
    public Integer getId() {
        return id;
    }
    
    
    /**
     * The setter for the id.
     * 
     * @param id The new id for this entry.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * The getter for the role.
     * 
     * @return This method returns the role.
     */
    public CoadunationRole getRole() {
        return role;
    }
    
    /**
     * The setter for the role.
     * 
     * @param role The new role
     */
    public void setRole(CoadunationRole role) {
        this.role = role;
    }
    
    
    /**
     * The getter for the principal
     * 
     * @return The principal.
     */
    public CoadunationPrincipal getPrincipal() {
        return principal;
    }
    
    
    /**
     * The setter for the principal
     * 
     * @param The principal.
     */
    public void setPrincipal(CoadunationPrincipal principal) {
        this.principal = principal;
    }
}
