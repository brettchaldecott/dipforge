/*
 * MessageService: The message service daemon
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
 * MessageQueueManager.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// logging import
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// coadunation imports
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.hibernate.util.HibernateUtil;


/**
 * This object is responsible for managing the message queues.
 *
 * @author Brett Chaldecott
 */
public class MessageQueueManager implements XAResource{
    
    /**
     * This object tracks the changes made by a transaction
     */
    public class Changes {
        // the class private member variables
        private Xid transactionId = null;
        private List<MessageQueue> queues = new ArrayList<MessageQueue>();
        private List locks = new ArrayList();
        
        
        /**
         * The constructor of the changes object.
         *
         * @param transactionId The id of the current transaction
         */
        public Changes(Xid transactionId) {
            this.transactionId = transactionId;
        }
        
        
        /**
         * This method adds a new message queue to the list of changes.
         *
         * @param messageQueue 
         */
        public void add(MessageQueue messageQueue, LockRef lockRef) throws
                MessageServiceException {
            try {
                lockRef.setLockName(transactionId);
                locks.add(lockRef);
                queues.add(messageQueue);
            } catch (Exception ex) {
                log.error("Failed to add the " +
                        "change entries : " + ex.getMessage(),ex);
                throw new MessageServiceException("Failed to add the " +
                        "change entries + " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns the list of queues.
         *
         * @return The list of queues.
         */
        public List getQueues() {
            return queues;
        }
        
        
        /**
         * This method returns the list of locks.
         *
         * @return The list of locks.
         */
        public List getLocks() {
            return locks;
        }

        /**
         * This method returns the queue with the specified name of null.
         *
         * @param name The name of the queue to retrieve.
         * @return The object to return.
         */
        public MessageQueue getQueue(String name) {
            for (MessageQueue queue : this.queues) {
                if (queue.getName().equals(name)) {
                    return queue;
                }
            }
            return null;
        }
    }
    
    // class constants
    public final static String UNSORTED = "UNSORTED";
    public final static String DEAD_LETTER = "DEAD_LETTER";
    
    // private singleton methods
    private static MessageQueueManager singleton = null;
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(MessageQueueManager.class.getName());
    
    
    // private member variables
    private ThreadLocal currentTransaction = new ThreadLocal();
    private Map keyLockMap = new HashMap();
    private Map messageQueues = new ConcurrentHashMap();
    private Map transactionChanges = new ConcurrentHashMap();
    private Vector listIndex = new Vector();
    private int pos = 0;
    
    /**
     * Creates a new instance of MessageQueueManager
     */
    private MessageQueueManager() {
    }
    
    
    /**
     * This method returns an instance of the MessageQueueManager singleton.
     *
     * @return An instance of the message queue manager.
     */
    public static synchronized MessageQueueManager getInstance() {
        if (singleton == null) {
            singleton = new MessageQueueManager();
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the message queue in questions.
     *
     * @return The reference to the message queue.
     * @param name The name of the message queue to return.
     * @exception MessageServiceException
     */
    public MessageQueue getQueue(String name) throws MessageServiceException {
        LockRef lockRef = null;
        try {
            TransactionManager.getInstance().bindResource(this,false);
            // check the changes for the current transaction
            Changes changes = (Changes)currentTransaction.get();
            if ((changes != null) && (changes.getQueue(name) != null)) {
                return changes.getQueue(name);
            }

            // get the lock ref
            lockRef = getLock(name);
            if (messageQueues.containsKey(name)) {
                MessageQueue messageQueue = 
                        (MessageQueue)messageQueues.get(name);
                lockRef.release();
                return messageQueue;
            }
            //MessageTransactionLock.getInstance().lock();
            Session session = HibernateUtil.
                    getInstance(MessageServiceManager.class).getSession();
            List list = session.createQuery("FROM MessageQueue AS queue " +
                    "WHERE queue.messageQueueName = ?").setString(0,name).list();
            MessageQueue queue = new MessageQueue(name);
            if (list.size() == 1) {
                com.rift.coad.daemon.messageservice.db.MessageQueue dbQueue =
                        (com.rift.coad.daemon.messageservice.db.MessageQueue)
                        list.get(0);
                if ((dbQueue.getNamed() != null) && 
                        (dbQueue.getNamed() == 1)) {
                    log.error("This is a named queue [" + name + 
                            "] and cannot be loaded into memory.");
                    throw new MessageServiceException
                            ("This is a named queue [" + name + 
                            "] and cannot be loaded into memory.");
                }
                messageQueues.put(name,queue);
                addQueueToIndex(queue);
                return queue;
            }

            com.rift.coad.daemon.messageservice.db.MessageQueue dbQueue = new
                    com.rift.coad.daemon.messageservice.db.MessageQueue(name);
            session.persist(dbQueue);
            changes = (Changes)currentTransaction.get();
            changes.add(queue,lockRef);
            lockRef = null;
            return queue;
        } catch (MessageServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve th message queue [" +name + "] : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException
                    ("Failed to retrieve th message queue [" +name + "] : " +
                    ex.getMessage(),ex);
        } finally {
            if (lockRef != null) {
                try {
                    lockRef.release();
                } catch (Exception ex2) {
                    log.error("Failed to unlock the queue [" + name + "] : " +
                        ex2.getMessage(),ex2);
                }
            }
        }
    }
    
    
    /**
     * This method returns the next message to process.
     *
     * @return NULL if no message is found, A message process object otherwise.
     * @param nextRunTime The next run time.
     * @exception MessageServiceException
     */
    public synchronized MessageProcessInfo getNextMessage(Date nextRunTime) 
            throws MessageServiceException {
        Vector index = cloneIndex();
        int currentPos = pos;
        Date currentDate = nextRunTime;
        MessageManager result = null;
        while (index.size() > 0) {
            currentPos++;
            if (currentPos >= index.size()) {
                currentPos = 0;
            }
            MessageQueue messageQueue = (MessageQueue)index.get(currentPos);
            Date nextDate = new Date();
            result = messageQueue.popFrontMessage(nextDate);
            if (result != null) {
                MessageProcessInfo messageProcessInfo = new 
                        MessageProcessInfo(messageQueue,result);
                return messageProcessInfo;
            }
            if ((currentDate == nextRunTime) || 
                    (currentDate.getTime() > nextDate.getTime())) {
                currentDate = nextDate;
            }
            if (currentPos == pos) {
                break;
            }
        }
        // set the next runtime delay
        nextRunTime.setTime(currentDate.getTime());
        
        // reset the pos
        pos = currentPos;
        
        // return the result
        return null;
    }
    
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        try {
            Changes changes = (Changes)transactionChanges.get(xid);
            transactionChanges.remove(xid);
            List queues = changes.getQueues();
            List locks = changes.getLocks();
            for (int index = 0; index < queues.size(); index++) {
                MessageQueue queue = (MessageQueue)queues.get(index);
                messageQueues.put(queue.getName(),queue);
                addQueueToIndex(queue);
            }
            for (int index = 0; index < locks.size(); index++) {
                LockRef lockRef = (LockRef)locks.get(index);
                lockRef.release();
            }
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
            transactionChanges.remove(xid);
            List locks = changes.getLocks();
            for (int index = 0; index < locks.size(); index++) {
                LockRef lockRef = (LockRef)locks.get(index);
                lockRef.release();
            }
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
            transactionChanges.remove(xid);
            List locks = changes.getLocks();
            for (int index = 0; index < locks.size(); index++) {
                LockRef lockRef = (LockRef)locks.get(index);
                lockRef.release();
            }
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
        if (transactionChanges.containsKey(xid)) {
            currentTransaction.set(transactionChanges.get(xid));
        } else {
            Changes changes = new Changes(xid);
            transactionChanges.put(xid,changes);
            currentTransaction.set(changes);
        }
    }
    
    
    /**
     * This method returns the named lock
     *
     * @return The reference to the lock.
     * @param The name of the queue that must be locked.
     * @exception MessageServiceException
     */
    private LockRef getLock(String name) throws MessageServiceException {
        try {
            Object key = null;
            synchronized(keyLockMap) {
                if (keyLockMap.containsKey(name)) {
                    key = keyLockMap.get(name);
                } else {
                    key = new String(name);
                    keyLockMap.put(name,key);
                }
            }
            LockRef lockRef = 
                    ObjectLockFactory.getInstance().acquireWriteLock(key);
            Changes changes = (Changes)currentTransaction.get();
            
            return lockRef;
        } catch (Exception ex) {
            log.error("Failed to retrieve a lock on the message queue : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException
                    ("Failed to retrieve a lock on the message queue : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to add an entry to the messsage queue
     */
    private void addQueueToIndex(MessageQueue messageQueue) {
        synchronized(listIndex) {
            listIndex.add(messageQueue);
        }
    }
    
    
    /**
     * This method is called to clone the index.
     *
     * @return The cloned index
     */
    private Vector cloneIndex() {
        synchronized(listIndex) {
            return (Vector)listIndex.clone();
        }
    }
}
