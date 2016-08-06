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
 * PasswordInfoHandler.java
 *
 * This class will handle a basic password login if both user name and password
 * are supplied when the class is instanciated.
 */

// the package path for the source file
package com.rift.coad.lib.security.login.handlers;

// java imports
import java.util.Map;
import java.util.HashMap;

// coadunation imports
import com.rift.coad.lib.security.login.AuthValues;
import com.rift.coad.lib.security.login.AuthTypes;
import com.rift.coad.lib.security.login.LoginInfoHandler;
import com.rift.coad.lib.security.login.LoginException;

/**
 * This class will handle a basic password login if both user name and password
 * are supplied when the class is instanciated.
 *
 * @author Brett Chaldecott
 */
public class PasswordInfoHandler implements LoginInfoHandler {
    
    // the classes private member variables
    private String username = null;
    private String password = null;
    
    /** 
     * Creates a new instance of PasswordInfoHandler 
     *
     * @param username The username to use for authentication
     * @param password The password to authenticate the user with
     */
    public PasswordInfoHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    /**
     * This method returns the authentication type required.
     *
     * @return The string containing the authentication type.
     */
    public String getAuthType() {
        return AuthTypes.PASSWORD;
    }
            
    
    
    /**
     * This method return the information required to perform the login.
     *
     * @return The map containing the authentication information.
     * @exception LoginException If the info cannot be retrieved
     */
    public Map getInfo() throws LoginException {
        Map parameters = new HashMap();
        parameters.put(AuthValues.USERNAME,username);
        parameters.put(AuthValues.PASSWORD,password);
        return parameters;
    }
}
