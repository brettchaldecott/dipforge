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
 * ThreadsPermissionContainerAccessor.java
 *
 * This object supplies access to the thread permission container.
 */

// package path
package com.rift.coad.lib.security;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

/**
 * This object supplies access to the thread permission container.
 *
 * @author Brett Chaldecott
 */
public class ThreadsPermissionContainerAccessor {
    
    // class constants
    private final static String ROLE = "role";
    
    // private singleton
    private static ThreadsPermissionContainerAccessor singleton = null;
    
    // private member variables
    private String role = null;
    private ThreadsPermissionContainer permissionContainer = null;
    
    /** 
     * Creates a new instance of ThreadsPermissionContainerAccessor 
     *
     * @param permissionContainer The container reference.
     * @exception SecurityException
     */
    private ThreadsPermissionContainerAccessor(
            ThreadsPermissionContainer permissionContainer) 
            throws SecurityException {
        try {
            this.permissionContainer = permissionContainer;
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            role = config.getString(ROLE);
        } catch (Exception ex) {
            throw new SecurityException(
                    "Failed to instanciate the thread permission container " +
                    "accessor because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method instanciates a new thread permission container accessor.
     *
     * @return A reference to this singleton.
     * @param permissionContainer The reference to the permissions container.
     */
    public synchronized static ThreadsPermissionContainerAccessor init (
            ThreadsPermissionContainer permissionContainer) 
            throws SecurityException {
        if (singleton == null) {
            singleton = new ThreadsPermissionContainerAccessor(
                    permissionContainer);
        }
        return singleton;
    }
    
    
    /**
     * This method is responsible for returning a reference to the singleton.
     *
     * @return A reference to the singleton.
     * @exception SecurityException
     */
    public synchronized static ThreadsPermissionContainerAccessor getInstance()
            throws SecurityException {
        if (singleton == null) {
            throw new SecurityException(
                    "The accessor has not been instanciated.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the threads permission container.
     *
     * @return A reference to the thread permission container.
     * @exception AuthorizationException
     * @exception SecurityException
     */
    public ThreadsPermissionContainer getThreadsPermissionContainer() throws 
            AuthorizationException, SecurityException
    {
        Validator.validate(this.getClass(),role);
        return permissionContainer;
    }
    
}
