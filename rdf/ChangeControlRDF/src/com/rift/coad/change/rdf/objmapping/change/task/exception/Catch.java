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


package com.rift.coad.change.rdf.objmapping.change.task.exception;

// semantic imports
import com.rift.coad.change.rdf.objmapping.change.ActionTaskDefinition;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

// change imports
import com.rift.coad.change.rdf.objmapping.change.task.Block;
import thewebsemantic.RdfProperty;

/**
 * This object represents a catch block.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Catch")
public class Catch extends Block {
    private String exceptionName;
    private String parameterName;

    /**
     * The default constructor
     */
    public Catch() {
    }

    /**
     * A constructor for the catch object.
     *
     * @param name The name of the catch.
     * @param description The description of the catch
     * @param child The child block.
     * @param exceptionName The exception name.
     * @param parameterName The name of the parameter.
     */
    public Catch(String name, String description, ActionTaskDefinition child, 
            String exceptionName, String parameterName) {
        super(name, description, child);
        this.exceptionName = exceptionName;
        this.parameterName = parameterName;
    }


    /**
     * The catch statement.
     * @param name The name of the catch statement.
     * @param description The description of the catch statement.
     * @param next The next block.
     * @param child The child block.
     * @param exceptionName The exception name.
     * @param parameterName The parameter name for the exception.
     */
    public Catch(String name, String description, ActionTaskDefinition next, 
            ActionTaskDefinition child, String exceptionName, String parameterName) {
        super(name, description, next, child);
        this.exceptionName = exceptionName;
        this.parameterName = parameterName;
    }

    /**
     * Get the exception that is being caught here.
     * @return
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ExceptionName")
    public String getExceptionName() {
        return exceptionName;
    }


    /**
     * This method sets the exception name member variables.
     *
     * @param exceptionName The name of the exception.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ExceptionName")
    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }


    /**
     * The name of the parameter.
     *
     * @return The name of the parameter.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CatchParameterName")
    public String getParameterName() {
        return parameterName;
    }


    /**
     * This method sets the parameter name.
     *
     * @param parameterName The parameter name for the catch variable.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#CatchParameterName")
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    



}
