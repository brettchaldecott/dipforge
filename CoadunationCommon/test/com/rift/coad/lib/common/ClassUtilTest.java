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
 * ClassUtilTest.java
 *
 * JUnit based test
 */

package com.rift.coad.lib.common;

import com.rift.coad.lib.common.*;
import junit.framework.*;

/**
 *
 * @author mincemeat
 */
public class ClassUtilTest extends TestCase {
    
    public interface ParentClass extends java.rmi.Remote {
        
    }
    
    
    public class SubClass implements ParentClass, java.io.Serializable {
        
    }
    
    public ClassUtilTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(ClassUtilTest.class);
        
        return suite;
    }

    /**
     * Test of testForParent method, of class com.rift.coad.lib.common.ClassUtil.
     */
    public void testTestForParent() {
        System.out.println("testForParent");
        
        if (false == ClassUtil.testForParent(SubClass.class, "java.rmi.Remote")) {
            fail("Failed to find java.rmi.Remote");
        }
        
        if (true == ClassUtil.testForParent(SubClass.class, "java.rmi.bob")) {
            fail("Found java.rmi.bob");
        }
        
        if (false == ClassUtil.testForParent(SubClass.class, "java.lang.Object")) {
            fail("Did not find java.lang.Object");
        }
    }
    
}
