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

import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.Block;
import com.rift.coad.change.rdf.objmapping.change.task.logic.Else;
import com.rift.coad.change.rdf.objmapping.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.change.task.logic.If;
import com.rift.coad.change.rdf.objmapping.change.task.logic.LogicalAlgorithm;



/**
 * This object is responsible for handling the if blocks
 *
 * @author brett chaldecott
 */
public class IfHandler implements OperatorHandler {

    // private member variables
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private If task;


    /**
     * This constructor sets up all internal values.
     *
     * @param instance The instance that is executing the flow.
     * @param stack The current stack entry.
     * @param task The current task.
     */
    public IfHandler(ActionInstanceImpl instance, StackEntry stack, If task) {
        this.instance = instance;
        this.stack = stack;
        this.task = task;
    }


    /**
     * This method returns the instance that is currenly being executed.
     *
     * @return The reference to the currently executing instance.
     */
    public ActionInstanceImpl getInstance() {
        return instance;
    }


    /**
     * This method sets the instance reference.
     *
     * @param instance The reference to the instance.
     */
    public void setInstance(ActionInstanceImpl instance) {
        this.instance = instance;
    }


    /**
     * The reference to the current stack information.
     *
     * @return This method returns the stack reference.
     */
    public StackEntry getStack() {
        return stack;
    }


    /**
     * This method sets the stack information.
     *
     * @param stack The stack information.
     */
    public void setStack(StackEntry stack) {
        this.stack = stack;
    }


    /**
     * This method returns the task information.
     *
     * @return The  task that this object is handling.
     */
    public ActionTaskDefinition getTask() {
        return task;
    }


    /**
     * This method sets the task that is to be handled by this object.
     *
     * @param task The reference to the task to manage.
     */
    public void setTask(If task) {
        this.task = task;
    }

    
    /**
     * This method execute the if statements
     * 
     * @return TRUE if execution is complete, FALSE if not.
     * @throws java.lang.Exception
     */
    public StackEntry execute() throws Exception {
        // set the current task
        Block block = getBlock();
        stack.setCurrentTask(task.getNext());
        if (block == null) {
            return null;
        }

        return instance.createStack(stack, block);
    }


    /**
     * Get the block to execute
     *
     * @param currentTask The current task.
     * @return
     */
    private Block getBlock() throws Exception {
        if (new LogicalAlgorithm(task.getExpression(),stack).evaluate()) {
            return task;
        }

        // this method will
        return getBlock(task.getElseBlock());
    }


    /**
     * This method returns the block information.
     *
     * @param elseBlock The else block.
     * @return The return statement.
     * @throws Exception
     */
    private Block getBlock(Else elseBlock) throws Exception {
        if (!(elseBlock instanceof ElseIf)) {
            return elseBlock;
        }
        ElseIf elseIf = (ElseIf)elseBlock;
        if (new LogicalAlgorithm(elseIf.getExpression(),stack).evaluate()) {
            return elseIf;
        }
        if (elseIf.getElseBlock() != null) {
            return getBlock(elseIf.getElseBlock());
        }
        return null;
    }


}
