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
 * RequestEvent.java
 */

// parameter.
package com.rift.coad.change.rdf.objmapping.change;

import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This operator returns the specified value.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("Return")
public class Return extends ActionTaskDefinition {
    private String parameter;

    /**
     * The default constructor
     */
    public Return() {
    }


    /**
     * This constructor creates a return statement that returns the parameter.
     *
     * @param name The name of the return object.
     * @param description The description of this operation.
     * @param parameter The parameter result.
     */
    public Return(String name, String description, String parameter) {
        super(name, description);
        this.parameter = parameter;
    }


    /**
     * This method returns the string that indicates the parameter.
     *
     * @return The string that defines the parameter value to return.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ReturnParameter")
    public String getParameter() {
        return parameter;
    }


    /**
     * This method sets the parameter value.
     *
     * @param parameter The parameter value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#ReturnParameter")
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    



    
    
}
