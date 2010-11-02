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
 * ProxyCache.java
 *
 * This object is responsible for caching the proxy entries. They will be
 * released after x number milli seconds of in activity. Forcing a reconnect
 * and the release of a resource.
 */

// the package path
package com.rift.coad.lib.bean;

// java imports
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This object is responsible for caching the proxy entries. They will be
 * released after x number milli seconds of in activity. Forcing a reconnect
 * and the release of a resource.
 *
 * @author Brett Chaldecott
 */
public class ProxyCache implements Cache {
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "proxy_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 60 * 1000;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(ProxyCache.class.getName());
    
    // private member variables
    private Map cacheEntries = new HashMap();
    private long defaultCacheExpiryTime = 0;
    private ThreadStateMonitor status = new ThreadStateMonitor();
    
    
    /**
     * Creates a new instance of ProxyCache
     *
     * @exception BeanException
     */
    public ProxyCache() throws BeanException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProxyCache.class);
            defaultCacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the ProxyCache object because : " + 
                    ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to start the ProxyCache object because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map cacheEntries = new HashMap();
        synchronized(this.cacheEntries) {
            cacheEntries.putAll(this.cacheEntries);
        }
        
        // the start time
        Date currentTime = new Date();
        for (Iterator iter = cacheEntries.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            ProxyCacheEntry cacheEntry = 
                    (ProxyCacheEntry)cacheEntries.get(key);
            if (cacheEntry.isExpired(currentTime)) {
                synchronized(this.cacheEntries) {
                    this.cacheEntries.remove(key);
                }
                cacheEntry.cacheRelease();
            }
        }
    }
    
    
    /**
     * This method is called to forcibly remove everything from the cache.
     */
    public void clear() {
        Map cacheEntries = new HashMap();
        synchronized(this.cacheEntries) {
            status.terminate(false);
            cacheEntries.putAll(this.cacheEntries);
            this.cacheEntries.clear();
        }
        
        for (Iterator iter = cacheEntries.keySet().iterator(); iter.hasNext();) {
            ProxyCacheEntry cacheEntry = 
                    (ProxyCacheEntry)cacheEntries.get(iter.next());
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
        // this method assumes the cache entry is the handler
        synchronized(cacheEntries) {
            return cacheEntries.containsKey(cacheEntry);
        }
    }
    
    
    /**
     *  This method adds a new entry to the proxy cache.
     *
     * @param timeout The timeout for this object.
     * @param proxy The proxy to add to the cache.
     * @param handler The handler to perform the check for.
     */
    public void addCacheEntry(long timeout, Object proxy, CacheEntry handler) 
            throws BeanException {
        synchronized(cacheEntries) {
            checkStatus();
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            cacheEntries.put(handler,new ProxyCacheEntry(cacheTimeout, proxy, 
                    handler));
        }
    }
    
    
    /**
     * This method will check to see if the cache has been terminated or not.
     *
     * @exception BeanException
     */
    private void checkStatus() throws BeanException {
        if (status.isTerminated()) {
            throw new BeanException("The proxy cache has been shut down.");
        }
    }
}
