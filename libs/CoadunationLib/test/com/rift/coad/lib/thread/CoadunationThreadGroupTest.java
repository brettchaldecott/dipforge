/*
 * CoadunationLib: The coaduntion implementation library.
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
 * CoadunationThreadGroupTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.thread;

import java.net.URLClassLoader;
import java.net.URL;
import junit.framework.*;
import java.util.Vector;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import org.apache.log4j.Logger;
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


/**
 *
 * @author mincemeat
 */
public class CoadunationThreadGroupTest extends TestCase {
    
    /**
     * The class defintion
     */
    public static class TestMonitor {
        // constants
        private static final long TIMEOUT = 10000;
        
        // static variables
        private static TestMonitor singleton = null;
        
        // private member variables
        private long count = 0;
        
        
        private TestMonitor() {
        }
        
        public static synchronized TestMonitor getInstance() {
            if (singleton == null) {
                singleton = new TestMonitor();
            }
            return singleton;
        }
        
        
        /**
         * This method will notify any waiting threads that the call landed.
         */
        public synchronized void notifyTest() {
            count++;
            notify();
        }
        
        
        /**
         * Wait for the test to complete.
         *
         * @return TRUE if the test succeeded, FALSE if not.
         * @exception Exception
         */
        public synchronized boolean waitForTest() throws Exception {
            try {
                long startTime = new Date().getTime();
                while (count < 6) {
                    wait(TIMEOUT);
                    long currentTime = new Date().getTime();
                    if (count == 6) {
                        return true;
                    } else if ((currentTime - TIMEOUT) >= startTime) {
                        return false;
                    }
                }
                return true;
            } catch (Exception ex) {
                throw new Exception ("Failed to wait for test : " + 
                        ex.getMessage(),ex);
            }
        }
    }
    
    /**
     *
     */
    public static class SubThread extends CoadunationThread {
        // the class log variable
        public static int subThreadTerminateCount = 0;
        protected Logger log =
            Logger.getLogger(TestThread.class.getName());
        
        // the flag 
        private boolean terminated = false;
        
        /**
         * This object is used to test the threading funcationlity implemented
         * by the threading group.
         */
        public SubThread() throws Exception {
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            boolean notify = false;
            while(isTerminated() == false) {
                System.out.println("Message from sub thread : " + this.getId());
                if (isTerminated() == false) {
                    delay(2000);
                }
                if (notify == false) {
                    com.rift.coad.lib.thread.CoadunationThreadGroupTest.TestMonitor.
                            getInstance().notifyTest();
                    notify = true;
                }
            }
            System.out.println("Sub Thread id [" + this.getId() + 
                    "] is terminated");
            subThreadTerminateCount++;
        }


        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public synchronized void terminate() {
            terminated = true;
            notify();
        }
        
        
        /**
         * This method will return true if the terminated flag has been set.
         *
         * @return TRUE if terminated flag set, FALSE otherwise
         */
        private synchronized boolean isTerminated() {
            return terminated;
        }
        
        
        /**
         * This method will wait for the required delay period.
         *
         * @param time The time to wait for.
         */
        private synchronized void delay(long time) {
            try{
                wait(time);
            } catch (Exception ex) {
                // do nothing
            }
        }
    }
    
    
    /**
     * The test thread class.
     */
    public static class TestThread extends BasicThread {
        // the class log variable
        protected Logger log =
            Logger.getLogger(TestThread.class.getName());
        
        // the flag 
        private boolean terminated = false;
        
