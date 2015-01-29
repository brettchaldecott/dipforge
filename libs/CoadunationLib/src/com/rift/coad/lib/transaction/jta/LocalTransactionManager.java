/*
 * CoadunationLib: The coaduntion library.
 * Copyright (C) 2015 Rift IT Contracting
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
 * LocalTransactionManager.java
 */

package com.rift.coad.lib.transaction.jta;


import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;


/**
 * This object is designed to manage local transactions within a container
 * 
 * @author brett chaldecott
 */
public class LocalTransactionManager implements TransactionManager {
    
    private ThreadLocal<LocalTransaction> transactions = new ThreadLocal<LocalTransaction>();
    
    /**
     * The default constructor for the dip transaction manager.
     */
    public LocalTransactionManager() {
        
    }
    
    
    /**
     * This method is called to begin the transaction
     * 
     * @throws NotSupportedException
     * @throws SystemException 
     */
    public void begin() throws NotSupportedException, SystemException {
        if (transactions.get() == null) {
            transactions.set(new LocalTransaction(this));
        } else {
            transactions.get().incrementReferenceCount();
        }
    }

    
    /**
     * This method is called to commit a transaction.
     * 
     * @throws RollbackException
     * @throws HeuristicMixedException
     * @throws HeuristicRollbackException
     * @throws SecurityException
     * @throws IllegalStateException
     * @throws SystemException 
     */
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        try {
            LocalTransaction transaction = transactions.get();
            if (transaction == null) {
                return;
            }
            int referenceCount = transaction.decrementReferenceCount();
            if (referenceCount <= 0) {
                if (transaction.isRollbackOnly()) {
                    throw new RollbackException("This exception is in rollback only status");
                }
                transaction.commit();
            }
        } catch (RollbackException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SystemException("Failed to commit the transaction : " + ex.getMessage());
        }
    }

    
    /**
     * This method is called to return the status of the existing transaction
     * 
     * @return The status of the transaction or STATUS_UNKNOWN if not know
     * @throws SystemException 
     */
    public int getStatus() throws SystemException {
        int status = Status.STATUS_NO_TRANSACTION;
        if (transactions.get() != null) {
            status = transactions.get().getStatus();
        }
        return status;
    }

    
    /**
     * This method is called to get a transaction.
     * 
     * @return The reference to the transaction or null if there is no transaction.
     * @throws SystemException 
     */
    public Transaction getTransaction() throws SystemException {
        return transactions.get();
    }

    
    /**
     * This method is not implemented.
     * 
     * @param t The transaction to resume.
     * @throws InvalidTransactionException
     * @throws IllegalStateException
     * @throws SystemException 
     */
    public void resume(Transaction t) throws InvalidTransactionException, IllegalStateException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * This method is responsible for calling rollback.
     * 
     * @throws IllegalStateException
     * @throws SecurityException
     * @throws SystemException 
     */
    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        LocalTransaction transaction = transactions.get();
        if (transaction == null) {
            return;
        }
        int referenceCount = transaction.decrementReferenceCount();
        if (referenceCount <= 0) {
            transaction.rollback();
        }
    }

    
    /**
     * This method is called to set rollback.
     * 
     * @throws IllegalStateException
     * @throws SystemException 
     */
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        LocalTransaction transaction = transactions.get();
        if (transaction == null) {
            return;
        }
        transaction.setRollbackOnly();
    }

    
    /**
     * This method is called to set the transaction timeout.
     * 
     * @param i The time out value
     * @throws SystemException 
     */
    public void setTransactionTimeout(int i) throws SystemException {
        // no transaction time out. will never expire.
    }

    /**
     * This method is not supported by a local transaction.
     * @return The transaction to suspend
     * @throws SystemException 
     */
    public Transaction suspend() throws SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
     * This method is called to remove a transaction
     */
    protected void remove() {
        transactions.remove();
    }
    
}
