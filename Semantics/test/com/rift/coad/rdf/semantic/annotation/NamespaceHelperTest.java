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
 * AnnotationHelperTest.java
 */

package com.rift.coad.rdf.semantic.annotation;

import com.rift.coad.rdf.semantic.annotation.test.TestClass;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class NamespaceHelperTest extends TestCase {
    
    public NamespaceHelperTest(String testName) {
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
     * Test of getNamespace method, of class NamespaceHelper.
     */
    public void testGetNamespace() throws Exception {
        System.out.println("getNamespace");
        NamespaceHelper instance = new NamespaceHelper(TestClass.class);
        String expResult = "http://www.coadunation.net/schema/rdf/1.0/test#";
        String result = instance.getNamespace();
        assertEquals(expResult, result);
    }

}
