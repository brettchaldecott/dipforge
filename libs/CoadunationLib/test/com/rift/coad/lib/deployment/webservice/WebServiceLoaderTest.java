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
 * WebServiceLoaderTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment.webservice;

import junit.framework.*;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// log 4 j
import org.apache.log4j.BasicConfigurator;

// axis imports
import org.apache.axis.AxisEngine;
import org.apache.axis.server.AxisServer;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.EngineConfiguration;

// coadunation imports
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.webservice.WebServiceWrapper;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.interceptor.InterceptorFactory;
import com.rift.coad.lib.naming.NamingDirector;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadPermissionSession;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.login.LoginManager;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.transaction.TransactionDirector;

/**
 *
 * @author mincemeat
 */
public class WebServiceLoaderTest extends TestCase {
    
    public WebServiceLoaderTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WebServiceLoaderTest.class);
        
        return suite;
    }

    /**
     * Test of of class com.rift.coad.lib.deployment.webservice.WebServiceLoader.
     */
    public void testWebServiceLoader() throws Exception {
        System.out.println("getServices");
        
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
        
        // instanciate the deployment loader
        DeploymentLoader deploymentLoader = new DeploymentLoader(
                new java.io.File(System.getProperty("test.jar")));
        
        // instanciate the axis engine
        AxisManager.init();
        ServiceAdmin.setEngine(AxisManager.getInstance().getServer(),"test");
        
        WebServiceLoader instance = new WebServiceLoader(deploymentLoader);
    }
    
}
