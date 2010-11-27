/*
 * ObjectTypeUtilTest.java
 * JUnit based test
 *
 * Created on November 28, 2007, 8:18 AM
 */

package com.rift.coad.web.admin.server;

import junit.framework.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import org.apache.log4j.Logger;
import com.rift.coad.web.admin.client.DaemonException;

/**
 *
 * @author brett
 */
public class ObjectTypeUtilTest extends TestCase {
    
    public ObjectTypeUtilTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of isPrimitive method, of class com.rift.coad.web.admin.server.ObjectTypeUtil.
     */
    public void testIsPrimitive() {
        System.out.println("isPrimitive");
        
        String name = "int";
        
        boolean expResult = true;
        boolean result = ObjectTypeUtil.isPrimitive(name);
        assertEquals(expResult, result);
        
        
        name = "long";
        expResult = true;
        result = ObjectTypeUtil.isPrimitive(name);
        assertEquals(expResult, result);
        
        name = "boolean";
        expResult = true;
        result = ObjectTypeUtil.isPrimitive(name);
        assertEquals(expResult, result);
        
        
        name = "float";
        expResult = true;
        result = ObjectTypeUtil.isPrimitive(name);
        assertEquals(expResult, result);
        
        name = "double";
        expResult = true;
        result = ObjectTypeUtil.isPrimitive(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrimitive method, of class com.rift.coad.web.admin.server.ObjectTypeUtil.
     */
    public void testGetPrimitive() throws Exception {
        System.out.println("getPrimitive");
        
        String name = "int";
        Class expResult = int.class;
        Class result = ObjectTypeUtil.getPrimitive(name);
        assertEquals(expResult, result);
        
        
        name = "long";
        expResult = long.class;
        result = ObjectTypeUtil.getPrimitive(name);
        assertEquals(expResult, result);
        
        name = "float";
        expResult = float.class;
        result = ObjectTypeUtil.getPrimitive(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMethod method, of class com.rift.coad.web.admin.server.ObjectTypeUtil.
     */
    public void testGetMethod() throws Exception {
        System.out.println("getMethod -- not implemented");
        
        
    }

    /**
     * Test of getValue method, of class com.rift.coad.web.admin.server.ObjectTypeUtil.
     */
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        
        Class type = String.class;
        String value = "test";
        
        Object expResult = "test";
        Object result = ObjectTypeUtil.getValue(type, value);
    }
    
}
