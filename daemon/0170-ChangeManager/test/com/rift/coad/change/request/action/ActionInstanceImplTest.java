/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * ActionInstanceImplTest.java
 */

package com.rift.coad.change.request.action;

import com.rift.coad.change.rdf.objmapping.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.change.ActionInfo;
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.action.ActionStack;
import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.exception.Catch;
import com.rift.coad.change.rdf.objmapping.change.task.exception.Try;
import com.rift.coad.change.rdf.objmapping.change.task.logic.CaseBlock;
import com.rift.coad.change.rdf.objmapping.change.task.logic.Else;
import com.rift.coad.change.rdf.objmapping.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.change.task.logic.If;
import com.rift.coad.change.rdf.objmapping.change.task.logic.LogicalExpression;
import com.rift.coad.change.rdf.objmapping.change.task.logic.Switch;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForEach;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForLoop;
import com.rift.coad.change.rdf.objmapping.change.task.loop.WhileLoop;
import com.rift.coad.change.rdf.objmapping.change.task.operator.OperatorLookup;
import com.rift.coad.change.request.rdf.TestInterface;
import com.rift.coad.change.request.rdf.TestTask;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.RDFArray;
import com.rift.coad.rdf.objmapping.base.VariableNameHolder;
import com.rift.coad.rdf.objmapping.base.number.RDFInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This object is responsible for testing the action instance.
 *
 * @author brett chaldecott
 */
public class ActionInstanceImplTest {


    public class SwitchResult implements TestInterface {
        private boolean result = false;
        public boolean execute(StackEntry stack) throws Exception {
            System.out.println("Execute");
            result = true;
            return true;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    
    }


    public class IfResult implements TestInterface {
        private boolean result = false;
        public boolean execute(StackEntry stack) throws Exception {
            System.out.println("Execute");
            result = true;
            return true;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

    }

    /**
     *
     */
    public class ForEachResult implements TestInterface {
        private int count = 0;

        public ForEachResult() {
        }



        public boolean execute(StackEntry stack) throws Exception {
            count++;
            return true;
        }

        public int getCount() {
            return count;
        }



    }


    /**
     *
     */
    public class ForLoopResult implements TestInterface {
        private int count = 0;

        public ForLoopResult() {
        }



        public boolean execute(StackEntry stack) throws Exception {
            count++;
            return true;
        }

        public int getCount() {
            return count;
        }



    }


    /**
     * while loop result
     */
    public class WhileLoopResult implements TestInterface {
        private int count = 0;

        public WhileLoopResult() {
        }



        public boolean execute(StackEntry stack) throws Exception {
            count++;
            System.out.println("In while loop test");
            if (count >= 10) {
                System.out.println("Count >= 10");
                RDFInteger integer = (RDFInteger)stack.getStackVariable("testValue2");
                if (integer !=null) {
                    integer.setValue(5);
                    return true;
                } else if (stack.getParent() == null) {
                    throw new java.lang.RuntimeException("The parent reference is not set");
                } else {
                    throw new java.lang.RuntimeException("The integer value is null");
                }
            }
            return true;
        }

        public int getCount() {
            return count;
        }



    }


    /**
     * This is the try test
     */
    public class TryTest implements TestInterface {

        public boolean execute(StackEntry stack) throws Exception {
            throw new Exception("Test exception");
        }
    }


    /**
     * This is the catch test
     */
    public class CatchTest implements TestInterface {
        private boolean called = false;

        public boolean execute(StackEntry stack) throws Exception {
            called = true;
            return true;
        }

        public boolean isCalled() {
            return called;
        }

        public void setCalled(boolean called) {
            this.called = called;
        }



    }

    public ActionInstanceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test the execution of the switch block
     */
    @Test
    public void testExecute_Switch() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        SwitchResult result = new SwitchResult();
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(40);
        testValue3.setDataName("testValue3");


        Switch task = new Switch("test", "test switch", testValue1, new CaseBlock[] {
            new CaseBlock("test1", "test value 1", new TestTask(new SwitchResult()), testValue2),
            new CaseBlock("test2", "test value 2", new TestTask(result), testValue1),
            new CaseBlock("test3", "test value 3", new TestTask(new SwitchResult()), testValue3)});


        StackEntry stack = new StackEntry(new DataType[] {testValue1}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        if (!result.isResult()) {
            fail("The switch statement failed.");
        }
    }


    /**
     * Test the execution of the switch block
     */
    @Test
    public void testExecute_If() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        IfResult result = new IfResult();
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(40);
        testValue3.setDataName("testValue3");


        RDFInteger testValue4 = new RDFInteger(20);
        testValue4.setDataName("testValue4");

