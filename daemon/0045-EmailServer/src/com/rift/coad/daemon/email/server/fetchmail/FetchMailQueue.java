/*
 * Email Server: The email server interface
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
 * FetchMailQueue.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.Date;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.transaction.UserTransactionWrapper;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;


/**
 * This object is responsible for handling smtp message queue queries.
 *
 * @author brett chaldecott
 */
public class FetchMailQueue implements XAResource {
    
    /**
     * This object represents a change in the change list
     */
    public class ChangeEntry {
        // member variables
        private FetchEntry value = null;
        private byte changeType = 0;
        
        
        /**
         * The constructor of the change entry object.
         *
         * @param value The new value for this change.
         * @param changeType The type of change that has occurred.
         */
        public ChangeEntry(FetchEntry value, byte changeType) {
            this.value = value;
            this.changeType = changeType;
        }
        
        
        /**
         * This method returns the value identifying this object.
         *
         * @return The value identifying this object.
         */
        public FetchEntry getValue() {
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
         * @param value The value to add to the list
         * @exception BeanException
         */
        public void addEntry(FetchEntry value) {
            changesEntries.add(new ChangeEntry(value,ADD));
        }
        
        
        /**
         * This method adds a new remove entry to the list.
         *
         * @param value The object that is getting removed.
         * @exception BeanException
         */
        public void addPopEntry(FetchEntry value) {
            changesEntries.add(new ChangeEntry(value,POP));
        }
        
        
        /**
         * This method adds a new remove entry to the list.
         *
         * @param value The object that is getting removed.
         * @exception BeanException
         */
        public void addRemoveEntry(FetchEntry value) {
            changesEntries.add(new ChangeEntry(value,REMOVE));
        }
        
        /**
         * This method is called to add a clear change
         */
        public void addClearChange() {
            changesEntries.add(new ChangeEntry(null,CLEAR_QUEUE));
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
    private final static byte POP = 2;
    private final static byte CLEAR_QUEUE = 3;
    private final static byte REMOVE = 4;
    private final static String RETRY_TIME = "fetchmail_retry_time";
    private final static long DEFAULT_RETRY_TIME = 1000 * 60 * 30;
    
    
    // private member variables.
    private Logger log = Logger.getLogger(FetchMailQueue.class);
    private ThreadLocal currentTransaction = new ThreadLocal();
    private Map keyLockMap = new HashMap();
    private BlockingQueue queue = new PriorityBlockingQueue();
    private Map transactionChanges = new ConcurrentHashMap();
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private long retryTime = 0;
    
    
    /**
     * Creates a new instance of SMTPMessageQueue
     */
    public FetchMailQueue() throws ServerException {
        try {
            Configuration config = ConfigurationFactory.
                    getInstance().getConfig(FetchMailQueue.class);
            retryTime = config.getLong(RETRY_TIME,DEFAULT_RETRY_TIME);
        } catch (Exception ex) {
            log.error("Failed to initialize the SMTP Message Queue : " + 
                    ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to initialize the SMTP Message Queue : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method addes this entry to the queue.
     */
    public void add(FetchEntry value) throws ServerException{
        try {
            TransactionManager.getInstance().bindResource(this,false);
            value.recalculateRetryTime(retryTime);
            getLock(value);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addEntry(value);
        } catch (Exception ex) {
            log.error("Failed to add the entry : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method checks to see if the given value can be found in the queue.
     */
    public boolean contains(FetchEntry value) throws ServerException{
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(value);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            return this.queue.contains(value);
        } catch (Exception ex) {
            log.error("Failed check if the queue contains a valid entry : " 
                    + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed check if the queue contains a valid entry : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes an entry from the queue.
     *
     * @param value The value to remove.
     * @exception ServerException
     */
    public void remove(FetchEntry value) throws ServerException{
        try {
            TransactionManager.getInstance().bindResource(this,false);
            getLock(value);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addRemoveEntry(value);
        } catch (Exception ex) {
            log.error("Failed to add a remove entry : " 
                    + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add a remove entry : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to pop and entry from the list
     *
     * @return A reference to the smtp server message.
     * @param ServerException
     */
    public FetchEntry pop(UserTransactionWrapper transaction) 
    throws ServerException {
        try {
            while (!state.isTerminated()) {
                if (queue.isEmpty()) {
                    monitor(0);
                    continue;
                }
                FetchEntry message = (FetchEntry)queue.peek();
                long retryTime = message.getRetryTime().getTime();
                long currentTime = new Date().getTime();
                if (retryTime <= currentTime) {
                    transaction.begin();
                    TransactionManager.getInstance().bindResource(this,false);
                    Changes changes = (Changes)transactionChanges.get(
                            currentTransaction.get());
                    changes.addPopEntry(message);
                    return message;
                }
                monitor(retryTime - currentTime);
            }
            transaction.begin();
            return null;
        } catch (Exception ex) {
            log.error("Failed to add the entry : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method addes this entry to the queue.
     */
    public void clear() throws ServerException{
        try {
            TransactionManager.getInstance().bindResource(this,false);
            Changes changes = (Changes)transactionChanges.get(
                    currentTransaction.get());
            changes.addClearChange();
        } catch (Exception ex) {
            log.error("Failed to add the entry : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to add the entry : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to terminate the processing of this queue.
     */
    public synchronized void terminate() {
        state.terminate(true);
        notify();
    }
    
    
    /**
     * This method returns the retry time.
     */
    public long getRetryTime() {
        return this.retryTime;
    }
    
    
    /**
     * Commit the changes made by a transaction.
     *
     * @param xid The id of the transaction to commit;
     * @param boolean TRUE if found FALSE if not.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        try {
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getChangeEntries().iterator(); 
            iter.hasNext();) {
                ChangeEntry entry = (ChangeEntry)iter.next();
                if ((entry.getChangeType() == ADD)) {
                    queue.add(entry.getValue());
                } else if (entry.getChangeType() == POP || 
                        entry.getChangeType() == REMOVE) {
                    queue.remove(entry.getValue());
                } else if (entry.getChangeType() == CLEAR_QUEUE) {
                    queue.clear();
                }
            }
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
            synchronized (this) {
                notify();
            }
        } catch (Exception ex) {
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
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
            synchronized (this) {
                notify();
            }
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
            Changes changes = (Changes)transactionChanges.get(xid);
            for (Iterator iter = changes.getLocks().iterator();
            iter.hasNext();) {
                LockRef lockRef = (LockRef)iter.next();
                lockRef.release();
            }
            transactionChanges.remove(xid);
            synchronized (this) {
                notify();
            }
        } catch (Exception ex) {
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
    private void getLock(Object name) throws ServerException {
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
            throw new ServerException
                    ("Failed to retrieve a lock on the bean cache entry : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to monitor the state.
     */
    private synchronized void monitor(long delay) {
        try {
            if (delay == 0) {
                wait();
            } else {
                wait(delay);
            }
        } catch (Exception ex) {
            log.error("Failed to wait because : " + ex.getMessage());
        }
    }
    
    
}
