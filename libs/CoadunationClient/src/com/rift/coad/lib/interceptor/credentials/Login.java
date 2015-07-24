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
 * Login.java
 *
 * This class defines the login information.
 */

// package path
package com.rift.coad.lib.interceptor.credentials;

/**
 * This class defines the login information.
 *
 * @author Brett Chaldecott
 */
public class Login extends Credential {
    // private member variables
    private String password = null;
    
    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    
    /**
     * The constructor responsible for setting the private member variables.
     *
     * @param username The name of the user.
     * @param password The password to authenticate the user.
     */
    public Login(String username, String password) {
        super(username);
        this.password = password;
    }
    
    
    /**
     * Sets the password for the given user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * Retrieves the password for the user.
     */
    public String getPassword() {
        return password;
    }
}
