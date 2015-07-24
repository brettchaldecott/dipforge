/*
 * CoadunationUtil: The coaduntion utility library.
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
 * ObjectLockFactory.java
 */

// package path
package com.rift.coad.util.lock;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

// logging import
import org.apache.log4j.Logger;


/**
 * The object lock factory is responsible for assigning locks based on the
 * object passed in. Object locks are designed to work in conjunction with
 * transaction locks. They are designed to cater for named locks that are not
 * thread id dependant. This means it is possible to lock an object until a
 * transaction is complete, even though the transaction is being controlled
 * from another and hense different threads.
 *
 * There are four different types of object locks; read lock, read named lock,
 * write lock and a named write lock.
 *
 * All locks are re-entrant. Meaning they can be locked a number of times and
 * must be unlocked the same number of times. If a read lock needs to change to
 * a write lock, that lock must be released before this can be done otherwise a
 * dead lock will occur.
 *
 * A write lock can change from a write thread id lock to a write thread named
 * lock. A read lock cannot change from a thread id lock to a named id lock.
 *
 * @author Brett Chaldecott
 */
public class ObjectLockFactory {
    
    /**
     * This class contains the lock information.
     */
    public class Lock {
        // lock types
        public final static int READ_LOCK = 0;
        public final static int THREAD_BOUND = 1;
        public final static int NAME_BOUND = 2;
        
        // private member variables
        private long threadId = 0;
        private long lockCount = 0;
        private Object name = null;
        private Map readLock = new HashMap();
        private int waiting = 0;
        
