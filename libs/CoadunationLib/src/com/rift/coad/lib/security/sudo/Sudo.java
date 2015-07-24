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
 * Sudo.java
 *
 * The object responsible for running code as a specified user.
 */

// package path
package com.rift.coad.lib.security.sudo;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.thread.BasicThread;


/**
 * The object responsible for running code as a specified user.
 *
 * @author Brett Chaldecott
 */
public class Sudo {
    
    // class constants
    private final static String ROLE = "role";
    
    // static member variables
    private static Logger log =
        Logger.getLogger(Sudo.class.getName());
    private static String role = null;
    
    // setup the role
    static {
        try {
            Configuration configuration = 
                    ConfigurationFactory.getInstance().getConfig(Sudo.class);
            role = configuration.getString(ROLE);
        } catch (Exception ex) {
            log.error("Failed to retrieve the sudo role : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * Creates a new instance of Sudo
     */
    private Sudo() {
    }
    
    
    /**
     * This method will get called to run a thread as another user.
     *
     * @param username The name of the user to run the handler as.
     * @param handler The reference to the object that will be called after the
     *          user has been set correctly.
     * @exception SudoException
     * @exception Exception
     */
    public static void sudoThreadByUser (String username, 
            SudoCallbackHandler handler) throws SudoException, Exception {
        Validator.validate(Sudo.class,role);
        ThreadsPermissionContainer threadsPermissionContainer =
                    ThreadsPermissionContainerAccessor.getInstance().
                    getThreadsPermissionContainer();
        UserStoreManager userStoreManager = 
                    UserStoreManagerAccessor.getInstance().
                    getUserStoreManager();
        
        // retrieve the use session information
        Thread currentThread = null;
        ThreadPermissionSession currentPermissions = null;
        UserSession newUserSession = null;
        try {
            // retrieve the current user session
            currentThread = Thread.currentThread();
            currentPermissions = threadsPermissionContainer.getSession(
                    new Long(currentThread.getId()));
            newUserSession = userStoreManager.getUserInfo(username);
        } catch (Exception ex) {
            throw new SudoException(
                    "Failed to retrieve the necessary user information : " +
                    ex.getMessage(),ex);
        }
        
        // set user
        threadsPermissionContainer.putSession(new Long(currentThread.getId()),
                new ThreadPermissionSession(
                new Long(currentThread.getId()),newUserSession));
        log.info("Set [" + currentThread.getId() + "] user from [" + 
                currentPermissions.getUser().getName() + "] to [" +
                newUserSession.getName() + "] to run the command on : " + 
                handler.getClass().getName());
        
        try {
            handler.process();
        } finally {
            // reset the user session
            threadsPermissionContainer.putSession(new Long(currentThread.getId()),
                    currentPermissions);
            // set the user back
            log.info("Set user back from [" + newUserSession.getName() +  
                    "] to [" +  currentPermissions.getUser().getName() +  
                    "] after running command on : " + handler.getClass().getName());
        }
    }
    
    
    /**
     * This method will sudo a user to a user session id.
     *
     * @param sessionId The id of the session to sudo.
     * @param handler The reference to the handler.
     * @exception SudoException
     * @exception Exception
     */
    public static void sudoThreadBySessionId(String sessionId, 
            SudoCallbackHandler handler) throws SudoException, Exception {
        Validator.validate(Sudo.class,role);
        ThreadsPermissionContainer threadsPermissionContainer =
                    ThreadsPermissionContainerAccessor.getInstance().
                    getThreadsPermissionContainer();
        UserSessionManager userSessionManager =
                UserSessionManagerAccessor.getInstance().
                getUserSessionManager();
        // retrieve the use session information
        Thread currentThread = null;
        ThreadPermissionSession currentPermissions = null;
        UserSession newUserSession = null;
        try {
            // retrieve the current user session
            currentThread = Thread.currentThread();
            currentPermissions = threadsPermissionContainer.getSession(
                    new Long(currentThread.getId()));
            newUserSession = userSessionManager.getSessionById(sessionId);
        } catch (Exception ex) {
            throw new SudoException(
                    "Failed to retrieve the necessary user information : " +
                    ex.getMessage(),ex);
        }
        
        // set user
        threadsPermissionContainer.putSession(new Long(currentThread.getId()),
                new ThreadPermissionSession(
                new Long(currentThread.getId()),newUserSession));
        
        log.info("Set [" + currentThread.getId() + "] user from [" 
                + currentPermissions.getUser().getName() +  "] to [" + 
                newUserSession.getName() +  "] to run the command on : " + 
                handler.getClass().getName());
        
        try {
            handler.process();
        } finally {
            // reset the user session
            threadsPermissionContainer.putSession(new Long(currentThread.getId()),
                    currentPermissions);
            
            // set the user back
            log.info("Set user back from [" + newUserSession.getName() +  
                    "] to [" +  currentPermissions.getUser().getName() +  
                    "] after running command on : " + handler.getClass().getName());
        }
    }
}
