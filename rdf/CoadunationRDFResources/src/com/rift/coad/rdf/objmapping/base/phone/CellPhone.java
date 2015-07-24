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
 * DataType.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.phone;


// imports
import com.rift.coad.rdf.objmapping.base.Phone;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

/**
 * This class represents a cell phone object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("CellPhone")
public class CellPhone extends Phone {
    
    /**
     * The required default contructor.
     */
    public CellPhone() {
    }
    
    
    /**
     * The constructor of the 
     * 
     * @param phoneType The phone type
     * @param value The value.
     * @param network The network.
     */
    public CellPhone(String value) {
        super(value);
    }
    
}
