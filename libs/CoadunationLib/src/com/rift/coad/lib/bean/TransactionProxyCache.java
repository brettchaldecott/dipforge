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
 * TransactionProxyCache.java
 */

// package path
package com.rift.coad.lib.bean;

// java imports
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.rmi.PortableRemoteObject;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.cache.Cache;
import com.rift.coad.lib.cache.CacheEntry;
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.transaction.TransactionManager;


/**
 * This object acts as the transaction proxy cache for the container.
 *
 * @author Brett Chaldecott
 */
public class TransactionProxyCache implements Cache,XAResource {
    
    /**
     * This object tracks the changes for a transaction
     */
    public class ChangeEntry {
        // private member variables
        private Xid transactionId = null;
        private List newEntries = new ArrayList();
        
        
        /**
         * The constructor of the change entry
         *
         * @param transactionId The id of the transaction.
         */
        public ChangeEntry(Xid transactionId) {
            this.transactionId = transactionId;
        }
        
        
        /**
         * This method returns the transaction id.
         *
         * @return The object containing the transaction id.
         */
        public Xid getTransactionId() {
            return transactionId;
        }
        
        
        /**
         * This method addes the cache entry.
         *
         * @param entry The proxy cache entry to add.
         */
        public void addEntry(ProxyCacheEntry entry) {
            newEntries.add(entry);
        }
        
        
        /**
         * This method returns the list of added entries.
         */
        public List getEntries() {
            return newEntries;
        }
    }
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "proxy_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 60 * 1000;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(ProxyCache.class.getName());
    
    // private member variables
    private ThreadLocal currentTransaction = new ThreadLocal();
    private ThreadStateMonitor status = new ThreadStateMonitor();
    private Map cacheEntries = new ConcurrentHashMap();
    private long defaultCacheExpiryTime = 0;
    private Map transactionChanges = new ConcurrentHashMap();
    
    
    /** 
     * Creates a new instance of TransactionProxyCache
     */
    public TransactionProxyCache() throws BeanException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(ProxyCache.class);
            defaultCacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the TransactionProxyCache object " +
                    "because : " + ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to start the TransactionProxyCache object " +
                    "because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        Map cacheEntries = new HashMap();
        cacheEntries.putAll(this.cacheEntries);
        
        // the start time
        Date currentTime = new Date();
        for (Iterator iter = cacheEntries.keySet().iterator(); 
                iter.hasNext();) {
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
        LockRef lockRef = null;
        try {
            lockRef = ObjectLockFactory.getInstance().acquireReadLock(this);
            Map cacheEntries = new HashMap();
            status.terminate(false);
            cacheEntries.putAll(this.cacheEntries);
            this.cacheEntries.clear();

            for (Iterator iter = cacheEntries.keySet().iterator(); 
                    iter.hasNext();) {
                ProxyCacheEntry cacheEntry = 
                        (ProxyCacheEntry)cacheEntries.get(iter.next());
                try {
                    cacheEntry.cacheRelease();
                } catch (Exception ex) {
                    log.error("Failed to release the cache entry : " + 
                            ex.getMessage(),ex);
                }
            }
        } catch (Exception ex) {
            log.error("Failed clear the cache : " + ex.getMessage(),ex);
        } finally {
            try {
                if (lockRef != null) {
                    lockRef.release();
                }
            } catch (Exception ex) {
                log.error("Failed to release the lock : " +
                        ex.getMessage(),ex);
            }
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
        try {
            checkStatus();
            TransactionManager.getInstance().bindResource(this,false);
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            ProxyCacheEntry newEntry = new ProxyCacheEntry(timeout, 
                    proxy, handler);
            ChangeEntry changeEntry = (ChangeEntry)transactionChanges.get(
                    currentTransaction.get());
            changeEntry.addEntry(newEntry);
        } catch (Exception ex) {
            log.error("Failed to add the cache entry : " +
                    ex.getMessage(),ex);
            throw new BeanException("Failed to add the cache entry : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheEntry The entry to perform the check for.
     */
    public boolean contains(Object cacheEntry) {
        if (!status.isTerminated()) {
            return cacheEntries.containsKey(cacheEntry);
        }
        return false;
    }
    
    
    /**
     * This method returns the bean cache entry.
     *
     * @return The reference to the bean cache object.
     * @param key The key to retrieve.
     * @exception BeanException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        try {
            ChangeEntry changeEntry = (ChangeEntry)transactionChanges.get(xid);
            List entries = changeEntry.getEntries();
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                ProxyCacheEntry proxyCacheEntry = (ProxyCacheEntry)iter.next();
                cacheEntries.put(proxyCacheEntry.getCacheEntry(),
                        proxyCacheEntry);
            }
        } catch (Exception ex) {
            log.error("Failed to commit the changes : " + ex.getMessage(),ex);
            throw new XAException("Failed to commit the changes : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * The resource manager has dissociated this object from the transaction.
     *
     * @param xid The id of the transaction that is getting ended.
     * @param flags The flags associated with this operation.
     * @exception XAException
     */
    public void end(Xid xid, int i) throws XAException {
    }
    
    
    /**
     * The transaction has been completed and must be forgotten.
     *
     * @param xid The id of the transaction to forget.
     * @exception XAException
     */
    public void forget(Xid xid) throws XAException {
        try {
            transactionChanges.remove(xid);
        } catch (Exception ex) {
            log.error("Failed to forget the changes : " + ex.getMessage(),ex);
            throw new XAException("Failed to forget the changes : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method returns the transaction timeout for this object.
     *
     * @return The int containing the transaction timeout.
     * @exception XAException
     */
    public int getTransactionTimeout() throws XAException {
        return -1;
    }
    
    
    /**
     * This method returns true if this object is the resource manager getting
     * queried.
     *
     * @return TRUE if this is the resource manager, FALSE if not.
     * @param xaResource The resource to perform the check against.
     * @exception XAException
     */
    public boolean isSameRM(XAResource xAResource) throws XAException {
        return this == xAResource;
    }
    
    
    /**
     * This is called before a transaction is committed.
     *
     * @return The results of the transaction.
     * @param xid The id of the transaction to check against.
     * @exception XAException
     */
    public int prepare(Xid xid) throws XAException {
        return XAResource.XA_OK;
    }
    
    
    /**
     * This method returns the list of transaction branches for this resource
     * manager.
     *
     * @return The list of resource branches.
     * @param flags The flags
     * @exception XAException
     */
    public Xid[] recover(int i) throws XAException {
        return null;
    }
    
    
    /**
     * This method is called to roll back the specified transaction.
     *
     * @param xid The id of the transaction to roll back.
     * @exception XAException
     */
    public void rollback(Xid xid) throws XAException {
        try {
            ChangeEntry changeEntry = (ChangeEntry)transactionChanges.get(xid);
            List entries = changeEntry.getEntries();
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                ProxyCacheEntry proxyCacheEntry = (ProxyCacheEntry)iter.next();
                try {
                    proxyCacheEntry.cacheRelease();
                } catch (Exception ex) {
                    log.error("Failed to release the entry");
                }
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
            log.error("Failed to rollback the changes : " + ex.getMessage(),ex);
            throw new XAException("Failed to rollback the changes : " +
                    ex.getMessage());
        }
    }
    
    /**
     * This method sets the transaction timeout for this resource manager.
     *
     * @return TRUE if the transaction timeout can be set successfully.
     * @param transactionTimeout The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int i) throws XAException {
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param flags The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int i) throws XAException {
        if (!transactionChanges.containsKey(xid)) {
            transactionChanges.put(xid,new ChangeEntry(xid));
        }
        currentTransaction.set(xid);
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
