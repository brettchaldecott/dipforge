/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2012  Rift IT Contracting
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
 * ThreadPermissionSession.java
 *
 * This object is responsible for generating a random guid. It is a copy of a
 * class found at http://javaexchange.com/aboutRandomGUID.html. With some 
 * modifications to support Coadunation better.
 */

// common library
package com.rift.coad.lib.common;

// imports
import junit.framework.TestCase;

/**
 * The id generator test
 * @author brett chaldecott
 */
public class IDGeneratorTest extends TestCase {
    
    /**
     * The id generator
     * @param testName 
     */
    public IDGeneratorTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of init method, of class IDGenerator.
     */
    public void testInit() throws Exception {
        System.out.println("init");
        IDGenerator inistance1 = IDGenerator.init();
        IDGenerator inistance2 = IDGenerator.init();
        
        inistance1.addKey("test1");
        inistance1.addKey("test2");
        inistance1.addKey("test3");
        inistance1.addKey("test4");
        
        inistance2.addKey("test1");
        inistance2.addKey("test2");
        inistance2.addKey("test3");
        inistance2.addKey("test4");
        
        assertEquals(inistance1.id(), inistance2.id());
    }

    
}
