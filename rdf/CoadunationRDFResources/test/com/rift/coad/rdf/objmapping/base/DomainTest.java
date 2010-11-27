/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.objmapping.base;

import com.rift.coad.rdf.objmapping.base.Domain;
import junit.framework.TestCase;

/**
 *
 * @author Brett
 */
public class DomainTest extends TestCase {
    
    public DomainTest(String testName) {
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
     * Test of getValue method, of class Domain.
     */
    public void testGetValue() throws Exception {
        System.out.println("getValue");
        Domain instance = new Domain("bob.com");
        String expResult = "bob.com";
        String result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of setValue method, of class Domain.
     */
    public void testSetValue() throws Exception {
        System.out.println("setValue");
        String value = "bob-bob1.com";
        Domain instance = new Domain();
        instance.setValue(value);
        assertEquals(instance.getValue(), value);

        try{
            instance.setValue("1_bob .com");
            fail("The domain value is invalid");
        } catch (Exception ex) {
            // ignore
        }
    }

}
