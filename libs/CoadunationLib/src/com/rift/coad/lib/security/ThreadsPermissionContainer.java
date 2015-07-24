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
 * ThreadsPermissionContainer.java
 *
 * This object contains all the thread permissions. It is not a singleton
 * as a result only the object designed to have the rights to elevate or
 * manipulate a threads premissions directly can access this object.
 */

// package definition
package com.rift.coad.lib.security;

// the java imports
import java.util.Map;
import java.util.HashMap;

// log 4 j imports
import org.apache.log4j.Logger;


/**
 * This object contains all the thread permissions. It is not a singleton
 * as a result only the object designed to have the rights to elevate or
 * manipulate a threads premissions directly can access this object.
 *
 * @author Brett Chaldecott
 */
public class ThreadsPermissionContainer {
    
    // log object
    private Logger log =
        Logger.getLogger(ThreadsPermissionContainer.class.getName());
    
    
    // the map in which all the information is stored.
    private Map permissions = null;
    
    /** 
     * Creates a new instance of ThreadsPermissionContainer 
     */
    public ThreadsPermissionContainer() {
        permissions = new HashMap();
    }
    
    
    /**
     * This method placeses the new thread session in the threads list.
     *
     * @param threadId The id of the new thread to plase in the map.
     * @param session The session to add to the list.
     */
    public synchronized void putSession(Long threadId, 
            ThreadPermissionSession session) {
        permissions.put(threadId,session);
    }
    
    
    /**
     * This method retrieves the session information from the map. Will return
     * null if the session is not found.
     *
     * @return The list of permissions.
     * @param threadId The id of the thread.
     * @exception SecurityException
     */
    public synchronized ThreadPermissionSession getSession(Long threadId) 
    throws SecurityException {
        return (ThreadPermissionSession)permissions.get(threadId);
    }
    
    
    /**
     * The synchronized method responsible for removing the session from the 
     * threads list.
     *
     * @exception SecurityException
     */
    public synchronized void removeSession(Long threadId) 
    throws SecurityException {
        permissions.remove(threadId);
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
        Long threadId = new Long(Thread.currentThread().getId());
        log.debug("Get session for [" + threadId + "]");
        ThreadPermissionSession session = getSession(threadId);
        if (session == null) {
            throw new SecurityException(
                    "No session can be found for current thread.");
        }
        return session;
    }
    
    
    /**
     * This method pushes the role onto the thread session.
     *
     * @param roleName The name of the role to push onto the thread session.
     * @exception SecurityException
     */
    public void pushRole(String roleName) throws SecurityException {
        ThreadPermissionSession session = getSession();
        Role role = RoleManager.getInstance().getRole(roleName);
        if (role.canAccessRole(session.getPrincipals()) == false) {
            throw new SecurityException(
                    "The session does not have the rights to these permissions");
        }
        session.addRole(role);
    }
    
    
    /**
     * Removed a role from the session.
     *
     * @param roleName The name of the role to remove from the session.
     * @exception SecurityException
     */
    public void popRole(String roleName) throws SecurityException {
        ThreadPermissionSession session = getSession();
        Role role = RoleManager.getInstance().getRole(roleName);
        session.removeRole(role);
    }
    
}