        /**
         * The constructor of the lock object.
         */
        public Lock() {
            
        }
        
        
        /**
         * This method returns the id of the thread that hold the lock.
         *
         * @return The id of the thread id.
         */
        public long getThreadId() {
            return threadId;
        }
        
        
        /**
         * This method sets the thread id.
         *
         * @param threadId The id of the thread.
         */
        public void setThreadId(long threadId) {
            this.threadId = threadId;
        }
        
        
        /**
         * This method returns the name of the thread id.
         *
         * @return The name of the object lock.
         */
        public Object getName() {
            return name;
        }
        
        
        /**
         * This method sets the name of the object lock.
         *
         * @param name The name of the lock.
         */
        public void setName(Object name) {
            this.name = name;
        }
        
        
        /**
         * This method returns the lock type.
         *
         * @return The object containing the lock type.
         */
        public synchronized int lockType() {
            if (readLock.size() > 0) {
                return READ_LOCK;
            } else if (name == null) {
                return THREAD_BOUND;
            }
            return NAME_BOUND;
        }
        
        
        /**
         * This method returns true if this lock is owned by the caller.
         *
         * @return TRUE if lock owned by CALLER
         * @param name The name associated with the lock.
         */
        public boolean ownLock() {
            if (threadId == Thread.currentThread().getId()) {
                return true;
            }
            return false;
        }
        
        
        /**
         * This method returns true if this lock is owned by the caller.
         *
         * @return TRUE if lock owned by CALLER
         * @param name The name associated with the lock.
         */
        public boolean ownLock(Object name) {
            if (threadId == Thread.currentThread().getId()) {
                return true;
            }
            if (((name == null) && (this.name == null)) ||
                    ((this.name == null) && (name != null))) {
                return false;
            }
            return this.name.equals(name);
        }
        
        
        /**
         * This method returns true if the object is locked.
         *
         * @return TRUE if locked, FALSE if not.
         */
        public boolean isLocked() {
            if ((threadId == 0) && (readLock.size() == 0)) {
                return false;
            }
            return true;
        }
        
        
        /**
         * This method will aquire a read lock.
         *
         * @exception LockException
         */
        public synchronized void getReadLock() throws LockException {
            waiting++;
            try {
                Long threadId = new Long(Thread.currentThread().getId());
                if (readLock.containsKey(threadId)) {
                    Integer lockCount = (Integer)readLock.get(threadId);
                    readLock.put(threadId,new Integer(lockCount.intValue() + 1));
                    return;
                }
                while(this.threadId != 0) {
                    wait();
                }
                
                // setup lock environment
                readLock.put(threadId,new Integer(1));
                this.threadId = 0;
                this.name = null;
            } catch (Exception ex) {
                throw new LockException("Failed to aquire a lock : " +
                        ex.getMessage(),ex);
            } finally {
                waiting--;
            }
        }
        
        
        /**
         * This method will aquire a read lock for the given name
         *
         * @return LockException
         */
        public synchronized void getReadLock(Object name) throws LockException {
            waiting++;
            try {
                if (readLock.containsKey(name)) {
                    Integer lockCount = (Integer)readLock.get(name);
                    readLock.put(name,new Integer(lockCount.intValue() + 1));
                    return;
                }
                while(this.threadId != 0) {
                    wait();
                }
                
                // set up environment
                readLock.put(name,new Integer(1));
                this.threadId = 0;
                this.name = null;
                
            } catch (Exception ex) {
                throw new LockException("Failed to aquire a lock : " +
                        ex.getMessage(),ex);
            } finally {
                waiting--;
            }
        }
        
        
        /**
         * This method will aquire a read lock.
         */
        public synchronized void getWriteLock() throws LockException {
            waiting++;
            try {
                long threadId = Thread.currentThread().getId();
                if (this.threadId == threadId) {
                    lockCount++;
                    return;
                }
                while(this.threadId != 0) {
                    wait();
                }
                // acquire the write lock
                this.threadId = threadId;
                lockCount = 1;
                this.name = null;
                
                // wait for all read locks to end
                while (readLock.size() > 0) {
                    wait();
                }
                
            } catch (Exception ex) {
                throw new LockException("Failed to aquire a write lock : " +
                        ex.getMessage(),ex);
            } finally {
                waiting--;
            }
        }
        
        
        /**
         * This method will aquire a read lock.
         */
        public synchronized void getWriteLock(Object name) throws
                LockException {
            waiting++;
            try {
                long threadId = Thread.currentThread().getId();
                if (this.threadId == threadId) {
                    lockCount++;
                    this.name = name;
                    return;
                } else if ((this.name != null) && (name != null) &&
                        this.name.equals(name)) {
                    this.threadId = threadId;
                    lockCount++;
                    return;
                }
                while(this.threadId != 0) {
                    wait();
                }
                // aquire the write named lock
                this.threadId = threadId;
                lockCount = 1;
                this.name = name;
                
                // wait for all read locks to end
                while (readLock.size() > 0) {
                    wait();
                }
                
            } catch (Exception ex) {
                throw new LockException("Failed to aquire a write lock : " +
                        ex.getMessage(),ex);
            } finally {
                waiting--;
            }
        }
        
        
        /**
         * This method will release the lock and return true if there are no
         * more waiting threads.
         *
         * @return TRUE if there are no more waiting threads.
         */
        public synchronized boolean releaseLock() {
            return releaseLock(new Long(Thread.currentThread().getId()));
        }
        
        
        /**
         * This method will release the lock and return true if there are no
         * more waiting threads.
         *
         * @return TRUE if there are no more waiting threads.
         */
        public synchronized boolean releaseLock(Object name) {
            try {
                long threadId = Thread.currentThread().getId();
                if (readLock.size() > 0) {
                    int intValue = ((Integer)readLock.get(name)).intValue() - 1;
                    if (intValue == 0) {
                        readLock.remove(name);
                    } else {
                        readLock.put(name,new Integer(intValue));
                    }
                } else if ((this.threadId == threadId) ||
                        ((this.name != null) && 
                        this.name.getClass().equals(name.getClass())
                        && this.name.equals(name))) {
                    lockCount--;
                    if (lockCount == 0) {
                        this.threadId = 0;
                        lockCount = 0;
                        name = null;
                    }
                }
                notify();
                if (waiting == 0) {
                    return true;
                }
                return false;
            } catch (java.lang.RuntimeException ex) {
                log.error("Failed to release the lock on [" + this.name + "][" +
                        name + "][" + name.getClass().getName() + "] : " + 
                        ex.getMessage(),ex);
                throw ex;
            }
            
        }
    }
    
    
    /**
     * The object that implements a reference to the object lock.
     */
    public class ObjectLockRef implements LockRef {
        // private member variables
        private Object obj = null;
        private Lock lock = null;
        private Object name = null;
        
