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
 * BeanLoaderTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.bean;

import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.transaction.TransactionDirector;
import java.util.HashSet;
import java.util.Set;
import junit.framework.*;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.BeanInfo;
import org.apache.log4j.BasicConfigurator;

// coadunation import
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;

/**
 *
 * @author Brett Chaldecott
 */
public class BeanLoaderTest extends TestCase {
    
    public BeanLoaderTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BeanLoaderTest.class);
        
        return suite;
    }

    /**
     * Test of of class com.rift.coad.lib.deployment.bean.BeanLoader.
     */
    public void testBeanLoader() throws Exception {
        System.out.println("getBeans");
        System.out.println("Jar path : " + System.getProperty("test.jar"));
        
        // instanciate the deployment loader
        ThreadsPermissionContainer permissionContainer = 
                new ThreadsPermissionContainer();
        
        // initialize the thread permissions
        SessionManager.init(permissionContainer);
        UserStoreManager userStoreManager = new UserStoreManager();
        UserSessionManager sessionManager = new UserSessionManager(permissionContainer,
                userStoreManager);
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
        
        DeploymentLoader deploymentLoader = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        // instanciate the bean loader
        BeanLoader instance = new BeanLoader(deploymentLoader,
                permissionContainer,threadGroup);
        instance.setContextClassLoader(deploymentLoader.getClassLoader());
        threadGroup.addThread(instance,"test");
        instance.start();
        instance.join();
        if (!instance.wasSucessfull()) {
            throw instance.getException();
        }
        
        // retrieve the list of beans
        Map beans = instance.getBeans();
        if (beans.get("testbean") == null) {
            fail("Failed to load the bean");
            return;
        }
        
        // unload
        instance.stopThreads();
        
    }
    
}
