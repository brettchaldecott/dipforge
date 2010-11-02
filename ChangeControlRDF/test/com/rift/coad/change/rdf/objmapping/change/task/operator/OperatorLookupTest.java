/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change.rdf.objmapping.change.task.operator;

import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class OperatorLookupTest extends TestCase {
    
    public OperatorLookupTest(String testName) {
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
     * Test of get method, of class OperatorLookup.
     */
    public void testGet() throws Exception {
        System.out.println("get");
        String name = "&&";
        Operator expResult = new Operator("&&","AND");
        Operator result = OperatorLookup.get(name);
        if (!expResult.equals(result)) {
            fail("The && operator was not found");
        }
        result = OperatorLookup.get("AND");
        if (!expResult.equals(result)) {
            fail("The && operator was not found");
        }
    }

}
