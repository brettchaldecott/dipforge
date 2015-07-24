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
 * BeanManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.bean;

import com.rift.coad.lib.transaction.TransactionDirector;
import junit.framework.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.log4j.BasicConfigurator;

// coaduntion imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.interceptor.InterceptorFactory;

/**
 * The test class
 *
 * @author Brett Chaldecott
 */
public class BeanManagerTest extends TestCase {
    
    public BeanManagerTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BeanManagerTest.class);
        
        return suite;
    }

    /**
     * Test of load method, of class com.rift.coad.lib.deployment.bean.BeanManager.
     */
    public void testBeanManager() throws Exception {
        System.out.println("testBeanManager");
        
        System.out.println("Jar path : " + System.getProperty("test.jar"));
        
        // instanciate the deployment loader
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        
        // initialize the thread permissions
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
        
        // setup the interceptor factory
        InterceptorFactory.init(permissionContainer,sessionManager,
                userStoreManager);
        
        // add a new user object and add to the permission
        Set set = new HashSet();
        set.add("test");
        UserSession user = new UserSession("test1", set);
        permissionContainer.putSession(new Long(Thread.currentThread().getId()),
                new ThreadPermissionSession(
                new Long(Thread.currentThread().getId()),user));
        
        // setup the naming
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // setup the initial context
        Context ctx = new InitialContext();
        
        // setup the cache registry
        CacheRegistry.init(threadGroup);
        
        DeploymentLoader deploymentLoader = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        BeanManager instance = new BeanManager(permissionContainer,threadGroup);
        
        // setup the class loader
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(
                deploymentLoader.getClassLoader());
        NamingDirector.getInstance().initContext();
        
        // load the bean
        instance.load(deploymentLoader);
        
        // retrieve the list of beans
        Set keys = instance.getKeys();
        
        if (keys.size() != 2) {
            fail("There should only be one bean in the list");
        }
        
        if (keys.contains("testbean") == false) {
            fail("The bean that should be loaded should be called [testbean]");
        }
        
        Object ref = ctx.lookup("java:comp/env/bean/testbean");
        
        // unload the bean
        instance.unLoad(deploymentLoader);
        NamingDirector.getInstance().releaseContext();
        Thread.currentThread().setContextClassLoader(original);
        
        // check that the beans get unloaded
        keys = instance.getKeys();
        if (keys.size() != 0) {
            fail("There should be no beans in the list");
        }
        
        try {
            ref = ctx.lookup("java:comp/env/bean/testbean");
            fail("The context is still bound");
        } catch (Exception ex) {
            // do nothing
        }
        
        CacheRegistry.getInstance().shutdown();
        
        NamingDirector.getInstance().shutdown();
    }
    
}
