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
 * ServerInterceptorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.interceptor;

import java.util.HashSet;
import java.util.Iterator;
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
 * This class test the server interceptor.
 *
 * @author Brett Chaldecott
 */
public class ServerInterceptorTest extends TestCase {
    
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
         * This method overwrites the existing session using the credentials 
         * passed in.
         *
         * @param credential The credentials overwrite the old session with.
         * @exception Exception
         */
        public void setSession(Credential credential) throws Exception {
            interceptor.setSession(credential);
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
    
    public ServerInterceptorTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(ServerInterceptorTest.class);
        
        return suite;
    }
    
    /**
     * Test of createSession method, of class com.rift.coad.lib.interceptor.ServerInterceptor.
     */
    public void testServerInterceptor() throws Exception {
        System.out.println("testServerInterceptor");
        
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
        
        
        Set principals = new HashSet();
        principals.add("test");
        Session session = new Session("test","xxxxxxxxxxxx",principals);
        
        if (null != permissions.getSession(Thread.currentThread().getId())) {
            fail("There is an existing session for this user");
        }
        
        testInterceptor.createSession(session);
        
        ThreadPermissionSession threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test")) {
            fail("The user name is not test");
        }
        if (!threadSession.getUser().getSessionId().equals("xxxxxxxxxxxx")) {
            fail("The session id [xxxxxxxxxxxx] was not found [" + 
                    threadSession.getUser().getSessionId() + "]");
        }
        if (!threadSession.getPrincipals().contains("test")) {
            fail("The principals are not setup correctly.");
        }
        
        principals = new HashSet();
        principals.add("test");
        principals.add("test1");
        principals.add("test2");
        session = new Session("test1","yyyyyyyyyyyyy",principals);
        
        testInterceptor.createSession(session);
        
        threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test1")) {
            fail("The user name is test");
        }
        if (!threadSession.getUser().getSessionId().contains("yyyyyyyyyyyyy")) {
            fail("The session id [yyyyyyyyyyyyy] was not found.");
        }
        if (!threadSession.getPrincipals().contains("test") || 
                !threadSession.getPrincipals().contains("test1") ||
                !threadSession.getPrincipals().contains("test2")) {
            fail("The principals are not setup correctly.");
        }
        
        testInterceptor.release();
        
        threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test")) {
            fail("The user name is test");
        }
        if (!threadSession.getUser().getSessionId().equals("xxxxxxxxxxxx")) {
            fail("The session id [xxxxxxxxxxxx] was not found");
        }
        if (!threadSession.getPrincipals().contains("test") ||
                threadSession.getPrincipals().contains("test1") ||
                threadSession.getPrincipals().contains("test2")) {
            String principalString = "";
            for (Iterator iter = threadSession.getPrincipals().iterator(); 
                    iter.hasNext();) {
                principalString += "," + (String)iter.next();
            }
            fail("The principals are not setup correctly [" + principalString + "]");
        }
        
        testInterceptor.release();
        
        if (null != permissions.getSession(Thread.currentThread().getId())) {
            fail("There is an existing session for this user");
        }
        
        // test set session
        principals = new HashSet();
        principals.add("test");
        principals.add("test1");
        principals.add("test2");
        session = new Session("test1","yyyyyyyyyyyyy",principals);
        
        testInterceptor.setSession(session);
        
        threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test1")) {
            fail("The user name is test");
        }
        if (!threadSession.getUser().getSessionId().contains("yyyyyyyyyyyyy")) {
            fail("The session id [yyyyyyyyyyyyy] was not found.");
        }
        if (!threadSession.getPrincipals().contains("test") || 
                !threadSession.getPrincipals().contains("test1") ||
                !threadSession.getPrincipals().contains("test2")) {
            fail("The principals are not setup correctly.");
        }
        
    }
    
    
    
}
