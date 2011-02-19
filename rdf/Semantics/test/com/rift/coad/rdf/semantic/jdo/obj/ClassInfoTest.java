/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.obj;

import com.rift.coad.rdf.semantic.jdo.obj.test.TestObject;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author brettc
 */
public class ClassInfoTest extends TestCase {
    
    public ClassInfoTest(String testName) {
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
     * Test of getClassRef method, of class ClassInfo.
     */
    public void testGetClassRef() throws Exception {
        System.out.println("getClassRef");
        Class classRef = TestObject.class;
        Class expResult = TestObject.class;
        ClassInfo result = ClassInfo.interrogateClass(classRef);
        assertEquals(expResult, result.getClassRef());
    }

    /**
     * Test of getLocalName method, of class ClassInfo.
     */
    public void testGetLocalName() throws Exception {
        System.out.println("getLocalName");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        String expResult = "TestClass";
        String result = instance.getLocalName();
        assertEquals(expResult, result);
    }


    /**
     * Test of getMethods method, of class ClassInfo.
     */
    public void testGetMethods() throws Exception {
        System.out.println("getMethods");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        List result = instance.getMethods();
        assertEquals(4, result.size());

    }

    /**
     * Test of getNamespace method, of class ClassInfo.
     */
    public void testGetNamespace() throws Exception {
        System.out.println("getNamespace");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        String expResult = "http://www.coadunation.net/schema/rdf/1.0/test";
        String result = instance.getNamespace();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGetters method, of class ClassInfo.
     */
    public void testGetGetters() throws Exception {
        System.out.println("getGetters");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        List result = instance.getGetters();
        assertEquals(2, result.size());
    }

    /**
     * Test of getOperators method, of class ClassInfo.
     */
    public void testGetOperators() throws Exception {
        System.out.println("getOperators");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        List result = instance.getOperators();
        assertEquals(1, result.size());
    }

    /**
     * Test of getSetters method, of class ClassInfo.
     */
    public void testGetSetters() throws Exception {
        System.out.println("getSetters");
        ClassInfo instance = ClassInfo.interrogateClass(TestObject.class);
        List result = instance.getSetters();
        assertEquals(1, result.size());
    }

}
