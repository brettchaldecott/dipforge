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
 * Cache.java
 *
 * The cache interface supplies a standard set of cache maintainance methods. It
 * does not enable a user to access the cache entries directly.
 */

package com.rift.coad.lib.cache;

/**
 * The cache interface supplies a standard set of cache maintainance methods. It
 * does not enable a user to access the cache entries directly.
 *
 * @author Brett Chaldecott
 */
public interface Cache {
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect();
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear();
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry);
}
