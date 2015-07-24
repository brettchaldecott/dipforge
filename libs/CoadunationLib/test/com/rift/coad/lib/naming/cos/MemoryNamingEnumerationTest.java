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
 * MemoryNamingEnumerationTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.naming.cos;

import junit.framework.*;
import java.util.Enumeration;
import javax.naming.NamingEnumeration;

/**
 *
 * @author mincemeat
 */
public class MemoryNamingEnumerationTest extends TestCase {
    
    public MemoryNamingEnumerationTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(MemoryNamingEnumerationTest.class);
        
        return suite;
    }

    /**
     * Test of close MemoryNamingEnumeration
     */
    public void testMemoryNamingEnumeration() throws Exception {
        System.out.println("MemoryNamingEnumeration");
        
        MemoryNamingEnumeration instance = new MemoryNamingEnumeration(
                new NamingParser().parse("java:comp/test/freddy/bob").getAll());
        
        int index = 0;
        while (instance.hasMore()) {
            Object result = instance.next();
            if ((index == 0) && (!result.equals("java:comp"))) {
                fail("java:comp not found.");
            } else if ((index == 1) && (!result.equals("test"))) {
                fail("test not found.");
            } else if ((index == 2) && (!result.equals("freddy"))) {
                fail("freddy not found.");
            } else if ((index == 3) && (!result.equals("bob"))) {
                fail("bob not found.");
            }
            index++;
        }
        
    }

    
}
