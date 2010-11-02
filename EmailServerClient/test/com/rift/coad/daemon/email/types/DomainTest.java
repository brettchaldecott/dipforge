/*
 * DomainTest.java
 * JUnit based test
 *
 * Created on March 3, 2008, 6:49 AM
 */

package com.rift.coad.daemon.email.types;

import junit.framework.*;
import com.rift.coad.daemon.email.EmailException;

/**
 *
 * @author brett
 */
public class DomainTest extends TestCase {
    
    public DomainTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of validate method, of class com.rift.coad.daemon.email.types.Domain.
     */
    public void testValidate() throws Exception {
        System.out.println("validate");
        
        String value = "www.domain.com";
        
        Domain.validate(value);
        
        try {
            Domain.validate("xx__invalid.com");
            fail("The domain was validated as valid");
        } catch (Exception ex) {
            // ignore
        }
        
    }

    /**
     * Test of getValue method, of class com.rift.coad.daemon.email.types.Domain.
     */
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        
        Domain instance = new Domain("www.domain.com");
        
        String expResult = "www.domain.com";
        String result = instance.getValue();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setValue method, of class com.rift.coad.daemon.email.types.Domain.
     */
    public void testSetValue() throws Exception {
        System.out.println("setValue");
        
        String value = "www.bob.com";
        Domain instance = new Domain("www.news.com");
        assertEquals("www.news.com", instance.getValue());
        instance.setValue(value);
        assertEquals(value, instance.getValue());
        
    }

    /**
     * Test of toString method, of class com.rift.coad.daemon.email.types.Domain.
     */
    public void testToString() throws Exception {
        System.out.println("toString");
        
        Domain instance = new Domain("www.bob.com");
        
        String expResult = "www.bob.com";
        String result = instance.toString();
        assertEquals(expResult, result);
        
    }
    
}
