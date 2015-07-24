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
 * CacheRegistry.java
 *
 * The cache registery is responsible for managing the cache information for
 * the deployments. Each deployment gets its own class loader.
 */

// package path
package com.rift.coad.lib.cache;

// java imports
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * The cache registery is responsible for managing the cache information for
 * the deployments. Each deployment gets its own class loader and own cache
 * entry.
 *
 * @author Brett Chaldecott
 */
public class CacheRegistry {
    
    /**
     * This object is responsible for managing a list of cache entries.
     */
    public class CacheListManager extends BasicThread {
        
        // private member variables
        private ClassLoader loader = null;
        private Map caches = new HashMap();
        private ThreadStateMonitor threadStateMonitor = null;
        
        
        /**
         * The constructor of the CacheListManager
         *
         * @param loader The reference to the class loader.
         */
        public CacheListManager(ClassLoader loader) throws Exception {
            this.loader = loader;
            threadStateMonitor  = new ThreadStateMonitor(delay);
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            while(!threadStateMonitor.isTerminated()) {
                threadStateMonitor.monitor();
                Set keySet = new HashSet();
                synchronized (caches) {
                    keySet.addAll(caches.keySet());
                }
                Iterator iter = keySet.iterator();
                while(iter.hasNext()) {
                    Object key = iter.next();
                    Cache cache = null;
                    synchronized (caches) {
                        cache = (Cache)caches.get(key);
                    }
                    try {
                        cache.garbageCollect();
                    } catch (Exception ex) {
                        log.error("Failed to clear the garbage collect : " + 
                                ex.getMessage(),ex);
                    }
                    if (threadStateMonitor.isTerminated())
                    {
                        break;
                    }
                }
            }
            
            // clean up the cache
            Iterator iter = caches.keySet().iterator();
            while(iter.hasNext()) {
                Object key = iter.next();
                Cache cache = (Cache)caches.get(key);
                try {
                    cache.clear();
                } catch (Exception ex) {
                    log.error("Failed to clear the cache : " + 
                            ex.getMessage(),ex);
                }
            }
        }
        
        
        /**
         * This method terminates the processing being performed by the cache
         * list manager object.
         */
        public void terminate() {
            threadStateMonitor.terminate(false);
        }
        
        
        /**
         * This method retrieves a cache entry.
         *
         * @return The cache entry to return.
         * @param cacheClass The cache class being requested.
         * @exception CacheException
         */
        public Cache getCache(Class cacheClass) throws CacheException {
            if (threadStateMonitor.isTerminated()) {
                throw new CacheException(
                        "The cache has been closed cannot add anymore entries.");
            }
            // synchronize access to the caches
            try
            {
                synchronized (caches) {
                    if (caches.containsKey(cacheClass)) {
                        return (Cache)caches.get(cacheClass);
                    }
                    Cache cache = (Cache)cacheClass.newInstance();
                    caches.put(cacheClass,cache);
                    return cache;
                }
            } catch (Exception ex) {
                
                throw new CacheException("Failed to retrieve the cache entry");
            }
        }
    }
    
    // the classes constants
    private static final String DELAY = "cache_process_delay";
    private static final long DELAY_DEFAULT = 500;
    private static final String USER = "cache_user";
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(CacheRegistry.class.getName());
    
