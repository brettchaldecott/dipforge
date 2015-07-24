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
 * LoginManager.java
 *
 * This class is responsible for managing the login requests initiated by users.
 */

// the package path
package com.rift.coad.lib.security.login;

// java imports
import java.util.Vector;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;

/**
 * This class is responsible for managing the login requests initiated by users.
 *
 * @author Brett Chaldecott
 */
public class LoginManager {
    
    // class constants
    private final static String SESSION_TIMEOUT = "session_timeout";
    
    // singleton variables
    private static LoginManager singleton = null; 
    
    // private member variables
    private Logger log =
            Logger.getLogger(LoginManager.class.getName());
    private Configuration config = null;
    private UserSessionManager sessionManager = null;
    private UserStoreManager userStoreManager = null;
    private long sessionTimeout = 0;
    
    /** 
     * Creates a new instance of LoginManager 
     *
     * @param sessionManager The reference to the session manager.
     * @param userStoreManager The reference to the user store manager object.
     */
    private LoginManager(UserSessionManager sessionManager,
            UserStoreManager userStoreManager) throws LoginException {
        try {
            config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            this.sessionManager = sessionManager;
            this.userStoreManager = userStoreManager;
            sessionTimeout = config.getLong(SESSION_TIMEOUT);
        } catch (Exception ex) {
            throw new LoginException(
                    "Failed to instanciate the login manager : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will instanicate a new login manager.
     *
     * @param sessionManager A reference to the user session manager.
     * @param userStoreManager The user store manager information.
     * @exception LoginException
     */
    public synchronized static void init(UserSessionManager sessionManager,
            UserStoreManager userStoreManager) throws LoginException {
        if (singleton == null) {
            singleton = new LoginManager(sessionManager,userStoreManager);
        }
    }
    
    
    /**
     * This method retrieve a reference to the Login manager singleton.
     *
     * @return The reference to the login manager.
     * @exception LoginException
     */
    public synchronized static LoginManager getInstance() throws LoginException {
        if (singleton == null) {
            throw new LoginException(
                    "The Login Manager has not been initialized");
        }
        return singleton;
    }
    
    
    /**
     * This method will perform the authentication request on behalf of a user.
     *
     * @return A reference to the user session.
     * @param infoHandler The information necessary for the login request.
     * @exception LoginException
     */
    protected UserSession authenticate(LoginInfoHandler infoHandler)
            throws LoginException {
        try {
            Vector handlerList = userStoreManager.getLoginHandlers(
                    infoHandler.getAuthType());
            for (int i = 0; i < handlerList.size(); i++) {
                LoginHandler loginHandler = (LoginHandler)handlerList.get(i);
                if (loginHandler.login(infoHandler)) {
                    UserSession userSession = loginHandler.getUserInfo();
                    userSession.setExpiryTime(sessionTimeout);
                    // We add the user session and do not init it
                    // as the init method over rides the current thread session.
                    sessionManager.addUserSession(userSession);
                    return userSession;
                }
            }
            throw new AuthenticationException("Failed to authenticate user");
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to authenticate the user : " +
                    ex.getMessage(),ex);
            throw new LoginException("Failed to authenticate the user : " +
                    ex.getMessage(),ex);
        }
    }
}
