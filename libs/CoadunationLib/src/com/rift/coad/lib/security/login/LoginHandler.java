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
 * LoginHandler.java
 *
 * This is the interface that processes the login request for a given user
 * store.
 */

// the package
package com.rift.coad.lib.security.login;

// coadunation imports
import com.rift.coad.lib.security.UserSession;


/**
 * This is the interface that processes the login request for a given user
 * store.
 *
 * @author Brett Chaldecott
 */
public interface LoginHandler {
    
    /**
     * This method returns a reference to the login manager the interface that
     * this object was retrieved from.
     *
     * @return A reference to the login manager.
     * @exception LoginException
     */
    public UserSession getUserInfo() throws LoginException;
    
    
    /**
     * This method preforms the login for a given user using the login
     * information supplied to that user.
     *
     * @return TRUE if the login succeeded, FALSE if not.
     * @param loginInfoHandler The handler containing the login information.
     * @exception LoginException
     */
    public boolean login(LoginInfoHandler loginInfoHandler) throws LoginException;
    
    
}
