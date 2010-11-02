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


// package imports
package com.rift.coad.client;

// java imports
import java.util.Hashtable;
import java.rmi.Remote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import org.omg.CORBA.ORB;

// junit imports
import junit.framework.*;

/**
 * Test the RMI connection to coadunation.
 *
 * @author Brett Chaldecott
 */
public class CoadunationRMIClient extends TestCase {
    
    public CoadunationRMIClient(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    
    /**
     * This method tests the RMI client
     */
    public void testClient() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
        env.put("com.rift.coad.username","test");
        env.put("com.rift.coad.password","112233");
        Context ctx = new InitialContext(env);
        
        Object obj = ctx.lookup(System.getProperty("coadunation.jndi"));
        System.out.println(obj.getClass().getName());
        com.test.BeanInterface beanInterface = null;
        try {
             beanInterface= (com.test.BeanInterface)
                    PortableRemoteObject.narrow(obj,
                    com.test.BeanInterface.class);
        } catch (Exception ex) {
            System.out.println("Failed to narrow : " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        if (beanInterface == null) {
            fail("Failed to narrow the interface");
        }
        System.out.println("Hello call : " + beanInterface.helloWorld("hmmm"));
        Object ref = beanInterface.getObject("test");
        com.test.FactoryParentInterface facParInter = (com.test.FactoryParentInterface)
                beanInterface.getObject("test");
        facParInter.parentMethod("bob");
        com.test.FactoryInterface facInter = (com.test.FactoryInterface)
                PortableRemoteObject.narrow(facParInter,
                com.test.FactoryInterface.class);
        facInter.getList("testing");
        System.out.println("Int value is : " + facInter.getInt("bob"));
        try {
            beanInterface.exceptionMethod("Test");
        } catch (Exception ex) {
            System.out.println("Caught an exception : " + ex.getMessage());
            ex.printStackTrace(System.out);
            if (ex instanceof java.rmi.ServerException) {
                java.rmi.ServerException ex2 = (java.rmi.ServerException)ex;
                System.out.println("Wrapped exception is : " +
                        ex2.getCause().getClass().getName());
            }
            if (ex instanceof java.rmi.RemoteException) {
                java.rmi.RemoteException ex2 = (java.rmi.RemoteException)ex;
                System.out.println("Wrapped exception is : " +
                        ex2.getCause().getClass().getName());
                if (ex2.getCause() instanceof org.omg.CORBA.UNKNOWN) {
                    org.omg.CORBA.UNKNOWN ex3 =
                            (org.omg.CORBA.UNKNOWN)ex2.getCause();
                    System.out.println(" Wrapped message : " +
                            ex3.getMessage());
                    
                }
            }
        }
        com.test.ValueObject value = new com.test.ValueObject();
        value.setValue("Man this is a test");
        beanInterface.setValue(value);
        
    }
    
    
    /**
     * This method tests the RMI client
     */
    public void testClient2() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
        env.put("com.rift.coad.username","test");
        env.put("com.rift.coad.password","112233");
        Context ctx = new InitialContext(env);
        
        Object obj = ctx.lookup("java:network/env/newbie/testbean3");
        System.out.println(obj.getClass().getName());
        com.test3.BeanInterface beanInterface = null;
        try {
             beanInterface= (com.test3.BeanInterface)
                    PortableRemoteObject.narrow(obj,
                    com.test3.BeanInterface.class);
        } catch (Exception ex) {
            System.out.println("Failed to narrow : " + ex.getMessage());
            ex.printStackTrace(System.out);
            fail("Failed to narrow");
        }
        if (beanInterface == null) {
            fail("Failed to narrow the interface");
        }
        System.out.println("Hello call : " + beanInterface.helloWorld("hmmm"));
        Object ref = beanInterface.getObject("test");
        com.test3.FactoryParentInterface facParInter = 
                (com.test3.FactoryParentInterface)
                beanInterface.getObject("test");
        facParInter.parentMethod("bob");
        com.test3.FactoryInterface facInter = (com.test3.FactoryInterface)
                PortableRemoteObject.narrow(facParInter,
                com.test3.FactoryInterface.class);
        facInter.getList("testing");
        System.out.println("Int value is : " + facInter.getInt("bob"));
        try {
            beanInterface.exceptionMethod("Test");
        } catch (Exception ex) {
            System.out.println("Caught an exception : " + ex.getMessage());
            ex.printStackTrace(System.out);
            if (ex instanceof java.rmi.ServerException) {
                java.rmi.ServerException ex2 = (java.rmi.ServerException)ex;
                System.out.println("Wrapped exception is : " +
                        ex2.getCause().getClass().getName());
            }
            if (ex instanceof java.rmi.RemoteException) {
                java.rmi.RemoteException ex2 = (java.rmi.RemoteException)ex;
                System.out.println("Wrapped exception is : " +
                        ex2.getCause().getClass().getName());
                if (ex2.getCause() instanceof org.omg.CORBA.UNKNOWN) {
                    org.omg.CORBA.UNKNOWN ex3 =
                            (org.omg.CORBA.UNKNOWN)ex2.getCause();
                    System.out.println(" Wrapped message : " +
                            ex3.getMessage());
                    
                }
            }
        }
        com.test3.ValueObject value = new com.test3.ValueObject();
        value.setValue("Man this is a test");
        beanInterface.setValue(value);
        
    }
    
    /**
     * This method tests the RMI client
     */
    public void testUnauthedClient() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
        Context ctx = new InitialContext(env);
        
        Object obj = ctx.lookup("java:network/env/newbie/testbean");
        com.test.BeanInterface beanInterface =
                (com.test.BeanInterface)
                PortableRemoteObject.narrow(obj,
                com.test.BeanInterface.class);
        if (beanInterface == null) {
            fail("Failed to narrow the interface");
        }
        try {
            System.out.println("Hello call : " + beanInterface.helloWorld("hmmm"));
            fail("Access was granted");
        } catch (java.rmi.AccessException ex) {
            System.out.println("Caught the access denied exception");
        }
    }
    
    
}
