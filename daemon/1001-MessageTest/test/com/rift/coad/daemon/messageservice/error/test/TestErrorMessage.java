/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 2015 Burntjam
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
 * TestBasicTextMessage.java
 */

// coadunation path
package com.rift.coad.daemon.messageservice.error.test;

// java imports
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

// junit imports
import junit.framework.*;

/**
 * The test of the basic text message
 *
 * @author Brett Chaldecott
 */
public class TestErrorMessage extends TestCase {
    
    public TestErrorMessage(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    
    /**
     * The test of the basic text method
     */
    public void testRPCMethod() throws Exception {
        System.out.println("testTextMethod");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.rift.coad.client.naming.CoadunationInitialContextFactory");
        env.put(Context.PROVIDER_URL,System.getProperty("coadunation.master"));
        env.put("com.rift.coad.username","test");
        env.put("com.rift.coad.password","112233");
        Context ctx = new InitialContext(env);
        
        System.out.println("Url is : " + System.getProperty("ErrorClient1"));
        Object obj = ctx.lookup(System.getProperty("ErrorClient1"));
        System.out.println(obj.getClass().getName());
        ErrorClient errorClient = null;
        try {
             errorClient = (ErrorClient)
                    PortableRemoteObject.narrow(obj,
                    ErrorClient.class);
        } catch (Exception ex) {
            System.out.println("Failed to narrow : " + ex.getMessage());
            ex.printStackTrace(System.out);
            throw ex;
        }
        errorClient.testErrorRouting(Integer.parseInt(
                System.getProperty("Num.Error.Messages")));
    }

}
