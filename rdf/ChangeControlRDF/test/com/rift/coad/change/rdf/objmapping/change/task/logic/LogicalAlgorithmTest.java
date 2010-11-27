/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.change.rdf.objmapping.change.task.logic;

import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.operator.OperatorLookup;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.VariableNameHolder;
import com.rift.coad.rdf.objmapping.base.number.RDFLong;
import junit.framework.TestCase;

/**
 *
 * @author brett
 */
public class LogicalAlgorithmTest extends TestCase {
    
    public LogicalAlgorithmTest(String testName) {
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
     * Test of evaluate method, of class LogicalAlgorithm.
     */
    public void testEvaluate() throws Exception {
        System.out.println("evaluate");
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

        LogicalAlgorithm instance = new LogicalAlgorithm(
                new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get("=="),new VariableNameHolder(lvalue1.getDataName())}),grandChild);
        boolean expResult = true;
        boolean result = instance.evaluate();
        assertEquals(expResult, result);


        instance = new LogicalAlgorithm(
                new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get("!="),(DataType)lvalue1.clone()}),grandChild);
        expResult = false;
        result = instance.evaluate();
        assertEquals(expResult, result);


        instance = new LogicalAlgorithm(
                new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get("=="),new VariableNameHolder(lvalue1.getDataName()),
                OperatorLookup.get("&&"),
                (DataType)lvalue1.clone(),OperatorLookup.get("!="),new VariableNameHolder(lvalue2.getDataName())}),grandChild);
        expResult = true;
        result = instance.evaluate();
        assertEquals(expResult, result);

        instance = new LogicalAlgorithm(
                new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get("=="),new VariableNameHolder(lvalue1.getDataName()),
                OperatorLookup.get("&&"),
                (DataType)lvalue1.clone(),OperatorLookup.get("!="),new VariableNameHolder(lvalue2.getDataName()),
                OperatorLookup.get("&&"),
        new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get("<"),(DataType)lvalue2.clone()}),
                OperatorLookup.get("||"),
        new LogicalExpression(new DataType[] {
                (DataType)lvalue1.clone(),OperatorLookup.get(">"),(DataType)lvalue2.clone()})}),grandChild);
        expResult = true;
        result = instance.evaluate();
        assertEquals(expResult, result);
    }

}
