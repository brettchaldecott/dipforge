/*
 * CoadunationUtil: The coaduntion utility library.
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
 * ObjectLockFactoryTest.java
 */

package com.rift.coad.util.lock;

import junit.framework.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 * 
 *
 * @author Brett Chaldecott
 */
public class ObjectLockFactoryTest extends TestCase {
    
    private String lockObject = "lock object";
    private String lockObject2 = "lock object2";
    private boolean hasLock = false;
    private long lockCount = 0;
    
    public ObjectLockFactoryTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of of class com.rift.coad.util.lock.ObjectLockFactory.
     */
    public void testObjectLockFactory() throws Exception {
        System.out.println("getInstance");
        
        try {
            ObjectLockFactory.getInstance();
            fail("Could retrieve an reference without calling init");
        } catch (LockException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        ObjectLockFactory.init();
        
        ObjectLockFactory expResult = ObjectLockFactory.getInstance();
        ObjectLockFactory result = ObjectLockFactory.getInstance();
        assertEquals(expResult, result);
        
        System.out.println("Aquire write lock");
        LockRef ref = result.acquireWriteLock(lockObject);
        System.out.println("Test lock");
        assertEquals(ref.getKey(),lockObject);
        System.out.println("Test thread id");
        assertEquals(ref.getThreadId(),Thread.currentThread().getId());
        
        System.out.println("Aquire lock");
        ref = result.acquireWriteLock(lockObject,"test");
        
        System.out.println("Test the lock");
        assertEquals(ref.getKey(),lockObject);
        assertEquals(ref.getThreadId(),Thread.currentThread().getId());
        assertEquals(ref.getLockName(),"test");
        
        Thread testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireWriteLock(lockObject,"test2");
                    hasLock = true;
                    ref2.release();
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        testThread.start();
        
        System.out.println("Test a seperate thread");
        Thread.sleep(1000);
        if (hasLock == true) {
            fail("Was able to get lock");
        }
        
        System.out.println("Attempt to release");
        ref.release();
        ref.release();
        
        Thread.sleep(500);
        if (hasLock == false) {
            fail("Was not able to get lock");
        }
        
        System.out.println("Aquire new lock");
        ref = result.acquireWriteLock(lockObject,"test2");
        hasLock = false;
        System.out.println("Create second thread");
        testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireWriteLock(lockObject,
                            ObjectLockFactory.WAIT_ON_THREAD);
                    hasLock = true;
                    ref2.release();
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        testThread.start();
        System.out.println("Sleep");
        Thread.sleep(1000);
        if (hasLock == true) {
            fail("Was able to get lock");
        }
        
        System.out.println("Release");
        ref.release();
        
        Thread.sleep(500);
        if (hasLock == true) {
            fail("Was able to get lock");
        }
        
        // wait for a named lock
        ref = result.acquireWriteLock(lockObject,"test2");
        hasLock = false;
        testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireWriteLock(lockObject,"test2",
                            ObjectLockFactory.WAIT_ON_NAMED);
                    hasLock = true;
                    ref2.release();
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        testThread.start();
        
        Thread.sleep(1000);
        if (hasLock == false) {
            fail("Was not able to acquire the lock");
        }
        
        ref.release();
        
        // attempt to aquire a lock on two different objects
        System.out.println("Aquire two different locks");
        ref = result.acquireWriteLock(lockObject,"test2");
        hasLock = false;
        testThread = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireWriteLock(lockObject2,"test2",
                            ObjectLockFactory.WAIT_ON_NAMED);
                    hasLock = true;
                    ref2.release();
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        testThread.start();
        
        Thread.sleep(1000);
        if (hasLock == false) {
            fail("Was not able to acquire the lock");
        }
        
        ref.release();
        
        // test the read
        Thread readThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject);
                    lockCount++;
                    Thread.sleep(1000);
                    System.out.println("Read1: Release lock");
                    ref2.release();
                    System.out.println("Read1: Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        Thread readThread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject);
                    lockCount++;
                    Thread.sleep(1000);
                    System.out.println("Read2: Release lock");
                    ref2.release();
                    System.out.println("Read2: Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        readThread1.start();
        readThread2.start();
        
        Thread.sleep(300);
        if (lockCount != 2) {
            fail("Aquire the lock");
        }
        
        System.out.println("Wait for lock");
        ref = result.acquireWriteLock(lockObject);
        System.out.println("1: Acquired lock");
        
        // test the read
        readThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("Read1: Acquired lock");
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject);
                    System.out.println("Read1: Acquired lock");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        readThread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("Read2: Acquire lock");
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject);
                    System.out.println("Read2: Acquired lock");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        
        lockCount = 0;
        readThread1.start();
        readThread2.start();
        
        Thread.sleep(300);
        if (lockCount != 0) {
            fail("The could aquire the lock");
        }
        
        ref.release();
        
        Thread.sleep(2000);
        if (lockCount != 2) {
            fail("The could not acquire the lock : " + lockCount);
        }
        
        // test the read
        readThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject,"test");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        readThread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject,"test");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        lockCount = 0;
        readThread1.start();
        readThread2.start();
        
        Thread.sleep(300);
        if (lockCount != 2) {
            fail("The could not aquire the lock");
        }
        
        System.out.println("Wait for lock");
        ref = result.acquireWriteLock(lockObject);
        System.out.println("Acquired lock");
        
        // test the read
        readThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject,"test");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        readThread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    LockRef ref2 = ObjectLockFactory.getInstance().
                            acquireReadLock(lockObject,"test");
                    lockCount++;
                    Thread.sleep(1000);
                    ref2.release();
                    System.out.println("Released lock");
                } catch (Exception ex) {
                    System.out.println("Failed to aquire the lock : " + 
                            ex.getMessage());
                    ex.printStackTrace(System.out);
                }
            }
        });
        
        lockCount = 0;
        readThread1.start();
        readThread2.start();
        
        Thread.sleep(300);
        if (lockCount != 0) {
            fail("The could aquire the lock");
        }
        
        ref.release();
        
        Thread.sleep(2000);
        if (lockCount != 2) {
            fail("The could not acquire the lock");
        }
        
        
        ObjectLockFactory.fin();
        try {
            ObjectLockFactory.getInstance();
            fail("Could retrieve an reference without after calling fin");
        } catch (LockException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
