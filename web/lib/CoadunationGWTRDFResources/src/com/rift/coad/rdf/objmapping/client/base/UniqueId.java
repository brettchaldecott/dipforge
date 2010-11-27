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
 * UniqueId.java
 */

package com.rift.coad.rdf.objmapping.client.base;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.Name;


/**
 * This object a unique identifier.
 * 
 * @author brett chaldecott
 */
public abstract class UniqueId extends DataType {

    /**
     * The getter and setter for the id number value.
     *
     * @return The value contained within.
     */
    public abstract String getValue();


    /**
     * This method sets the value of the id number.
     *
     * @param value The new value.
     */
    public abstract void setValue(String value);
}
