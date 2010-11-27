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
import com.rift.coad.change.rdf.objmapping.change.task.logic.CaseBlock;
import com.rift.coad.change.rdf.objmapping.change.task.logic.Switch;
import com.rift.coad.rdf.objmapping.base.DataType;

/**
 * The switch handler.
 *
 * @author brett chaldecott
 */
public class SwitchHandler implements OperatorHandler {
    private ActionInstanceImpl instance;
    private StackEntry stack;
    private Switch switchObj;

    /**
     * The constructor that sets up all the information.
     *
     * @param instance The instance that references the switch statement.
     * @param stack The stack value.
     * @param sitch The switch statement.
     */
    public SwitchHandler(ActionInstanceImpl instance, StackEntry stack, Switch switchObj) {
        this.instance = instance;
        this.stack = stack;
        this.switchObj = switchObj;
    }


    /**
     * This method returns the instance information.
     *
     * @return The reference to the action instance.
     */
    public ActionInstanceImpl getInstance() {
        return instance;
    }


    /**
     * This method sets the instance object.
     *
     * @param instance The reference to the instance.
     */
    public void setInstance(ActionInstanceImpl instance) {
        this.instance = instance;
    }


    /**
     * This method retrieves the switch information.
     *
     * @return The reference to the switch statement.
     */
    public Switch getSwitchObj() {
        return switchObj;
    }


    /**
     * The reference to the switch object.
     *
     * @param switchObj The object that contains the switch information.
     */
    public void setSwitchObj(Switch switchObj) {
        this.switchObj = switchObj;
    }


    /**
     * This method retrieves the stack information.
     *
     * @return The stack information.
     */
    public StackEntry getStack() {
        return stack;
    }


    /**
     * This method sets the stack information.
     *
     * @param stack The reference to the stack information.
     */
    public void setStack(StackEntry stack) {
        this.stack = stack;
    }


    /**
     * This method is called to execute the switch object
     */
    public StackEntry execute() throws Exception {
        DataType value = 
                stack.getStackVariable(switchObj.getSwitchData().getDataName());
        if (value == null) {
            throw new ActionException("The data [" +
                    switchObj.getSwitchData().getDataName() + "] does not exist.");
        }
        System.out.println("Loop through all case statements.");
        for (CaseBlock caseBlock : switchObj.getCaseStatements()) {
            if (caseBlock.getComparisonData().equals(value)) {
                System.out.println("Create the child block.");
                return instance.createStack(stack, caseBlock);
            }
        }
        return null;

    }

}
