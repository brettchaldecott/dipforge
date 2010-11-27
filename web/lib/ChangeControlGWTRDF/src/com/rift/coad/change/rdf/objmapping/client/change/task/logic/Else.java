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
 * Else.java
 */


package com.rift.coad.change.rdf.objmapping.client.change.task.logic;


// semantic imports
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;

// change imports
import com.rift.coad.change.rdf.objmapping.client.change.Constants;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;


/**
 * This object represents an else block.
 *
 * @author brett
 */
public class Else extends Block {

    /**
     * The default constructor
     */
    public Else() {
    }

    /**
     * A constructor that sets the child task and name and description but not the next task.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     * @param child The child task link.
     */
    public Else(String name, String description, ActionTaskDefinition child) {
        super(name, description, child);
    }


    /**
     * This constructor sets up all the parent parameters.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next task reference.
     * @param child The child task.
     */
    public Else(String name, String description, ActionTaskDefinition next, ActionTaskDefinition child) {
        super(name, description, next, child);
    }

    
    /**
     * The default constructor
     */
    public static Else createInstance() {
        Else elseBlock = new Else();
        elseBlock.setIdForDataType(Constants.ELSE_BLOCK);
        elseBlock.setBasicType(Constants.ELSE_BLOCK);
        return elseBlock;
    }

    /**
     * A constructor that sets the child task and name and description but not the next task.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     * @param child The child task link.
     */
    public static Else createInstance(String name, String description, ActionTaskDefinition child) {
        Else elseBlock = createInstance();
        elseBlock.setName(name);
        elseBlock.setDescription(description);
        elseBlock.setChild(child);
        return elseBlock;
    }


    /**
     * This constructor sets up all the parent parameters.
     *
     * @param name The name of this task.
     * @param description The description of this task.
     * @param next The next task reference.
     * @param child The child task.
     */
    public static Else createInstance(String name, String description,
            ActionTaskDefinition next, ActionTaskDefinition child) {
        Else elseBlock = createInstance();
        elseBlock.setName(name);
        elseBlock.setDescription(description);
        elseBlock.setNext(next);
        elseBlock.setChild(child);
        return elseBlock;
    }
}
