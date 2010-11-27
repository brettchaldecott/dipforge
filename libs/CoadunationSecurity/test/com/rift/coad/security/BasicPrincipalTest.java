/*
 * BasicPrincipalTest.java
 * JUnit based test
 *
 * Created on June 20, 2007, 6:45 AM
 */

package com.rift.coad.security;

import junit.framework.*;
import java.security.Principal;
import java.io.Serializable;

/**
 *
 * @author brett
 */
public class BasicPrincipalTest extends TestCase {
    
    public BasicPrincipalTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of getName method, of class com.rift.coad.security.BasicPrincipal.
     */
    public void testGetName() {
        System.out.println("getName");
        
        BasicPrincipal instance = new BasicPrincipal("test");
        
        String expResult = "test";
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of equals method, of class com.rift.coad.security.BasicPrincipal.
     */
    public void testEquals() {
        System.out.println("equals");
        
        Object value = new BasicPrincipal("test");
        BasicPrincipal instance = new BasicPrincipal("test");
        
        boolean expResult = true;
        boolean result = instance.equals(value);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of hashCode method, of class com.rift.coad.security.BasicPrincipal.
     */
    public void testHashCode() {
        System.out.println("hashCode");
        
        BasicPrincipal instance = new BasicPrincipal(null);
        
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        
        instance = new BasicPrincipal("test");
        assertEquals(instance.hashCode(), instance.hashCode());
        
    }

    /**
     * Test of toString method, of class com.rift.coad.security.BasicPrincipal.
     */
    public void testToString() {
        System.out.println("toString");
        
        BasicPrincipal instance = new BasicPrincipal("test");
        
        String expResult = "test";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }
    
}
