/*
 * MessageService: The message service daemon
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
 * MessageQueue.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.lang.ThreadLocal;
import java.util.Date;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;

/**
 * The message queue object is responsible for queuing messages in memory.
 *
 * @author Brett Chaldecott
 */
public class MessageQueue implements XAResource {
    
    /**
     * This object tracks the changes made by a transaction.
     */
    public class TransactionChange {
        
        // private member variables
        private ArrayList addList = new ArrayList();
        private ArrayList updateList = new ArrayList();
        private ArrayList removeList = new ArrayList();
        
        /**
         * The constructor of the transaction change object.
         */
        public TransactionChange() {
            
        }
        
        
        /**
         * This method adds to the add list
         *
         * @param message The message to add to the add list.
         */
        public void add(MessageManager messageManager) {
            addList.add(messageManager);
        }
        
        
        /**
         * This method returns the list of added entries.
         *
         * @return The list of added messages.
         */
        public ArrayList getAddList() {
            return addList;
        }
        
        
        /**
         * This method add an entry to the update list.
         *
         * @param message The message to add.
         */
        public void update(MessageManager messageManager) {
            updateList.add(messageManager);
        }
        
        
        /**
         * This method returns an array list containing all the updated messages.
         *
         * @return The array list of all the updated messages.
         */
        public ArrayList getUpdateList() {
            return updateList;
        }
        
        
        /**
         * This method marks a message within a transaction as being removed
         * by that transaction.
         *
         * @param message The message to remove.
         */
        public void remove(MessageManager messageManager) {
            removeList.add(messageManager);
        }
        
        
        /**
         * This method returns list of removed entries.
         *
         */
        public ArrayList getRemoveList() {
            return removeList;
        }
    }
    
    
    /**
     * This is the id index.
     */
    public class IDIndex {
        private Map baseIndex = new HashMap();
        private ThreadLocal threadIndex = new ThreadLocal();
        private Map transactionIndex = new HashMap();
        
        
        /**
         * The constructor of the ID index.
         */
        public IDIndex() {
            
        }
        
        
        /**
         * This method is called to commit the specified transaction.
         *
         * @param xid The id of the transaction to commit.
         * @exception MessageServiceException
         */
        public synchronized void commit(Xid xid) throws
                MessageServiceException {
            try {
                TransactionChange changes = (TransactionChange)transactionChange.
                        get(xid);
                // add entries
                ArrayList addedEntries = changes.getAddList();
                for (int index = 0; index < addedEntries.size(); index++) {
                    MessageManager messageManager =
                            (MessageManager)addedEntries.get(index);
                    baseIndex.put(messageManager.getID(),messageManager);
                }
                
                // remove entries
                ArrayList removedEntries = changes.getRemoveList();
                for (int index = 0; index < removedEntries.size(); index++) {
                    MessageManager messageManager =
                            (MessageManager)removedEntries.get(index);
                    baseIndex.remove(messageManager.getID());
                }
                transactionIndex.remove(xid);
            } catch (Exception ex) {
                log.error("Failed to commit the transaction : " + ex.getMessage()
                ,ex);
                throw new MessageServiceException("Failed to commit the " +
                        "transaction : " + ex.getMessage());
            }
        }
        
        
        /**
         * The transaction has been completed and must be forgotten.
         *
         * @param xid The id of the transaction to forget.
         * @exception MessageServiceException
         */
        public synchronized void forget(Xid xid) throws MessageServiceException {
            transactionIndex.remove(xid);
        }
        
        
        /**
         * This method is called to roll back the specified transaction.
         *
         * @param xid The id of the transaction to roll back.
         * @exception MessageServiceException
         */
        public synchronized void rollback(Xid xid) throws MessageServiceException {
            transactionIndex.remove(xid);
        }
        
        
        /**
         * This method is called to start a transaction on a resource manager.
         *
         * @param xid The id of the new transaction.
         * @exception MessageServiceException
         */
        public synchronized void start(Xid xid) throws MessageServiceException {
            Map transactionScopedIndex = null;
            if (transactionIndex.containsKey(xid)) {
                transactionScopedIndex = (Map)transactionIndex.get(
                        xid);
            } else {
                transactionScopedIndex = new HashMap(baseIndex);
                transactionIndex.put(xid,
                        transactionScopedIndex);
            }
            threadIndex.set(transactionScopedIndex);
        }
        
        
        /**
         * This method adds a message to the message queue.
         *
         * @param message The message to add to the message queue.
         * @exception MessageServiceException
         */
        public void addMessage(MessageManager message) throws
                MessageServiceException {
            Map index = (Map)threadIndex.get();
            index.put(message.getID(),message);
        }
        
        
        /**
         * This method removes the specified message from the list.
         *
         * @param messageId The id of the message to remove.
         * @exception MessageServiceException
         */
        public void removeMessage(String messageId) throws
                MessageServiceException {
            try {
                Map index = (Map)threadIndex.get();
                if (index.containsKey(messageId)) {
                    MessageManager message = (MessageManager)index.get(messageId);
                    TransactionChange change = (TransactionChange)
                            transactionChange.get(transactionId.get());
                    change.remove(message);
                    index.remove(messageId);
                } else {
                    throw new MessageServiceException("The message [" + messageId
                            + "] was not found to remove");
                }
            } catch (MessageServiceException ex) {
                throw ex;
            } catch (Exception ex) {
                log.error("Failed to remove the message : " + 
                        ex.getMessage(),ex);
                throw new MessageServiceException(
                        "Failed to remove the message : " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method retrieves the specified message from the list.
         *
         * @param messageId The id of the message to retrieve.
         * @exception MessageServiceException
         */
        public MessageManager getMessage(String messageId) throws
                MessageServiceException {
            Map index = (Map)threadIndex.get();
            if (index.containsKey(messageId)) {
                return (MessageManager)index.get(messageId);
            } else {
                throw new MessageServiceException("The message [" + messageId
                        + "] was not found.");
            }
        }
        
        
    }
    
    
    /**
     * This class is responsible for managing the queue of entries.
     */
    public class QueueIndex {
        // private member variables
        private PriorityQueue baseQueue = new PriorityQueue();
        private Map processingEntries = new HashMap();
        
        
        /**
         * The constructor of the 
         */
        public QueueIndex() {
            
        }
        
        
        /**
         * This method is called to commit the specified transaction.
         *
         * @param xid The id of the transaction to commit.
         * @exception MessageServiceException
         */
        public synchronized void commit(Xid xid) throws
                MessageServiceException {
            try {
                TransactionChange changes = (TransactionChange)transactionChange.
                        get(xid);
                // add entries
                ArrayList addedEntries = changes.getAddList();
                for (int index = 0; index < addedEntries.size(); index++) {
                    MessageManager messageManager =
                            (MessageManager)addedEntries.get(index);
                    baseQueue.add(messageManager);
                }
                
                // remove entries
                ArrayList removedEntries = changes.getRemoveList();
                for (int index = 0; index < removedEntries.size(); index++) {
                    MessageManager messageManager =
                            (MessageManager)removedEntries.get(index);
                    if (baseQueue.contains(messageManager)) {
                        baseQueue.remove(messageManager);
                    } else if (processingEntries.containsKey(messageManager)) {
                        LockRef lockRef = (LockRef)processingEntries.get(
                                messageManager);
                        processingEntries.remove(messageManager);
                        lockRef.release();
                    }
                }
            } catch (Exception ex) {
                log.error("Failed to commit the transaction : " + ex.getMessage()
                        ,ex);
                throw new MessageServiceException("Failed to commit the " +
                        "transaction : " + ex.getMessage());
            }
        }
        
        
        /**
         * The transaction has been completed and must be forgotten.
         *
         * @param xid The id of the transaction to forget.
         * @exception MessageServiceException
         */
        public synchronized void forget(Xid xid) throws MessageServiceException {
            
        }
        
        
        /**
         * This method is called to roll back the specified transaction.
         *
         * @param xid The id of the transaction to roll back.
         * @exception MessageServiceException
         */
        public synchronized void rollback(Xid xid) throws MessageServiceException {
            
        }
        
        
        /**
         * This method is called to start a transaction on a resource manager.
         *
         * @param xid The id of the new transaction.
         * @exception MessageServiceException
         */
        public synchronized void start(Xid xid) throws MessageServiceException {
            
        }
        
        
        /**
         * This method returns the next message manager or null.
         *
         * @return The reference to message manager.
         * @param nextRunTime The date wrapper object.
         * @exception MessageServiceException
         */
        public synchronized MessageManager popFrontMessage(Date nextRunTime) throws
                MessageServiceException {
            LockRef lockRef = null;
            try {
                MessageManager messageManager = (MessageManager)baseQueue.peek();
                if (messageManager == null) {
                    return null;
                }
                try {
                    lockRef = ObjectLockFactory.getInstance().acquireWriteLock(
                            messageManager,ObjectLockFactory.WAIT_ON_THREAD);
                } catch (Exception ex) {
                    log.debug("Cannot aquire a lock on this object because : " + 
                            ex.getMessage(),ex);
                    return null;
                }
                Date currentDate = new Date();
                Date nextProcessDate = messageManager.nextProcessTime();
                if (nextProcessDate == null) {
                    throw new MessageServiceException(
                        "The next process date is invalid cannot be null");
                } else if (nextProcessDate.getTime() <= currentDate.getTime()) {
                    baseQueue.poll();
                    processingEntries.put(messageManager,lockRef);
                    lockRef = null;
                    return messageManager;
                }
                nextRunTime.setTime(nextProcessDate.getTime());
                return null;
            } catch (Exception ex) {
                log.error("Failed to pop a message off the  queue : " + 
                        ex.getMessage(),ex);
                throw new MessageServiceException(
                        "Failed to pop a message off the  queue : " + 
                        ex.getMessage(),ex);
            } finally {
                try {
                    if (lockRef != null) {
                        lockRef.release();
                        lockRef = null;
                    }
                } catch (Exception ex2) {
                    log.error("Failed to release the lock :" + ex2.getMessage(),
                            ex2);
                }
            }
        }


        /**
         * This method returns the next message manager or null.
         *
         * @return The reference to message manager.
         * @param nextRunTime The date wrapper object.
         * @exception MessageServiceException
         */
        public synchronized void pushBackMessage(MessageManager messageManager) throws
                MessageServiceException {
            try {
                LockRef lockRef = (LockRef)processingEntries.get(messageManager);
                if (lockRef == null) {
                    log.error("This message is not locked : " + 
                            messageManager.getID());
                    throw new MessageServiceException(
                            "This message is not locked : " + 
                            messageManager.getID());
                }
                baseQueue.add(messageManager);
                processingEntries.remove(messageManager);
                lockRef.release();
            } catch (MessageServiceException ex) {
                throw ex;
            } catch (Exception ex) {
                log.error("Failed to push a message back in the queue for " +
                        "processing : " + ex.getMessage(),ex);
                throw new MessageServiceException(
                        "Failed to push a message back in the queue for " +
                        "processing : " + ex.getMessage(),ex);
            }
        }
        
    }
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(MessageQueue.class.getName());
    
    // private member variables
    private ThreadLocal transactionId = new ThreadLocal();
    private Map transactionChange = new ConcurrentHashMap();
    private IDIndex idIndex = new IDIndex();
    private QueueIndex queueIndex = new QueueIndex();
    private String name = null;
    
    /**
     * Creates a new instance of MessageQueue
     */
    public MessageQueue(String name) {
        this.name = name;
    }
    
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public synchronized void commit(Xid xid, boolean onePhase) throws
            XAException {
        try {
            log.debug("Commit the changes to the message queue");
            idIndex.commit(xid);
            queueIndex.commit(xid);
            transactionChange.remove(xid);
            ProcessMonitor.getInstance().notifyProcessor();
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
     * @param flags The flags associated with this operation.
     * @exception XAException
     */
    public void end(Xid xid, int flags) throws XAException {
    }
    
    
    /**
     * The transaction has been completed and must be forgotten.
     *
     * @param xid The id of the transaction to forget.
     * @exception XAException
     */
    public void forget(Xid xid) throws XAException {
        try {
            idIndex.forget(xid);
            queueIndex.forget(xid);
            transactionChange.remove(xid);
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
    public Xid[] recover(int flags) throws XAException {
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
            idIndex.rollback(xid);
            queueIndex.rollback(xid);
            transactionChange.remove(xid);
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
     * @param transactionTimeout The new transaction timeout value.
     * @exception XAException
     */
    public boolean setTransactionTimeout(int transactionTimeout) throws
            XAException {
        return true;
    }
    
    
    /**
     * This method is called to start a transaction on a resource manager.
     *
     * @param xid The id of the new transaction.
     * @param flags The flags associated with the transaction.
     * @exception XAException
     */
    public void start(Xid xid, int flags) throws XAException {
        try {
            if (!transactionChange.containsKey(xid)) {
                transactionChange.put(xid,new TransactionChange());
            }
            transactionId.set(xid);
            idIndex.start(xid);
            queueIndex.start(xid);
        } catch (Exception ex) {
            log.error("Failed to start the transaction : " + ex.getMessage(),ex);
            throw new XAException("Failed to start the transaction : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method returns the name of the message queue.
     *
     * @return The name of the message queue.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method adds a message to the message queue.
     *
     * @param message The message to add to the message queue.
     */
    public void addMessage(MessageManager message) throws
            MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            TransactionChange change = (TransactionChange)transactionChange.get(
                    transactionId.get());
            change.add(message);
            idIndex.addMessage(message);
        } catch (Exception ex) {
            log.error("Failed to add a message : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to add a message : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method removes the specified message from the list.
     *
     * @param messageId The id of the message to remove.
     */
    public void removeMessage(String messageId) throws
            MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            idIndex.removeMessage(messageId);
        } catch (Exception ex) {
            log.error("Failed to remove a message : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to remove a message : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method retrieves the specified message from the list.
     *
     * @param messageId The id of the message to retrieve.
     */
    public MessageManager getMessage(String messageId) throws
            MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            return idIndex.getMessage(messageId);
        } catch (Exception ex) {
            log.error("Failed to get a message : " + ex.getMessage(),ex);
            throw new MessageServiceException("Failed to get a message : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method returns the next message manager or null.
     *
     * @return The reference to message manager.
     * @param nextRunTime The date wrapper object.
     * @exception MessageServiceException
     */
    public MessageManager popFrontMessage(Date nextRunTime) throws
            MessageServiceException {
        return queueIndex.popFrontMessage(nextRunTime);
    }
    
    
    /**
     * This method returns the next message manager or null.
     *
     * @return The reference to message manager.
     * @param nextRunTime The date wrapper object.
     * @exception MessageServiceException
     */
    public void pushBackMessage(MessageManager messageManager) throws
            MessageServiceException {
        queueIndex.pushBackMessage(messageManager);
    }
}
