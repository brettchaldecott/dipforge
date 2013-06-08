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
 * CosNamingContextManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.naming.cos;

import junit.framework.*;
import org.apache.log4j.Logger;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.net.URLClassLoader;
import java.net.URL;

import java.util.Set;
import java.util.HashSet;
import com.rift.coad.lib.naming.NamingDirector;
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
public class CosNamingContextManagerTest extends TestCase {
    
    public CosNamingContextManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CosNamingContextManagerTest.class);
        
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
        
        Context context = new InitialContext();
        
        context.bind("java:comp/env/test","fred");
        context.bind("java:comp/env/test2","fred2");
        
        if (!context.lookup("java:comp/env/test").equals("fred")) {
           fail("Could not retrieve the value for test");
        }
        if (!context.lookup("java:comp/env/test2").equals("fred2")) {
           fail("Could not retrieve the value for test2");
        }
        System.out.println("Creating the sub context");
        Context subContext = context.createSubcontext("java:comp/env/test3/test3");
        System.out.println("Adding the binding for bob to the sub context");
        subContext.bind("bob","bob");
        System.out.println("Looking up the binding for bob on the sub context.");
        Object value = subContext.lookup("bob");
        System.out.println("Object type is : " + value.getClass().getName());
        if (!value.equals("bob")) {
           fail("Could not retrieve the value bob");
        }
        if (!context.lookup("java:comp/env/test3/test3/bob").equals("bob")) {
           fail("Could not retrieve the value bob");
        }
        
        ClassLoader loader = new URLClassLoader(new URL[0]);
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(loader);
        NamingDirector.getInstance().initContext();
        
        context.bind("java:comp/env/test5","fred5");
        if (!context.lookup("java:comp/env/test5").equals("fred5")) {
           fail("Could not retrieve the value fred5");
        }
        
        Thread.currentThread().setContextClassLoader(original);
        
        try{
            context.lookup("java:comp/env/test5");
            fail("Failed retrieve a value that should not exist");
        } catch (NameNotFoundException ex) {
            // ignore
        }
        
        Thread.currentThread().setContextClassLoader(loader);
        
        NamingDirector.getInstance().releaseContext();
        
        try{
            context.lookup("java:comp/env/test5");
            fail("Failed retrieve a value that should not exist");
        } catch (NameNotFoundException ex) {
            // ignore
        }
        Thread.currentThread().setContextClassLoader(original);
        System.out.println("Add value 1");
        context.bind("basic","basic");
        System.out.println("Add value 2");
        context.bind("basic2/bob","basic2");
        if (context.lookup("basic") != null) {
            fail("Could not retrieve the basic value from the [" + 
                    context.lookup("basic") + "]");
        }
        if (context.lookup("basic2/bob") != null) {
            fail("Could not retrieve the basic value from the JNDI [" +
                    context.lookup("basic2/bob") + "]");
        }
        
        try {
            context.bind("java:network/env/test","fred");
            fail("This should have thrown as only relative urls can be used " +
                    "JNDI");
        } catch (NamingException ex) {
            // ignore
        }
        
        try {
            context.unbind("java:network/env/test");
            fail("This should have thrown as only relative urls can be used " +
                    "JNDI");
        } catch (NamingException ex) {
            // ignore
        }
        context.rebind("basic","test1");
        context.rebind("basic2/bob","test2");
        
        if (context.lookup("basic") != null) {
            fail("Could not retrieve the basic value from the JNDI");
        }
        if (context.lookup("basic2/bob") != null) {
            fail("Could not retrieve the basic value from the JNDI");
        }
        
        context.unbind("basic");
        context.unbind("basic2/bob");
        
        try{
            context.lookup("basic2/bob");
            fail("The basic bob value could still be found");
        } catch (NameNotFoundException ex) {
            // ignore
        }
        
        NamingDirector.getInstance().shutdown();
    }

    
}
