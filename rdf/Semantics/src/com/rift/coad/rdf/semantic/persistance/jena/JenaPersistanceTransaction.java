/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * PersistanceSession.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.Lock;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceTransaction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 * This interface defines the methods to manage a transaction on behalf of a
 * persistance session.
 *
 * @author brett chaldecott
 */
public class JenaPersistanceTransaction implements PersistanceTransaction {

    /**
     * The lock manager
     */
    public static class LockManager {
        
        // class singleton
        private static Map<Model,LockManager> singleton = 
                new ConcurrentHashMap<Model,LockManager>();
        
        // private member variables
        private ThreadLocal<Integer> currentCount = new ThreadLocal<Integer>();
        
        /**
         * This is the private lock manager constructor.
         */
        private LockManager() {
            
        }
        
        
        /**
         * The method to get the reference to the lock manager singleton.
         * 
         * @param jenaModel The model to identify the reference.
         * @return The reference to the object.
         */
        public static synchronized LockManager getInstance(Model jenaModel) {
            LockManager result = null;
            if (!singleton.containsKey(jenaModel)) {
                log.info("######## Create new lock manager for model");
                result = new LockManager();
                singleton.put(jenaModel, result);
            } else {
                log.info("######## Create new lock manager for model");
                result = singleton.get(jenaModel);
            }
            return result;
        }
        
        
        /**
         * This method is called to increment the lock count on a particular jena model.
         * 
         * @return The incremented count.
         */
        public int incrementLockCount() {
            Integer count = this.currentCount.get();
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            this.currentCount.set(count);
            return count;
        }
        
        
        /**
         * This method is called to decrement the reference count.
         * 
         * @return The reference count for this thread on the model.
         */
        public int decrementLockCount() {
            Integer count = this.currentCount.get();
            if (count == null) {
                return 0;
            }
            count--;
            this.currentCount.set(count);
            return count;
        }
    }
    
    // static member variables
    private static Logger log = Logger.getLogger(JenaPersistanceTransaction.class);
    
    // private member variables
    private Model jenaModel;
    private boolean inTransaction = false;
    private SessionManager.SessionLock lock;
    

    /**
     * The constructor responsible for creating the jena transaction.
     *
     * @param jenaModel The reference to the jena model.
     * @param type The type of store to utilize
     */
    protected JenaPersistanceTransaction(Model jenaModel, JenaStoreType type) {
        this.jenaModel = jenaModel;
        // sets up the default read lock
        if (type == JenaStoreType.XML) {
            this.lock = SessionManager.SessionLock.NO_LOCK;
        } else {
            this.lock = SessionManager.SessionLock.READ_LOCK;
        }
    }
    
    
    /**
     * The constructor responsible for creating the jena transaction.
     *
     * @param jenaModel The reference to the jena model.
     * @param lock The type of lock to utilize on this session.
     */
    protected JenaPersistanceTransaction(Model jenaModel, 
            SessionManager.SessionLock lock) {
        
        this.jenaModel = jenaModel;
        this.lock = lock;
    }

    /**
     * This method is called to being the transaction scope.
     *
     * @throws PersistanceException
     */
    public void begin() throws PersistanceException {
        if (inTransaction) {
            throw new PersistanceException(
                    "Transaction already started for this object.");
        }
        try {
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                int count = LockManager.getInstance(jenaModel).incrementLockCount();
                if (count == 1) {
                    if (lock == SessionManager.SessionLock.READ_LOCK) {
                        jenaModel.enterCriticalSection(Lock.READ);
                    } else if (lock == SessionManager.SessionLock.WRITE_LOCK) {
                        jenaModel.enterCriticalSection(Lock.WRITE);
                    }
                    jenaModel.begin();
                }
            }
            inTransaction = true;
        } catch (Exception ex) {
            log.error("Failed to begin the changes : " + ex.getMessage());
        }
    }



    /**
     * This method is responsible for commiting the transaction.
     *
     * @throws PersistanceException
     */
    public void commit() throws PersistanceException {
        try {
            jenaModel.commit();
        } catch (Exception ex) {
            log.error("Failed to commit the changes : " + ex.getMessage());
        } finally {
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                int count = LockManager.getInstance(jenaModel).decrementLockCount();
                if (count <= 0 && (lock == SessionManager.SessionLock.READ_LOCK || 
                        lock == SessionManager.SessionLock.WRITE_LOCK)) {
                    try {
                        jenaModel.leaveCriticalSection();
                    } catch (Exception ex) {
                        log.error("Failed to release the critical lock : " + 
                                ex.getMessage());
                    }
                }
            }
        }
    }


    /**
     * This method is responsible for rolling back the transactional changes.
     * 
     * @throws PersistanceException
     */
    public void rollback() throws PersistanceException {
        try {
            jenaModel.abort();
        } catch (Exception ex) {
            log.error("Failed to commit the changes : " + ex.getMessage());
        } finally {
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                int count = LockManager.getInstance(jenaModel).decrementLockCount();
                if (count <= 0 && (lock == SessionManager.SessionLock.READ_LOCK || 
                        lock == SessionManager.SessionLock.WRITE_LOCK)) {
                    try {
                        jenaModel.leaveCriticalSection();
                    } catch (Exception ex) {
                        log.error("Failed to release the critical lock : " + 
                                ex.getMessage());
                    }
                }
            }
        }
    }


    /**
     * TRUE if transaction has been started for this object, FALSE if not.
     *
     * @return TRUE if a transaction has been started.
     * @throws PersistanceException
     */
    public boolean isInTransaction() throws PersistanceException {
        return inTransaction;
    }
}
