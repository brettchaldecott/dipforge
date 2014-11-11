/*
 * CoadunationLib: The coaduntion library.
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
 * TransactionManager.java
 */

// package path
package com.rift.coad.util.transaction;

// java imports
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Vector;
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
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.lock.LockRef;
import com.rift.coad.util.lock.ObjectLockFactory;

/**
 * This object is responsible for managing the transactions for a group of
 * objects.
 *
 * @author Brett Chaldecott
 */
public class TransactionManager implements XAResource {
    
    /**
     * The transaction object, responsible for managing an individual
     * transaction.
     */
    public class Transaction {
        
        // private member variables
        private Xid xid = null;
        private Map transactionEntries = new LinkedHashMap();
        private Vector transactionQueue = new Vector();
        
        /**
         * The constructor of the transaction object.
         *
         * @param xid The id of this transaction.
         */
        public Transaction(Xid xid) {
            this.xid = xid;
        }
        
        
        /**
         * This method adds a resource to the transaction for tracking.
         *
         * @param xaResource The reference to the resource to add to this
         *          transaction for tracking.
         * @param writeLock If true a write lock must be aquired
         * @exception TransactionException
         */
        public void addResource(XAResource xaResource, boolean writeLock) throws
                TransactionException {
            try {
                LockRef lockRef = (LockRef)transactionEntries.get(xaResource);
                int flags = XAResource.TMRESUME;
                if (lockRef == null) {
                    if (writeLock) {
                        lockRef = ObjectLockFactory.getInstance().acquireWriteLock(
                                xaResource,xid,ObjectLockFactory.WAIT_ON_NAMED);
                    } else {
                        lockRef = ObjectLockFactory.getInstance().acquireReadLock(
                                xaResource,xid);
                    }
                    transactionEntries.put(xaResource,lockRef);
                    transactionQueue.add(xaResource);
                    flags = XAResource.TMJOIN;
                } else if (writeLock && (lockRef.getLockType() == LockRef.READ)) {
                    throw new TransactionException("Lock Conflict: There is a " +
                            "read lock in place on this object " +
                            "for this transaction.");
                } else if (!writeLock && (lockRef.getLockType() == LockRef.WRITE)) {
                    throw new TransactionException("Lock Conflict: There is a " +
                            "write lock in place on this object " +
                            "for this transaction.");
                }
                xaResource.start(xid,flags);
            } catch (TransactionException ex) {
                throw ex;
            } catch (Exception  ex) {
                log.error("Failed to add a new resource : " + ex.getMessage(),
                        ex);
                throw new TransactionException(
                        "Failed to add a new resource : " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method is responsible for commiting this transaction
         *
         * @param onePhase If true this transaction must commit in a single 
         *      phase.
         * @exception TransactionException
         */
        public void commit(boolean onePhase) throws TransactionException {
            try {
                for (int index = 0; index < transactionQueue.size(); index++) {
                    Object ref = transactionQueue.get(index);
                    if (ref instanceof XAResource) {
                        ((XAResource)ref).commit(xid,onePhase);
                    }
                }
                for (int index = 0; index < transactionQueue.size(); index++) {
                    LockRef lockRef = (LockRef)transactionEntries.get(
                            transactionQueue.get(index));
                    lockRef.release();
                }
                
            } catch (Exception  ex) {
                log.error("Failed to commit the resource transaction : " + 
                        ex.getMessage(),ex);
                throw new TransactionException(
                        "Failed to commit the resource transaction : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method is responsible for commiting this transaction
         *
         * @exception TransactionException
         */
        public void forget() throws TransactionException {
            try {
                for (int index = 0; index < transactionQueue.size(); index++) {
                    Object ref = transactionQueue.get(index);
                    if (ref instanceof XAResource) {
                        ((XAResource)ref).forget(xid);
                    }
                }
                for (int index = 0; index < transactionQueue.size(); index++) {
                    LockRef lockRef = (LockRef)transactionEntries.get(
                            transactionQueue.get(index));
                    lockRef.release();
                }
                
            } catch (Exception  ex) {
                log.error("Failed to forget the resource transaction: " 
                        + ex.getMessage(),ex);
                throw new TransactionException(
                        "Failed to forget the resource transaction : " + 
                        ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method is called to roll back the specified transaction.
         *
         * @param xid The id of the transaction to roll back.
         * @exception XAException
         */
        public void rollback() throws TransactionException {
            try {
                for (int index = 0; index < transactionQueue.size(); index++) {
                    int pos = transactionQueue.size() - (index + 1);
                    Object ref = transactionQueue.get(pos);
                    if (ref instanceof XAResource) {
                        ((XAResource)ref).rollback(xid);
                    }
                }
                for (int index = 0; index < transactionQueue.size(); index++) {
                    int pos = transactionQueue.size() - (index + 1);
                    LockRef lockRef = (LockRef)transactionEntries.get(
                            transactionQueue.get(pos));
                    lockRef.release();
                }
                
            } catch (Exception  ex) {
                log.error("Failed to forget the resource transaction: " 
                        + ex.getMessage(),ex);
                throw new TransactionException(
                        "Failed to forget the resource transaction : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    // class constants
    private final static String TIMEOUT = "transaction_timeout";
    // the default infinit transaction timout value.
    private final static long DEFAULT_TIMEOUT = 60 * 60 * 100;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(TransactionManager.class.getName());
    
    // the singleton object.
    private static Map singletonMap = new HashMap();
    
    // private member variables
    private Context context = null;
    private int timeout = 0;
    private javax.transaction.TransactionManager jtaTransManager = null;
    private ThreadLocal threadXID = new ThreadLocal();
    private Map transactions = new ConcurrentHashMap();
    
    
    /**
     * Creates a new instance of TransactionManager
     */
    private TransactionManager() throws TransactionException {
        try {
            context = new InitialContext();
            jtaTransManager = (javax.transaction.TransactionManager)context.
                    lookup("java:comp/TransactionManager");
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            timeout = (int)config.getLong(TIMEOUT,DEFAULT_TIMEOUT);
        } catch (Exception ex) {
            log.error("Failed to log init the transaction manager because : " +
                    ex.getMessage(),ex);
            throw new TransactionException(
                    "Failed to log init the transaction manager because : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a new object lock factory singleton for a class 
     * loader.
     *
     * @exception TransactionException
     */
    public synchronized static void init() throws TransactionException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (!singletonMap.containsKey(loader)) {
            singletonMap.put(loader,new TransactionManager());
        }
    }
    
    
    /**
     * This method returns an instance of the transaction manager singleton.
     *
     * @return An instance of the transaction manager singleton.
     */
    public synchronized static TransactionManager getInstance() throws
            TransactionException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        TransactionManager singleton = null;
        if (singletonMap.containsKey(loader)) {
            singleton = (TransactionManager)singletonMap.get(loader);
        } else {
            throw new TransactionException("There is no transaction manager " +
                    "for this class loader");
        }
        return singleton;
    }
    
    
    /**
     * This method removes the object lock factory associated with a class 
     * loader.
     *
     * @exception TransactionException
     */
    public synchronized static void fin() throws TransactionException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (singletonMap.containsKey(loader)) {
            singletonMap.remove(loader);
        }
    }
    
    
    /**
     * This method binds a resource to the current transaction bound to this
     * thread.
     *
     * @param xaResource The reference to the xaResource.
     * @param writeLock If true the object must aquire a write lock.
     * @exception TransactionException
     */
    public void bindResource(XAResource xaResource, boolean writeLock) throws
            TransactionException {
        try {
            jtaTransManager.getTransaction().enlistResource(this);
            Xid currentTransactionId = (Xid)threadXID.get();
            Transaction transaction = (Transaction)transactions.get(
                    currentTransactionId);
            if (transaction == null) {
                transaction = new Transaction(currentTransactionId);
                transactions.put(currentTransactionId,transaction);
            }
            transaction.addResource(xaResource,writeLock);
        } catch (Exception ex) {
            log.error("Failed to bind the resource to the transaction " +
                    "manager : " + ex.getMessage(),ex);
            throw new TransactionException(
                    "Failed to bind the resource to the transaction " +
                    "manager : " + ex.getMessage(),ex);
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
    public void commit(Xid xid, boolean onePhase) throws XAException {
        try {
            Transaction transaction = (Transaction)transactions.get(xid);
            if (transaction != null) {
                transaction.commit(onePhase);
                transactions.remove(xid);
            }
        } catch (Exception ex) {
            log.error("Failed to commit the transaction :" + ex.getMessage(),ex);
            throw new XAException("Failed to commit the transaction :" + 
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
            Transaction transaction = (Transaction)transactions.get(xid);
            if (transaction != null) {
                transaction.forget();
                transactions.remove(xid);
            }
        } catch (Exception ex) {
            log.error("Failed to forget the transaction :" + ex.getMessage(),ex);
            throw new XAException("Failed to forget the transaction :" + 
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
        return timeout;
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
            Transaction transaction = (Transaction)transactions.get(xid);
            if (transaction != null) {
                transaction.rollback();
                transactions.remove(xid);
            }
        } catch (Exception ex) {
            log.error("Failed to rollback the transaction :" + 
                    ex.getMessage(),ex);
            throw new XAException("Failed to rollback the transaction :" + 
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
        this.timeout = transactionTimeout;
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
        threadXID.set(xid);
    }
    
    
}
