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
 * RandomGuidTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.MessageDigest;

/**
 *
 * @author mincemeat
 */
public class RandomGuidTest extends TestCase {
    
    public RandomGuidTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RandomGuidTest.class);
        
        return suite;
    }
    
    
    /**
     * Test of getGuid method, of class com.rift.coad.lib.common.RandomGuid.
     */
    public void testGetGuid() throws Exception {
        System.out.println("getGuid");
        
        RandomGuid generator1 = RandomGuid.getInstance();
        RandomGuid generator2 = RandomGuid.getInstance();
        
        // check that generated values from two different object instances are 
        // not the same
        String id1 = generator1.getGuid();
        String id2 = generator2.getGuid();
        
        if (id1.equals(id2)) {
            fail("The ids are the same");
        }
        
        // check that the object instance returns the same values.
        if (!id1.equals(generator1.toString())) {
            fail("The generated ids from the same object instance are not the same");
        }
        
        if (!id2.equals(generator2.toString())) {
            fail("The generated ids from the same object instance are not the same");
        }
        
        // print ids
        System.out.println("ID 1 [" + id1 + "] ID 2 [" + id2 + "]");
    }

    
}
