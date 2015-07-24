/*
 * MessageService: The message service daemon
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * IDLock.java
 */

// the package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.transaction.TransactionManager;


/**
 * This object is responsible for creating a lock based on the string id passed 
 * in.
 *
 * @author Bret Chaldecott
 */
public class IDLock implements XAResource {
    
    /**
     * This class is responsible for representing a lock based on an id.
     */
    public class Lock {
        
        // private member variables
        private String id = null;
        private Xid owner = null;
        private int referenceCount = 0;
        
        /**
         * The constructor of the lock object.
         */
        public Lock(String id) {
            this.id = id;
        }
        
        
        /**
         * The getter for the id value.
         *
         * @return The value of the id for this object.
         */
        public String getId() {
            return id;
        }
        
        
        /**
         * This method increments the reference count.
         *
         * @return The integer reference count.
         */
        public synchronized int incrementReferenceCount() {
            return ++referenceCount;
        }
        
        
        /**
         * This method decrements the reference count to this object and returns
         * the result of this.
         *
         * @return The integer reference count.
         */
        public synchronized int decrementReferenceCount() {
            return --referenceCount;
        }
        
        
        /**
         * This method attempts to lock this object based on the owner id
         * passed in
         *
         * @param owner The owner of this lock.
         * @exception IDLockException
         */
        public synchronized void lock(Xid owner) throws IDLockException {
            while (this.owner != null) {
                try {
                    wait();
                } catch (Exception ex) {
                    log.error("Failed to lock this object : " + ex.getMessage(),
                            ex);
                    throw new IDLockException(
                            "Failed to lock this object : " + ex.getMessage(),
                            ex);
                }
            }
            this.owner = owner;
        }
        
        
        /**
         * This method unlocks the object.
         */
        public synchronized void unlock() {
            this.owner = null;
            notify();
        }
    }

    // singletons
    private static IDLock singleton = null;
    
    // log object
    private Logger log = Logger.getLogger(IDLock.class.getName());
    
    // private member variables
    private Map transactionMap = new ConcurrentHashMap();
    private Map lockMap = new HashMap();
    private ThreadLocal currentLock = new ThreadLocal();
    
    
    /** 
     * Creates a new instance of IDLock 
     */
    private IDLock() {
    }
    
    
    /**
     * This method returns an instance of the IDLock object.
     *
     * @return A reference to the id lock singleton.
     */
    public synchronized static IDLock getInstance() {
        if (singleton == null) {
            singleton = new IDLock();
        }
        return singleton;
    }
    
    
    /**
     * This method creates a lock based on the supplied id.
     *
     * @param id The id of the key to lock
     * @exception IDLockException
     */
    public void lock(String id) throws IDLockException {
        try {
            Lock lock = null;
            synchronized(lockMap) {
                lock = (Lock)lockMap.get(id);
                if (lock == null) {
                    lock = new Lock(id);
                    lockMap.put(id,lock);
                }
                lock.incrementReferenceCount();
            }
            currentLock.set(lock);
            TransactionManager.getInstance().bindResource(this,false);
        } catch (Exception ex) {
            log.error("Failed to lock the id [" + id + "] : " +
                    ex.getMessage(),ex);
            throw new IDLockException("Failed to lock the id [" + id + "] : " +
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is responsible for handling the committing of a transaction
     * identified by the xid.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        try {
            Lock lock = (Lock)transactionMap.remove(xid);
            lock.unlock();
            synchronized(lockMap) {
                if (lock.decrementReferenceCount() <= 0) {
                    lockMap.remove(lock.getId());
                }
            }
        } catch (Exception ex) {
            log.error("Failed to roll back the changes : " + 
                    ex.getMessage(),ex);
            throw new XAException("Failed to roll back the changes : " + 
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
            Lock lock = (Lock)transactionMap.remove(xid);
            lock.unlock();
            synchronized(lockMap) {
                if (lock.decrementReferenceCount() <= 0) {
                    lockMap.remove(lock.getId());
                }
            }
        } catch (Exception ex) {
            log.error("Failed to roll back the changes : " + 
                    ex.getMessage(),ex);
            throw new XAException("Failed to roll back the changes : " + 
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
            Lock lock = (Lock)transactionMap.remove(xid);
            lock.unlock();
            synchronized(lockMap) {
                if (lock.decrementReferenceCount() <= 0) {
                    lockMap.remove(lock.getId());
                }
            }
        } catch (Exception ex) {
            log.error("Failed to roll back the changes : " + 
                    ex.getMessage(),ex);
            throw new XAException("Failed to roll back the changes : " + 
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
        try {
            Lock lock = (Lock)currentLock.get();
            lock.lock(xid);
            transactionMap.put(xid,lock);
        } catch (Exception ex) {
            log.error("Failed to start the transaction : " + 
                    ex.getMessage(),ex);
            throw new XAException("Failed to start the transaction : " + 
                    ex.getMessage());
        }
    }
    
    
    
}