        // if statement
        If task = new If("test1", "test if", new TestTask(result),
            new LogicalExpression(new DataType[] {testValue1,OperatorLookup.get("=="),testValue1}), null);
        task.setParameters(new DataType[] {testValue4});

        StackEntry stack = new StackEntry(new DataType[] {testValue1}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        if (!result.isResult()) {
            fail("The if statement failed.");
        }
        
        // else statement
        stack.setCurrentTask(task);
        stack.setVariables(new DataType[] {testValue2});
        IfResult elseIfResult = new IfResult();
        task.setExpression(new LogicalExpression(new DataType[] {(DataType)testValue2.clone(),OperatorLookup.get("=="),
            new VariableNameHolder(testValue3.getDataName())}));
        ElseIf elseIf = new ElseIf("elseif", "elseif", new TestTask(elseIfResult),
            new LogicalExpression(new DataType[] {(DataType)testValue2.clone(),OperatorLookup.get("=="),
            new VariableNameHolder(testValue2.getDataName())}), null);
        task.setElseBlock(elseIf);

        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        if (!elseIfResult.isResult()) {
            fail("The else if statement failed.");
        }

        // else statement
        stack.setCurrentTask(task);
        stack.setVariables(new DataType[] {testValue2});
        elseIf.setExpression(new LogicalExpression(new DataType[] {(DataType)testValue2.clone(),OperatorLookup.get("=="),
            new VariableNameHolder(testValue3.getDataName())}));
        IfResult elseResult = new IfResult();
        elseIf.setElseBlock(new Else("Else", "Else", new TestTask(elseResult)));
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        if (!elseResult.isResult()) {
            fail("The else statement failed.");
        }
    }


    /**
     * Test the execution of the switch block
     */
    @Test
    public void testExecute_ForEach() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(40);
        testValue3.setDataName("testValue3");

        RDFArray array = new RDFArray(new RDFInteger(), new DataType[] {testValue1,testValue2,testValue3});
        array.setDataName("array");
        ForEachResult result = new ForEachResult();
        ForEach task = new ForEach("ForEach", "ForEach", new TestTask(result), "array", "integer");
        StackEntry stack = new StackEntry(new DataType[] {array}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        assertEquals(3, result.getCount());
    }


    @Test
    public void testExecute_ForLoop() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(40);
        testValue3.setDataName("testValue3");


        ForLoopResult result = new ForLoopResult();
        ForLoop task = new ForLoop("ForLoop", "ForLoop", null, new TestTask(result),
            new RDFInteger(0), new RDFInteger(20), new RDFInteger(1));
        StackEntry stack = new StackEntry(new DataType[] {testValue1,testValue2,testValue3}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        assertEquals(20, result.getCount());
    }


    @Test
    public void testExecute_WhileLoop() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(30);
        testValue3.setDataName("testValue3");


        WhileLoopResult result = new WhileLoopResult();
        WhileLoop task = new WhileLoop("WhileLoop", "WhileLoop", new TestTask(result),
            new LogicalExpression(new DataType[] {testValue2,OperatorLookup.get("=="),testValue3}));

        StackEntry stack = new StackEntry(new DataType[] {testValue1,testValue2,testValue3}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        assertEquals(10, result.getCount());
    }


    @Test
    public void testExecute_Catch() throws Exception {
        System.out.println("execute");
        ActionInfo actionInfo = new ActionInfo("test","test action");
        ActionDefinition action = new ActionDefinition("test", actionInfo);

        RDFInteger testValue1 = new RDFInteger(20);
        testValue1.setDataName("testValue1");
        RDFInteger testValue2 = new RDFInteger(30);
        testValue2.setDataName("testValue2");
        RDFInteger testValue3 = new RDFInteger(30);
        testValue3.setDataName("testValue3");


        CatchTest result = new CatchTest();
        Try task = new Try("Try", "Try Block", new TestTask(new TryTest()), new Catch[] {
            new Catch("Catch", "Catch", new TestTask(new CatchTest()),
            java.lang.RuntimeException.class.getName(), "ex"),
            new Catch("Catch", "Catch", new TestTask(result),
            java.lang.Exception.class.getName(), "ex")});
        StackEntry stack = new StackEntry(new DataType[] {testValue1,testValue2,testValue3}, task);
        stack.setCurrentTask(task);
        ActionStack actionStack = new ActionStack("test", action, "test", stack);
        ActionInstanceImpl instance = new ActionInstanceImpl(actionStack);
        if (!instance.execute(stack)) {
            fail("Failed to execute");
        }
        assertEquals(true, result.isCalled());
    }
}