        /**
         * This object is used to test the threading funcationlity implemented
         * by the threading group.
         */
        public TestThread() throws Exception {
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            boolean notify = false;
            while(isTerminated() == false) {
                System.out.println("Message from thread : " + this.getId());
                if (isTerminated() == false) {
                    delay(2000);
                }
                if (notify == false) {
                    SubThread subThread = new SubThread();
                    try {
                        subThread.start("test");
                    } catch (Exception ex) {
                        System.out.println("Failed to start the sub thread : " 
                                + ex.getMessage());
                        ex.printStackTrace(System.out);
                    }
                    com.rift.coad.lib.thread.CoadunationThreadGroupTest.TestMonitor.
                            getInstance().notifyTest();
                    notify = true;
                }
            }
            System.out.println("Thread id [" + this.getId() + "] is terminated");
        }


        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public synchronized void terminate() {
            terminated = true;
            notify();
        }
        
        
        /**
         * This method will return true if the terminated flag has been set.
         *
         * @return TRUE if terminated flag set, FALSE otherwise
         */
        private synchronized boolean isTerminated() {
            return terminated;
        }
        
        
        /**
         * This method will wait for the required delay period.
         *
         * @param time The time to wait for.
         */
        private synchronized void delay(long time) {
            try{
                wait(time);
            } catch (Exception ex) {
                // do nothing
            }
        }
    }
    
    
    /**
     * The test thread class.
     */
    public static class NoTerminateTestThread extends BasicThread {
        // the class log variable
        protected Logger log =
            Logger.getLogger(NoTerminateTestThread.class.getName());
        
        /**
         * This object is used to test the threading funcationlity implemented
         * by the threading group.
         */
        public NoTerminateTestThread() throws Exception {
        }
        
        
        /**
         * This method replaces the run method in the BasicThread.
         *
         * @exception Exception
         */
        public void process() throws Exception {
            while(true) {
                System.out.println("Message from thread : " + this.getId());
                Thread.sleep(2000);
            }
        }


        /**
         * This method will be implemented by child objects to terminate the
         * processing of this thread.
         */
        public synchronized void terminate() {
            // do nothing
        }
    }
    
    
    
    public CoadunationThreadGroupTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CoadunationThreadGroupTest.class);
        
        return suite;
    }

    /**
     * Test of the thread group class com.rift.coad.lib.thread.CoadunationThreadGroup.
     */
    public void testThreadGroup() throws Exception {
        System.out.println("testThreadGroup");
        
        // initialize the thread permissions
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // the thread group manager
        
        
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        ClassLoader loader = new URLClassLoader(new URL[0],
                this.getClass().getClassLoader());
        Thread.currentThread().setContextClassLoader(loader);
        ThreadGroupManager.getInstance().initThreadGroup(threadGroup);
        // start 3 threads
        threadGroup.startThreads(
                com.rift.coad.lib.thread.CoadunationThreadGroupTest.TestThread.class, 
                "test", 3);
        
        // start 3 threads
        threadGroup.startThreads(
                com.rift.coad.lib.thread.CoadunationThreadGroupTest.NoTerminateTestThread.class, 
                "test", 1);
        
        if (TestMonitor.getInstance().waitForTest() == false) {
            fail("Failed to run the test.");
            return;
        }
        
        // retrieve the thread list
        List threadList = threadGroup.getThreadInfo();
        if (threadList.size() != 4) {
            fail("There should be 4 threads in the group there are [" + 
                    threadList.size() + "]");
        }
        
        // loop through the results
        boolean found = false;
        for (int i = 0; i < threadList.size(); i++) {
            ThreadInfo info = (ThreadInfo)threadList.get(i);
            System.out.println("Class [" + info.getThreadClass().getName() + "] id [" 
                    + info.getThreadId() + "] username [" + 
                    info.getUser().getName() + "] info [" + info.getInfo() + "]");
            if (info.getUser().getName().equals("test") == false) {
                fail("The invalid user [" + info.getUser().getName() + "] id ["
                        + info.getThreadId() + "]");
            }
            if (info.getThreadClass().getName().equals(
                    com.rift.coad.lib.thread.CoadunationThreadGroupTest.TestThread.class.
                    getName())) {
                found = true;
            }
        }
        
        if (found == false) {
            fail("Failed to find the matching threads");
        }
        
        // create a child thread group
        CoadunationThreadGroup childThreadGroup = threadGroup.createThreadGroup();
        
        // terminate
        threadGroup.terminate();
        ThreadGroupManager.getInstance().terminateThreadGroup();
        
        if (SubThread.subThreadTerminateCount != 3) {
            fail("Failed to terminate the sub threads");
        }
    }
}
