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
 * ClientInterceptorTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.interceptor;

// java imports
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// unit test
import junit.framework.*;

// logging
import org.apache.log4j.Logger;

// coadunation
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.interceptor.credentials.Credential;
import com.rift.coad.lib.interceptor.credentials.Login;
import com.rift.coad.lib.interceptor.credentials.Session;
import com.rift.coad.lib.interceptor.authenticator.SessionAuthenticator;
import com.rift.coad.lib.security.AuthorizationException;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 * The client interceptor test class 
 *
 * @author Brett Chaldecott
 */
public class ClientInterceptorTest extends TestCase {
    
    /**
     * This class is responsible for supplying access to the Server
     * interceptor methods
     */
    public class TestInterceptor extends InterceptorWrapper {
        
        // private member variables
        private ClientInterceptor interceptor = null;
        
        
        /**
         * The constructor of the test interceptor.
         */
        public TestInterceptor() throws Exception {
            interceptor = this.getClientInterceptor();
        }
        
        
        /**
         * This method returns the session credentials
         */
        public Credential getSessionCredential() throws InterceptorException {
            return interceptor.getSessionCredential();
        }
        
        
    }
    
    public ClientInterceptorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ClientInterceptorTest.class);
        
        return suite;
    }

    /**
     * Test of getSessionCredential method, of class com.rift.coad.lib.interceptor.ClientInterceptor.
     */
    public void testGetSessionCredential() throws Exception {
        System.out.println("getSessionCredential");
        
        // init the session information
        ThreadsPermissionContainer permissions = new ThreadsPermissionContainer();
        SessionManager.init(permissions);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissions,
                userStoreManager);
        LoginManager.init(sessionManager,userStoreManager);
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // add a user to the session for the current thread
        RoleManager.getInstance();
        
        InterceptorFactory.init(permissions,sessionManager,userStoreManager);
        
        // instanciate the test interceptor
        TestInterceptor testInterceptor = new TestInterceptor();
        
        try {
            testInterceptor.getSessionCredential();
            fail("Was able to get the session credentials for a none " +
                    "existant session");
        } catch (InterceptorException ex) {
            // ignore
        }
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        set.add("test1");
        set.add("test2");
        set.add("test3");
        UserSession user = new UserSession("test", set);
        ThreadPermissionSession currentSession = new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                currentSession);
        
        try {
            Session session = (Session)testInterceptor.getSessionCredential();
            if (!session.getUsername().equals("test")) {
                fail("The username is not equal to test");
            }
            Set principals = session.getPrincipals();
            if ((!principals.contains("test")) ||
                    (!principals.contains("test1")) || 
                    (!principals.contains("test2")) || 
                    (!principals.contains("test3")))
            {
                fail("Principals not set correctly");
            }
        } catch (InterceptorException ex) {
            fail("Should have been able to retrieve credentials");
        }
        
    }
    
}
