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
 * KeySyncCache.java
 *
 * This object is responsible for returning synchronization objects based on a
 * key passed in.
 */

// package path
package com.rift.coad.lib.cache;

// java imports
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;


/**
 * This object is responsible for returning synchronization objects based on a
 * key passed in.
 *
 * @author Brett Chaldecott
 */
public class KeySyncCache implements Cache {
    
    /**
     * This object is used to synchronize access to a specific key value.
     */
    public static class KeySync {
        // private
        private Date touchTime = null;
        
        /**
         * The constructor of the key sync object.
         */
        public KeySync() {
            touch();
        }
        
        
        /**
         * This object returns true if this object is expiried.
         *
         * @return TRUE if expired FALSE if not.
         * @param expiryDate The date the time is older than for expiry to
         *          succeed.
         */
        public synchronized boolean isExpired(Date expiryDate) {
            return (touchTime.getTime() < expiryDate.getTime());
        }
        
        
        /**
         * This method updates the last touch time for this object.
         */
        public synchronized void touch() {
            touchTime = new Date();
        }
    }
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "key_sync_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 30 * 60 * 1000;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(KeySyncCache.class.getName());
            
    // private member variable
    private ThreadStateMonitor status = new ThreadStateMonitor();
    private Map keySyncMap = new HashMap();
    private Configuration config = null;
    private long cacheExpiryTime = 0;
    
    
    /** 
     * Creates a new instance of KeySyncCache 
     *
     * @exception CacheException
     */
    public KeySyncCache() throws CacheException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(KeySyncCache.class);
            cacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the KeySyncCache object because : " + 
                    ex.getMessage(),ex);
            throw new CacheException(
                    "Failed to start the KeySyncCache object because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the key sync object for the requested key.
     *
     * @return The reference to the key stink object to return.
     * @param key The key to retrieve.
     */
    public KeySync getKeySync(Object key) throws CacheException {
        checkStatus();
        synchronized(keySyncMap) {
            if (!keySyncMap.containsKey(key)) {
                keySyncMap.put(key,new KeySync());
            }
            KeySync keySync = (KeySync)keySyncMap.get(key);
            keySync.touch();
            return keySync;
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map keySyncMap = new HashMap();
        synchronized(this.keySyncMap) {
            keySyncMap.putAll(this.keySyncMap);
        }
        Date expiryDate = new Date(new Date().getTime() - cacheExpiryTime);
        for (Iterator iter = keySyncMap.keySet().iterator();iter.hasNext();) {
            Object key = iter.next();
            KeySync keySync = (KeySync)keySyncMap.get(key);
            synchronized(this.keySyncMap) {
                if (keySync.isExpired(expiryDate)) {
                    this.keySyncMap.remove(key);
                }
            }
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        status.terminate(false);
        synchronized(this.keySyncMap) {
            this.keySyncMap.clear();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        synchronized (keySyncMap) {
            return keySyncMap.containsKey(cacheEntry);
        }
    }
    
    
    /**
     * This method checks the status of this cache and throws if it has been
     * terminated.
     *
     * @exception CacheException
     */
    private void checkStatus() throws CacheException {
        if (status.isTerminated()) {
            throw new CacheException("The cache has been terminated.");
        }
    }
    
}
