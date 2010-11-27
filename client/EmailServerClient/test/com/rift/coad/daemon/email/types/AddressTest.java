/*
 * AddressTest.java
 * JUnit based test
 *
 * Created on February 13, 2008, 8:22 AM
 */

package com.rift.coad.daemon.email.types;

import junit.framework.*;
import com.rift.coad.daemon.email.EmailException;

/**
 *
 * @author brett
 */
public class AddressTest extends TestCase {
    
    public AddressTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    /**
     * Test the validation
     */
    public void testValidation() throws Exception {
        
        new Address("bob@bob.com");
        try {
            new Address("bob@___bob.com");
            fail("invalid characters");
        } catch (Exception ex) {
            // ignore
        }
        new Address("###8988__ bob@bob.com");
        
        
        new Address().setAddress("bob@bob.com");
        try {
            new Address().setAddress("bob@___bob.com");
            fail("invalid characters");
        } catch (Exception ex) {
            // ignore
        }
        new Address().setAddress("###8988__ bob@bob.com");
        
    }
    
    /**
     * Test of getAddres method, of class com.rift.coad.daemon.email.smtp.Address.
     */
    public void testGetAddress() throws Exception {
        System.out.println("getAddress");
        
        Address instance = new Address("bob@bob.com");
        
        String expResult = "bob@bob.com";
        String result = instance.getAddress();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of setAddress method, of class com.rift.coad.daemon.email.smtp.Address.
     */
    public void testSetAddress() throws Exception {
        System.out.println("setAddress");
        
        String value = "bob@bob.com";
        Address instance = new Address();
        
        instance.setAddress(value);
        
        String result = instance.getAddress();
        assertEquals(value, result);
        
        
    }

    /**
     * Test of getLocalPart method, of class com.rift.coad.daemon.email.smtp.Address.
     */
    public void testGetLocalPart() throws Exception{
        System.out.println("getLocalPart");
        
        Address instance = new Address("bob@bill.com");
        
        String expResult = "bob";
        String result = instance.getLocalPart();
        assertEquals(expResult, result);
        
        instance = new Address();
        expResult = null;
        result = instance.getLocalPart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDomain method, of class com.rift.coad.daemon.email.smtp.Address.
     */
    public void testGetDomain() throws Exception {
        System.out.println("getDomain");
        
        Address instance = new Address("Bob <bob@bill.com>");
        
        String expResult = "bill.com";
        String result = instance.getDomain();
        assertEquals(expResult, result);
        
        instance = new Address();
        
        expResult = null;
        result = instance.getDomain();
        assertEquals(expResult, result);
        
    }
    
}
