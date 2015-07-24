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
 * KeySyncCacheManager.java
 *
 * This class is responsible for managing the the key sync caches.
 */

// package path
package com.rift.coad.lib.cache;

// java imports
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

// coadunation imports
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This class is responsible for managing the the key sync caches.
 *
 * @author Brett Chaldecott
 */
public class KeySyncCacheManager implements Cache {
    
    // private member variables
    private ThreadStateMonitor status = new ThreadStateMonitor();
    private Map keySyncCaches = new HashMap();
    
    /** 
     * Creates a new instance of KeySyncCacheManager 
     */
    public KeySyncCacheManager() {
    }
    
    
    /**
     * This method returns the key synch cache for the given key.
     *
     * @return KeySyncCache The cache object for the given identifier.
     * @param identifier The identifier to look up.
     * @exception CacheException
     */
    public KeySyncCache getKeySyncCache(Object identifier) throws CacheException {
        checkStatus();
        synchronized (keySyncCaches) {
            if (!keySyncCaches.containsKey(identifier)) {
                keySyncCaches.put(identifier,new KeySyncCache());
            }
            return (KeySyncCache)keySyncCaches.get(identifier);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map keySyncCaches = new HashMap();
        synchronized (this.keySyncCaches) {
            keySyncCaches.putAll(this.keySyncCaches);
        }
        for (Iterator iter = keySyncCaches.keySet().iterator(); iter.hasNext();) {
            KeySyncCache keySyncCache = (KeySyncCache)keySyncCaches.get(
                    iter.next());
            keySyncCache.garbageCollect();
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        status.terminate(false);
        Map keySyncCaches = new HashMap();
        synchronized (this.keySyncCaches) {
            keySyncCaches.putAll(this.keySyncCaches);
            this.keySyncCaches.clear();
        }
        for (Iterator iter = keySyncCaches.keySet().iterator(); iter.hasNext();) {
            KeySyncCache keySyncCache = (KeySyncCache)keySyncCaches.get(
                    iter.next());
            keySyncCache.clear();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        synchronized (keySyncCaches) {
            return keySyncCaches.containsKey(cacheEntry);
        }
    }
    
    
    /**
     * This method checks the status of the cache.
     *
     * @exception CacheException
     */
    private void checkStatus() throws CacheException {
        if (status.isTerminated()) {
            throw new CacheException("The cache has been terminated.");
        }
    }
}
