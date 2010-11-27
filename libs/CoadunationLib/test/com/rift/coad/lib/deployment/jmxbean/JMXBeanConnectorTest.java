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
 * JMXBeanConnectorTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.jmxbean;

import com.rift.coad.lib.transaction.TransactionDirector;
import junit.framework.*;
import java.util.Set;
import java.util.HashSet;
import com.rift.coad.lib.bean.BeanWrapper;

// coadunation import
import com.rift.coad.lib.security.ThreadsPermissionContainer;
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
import com.rift.coad.lib.deployment.DeploymentLoader;
import java.lang.management.ManagementFactory;
import org.apache.log4j.BasicConfigurator;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.interceptor.InterceptorFactory;

/**
 *
 * @author Brett Chaldecott
 */
public class JMXBeanConnectorTest extends TestCase {
    
    public JMXBeanConnectorTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(JMXBeanConnectorTest.class);
        
        return suite;
    }

    /**
     * Test of testJMXBeanConnector, of class com.rift.coad.lib.deployment.jmxbean.JMXBeanConnector.
     */
    public void testJMXBeanConnector() throws Exception {
        System.out.println("testJMXBeanConnector");
        
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
        
        
        CacheRegistry.init(threadGroup);
        
        
        
        JMXBeanManager beanManager = new JMXBeanManager(permissionContainer,
                threadGroup);
        
        JMXBeanConnector.init(beanManager);
        
        DeploymentLoader deploymentLoader = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        beanManager.load(deploymentLoader);
        
        // load the bean 
        JMXBeanConnector instance = JMXBeanConnector.getInstance();
        Set bindKeys = instance.getJMXBeanKeys();
        if ((bindKeys.size() != 1) || (bindKeys.contains("testjmxbean") == false)) {
            fail("bind object [testjmxbean] not found.");
        }
        
        if (null == instance.getJMXBean("testjmxbean")) {
            fail("Failed to retrieve the object [testjmxbean] not found.");
        }
        
        // load the object into memory
        beanManager.unLoad(deploymentLoader);
        
        bindKeys = instance.getJMXBeanKeys();
        if ((bindKeys.size() != 0) || (bindKeys.contains("testjmxbean") == true)) {
            fail("bind object [testjmxbean] not found.");
        }
        
        CacheRegistry.getInstance().shutdown();
        
        NamingDirector.getInstance().shutdown();
    }

}
