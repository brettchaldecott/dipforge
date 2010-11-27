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
 * RoleManager.java
 *
 * The role manager, responsible for managing all the role information used
 * in coadunation.
 */

// the package name
package com.rift.coad.lib.security;

// java import
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Calendar;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * The role manager, responsible for managing all the role information used
 * in coadunation.
 *
 * @author Brett Chaldecott
 */
public class RoleManager {

    /**
     * This object represents a 
     */
    public class RoleCache {
        // private member variables
        private Role role = null;
        private long expiryTime = 0;

        /**
         * The constructor of the role cache object.
         */
        public RoleCache(Role role) {
            this.role = role;
            try {
                expiryTime = Calendar.getInstance().getTimeInMillis() +
                        config.getLong(ROLE_TIMEOUT, DEFAULT_ROLE_TIMEOUT);
            } catch (Exception ex) {
                log.warn("Failed to retrieve the role time out using defaults");
                expiryTime = Calendar.getInstance().getTimeInMillis() +
                        DEFAULT_ROLE_TIMEOUT;
            }
        }

        /**
         * This object returns the reference to the internal role.
         * 
         * @return The reference to the internal role.
         */
        public Role getRole() {
            return role;
        }

        /**
         * This method returns true if the role is expired.
         * 
         * @return The boolean value.
         */
        public synchronized boolean isExpired() {
            return Calendar.getInstance().getTimeInMillis() > expiryTime;
        }
    }
    // the classes constant static variables
    private static final String ROLE_HANDLERS = "role_handlers";
    private static final String ROLE_TIMEOUT = "role_timeout";
    private static final long DEFAULT_ROLE_TIMEOUT = 60 * 1000;
    // The role manager logger
    protected static Logger log =
            Logger.getLogger(RoleManager.class.getName());
    // the class imports
    private static RoleManager singleton = null;
    private List roleHandlers = null;
    private Configuration config = null;
    private Map roles = new HashMap();

    /** 
     * Creates a new instance of the RoleManager.
     *
     * @exception SecurityException
     */
    private RoleManager() throws SecurityException {
        roleHandlers = new ArrayList();

        try {
            config = ConfigurationFactory.getInstance().getConfig(
                    RoleManager.class);
            StringTokenizer roleHandlerList = new StringTokenizer(
                    config.getString(ROLE_HANDLERS), ",");
            while (roleHandlerList.hasMoreTokens()) {
                String roleHandler = roleHandlerList.nextToken().trim();
                log.info("Init the role handler [" + roleHandler + "]");
                try {
                    roleHandlers.add(
                            Class.forName(roleHandler).
                            newInstance());
                } catch (Exception ex) {
                    log.error("Failed to load the handler [" + roleHandler + "]");
                }
            }
        } catch (Exception ex) {
            throw new SecurityException(
                    "Failed to load the configuration role handler list : " +
                    ex.getMessage(), ex);
        }
    }

    /**
     * The method that retrieves the reference to the role manager singleton.
     *
     * @return The reference to the role manager singleton.
     * @exception SecurityException
     */
    public static synchronized RoleManager getInstance()
            throws SecurityException {
        if (singleton != null) {
            return singleton;
        }
        singleton = new RoleManager();
        return singleton;
    }

    /**
     * This method is called to start the role managers background thread.
     */
    public void startBackgroundThread() {
    }

    /**
     * This method is called to terminate the background threads processing. It
     * will only succedded if the coadunation instance has been terminated.
     */
    public void terminateBackgroundThread() {
    }

    /**
     * Retrieve the list of roles.
     *
     * @return The list of roles.
     */
    public synchronized Set getRoles() {
        Set roles = new HashSet();
        for (Iterator iter = roleHandlers.iterator(); iter.hasNext();) {
            RoleHandler handler = (RoleHandler) iter.next();
            try {
                Map updatedRoles = handler.getRoles();
                for (Iterator roleIter = updatedRoles.keySet().iterator();
                        roleIter.hasNext();) {
                    String role = (String) roleIter.next();
                    if (!roles.contains(role)) {
                        roles.add(role);
                    }
                }
            } catch (Exception ex) {
                log.info("Failed to retrieve the role information from : " +
                        ex.getMessage(), ex);
            }
        }
        return roles;
    }

    /**
     * Retrieve the role with the matching name.
     *
     * @return The role object.
     * @exception SecurityException
     */
    public Role getRole(String role) throws SecurityException {
        synchronized (roles) {
            if (roles.containsKey(role)) {
                RoleCache cache = (RoleCache) roles.get(role);
            }
        }
        for (Iterator iter = roleHandlers.iterator(); iter.hasNext();) {
            RoleHandler handler = (RoleHandler) iter.next();
            try {
                Role ref = handler.getRole(role);
                if (ref != null) {
                    synchronized (roles) {
                        roles.put(role, new RoleCache(ref));
                    }
                    return ref;
                }
            } catch (Exception ex) {
            // ignore the error and continue assume the role will be supplied
            // by another object.
            }
        }
        throw new SecurityException("The role [" + role + "] could not be found");
    }
}
