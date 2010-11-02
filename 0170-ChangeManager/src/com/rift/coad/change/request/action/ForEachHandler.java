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
 * SwitchHandler.java
 */

// package path
package com.rift.coad.change.request.action;

import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.loop.ForEach;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.RDFArray;
import com.rift.coad.rdf.objmapping.base.number.RDFInteger;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;

/**
 * This object handles the logic processing for a for each block.
 *
 * @author brett chaldecott
 */
public class ForEachHandler implements OperatorHandler {
    private final static String FOR_EACH_COUNT = "FORE_EACH_COUNTER";

    // private member variables
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private ForEach task;


    /**
     * This constructor sets all internal values.
     *
     * @param instance The current instance.
     * @param stack The stack entry.
     * @param task The task.
     */
    public ForEachHandler(ActionInstanceImpl instance, StackEntry stack, ForEach task) {
        this.instance = instance;
        this.stack = stack;
        this.task = task;
    }

    
    /**
     * This method is called to execute the for each handler.
     * 
     * @return TRUE if execution is complete.
     * @throws java.lang.Exception
     */
    public StackEntry execute() throws Exception {
        ForEach forEach = (ForEach)stack.getCurrentTask();
        if (stack.getBlock().equals(stack.getCurrentTask()) && (stack.getParent() != null)) {
            
            // validate
            DataType[] stackVars = stack.getVariables();
            RDFInteger count = (RDFInteger)stackVars[0];
            RDFArray array = (RDFArray)stackVars[1];
            if (!(count.getValue() < array.getEntries().length)) {
                return null;
            }
            DataType value = (DataType)array.getEntries()[count.getValue()].clone();
            value.setDataName(forEach.getInstance());
            count.setValue(count.getValue() + 1);

            // create the stack
            return createStack(stack,forEach,value);
        } else {
            // setup the initial loop sub scope
            StackEntry entry = createStack(stack, forEach);
            // override the stack current task algorithm
            entry.setCurrentTask(forEach);
            stack.setCurrentTask(stack.getCurrentTask().getNext());
            return entry;
        }
    }


    /**
     * This method creates the stack necessary for the for each block.
     *
     * @param parent The parent task.
     * @param task The for each task.
     * @return The newly created stack.
     * @throws java.lang.Exception
     */
    protected StackEntry createStack(StackEntry parent, ForEach task) throws Exception {
        instance.addStackEvent(task, ActionInstanceImpl.ACTION_STATUS.ACTION_RUNNING);
        StackEntry child = new StackEntry(new DataType[]{}, task, parent);
        parent.setChild(child);
        DataType[] forEachVariables = new DataType[2];
        RDFInteger integer = new RDFInteger(0);
        integer.setDataName(FOR_EACH_COUNT);
        forEachVariables[0] = integer;
        if (task.getVarName() == null) {
            throw new ActionException("There is no variable name set for this for each loop.");
        }
        String[] vars = task.getVarName().split("\\.");
        if (vars.length == 0) {
            throw new ActionException("There is no variable name set for this for each loop.");
        }
        DataType type = parent.getStackVariable(vars[0]);
        for (int index = 1; index < vars.length; index++) {
            String varName = vars[index];
            if (!(type instanceof ResourceBase)) {
                throw new ActionException("Attempting to retrieve resource[" +
                        varName + "] from none resource [" + type.getClass().getName() + "]");
            }
            type = ResourceBase.class.cast(type).getAttribute(DataType.class, varName);
            if (type == null) {
                throw new ActionException("The variable [" +
                        varName + "] does not exist");
            }
        }
        if (!(type instanceof RDFArray)) {
            throw new ActionException("For Each cannot be perform on : " + type.getClass().getName());
        }
        forEachVariables[1] = type;
        child.setVariables(forEachVariables);
        return child;
    }


    /**
     * This method is called to create a new stack entry
     *
     * @param parent This method sets the parent link
     * @param task The task reference.
     * @param value The value from the parent.
     * @return The return stack.
     * @throws java.lang.Exception
     */
    private StackEntry createStack(StackEntry parent, ForEach task, DataType value) throws Exception {
        instance.addStackEvent(task, ActionInstanceImpl.ACTION_STATUS.ACTION_RUNNING);
        StackEntry child = new StackEntry(new DataType[]{}, task, parent);
        parent.setChild(child);
        DataType[] data = null;
        if (task.getParameters() != null) {
            data = new DataType[task.getParameters().length + 1];
            for (int index = 0; index < task.getParameters().length; index++) {
                System.out.println("Clone an object");
                data[index] = (DataType)task.getParameters()[index].clone();
            }
            data[task.getParameters().length] = value;
        } else {
            data = new DataType[]{value};
        }
        
        child.setVariables(data);
        return child;
    }
}
