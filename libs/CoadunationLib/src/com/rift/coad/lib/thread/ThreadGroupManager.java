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
 * ThreadGroupManager.java
 *
 * This object is responsible for managing the thread groups within Coadunation.
 */

// package path
package com.rift.coad.lib.thread;

// java imports
import java.util.Map;
import java.util.HashMap;

// logging import
import org.apache.log4j.Logger;


// coadunation imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.Validator;

/**
 * This object is responsible for managing the thread groups within Coadunation.
 *
 * @author Brett Chaldecott
 */
public class ThreadGroupManager {
    
    /**
     * The class loader thread group.
     */
    public class LoaderThreadGroupManager {
        // private member variables
        private ThreadGroup threadGroup = null;
        private CoadunationThreadGroup coadThreadGroup = null;
        
        /**
         * The class loader thread group manager
         */
        public LoaderThreadGroupManager(CoadunationThreadGroup coadThreadGroup) 
                throws ThreadException {
            try {
                threadGroup = new ThreadGroup(
                    RandomGuid.getInstance().getGuid());
                this.coadThreadGroup = coadThreadGroup.createThreadGroup();
            } catch (Exception ex) {
                throw new ThreadException("Failed to create the " +
                        "LoaderThreadGroupManager : " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns the JAVA thread group.
         */
        public ThreadGroup getThreadGroup() {
            return threadGroup;
        }
        
        
        /**
         * This method returns the reference to the coad thread group.
         */
        public CoadunationThreadGroup getCoadThreadGroup() {
            return coadThreadGroup;
        }
        
        
        /**
         * This method terminates the thread groups
         */
        public void terminate() {
            try {
                coadThreadGroup.terminate();
                if (threadGroup.activeCount() > 0) {
                    log.warn("There may still be threads in daemon that has " +
                            "undeployed.");
                }
                try {
                    threadGroup.destroy();
                } catch (Exception ex) {
                    log.debug("Failed to destroy the thread group " + 
                        ex.getMessage(),ex);
                }
            } catch (Exception ex) {
                log.error("Failed to terminate the thread group cleanly " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    // class constants
    private final static String ROLE = "role";
    
    // the classes singletons
    private static ThreadGroupManager threadGroupManager = null;
    protected static Logger log =
        Logger.getLogger(ThreadGroupManager.class.getName());
    
    // classes private member variables
    private Map managedGroups = new HashMap();
    
    // static member variables
    private static String role = null;
    
    // setup the role
    static {
        try {
            Configuration configuration = 
                    ConfigurationFactory.getInstance().getConfig(
                    ThreadGroupManager.class);
            role = configuration.getString(ROLE);
        } catch (Exception ex) {
            log.error("Failed to retrieve the thread group manager role : " 
                    + ex.getMessage(),ex);
            throw new RuntimeException(
                    "Failed to retrieve the thread group manager role : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /** Creates a new instance of ThreadGroupManager */
    private ThreadGroupManager() {
    }
    
    
    /**
     * This method is responsible for getting and instance of the thread group
     * manager.
     *
     * @return The reference to the in memory thread group.
     */
    public static synchronized ThreadGroupManager getInstance() {
        if (threadGroupManager == null) {
            threadGroupManager = new ThreadGroupManager();
        }
        return threadGroupManager;
    }
    
    
    /**
     * This method inits the thread group for the given class loader.
     *
     * @param coadThreadGroup The thread group to start.
     */
    public void initThreadGroup (CoadunationThreadGroup coadThreadGroup) throws
            ThreadException {
        LoaderThreadGroupManager loaderThreadGroupManager = new
                LoaderThreadGroupManager(coadThreadGroup);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        synchronized(managedGroups) {
            managedGroups.put(classLoader,loaderThreadGroupManager);
        }
    }
    
    
    /**
     * This method returns the reference to the thread group
     */
    public ThreadGroup getThreadGroup() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        LoaderThreadGroupManager loaderThreadGroupManager = null;
        synchronized(managedGroups) {
            if (!managedGroups.containsKey(classLoader)) {
                return null;
            }
            loaderThreadGroupManager = (LoaderThreadGroupManager)managedGroups.
                    get(classLoader);
        }
        return loaderThreadGroupManager.getThreadGroup();
    }
    
    
    /**
     * This method returns the reference to the thread group
     */
    public void terminateThreadGroup() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        LoaderThreadGroupManager loaderThreadGroupManager = null;
        synchronized(managedGroups) {
            if (!managedGroups.containsKey(classLoader)) {
                return;
            }
            loaderThreadGroupManager = (LoaderThreadGroupManager)managedGroups.
                    get(classLoader);
            managedGroups.remove(classLoader);
        }
        loaderThreadGroupManager.terminate();
    }
    
    
    /**
     * This method returns the reference to the thread group
     */
    public void addThreadToGroup(BasicThread thread, String username) throws 
            ThreadException,Exception {
        Validator.validate(ThreadGroupManager.class,role);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        LoaderThreadGroupManager loaderThreadGroupManager = null;
        synchronized(managedGroups) {
            if (!managedGroups.containsKey(classLoader)) {
                return;
            }
            loaderThreadGroupManager = (LoaderThreadGroupManager)managedGroups.
                    get(classLoader);
        }
        loaderThreadGroupManager.getCoadThreadGroup().addThread(thread,
                username);
    }
}
