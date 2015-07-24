/*
 * CoadunationLib: The coadunation core library.
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
 * ThreadPoolManagerTest.java
 */

package com.rift.coad.lib.thread.pool;

import junit.framework.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.URLClassLoader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import com.rift.coad.lib.common.ClassUtil;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.ThreadGroupManager;
import com.rift.coad.lib.security.ThreadPermissionSession;

/**
 * The test of the thread pool manager.
 *
 * @author Brett Chaldecott
 */
public class ThreadPoolManagerTest extends TestCase {
    
    /**
     * This object is called by the test task object.
     */
    public static class TestMonitor {
        
        private int waitCount = 0;
        private int callCount = 0;
        
        /**
         * This class is used to monitor the test
         */
        public TestMonitor() {
            
        }
        
        
        /**
         * This method is called to by the threads to wait indefinitly.
         */
        public synchronized void threadWait() {
            waitCount++;
            callCount++;
            try {
                wait();
            } catch (Exception ex) {
                System.out.println("Failed to wait : " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
            waitCount--;
        }
        
        
        /**
         * This returns the wait count
         */
        public synchronized int getWaitCount() {
            return waitCount;
        }
        
        
        /**
         * This method returns the call count
         */
        public synchronized int getCallCount() {
            return callCount;
        }
        
        /**
         * This method resets the called count
         */
        public synchronized void resetCalledCount() {
            callCount = 0;
        }
        
        
        /**
         * This method is called to release all waiting threads
         */
        public synchronized void notifyAllWaitingThreads() {
            notifyAll();
        }
    }
    
    
    
    /**
     * This class is responsible for processing as a task.
     */
    public static class TestTask implements Task {
        
        /**
         * The process method used by the task object.
         */
        public void process(ThreadPoolManager pool) throws Exception {
            testMonitor.threadWait();
        }
        
    }
    
    public static TestMonitor testMonitor = new TestMonitor();
    
    public ThreadPoolManagerTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test com.rift.coad.lib.thread.pool.ThreadPoolManager.
     */
    public void testThreadPoolManager() throws Exception {
        System.out.println("ThreadPoolManager");
        
        // initialize the thread permissions
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(
                sessionManager,userStoreManager);
        ClassLoader loader = new URLClassLoader(new URL[0],
                this.getClass().getClassLoader());
        Thread.currentThread().setContextClassLoader(loader);
        ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("test1", set);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // start the thread pool
        ThreadPoolManager instance1 = new ThreadPoolManager(2, TestTask.class, 
                "test");
        ThreadPoolManager instance2 = new ThreadPoolManager(3,5, TestTask.class, 
                "test");
        
        assertEquals(2, instance1.getSize());
        instance1.setSize(3);
        assertEquals(3, instance1.getSize());
        assertEquals(3, instance2.getMinSize());
        instance2.setMinSize(4);
        assertEquals(4, instance2.getMinSize());
        try {
            instance2.setMinSize(6);
            fail("Should not be able to set the pool size to greater than max.");
        } catch (PoolException ex) {
            // ignore correct
        }
        assertEquals(5, instance2.getMaxSize());
        try {
            instance2.setMaxSize(3);
            fail("Should not be able to set the pool size max to smaller " +
                    "than max.");
        } catch (PoolException ex) {
            // ignore correct
        }
        
        assertEquals(2,testMonitor.getWaitCount());
        assertEquals(2,testMonitor.getCallCount());
        
        instance1.releaseThread();
        instance2.releaseThread();
        Thread.sleep(500);
        
        assertEquals(4,testMonitor.getCallCount());
        assertEquals(4,testMonitor.getWaitCount());
        
        instance1.releaseThread();
        instance1.releaseThread();
        instance2.releaseThread();
        instance2.releaseThread();
        instance2.releaseThread();
        instance2.releaseThread();
        Thread.sleep(500);
        
        assertEquals(8,testMonitor.getCallCount());
        assertEquals(8,testMonitor.getWaitCount());
        
        testMonitor.notifyAllWaitingThreads();
        
        instance1.releaseThread();
        instance1.releaseThread();
        instance2.releaseThread();
        instance2.releaseThread();
        Thread.sleep(500);
        
        assertEquals(6,testMonitor.getWaitCount());
        assertEquals(14,testMonitor.getCallCount());
        
        instance1.terminate();
        instance2.terminate();
    }

}