    // class member variables
    private static CacheRegistry singleton = null;
    private CoadunationThreadGroup threadGroup = null;
    private String threadUser = null;
    private Map cacheManagers = new HashMap();
    private Configuration config = null;
    private long delay = 0;
    private ThreadStateMonitor stateMonitor = new ThreadStateMonitor();
    
    
    /** 
     * Creates a new instance of CacheRegistry 
     *
     * @param threadGroup The thread group used to greate a sub thread group.
     * @exception CacheException
     */
    private CacheRegistry(CoadunationThreadGroup threadGroup) throws CacheException {
        try {
            this.threadGroup = threadGroup.createThreadGroup();
            config = ConfigurationFactory.
                    getInstance().getConfig(CacheRegistry.class);
            delay = config.getLong(DELAY,DELAY_DEFAULT);
            threadUser = config.getString(USER);
        } catch (Exception ex) {
            log.error("Failed to instanciate the cache registry : " + 
                    ex.getMessage(),ex);
            throw new CacheException("Failed to instanciate the cache registry : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns an instance of the cache registry object. Do not call
     * more than once.
     *
     * @return The reference to the cache registry object.
     * @param threadGroup The thread group used for initialization purposes.
     * @exception CacheException
     */
    public static synchronized CacheRegistry init(CoadunationThreadGroup threadGroup) throws 
            CacheException {
        // this method overrides the existing cache registry
        if (singleton == null) {
            singleton = new CacheRegistry(threadGroup);
        }
        return singleton;
    }
    
    
    /**
     * This method returns an instance of the cache registry object.
     *
     * @return The reference to the cache registry object.
     * @exception CacheException
     */
    public static synchronized CacheRegistry getInstance() throws CacheException {
        if (singleton == null) {
            throw new CacheException(
                    "The cache registry has not been initialized");
        }
        return singleton;
    }
    
    
    /**
     * This method inits the cache for a given class loader. This is based on
     * the class loader that attached to the calling thread.
     */
    public void initCache() throws CacheException {
        if (stateMonitor.isTerminated()) {
            throw new CacheException("Cache is terminated");
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        CacheListManager cacheListManager = null;
        synchronized (cacheManagers) {
            if (cacheManagers.containsKey(loader)) {
                throw new CacheException("There is already a cache entry for " +
                        "this loader");
            }
            try {
                cacheListManager = new CacheListManager(loader);
            } catch (Exception ex) {
                throw new CacheException (
                        "Failed to instanciate the cace list manager : " +
                        ex.getMessage(),ex);
            }
            cacheManagers.put(loader,cacheListManager);
        }
        try {
            threadGroup.addThread(cacheListManager,threadUser);
            cacheListManager.start();
            cacheListManager.setContextClassLoader(loader);
        } catch (Exception ex) {
            // if fails to start remove cache entry
            synchronized (cacheManagers) {
                cacheManagers.remove(loader);
            }
            log.error("Failed to start the cache list manager : " + 
                    ex.getMessage(),ex);
            throw new CacheException("Failed to init the cache : " + 
                    ex.getMessage(),ex);
        }

    }
    
    
    /**
     * This method inits the cache for a given class loader. This is based on
     * the class loader that attached to the calling thread.
     *
     * @return The reference to the requested cache class.
     * @param cacheClass The cache class to retrieve.
     * @exception CacheException
     */
    public Cache getCache(Class cacheClass) throws CacheException {
        if (stateMonitor.isTerminated()) {
            throw new CacheException("Cache is terminated");
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        CacheListManager cacheListManager = null;
        synchronized (cacheManagers) {
            if (!cacheManagers.containsKey(loader)) {
                throw new CacheException("The is no cache list manager for " +
                        "this loader");
            }
            cacheListManager = (CacheListManager)cacheManagers.get(loader);
        }
        return cacheListManager.getCache(cacheClass);
    }
    
    
    /**
     * This method terminates the cache for the class loader making the request.
     *
     * @exception CacheException
     */
    public void terminateCache() throws CacheException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        CacheListManager cacheListManager = null;
        synchronized (cacheManagers) {
            if (!cacheManagers.containsKey(loader)) {
                log.info("The is no cache list manager for this loader. " +
                        "The deploy was not completed successfully");
                return;
            }
            cacheListManager = (CacheListManager)cacheManagers.get(loader);
            cacheManagers.remove(loader);
        }
        cacheListManager.terminate();
    }
    
    
    /**
     * This method shuts down the cache registry
     *
     * @exception CacheException
     */
    public void shutdown() throws CacheException {
        stateMonitor.terminate(false);
        try {
            threadGroup.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the cache thread managers : " + 
                    ex.getMessage(),ex);
        }
        synchronized(cacheManagers) {
            cacheManagers.clear();
        }
    }
}
