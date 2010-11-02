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
 * HttpDaemonTest.java
 *
 * JUnit based test
 */

// package path
package com.rift.coad.lib.httpd;

// java imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.InetAddress;
import javax.xml.rpc.Service;
import javax.xml.rpc.JAXRPCException;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.Stub;

// junit imports
import junit.framework.*;

// log 4 j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentLoader;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;
import com.rift.coad.lib.deployment.webservice.WebServiceConnector;
import com.rift.coad.lib.thirdparty.axis.AxisManager;
import com.rift.coad.lib.security.RoleManager;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.lib.security.user.UserStoreManager;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;
import com.rift.coad.lib.security.user.UserSessionManager;
import com.rift.coad.lib.security.user.UserSessionManagerAccessor;
import com.rift.coad.lib.security.login.LoginManager;


/**
 *
 * @author Brett Chaldecott
 */
public class HttpDaemonTest extends TestCase {
    
    
    /**
     * The service end point interface for the web service
     */
    public interface WebServiceSEI extends java.rmi.Remote {

        /**
         * This method will be called to test the web service end point interface.
         *
         * @param msg The string containing the message for the server.
         * @return The containing the message from the server.
         */
        public String helloWorld(String msg);
    }
    
    
    /**
     * Web service client
     */
    public class POJOWebServiceClient {

        // class static variables
        private final static String SERVICE_NAME = "urn:WebService/wsdl";

        // member variables
        private String url = null;
        private WebServiceSEI proxy = null;
        
        // the class responsible for handling the auth requests
        public class TestAuthenticator extends java.net.Authenticator {
            private String username = null;
            private String password = null;

            /**
             * The constructor of the test authenticator
             */
            public TestAuthenticator (String username, String password) {
                this.username = username;
                this.password = password;
            }


            /**
             * Retrieve the password authentication information
             */
            public java.net.PasswordAuthentication getPasswordAuthentication () {
                return new java.net.PasswordAuthentication(username,
                        password.toCharArray());
            }
        }
        
        
        /** 
         * Creates a new instance of POJOWebServiceClient 
         */
        public POJOWebServiceClient(String url) {
            this.url = url;
        }

        /**
         * This method returns a reference to the web service interface.
         *
         * @return The reference ot the test web service interface.
         * @exception Exception
         */
        public WebServiceSEI connect() throws Exception {
            // the proxy reference 
            if (proxy != null) {
                return proxy;
            }

            // make the necessary connection
            try {
                
                java.net.Authenticator.setDefault(new TestAuthenticator(
                    "test","112233"));
                
                URL serviceUrl = new URL(url);

                ServiceFactory serviceFactory = ServiceFactory.newInstance();
                
                Service testService = 
                        serviceFactory.createService(serviceUrl,
                        new QName(SERVICE_NAME,"WebService"));

                proxy = (WebServiceSEI)testService.getPort(
                        new QName(SERVICE_NAME,"WebServiceSEIPort"),
                        WebServiceSEI.class);

                Stub stub = (Stub)proxy;
                stub._setProperty(Stub.USERNAME_PROPERTY,"test");
                stub._setProperty(Stub.PASSWORD_PROPERTY,"112233");

                return proxy;

            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                throw new Exception(
                        "Failed to make a connection to the test service : " +
                        ex.getMessage(),ex);
            }
        }

    }
    
    public HttpDaemonTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(HttpDaemonTest.class);
        
        return suite;
    }

    /**
     * Test of shutdown method, of class com.rift.coad.lib.httpd.HttpDaemon.
     */
    public void testDaemon() throws Exception {
        System.out.println("Daemon");
        
        // retrieve full host name
        String fqdn = InetAddress.getLocalHost().getCanonicalHostName();
        
        // TODO: Implement test
        try {
            httpConnection(fqdn);
        } catch (Exception ex) {
            // ignore it
        }
        POJOWebServiceClient pojoWebServiceClient = new POJOWebServiceClient(
                "http://" + fqdn + ":8085/WebServiceTest?WSDL");
        rpcConnection(pojoWebServiceClient);
    }
    
    
    /**
     * This method makes a standard http connection and query
     */
    private void httpConnection(String fqdn) throws Exception {
        try {
            URL url = new URL("http://" + fqdn + ":8085/");
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            fail("No out put should be retrieve here");
        } catch (java.io.FileNotFoundException ex) {
            // ignore
        }
    }
    
    /**
     * This makes a wsdl connection
     */
    private void wsdlConnection(String fqdn) throws Exception {
        URL url = new URL("http://" + fqdn + ":8085/WebServiceTest?WSDL");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
    
    
    /**
     * This method makes a standard http connection and query
     */
    private void rpcConnection(POJOWebServiceClient pojoWebServiceClient) 
            throws Exception {
        String result = null;
        try {
            WebServiceSEI webServiceSEI = pojoWebServiceClient.connect();
            result = webServiceSEI.helloWorld("This is a test call");
            System.out.println("Server returned : " + result);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            throw ex;
        }
        if (result.equals("Test failed") || result.equals("bob")) {
            fail("The test failed");
        }
    }
    
    
    
}
