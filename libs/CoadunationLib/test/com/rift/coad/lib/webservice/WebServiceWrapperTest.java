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
 * WebServiceWrapperTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.webservice;

// java imports
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
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

// jaxb imports
import javax.xml.soap.MimeHeaders;


// axis includes
import org.apache.axis.AxisEngine;
import org.apache.axis.server.AxisServer;
import org.apache.axis.management.ServiceAdmin;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.EngineConfiguration;

// junit imports
import junit.framework.*;

// coadunation imports
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;

/**
 *
 * @author mincemeat
 */
public class WebServiceWrapperTest extends TestCase {
    
    public WebServiceWrapperTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WebServiceWrapperTest.class);
        
        return suite;
    }

    /**
     * Test of class com.rift.coad.lib.deployment.webservice.WebServiceManager.
     */
    public void testWebServiceManager() throws Exception {
        System.out.println("testWebServiceManager");
        
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
        
        // instanciate the web service manager
        WebServiceManager instance = new WebServiceManager();
        
        // load the files
        instance.load(deploymentLoader);
        
        // retrieve the keys
        Set paths = instance.getServices();
        if (paths.size() != 1) {
            fail("There should be one path there are [" + paths.size() + "]");
        }
        
        if (paths.contains("/WebServiceTest") == false) {
            fail("There should be one path [/WebServiceTest]");
        }
        
        // retrieve the service reference
        WebServiceWrapper webServiceWrapper = 
                (WebServiceWrapper)instance.getService("/WebServiceTest");
        
        System.out.println(webServiceWrapper.generateWSDL());
        
        String xmlQuery = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=" +
                "\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "<soapenv:Body><helloWorld xmlns=\"urn:WebService/wsdl\">" +
                "<String_1 xmlns=\"\">This is a test call</String_1>" +
                "</helloWorld></soapenv:Body></soapenv:Envelope>";
        
        String xmlResult1 = webServiceWrapper.processRequest(xmlQuery);
        
        
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlQuery.getBytes());
        String xmlResult2 = webServiceWrapper.processRequest(input,
                new MimeHeaders());
        System.out.println("Result 1 [" + xmlResult1 + "]");
        System.out.println("Result 2 [" + xmlResult2 + "]");
        if (!xmlResult1.equals(xmlResult2)) {
            fail("The processing requests failed.");
        }
        
        // unload the service
        instance.unLoad(deploymentLoader);
        
        paths = instance.getServices();
        if (paths.size() != 0) {
            fail("There should be zero paths there are [" + paths.size() + "]");
        }
    }
    
}
