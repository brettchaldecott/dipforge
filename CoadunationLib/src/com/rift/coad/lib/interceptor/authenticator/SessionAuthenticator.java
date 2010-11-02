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
 * SessionAuthenticator.java
 *
 * This class acts as the standard interceptor authenticator.
 */

// package path
package com.rift.coad.lib.interceptor.authenticator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.interceptor.InterceptorAuthenticator;
import com.rift.coad.lib.interceptor.InterceptorException;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Session;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserException;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;

/**
 * This class acts as the standard interceptor authenticator.
 *
 * @author Brett Chaldecott
 */
public class SessionAuthenticator implements
        InterceptorAuthenticator {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(SessionAuthenticator.class.getName());
    
    // private member variables
    private UserSessionManager userSessionManager = null;
    private UserStoreManager userStoreManager = null;
    
    /**
     * The private default constructor private so it cannot not be called
     * by anyone
     */
    private SessionAuthenticator() {
        System.out.println("The call to the default constructor");
    }
    
    /**
     * Creates a new instance of SessionAuthenticator
     * 
     * 
     * @param userSessionManager The reference to the user session manager.
     * @param userStoreManager Reference to the user store manager
     */
    public SessionAuthenticator(UserSessionManager
            userSessionManager, UserStoreManager userStoreManager) {
        this.userSessionManager = userSessionManager;
        this.userStoreManager = userStoreManager;
    }
    
    
    /**
     * This method returns a valid user session object for the supplied
     * credentials
     *
     * @return UserSession The new user session for the given credentials.
     * @param credentials The credentials for the user session.
     * @exception InterceptorException
     * @exception AuthorizationException
     */
    public UserSession authenticate(Credential credential) throws
            InterceptorException, AuthorizationException {
        if (!(credential instanceof Session)) {
            throw new InterceptorException(
                    "The session authenticator is only capable of dealling " +
                    "with session credentials.");
        }
        Session sessionCredential = (Session)credential;
        UserSession userSession = null;
        try {
            userSession = (UserSession)(userSessionManager.getSessionById(
                    sessionCredential.getSessionId()).clone());
        } catch (UserException ex) {
            try {
                userSession = userStoreManager.getUserInfo(
                        sessionCredential.getUsername());
                userSession.setSessionId(sessionCredential.getSessionId());
                userSessionManager.addUserSession(userSession);
                userSession = (UserSession)userSession.clone();
            } catch (Exception ex2) {
                log.error("Failed to retrieve the session information : " +
                        ex2.getMessage(),ex2);
                throw new InterceptorException(
                        "Failed to retrieve the session information : " +
                        ex2.getMessage(),ex2);
            }
        } catch (Exception ex) {
            throw new InterceptorException(
                    "Failed to retrieve the session information : " +
                    ex.getMessage(),ex);
        }
        userSession.setPrincipals(sessionCredential.getPrincipals());
        return userSession;
    }
}
