/*
 * CoadunationUtil: The coadunation util library.
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
 * BeanWrapper.java
 *
 * This object is responsible for wrapping a bean and loading it into memory.
 */

// package path
package com.rift.coad.util.connection;

// java imports
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// junit imports
import junit.framework.*;

// logging import
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 * Test the connection handler.
 *
 * @author Brett Chaldecott
 */
public class ConnectionHandlerTest extends TestCase {
    
    /**
     * The test interface
     */
    public interface TestInter {
        public String helloWorld(String msg) throws java.rmi.RemoteException;
    }
    
    /**
     * The test interface implementation
     */
    public class TestInterImpl implements TestInter {
        
        /**
         *
         */
        public String helloWorld(String msg) throws java.rmi.RemoteException {
            if (throwException) {
                throw new java.rmi.RemoteException("This is a test ex");
            }
            System.out.println("Message is :" + msg);
            called = true;
            return "Bob is your uncle";
        }
    }
    
    public boolean called = false;
    public boolean throwException = false;
    
    public ConnectionHandlerTest(String testName) {
        super(testName);
        BasicConfigurator.configure();
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of class com.rift.coad.util.connection.ConnectionHandler.
     */
    public void testHandler() throws Exception {
        System.out.println("Handler");
        
        TestInterImpl rmiRef = new TestInterImpl();
        RMIConnection rmiConnection = new RMIConnection(null,"test");
        ConnectionHandler handler = new ConnectionHandler(rmiConnection,rmiRef);
        TestInter testInter =  (TestInter)Proxy.newProxyInstance(
                TestInter.class.getClassLoader(),
                new Class[] {TestInter.class},handler);
        
        String result = testInter.helloWorld("test message");
        System.out.println("Result message : " + result);
        
        if (called == false) {
            fail("Failed to make the call");
        }
        
        called = false;
        throwException = true;
        
        try {
            testInter.helloWorld("Test");
            fail("Failed to generate an exception");
        } catch (java.rmi.RemoteException ex) {
            if (called) {
                fail("The call landed");
            }
        }
    }
    
}
