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
 * ValidatedString.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.str;

// semantics
import com.rift.coad.rdf.objmapping.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.exception.ObjException;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

// rdf imports
import com.rift.coad.rdf.objmapping.base.Str;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

/**
 * This object represents a generic string.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("GenericString")
public class GenericString extends Str implements ComparisonOperators {

    // private member variables
    private String value;

    /**
     * The default constructor
     */
    public GenericString() {
    }


    /**
     * This method returns the id of this object.
     *
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return value;
    }
    
    
    /**
     * This method sets the value of the generic string object.
     * 
     * @return This method returns the value of the string
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#GenericStringValue")
    @Identifier()
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the generic string.
     *
     * @param value The value of the generic string.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#GenericStringValue")
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenericString other = (GenericString) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
    

    /**
     * This method performs the equals operator on the value.
     * @param value The value to perfrm the equals on.
     * @return TRUE if equals, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean equalValue(DataType value) throws ObjException {
        if (value == null) {
            return false;
        }
        if (value instanceof GenericString) {
            return this.value.equals(GenericString.class.cast(value).getValue());
        } else if (value instanceof ValidatedString) {
            return this.value.equals(ValidatedString.class.cast(value).getValue());
        } else {
            return this.value.equals(value.toString());
        }
    }


    /**
     * This method is not supported.
     *
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean lessThan(DataType value) throws ObjException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method is not supported
     *
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean greaterThan(DataType value) throws ObjException {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
