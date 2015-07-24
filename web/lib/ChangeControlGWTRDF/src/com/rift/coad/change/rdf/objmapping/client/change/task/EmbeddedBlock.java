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

// package path
package com.rift.coad.change.rdf.objmapping.client.change.task;


// change import
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;

/**
 * The definition of the embedded block.
 * 
 * @author brett chaldecott
 */
public abstract class EmbeddedBlock extends ActionTaskDefinition {

    /**
     * The default constructor
     */
    public EmbeddedBlock() {
    }

    
    /**
     * This constructor set the name and description.
     * 
     * @param name The name of this embedded block.
     * @param description The description of this embedded block.
     */
    public EmbeddedBlock(String name, String description) {
        super(name, description);
    }

    
    /**
     * This constructor sets the name, description and next task.
     * 
     * @param name The name of the task.
     * @param description The description of the task.
     * @param next The next task.
     */
    public EmbeddedBlock(String name, String description, ActionTaskDefinition next) {
        super(name, description, next);
    }

    

}
