/*
 * CoadunationUtil: The coaduntion utility library.
 * Copyright (C) 2008  2015 Burntjam
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
 * TransactionException.java
 */

// package path
package com.rift.coad.util.transaction;

// java imports
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// coadunation imports
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;


/**
 * This object represents a standard Java map that is transaction aware. It does
 * not provide access to dirty reads of any form.
 *
 * @author brett chaldecott
 */
public class CoadunationHashMap implements Map, XAResource {
    
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
         */
        public void addLock(LockRef lock) {
            locks.add(lock);
        }
        
        
        /**
         * This method adds a new entry to the entries list.
         *
         * @param key The key to add to the list.
         * @param value The value to add to the list
         */
        public void addEntry(Object key, Object value) {
            changesEntries.add(new ChangeEntry(key,value,ADD));
        }
        
        
        /**
         * This method adds a new remove entry to the list.
         *
         * @param key The key to add to the list.
         */
        public void addRemoveEntry(Object key) {
            changesEntries.add(new ChangeEntry(key,null,REMOVE));
        }
        
        
        /**
         * This method is called to add a clear change
         */
        public void addClearChange() {
            changesEntries.add(new ChangeEntry(null,null,CLEAR_MAP));
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
    private final static byte ADD = 1;
    private final static byte UPDATE = 2;
    private final static byte REMOVE = 3;
    private final static byte CLEAR_MAP = 4;
    
    
    // private member variables
    private ThreadLocal currentTransaction = new ThreadLocal();
    private Map keyLockMap = new HashMap();
    private Map internalMap = new ConcurrentHashMap();
    private Map transactionChanges = new ConcurrentHashMap();
    
    
    /**
     * Creates a new instance of Map
     */
    public CoadunationHashMap() {
    }
    
    
    /**
     * This method returns the size of the map.
     *
     * @return The integer value containing the size of the map.
     */
    public int size() {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.size();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to check the size of the map: " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method returns true if the map is empty.
     *
     * @return TRUE if empty, FALSE if not.
     */
    public boolean isEmpty() {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.isEmpty();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to check if the map is empty : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This methood checks to see if the map contains a specified key.
     *
     * @return boolean TRUE if the key is found, FALSE if not.
     * @param key The key to look for.
     */
    public boolean containsKey(Object key) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(key);
            return internalMap.containsKey(key);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to check for a key : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method checks to see if the specified value can be found.
     *
     * @return TRUE if the value is found false if not.
     * @param value The value to find.
     */
    public boolean containsValue(Object value) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.containsValue(value);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to check for a value : " + ex.getMessage(),
                    ex);
        }   
    }
    
    
    /**
     * This methode returns the specified object from the list.
     *
     * @param key The keyt to retrieve the object for.
     */
    public Object get(Object key) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(key);
            return internalMap.get(key);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to get an object : " + ex.getMessage(),ex);
        } 
    }
    
    
    /**
     * This method puts a new entry into the map
     *
     * @return The value that was added to the map.
     * @param key The key to add.
     * @param value The value to add.
     */
    public Object put(Object key, Object value) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(key);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addEntry(key,value);
            return value;
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes
     */
    public Object remove(Object key) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(key);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addRemoveEntry(key);
            return internalMap.get(key);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to remove the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method puts all the entries from one map into another.
     *
     * @param map The method to put entries.
     */
    public void putAll(Map map) {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            Set entries = map.keySet();
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                Object key = iter.next();
                Object value = map.get(key);
                getLock(key);
                changes.addEntry(key,value);
            }
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to clear the map.
     */
    public void clear() {
        try {
            TransactionManager.getInstance().bindResource(this,true);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addClearChange();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the key set for this object.
     *
     * @return The key set for this object.
     */
    public Set<Object> keySet() {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.keySet();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a collection of values.
     *
     * @return This method returns the values.
     */
    public Collection<Object> values() {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.values();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to retrieve the collection : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * This method returns the entry set.
     */        
    public Set<Map.Entry<Object, Object>> entrySet() {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return internalMap.entrySet();
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to retrieve the collection : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * Commit the changes made by a transaction.
     *
     * @param xid The id of the transaction to commit;
     * @param b TRUE if found FALSE if not.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        try {
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getChangeEntries().iterator(); 
            iter.hasNext();) {
                ChangeEntry entry = (ChangeEntry)iter.next();
                if ((entry.getChangeType() == ADD) || 
                        (entry.getChangeType() == UPDATE)) {
                    internalMap.put(entry.getKey(),entry.getValue());
                } else if (entry.getChangeType() == REMOVE) {
                    internalMap.remove(entry.getKey());
                } else if (entry.getChangeType() == CLEAR_MAP) {
                    internalMap.clear();
                }
            }
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
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
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
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
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
        } catch (Exception ex) {
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
            if (!transactionChanges.containsKey(xid)) {
                transactionChanges.put(xid,new Changes(xid));
            }
            currentTransaction.set(xid);
        } catch (Exception ex) {
            throw new XAException("Cannot start a transaction because : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method creates a lock for the given object and attaches it to the
     * transaction change set.
     *
     * @return The reference to the lock.
     * @param The name of the queue that must be locked.
     * @exception TransactionException
     */
    private void getLock(Object name) throws TransactionException {
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
            throw new TransactionException
                    ("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
        }
    }
}
