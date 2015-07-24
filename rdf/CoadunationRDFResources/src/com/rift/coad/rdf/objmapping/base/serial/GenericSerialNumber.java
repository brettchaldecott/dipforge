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
 * SerialNumber.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.serial;

// imports
import com.rift.coad.rdf.objmapping.base.SerialNumber;
import com.rift.coad.rdf.objmapping.exception.ObjException;

// the semantic information
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

/**
 * This represents a generic serial number.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base/")
@RdfType("GenericSerialNumber")
public class GenericSerialNumber extends SerialNumber {

    // the private member variables
    private String value;

    /**
     * The default constructor of the generic serial number.
     */
    public GenericSerialNumber() {
    }

    /**
     * This constructor sets the generic serial number value.
     *
     * @param value The new value to set.
     */
    public GenericSerialNumber(String value) {
        this.value = value;
    }


    /**
     * This method returns the value of the object.
     *
     * @return The value of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the object.
     * @param value
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Override
    public void setValue(String value) throws ObjException {
        this.value = value;
    }

    
    /**
     * This method returns the unique object identifier.
     * 
     * @return The string containing the unque object identifer.
     */
    @Override
    public String getObjId() {
        return value;
    }
    
    
    /**
     * This method checks if the objects are of equal value.
     * 
     * @param obj The object to check the equality on.
     * @return TRUE if the objects are equal, FALSE if not.
     */
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
        final GenericSerialNumber other = (GenericSerialNumber) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * This object returns the hash value of the object.
     *
     * @return The new hash value of the object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value.
     *
     * @return The string value
     */
    @Override
    public String toString() {
        return value;
    }




}
