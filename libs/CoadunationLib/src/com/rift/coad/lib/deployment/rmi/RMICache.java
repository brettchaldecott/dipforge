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
 * XMLConfigurationException.java
 *
 * RMICache.java
 *
 * This class is responsible for temporarily caching of rmi factory objects.
 * They will be cache until they have not been touched for a given period of
 * time.
 */

// package path
package com.rift.coad.lib.deployment.rmi;

// java imports
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.rmi.PortableRemoteObject;

// logging import
import org.apache.log4j.Logger;

// coad imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This class is responsible for temporarily caching of rmi factory objects.
 * They will be cache until they have not been touched for a given period of
 * time.
 *
 * @author Brett Chaldecott
 */
public class RMICache implements Cache {
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "rmi_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 60 * 1000;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(RMICache.class.getName());
    
    // private member variables
    private Map cacheEntries = new HashMap();
    private long defaultCacheExpiryTime = 0;
    private ThreadStateMonitor status = new ThreadStateMonitor();
    
    
    /** 
     * Creates a new instance of RMICache
     *
     * @exception RMIException
     */
    public RMICache() throws RMIException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(RMICache.class);
            defaultCacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the RMICache object because : " + 
                    ex.getMessage(),ex);
            throw new RMIException(
                    "Failed to start the RMICache object because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map currentEntryList = new HashMap();
        synchronized (cacheEntries) {
            currentEntryList.putAll(cacheEntries);
        }
        Date expiryDate = new Date();
        for (Iterator iter = currentEntryList.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            RMICacheEntry cacheEntry = (RMICacheEntry)currentEntryList.get(key);
            if (cacheEntry.isExpired(expiryDate)) {
                try {
                    PortableRemoteObject.unexportObject(
                            cacheEntry.getRemoteInterface());
                    synchronized(cacheEntries) {
                        cacheEntries.remove(key);
                    }
                    cacheEntry.cacheRelease();
                } catch (java.rmi.NoSuchObjectException ex) {
                    log.warn("The object was never exported : " + 
                            ex.getMessage(),ex);
                    // remove from cache
                    synchronized(cacheEntries) {
                        cacheEntries.remove(key);
                    }
                    cacheEntry.cacheRelease();
                } catch (Exception ex) {
                    log.error("Failed to remove a cache entry because : " + 
                            ex.getMessage(),ex);
                }
            }
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        status.terminate(false);
        Map currentEntryList = new HashMap();
        synchronized (cacheEntries) {
            currentEntryList.putAll(cacheEntries);
            cacheEntries.clear();
        }
        for (Iterator iter = currentEntryList.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            RMICacheEntry cacheEntry = (RMICacheEntry)currentEntryList.get(key);
            try {
                PortableRemoteObject.unexportObject(
                        cacheEntry.getRemoteInterface());
            } catch (java.rmi.NoSuchObjectException ex) {
                log.warn("The object was never exported : " + 
                        ex.getMessage(),ex);
            } catch (Exception ex) {
                log.error("Failed to remove a cache entry because : " + 
                        ex.getMessage(),ex);
            }
            cacheEntry.cacheRelease();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        synchronized(cacheEntries) {
            return cacheEntries.containsKey(cacheEntry);
        }
    }
    
    
    /**
     * This method is responsible for adding an entry to the cache.
     *
     * @param entry The entry to add to the cache.
     */
    public void addCacheEntry(long timeout, CacheEntry entry) throws RMIException {
        synchronized(cacheEntries) {
            checkStatus();
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            cacheEntries.put(entry,new RMICacheEntry(cacheTimeout,entry));
        }
    }
    
    /**
     * This method will check to see if the cache has been terminated or not.
     *
     * @exception BeanException
     */
    private void checkStatus() throws RMIException {
        if (status.isTerminated()) {
            throw new RMIException("The RMI cache has been shut down.");
        }
    }
}
