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
 * ResourceReaderTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;
import java.lang.ClassLoader;
import java.io.*;
import java.net.URL;

/**
 *
 * @author mincemeat
 */
public class ResourceReaderTest extends TestCase {
    
    public ResourceReaderTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ResourceReaderTest.class);
        
        return suite;
    }

    /**
     * Test of getPath method, of class com.rift.coad.lib.common.ResourceReader.
     */
    public void testGetPath() throws Exception {
        System.out.println("getPath");
        
        ResourceReader instance = new ResourceReader(
                "com/rift/coad/lib/common/coadunation-test.xml");
        
        String expResult = "com/rift/coad/lib/common/coadunation-test.xml";
        String result = instance.getPath();
        assertEquals(expResult, result);
        
        
    }
    
    
    /**
     * Test of getPath method, of class com.rift.coad.lib.common.ResourceReader.
     */
    public void testReader() throws Exception {
        System.out.println("testReader");
        
        ResourceReader instance = new ResourceReader(
                "com/rift/coad/lib/common/coadunation-test.xml");
        
        String result = instance.getDocument();
        if (!result.contains("\n")) {
            fail("No ending lines were found");
        }
    }
}
