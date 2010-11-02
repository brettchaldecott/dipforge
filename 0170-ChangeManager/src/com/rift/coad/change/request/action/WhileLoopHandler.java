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
 * WhileLoopHandler.java
 */


// package path
package com.rift.coad.change.request.action;

// imports
import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.logic.LogicalAlgorithm;
import com.rift.coad.change.rdf.objmapping.change.task.loop.WhileLoop;

/**
 * This object is responsible for handling the while loop.
 *
 * @author brett chaldecott
 */
public class WhileLoopHandler implements OperatorHandler {

    // private member variables
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private WhileLoop task;


    /**
     * The constructor of the while loop handler.
     *
     * @param instance The action instance.
     * @param stack The stack.
     * @param task The task.
     */
    public WhileLoopHandler(ActionInstanceImpl instance, StackEntry stack, WhileLoop task) {
        this.instance = instance;
        this.stack = stack;
        this.task = task;
    }


    /**
     * This method executes the stack.
     *
     * @return The reference to the newly created stack.
     * @throws java.lang.Exception
     */
    public StackEntry execute() throws Exception {
        if (stack.getBlock().equals(task) && (stack.getParent() != null)) {
            if (!new LogicalAlgorithm(task.getExpression(),stack).evaluate()) {
                System.out.println("The logical algorithm is false");
                return null;
            }
            System.out.println("The stack has been created");
            return instance.createStack(stack, task);
        } else {
            // setup the initial loop sub scope
            StackEntry entry = instance.createStack(stack, task);
            // override the stack current task algorithm
            entry.setCurrentTask(task);
            stack.setCurrentTask(stack.getCurrentTask().getNext());
            return entry;
        }
    }

}
