/*
 * CoadunationLib: The coaduntion library.
 * Copyright (C) 2007 2015 Burntjam
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
 * UserTransactionWrapper.java
 */

// the package path
package com.rift.coad.util.transaction;

// java imports
import com.rift.coad.lib.transaction.TransactionManagerConnector;
import com.rift.coad.lib.transaction.TransactionManagerType;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;


/**
 * This object is responsible for wrapping the user transaction.
 *
 * @author Brett Chaldecott
 */
public class UserTransactionWrapper {
    
    /**
     * This object contains the information about a single transaction.
     */
    public class TransactionInfo {
        
        // private member variable
        private boolean ownLock = false;
        private int lockCount = 1;
        private boolean committed = false;
        private List<Transaction> transactions;
        
        
        
        /**
         * The constructor of the transaction information object.
         */
        public TransactionInfo(List<TransactionManager> transactionManagers) throws TransactionException {
            this.ownLock = true;
            try {
                transactions = new ArrayList<>();
                for (TransactionManager manager: transactionManagers) {
                    
                    try {
                        manager.begin();
                    } catch (Exception ex) {
                        // check type of transaction manager
                        // and attempt a recovery
                        if (manager instanceof org.objectweb.jotm.Current) {
                            log.error("If this is a JOTM transaction we will attempt to recover :" +
                                ex.getMessage(),ex);
                            // This is a nasty hack and very JOTM specific
                            // we are going to attempt to clear previous transactions
                            // from the current transaction manager reference
                            try {
                                org.objectweb.jotm.Current current = 
                                        (org.objectweb.jotm.Current)manager;
                                current.clearThreadTx();
                                manager.begin();
                            } catch (Exception ex2) {
                                log.error("Failed recover from JOTM internal error : " 
                                        + ex2.getMessage(),ex2);
                                throw ex;
                            }
                        } else {
                            throw ex;
                        }
                    }
                    Transaction transaction = manager.getTransaction();
                    
                    // if transaction is already committed attempt to create a new one.
                    // this is a hack to work around the problems seen with the
                    // data source.
                    if (transaction.getStatus() == Status.STATUS_COMMITTED) {
                        try {
                            transaction.commit();
                            manager.begin();
                            transaction = manager.getTransaction();
                        } catch (Exception ex) {
                            log.error("Failed to forcibly release "
                                    + "the transaction : " + ex.getMessage());
                            try {
                                transaction.rollback();
                                manager.begin();
                                transaction = manager.getTransaction();
                            } catch (Exception ex2) {
                                log.error("Failed to forcibly release "
                                    + "the transaction : " + ex.getMessage());
                            }
                        }
                    }
                    transactions.add(transaction);
                }
            } catch (Exception ex) {
                log.error("Failed to start the transaction : " + ex.getMessage(),ex);
                throw new TransactionException(
                    "Failed to start the transaction : " + ex.getMessage(),ex);
            }
        }
        
        
        /**
         * This method returns true if this object owns the lock.
         *
         * @return This method returns TRUE if this lock is owned buy this
         *      object.
         */
        public boolean getOwnLock() {
            return ownLock;
        }
        
        
        /**
         * This method returns the lock count for this transaction.
         *
         * @return The lock value.
         */
        public int lock() {
            return ++lockCount;
        }
        
        
        /**
         * This method returns the lock count.
         *
         * @return The lock value.
         */
        public int unlock() {
            if (lockCount == 0) {
                return lockCount;
            }
            return --lockCount;
        }
        
        
        /**
         * This method returns the lock count for the current transaction.
         *
         * @return The current value of the lock count.
         */
        public int getLockCount() {
            return lockCount;
        }
        
        
        /**
         * This method returns true if this object is committed.
         *
         * @return TRUE if committed, FALSE if not.
         */
        public boolean getCommitted() {
            return committed;
        }
        
        
        /**
         * This method is called to set the commit flag
         * @param committed 
         */
        public void setComitted(boolean committed) {
            this.committed = committed;
        }
        
        
        /**
         * This method sets the committed flag to true.
         *
         * @exception TransactionException
         */
        public void commit() throws Exception {
            Exception exception = null;
            for (Transaction transaction : transactions) {
                try {
                    transaction.commit();
                } catch (Exception ex) {
                    log.error("Failed to commit the transaction : " + ex.getMessage(),ex);
                    // cannot recover from a failed commit
                    // JOTM internal doesnt support this
                    // once commited the transaction is lost and removed
                    // cannot roll back
//                    try {
//                        if (transaction.getStatus() != Status.STATUS_ROLLEDBACK
//                          && transaction.getStatus() != Status.STATUS_COMMITTED) {
//                            log.info("Calling rollback to attempt to undo the changes");
//                            transaction.rollback();
//                            log.info("After calling rollback to attempt to undo the changes");
//                        }
//                    } catch (Exception ex2) {
//                        log.error("Failed to rollback a failed commit : " +
//                                ex2.getMessage(),ex2);
//                    }
                    if (exception == null) {
                       exception = ex; 
                    }
                }
            }
            
            
            if (exception != null) {
                throw exception;
            }
        }
        
        
        /**
         * This method is called to rollback the transaction
         * 
         * @throws Exception 
         */
        public void rollback() throws Exception{
            Exception exception = null;
            for (Transaction transaction : transactions) {
                try {
                    transaction.rollback();
                } catch (Exception ex) {
                    log.error("Failed to rollback the transaction : " + ex.getMessage(),ex);
                    if (exception == null) {
                        exception = ex;
                    }
                }
            }
            
            if (exception != null) {
                throw exception;
            }
        }
    }
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(UserTransactionWrapper.class.getName());
    
