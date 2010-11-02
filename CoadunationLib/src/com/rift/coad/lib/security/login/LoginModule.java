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
 * LoginModule.java
 *
 * The login manager is responsible for suppling the login methods for the user
 * store object. This is the object that will be responsible for retrieving user
 * information, and validating user logons against the store.
 */

package com.rift.coad.lib.security.login;

/**
 * The login manager is responsible for suppling the login methods for the user
 * store object. This is the object that will be responsible for retrieving user
 * information, and validating user logons against the store.
 *
 * @author Brett Chaldecott
 */
public interface LoginModule {
    /**
     * This method returns true if the login manager can handle the requested
     * authentication type.
     *
     * @return TRUE if it can FALSE if not.
     * @param type The type of auth to check.
     */
    public boolean handleAuthType(String type);
    
    
    /**
     * This method returns the login handler for the given auth type.
     *
     * @return The login handler that can handle the given auth type.
     * @param type The type of login.
     * @exception LoginException
     */
    public LoginHandler getLoginHandler(String type) throws LoginException;
}
