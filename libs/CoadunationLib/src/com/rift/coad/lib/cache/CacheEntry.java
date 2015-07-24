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
 * CacheEntry.java
 *
 * This is a basic generic cache entry interface. It can be used by classes
 * needing to be placed in a cache.
 */

// package path
package com.rift.coad.lib.cache;

// java imports
import java.util.Date;


/**
 * This is a basic generic cache entry interface. It can be used by classes
 * needing to be placed in a cache.
 *
 * @author Brett Chaldecott
 */
public interface CacheEntry {
    /**
     * The touch method use to update the last touch time of a cache entry.
     */
    public void touch();
    
    
    /**
     * This method will return true if the date is older than the given expiry
     * date.
     *
     * @return TRUE if expired FALSE if not.
     * @param expiryDate The expiry date to perform the check with. 
     */
    public boolean isExpired(Date expiryDate);
    
    
    /**
     * This method is called by the cache when an object is removed.
     */
    public void cacheRelease();
}
