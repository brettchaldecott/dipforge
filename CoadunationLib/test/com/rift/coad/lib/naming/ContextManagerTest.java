/*
 * CoadunationLib: The coaduntion implementation library.
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
 * ContextManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.naming;

import junit.framework.*;
import org.apache.log4j.Logger;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

import java.util.Set;
import java.util.HashSet;
import com.rift.coad.lib.interceptor.InterceptorFactory;
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
 * @author Brett Chaldecott
 */
public class ContextManagerTest extends TestCase {
    
    public ContextManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ContextManagerTest.class);
        
        return suite;
    }

    /**
     * Test of testContextManager, of class com.rift.coad.lib.naming.ContextManager.
     */
    public void testContextManager() throws Exception {
        System.out.println("testContextManager");
        
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
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("test1", set);
        permissions.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        
        NamingDirector.init(threadGroup);
        
        Context startCtx = new InitialContext();
        
        ContextManager instance1 = new ContextManager("java:comp/env/test1");
        ContextManager instance2 = new ContextManager("java:comp/env/test2");
        
        System.out.println("Bind value 1");
        instance1.bind("test1","value1");
        System.out.println("Bind value 2");
        instance2.bind("test2","value2");
        
        System.out.println("Perform lookup");
        String value1 = (String)startCtx.lookup("java:comp/env/test1/test1");
        System.out.println("User the values");
        
        if ((value1 == null) || (value1.equals("value1") == false))
        {
            fail("Failed to retrieve value 1");
        }
        System.out.println("Test value 1 : " + value1);
        
        String value2 = (String)startCtx.lookup("java:comp/env/test2/test2");
        if ((value2 == null) || (value2.equals("value2") == false))
        {
            fail("Failed to retrieve value 2");
        }
        System.out.println("Test value 2 : " + value2);
        
        instance1.unbind("test1");
        try {
            value1 = (String)startCtx.lookup("java:comp/env/test1/test1");
            fail("Test 1 value is still bound");
        } catch (Exception ex) {
            // ignore
        }
        
        NamingDirector.getInstance().shutdown();
    }

    
}
