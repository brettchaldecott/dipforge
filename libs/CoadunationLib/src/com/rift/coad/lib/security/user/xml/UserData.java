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
 * UserData.java
 *
 * This object stores the user data retrieve from the XML user store.
 */

// the package that this object will reside in
package com.rift.coad.lib.security.user.xml;

// the java imports
import java.util.Set;
import java.util.HashSet;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserException;

/**
 * This object stores the user data retrieve from the XML user store.
 *
 * @author Brett Chaldecott
 */
public class UserData {
    
    // The classes private member variables
    private String username = null;
    private String password = null;
    private Set principals = null;
    
    
    /** 
     * Creates a new instance of UserData 
     *
     * @param username The name of the user that this user data object will be
     *          associated with.
     * @param password The password value for the user object.
     */
    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
        principals = new HashSet();
    }
    
    
    /**
     * This method sets the username to the value supplied.
     *
     * @param username The string object containing the new username of the
     *          object.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     * This method retrieves the username value for the user.
     *
     * @return The string containing the username information.
     */
    public String getUsername() {
        return username;
    }
    
    
    /**
     * Set the password value assigned to this user object.
     *
     * @param password The string containing the new password value to assign.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * This method retrieves the password value held within.
     *
     * @return The string containing the password information.
     */
    public String getPassword() {
        return password;
    }
    
    
    /**
     * This method adds the principal to the list of principals.
     *
     * @param principal The string object containing the principal value.
     */
    public void addPrincipal(String principal) {
        principals.add(principal);
    }
    
    
    /**
     * This method retrieves the list of principals from within the user data
     * object.
     *
     * @return The list of principals.
     */
    public Set getPrincipals() {
        return principals;
    }
    
    
    /**
     * This method will return TRUE if this object has been initialized.
     *
     * @return TRUE if initialized FALSE if not.
     */
    public boolean isInitialized() {
        if ((username == null) || (password == null)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This method returns the reference to the user object.
     *
     * @return The reference to the user object.
     */
    public UserSession getUser() throws UserException {
        try {
            return new UserSession(username,principals);
        } catch (Exception ex) {
            throw new UserException (
                    "Failed to instanciate the user exception : " + 
                    ex.getMessage(),ex);
        }
    }
}
