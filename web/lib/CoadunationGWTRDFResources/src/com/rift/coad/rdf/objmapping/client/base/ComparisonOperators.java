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
 * AddressCode.java
 */


// package path
package com.rift.coad.rdf.objmapping.client.base;

import com.rift.coad.rdf.objmapping.client.exception.ObjException;

/**
 * This interface defines the comparison operators that can be performed on objects.
 *
 * @author brett chaldecott
 */
public interface ComparisonOperators {


    /**
     * This method is responsible for returning true if the values are equals, the
     * objects might not be equals because of the name or datatype id.
     *
     * @param value The value to make the comparison on.
     * @return TRUE if equal, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean equalValue(DataType value) throws ObjException;


    /**
     * This method checks to see if the current value is less than the supplied value.
     *
     * @param value The value to perform the comparison against.
     * @return TRUE if less than, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean lessThan(DataType value) throws ObjException;
    

    /**
     * This method checks to see if the current value is greater than the supplied value.
     * @param value The value to perform the comparison against.
     * @return The result of the comparison.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean greaterThan(DataType value) throws ObjException;
}
