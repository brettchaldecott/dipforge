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
 * UserPasswordAuthenticator.java
 *
 * The user password authenticator object.
 */

// package imports
package com.rift.coad.lib.interceptor.authenticator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.interceptor.InterceptorAuthenticator;
import com.rift.coad.lib.interceptor.InterceptorException;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Login;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.login.SessionLogin;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;

/**
 * The user password authenticator object.
 *
 * @author Brett Chaldecott
 */
public class UserPasswordAuthenticator implements InterceptorAuthenticator {
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(UserPasswordAuthenticator.class.getName());
    
    
    /** Creates a new instance of UserPasswordAuthenticator */
    public UserPasswordAuthenticator() {
    }
    
    /**
     * This method returns a valid user session object for the supplied
     * credentials
     *
     * @return UserSession The new user session for the given credentials.
     * @param credential The credentials for the user session.
     * @exception InterceptorException
     * @exception AuthorizationException
     */
    public UserSession authenticate(Credential credential) throws
            InterceptorException, AuthorizationException {
        if (!(credential instanceof Login)) {
            throw new InterceptorException(
                    "The User Password Authenticator is only capable of " +
                    "dealling with login credentials.");
        }
        try {
            Login loginInfo = (Login)credential;
            SessionLogin sessionLogin = new SessionLogin(
                    new PasswordInfoHandler(loginInfo.getUsername(),
                    loginInfo.getPassword()));
            
            sessionLogin.login();
            
            return sessionLogin.getUser();
        } catch (Exception ex) {
            log.error("Failed to authenticate the user because : " +
                    ex.getMessage(),ex);
            throw new InterceptorException(
                    "Failed to authenticate the user because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
}
