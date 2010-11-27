/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base.serial;

import junit.framework.TestCase;

/**
 *
 * @author Brett
 */
public class ISBNTest extends TestCase {
    
    public ISBNTest(String testName) {
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
     * Test of getValue method, of class ISBN.
     */
    public void testGetValue() throws Exception{
        System.out.println("getValue");
        ISBN instance = new ISBN("1234567890");
        String expResult = "1234567890";
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of setValue method, of class ISBN.
     */
    public void testSetValue() throws Exception {
        System.out.println("setValue");
        String value = "1234567890123";
        ISBN instance = new ISBN();
        instance.setValue(value);
        assertEquals(value, instance.getValue());
        try {
            instance.setValue("123456789");
            fail("The value is incorrectly set.");
        } catch (Exception ex) {

        }
        try {
            instance.setValue("123456789012");
            fail("The value is incorrectly set.");
        } catch (Exception ex) {

        }
    }

}
