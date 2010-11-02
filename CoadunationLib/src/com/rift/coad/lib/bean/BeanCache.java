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
 * BeanCache.java
 *
 * This object is responsible for mantaining a cache of beans. It is used by
 * both the RMI tie classes and the Local proxy objects when a method that
 * contains, find|get and remove|delete is found.
 */

// package path
package com.rift.coad.lib.bean;

// java imports
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import javax.rmi.PortableRemoteObject;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This object is responsible for mantaining a cache of beans. It is used by
 * both the RMI tie classes and the Local proxy objects when a method that
 * contains, find|get and remove|delete is found.
 *
 * @author Brett Chaldecott
 */
public class BeanCache implements Cache {
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "bean_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 30 * 60 * 1000;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(BeanCache.class.getName());
    
    // private member variables
    private Map entries = new HashMap();
    private long defaultCacheExpiryTime = 0;
    private ThreadStateMonitor status = new ThreadStateMonitor();
    
    /**
     * Creates a new instance of BeanCache
     *
     * @exception BeanException
     */
    public BeanCache() throws BeanException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(BeanCache.class);
            defaultCacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the BeanCache object because : " + 
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to start the BeanCache object because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        // copy the entries map
        Map entries = new HashMap();
        synchronized (this.entries) {
            entries.putAll(this.entries);
        }
        
        // loop through the entires and remove the expired ones
        Date expiryDate = new Date();
        for (Iterator iter = entries.keySet().iterator();iter.hasNext();) {
            Object cacheKey = iter.next();
            BeanCacheEntry beanCacheEntry = 
                    (BeanCacheEntry)entries.get(cacheKey);
            if (beanCacheEntry.isExpired(expiryDate)) {
                synchronized (this.entries) {
                    if (beanCacheEntry.getCacheEntry() != null)
                    {
                        try {
                            PortableRemoteObject.unexportObject(
                                    (java.rmi.Remote)
                                    beanCacheEntry.getCacheEntry());
                            // remove from cache
                            this.entries.remove(cacheKey);
                            beanCacheEntry.cacheRelease();
                        } catch (java.rmi.NoSuchObjectException ex) {
                            log.warn("The object was never exported : " + 
                                    ex.getMessage(),ex);
                            // remove from cache
                            this.entries.remove(cacheKey);
                            beanCacheEntry.cacheRelease();
                        } catch (Exception ex) {
                            log.error("Failed to un-export this object : " + 
                                    ex.getMessage(),ex);
                        }
                    } else {
                        // if this object has not cache entry as in no rmi tie
                        // class than just remove it.
                        this.entries.remove(cacheKey);
                        beanCacheEntry.cacheRelease();
                    }
                }
            }
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        // copy the entries map
        Map entries = new HashMap();
        synchronized (this.entries) {
            entries.putAll(this.entries);
            this.entries.clear();
            status.terminate(false);
        }
        
        // loop through the entires and remove the expired ones
        for (Iterator iter = entries.keySet().iterator();iter.hasNext();) {
            Object cacheKey = iter.next();
            BeanCacheEntry beanCacheEntry = 
                    (BeanCacheEntry)entries.get(cacheKey);
            if (beanCacheEntry.getCacheEntry() != null)
            {
                try {
                    PortableRemoteObject.unexportObject(
                            (java.rmi.Remote)
                            beanCacheEntry.getCacheEntry());
                } catch (java.rmi.NoSuchObjectException ex) {
                    log.warn("The cache object was not bound : " + 
                            ex.getMessage(),ex);
                } catch (Exception ex) {
                    log.error("Failed to un-export the cached object : " + 
                            ex.getMessage(),ex);
                }
            }
            beanCacheEntry.cacheRelease();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheKey The entry to perform the check for.
     */
    public boolean contains(Object cacheKey) {
        synchronized (entries) {
            return entries.containsKey(cacheKey);
        }
    }
    
    
    /**
     * This method returns the bean cache entry for manipulation by the caller.
     *
     * @return A reference to the bean cache object.
     * @param cacheEntry The cache entry to retrieve.
     */
    public BeanCacheEntry getEntry(Object cacheKey) throws BeanException {
        synchronized (entries) {
            checkStatus();
            BeanCacheEntry beanCacheEntry = 
                    (BeanCacheEntry)entries.get(cacheKey);
            if (beanCacheEntry != null) {
                beanCacheEntry.touch();
            }
            return beanCacheEntry;
        }
    }
    
    
    /**
     * This method adds the entry to the cache.
     *
     * @param cacheKey The key to identify this entry by.
     * @param wrappedObject The object wrapped by the cache entry.
     * @param entry An entry in the cache
     */
    public void addCacheEntry(long timeout,Object cacheKey, Object wrappedObject, 
            CacheEntry entry) throws BeanException {
        synchronized(entries) {
            checkStatus();
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            // remove the original entry if there is one.
            BeanCacheEntry beanCacheEntry = getEntry(cacheKey);
            if (beanCacheEntry != null) {
                try {
                  PortableRemoteObject.unexportObject(
                            (java.rmi.Remote)
                            beanCacheEntry.getCacheEntry());
                } catch (java.rmi.NoSuchObjectException ex) {
                    log.warn("The cache object was not bound : " + 
                            ex.getMessage(),ex);
                }
            }
            entries.put(cacheKey,new BeanCacheEntry(cacheTimeout,cacheKey, 
                    wrappedObject, entry));
        }
    }
    
    
    /**
     * This method adds a new entry to the cache.
     *
     * @param cacheKey The key to identify this entry by.
     * @param wrappedObject The object wrapped by the proxy.
     * @param proxy The proxy to add.
     * @param handle The handler for the bean proxy object.
     */
    public void addCacheEntry(long timeout, Object cacheKey, Object wrappedObject, 
            Object proxy, CacheEntry handle) throws BeanException {
        synchronized (entries) {
            checkStatus();
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            // remove the original entry if there is one.
            BeanCacheEntry beanCacheEntry = getEntry(cacheKey);
            if (beanCacheEntry != null) {
                try {
                  PortableRemoteObject.unexportObject(
                            (java.rmi.Remote)
                            beanCacheEntry.getCacheEntry());
                } catch (java.rmi.NoSuchObjectException ex) {
                    log.warn("The cache object was not bound : " + 
                            ex.getMessage(),ex);
                }
            }
            entries.put(cacheKey,new BeanCacheEntry(cacheTimeout,cacheKey, 
                    wrappedObject, proxy, handle));
        }
    }
    
    
    /**
     * This method removes the entry from the cache based on the key passed in.
     *
     * @param cacheKey The key in the cache to remove.
     */
    public void removeCacheEntry(Object cacheKey) throws BeanException {
        synchronized (entries) {
            checkStatus();
            entries.remove(cacheKey);
        }
    }
    
    
    /**
     * This method check the bean cache status. 
     */
    private void checkStatus() throws BeanException {
        if (status.isTerminated()) {
            throw new BeanException("Bean cache has been terminated.");
        }
    }
}
