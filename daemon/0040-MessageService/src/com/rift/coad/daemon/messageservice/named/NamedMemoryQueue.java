/*
 * MessageService: The message service daemon
 * Copyright (C) 2007 Rift IT Contracting
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
 * NamedMemoryQueue.java
 */

// package path
package com.rift.coad.daemon.messageservice.named;

// java imports
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.messageservice.Message;
import com.rift.coad.daemon.messageservice.MessageServiceException;
import com.rift.coad.daemon.messageservice.MessageManager;
import com.rift.coad.daemon.messageservice.MessageServiceManager;
import com.rift.coad.daemon.messageservice.ProcessMonitor;
import com.rift.coad.daemon.messageservice.message.MessageManagerFactory;
import com.rift.coad.daemon.messageservice.db.*;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.util.transaction.TransactionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;
        
/**
 * This object is responsible for acting as the none volitile part of the named
 * queue.
 *
 * @author Brett Chaldecott
 */
public class NamedMemoryQueue implements XAResource {
    
    /**
     * This object tracks the changes to a 
     */
    public class Changes {
        
        // private member variables
        private List removeList = new ArrayList();
        private List addList = new ArrayList();
        
        
        /**
         * The changes made in a transaction
         */
        public Changes() {
        }
        
        
        /**
         * This method adds an id to the list.
         */
        public void addRemoveMessage(MessageManager messageManager) {
            removeList.add(messageManager);
        }
        
        
        /**
         * This method returns the ids of the messages.
         *
         * @return The list of ids.
         */
        public List getRemoveList() {
            return removeList;
        }
        
        /**
         * This method add a message to the list of messages
         */
        public void addNewMessage(MessageManager messageManager) {
            addList.add(messageManager);
        }
        
        /**
         * This method returns the list of message that have been added in the
         * transaction.
         *
         * @return The list of ids.
         */
        public List getAddList() {
            return addList;
        }
    }
    
    
    // singleton methods
    private static Map singletons = new ConcurrentHashMap();
    private static Map keyIndex = new HashMap();
    
    protected static Logger log =
            Logger.getLogger(NamedMemoryQueue.class.getName());
    
    // private member variables
    private String queueName = null;
    private Queue queue = new ConcurrentLinkedQueue();
    private UserTransactionWrapper utw = null;
    private Map changes = new ConcurrentHashMap();
    private ThreadLocal currentTransaction = new ThreadLocal();
    
    /** 
     * Creates a new instance of QueueMemoryIndex 
     *
     * @param queueName The name of the queue to instanciate.
     * @exception MessageServiceException
     */
    public NamedMemoryQueue(String queueName) throws MessageServiceException {
        this.queueName = queueName;
    }
    
    
    /**
     * This object is responsible for returning returning an instance of the
     * named memory queue identified by the queuename. If not found it returns
     * null.
     *
     * @return The reference to the named memory queueu.
     * @param queueName The name of the queue to retrieve.
     * @exception MessageServiceException
     */
    public static NamedMemoryQueue getInstance(String queueName) throws 
            MessageServiceException {
        Object syncObj = getSyncObject(queueName);
        synchronized(syncObj) {
            NamedMemoryQueue singleton = 
                    (NamedMemoryQueue)singletons.get(queueName);
            if (singleton == null) {
                singleton = new NamedMemoryQueue(queueName);
                singletons.put(queueName,singleton);
            }
            return singleton;
        }
    }
    
    
    /**
     * This method lists the queues currently in memory.
     *
     * @param list The list of named queues.
     * @exception MessageServiceException
     */
    public static List listQueues() throws MessageServiceException {
        return new ArrayList(singletons.keySet());
    }
    
    
    /**
     * This method returns the synchronization key.
     *
     * @return The reference to the object that the synchronization can be done
     *      on.
     * @param queueName The name of the queue.
     */
    private static synchronized Object getSyncObject(String queueName) {
        Object syncObj = keyIndex.get(queueName);
        if (syncObj == null) {
            syncObj = new String(queueName);
            keyIndex.put(queueName,syncObj);
        }
        return syncObj;
    }
    
    
    /**
     * This method adds a message to the queue.
     *
     * @param messageManager The message manager.
     */
    public void addMessage(MessageManager messageManager) throws 
            MessageServiceException {
        try {
            TransactionManager.getInstance().bindResource(this,false);
            ((Changes)currentTransaction.get()).addNewMessage(
                    messageManager);
        } catch (Exception ex) {
            log.error("Failed to add a message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException("Failed to add a message : " +
                    ex.getMessage(),ex);
        }
    }
        
    
    /**
     * This method returns the first message on the list.
     */
    public synchronized Message poll(long delay) throws 
            MessageServiceException {
        try {
            Date startTime = new Date();
            while (queue.size() == 0) {
                Date currentTime = new Date();
                long difference = (startTime.getTime() + delay) - 
                        currentTime.getTime();
                if (difference <= 0) {
                    return null;
                }
                wait(difference);
            }
            TransactionManager.getInstance().bindResource(this,false);
            MessageManager messageManager = (MessageManager)queue.poll();
            Message message = messageManager.getMessage();
            messageManager.remove();
            ((Changes)currentTransaction.get()).addRemoveMessage(
                    messageManager);
            log.debug("Return the message : " + message.getMessageId());
            return message;
        } catch (MessageServiceException ex) {
            log.error("Failed to poll for a message : " +
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to poll for a message : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to poll for a message : " + 
                    ex.getMessage(),ex);
        } catch (Throwable ex) {
            log.error("Caught an unexpected exception : " +
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Caught an unexpected exception : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the list of messages for this queue.
     *
     * @return The list of messages in the queue.
     * @exception MessageServiceException
     */
    public synchronized List getMessages() throws MessageServiceException {
        try {
            List list = new ArrayList();
            for (Iterator iter = queue.iterator(); iter.hasNext();) {
                MessageManager messageManager = (MessageManager)iter.next();
                list.add(messageManager.getMessage());
            }
            return list;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of messages : " + 
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to retrieve the list of messages : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for purging the contents of the named message
     * queue.
     *
     * @exception MessageServiceException
     */
    public synchronized void purge()  throws MessageServiceException {
        try {
            for (Iterator iter = queue.iterator(); iter.hasNext();) {
                MessageManager messageManager = (MessageManager)iter.next();
                messageManager.remove();
            }
            queue.clear();
        } catch (Exception ex) {
            log.error("Failed to purge the queue : " + 
                    ex.getMessage(),ex);
            throw new MessageServiceException(
                    "Failed to purge the queue : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to commit the specified transaction.
     *
     * @param xid The id of the transaction to commit.
     * @param onePhase If true a one phase commit should be used.
     * @exception XAException
     */
    public void commit(Xid xid, boolean b) throws XAException {
        Changes changes = (Changes)this.changes.remove(xid);
        for (Iterator iter = changes.getAddList().iterator(); iter.hasNext();) {
            queue.add(iter.next());
        }
        synchronized(this) {
            notifyAll();
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
        changes.remove(xid);
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
        Changes changes = (Changes)this.changes.get(xid);
        if (changes == null) {
            return;
        }
        for (Iterator iter = changes.getRemoveList().iterator(); iter.hasNext();) {
            queue.add(iter.next());
        }
        synchronized (this) {
            notifyAll();
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
        Changes changes = (Changes)this.changes.get(xid);
        if (changes == null) {
            changes = new Changes();
            this.changes.put(xid,changes);
        }
        currentTransaction.set(changes);
    }
}
