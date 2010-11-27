/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * MethodAnnotationHelperTest.java
 */

package com.rift.coad.rdf.semantic.annotation;

import com.rift.coad.rdf.semantic.annotation.test.TestClass;
import java.lang.reflect.Method;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class MethodAnnotationHelperTest extends TestCase {
    
    public MethodAnnotationHelperTest(String testName) {
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
     * Test of getMethodForAnnotation method, of class MethodAnnotationHelper.
     */
    public void testGetMethodForAnnotation() {
        System.out.println("getMethodForAnnotation");
        Object objectType = new TestClass();
        Class annotation = thewebsemantic.ObjTypeId.class;
        Method result = MethodAnnotationHelper.getMethodForAnnotation(objectType, annotation);
        assertEquals("getObjectTypeId", result.getName());
    }

}
