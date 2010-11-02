/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base.address;


// junit imports
import junit.framework.TestCase;

// coadunation imports
import com.rift.coad.rdf.objmapping.exception.ObjException;

/**
 * The test object for the zip code.
 * 
 * @author brett chaldecott
 */
public class ZipCodeTest extends TestCase {
    
    public ZipCodeTest(String testName) {
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
     * Test of getValue method, of class ZipCode.
     */
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        ZipCode instance = new ZipCode("12345");
        String expResult = "12345";
        String result = instance.getValue();
        assertEquals(expResult, result);
        
    }
    
    
    /**
     * Test of setValue method, of class ZipCode.
     */
    public void testSetValue() throws Exception {
        System.out.println("setValue");
        String value = "12345";
        ZipCode instance = new ZipCode();
        instance.setValue(value);
        
        try {
            instance.setValue("123456");
            fail("The value has been set incorrectly.");
        } catch (ObjException ex) {
            // successfull
        }
        
        try {
            instance.setValue("123A5");
            fail("The value has been set incorrectly.");
        } catch (ObjException ex) {
            // successfull
        }
        
        
        instance.setValue("12345-1234");
    }

}
