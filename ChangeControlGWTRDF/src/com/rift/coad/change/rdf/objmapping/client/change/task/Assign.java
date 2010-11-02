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


// package path
package com.rift.coad.change.rdf.objmapping.client.change.task;

// change control rdf
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * The assignment object.
 *
 * @author brett chaldecott
 */
public class Assign extends ActionTaskDefinition {

    // private member variables
    private String target;
    private String source;


    /**
     * The default constructor
     */
    public Assign() {

    }


    /**
     * This constructor sets all parameters besides the next parameter.
     *
     * @param name The name of the next task
     * @param description The description of the task
     * @param target The data target.
     * @param source The source target.
     */
    public Assign(String name, String description, String target, String source) {
        super(name, description);
        this.target = target;
        this.source = source;
    }


    /**
     * The constructor of the assignment.
     *
     * @param name The name of the assignment.
     * @param description The description of the assignment.
     * @param next The next task.
     * @param target The target.
     * @param source The source.
     */
    public Assign(String name, String description, ActionTaskDefinition next,
            String target, String source) {
        super(name, description, next);
        this.target = target;
        this.source = source;
    }


    /**
     * This method returns the source object.
     *
     * @return The result.
     */
    public String getSource() {
        return source;
    }


    /**
     * This method sets the source.
     *
     * @param source The string containing the source information.
     */
    public void setSource(String source) {
        this.source = source;
    }

    
    /**
     * This method gets the target.
     * 
     * @return This method gets the target variable.
     */
    public String getTarget() {
        return target;
    }

    
    /**
     * This method sets the target of the
     *
     * @param target The variable.
     */
    public void setTarget(String target) {
        this.target = target;
    }


    /**
     * The string value
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nTarget: " + target + "\nSource: " + source;
    }

    /**
     * Return a new instance of the assignment
     */
    public static Assign createInstance() {
        Assign assign = new Assign();
        assign.setIdForDataType(Constants.ASSIGN);
        assign.setBasicType(Constants.ASSIGN);
        return assign;
    }


    /**
     * This constructor sets all parameters besides the next parameter.
     *
     * @param name The name of the next task
     * @param description The description of the task
     * @param target The data target.
     * @param source The source target.
     */
    public static Assign createInstance(String name, String description,
            String target, String source) {
        Assign assign = createInstance();
        assign.setName(name);
        assign.setDescription(description);
        assign.setTarget(target);
        assign.setSource(source);
        return assign;
    }


    /**
     * The constructor of the assignment.
     *
     * @param name The name of the assignment.
     * @param description The description of the assignment.
     * @param next The next task.
     * @param target The target.
     * @param source The source.
     */
    public static Assign createInstance(String name, String description, ActionTaskDefinition next,
            String target, String source) {
        Assign assign = createInstance();
        assign.setName(name);
        assign.setDescription(description);
        assign.setNext(next);
        assign.setTarget(target);
        assign.setSource(source);
        return assign;
    }

}
