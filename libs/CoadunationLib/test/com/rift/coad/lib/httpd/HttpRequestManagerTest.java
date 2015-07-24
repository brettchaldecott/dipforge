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
 * HttpRequestManagerTest.java
 *
 * JUnit based test
 */

// package imports
package com.rift.coad.lib.httpd;

// java imports
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Vector;

// junit imports
import junit.framework.*;

// login imports
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Priority;

// coadunation imports
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.login.LoginManager;

/**
 *
 * @author mincemeat
 */
public class HttpRequestManagerTest extends TestCase {
    
    public class TestResults {
        
        // number of tests
        private int numTests = 0;
        private int countTests = 0;
        
        
        /**
         * The default for the test handler
         */
        public TestResults() {
            
        }
        
        /**
         * The constructor of the handler
         *
         * @param numTests The number of tests.
         */
        public TestResults(int numTests) {
            this.numTests = numTests * 2;
        }
        
        
        /**
         * This method will be called to invoke the call.
         */
        public synchronized void increment() {
            countTests++;
            notify();
            System.out.println("Increment has been called : " + countTests);            
        }
        
        
        /**
         * This method will be called to wait on the test
         *
         * @exception Exception
         */
        public synchronized void waitOnTest() throws Exception {
            while (countTests < numTests) {
                wait();
            }
        }
    }
    
    
    /**
     * The implementation of the test request object.
     */
    public class TestRequest extends Request {
        private TestResults results = null;
        public TestRequest(TestResults results) {
            super(null,null);
            this.results = results;
        }
        
        
        /**
         * The handle request method placed in this interface for test reasons.
         */
        public void handleRequest() {
            this.results.increment();
        }


        /**
         * This method is called to destory the http request when finished.
         */
        public void destroy() {
            this.results.increment();
        }
    }
    
    public HttpRequestManagerTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(HttpRequestManagerTest.class);
        
        return suite;
    }

    /**
     * Test of addRequest method, of class com.rift.coad.lib.httpd.HttpRequestManager.
     */
    public void testAddRequest() throws Exception {
        System.out.println("addRequest");
        
        // instanciate the deployment loader
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        SessionManager.init(permissionContainer);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(
                permissionContainer,userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        
        HttpRequestManager instance = new HttpRequestManager(threadGroup);
        
        // Init the test handler
        TestResults results = new TestResults(100);
        for (int index = 0; index < 100; index++) {
            // add the proxy object
            Request req = new TestRequest(results);
            System.out.println("Add entry to request [" + (index + 1) + "]");
            instance.addRequest(req);
        }
        
        System.out.println("Wait on the test.");
        results.waitOnTest();
        System.out.println("After waiting on test");
    }
    
}
