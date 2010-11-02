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
 * UserStoreConnector.java
 *
 * This interface supplies the ability to connect to the user data store. This
 * data store could be Database, Text File, XML File, LDAP etc.
 */

// The package path
package com.rift.coad.lib.security.user;

// coadunation imports
import com.rift.coad.lib.security.login.LoginModule;
import com.rift.coad.lib.security.UserSession;

/**
 * This interface supplies the ability to connect to the user data store. This
 * data store could be Database, Text File, XML File, LDAP etc.
 *
 * @author Brett Chaldecott
 */
public interface UserStoreConnector extends LoginModule {
    
    /**
     * This method returns the name of the user store.
     *
     * @return The string containing the name of the user store.
     */
    public String getName();
    
    
    /**
     * This method returns the user information for the given username. Note:
     * this method must not throw an exception if the user is not found, it must
     * instead return null.
     * 
     * 
     * @param username The name of the user to retrieve information for.
     * @return UserSession The user object for the given username.
     * @exception UserException
     */
    public UserSession getUserInfo(String username) throws UserException;
}