    // private member variables
    private Context context = null;
    private TransactionManager transactionManager = null;
    private TransactionManager localTransactionManager = null;
    private ThreadLocal currentTransaction = new ThreadLocal();
    private int transactionTimout = 60 * 60 * 1000;
    
    /**
     * Creates a new instance of UserTransactionWrapper
     */
    public UserTransactionWrapper() throws TransactionException {
        try {
            context = new InitialContext();
            transactionManager = TransactionManagerConnector.getTransactionManager(TransactionManagerType.GLOBAL);
            localTransactionManager = TransactionManagerConnector.getTransactionManager(TransactionManagerType.LOCAL);
        } catch (Exception ex) {
            throw new TransactionException("Failed to instanciate the " +
                    "UserTransactionWrapper because : " + ex.getMessage(),ex);
        }
        
    }
    
    
    /**
     * Creates a new instance of UserTransactionWrapper
     */
    public UserTransactionWrapper(int transactionTimeout) 
    throws TransactionException {
        try {
            context = new InitialContext();
            transactionManager = TransactionManagerConnector.getTransactionManager(TransactionManagerType.GLOBAL);
            localTransactionManager = TransactionManagerConnector.getTransactionManager(TransactionManagerType.LOCAL);
            this.transactionTimout = transactionTimeout;
        } catch (Exception ex) {
            throw new TransactionException("Failed to instanciate the " +
                    "UserTransactionWrapper because : " + ex.getMessage(),ex);
        }
        
    }
    
    
    /**
     * This method begins a transaction for a thread, if one is not already
     * running.
     *
     * @exception TransactionException
     */
    public void begin() throws TransactionException {
        try {
            TransactionInfo trans = (TransactionInfo)currentTransaction.get();
            if (trans == null) {
                List<TransactionManager> managers = new ArrayList<>();
                
                // check if there is not transaction or if the 
                // transaction associated with this thread has allready been
                // committed. If commited we need to start a new transaction
                // ass all changes will fail to commit
                if (transactionManager.getStatus() == Status.STATUS_NO_TRANSACTION || 
                        transactionManager.getStatus() == Status.STATUS_COMMITTED || 
                        transactionManager.getStatus() == Status.STATUS_ROLLEDBACK) {
                    if (transactionTimout != 0) {
                        transactionManager.setTransactionTimeout(transactionTimout);
                    }
                    managers.add(transactionManager);
                }
                if (localTransactionManager.getStatus() == Status.STATUS_NO_TRANSACTION) {
                    managers.add(localTransactionManager);
                }
                trans = new TransactionInfo(managers);
                currentTransaction.set(trans);
            } else {
                trans.lock();
            }
        } catch (TransactionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TransactionException("Failed to start the transaction : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method commits the transaction
     *
     * @exception TransactionException
     */
    public void commit() throws TransactionException {
        try {
            TransactionInfo trans = (TransactionInfo)currentTransaction.get();
            if (trans == null) {
                // there is no transaction for this thread ignore
                return;
            } else if (trans.getOwnLock() == false) {
                log.debug("Commit called on transaction not owned by this object");
                return;
            } 
            trans.setComitted(true);
        } catch (Exception ex) {
            throw new TransactionException("Failed to start the transaction : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to release a lock on a transaction and will result
     * in rollback if the transaction is not commited
     *
     * @exception TransactionException;
     */
    public int release() {
        try {
            TransactionInfo trans = (TransactionInfo)currentTransaction.get();
            if (trans == null) {
                // there is no transaction for this thread ignore
                return 0;
            }
            int lockCount = trans.unlock();
            if ((0 >= lockCount) && trans.getOwnLock()) {
                // commit the transaction if need be
                try{
                    if (transactionManager.getStatus() == Status.STATUS_ACTIVE) {
                        if (trans.getCommitted()) {
                            trans.commit();
                        // roll back if not set to commit
                        } else {
                            trans.rollback();
                        }
                    }
                } finally {
                    currentTransaction.remove();
                }
            }
            return lockCount;
        } catch (Exception ex) {
            log.warn("Failed to release the transaction : " +
                    ex.getMessage());
            return -1;
        }
    }
    
    
}
