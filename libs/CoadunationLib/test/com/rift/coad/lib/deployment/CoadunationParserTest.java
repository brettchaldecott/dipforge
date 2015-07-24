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
 * CoadunationParserTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.deployment;

import java.util.List;
import java.util.Vector;
import junit.framework.*;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import com.rift.coad.lib.common.ResourceReader;

/**
 *
 * @author mincemeat
 */
public class CoadunationParserTest extends TestCase {
    
    public CoadunationParserTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(CoadunationParserTest.class);
        
        return suite;
    }

    /**
     * Test of getDeploymentInfo method, of class com.rift.coad.lib.deployment.CoadunationParser.
     */
    public void testGetDeploymentInfo() throws Exception {
        System.out.println("getDeploymentInfo");
        
        ResourceReader resourceReader = new ResourceReader(
                "com/rift/coad/lib/deployment/coadunation-test.xml");
        if (resourceReader.getDocument() == null) {
            fail("Document not retrieved.");
            return;
        }
        
        System.out.println("Parse the document");
        CoadunationParser instance = new CoadunationParser(
                resourceReader.getDocument());
        System.out.println("Get the results");
        
        DeploymentInfo result = instance.getDeploymentInfo();
        
        // basic result test
        if (result.getVersion().equals("1") == false) {
            fail("The parser failed to retrieve the correct version");
            return;
        } else if (result.getName().equals("CoadunationTest") == false) {
            fail("The parser failed to retrieve the correct name");
            return;
        } else if (result.getName().equals("CoadunationTest") == false) {
            fail("The parser failed to retrieve the correct name");
            return;
        } else if (result.getDescription().equals("The test coadunationfile") 
            == false) {
            fail("Failed to retrieve the description.");
            return;
        }
        
        // web service test
        java.util.Map map = result.getWebServices();
        if (map.size() != 2) {
            fail("Two web service must exist [" + map.size() + "]");
            return;
        }
        com.rift.coad.lib.deployment.WebServiceInfo webServiceInfo =
                (com.rift.coad.lib.deployment.WebServiceInfo)map.get(
                "com.test.webservice");
        if (webServiceInfo == null) {
            fail("The web service value is not set correctly.");
            return;
        } else if (webServiceInfo.getClassName().equals("com.test.webservice")
            == false) {
            fail("Class name not set to com.test.webservice");
            return;
        } else if (webServiceInfo.getPath().equals("/WebService")
            == false) {
            fail("Path not set to /WebService");
            return;
        } else if (webServiceInfo.getRole().equals("test")
            == false) {
            fail("Role not set to test");
            return;
        } else if (webServiceInfo.getWSDLPath().equals(
                "com/webservicetest/webservice/WebService.wsdl")
            == false) {
            fail("WSDL path not set to " +
                    "com/webservicetest/webservice/WebService.wsdl");
            return;
        } else if (webServiceInfo.getTransaction()) {
            fail("The transaction was set");
            return;
        } else if (webServiceInfo.getClasses().size() != 2) {
            fail("There is no list of classes for the web service.");
            return;
        } else if (webServiceInfo.getClasses().size() == 2) {
            if (!webServiceInfo.getClasses().get(0).
                    equals("com.test2.returnbean")) {
                fail("Expected com.test2.returnbean"); 
            }
            if (!webServiceInfo.getClasses().get(1).
                    equals("com.test2.returnbean2")) {
                fail("Expected com.test2.returnbean2"); 
            }
        }
        
        webServiceInfo =
                (com.rift.coad.lib.deployment.WebServiceInfo)map.get(
                "com.test.webservice2");
        if (webServiceInfo == null) {
            fail("The web service value is not set correctly.");
            return;
        } else if (webServiceInfo.getClassName().equals("com.test.webservice2")
            == false) {
            fail("Class name not set to com.test.webservice2");
            return;
        } else if (webServiceInfo.getPath().equals("/WebService2")
            == false) {
            fail("Path not set to /WebService2");
            return;
        } else if (webServiceInfo.getRole().equals("test")
            == false) {
            fail("Role not set to test");
            return;
        } else if (webServiceInfo.getWSDLPath().equals(
                "com/webservicetest/webservice/WebService2.wsdl")
            == false) {
            fail("WSDL path not set to " +
                    "com/webservicetest/webservice/WebService2.wsdl");
            return;
        } else if (!webServiceInfo.getTransaction()) {
            fail("The transaction was not set");
            return;
        } 
        
        // test the jmx beans
        map = result.getJmxBeans();
        if (map.size() != 1) {
            fail("One jmx bean must exist [" + map.size() + "]");
            return;
        }
        
        com.rift.coad.lib.deployment.JMXBeanInfo jmxBeanInfo = 
                (com.rift.coad.lib.deployment.JMXBeanInfo)map.get(
                "com.test2.jmxbean");
        if (jmxBeanInfo == null) {
            fail("The JMX beans are not set.");
            return;
        } else if (jmxBeanInfo.getInterfaceName().equals("com.test2.jmxbeaninter")
            == false) {
            fail("JMX Interface name not set to com.test2.jmxbeaninter");
            return;
        } else if (jmxBeanInfo.getClassName().equals("com.test2.jmxbean")
            == false) {
            fail("JMX Class name not set to com.test2.jmxbean");
            return;
        } else if (jmxBeanInfo.getObjectName().equals("com.test2:type=jmxbean")
            == false) {
            fail("JMX object name not set to com.test2:type=jmxbean");
            return;
        } else if (jmxBeanInfo.getBindName().equals("jmxbean")
            == false) {
            fail("JMX bind name not set to jmxbean");
            return;
        } else if (jmxBeanInfo.getRole().equals("test")
            == false) {
            fail("JMX role not set to test");
            return;
        } else if (jmxBeanInfo.getUsername().equals("testuser")
            == false) {
            fail("JMX username not set to testuser [" + 
                    jmxBeanInfo.getUsername() + "]");
            return;
        } else if (!jmxBeanInfo.getCacheResults()) {
            fail("JMX Bean cache result is set to false should be set to true");
            return;
        } else if (jmxBeanInfo.getCacheTimeout() != 10) {
            fail("The cache time out is not set to 10 meaning default.");
            return;
        } else if (jmxBeanInfo.getTransaction() != true) {
            fail("The transaction is not set");
            return;
        }
        Vector classes = jmxBeanInfo.getClasses();
        checkForClass(classes, "com.test2.returnbean");
        checkForClass(classes, "com.test2.returnbean2");
                
        // check jmx threads
        List threadList = jmxBeanInfo.getThreadInfoList();
        if (threadList.size() != 1) {
            fail("There must be one thread in the list [" + 
                    threadList.size() + "]");
        }
        DeploymentThreadInfo threadInfo = 
                (DeploymentThreadInfo)threadList.get(0);
        
        if (threadInfo.getClassName().equals("com.test2.thread") == false) {
            fail("Thread class name is set incorrectly to [" + 
                    threadInfo.getClassName() + "]");
        } else if (threadInfo.getUsername().equals("test") == false) {
            fail("Thread username is set incorrectly to [" + 
                    threadInfo.getUsername() + "]");
        } else if (threadInfo.getThreadNumber() != 1) {
            fail("Thread number is set incorrectly to [" + 
                    threadInfo.getThreadNumber() + "]");
        }
        
        
        // test the jmx beans
        map = result.getBeans();
        if (map.size() != 2) {
            fail("One coadunation bean must exist [" + map.size() + "]");
            return;
        }
        
        com.rift.coad.lib.deployment.BeanInfo beanInfo = 
                (com.rift.coad.lib.deployment.BeanInfo)map.get(
                "com.test3.testbean");
        if (beanInfo == null) {
            fail("Failed to retrieve the coaduantion test bean info.");
            return;
        } else if (beanInfo.getClassName().equals("com.test3.testbean")
            == false) {
            fail("Coadunation bean Class name not set to com.test3.testbean");
            return;
        } else if (beanInfo.getInterfaceName().equals("com.test3.testinter")
            == false) {
            fail("Coadunation bean interface name not set to com.test3.testinter");
            return;
        } else if (beanInfo.getRole().equals("test")
            == false) {
            fail("Coadunation bean role name not set to test");
            return;
        } else if (beanInfo.getBindName().equals("testbean")
            == false) {
            fail("Coadunation bean bind name not bean set to testbean");
            return;
        } else if (beanInfo.getUsername().equals("testuser")
            == false) {
            fail("Coadunation username not set to testuser");
            return;
        } else if (!beanInfo.getCacheResults()) {
            fail("Coadunation bean cache result is set to false should be " +
                    "set to true");
            return;
        } else if (beanInfo.getCacheTimeout() != 10) {
            fail("The cache timeout is not set to 10 : " + 
                    beanInfo.getCacheTimeout());
            return;
        } else if (beanInfo.getTransaction() != true) {
            fail("The transaction is not set to true");
            return;
        }
        
        classes = beanInfo.getClasses();
        checkForClass(classes, "com.test3.returnbean");
        checkForClass(classes, "com.test3.returnbean2");
        
        // check coadunation threads
        threadList = beanInfo.getThreadInfoList();
        if (threadList.size() != 1) {
            fail("There must be one thread in the list [" + 
                    threadList.size() + "]");
        }
        threadInfo = 
                (DeploymentThreadInfo)threadList.get(0);
        
        if (threadInfo.getClassName().equals("com.test3.thread") == false) {
            fail("Thread class name is set incorrectly to [" + 
                    threadInfo.getClassName() + "]");
        } else if (threadInfo.getUsername().equals("test") == false) {
            fail("Thread username is set incorrectly to [" + 
                    threadInfo.getUsername() + "]");
        } else if (threadInfo.getThreadNumber() != 1) {
            fail("Thread number is set incorrectly to [" + 
                    threadInfo.getThreadNumber() + "]");
        }
        
        
        beanInfo = (com.rift.coad.lib.deployment.BeanInfo)map.get(
                "com.test4.testbean");
        if (beanInfo.getTransaction() != false) {
            fail("The transaction is not set to false");
            return;
        }
    }
    
    /**
     * Check for the class name
     */
    private void checkForClass(Vector classes, String className) {
        for (int index = 0; index < classes.size(); index++) {
            String testName = (String)classes.get(index);
            if (testName.equals(className)) {
                return;
            }
        }
        fail("The class [" + className + "] was not found.");
    }
    
}
