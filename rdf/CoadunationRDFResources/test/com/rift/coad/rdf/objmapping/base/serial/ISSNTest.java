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
public class ISSNTest extends TestCase {
    
    public ISSNTest(String testName) {
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
     * Test of getValue method, of class ISSN.
     */
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        ISSN instance = new ISSN("1234-1234");
        String expResult = "1234-1234";
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of setValue method, of class ISSN.
     */
    public void testSetValue() throws Exception {
        System.out.println("setValue");
        String value = "1234-1234";
        ISSN instance = new ISSN();
        instance.setValue(value);
        String result = instance.getValue();
        assertEquals(value, result);
        try {
            instance.setValue("123-123");
            fail("Value was set incorrectly");
        } catch (Exception ex) {

        }
    }

}
