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
 * UserPasswordAuthenticatorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.interceptor.authenticator;

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
import com.rift.coad.lib.interceptor.InterceptorAuthenticator;
import com.rift.coad.lib.interceptor.InterceptorException;
import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.interceptor.InterceptorWrapper;
import com.rift.coad.lib.interceptor.ServerInterceptor;
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
 * Test the user password authentication
 *
 * @author Brett Chaldecott
 */
public class UserPasswordAuthenticatorTest extends TestCase {
    
    /**
     * This class is responsible for supplying access to the Server
     * interceptor methods
     */
    public class TestInterceptor extends InterceptorWrapper {
        
        // private member variables
        private ServerInterceptor interceptor = null;
        
        
        /**
         * The constructor of the test interceptor.
         */
        public TestInterceptor() throws Exception {
            interceptor = this.getServerInterceptor();
        }
        
        
        /**
         * This method creates a new session using the credentials passed in.
         *
         * @param credential The credentials to create a new session for.
         * @exception Exception
         */
        public void createSession(Credential credential) throws Exception {
            interceptor.createSession(credential);
        }
        
        
        /**
         * This method releases this thread, removing all user information from it.
         *
         * @exception InterceptorException
         */
        public void release() throws InterceptorException {
            interceptor.release();
        }
        
    }
    
    public UserPasswordAuthenticatorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(UserPasswordAuthenticatorTest.class);
        
        return suite;
    }

    /**
     * Test of authenticate method, of class com.rift.coad.lib.interceptor.authenticator.UserPasswordAuthenticator.
     */
    public void testAuthenticate() throws Exception {
        System.out.println("authenticate");
        
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
        
        TestInterceptor testInterceptor = new TestInterceptor();
        
        Login login = new Login("test","112233");
        
        testInterceptor.createSession(login);
        
        ThreadPermissionSession threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test")) {
            fail("The user name is not test");
        }
        
        testInterceptor.release();
    }
    
}
