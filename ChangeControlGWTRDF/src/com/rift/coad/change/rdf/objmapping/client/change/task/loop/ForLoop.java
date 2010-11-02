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
 * ForEach.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task.loop;

// change import
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;
import com.rift.coad.rdf.objmapping.client.base.DataType;




/**
 * This method loops until the specified logical expression is true.
 *
 * @author brett chaldecott
 */
public class ForLoop extends Block {

    // member variables
    private DataType index;
    private DataType end;
    private DataType increment;

    
    /**
     * The default constructor of the for loop.
     */
    public ForLoop() {
    }


    /**
     * This constructor sets all the internal values except for the next task.
     *
     * @param name The nme of the for loop.
     * @param description The description of the for loop.
     * @param child The child task.
     * @param index The index of the value.
     * @param end The end value.
     * @param increment The
     */
    public ForLoop(String name, String description, ActionTaskDefinition child,
            DataType index, DataType end, DataType increment) {
        super(name, description, child);
        this.index = index;
        if (this.index != null) {
            this.index.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.end = end;
        if (this.end != null) {
            this.end.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.increment = increment;
        if (this.increment != null) {
            this.increment.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }

    
    /**
     * This constructor sets the information.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The link to the next task.
     * @param child The start of the child flow.
     * @param index The index starting point.
     * @param end The ending point for the for loop.
     * @param increment The increment method.
     */
    public ForLoop(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child, DataType index, DataType end, DataType increment) {
        super(name, description, next, child);
        if (this.index != null) {
            this.index.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.end = end;
        if (this.end != null) {
            this.end.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.increment = increment;
        if (this.increment != null) {
            this.increment.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }


    /**
     * This method sets the end position.
     *
     * @return The end position.
     */
    public DataType getEnd() {
        return end;
    }


    /**
     * This method sets the end of the the loop value.
     *
     * @param end The end of the loop value.
     */
    public void setEnd(DataType end) {
        this.end = end;
        if (this.end != null) {
            this.end.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }


    /**
     * This method returns the increment value.
     *
     * @return The value that identifies the increment value.
     */
    public DataType getIncrement() {
        return increment;
    }


    /**
     * This method sets the increment value.
     *
     * @param increment The reference to the object containing the increment value.
     */
    public void setIncrement(DataType increment) {
        this.increment = increment;
        if (this.increment != null) {
            this.increment.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }


    /**
     * This method returns a reference to the index position.
     *
     * @return A reference to the index position.
     */
    public DataType getIndex() {
        return index;
    }


    /**
     * This method sets the index position.
     *
     * @param index The object containing the index position.
     */
    public void setIndex(DataType index) {
        this.index = index;
        if (this.index != null) {
            this.index.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }


    /**
     * This method is used to set the task definition id and than set the
     * associated object reference for the member variables.
     *
     * @param id The id of the object.
     */
    @Override
    public void setTaskDefinitionId(String id) {
        super.setTaskDefinitionId(id);
        if (this.index != null) {
            this.index.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.end = end;
        if (this.end != null) {
            this.end.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
        this.increment = increment;
        if (this.increment != null) {
            this.increment.setAssociatedObject(Constants.FOR_LOOP +
                this.getTaskDefinitionId());
        }
    }



    /**
     * The default constructor of the for loop.
     */
    public static ForLoop createInstance() {
        ForLoop forBlock = new ForLoop();
        forBlock.setIdForDataType(Constants.FOR_LOOP);
        forBlock.setBasicType(Constants.FOR_LOOP);
        return forBlock;
    }


    /**
     * This constructor sets all the internal values except for the next task.
     *
     * @param name The nme of the for loop.
     * @param description The description of the for loop.
     * @param child The child task.
     * @param index The index of the value.
     * @param end The end value.
     * @param increment The
     */
    public static ForLoop createInstance (
            String name, String description, ActionTaskDefinition child,
            DataType index, DataType end, DataType increment) {
        ForLoop forBlock = createInstance();
        forBlock.setName(name);
        forBlock.setDescription(description);
        forBlock.setChild(child);
        forBlock.setIndex(index);
        forBlock.setEnd(end);
        forBlock.setIncrement(increment);
        return forBlock;
    }


    /**
     * This constructor sets the information.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The link to the next task.
     * @param child The start of the child flow.
     * @param index The index starting point.
     * @param end The ending point for the for loop.
     * @param increment The increment method.
     */
    public static ForLoop createInstance(
            String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child, DataType index, DataType end, DataType increment) {
        ForLoop forBlock = createInstance();
        forBlock.setName(name);
        forBlock.setDescription(description);
        forBlock.setChild(child);
        forBlock.setNext(next);
        forBlock.setIndex(index);
        forBlock.setEnd(end);
        forBlock.setIncrement(increment);
        return forBlock;
    }

}
