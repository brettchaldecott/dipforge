/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.util;

import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brett
 */
public class ClassTypeInfoTest {

    public ClassTypeInfoTest() {
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
     * Test of isBasicType method, of class ClassTypeInfo.
     */
    @Test
    public void testIsBasicType() {
        System.out.println("isBasicType");
        Class c = Long.class;
        boolean expResult = true;
        boolean result = ClassTypeInfo.isBasicType(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCollection method, of class ClassTypeInfo.
     */
    @Test
    public void testIsCollection() {
        System.out.println("isCollection");
        Class c = List.class;
        boolean expResult = true;
        boolean result = ClassTypeInfo.isCollection(c);
        assertEquals(expResult, result);
        c = Collection.class;
        result = ClassTypeInfo.isCollection(c);
        assertEquals(expResult, result);

        c = String.class;
        result = ClassTypeInfo.isCollection(c);
        expResult = false;
        assertEquals(expResult, result);
    }

}