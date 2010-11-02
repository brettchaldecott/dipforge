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
 * UserSessionManager.java
 *
 * The user session manager is responsible for creating new session for users.
 */

// the package
package com.rift.coad.lib.security.user;

// java
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

// logging import
import org.apache.log4j.Logger;

// coadunation
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * The user session manager is responsible for creating new session for users.
 *
 * @author Brett Chaldecott
 */
public class UserSessionManager {
    
    /**
     * This object is responsible for maintaining the user session 
     */
    public class UserSessionIndex {
        // Private member variables
        private Map sessionIdMap = new HashMap();
        
        
        /**
         * The default constructor of the user session index
         */
        public UserSessionIndex() {
            
        }
        
        
        /**
         * This method will add add a user session to the indexs appropriatly.
         *
         * @param userSession The reference to the user session object.
         */
        public synchronized void putUser(UserSession userSession) {
            sessionIdMap.put(userSession.getSessionId(),userSession);
        }
        
        
        /**
         * This method returns the user session object instance.
         *
         * @return The reference to the user session object.
         * @param sessionId The id of the users session
         * @exception UserException
         */
        public synchronized UserSession getSessionById(String sessionId) 
                throws UserException {
            try {
                if (!sessionIdMap.containsKey(sessionId)) {
                    throw new UserException("No session exists for session id.");
                }
                UserSession userSession = 
                        (UserSession)sessionIdMap.get(sessionId);
                if (userSession.isExpired()) {
                    sessionIdMap.remove(userSession.getSessionId());
                    throw new UserException("No session exists for user.");
                }
                userSession.touch();
                return userSession;
            } catch (SecurityException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new UserException("Failed to retrieve the user session : "
                        + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method removes a session identifed by a session id
         *
         * @param sessionId The id of the users session
         */
        public synchronized void removeSessionById(String sessionId) {
            if (!sessionIdMap.containsKey(sessionId)) {
                return;
            }
            UserSession userSession = 
                    (UserSession)sessionIdMap.get(sessionId);
            sessionIdMap.remove(userSession.getSessionId());
        }
    }
    
    
    /**
     * This is a thread that is responsible for cleaning up the old user 
     * sessions. It does not inhert from BasicThread because the environment
     * has not been configured properly yet for a basic thread to be started.
     */
    public class TimeoutThread extends Thread
    {
        // the classes constants
        private final static String DELAY = "session_timeout_delay";
        private final static long DELAY_DEFAULT = 60 * 1000;
        
        // private member variables
        private ThreadStateMonitor stateMonitor = null;
        
        /**
         * The constructor of the timeout thread.
         *
         * @exception UserException
         */
        public TimeoutThread() throws UserException {
            try {
                Configuration config = ConfigurationFactory.getInstance().
                        getConfig(this.getClass());
                long delay = config.getLong(DELAY,DELAY_DEFAULT);
                stateMonitor = new ThreadStateMonitor(delay);
            } catch (Exception ex) {
                log.error("Failed to init the timeout thread : " + 
                        ex.getMessage(),ex);
                throw new UserException("Failed to init the timeout thread : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method runs through and removes sessions that have expired.
         */
        public void run() {
            while(!stateMonitor.isTerminated()) {
                stateMonitor.monitor();
                Map sessionMapCopy = new HashMap();
                synchronized(userSessionIndex) {
                    sessionMapCopy.putAll(userSessionIndex.sessionIdMap);
                }
                for (Iterator iter = sessionMapCopy.keySet().iterator();
                        iter.hasNext() && !stateMonitor.isTerminated();) {
                    UserSession userSession = (UserSession)sessionMapCopy.get(
                            iter.next());
                    // we check the expiry date twice to deal with race
                    // conditions that exist with aquiring the lock on the
                    // user session index.
                    if (userSession.isExpired()) {
                        synchronized(userSessionIndex) {
                            if (userSession.isExpired()) {
                                userSessionIndex.sessionIdMap.remove(
                                        userSession.getSessionId());
                            }
                        }
                    }
                }
            }
        }
        
        
        /**
         * This method terminates the timeout thread.
         */
        public void terminte() {
            stateMonitor.terminate(true);
        }
    }
    
    // the user session logger
    private Logger log =
        Logger.getLogger(UserSessionManager.class.getName());
    
    // the classes member variables
    private UserSessionIndex userSessionIndex = new UserSessionIndex();
    private TimeoutThread timeoutThread = null;
    private ThreadsPermissionContainer permissions = null;
    private UserStoreManager userStoreManager = null;
    
    /** 
     * Creates a new instance of UserSessionManager 
     *
     * @param permissions The permissions for the threads.
     * @param userStoreManager The reference to the user store manager.
     */
    public UserSessionManager(ThreadsPermissionContainer permissions,
            UserStoreManager userStoreManager) {
        this.permissions = permissions;
        this.userStoreManager = userStoreManager;
    }
    
    
    /**
     * This method will create a new session for the calling thread using the
     * supplied username.
     *
     * @param username The name of the user that the session must be created for.
     * @exception UserException
     */
    public void initSessionForUser(String username) throws UserException {
        try {
            UserSession user = userStoreManager.getUserInfo(username);
            Long threadId = new Long(Thread.currentThread().getId());
            permissions.putSession(threadId,new ThreadPermissionSession(
                    threadId,user));
            userSessionIndex.putUser(user);
        } catch (Exception ex) {
            throw new UserException("Failed to init the session for [" +
                    username + "] : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will create a new session for the calling thread using the
     * supplied username.
     *
     * @param user The user
     * @exception UserException
     */
    public void initSessionForUser(UserSession user) throws UserException {
        try {
            Long threadId = new Long(Thread.currentThread().getId());
            permissions.putSession(threadId,new ThreadPermissionSession(
                    threadId,user));
            userSessionIndex.putUser(user);
        } catch (Exception ex) {
            throw new UserException("Failed to init the session for [" +
                    user.getName() + "] : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a session for a user to the user session index so that
     * it can be refered to later. It does not over ride the session on the
     * thread as this will be done when the thread Sudo's to that user.
     *
     * @param user The user
     * @exception UserException
     */
    public void addUserSession(UserSession user) throws UserException {
        try {
            userSessionIndex.putUser(user);
        } catch (Exception ex) {
            throw new UserException("Failed to init the session for [" +
                    user.getName() + "] : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the user session id.
     *
     * @return The reference to the user session object.
     * @param sessionId The references to the user session id.
     * @exception UserException
     */
    public UserSession getSessionById(String sessionId) throws UserException {
        return userSessionIndex.getSessionById(sessionId);
    }
    
    
    /**
     * This method starts the clean up thread
     *
     * @exception UserException
     */
    public void startCleanup() throws UserException {
        timeoutThread = new TimeoutThread();
        timeoutThread.start();
    }
    
    
    /**
     * This method is responsible for terminating the user session store
     */
    public void shutdown() {
        try {
            timeoutThread.terminte();
            timeoutThread.join();
        } catch (Exception ex) {
            log.error("Failed to wait for the time out thread to terminate " +
                    "because : "  + ex.getMessage(),ex);
        }
    }
}
