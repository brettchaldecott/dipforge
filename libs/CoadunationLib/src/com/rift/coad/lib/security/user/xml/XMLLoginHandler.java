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
 * XMLLoginHandler.java
 *
 * This class is responsible for authenticating a login request against the
 * XML user information.
 */

// the package path
package com.rift.coad.lib.security.user.xml;

// java imports
import java.util.Map;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.security.login.AuthValues;
import com.rift.coad.lib.security.login.LoginHandler;
import com.rift.coad.lib.security.login.LoginException;
import com.rift.coad.lib.security.login.LoginInfoHandler;
import com.rift.coad.lib.security.UserSession;

/**
 * This class is responsible for authenticating a login request against the
 * XML user information.
 *
 * @author Brett Chaldecott
 */
public class XMLLoginHandler implements LoginHandler {
    
    // the classes private member variables
    private Logger log =
            Logger.getLogger(XMLLoginHandler.class.getName());
    private Map users = null;
    private UserSession user = null;
    
    /** 
     * Creates a new instance of XMLLoginHandler 
     */
    public XMLLoginHandler(Map users) {
        this.users = users;
    }
    
    /**
     * This method returns a reference to the login manager the interface that
     * this object was retrieved from.
     *
     * @return A reference to the login manager.
     * @exception LoginException
     */
    public UserSession getUserInfo() throws LoginException {
        if (user == null) {
            throw new LoginException(
                    "No valid login request has been processed by this object.");
        }
        return user;
    }
    
    
    /**
     * This method preforms the login for a given user using the login
     * information supplied to that user.
     *
     * @return TRUE if the login succeeded, FALSE if not.
     * @param loginInfoHandler The handler containing the login information.
     * @exception LoginException
     */
    public boolean login(LoginInfoHandler loginInfoHandler) 
    throws LoginException {
        try {
            Map parameters = loginInfoHandler.getInfo();
            String username = (String)parameters.get(AuthValues.USERNAME);
            if (username == null) {
                throw new LoginException(
                        "The login information handler has not supplied the " +
                        "username");
            }
            UserData userData = (UserData)users.get(username);
            if (userData == null) {
                log.debug("Username [" + username + "] was not found.");
                return false;
            }
            String password = (String)parameters.get(AuthValues.PASSWORD);
            if (userData.getPassword().equals(password) == false) {
                return false;
            }
            user = userData.getUser();
            return true;
        } catch (Exception ex) {
            throw new LoginException("Failed to log the user in because : " + 
                    ex.getMessage(),ex);
        }
    }
}
