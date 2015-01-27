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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


/**
 * The implementation of the local transaction.
 * 
 * @author brett chaldecott
 */
public class LocalTransaction implements Transaction {
    
    public class TransactionId implements Xid {

        private int id;
        
        /**
         * 
         * @param id 
         */
        public TransactionId (Object id) {
            id = System.identityHashCode(id);
        }
        
        /**
         * This method returns the formated id
         * 
         * @return The id
         */
        @Override
        public int getFormatId() {
            return id;
        }

        
        /**
         * The global transaction id
         * 
         * @return The id of the transaction
         */
        @Override
        public byte[] getGlobalTransactionId() {
            return ByteBuffer.allocate(4).putInt(id).array();
        }

        
        /**
         * The branch qualifier.
         * 
         * @return The reference to the 
         */
        @Override
        public byte[] getBranchQualifier() {
            return ByteBuffer.allocate(4).putInt(id).array();
        }
        
    }
    
    // private member variables
    private boolean rollbackOnly = false;
    private int referenceCount = 1;
    private int status;
    private TransactionId id;
    private List<Synchronization> syncs = new ArrayList<>();
    private List<XAResource> resources = new ArrayList<>();
    
    /**
     * The local transaction
     */
    public LocalTransaction() {
        status = Status.STATUS_ACTIVE;
        id = new TransactionId(this);
    }

    
    /**
     * The method invoked to call the commit.
     * 
     * @throws RollbackException
     * @throws HeuristicMixedException
     * @throws HeuristicRollbackException
     * @throws SecurityException
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        if (rollbackOnly) {
            throw new RollbackException("Rollback only is set cannot commit");
        }
        try {
            status = Status.STATUS_COMMITTING;
            for(Synchronization sync : syncs) {
                sync.beforeCompletion();
            }
            for (XAResource resource : resources) {
                resource.prepare(id);
                resource.commit(id, true);
            }
            resources.clear();
            
            status = Status.STATUS_COMMITTED;
            for(Synchronization sync : syncs) {
                sync.afterCompletion(status);
            }
        } catch (Exception ex) {
            status = Status.STATUS_UNKNOWN;
            throw new SystemException("Failed to commit : " + ex.getMessage());
        }
    }

    
    /**
     * This method is called to delist a transaction.
     * 
     * @param xar The xar reference to delist.
     * @param i The 
     * @return
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public boolean delistResource(XAResource xar, int i) throws IllegalStateException, SystemException {
        this.resources.remove(xar);
        return true;
    }

    
    /**
     * This method is called to enlist a resource in a transaction.
     * @param xar The reference to the enlisted resource.
     * @return TRUE if enlisted, FALSE if not.
     * @throws RollbackException
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public boolean enlistResource(XAResource xar) throws RollbackException, IllegalStateException, SystemException {
        if (rollbackOnly) {
            throw new RollbackException("Cannot enlist as this transaction is set to rollback only");
        }
        try {
            // check if the resource has already been added. If so ignore it.
            if (this.resources.contains(xar)) {
                return true;
            }
            xar.start(id, XAResource.TMNOFLAGS);
            this.resources.add(xar);
            return true;
        } catch (Exception ex) {
            throw new SystemException("Failed to enlist the resource in the transaction : " + ex.getMessage());
        }
    }

    
    /**
     * The status of the transaction.
     * 
     * @return The status of the transaction
     * @throws SystemException 
     */
    @Override
    public int getStatus() throws SystemException {
        return status;
    }

    
    /**
     * This method is responsible for registering a transaction.
     * 
     * @param s The synchronization object.
     * @throws RollbackException
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {
        this.syncs.add(sync);
    }

    
    /**
     * 
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public void rollback() throws IllegalStateException, SystemException {
        try {
            status = Status.STATUS_ROLLING_BACK;
            for (XAResource resource : resources) {
                resource.rollback(id);
            }
            resources.clear();
            status = Status.STATUS_ROLLEDBACK;
        } catch (Exception ex) {
            status = Status.STATUS_UNKNOWN;
            throw new SystemException("Failed to rollback : " + ex.getMessage());
        }
    }

    
    /**
     * This method is set for rollback.
     * 
     * @throws IllegalStateException
     * @throws SystemException 
     */
    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {
        this.rollbackOnly = true;
    }
    
    /**
     * This method is called to determine if the rollback only flag is set.
     * 
     * @return True if rollback only is set.
     */
    public boolean isRollbackOnly() {
        return rollbackOnly;
    }
    
    // rollback variables
    /**
     * This method returns the reference count
     * @return 
     */
    public int getReferenceCount() {
        return referenceCount;
    }

    
    /**
     * This method is called to set the reference count.
     * 
     * @param referenceCount The reference count.
     */
    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }
    
    
    /**
     * This method is called to increment the reference count.
     * 
     * @return The incremented reference count.
     */
    public int incrementReferenceCount() {
        return ++referenceCount;
    }
    
    
    /**
     * This method is called to decrement the reference count.
     * 
     * @return The decrement reference count
     */
    public int decrementReferenceCount() {
        return --referenceCount;
    }
    
}
