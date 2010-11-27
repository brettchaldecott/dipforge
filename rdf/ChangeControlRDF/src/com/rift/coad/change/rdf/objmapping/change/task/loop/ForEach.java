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


package com.rift.coad.change.rdf.objmapping.change.task.loop;

// the change imports
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.change.task.Block;
import com.rift.coad.rdf.objmapping.base.RDFArray;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;


/**
 * This object represents a loop that will run over each entry in the supplied
 * object.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("ForEach")
public class ForEach extends Block {
    // private member variables.
    private String varName;
    private String instance;


    /**
     * The default constructor.
     */
    public ForEach() {
    }


    /**
     * This constructor
     *
     * @param name The name of this object.
     * @param description The description of this object.
     * @param child The child tasks that will be looped through by this object.
     * @param varName The name of the variable
     */
    public ForEach(String name, String description, ActionTaskDefinition child,
            String varName, String instance) {
        super(name, description, child);
        this.varName = varName;
        this.instance = instance;
    }

    
    /**
     * This constructor sets all the internal values.
     * 
     * @param name The name of the task.
     * @param description The description of the task.
     * @param next The next task.
     * @param child The child task.
     * @param varName The variable name
     */
    public ForEach(String name, String description, ActionTaskDefinition next,
            ActionTaskDefinition child, String varName, String instance) {
        super(name, description, next, child);
        this.varName = varName;
        this.instance = instance;
    }


    /**
     * This method returns the variable name.
     *
     * @return The string containing the variable name for this each loop.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ForEachVarName")
    public String getVarName() {
        return varName;
    }


    /**
     * The string containing the name of the variable.
     *
     * @param varName The name of the variable.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ForEachVarName")
    public void setVarName(String varName) {
        this.varName = varName;
    }


    /**
     * This method returns the instance name.
     *
     * @return The string containing the instance name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ForEachInstanceName")
    public String getInstance() {
        return instance;
    }


    /**
     * This method sets the instance name.
     *
     * @param instance The string containing the instance name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ForEachInstanceName")
    public void setInstance(String instance) {
        this.instance = instance;
    }




}
