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
 * This object ties the user to a princial.
 * 
 * @author brett chaldecott
 */
public class CoadunationUserPrincipal {
    // private member variables
    private Integer id = null;
    private CoadunationUser user = null;
    private CoadunationPrincipal principal = null;
    
    /**
     * The default constructor.
     */
    public CoadunationUserPrincipal() {
        
    }
    
    
    /**
     * This constructor sets the user and principal values.
     * 
     * @param user The user role.
     * @param principal The principal.
     */
    public CoadunationUserPrincipal(CoadunationUser user,
            CoadunationPrincipal principal) {
        this.user = user;
        this.principal = principal;
    }
    
    
    /**
     * This constructor sets the user and principal values.
     * 
     * @param id The id of the user principal.
     * @param user The user role.
     * @param principal The principal.
     */
    public CoadunationUserPrincipal(Integer id,CoadunationUser user,
            CoadunationPrincipal principal) {
        this.id = id;
        this.user = user;
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
     * The getter for the user.
     * 
     * @return The user name to return.
     */
    public CoadunationUser getUser() {
        return user;
    }
    
    
    /**
     * The setter for the user.
     * 
     * @param user The user reference.
     */
    public void setUser(CoadunationUser user) {
        this.user = user;
    }
    
    
    /**
     * This getter for the principal reference.
     * 
     * @return This method returns the principal.
     */
    public CoadunationPrincipal getPrincipal() {
        return principal;
    }
    
    
    /**
     * This setter for the principal reference.
     * 
     * @param principal The new principal value.
     */
    public void setPrincipal(CoadunationPrincipal principal) {
        this.principal = principal;
    }
    
}
