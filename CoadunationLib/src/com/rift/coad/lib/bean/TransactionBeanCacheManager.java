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
 * This object is responsible for managing the transaction bean cache entries.
 *
 * @author Brett Chaldecott
 */
public class TransactionBeanCacheManager implements Cache {
    
    // the classes private member variables
    private Map transactionBeanCaches = new HashMap();
    private ThreadStateMonitor status = new ThreadStateMonitor();
    
    /** 
     * Creates a new instance of BeanCacheManager
     */
    public TransactionBeanCacheManager() {
        
    }
    
    
    /**
     * This method returns the reference to the bean cache.
     *
     * @return The reference to the bean cache.
     * @param ref The reference to the bean cache.
     * @exception BeanException
     */
    public TransactionBeanCache getBeanCache(Object ref) throws BeanException {
        checkStatus();
        synchronized (transactionBeanCaches) {
            if (!transactionBeanCaches.containsKey(ref)) {
                transactionBeanCaches.put(ref,new TransactionBeanCache());
            }
            return (TransactionBeanCache)transactionBeanCaches.get(ref);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map transactionBeanCaches = new HashMap();
        synchronized (this.transactionBeanCaches) {
            transactionBeanCaches.putAll(this.transactionBeanCaches);
        }
        for (Iterator iter = transactionBeanCaches.keySet().iterator(); 
                iter.hasNext();) {
            TransactionBeanCache transactionBeanCache = 
                    (TransactionBeanCache)transactionBeanCaches.get(
                    iter.next());
            transactionBeanCache.garbageCollect();
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        status.terminate(true);
        Map transactionBeanCaches = new HashMap();
        synchronized (this.transactionBeanCaches) {
            transactionBeanCaches.putAll(this.transactionBeanCaches);
            this.transactionBeanCaches.clear();
        }
        for (Iterator iter = transactionBeanCaches.keySet().iterator(); 
                iter.hasNext();) {
            TransactionBeanCache transactionBeanCache = 
                    (TransactionBeanCache)transactionBeanCaches.get(
                    iter.next());
            transactionBeanCache.clear();
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        synchronized (transactionBeanCaches) {
            return transactionBeanCaches.containsKey(cacheEntry);
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
