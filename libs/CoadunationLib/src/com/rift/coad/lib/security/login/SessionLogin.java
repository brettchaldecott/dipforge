/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * SessionLogin.java
 *
 * This class is responsible for setting up a session for a user.
 */

// The package path for the session
package com.rift.coad.lib.security.login;

// coadunation imports
import com.rift.coad.lib.security.UserSession;

/**
 * This class is responsible for setting up a session for a user.
 *
 * @author Brett Chaldecott
 */
public class SessionLogin {
    
    // The classes private member variables.
    private LoginInfoHandler infoHandler = null;
    private UserSession user = null;
    
    /** 
     * Creates a new instance of SessionLogin 
     *
     * @param infoHandler The reference to the class that will gather the
     *          information required for the authentication request.
     */
    public SessionLogin(LoginInfoHandler infoHandler) {
        this.infoHandler = infoHandler;
    }
    
    
    /**
     * This method will process the login request.
     *
     * @exception LoginException
     * @exception AuthenticationException
     */
    public void login() throws LoginException, AuthenticationException {
        user = LoginManager.getInstance().authenticate(infoHandler);
    }
    
    
    /**
     * This method returns the user session login object.
     *
     * @return The reference to the user session.
     */
    public UserSession getUser() {
        return user;
    }
}
