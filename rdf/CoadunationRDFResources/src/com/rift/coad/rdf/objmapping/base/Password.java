/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * IPAddress.java
 */


// package base
package com.rift.coad.rdf.objmapping.base;

// rdf imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;


/**
 * The password object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base/")
@RdfType("Password")
public abstract class Password extends DataType {
    
    /**
     * This method returns the internal value.
     * 
     * @return The string containing the value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    public abstract String getValue();
    
    
    /**
     * This method sets the value.
     * 
     * @param value The value of the address.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    public abstract void setValue(String value);

}
