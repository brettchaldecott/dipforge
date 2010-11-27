/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * AssignHandler.java
 */

package com.rift.coad.change.request.action;

import com.rift.coad.change.rdf.objmapping.change.action.StackEntry;
import com.rift.coad.change.rdf.objmapping.change.task.Assign;
import com.rift.coad.rdf.objmapping.base.DataType;

/**
 * This object handles the assignment operation.
 *
 * @author brett chaldecott
 */
public class AssignHandler implements OperatorHandler {

    // private member variables
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private Assign task;


    /**
     * This constructor sets up the operator information.
     *
     * @param instance The instance to assign.
     * @param stack The stack information.
     * @param task The task.
     */
    public AssignHandler(ActionInstanceImpl instance, StackEntry stack, Assign task) {
        this.instance = instance;
        this.stack = stack;
        this.task = task;
    }



    /**
     * The execute method.
     *
     * @return The reference to the stack.
     * @throws java.lang.Exception
     */
    public StackEntry execute() throws Exception {
        DataType target = stack.getStackVariable(task.getSource());
        stack.replaceStackVariable(task.getTarget(), target);
        return stack;
    }

}
