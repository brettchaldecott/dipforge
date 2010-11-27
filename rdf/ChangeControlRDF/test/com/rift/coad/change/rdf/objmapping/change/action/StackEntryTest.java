/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change.rdf.objmapping.change.action;

import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.number.RDFLong;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class StackEntryTest extends TestCase {
    
    public StackEntryTest(String testName) {
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
     * Test of getStackVariable method, of class StackEntry.
     */
    public void testGetStackVariable() {
        System.out.println("getStackVariable");
        String name = "test1";
        StackEntry parent = new StackEntry();
        RDFLong lvalue1 = new RDFLong(new Long(10));
        lvalue1.setDataName("test1");
        parent.setVariables(new DataType[]{lvalue1});
        StackEntry child1 = new StackEntry();
        RDFLong lvalue2 = new RDFLong(new Long(20));
        lvalue2.setDataName("test2");
        child1.setVariables(new DataType[]{lvalue2});
        child1.setParent(parent);
        StackEntry grandChild = new StackEntry();
        RDFLong lvalue3 = new RDFLong(new Long(30));
        lvalue3.setDataName("test3");
        grandChild.setVariables(new DataType[]{lvalue3});
        grandChild.setParent(child1);

        DataType expResult = lvalue1;
        DataType result = grandChild.getStackVariable(name);
        assertEquals(expResult, result);
    }


    public void testReplaceStackVariable() {
        System.out.println("testReplaceStackVariable");
        String name = "test1";
        StackEntry parent = new StackEntry();
        RDFLong lvalue1 = new RDFLong(new Long(10));
        lvalue1.setDataName("test1");
        parent.setVariables(new DataType[]{lvalue1});
        StackEntry child1 = new StackEntry();
        RDFLong lvalue2 = new RDFLong(new Long(20));
        lvalue2.setDataName("test2");
        child1.setVariables(new DataType[]{lvalue2});
        child1.setParent(parent);
        StackEntry grandChild = new StackEntry();
        RDFLong lvalue3 = new RDFLong(new Long(30));
        lvalue3.setDataName("test3");
        grandChild.setVariables(new DataType[]{lvalue3});
        grandChild.setParent(child1);


        DataType expResult = lvalue1;
        assertEquals(grandChild.replaceStackVariable("test2",lvalue1),true);
        DataType replacedValue = grandChild.getStackVariable("test2");
        assertEquals(lvalue1.getValue(), ((RDFLong)replacedValue).getValue());
    }
}
