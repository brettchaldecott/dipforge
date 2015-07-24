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
 * JMXBeanManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.jmxbean;

import com.rift.coad.lib.transaction.TransactionDirector;
import junit.framework.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import javax.management.MBeanServer;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.lang.management.ManagementFactory;
import org.apache.log4j.Logger;
import com.rift.coad.lib.bean.BeanWrapper;


// coaduntion imports
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.interceptor.InterceptorFactory;

/**
 * 
 *
 * @author Brett Chaldecott
 */
public class JMXBeanManagerTest extends TestCase {
    
    public JMXBeanManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(JMXBeanManagerTest.class);
        
        return suite;
    }

    /**
     * Test of class com.rift.coad.lib.deployment.jmxbean.JMXBeanManager.
     */
    public void testJMXBeanManager() throws Exception {
        System.out.println("testJMXBeanManager");
        
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
        
        // instanciate the thread manager
        CoadunationThreadGroup threadGroup = new CoadunationThreadGroup(sessionManager,
            userStoreManager);
        
        // setup the naming context
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();

        // setup the initial context
        Context ctx = new InitialContext();
        
        CacheRegistry.init(threadGroup);
        
        DeploymentLoader deploymentLoader = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        JMXBeanManager instance = new JMXBeanManager(permissionContainer,
                threadGroup);
        
        // setup the class loader
        ClassLoader loader = new URLClassLoader(new URL[0]);
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(loader);
        NamingDirector.getInstance().initContext();
        
        // load the object into memory
        instance.load(deploymentLoader);
        
        Set objectKeys = instance.getObjectKeys();
        if (objectKeys.size() != 1) {
          fail("There should be atleast one object [" + objectKeys.size() + "]");
        }
        
        if (objectKeys.contains("com.test2:type=JMXBean1") == false) {
            fail("Object [com.test2:type=JMXBean1] not found.");  
        }
        
        if (null == instance.getObject("com.test2:type=JMXBean1")) {
            fail("Failed to retrieve the Object [com.test2:type=JMXBean1]");  
        }
        
        Set bindKeys = instance.getBindKeys();
        if (bindKeys.size() != 1) {
          fail("There should be atleast one bind [" + bindKeys.size() + "]");
        }
        
        if (bindKeys.contains("testjmxbean") == false) {
            fail("Object [com.test2:type=JMXBean1] not found.");  
        }
        
        if (null == instance.getBindObject("testjmxbean")) {
            fail("Failed to retrieve the Object [testjmxbean]");  
        }
        
        Object ref = ctx.lookup("java:comp/env/jmx/testjmxbean");
        
        // unload the object
        instance.unLoad(deploymentLoader);
        NamingDirector.getInstance().releaseContext();
        Thread.currentThread().setContextClassLoader(loader);
        
        bindKeys = instance.getBindKeys();
        if (bindKeys.size() != 0) {
          fail("There should be no keys [" + bindKeys.size() + "]");
        }
        
        objectKeys = instance.getObjectKeys();
        if (objectKeys.size() != 0) {
          fail("There should be no object [" + objectKeys.size() + "]");
        }
        
        try {
            ref = ctx.lookup("java:comp/env/jmx/testjmxbean");
            fail("JMX Bean still bound");
        } catch (Exception ex) {
            // do nothing
        }
        
        CacheRegistry.getInstance().shutdown();
        NamingDirector.getInstance().shutdown();
    }
    
}
