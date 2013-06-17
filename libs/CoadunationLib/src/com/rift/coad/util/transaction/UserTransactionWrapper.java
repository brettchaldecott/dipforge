/*
 * CoadunationLib: The coaduntion library.
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
 * UserTransactionWrapper.java
 */

// the package path
package com.rift.coad.util.transaction;

// java imports
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.transaction.TransactionManager;
import javax.transaction.Status;

// logging import
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
        private int lockCount = 0;
        private boolean committed = false;
        
        /**
         * The constructor of the transaction information object.
         */
        public TransactionInfo(boolean ownLock) {
            this.ownLock = ownLock;
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
        public boolean getCommited() {
            return committed;
        }
        
        
        /**
         * This method sets the committed flag to true.
         *
         * @exception TransactionException
         */
        public void commit() throws TransactionException {
            try {
                ut.commit();
            } catch (java.lang.NullPointerException ex) {
                log.error("Failed to commit the changes because of null " +
                        "pointer exception. Assuming cleanup was successfull : "
                        + ex.getMessage(),ex);
            } catch (javax.transaction.RollbackException ex) {
                log.error("Failed to commit the changes because of transaction " +
                        "transaction rollback : " + ex.getMessage(),ex);
                try {
                    ut.rollback();
                } catch (Exception ex2) {
                    log.error("Failed to roll back exception : " + 
                            ex.getMessage(),ex);
                }
                log.error("Ignoring errors and continuing");
            } catch (Exception ex) {
                log.error("Failed to commit the changes : "
                        + ex.getMessage(),ex);
                log.error("Ignoring errors and continuing");
            } finally {
                lockCount--;
                committed = true;
            }
            
        }
    }
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(UserTransactionWrapper.class.getName());
    
    // private member variables
    private Context context = null;
    private UserTransaction ut = null;
    private TransactionManager transactionManager = null;
    private ThreadLocal currentTransaction = new ThreadLocal();
    private int transactionTimout = 0;
    
    /**
     * Creates a new instance of UserTransactionWrapper
     */
    public UserTransactionWrapper() throws TransactionException {
        try {
            context = new InitialContext();
            ut = (UserTransaction)context.lookup("java:comp/UserTransaction");
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
            ut = (UserTransaction)context.lookup("java:comp/UserTransaction");
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
                if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
                    if (transactionTimout != 0) {
                        ut.setTransactionTimeout(transactionTimout);
                    }
                    ut.begin();
                    trans = new TransactionInfo(true);
                    currentTransaction.set(trans);
                } else {
                    trans = new TransactionInfo(false);
                    currentTransaction.set(trans);
                }
            }
            trans.lock();
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
                throw new TransactionException(
                        "There is no transaction for this thread");
            } else if (trans.getOwnLock() == false) {
                log.info("Commit called on transaction not owned by this object");
                return;
            } else if (trans.getLockCount() != 1) {
                throw new TransactionException(
                        "This transaction cannot be commit at this point as " +
                        "there are two many recursions. " +
                        "Must be commit at the top.");
            }
            trans.commit();
        } catch (TransactionException ex) {
            throw ex;
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
    public void release() {
        try {
            TransactionInfo trans = (TransactionInfo)currentTransaction.get();
            if (trans == null) {
                return;
            }
            if ((0 == trans.unlock()) && trans.getOwnLock() &&
                    !trans.getCommited() &&
                    (ut.getStatus() == Status.STATUS_ACTIVE)) {
                ut.rollback();
            }
            if (trans.getLockCount() == 0) {
                currentTransaction.set(null);
            }
        } catch (Exception ex) {
            log.warn("Failed to release the transaction : " +
                    ex.getMessage());
        }
    }
    
    
}
