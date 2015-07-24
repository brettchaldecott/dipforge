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
 * MemoryContextTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.naming.cos;

import junit.framework.*;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NameNotFoundException;

/**
 *
 * @author mincemeat
 */
public class MemoryContextTest extends TestCase {
    
    public MemoryContextTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MemoryContextTest.class);
        
        return suite;
    }

    /**
     * Test of addToEnvironment method, of class com.rift.coad.lib.naming.cos.MemoryContext.
     */
    public void testMemoryContext() throws Exception {
        System.out.println("MemoryContext");
        
        Hashtable env = new Hashtable();
        MemoryContext memoryContext = new MemoryContext(env, new NamingParser().
                parse(""));
        memoryContext.addToEnvironment("test env1","test env1");
        memoryContext.addToEnvironment("test env2","test env2");
        memoryContext.addToEnvironment("test env3","test env3");
        if (memoryContext.getEnvironment().size() != 3) {
            fail("The environment should contain 3 entries");
        }
        memoryContext.removeFromEnvironment("test env3");
        if (memoryContext.getEnvironment().size() != 2) {
            fail("The environment should contain 2 entries");
        }
        
        memoryContext.bind("java:comp/test/test1","test value1");
        memoryContext.bind("java:comp/test/test2","test value2");
        memoryContext.bind("java:comp/test/sub/test1","test value3");
        memoryContext.bind("java:comp/test/sub/sub2/test1","test value4");
        
        if (!(memoryContext.lookup("java:comp/test") instanceof MemoryContext)) {
            fail("Failed to lookup a sub context");
        }
        if (!memoryContext.lookup("java:comp/test/test1").equals("test value1")) {
            fail("Failed to find value");
        }
        if (!memoryContext.lookup("java:comp/test/sub/sub2/test1").
                equals("test value4")) {
            fail("Failed to find value");
        }
        
        try {
            memoryContext.bind("java:comp/test/sub/sub2/test1","test value4");
            fail("Could bind a value again.");
        } catch (NamingException ex) {
            // ignore
        }
        memoryContext.rebind("java:comp/test/sub/sub2/test1","test value5");
        
        if (!memoryContext.lookup("java:comp/test/sub/sub2/test1").
                equals("test value5")) {
            fail("Failed to find value");
        }
        
        memoryContext.rename("java:comp/test/sub/test1","java:comp/test/sub/sub3/test1");
        
        try {
            memoryContext.lookup("java:comp/test/sub/test1");
            fail("Could still find value.");
        } catch (NamingException ex) {
            // ignore
        }
        if (!memoryContext.lookup("java:comp/test/sub/sub3/test1").
                equals("test value3")) {
            fail("Failed to find value");
        }
        
        memoryContext.unbind("java:comp/test/test2");
        memoryContext.bind("java:comp/test/test2","test value6");
        
        NamingEnumeration enumer = 
                memoryContext.listBindings("java:comp/test");
        int found = 0;
        while(enumer.hasMore()) {
            Object result = enumer.next();
            if (result.equals("test value1")) {
                found++;
            } else if (result.equals("test value6")) {
                found++;
            } else if (result instanceof MemoryContext) {
                found++;
            } else {
                fail("Un-recognised entry");
            } 
        }
        if (found != 3) {
            fail("List bindings return [" + found + "] rather than 3");
        }
        
        String composedName = memoryContext.
                composeName("test/bob","java:comp/test");
        if (!composedName.equals("java:comp/test/test/bob")) {
            fail("Failed to compose name properly got [" + 
                    composedName.toString() + "]");
        }
        Name name = new NamingParser().parse("java:comp/test/test2");
        memoryContext.bind("java:comp/link/linkvalue",name);
        if (!memoryContext.lookupLink("java:comp/link/linkvalue").
                equals("test value6")) {
            fail("Failed to use the lookup link");
        }
        
    }
    
}
