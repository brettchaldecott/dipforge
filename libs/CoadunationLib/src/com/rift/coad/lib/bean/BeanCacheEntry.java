/*
 * MessageService: The message service daemon
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
 * BeanCacheEntry.java
 */

// package paths
package com.rift.coad.lib.bean;

// java imports
import java.util.Date;

// coadunation imports
import com.rift.coad.lib.cache.CacheEntry;

/**
 * The bean cache entry is responsible for
 *
 * @author Brett Chaldecott
 */
public class BeanCacheEntry {
    // the classes private member variables
    private long timeout = 0;
    private Object cacheKey = null;
    private Object wrappedObject = null;
    private CacheEntry cacheEntry = null;
    private Object proxy = null;
    private CacheEntry beanHandler = null;
    
    
    /**
     * The constructor of the BeanCacheEntry object.
     *
     * @param timeout The time out value.
     * @param cacheKey The cache key that uniqly identifies this object.
     * @param wrapperObject The wrapper object reference.
     * @param cacheEntry The new entry to add to the cache.
     */
    public BeanCacheEntry(long timeout, Object cacheKey, Object wrappedObject,
            CacheEntry cacheEntry) {
        this.timeout = timeout;
        this.cacheKey = cacheKey;
        this.wrappedObject = wrappedObject;
        this.cacheEntry = cacheEntry;
    }
    
    
    /**
     * The constructor of the BeanCacheEntry object.
     *
     * @param timeout The time out period.
     * @param cacheKey The cache key that uniqly identifies this object.
     * @param proxy The proxy object.
     * @param beanHandler The handler to perform the search for.
     */
    public BeanCacheEntry(long timeout,Object cacheKey, Object wrappedObject,
            Object proxy, CacheEntry beanHandler) {
        this.timeout = timeout;
        this.cacheKey = cacheKey;
        this.wrappedObject = wrappedObject;
        this.proxy = proxy;
        this.beanHandler = beanHandler;
    }
    
    
    /**
     * This method returns the cache key object.
     *
     * @return The cache key object.
     */
    public Object getCacheKey() {
        return cacheKey;
    }
    
    
    /**
     * This method returns the wrapped object.
     *
     * @return The wrapped object.
     */
    public Object getWrappedObject() {
        return wrappedObject;
    }
    
    
    /**
     * This method returns the cache entry that this object wrapps.
     */
    public CacheEntry getCacheEntry() {
        return cacheEntry;
    }
    
    
    /**
     * This method sets the cache entry.
     *
     * @param cacheEntry The new cache entry to set.
     */
    public void setCacheEntry(CacheEntry cacheEntry) {
        this.cacheEntry = cacheEntry;
    }
    
    
    /**
     * This methos returns the proxy object.
     *
     * @return The proxy object.
     */
    public Object getProxy() {
        return proxy;
    }
    
    
    /**
     * This method sets the proxy object.
     *
     * @param proxy The proxy object value to set.
     */
    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
    
    
    /**
     * This method retrieves the bean handler object.
     *
     * @return The bean handler reference.
     */
    public CacheEntry getBeanHandler() {
        return beanHandler;
    }
    
    
    /**
     * This method sets the bean handler flag.
     *
     * @param beanHandler The bean handler to set.
     */
    public void setBeanHandler(CacheEntry beanHandler) {
        this.beanHandler = beanHandler;
    }
    
    
    /**
     * This method sets the last touch time
     */
    public void touch() {
        if (cacheEntry != null){
            cacheEntry.touch();
        }
        if (beanHandler != null) {
            beanHandler.touch();
        }
    }
    
    
    /**
     * This method returns true if this object is expired.
     *
     * @return TRUE if expired, FALSE if not.
     * @param expiryDate The expiry date.
     */
    public boolean isExpired(Date expiryDate) {
        Date calculatedExpiryTime = new Date(expiryDate.getTime() - timeout);
        if ((((cacheEntry != null) &&
                (cacheEntry.isExpired(calculatedExpiryTime))) ||
                (cacheEntry == null)) && ( (beanHandler == null) ||
                ((beanHandler != null) &&
                (beanHandler.isExpired(calculatedExpiryTime))) ) )  {
            return true;
        }
        return false;
    }
    
    
    /**
     * Call the cache entries to release
     */
    public void cacheRelease() {
        if (cacheEntry != null){
            cacheEntry.cacheRelease();
        }
        if (beanHandler != null) {
            beanHandler.cacheRelease();
        }
    }
}
