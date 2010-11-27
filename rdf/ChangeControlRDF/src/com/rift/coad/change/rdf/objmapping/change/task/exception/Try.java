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
 * Try.java
 */

// package path
package com.rift.coad.change.rdf.objmapping.change.task.exception;

// the reference to the block
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.task.Block;

// semantics
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents the try catch block.
 *
 * @author brett chaldecott
 */

@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Try")
public class Try extends Block {

    private Catch[] catches;

    /**
     * The default constructor of this object.
     */
    public Try() {
    }



    /**
     * A constructor
     *
     * @param name The name of this try.
     * @param description The description of this try.
     * @param child The child series of tasks.
     * @param catches A catch statement.
     */
    public Try(String name, String description, ActionTaskDefinition child, Catch[] catches) {
        super(name, description, child);
        this.catches = catches;
    }


    /**
     * A constructor for the try block.
     *
     * @param name The name of the task.
     * @param description The description of this task.
     * @param next The next task.
     * @param child The child tasks.
     * @param catches The catch blocks.
     */
    public Try(String name, String description, ActionTaskDefinition next, ActionTaskDefinition child, Catch[] catches) {
        super(name, description, next, child);
        this.catches = catches;
    }

    
    /**
     * The list of catches.
     *
     * @return The list of catches.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CatchBlocks")
    public Catch[] getCatches() {
        return catches;
    }


    /**
     * The list of catches.
     *
     * @param catches The list of catches.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CatchBlocks")
    public void setCatches(Catch[] catches) {
        this.catches = catches;
    }


    
}
