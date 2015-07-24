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
 * XMLUserStore.java
 *
 * The class responsible for retrieving the XML user information.
 */

// package path
package com.rift.coad.lib.security.user.xml;

// java imports
import java.util.Map;
import java.util.HashMap;

// coadunation imports
import com.rift.coad.lib.security.login.AuthTypes;
import com.rift.coad.lib.security.login.LoginException;
import com.rift.coad.lib.security.login.LoginHandler;
import com.rift.coad.lib.security.user.UserStoreConnector;
import com.rift.coad.lib.security.user.UserException;
import com.rift.coad.lib.security.UserSession;

/**
 * The class responsible for retrieving the XML user information.
 *
 * @author Brett Chaldecott
 */
public class XMLUserStore implements UserStoreConnector {
    
    // the list of users
    private XMLUserParser userParser = null;
    
    /**
     * Creates a new instance of XMLUserStore 
     */
    public XMLUserStore() throws UserException {
        userParser = new XMLUserParser();
    }
    
    
    /**
     * This method returns the name of the user store.
     *
     * @return The string containing the name of the user store.
     */
    public String getName() {
        return "XMLUserStore";
    }
    
    
    /**
     * This method returns the user information for the given username. Note:
     * this method must not throw an exception if the user is not found, it must
     * instead return null.
     * 
     * 
     * @return The name of the user to retrieve information for.
     * @return User The user object for the given username.
     * @exception UserException
     */
    public UserSession getUserInfo(String username) throws UserException {
        userParser.reload();
        UserData userData = (UserData)userParser.getUsers().get(username);
        if (userData == null) {
            return null;
        }
        return userData.getUser();
    }
    
    
    /**
     * This method returns true if the login manager can handle the requested
     * authentication type.
     *
     * @return TRUE if it can FALSE if not.
     * @param type The type of auth to check.
     */
    public boolean handleAuthType(String type) {
        return AuthTypes.PASSWORD.equals(type);
    }
    
    
    /**
     * This method returns the login handler for the given auth type.
     *
     * @return The login handler that can handle the given auth type.
     * @param type The type of login.
     * @exception LoginException
     */
    public LoginHandler getLoginHandler(String type) throws LoginException {
        userParser.reload();
        return new XMLLoginHandler(userParser.getUsers());
    }
}
