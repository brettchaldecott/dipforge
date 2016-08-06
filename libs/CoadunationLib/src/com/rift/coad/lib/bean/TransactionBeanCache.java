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
 * TransactionBeanCache.java
 */

// java package
package com.rift.coad.lib.bean;

// java imports
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
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
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.util.transaction.TransactionManager;

/**
 * This object is responsible for managing the transaction bean cache.
 *
 * @author Brett Chaldecott
 */
public class TransactionBeanCache implements Cache,XAResource {
    
    /**
     * This object represents a change in the change list
     */
    public class ChangeEntry {
        // member variables
        private Object key = null;
        private Object value = null;
        private byte changeType = 0;
        
        
        /**
         * The constructor of the change entry object.
         *
         * @param key The key to identify this change.
         * @param value The new value for this change.
         * @param changeType The type of change that has occurred.
         */
        public ChangeEntry(Object key, Object value, byte changeType) {
            this.key = key;
            this.value = value;
            this.changeType = changeType;
        }
        
        
        /**
         * This method returns the key identifying this object.
         *
         * @return The key identifying this object.
         */
        public Object getKey() {
            return key;
        }
        
        
        /**
         * This method returns the value identifying this object.
         *
         * @return The value identifying this object.
         */
        public Object getValue() {
            return value;
        }
        
        
        /**
         * This method returns the change type for this object.
         *
         * @return The change type for this object.
         */
        public byte getChangeType() {
            return changeType;
        }
    }
    
    /**
     * The object that represents a change on this cache object.
     */
    public class Changes {
        // the class private member variables
        private Xid transactionId = null;
        private List locks = new ArrayList();
        private List changesEntries = new ArrayList();
        
        
        /**
         * The constructor of the changes object.
         *
         * @param transactionId The id of the current transaction
         */
        public Changes(Xid transactionId) {
            this.transactionId = transactionId;
        }
        
        
        /**
         * This method will add a lock to the list of locks.
         *
         * @param lock The lock reference
         * @exception BeanException
         */
        public void addLock(LockRef lock) throws BeanException {
            locks.add(lock);
        }
        
        
        /**
         * This method adds a new entry to the entries list.
         *
         * @param key The key to add to the list.
         * @param value The value to add to the list
         * @exception BeanException
         */
        public void addEntry(Object key, Object value) throws
                BeanException {
            changesEntries.add(new ChangeEntry(key,value,ADD));
        }
        
        
        /**
         * This method adds a new remove entry to the list.
         *
         * @param key The key to add to the list.
         * @param value The object that is getting removed.
         * @exception BeanException
         */
        public void addRemoveEntry(Object key, Object value) throws
                BeanException {
            changesEntries.add(new ChangeEntry(key,value,REMOVE));
        }
        
        
        /**
         * This method returns the list of added entries
         *
         * @return The list of queues.
         */
        public List getChangeEntries() {
            return changesEntries;
        }
        
        
        /**
         * This method returns the list of locks.
         *
         * @return The list of locks.
         */
        public List getLocks() {
            return locks;
        }
    }
    
    // class constants
    private final static String CACHE_EXPIRY_TIME = "bean_cache_expiry";
    private final static long CACHE_EXPIRY_TIME_DEFAULT = 30 * 60 * 1000;
    private final static byte ADD = 1;
    private final static byte UPDATE = 2;
    private final static byte REMOVE = 3;
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(TransactionBeanCache.class.getName());
    
    // the cache entries
    private long defaultCacheExpiryTime = 0;
    private ThreadStateMonitor status = new ThreadStateMonitor();
    private ThreadLocal currentTransaction = new ThreadLocal();
    private Map keyLockMap = new HashMap();
    private Map baseCacheEntries = new ConcurrentHashMap();
    private Map transactionChanges = new ConcurrentHashMap();
    
