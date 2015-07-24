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
 * DeploymentManagerTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment;

// junit framework
import com.rift.coad.lib.transaction.TransactionDirector;
import junit.framework.*;

// java imports
import java.io.FileWriter;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


// axis includes
import org.apache.axis.AxisEngine;
import org.apache.axis.server.AxisServer;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.EngineConfiguration;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.cache.CacheRegistry;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.bean.BeanManager;
import com.rift.coad.lib.deployment.jmxbean.JMXBeanManager;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.login.handlers.PasswordInfoHandler;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.Validator;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.interceptor.InterceptorFactory;


/**
 * 
 *
 * @author Brett Chaldecott
 */
public class DeploymentManagerTest extends TestCase {
    
    public DeploymentManagerTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(DeploymentManagerTest.class);
        
        return suite;
    }
    
    
    /**
     * This method test the deployment manager.
     */
    public void testDeploymentManager() throws Exception {
        
        // init the test jar file
        File jarFile = new File(System.getProperty("test.jar"));
        if (jarFile.isFile() == false){
            throw new Exception("Test not configured properly");
        }
        
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
        
        // init the naming director
        NamingDirector.init(threadGroup);
        
        // instanciate the transaction director
        TransactionDirector transactionDirector = TransactionDirector.init();
        
        // init the database source
        CacheRegistry.init(threadGroup);
        
        // instanciate the bean manager
        BeanManager beanManager = new BeanManager(permissions,
                threadGroup);
        JMXBeanManager jmxBeanManager = new JMXBeanManager(permissions,
                threadGroup);
        
        // instanciate the axis engine
        AxisManager.init();
        
        // instanciate the web service manager
        WebServiceManager webServiceManager = new WebServiceManager();
        
        // init the test
        TestMonitor.init();
        
        // instanciate the thread manager
        DeploymentManager deploymentManager = new DeploymentManager(
                threadGroup,beanManager,jmxBeanManager, webServiceManager);
        
        // wait on the test
        TestMonitor.getInstance().monitor();
        
        // reset the test monitor
        TestMonitor.init();
        
        // touch the test jar file
        System.out.println("Set the last modified date on the file [" + 
                jarFile.getPath() + "]");
        if (jarFile.setLastModified(new Date().getTime()) == false){
            fail("Failed to set the last modified time for [" + 
                    jarFile.getPath() + "]");
        }
        
        // wait on the test
        TestMonitor.getInstance().monitor();
        
        // terminate the threads
        deploymentManager.shutdown();
        
        CacheRegistry.getInstance().shutdown();
        
        NamingDirector.getInstance().shutdown();
    }
    
}
