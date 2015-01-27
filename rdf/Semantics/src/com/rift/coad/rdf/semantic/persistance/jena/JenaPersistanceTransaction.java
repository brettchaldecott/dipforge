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
        private static Map<JenaModelWrapper,LockManager> singleton = 
                new ConcurrentHashMap<JenaModelWrapper,LockManager>();
        
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
        public static synchronized LockManager getInstance(JenaModelWrapper jenaModel) {
            LockManager result = null;
            if (!singleton.containsKey(jenaModel)) {
                log.info("######## Create new lock manager for model");
                result = new LockManager();
                singleton.put(jenaModel, result);
            } else {
                log.info("######## Return an existing lock.");
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
            if (count <= 0) {
                count = 0;
            } else {
                count--;
            }
            this.currentCount.set(count);
            return count;
        }
    }
    
    // static member variables
    private static Logger log = Logger.getLogger(JenaPersistanceTransaction.class);
    
    // private member variables
    private JenaModelWrapper jenaModel;
    private boolean inTransaction = false;
    private SessionManager.SessionLock lock;
    

    /**
     * The constructor responsible for creating the jena transaction.
     *
     * @param jenaModel The reference to the jena model.
     * @param type The type of store to utilize
     */
    protected JenaPersistanceTransaction(JenaModelWrapper jenaModel, JenaStoreType type) {
        System.out.println("Retrieve the Transaction using the constructor without a lock");
        this.jenaModel = jenaModel;
        // sets up the default read lock
        if (type == JenaStoreType.XML) {
            this.lock = SessionManager.SessionLock.NO_LOCK;
        } else if (type == JenaStoreType.TDB) {
            this.lock = SessionManager.SessionLock.READ_LOCK;
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
    protected JenaPersistanceTransaction(JenaModelWrapper jenaModel, 
            SessionManager.SessionLock lock) {
        System.out.println("Retrieve the session using the lock : " + lock);
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
            System.out.println("[" + Thread.currentThread().getId() + "]Before locking");
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                int count = LockManager.getInstance(jenaModel).incrementLockCount();
                System.out.println("[" + Thread.currentThread().getId() + "]The jena persistance transaction : " + count);
                if (count == 1) {
                    if (lock == SessionManager.SessionLock.READ_LOCK) {
                        System.out.println("[" + Thread.currentThread().getId() + "]Setup the read critical lock");
                        jenaModel.enterCriticalSection(Lock.READ);
                    } else if (lock == SessionManager.SessionLock.WRITE_LOCK) {
                        System.out.println("[" + Thread.currentThread().getId() + "]Setup the write critical lock");
                        jenaModel.enterCriticalSection(Lock.WRITE);
                        
                    }
                }
                System.out.println("[" + Thread.currentThread().getId() + "]Begin the model transaction");
                jenaModel.begin();
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
            log.error("[" + Thread.currentThread().getId() + "]Before commiting the change");
            System.out.println("[" + Thread.currentThread().getId() + "]Before commiting the change");
            jenaModel.commit();
            log.error("[" + Thread.currentThread().getId() + "]After Commit the changes");
            System.out.println("[" + Thread.currentThread().getId() + "]After Commit the changes");
        } catch (Exception ex) {
            log.error("[" + Thread.currentThread().getId() + "]Failed to commit the changes : " + ex.getMessage());
        } finally {
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                int count = LockManager.getInstance(jenaModel).decrementLockCount();
                System.out.println("[" + Thread.currentThread().getId() + "]The lock count is : " + count);
                if (count <= 0 && (lock == SessionManager.SessionLock.READ_LOCK || 
                        lock == SessionManager.SessionLock.WRITE_LOCK)) {
                    try {
                        System.out.println("[" + Thread.currentThread().getId() + "]Leave the critical lock");
                        log.error("[" + Thread.currentThread().getId() + "]Leave the critical lock");
                        jenaModel.leaveCriticalSection();
                        System.out.println("[" + Thread.currentThread().getId() + "]After critical lock");
                        log.error("[" + Thread.currentThread().getId() + "]After critical lock");
                    } catch (Exception ex) {
                        log.error("[" + Thread.currentThread().getId() + "]Failed to release the critical lock : " + 
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
            log.error("[" + Thread.currentThread().getId() + "]Before aborting the changes");
            System.out.println("[" + Thread.currentThread().getId() + "]Before aborting the changes");
            jenaModel.abort();
            log.error("[" + Thread.currentThread().getId() + "]After aborting the changes");
            System.out.println("[" + Thread.currentThread().getId() + "]After aborting the changes");
        } catch (Exception ex) {
            log.error("[" + Thread.currentThread().getId() + "]Failed to commit the changes : " + ex.getMessage());
        } finally {
            if (this.lock != SessionManager.SessionLock.NO_LOCK) {
                log.error("[" + Thread.currentThread().getId() + "]Release the lock count in rollback");
                System.out.println("[" + Thread.currentThread().getId() + "]Release the lock count in rollback");
                int count = LockManager.getInstance(jenaModel).decrementLockCount();
                log.error("[" + Thread.currentThread().getId() + "]After releasing the lock count : " + count);
                System.out.println("[" + Thread.currentThread().getId() + "]After releasing the lock count : " + count);
                if (count <= 0 && (lock == SessionManager.SessionLock.READ_LOCK || 
                        lock == SessionManager.SessionLock.WRITE_LOCK)) {
                    try {
                        log.error("[" + Thread.currentThread().getId() + "]Call leave the critical section : " + count);
                        System.out.println("[" + Thread.currentThread().getId() + "]Call leave the critical section : " + count);
                        jenaModel.leaveCriticalSection();
                        log.error("[" + Thread.currentThread().getId() + "]After calling leave the critical section : " + count);
                        System.out.println("[" + Thread.currentThread().getId() + "]After calling leave the critical section : " + count);
                    } catch (Exception ex) {
                        log.error("[" + Thread.currentThread().getId() + "]Failed to release the critical lock : " + 
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
