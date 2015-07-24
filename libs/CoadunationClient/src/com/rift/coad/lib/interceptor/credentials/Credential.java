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
 * Credential.java
 *
 * The base of the credential object hierachy tree.
 */

package com.rift.coad.lib.interceptor.credentials;

// java packages
import java.io.Serializable;

/**
 * The base of the credential object hierachy tree.
 *
 * @author Brett Chaldecott
 */
public class Credential implements Serializable  {
    
    // private member variables
    private String username = null;
    
    /** 
     * Creates a new instance of Credential 
     */
    public Credential() {
    }
    
    
    /** 
     * Creates a new instance of Credential 
     *
     * @param username The username for authentication purposes.
     */
    public Credential(String username) {
        this.username = username;
    }
    
    
    /**
     * This method returns the username of object.
     *
     * @return The string containing the username of this credential.
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * This method sets the username for these credentials.
     *
     * @param username The name of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
