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


package com.rift.coad.change.rdf.objmapping.change.task.logic;


// semantic imports
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

// change imports
import com.rift.coad.change.rdf.objmapping.change.task.Block;


/**
 * This object represents an else block.
 *
 * @author brett
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Else")
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

    

}
