/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * MethodUtilsTest.java
 */

package com.rift.coad.rdf.semantic.util;

import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This object tests the methods.
 *
 * @author brett chaldecott
 */
public class MethodUtilsTest {

    public class TestObject {

        public String getName() {
            return null;
        }

    }

    public MethodUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isExcludableMethod method, of class MethodUtils.
     */
    @Test
    public void testIsExcludableMethod() throws Exception {
        System.out.println("isExcludableMethod");
        Method checkMethod = TestObject.class.getMethod("getName");
        boolean expResult = false;
        boolean result = MethodUtils.isExcludableMethod(checkMethod);
        assertEquals(expResult, result);

        checkMethod = TestObject.class.getMethod("wait");
        expResult = true;
        result = MethodUtils.isExcludableMethod(checkMethod);
        assertEquals(expResult, result);
    }

}