/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2007  2015 Burntjam
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
 * CoadunationInterceptorTest.java
 */

// package imports
package com.rift.coad.daemon.tomcat.security;

// java imports
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// junit framework
import junit.framework.*;


// logging
import org.apache.log4j.Logger;

// coadunation
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.interceptor.InterceptorFactory;
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
 *
 * @author brett
 */
public class CoadunationInterceptorTest extends TestCase {
    
    public CoadunationInterceptorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of pushUser method, of class com.rift.coad.daemon.tomcat.security.CoadunationInterceptor.
     */
    public void testPushUser() throws Exception {
        System.out.println("pushUser");
        
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
        
        CoadunationInterceptor instance = new CoadunationInterceptor();
        
        Set principals = new HashSet();
        
        instance.pushUser(null);
        
        try {
            permissions.getSession();
            fail("Should not have been able to get the session");
        } catch (com.rift.coad.lib.security.SecurityException ex) {
            // ignore
        }
        
        
        principals = new HashSet();
        principals.add("test1");
        principals.add("test2");
        UserSession session2 = new UserSession("test1", "yyyyyyyyyyyyy",
                principals);
        
        instance.pushUser(session2);
        
        ThreadPermissionSession threadSession = permissions.getSession();
        
        if (!threadSession.getUser().getName().equals("test1")) {
            fail("The user name is [" +
                    threadSession.getUser().getName() + "]");
        }
        if (!threadSession.getUser().getSessionId().contains("yyyyyyyyyyyyy")) {
            fail("The session id [yyyyyyyyyyyyy] was not found.");
        }
        if (    !threadSession.getPrincipals().contains("test1") ||
                !threadSession.getPrincipals().contains("test2")) {
            fail("The principals are not setup correctly.");
        }
        
        
        instance.popUser();
        
        try {
            permissions.getSession();
            fail("Should not have been able to get the session");
        } catch (com.rift.coad.lib.security.SecurityException ex) {
            // ignore
        }
        
        instance.popUser();
        
        try {
            permissions.getSession();
            fail("Should not have been able to get the session");
        } catch (com.rift.coad.lib.security.SecurityException ex) {
            // ignore
        }
        
    }

}
