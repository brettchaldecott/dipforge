/*
 * SampleFactory: The basic factory object implementation.
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
 * BasicBeanImplTest.java
 */

package com.test;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import junit.framework.*;
import java.rmi.RemoteException;

/**
 *
 * @author Admin
 */
public class BasicBeanImplTest extends TestCase {
    
    public BasicBeanImplTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(BasicBeanImplTest.class);
        
        return suite;
    }

    /**
     * Test of testMethod method, of class com.test.BasicBeanImpl.
     */
    public void testClient() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        // the reference to the master coadunation instance "host:port"
        // this can be hard code or set in the properties file
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.ref"));
        env.put("com.rift.coad.username","test");
        env.put("com.rift.coad.password","112233");
        Context ctx = new InitialContext(env);
        
        // set in the reference to the coadunation sample
        // java:network/env/coadunationid/basicfactory
        Object obj = ctx.lookup(System.getProperty("sample.url"));
        com.test.BasicBeanInterface beanInterface =
                (com.test.BasicBeanInterface)
                PortableRemoteObject.narrow(obj,
                com.test.BasicBeanInterface.class);
        if (beanInterface == null) {
            fail("Failed to narrow. Has a security manager and policy been " +
                    "put in place");
        }
        FactoryObjectInterface factoryObject = 
                beanInterface.addObject("something");
        factoryObject.helloWorld();
    }
    
}
