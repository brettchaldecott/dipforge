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
 * RMICacheEntry.java
 */

// package path
package com.rift.coad.lib.deployment.rmi;

// java imports
import java.util.Date;

// coad imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;


/**
 * This object is responsible for implementing the cache entry.
 *
 * @author Brett Chaldecott
 */
public class RMICacheEntry implements CacheEntry {
    // private member variables
    private long timeout = -1;
    private CacheEntry cacheEntry = null;
    
    
    /**
     * The constructor of the proxy cache object.
     *
     * @param timeout The timeout for this proxy object.
     * @param cacheEntry The cache entry object.
     */
    public RMICacheEntry(long timeout, CacheEntry cacheEntry) {
        this.timeout = timeout;
        this.cacheEntry = cacheEntry;
    }
    
    
    /**
     * The touch method
     */
    public void touch() {
        // do nothing
    }
    
    
    /**
     * This method will return true if the date is older than the given expiry
     * date.
     *
     * @return TRUE if expired FALSE if not.
     * @param expiryDate The expiry date to perform the check with.
     */
    public boolean isExpired(Date expiryDate) {
        // check if this object should never be expired
        if (timeout <= 0) {
            return false;
        }
        Date calculatedExpiry = new Date(expiryDate.getTime() - timeout);
        return cacheEntry.isExpired(calculatedExpiry);
    }
    
    
    /**
     * This method is called by the cache when an object is removed.
     */
    public void cacheRelease() {
        cacheEntry.cacheRelease();
    }
    
    
    /**
     * This method returns the cache entry
     */
    public CacheEntry getCacheEntry() {
        return cacheEntry;
    }
    
    
    /**
     * This method returns the remove interface reference.
     *
     * @return The reference to the remove interface.
     */
    public java.rmi.Remote getRemoteInterface() {
        return (java.rmi.Remote)cacheEntry;
    }
}