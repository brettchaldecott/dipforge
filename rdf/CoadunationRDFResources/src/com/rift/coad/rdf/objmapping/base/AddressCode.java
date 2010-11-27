/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * AddressCode.java
 */

// package path
package com.rift.coad.rdf.objmapping.base;

// imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;

// coadunation import
import com.rift.coad.rdf.objmapping.exception.ObjException;


/**
 * This object represents the address code.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("AddressCode")
public abstract class AddressCode extends DataType {
    /**
     * This method returns the internal value.
     * 
     * @return The string containing the address code.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    public abstract String getValue();
    
    
    /**
     * This method sets the address code.
     * 
     * @param value The value of the address code.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    public abstract void setValue(String value) throws ObjException;
}
