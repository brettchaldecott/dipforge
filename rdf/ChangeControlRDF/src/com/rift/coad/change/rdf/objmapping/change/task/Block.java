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
 * Block.java
 */

package com.rift.coad.change.rdf.objmapping.change.task;


// imports
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.rdf.objmapping.base.DataType;
import java.util.Arrays;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;


/**
 * An execution block
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Block")
public class Block extends ActionTaskDefinition {

    // private member variables
    private ActionTaskDefinition child;
    private DataType[] parameters;

    
    public Block() {
    }

    public Block(String name, String description,
            ActionTaskDefinition child) {
        super(name, description);
        this.child = child;
    }


    public Block(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child) {
        super(name, description, next);
        this.child = child;
    }


    /**
     * This method returns the child information.
     *
     * @return The string containing the child information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Child")
    public ActionTaskDefinition getChild() {
        return child;
    }


    /**
     * This method sets the child member variable
     *
     * @param child The new child value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Child")
    public void setChild(ActionTaskDefinition child) {
        this.child = child;
    }


    /**
     * This method retrieves a list of parameters.
     *
     * @return The list of parameters associated with this block scope.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#BlockParameters")
    public DataType[] getParameters() {
        return parameters;
    }


    /**
     * This method sets the parameters.
     *
     * @param parameters This method sets the parameters.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#BlockParameters")
    public void setParameters(DataType[] parameters) {
        if (parameters != null) {
            for (DataType parameter: parameters) {
                setAssociatedObjectInfo(parameter);
            }
            this.parameters = parameters;
        } else {
            this.parameters = null;
        }
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
            parameters = Arrays.copyOf(parameters, parameters.length +1);
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
            parameter.setAssociatedObject(this.getIdForDataType() + "/" + super.getObjId() + "/" + super.getName());
        }
    }


    
}
