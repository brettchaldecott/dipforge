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
 * SessionManager.java
 *
 * This object is responsible for managing the ThreadPermissionSession objects
 * in memory.
 */

// package definition
package com.rift.coad.lib.security;

// log 4 j imports
import org.apache.log4j.Logger;


/**
 * This object is responsible for managing the ThreadPermissionSession objects
 * in memory.
 *
 * @author Brett Chaldecott
 */
public final class SessionManager {
    
    // log object
    private Logger log =
        Logger.getLogger(SessionManager.class.getName());
    
    
    // the classes private singleton member variables
    private static SessionManager singleton = null;
    
    // the classes private member variables
    private ThreadsPermissionContainer permissions = null;
    
    
    /** 
     * Creates a new instance of SessionManager 
     */
    private SessionManager(ThreadsPermissionContainer permissions) {
        this.permissions = permissions;
    }
    
    
    /**
     * The init method for the session manager. This will be called to
     * instanciate the session manager.
     *
     * @return The reference to the session manager.
     * @param permissions The reference to the permission object.
     */
    public synchronized static SessionManager init(
            ThreadsPermissionContainer permissions) 
            throws SecurityException {
        if (singleton != null) {
            throw new SecurityException(
                "The SessionManager has already been initialized.");
        }
        singleton = new SessionManager(permissions);
        return singleton;
    }
    
    
    /**
     * This method returns the current instance of the session manager of a new
     * on if none exists.
     *
     * @return A reference to the singleton session manager.
     */
    public synchronized static SessionManager getInstance() 
        throws SecurityException {
        if (singleton != null) {
            return singleton;
        }
        throw new SecurityException(
                "The session manager has not been initialized");
    }
    
    
    /**
     * This method will init a new session for the given thread. If one does
     * exist that session will be over written.
     *
     * @param user The user object used to init the session.
     * @exception SecurityException
     */
    public void initSession() throws SecurityException {
        Long threadId = new Long(Thread.currentThread().getId());
        permissions.putSession(threadId,
                new ThreadPermissionSession(threadId,new UserSession()));
    }
    
    
    /**
     * This method returns the permission information for the current session
     * identified by the thread making the call.
     *
     * @return The thread permission session object.
     * @exception SecurityException
     */
    public ThreadPermissionSession getSession() 
    throws SecurityException {
        return permissions.getSession();
    }
    
    
    /**
     * This method removes the session information for the calling thread.
     *
     * @exception SecurityException
     */
    public void purgeSession() throws SecurityException {
        Long threadId = new Long(Thread.currentThread().getId());
        permissions.removeSession(threadId);
    }
}
