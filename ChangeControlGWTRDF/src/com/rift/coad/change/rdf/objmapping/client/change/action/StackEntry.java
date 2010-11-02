/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * StackEntry.java
 */


// the package path
package com.rift.coad.change.rdf.objmapping.client.change.action;


// the data import
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object represents a status within the stack.
 *
 * @author brett chaldecott
 */
public class StackEntry extends DataType {

    // private member variables
    private String id;
    private DataType[] variables;
    private ActionTaskDefinition block;
    private ActionTaskDefinition currentTask;
    private StackEntry parent;
    private StackEntry child;


    /**
     * The default constructor.
     */
    public StackEntry() {
    }


    /**
     * This constructor sets the internal variables except child and parent.
     *
     * @param variables The variables associated with this scope.
     * @param block The block that defines this entry.
     */
    public StackEntry(DataType[] variables, ActionTaskDefinition block) {
        this.variables = variables;
        this.block = block;
        if (block instanceof Block) {
            this.currentTask = ((Block)block).getChild();
        }
    }




    /**
     * This constructor sets the import stack entries.
     *
     * @param variables The variables associated with the stack.
     * @param block The block that defines the scope of this stack.
     * @param parent The parent stack entry.
     */
    public StackEntry(DataType[] variables, ActionTaskDefinition block, StackEntry parent) {
        this.variables = variables;
        this.block = block;
        if (block instanceof Block) {
            this.currentTask = ((Block)block).getChild();
        }
        this.parent = parent;
    }

    
    /**
     * This constructor sets all internal private member variables.
     * 
     * @param id The id of this stack entry.
     * @param variables The variables associated with this scope entry.
     * @param currentTask The current task.
     * @param parent The parent information.
     * @param child The child task.
     */
    public StackEntry(String id, DataType[] variables,
            ActionTaskDefinition block, ActionTaskDefinition currentTask, StackEntry parent,
            StackEntry child) {
        this.id = id;
        this.variables = variables;
        this.block = block;
        this.currentTask = currentTask;
        this.parent = parent;
        this.child = child;
    }
    

    /**
     * The child stack entry.
     *
     * @return The stack entry to store.
     */
    public StackEntry getChild() {
        return child;
    }


    /**
     * This method sets the child information for this stack.
     *
     * @param child The child information.
     */
    public void setChild(StackEntry child) {
        this.child = child;
    }


    /**
     * This method method returns the block this stack is attached to.
     *
     * @return The block the value is attached to.
     */
    public ActionTaskDefinition getBlock() {
        return block;
    }


    /**
     * This method sets the block information.
     *
     * @param block The block to set
     */
    public void setBlock(ActionTaskDefinition block) {
        this.block = block;
    }


    /**
     * This method returns the current task.
     *
     * @return This method returns current task.
     */
    public ActionTaskDefinition getCurrentTask() {
        return currentTask;
    }


    /**
     * This method sets the current task.
     *
     * @param currentTask The current task to store.
     */
    public void setCurrentTask(ActionTaskDefinition currentTask) {
        this.currentTask = currentTask;
    }


    /**
     * This method returns the id of the stack entry.
     *
     * @return The id of the stack entry.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the stack id.
     *
     * @param id The id of the stack entry.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the parent information.
     *
     * @return The object returning the parent information.
     */
    public StackEntry getParent() {
        return parent;
    }


    /**
     * This method sets the parent information.
     *
     * @param parent The parent information.
     */
    public void setParent(StackEntry parent) {
        this.parent = parent;
    }


    /**
     * This method sets the variables associated with this stack entry.
     *
     * @return The variables associated with this stack entry.
     */
    public DataType[] getVariables() {
        return variables;
    }


    /**
     * This method sets the variables associated with this stack entry.
     *
     * @param variables The variables associated with this stack entry.
     */
    public void setVariables(DataType[] variables) {
        this.variables = variables;
    }

    
    /**
     * This method is called to replace a stack veriable.
     * @param name The name of the stack variable to retreive
     * @return TRUE if the variable was replaced, FALSE if not.
     */
    public boolean replaceStackVariable(String name, DataType value) {
        if (this.variables != null) {
            for (int index = 0; index < this.variables.length; index++) {
                DataType variable = this.variables[index];
                if (variable.getDataName().equals(name)) {
                    value.setDataName(name);
                    this.variables[index] = value;
                    return true;
                }
            }
        }
        if (this.parent != null) {
            return parent.replaceStackVariable(name,value);
        }
        return false;
    }


    /**
     * This method returns the specified stack variable.
     * @param name The name of the stack variable to retreive
     * @return
     */
    public DataType getStackVariable(String name) {
        if (this.variables != null) {
            for (DataType value : this.variables) {
                if (value.getDataName().equals(name)) {
                    return value;
                }
            }
        }
        if (this.parent != null) {
            return parent.getStackVariable(name);
        }
        return null;
    }

    
    /**
     * This method returns true if the objects are equal.
     *
     * @param obj The objects to perform the equals comparison on.
     * @return TRUE if the objects are equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StackEntry other = (StackEntry) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code value.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    

}
