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
 * SerialNumber.java
 */

// the package path
package com.rift.coad.rdf.objmapping.base.serial;

// imports
import com.rift.coad.rdf.objmapping.base.SerialNumber;
import com.rift.coad.rdf.objmapping.exception.ObjException;

// the semantic information
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;


/**
 * This serial number
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base/")
@RdfType("ISBN")
public class ISBN extends SerialNumber {
    private String value;

    /**
     * The default constructor
     */
    public ISBN() {
    }

    /**
     * The constructor that sets the ISBN value.
     * 
     * @param value The new value to set
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public ISBN(String value) throws ObjException {
        if (value.startsWith("http://")) {
            this.value = null;
        } else {
            validate(value);
            this.value = value;
        }
    }


    /**
     * This object returns the value.
     *
     * @return The string value for this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }

    /**
     * This method sets the value of this object.
     * 
     * @param value
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Override
    public void setValue(String value) throws ObjException {
        validate(value);
        this.value = value;
    }
    
    
    /**
     * This method returns the unique identifier for this object.
     * 
     * @return The unique identifier for this object.
     */
    @Override
    public String getObjId() {
        return value;
    }


    /**
     * This method is used to validate the value passed in.
     * @param value
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    private void validate(String value) throws ObjException {
        if ((value != null) && !value.matches("[0-9]{10}|[0-9]{13}")) {
            throw new ObjException("The ISSN number is incorrectly formated");
        }
    }


    /**
     * This method checks the equals operator.
     *
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equals false if not.
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
        final ISBN other = (ISBN) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * The new hash value.
     * 
     * @return The new hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value of this object.
     *
     * @return The string value of this object.
     */
    @Override
    public String toString() {
        return value;
    }



}