        /**
         * The constructor of object lock ref.
         *
         * @param obj The object reference.
         * @param lock The reference to the lock information.
         */
        public ObjectLockRef(Object obj, Lock lock) {
            this.obj = obj;
            this.lock = lock;
        }
        
        
        /**
         * The constructor of object lock ref.
         *
         * @param obj The object reference.
         * @param lock The reference to the lock information.
         */
        public ObjectLockRef(Object obj, Lock lock, Object name) {
            this.obj = obj;
            this.lock = lock;
            this.name = name;
        }
        
        
        /**
         * This method returns the object lock key.
         *
         * @return The object that this lock is held for.
         */
        public Object getKey() {
            return obj;
        }
        
        
        /**
         * This method returns the id of the thread holding the lock.
         *
         * @return This mehod returns the thread id.
         */
        public long getThreadId() throws LockException {
            if (lock.lockType() == Lock.READ_LOCK) {
                throw new LockException("This is a read lock no unique thread " +
                        "id is present.");
            }
            return lock.getThreadId();
        }
        
        
        /**
         * This method sets the thread id for for the object lock.
         *
         * @param id The id of the thread controlling the lock.
         */
        public void setThreadId(long id) throws LockException {
            if (lock.lockType() == Lock.READ_LOCK) {
                throw new LockException("This is a read lock cannot set the " +
                        "thread id on it");
            }
            lock.setThreadId(id);
        }
        
        
        /**
         * This method returns the name of the object lock.
         *
         * @return The name of the object lock.
         */
        public Object getLockName() throws LockException {
            if (lock.lockType() == Lock.READ_LOCK) {
                throw new LockException("This is a read lock no name associated " +
                        "with it");
            }
            return lock.getName();
        }
        
        
        /**
         * This method sets the name of the object lock.
         *
         * @param name The new name for the object lock.
         */
        public void setLockName(Object name)  throws LockException {
            if (lock.lockType() == Lock.READ_LOCK) {
                throw new LockException("This is a read lock cannot associate a" +
                        "name with it");
            }
            lock.setName(name);
        }
        
        
        /**
         * This method returns the lock type for this object.
         *
         * @return The lock type for this object.
         * @exception LockException
         */
        public int getLockType() throws LockException {
            if (lock.lockType() == Lock.READ_LOCK) {
                return LockRef.READ;
            }
            return LockRef.WRITE;
        }
        
        
        /**
         * This method is called to release the lock on the object.
         */
        public void release() {
            synchronized(obj) {
                boolean remove = false;
                if (name == null) {
                    remove = lock.releaseLock();
                } else {
                    remove = lock.releaseLock(name);
                }
                
                if (remove) {
                    locks.remove(obj);
                }
            }
        }
        
    }
    
    // class constants
    public final static int WAIT = 1;
    public final static int WAIT_ON_NAMED = 2;
    public final static int WAIT_ON_THREAD = 3;
    public final static int DO_NOT_WAIT = 4;
    
    // private member variables
    protected static Logger log =
            Logger.getLogger(ObjectLockFactory.class.getName());
    
    // singleton methods
    private static Map singletonMap = new HashMap();
    
    // private member variables
    private Map locks = new ConcurrentHashMap();
    
