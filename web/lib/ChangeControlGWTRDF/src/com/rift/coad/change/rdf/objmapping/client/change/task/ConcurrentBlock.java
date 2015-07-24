/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  2015 Burntjam
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
 * ConcurrentBlock.java
 */


// the package
package com.rift.coad.change.rdf.objmapping.client.change.task;

// the action task
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import java.util.Arrays;



/**
 * This object represents a concurrent block
 *
 * @author brett chaldecott
 */
public class ConcurrentBlock extends ActionTaskDefinition {

    // private member variables
    public ActionTaskDefinition[] children;
    public DataType[] parameters;

    /**
     * The default constructor of the concurrent block
     */
    public ConcurrentBlock() {
    }


    /**
     * The constructor that sets all member variables.
     *
     * @param name The name of this concurrent block
     * @param description The description.
     * @param children The children
     * @param parameters The parameter.
     */
    public ConcurrentBlock(String name, String description,
            ActionTaskDefinition[] children, DataType[] parameters) {
        super(name, description);
        this.children = children;
        this.parameters = parameters;
    }

    
    /**
     * Sets all parameters besides the id of this task.
     * 
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next entry.
     * @param children The children
     * @param parameters The parameter.
     */
    public ConcurrentBlock(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition[] children, DataType[] parameters) {
        super(name, description, next);
        this.children = children;
        this.parameters = parameters;
    }


    /**
     * This constructor sets all member variables.
     *
     * @param id The id of this task.
     * @param name The name of the task.
     * @param description The description of the task.
     * @param next The next entry.
     * @param children The children.
     * @param parameters The parameters.
     */
    public ConcurrentBlock(String id, String name, String description,
            ActionTaskDefinition next, ActionTaskDefinition[] children, DataType[] parameters) {
        super(id, name, description, next);
        this.children = children;
        this.parameters = parameters;
    }


    /**
     * This method returns the children for the concurrent block.
     *
     * @return The list of children tasks.
     */
    public ActionTaskDefinition[] getChildren() {
        return children;
    }


    /**
     * This method sets the children of the concurrent block.
     *
     * @param children The list of children for the concurrent block.
     */
    public void setChildren(ActionTaskDefinition[] children) {
        this.children = children;
    }


    /**
     * This method retrieves the list of parameters that will be shared by all
     * concurrent children processes.
     *
     * @return The list of parameters.
     */
    public DataType[] getParameters() {
        return parameters;
    }


    /**
     * This method sets the parameters for this concurrent block
     * @param parameters
     */
    public void setParameters(DataType[] parameters) {
        for (DataType parameter: parameters) {
            setAssociatedObjectInfo(parameter);
        }
        this.parameters = parameters;
    }


    /**
     * This method adds the specified attribute.
     *
     * @param attribute The attribute to add to the list of attributes
     */
    public void addParameter(DataType parameter) {
        if (parameter == null) {
            return;
        }
        if (parameters == null) {
            parameters = new DataType[1];
        } else {
            //parameters = Arrays.copyOf(parameters, parameters.length +1);
        }
        setAssociatedObjectInfo(parameter);
        parameters[parameters.length - 1] = parameter;
    }


    /**
     * This method is used to guarantee the uniqueness of any parameters added to a block.
     *
     * @param parameter The parameter to guarantee the uniqueness of.
     */
    private void setAssociatedObjectInfo(DataType parameter) {
        if (parameter.getAssociatedObject() == null) {
            //parameter.setAssociatedObject(this.getIdForDataType() + "/" + super.getObjId() + "/" + super.getName());
            // ignore in gwt version
        }
    }






}
