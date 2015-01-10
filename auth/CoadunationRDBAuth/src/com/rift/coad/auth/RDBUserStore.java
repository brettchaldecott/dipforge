/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
 * Copyright (C) 2008  Rift IT Contracting
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
 * RDBUserStore.java
 */

// package path
package com.rift.coad.auth;

// java imports
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

// log4j imports
import org.apache.log4j.Logger;


// coadunation imports
import com.rift.coad.lib.security.login.AuthValues;
import com.rift.coad.lib.security.login.AuthTypes;
import com.rift.coad.lib.security.login.LoginException;
import com.rift.coad.lib.security.login.LoginHandler;
import com.rift.coad.lib.security.login.LoginInfoHandler;
import com.rift.coad.lib.security.user.UserStoreConnector;
import com.rift.coad.lib.security.user.UserException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.lib.deployment.DeploymentMonitor;

// import database
import com.rift.coad.auth.db.*;
import com.rift.coad.auth.util.HashPassword;

/**
 * This objects represents a user store.
 * 
 * @author brett chaldecott
 */
public class RDBUserStore implements UserStoreConnector {

    /**
     * This object handles the login authentication.
     * 
     * @author brett chaldecott
     */
    public class RDBLoginHandler implements LoginHandler {
        // the classes private member variables
        private UserSession user = null;

        /**
         * The constructor of the RDB login handler.
         */
        public RDBLoginHandler() {

        }

        /**
         * This object returns a reference to the authenticated session this object
         * was created to initialize.
         * 
         * @return This method returns a copy of the authenticated user session.
         * @throws com.rift.coad.lib.security.login.LoginException
         */
        public UserSession getUserInfo() throws LoginException {
            return user;
        }

        /**
         * This method returns true if the login succeeded.
         * 
         * @param loginInfoHandler The reference to the authentication data.
         * @return Return true if authenticated, false if not.
         * @throws com.rift.coad.lib.security.login.LoginException
         */
        public boolean login(LoginInfoHandler loginInfoHandler) throws LoginException {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            UserTransactionWrapper transaction = null;
            try {
                if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
                    return false;
                }
                Map parameters = loginInfoHandler.getInfo();
                String username = (String) parameters.get(AuthValues.USERNAME);
                if (username == null) {
                    throw new LoginException(
                            "The login information handler has not supplied the " +
                            "username");
                }
                String password = (String) parameters.get(AuthValues.PASSWORD);
                if (password == null) {
                    throw new LoginException(
                            "The login information handler has not supplied the " +
                            "username");
                }
                String shaPassword = HashPassword.generateSha(password);

                transaction = new UserTransactionWrapper();
                transaction.begin();
                Session session =
                        HibernateUtil.getInstance(RDBUserStore.class).getSession();
                CoadunationUser user = (CoadunationUser) session.get(
                        CoadunationUser.class, username);
                if (user.getPassword().equals(shaPassword)) {
                    this.user = new UserSession(user.getUsername(),
                            getPrincipals(user));
                    return true;
                }
            } catch (Throwable ex) {
                log.debug("Failed to retrieve the user information : " +
                        ex.getMessage(), ex);
            } finally {
                if (transaction != null) {
                    transaction.release();
                }
                // reset the loader
                Thread.currentThread().setContextClassLoader(loader);
            }
            return false;
        }
    }

    // private member variables
    private Logger log = Logger.getLogger(RDBUserStore.class);

    /**
     * The constructor of the RDB user store.
     */
    public RDBUserStore() throws RDBException {
    }

    /**
     * This method returns the name of the rdb user store.
     * 
     * @return The string containing the name of the rdb user store.
     */
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * This method returns a user session for the user name. This contains
     * 
     * @param username This method returns the user session.
     * @return This method user session object for the user name.
     * @throws com.rift.coad.lib.security.user.UserException
     */
    public UserSession getUserInfo(String username) throws UserException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        UserTransactionWrapper transaction = null;
        try {
            if (!DeploymentMonitor.getInstance().isInitDeployComplete()) {
                return null;
            }
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session =
                    HibernateUtil.getInstance(RDBUserStore.class).getSession();
            CoadunationUser user = (CoadunationUser) session.get(
                    CoadunationUser.class, username);
            // return the user session.
            return new UserSession(user.getUsername(),
                    getPrincipals(user));
        } catch (Exception ex) {
            log.debug("Failed to retrieve the user information : " +
                    ex.getMessage(), ex);
            return null;
        } finally {
            if (transaction != null) {
                transaction.release();
            }
            // reset the loader
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    /**
     * This method returns the authentication type.
     * 
     * @param type This method returns true if the specified auth type is handled
     *        by this object.
     * @return True if 
     */
    public boolean handleAuthType(String type) {
        return AuthTypes.PASSWORD.equals(type);
    }

    /**
     * This method returns the login handler.
     * 
     * @param type This method returns a reference to the login handler.
     * @return The reference to the login handler.
     * @throws com.rift.coad.lib.security.login.LoginException
     */
    public LoginHandler getLoginHandler(String type) throws LoginException {
        return new RDBLoginHandler();
    }

    /**
     * This method returns the list of principals for the user.
     *
     * @param user The user to retrieve the principals from.
     * @return The set containing the string principals.
     * @throws RDBException
     */
    private Set getPrincipals(CoadunationUser user) throws RDBException {
        try {
            Set principals = new HashSet();
            for (Iterator iter = user.getPrincipals().iterator();
                    iter.hasNext();) {
                CoadunationUserPrincipal principal =
                        (CoadunationUserPrincipal) iter.next();
                principals.add(principal.getPrincipal().getName());
            }
            return principals;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the principal information : " +
                    ex.getMessage(), ex);
            throw new RDBException(
                    "Failed to retrieve the principal information : " +
                    ex.getMessage(), ex);
        }
    }
}
