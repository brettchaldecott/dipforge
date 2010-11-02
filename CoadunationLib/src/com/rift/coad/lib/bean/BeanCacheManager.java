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
 * BeanCacheManager.java
 *
 * This object is responsible for managing the bean cache entries.
 */

// package path
package com.rift.coad.lib.bean;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

// coadunation imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This object is responsible for managing the bean cache entries.
 *
 * @author Brett Chaldecott
 */
public class BeanCacheManager implements Cache {
    
    // the classes private member variables
    private Map beanCaches = new HashMap();
    private ThreadStateMonitor status = new ThreadStateMonitor();
    
    /** 
     * Creates a new instance of BeanCacheManager
     */
    public BeanCacheManager() {
        
    }
    
    
    /**
     * This method returns the reference to the bean cache.
     *
     * @return The reference to the bean cache.
     * @param ref The reference to the bean cache.
     * @exception BeanException
     */
    public BeanCache getBeanCache(Object ref) throws BeanException {
        checkStatus();
        synchronized (beanCaches) {
            if (!beanCaches.containsKey(ref)) {
                beanCaches.put(ref,new BeanCache());
            }
            return (BeanCache)beanCaches.get(ref);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map beanCaches = new HashMap();
        synchronized (this.beanCaches) {
            beanCaches.putAll(this.beanCaches);
        }
        for (Iterator iter = beanCaches.keySet().iterator(); iter.hasNext();) {
            BeanCache beanCache = (BeanCache)beanCaches.get(iter.next());
            beanCache.garbageCollect();
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        status.terminate(true);
        Map beanCaches = new HashMap();
        synchronized (this.beanCaches) {
            beanCaches.putAll(this.beanCaches);
            this.beanCaches.clear();
        }
        for (Iterator iter = beanCaches.keySet().iterator(); iter.hasNext();) {
            BeanCache beanCache = (BeanCache)beanCaches.get(iter.next());
            beanCache.clear();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        synchronized (beanCaches) {
            return beanCaches.containsKey(cacheEntry);
        }
    }
    
    
    /**
     * This method checks the status of the bean cache manager.
     *
     * @exception BeanException
     */
    private void checkStatus() throws BeanException {
        if (status.isTerminated()) {
            throw new BeanException("Bean cache manager has been terminated.");
        }
    }
}