    /**
     * Creates a new instance of ObjectLockFactory
     */
    private ObjectLockFactory() {
    }
    
    
    /**
     * This method creates a new object lock factory singleton for a class
     * loader.
     *
     * @exception LockException
     */
    public synchronized static void init() throws LockException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (!singletonMap.containsKey(loader)) {
            singletonMap.put(loader,new ObjectLockFactory());
        }
    }
    
    
    /**
     * This method returns the object lock factory singleton reference.
     *
     * @return The object lock factory singleton reference.
     * @exception LockException.
     */
    public synchronized static ObjectLockFactory getInstance() throws
            LockException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ObjectLockFactory singleton = null;
        if (singletonMap.containsKey(loader)) {
            singleton = (ObjectLockFactory)singletonMap.get(loader);
        } else {
            throw new LockException("There is no object lock factory for " +
                    "this class loader");
        }
        return singleton;
    }
    
    
    /**
     * This method removes the object lock factory associated with a class
     * loader.
     *
     * @exception LockException
     */
    public synchronized static void fin() throws LockException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (singletonMap.containsKey(loader)) {
            singletonMap.remove(loader);
        }
    }
    
    
    /**
     * This method creates a new lock for the specified key.
     *
     * @return A reference to the lock object.
     * @param key The key that identifies the unique lock.
     * @exception LockException
     */
    public LockRef acquireWriteLock(Object key) throws LockException {
        try {
            Lock lock = getLock(key);
            lock.getWriteLock();
            return new ObjectLockRef(key,lock);
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method creates a new lock for the specified key. It will wait
     * depending on the specified flag.
     *
     * @return The object lock reference.
     * @param key The key to lock this object with.
     * @param waitFlags The flags to wait on.
     * @exception LockException
     * @exception LockConflict
     */
    public LockRef acquireWriteLock(Object key, int waitFlags)
    throws LockException, LockConflict {
        try {
            Lock lock = null;
            synchronized(key) {
                lock = getLock(key);
                // This is not a guaranteed check, due to raise conditions
                // and what might be waiting to aquire a lock on this object
                // this test might fail.
                if (lock.isLocked()) {
                    if (!lock.ownLock() && (WAIT_ON_NAMED == waitFlags) &&
                            (lock.lockType() != Lock.NAME_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a thread lock.");
                        throw new LockConflict("The object is currently locked" +
                                " by a thread lock.");
                    } else if (!lock.ownLock() && (WAIT_ON_THREAD == waitFlags) &&
                            (lock.lockType() != Lock.THREAD_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a named object.");
                        throw new LockConflict("The object is currently locked" +
                                " by a named lock object.");
                    } else if (!lock.ownLock() && (WAIT_ON_NAMED == waitFlags) &&
                            (lock.lockType() != Lock.NAME_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a named object.");
                        throw new LockConflict("The object is currently locked" +
                                " by a named lock object.");
                    } else if (!lock.ownLock() && (DO_NOT_WAIT == waitFlags)) {
                        throw new LockConflict("The object is currently locked by " +
                                "another.");
                    }
                }
            }
            lock.getWriteLock();
            return new ObjectLockRef(key,lock);
        } catch (LockConflict ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method creates a new lock for the specified key.
     *
     * @return The reference to the lock object.
     * @param key The key that the lock will be aquired for.
     * @param name The name of the lock.
     * @exception LockException
     */
    public LockRef acquireWriteLock(Object key, Object name) throws LockException {
        try {
            Lock lock = getLock(key);
            lock.getWriteLock(name);
            return new ObjectLockRef(key,lock);
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method creates a new lock for the specified key. It will wait
     * depending on the specified flag.
     *
     * @return The object lock reference.
     * @param key The key to lock this object with.
     * @param name The name ofthe lock.
     * @param waitFlags The flags to wait on.
     * @exception LockException
     * @exception LockConflict
     */
    public LockRef acquireWriteLock(Object key, Object name, int waitFlags)
    throws LockException, LockConflict {
        try {
            Lock lock = null;
            synchronized(key) {
                lock = getLock(key);
                // This is not a guaranteed check, due to raise conditions
                // and what might be waiting to aquire a lock on this object
                // this test might fail.
                if (lock.isLocked()) {
                    if (!lock.ownLock(name) && (WAIT_ON_NAMED == waitFlags) &&
                            (lock.lockType() != Lock.NAME_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a thread lock.");
                        throw new LockConflict("The object is currently locked" +
                                " by a thread lock.");
                    } else if (!lock.ownLock(name) && (WAIT_ON_THREAD == waitFlags) &&
                            (lock.lockType() != Lock.THREAD_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a named object.");
                        throw new LockConflict("The object is currently locked" +
                                " by a named lock object.");
                    } else if (!lock.ownLock(name) && (WAIT_ON_NAMED == waitFlags) &&
                            (lock.lockType() != Lock.NAME_BOUND)) {
                        log.debug("The object is currently locked" +
                                " by a named object.");
                        throw new LockConflict("The object is currently locked" +
                                " by a named lock object.");
                    } else if (!lock.ownLock(name) && (DO_NOT_WAIT == waitFlags)) {
                        throw new LockConflict("The object is currently locked by " +
                                "another.");
                    }
                }
            }
            lock.getWriteLock(name);
            return new ObjectLockRef(key,lock);
        } catch (LockConflict ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method creates a new lock for the specified key.
     *
     * @return The object lock reference.
     * @param key The key to lock this object with.
     * @exception LockException
     */
    public LockRef acquireReadLock(Object key)
    throws LockException {
        try {
            Lock lock = getLock(key);
            lock.getReadLock();
            return new ObjectLockRef(key,lock);
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    /**
     * This method creates a new read lock for the specified key.
     *
     * @return The object lock reference.
     * @param key The key to lock this object with.
     * @param name The name ofthe lock.
     * @exception LockException
     */
    public LockRef acquireReadLock(Object key,Object name)
    throws LockException {
        try {
            Lock lock = getLock(key);
            lock.getReadLock(name);
            return new ObjectLockRef(key,lock,name);
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method releases the lock held on a given object.
     *
     * @param key The key identifying the lock.
     * @exception LockException
     */
    public void releaseLock(Object key) throws LockException {
        try {
            synchronized(key) {
                Lock lock = (Lock)locks.get(key);
                if (lock.releaseLock()) {
                    locks.remove(key);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method releases the lock held on a given object.
     *
     * @param key The key identifying the lock.
     * @exception LockException
     */
    public void releaseLock(Object key, Object name) throws LockException {
        try {
            synchronized(key) {
                Lock lock = (Lock)locks.get(key);
                if (lock.releaseLock(name)) {
                    locks.remove(key);
                }
            }
        } catch (Exception ex) {
            log.error("Failed to aquire the object lock : " +
                    ex.getMessage(),ex);
            throw new LockException("Failed to aquire the object lock : " +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method retrievs a lock for an object
     */
    private Lock getLock(Object key) throws LockException {
        synchronized (key) {
            Lock lock = (Lock)locks.get(key);
            if (lock == null) {
                lock = new Lock();
                locks.put(key,lock);
            }
            return lock;
        }
    }
}
