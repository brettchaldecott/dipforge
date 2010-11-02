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
 * ForHandler.java
 */

// package path
package com.rift.coad.change.request.action;

import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForLoop;
import com.rift.coad.rdf.objmapping.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.RDFNumber;
import com.rift.coad.rdf.objmapping.base.number.RDFInteger;
import com.rift.coad.rdf.objmapping.base.number.RDFLong;


/**
 * This object is handle the for operator.
 *
 * @author brett chaldecott
 */
public class ForHandler implements OperatorHandler {

    // private member variables
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private ForLoop task;


    /**
     * This constructor is called to setup the for handler.
     *
     * @param instance 
     * @param stack
     * @param task
     */
    public ForHandler(ActionInstanceImpl instance, StackEntry stack, ForLoop task) {
        this.instance = instance;
        this.stack = stack;
        this.task = task;
    }


    /**
     * This method executes the operator on the stack.
     *
     * @return The stack that has been modified during the execution of this call.
     * @throws java.lang.Exception
     */
    public StackEntry execute() throws Exception {
        if (stack.getBlock().equals(task) && (stack.getParent() != null)) {

            // validate
            DataType[] stackVars = stack.getVariables();
            RDFNumber index = (RDFNumber)stackVars[0];
            RDFNumber increment = (RDFNumber)stackVars[1];
            RDFNumber end = (RDFNumber)stackVars[2];
            if (!ComparisonOperators.class.cast(index).lessThan(end)) {
                return null;
            }
            if ((index instanceof RDFInteger) && (increment instanceof RDFInteger)) {
                RDFInteger integerIndex = (RDFInteger)index;
                RDFInteger integerIncrement = (RDFInteger)increment;
                integerIndex.setValue(integerIndex.getValue() + integerIncrement.getValue());
            } else if ((index instanceof RDFLong) && (increment instanceof RDFLong)) {
                RDFLong longIndex = (RDFLong)index;
                RDFLong longIncrement = (RDFLong)increment;
                longIndex.setValue(longIndex.getValue() + longIncrement.getValue());
            } else {
                throw new ActionException("Cannot for loop on these data types must either be integer or long.");
            }
            return instance.createStack(stack, task);
        } else {
            // setup the initial loop sub scope
            StackEntry entry = createStack(stack, task);
            // override the stack current task algorithm
            entry.setCurrentTask(task);
            stack.setCurrentTask(stack.getCurrentTask().getNext());
            return entry;
        }
    }


    /**
     * This method creates a new stack entry.
     *
     * @param parent The current stack value.
     * @param task The task that
     * @return The newly created stack value.
     * @throws java.lang.Exception
     */
    private StackEntry createStack(StackEntry parent, ForLoop task) throws Exception {
        instance.addStackEvent(task, ActionInstanceImpl.ACTION_STATUS.ACTION_RUNNING);
        StackEntry child = new StackEntry(new DataType[]{}, task, parent);
        parent.setChild(child);
        DataType[] variables = new DataType[3];
        variables[0] = (DataType)task.getIndex().clone();
        variables[1] = (DataType)task.getIncrement().clone();
        variables[2] = (DataType)task.getEnd().clone();
        child.setVariables(variables);
        return child;

    }
}