    /**
     * Creates a new instance of TransactionBeanCache
     *
     * @exception BeanException
     */
    public TransactionBeanCache() throws BeanException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(BeanCache.class);
            defaultCacheExpiryTime = config.getLong(CACHE_EXPIRY_TIME,
                    CACHE_EXPIRY_TIME_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to start the TransactionBeanCache object " +
                    "because : " + ex.getMessage(),ex);
            throw new BeanException(
                    "Failed to start the TransactionBeanCache object " +
                    "because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to perform garbage collection on the cache entries.
     */
    public void garbageCollect() {
        // copy the entries map
        Map entries = new HashMap();
        entries.putAll(this.baseCacheEntries);
        
        // loop through the entires and remove the expired ones
        Date expiryDate = new Date();
        for (Iterator iter = entries.keySet().iterator();iter.hasNext();) {
            Object cacheKey = iter.next();
            LockRef lockRef = null;
            try {
                lockRef = getLockRef(cacheKey);
            } catch (Exception ex) {
                log.error("Failed to aquire lock on [" + cacheKey 
                        + "] because :" + ex.getMessage(),ex);
                continue;
            }
            try {
                BeanCacheEntry beanCacheEntry =
                        (BeanCacheEntry)entries.get(cacheKey);
                if (beanCacheEntry.isExpired(expiryDate)) {
                    if (beanCacheEntry.getCacheEntry() != null) {
                        try {
                            PortableRemoteObject.unexportObject(
                                    (java.rmi.Remote)
                                    beanCacheEntry.getCacheEntry());
                            this.baseCacheEntries.remove(cacheKey);
                            beanCacheEntry.cacheRelease();
                        } catch (java.rmi.NoSuchObjectException ex) {
                            log.warn("The object was never exported : " +
                                    ex.getMessage(),ex);
                            // remove from cache
                            synchronized(entries) {
                                this.baseCacheEntries.remove(cacheKey);
                            }
                            beanCacheEntry.cacheRelease();
                        } catch (Exception ex) {
                            log.error("Failed to un-export this object : " +
                                    ex.getMessage(),ex);
                        }
                    } else {
                        // if this object has not cache entry as in no rmi tie
                        // class than just remove it.
                        this.baseCacheEntries.remove(cacheKey);
                        beanCacheEntry.cacheRelease();
                    }
                }
            } finally {
                try {
                    lockRef.release();
                } catch (Exception ex) {
                    log.error("Failed to release the lock on [" + cacheKey 
                            + "] because :" + ex.getMessage(),ex);
                }
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
            // copy the entries map
            status.terminate(false);
            Map entries = new HashMap();
            entries.putAll(this.baseCacheEntries);
            this.baseCacheEntries.clear();

            // loop through the entires and remove the expired ones
            for (Iterator iter = entries.keySet().iterator();iter.hasNext();) {
                Object cacheKey = iter.next();
                BeanCacheEntry beanCacheEntry = 
                        (BeanCacheEntry)entries.get(cacheKey);
                if (beanCacheEntry.getCacheEntry() != null)
                {
                    try {
                        PortableRemoteObject.unexportObject(
                                (java.rmi.Remote)
                                beanCacheEntry.getCacheEntry());
                    } catch (java.rmi.NoSuchObjectException ex) {
                        log.warn("The cache object was not bound : " + 
                                ex.getMessage(),ex);
                    } catch (Exception ex) {
                        log.error("Failed to un-export the cached object : " + 
                                ex.getMessage(),ex);
                    }
                }
                beanCacheEntry.cacheRelease();
            }
            
        } catch (Exception ex) {
            log.error("Failed to clear the bean cache : " + ex.getMessage(),ex);
        } finally {
            try {
                lockRef.release();
            } catch (Exception ex) {
                log.error("Failed to release the write lock : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    
    /**
     * This mehtod returns true if the cache contains the checked entry.
     *
     * @return TRUE if the cache contains the checked entry.
     * @param cacheKey The entry to perform the check for.
     */
    public boolean contains(Object cacheKey) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(cacheKey);
            return baseCacheEntries.containsKey(cacheKey);
        } catch (Exception ex) {
            log.error(
                    "Failed to retrieve the cache entries : " + ex.getMessage(),
                    ex);
            return false;
        }
    }
    
    
    /**
     * This method returns the bean cache entry.
     *
     * @return The reference to the bean cache object.
     * @param cacheKey The key to retrieve.
     * @exception BeanException
     */
    public BeanCacheEntry getCacheEntry(Object cacheKey) throws BeanException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(cacheKey);
            BeanCacheEntry beanCacheEntry =
                    (BeanCacheEntry)baseCacheEntries.get(cacheKey);
            if (beanCacheEntry != null) {
                beanCacheEntry.touch();
            }
            return beanCacheEntry;
        } catch (Exception ex) {
            throw new BeanException("Failed to retrieve the cache entries : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds the entry to the cache.
     *
     * @param cacheKey The key to identify this entry by.
     * @param wrappedObject The object wrapped by the cache entry.
     * @param entry An entry in the cache
     */
    public void addCacheEntry(long timeout,Object cacheKey, Object wrappedObject,
            CacheEntry entry) throws BeanException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(cacheKey);
            if (baseCacheEntries.containsKey(cacheKey)) {
                throw new BeanException("Entry is already in the cache.");
            }
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            BeanCacheEntry beanCacheEntry = new BeanCacheEntry(cacheTimeout,
                    cacheKey, wrappedObject, entry);
            baseCacheEntries.put(cacheKey, beanCacheEntry);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addEntry(cacheKey,beanCacheEntry);
        } catch (BeanException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BeanException("Failed to add a cache entrie : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a new entry to the cache.
     *
     * @param cacheKey The key to identify this entry by.
     * @param wrappedObject The object wrapped by the proxy.
     * @param proxy The proxy to add.
     * @param handle The handler for the bean proxy object.
     */
    public void addCacheEntry(long timeout, Object cacheKey, Object wrappedObject,
            Object proxy, CacheEntry handle) throws BeanException {
        try {
            
            TransactionManager.getInstance().bindResource(this,false);
            getLock(cacheKey);
            if (baseCacheEntries.containsKey(cacheKey)) {
                throw new BeanException("Entry is already in the cache.");
            }
            long cacheTimeout = timeout;
            if (timeout == -1) {
                cacheTimeout = defaultCacheExpiryTime;
            }
            BeanCacheEntry beanCacheEntry = new BeanCacheEntry(cacheTimeout,
                    cacheKey, wrappedObject, proxy, handle);
            
            baseCacheEntries.put(cacheKey, beanCacheEntry);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addEntry(cacheKey,beanCacheEntry);
        } catch (BeanException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BeanException("Failed to add a cache entrie : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the entry from the cache based on the key passed in.
     *
     * @param cacheKey The key in the cache to remove.
     */
    public void removeCacheEntry(Object cacheKey) throws BeanException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(cacheKey);
            Object entry = baseCacheEntries.get(cacheKey);
            baseCacheEntries.remove(cacheKey);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addRemoveEntry(cacheKey,entry);
        } catch (Exception ex) {
            throw new BeanException("Failed to remove a cache entrie : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param b If true a one phase commit should be used.
     * @exception XAException
     */
    public synchronized void commit(Xid xid, boolean b) throws XAException {
        try {
            if (this.status.isTerminated()) {
                log.error("Commit called on terminated cache, ignoring.");
                return;
            }
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
            log.error("Failed to commit the changes : " +
                    ex.getMessage(),ex);
            throw new XAException("Failed to commit the changes : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * The resource manager has dissociated this object from the transaction.
     *
     * @param xid The id of the transaction that is getting ended.
     * @param i The flags associated with this operation.
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
            if (this.status.isTerminated()) {
                log.error("Commit called on terminated cache, ignoring.");
                return;
            }
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
            log.error("Failed to forget the changes : " +
                    ex.getMessage(),ex);
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
     * @param xAResource The resource to perform the check against.
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
     * @param i The flags
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
            if (this.status.isTerminated()) {
                log.error("Commit called on terminated cache, ignoring.");
            return;
            }
            Changes changes = (Changes)transactionChanges.get(xid);
            List changeEntries = changes.getChangeEntries();
            for (int index = 0; index < changeEntries.size(); index++) {
                ChangeEntry changeEntry = (ChangeEntry)changeEntries.get(
                        changeEntries.size() - (index + 1));
                if (changeEntry.getChangeType() == ADD) {
                    BeanCacheEntry beanCacheEntry = 
                            (BeanCacheEntry)changeEntry.getValue();
                    if (beanCacheEntry.getCacheEntry() != null) {
                        try {
                            PortableRemoteObject.unexportObject(
                                    (java.rmi.Remote)
                                    beanCacheEntry.getCacheEntry());
                            beanCacheEntry.cacheRelease();
                        } catch (java.rmi.NoSuchObjectException ex) {
                            log.warn("The object was never exported : " +
                                    ex.getMessage(),ex);
                            beanCacheEntry.cacheRelease();
                        } catch (Exception ex) {
                            log.error("Failed to un-export this object : " +
                                    ex.getMessage(),ex);
                            beanCacheEntry.cacheRelease();
                        }
                    } else {
                        // if this object has not cache entry as in no rmi tie
                        // class than just remove it.
                        beanCacheEntry.cacheRelease();
                    }
                    baseCacheEntries.remove(changeEntry.getKey());
                    
                } else if (changeEntry.getChangeType() == REMOVE) {
                    baseCacheEntries.put(changeEntry.getKey(),
                            changeEntry.getValue());
                }
            }
            
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
            log.error("Failed to rollback the changes : " +
                    ex.getMessage(),ex);
            throw new XAException("Failed to rollback the changes : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method sets the transaction timeout for this resource manager.
     *
     * @return TRUE if the transaction timeout can be set successfully.
     * @param i The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int i) throws XAException {
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param i The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int i) throws XAException {
        try {
            checkStatus();
            if (!transactionChanges.containsKey(xid)) {
                transactionChanges.put(xid,new Changes(xid));
            }
            currentTransaction.set(xid);
        } catch (Exception ex) {
            log.error("Cannot start a transaction because : " +
                    ex.getMessage(),ex);
            throw new XAException("Cannot start a transaction because : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method checks the bean cache status.
     */
    private void checkStatus() throws BeanException {
        if (status.isTerminated()) {
            throw new BeanException("Bean cache has been terminated.");
        }
    }
    
    
    /**
     * This method returns the named lock
     *
     * @return The reference to the lock.
     * @param The name of the queue that must be locked.
     * @exception MessageServiceException
     */
    private void getLock(Object name) throws BeanException {
        try {
            Object key = null;
            synchronized(keyLockMap) {
                if (keyLockMap.containsKey(name)) {
                    key = keyLockMap.get(name);
                } else {
                    key = name.toString();
                    keyLockMap.put(name,key);
                }
            }
            LockRef lockRef =
                    ObjectLockFactory.getInstance().acquireWriteLock(key,
                    currentTransaction.get());
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addLock(lockRef);
        } catch (Exception ex) {
            log.error("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
            throw new BeanException
                    ("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the named lock
     *
     * @return The reference to the lock.
     * @param The name of the queue that must be locked.
     * @exception MessageServiceException
     */
    private LockRef getLockRef(Object name) throws BeanException {
        try {
            Object key = null;
            synchronized(keyLockMap) {
                if (keyLockMap.containsKey(name)) {
                    key = keyLockMap.get(name);
                } else {
                    key = name.toString();
                    keyLockMap.put(name,key);
                }
            }
            return ObjectLockFactory.getInstance().acquireWriteLock(key);
        } catch (Exception ex) {
            log.error("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
            throw new BeanException
                    ("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
        }
    }
}
