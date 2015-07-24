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

// the package information
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
 * This object represents an ISSN serial number.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base/")
@RdfType("ISSN")
public class ISSN extends SerialNumber {
    
    private String value;

    /**
     * The default constructor.
     */
    public ISSN() {
    }

    
    /**
     * The constructor that sets the ISSN value.
     * 
     * @param value The value of the ISSN serial number.
     */
    public ISSN(String value) throws ObjException {
        if (value.startsWith("http://")) {
            this.value = null;
        } else {
            this.validate(value);
            this.value = value;
        }
    }
    
    
    /**
     * This method returns the value of the serial number.
     * 
     * @return This method represents the value of the object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }

    /**
     * This method sets the value of the ISSN serial number.
     * @param value
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SerialNumberValue")
    @Override
    public void setValue(String value) throws ObjException {
        this.validate(value);
        this.value = value;
    }

    
    /**
     * The id for the object.
     * 
     * @return The id for the object.
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
        if ((value != null) && !value.matches("[0-9]{4}-[0-9Xx]{4}")) {
            throw new ObjException("The ISSN number is incorrectly formated");
        }
    }


    /**
     * The equals operator.
     *
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal FALSE if not.
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
        final ISSN other = (ISSN) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code operator.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    /**
     * The string value of this object.
     * @return
     */
    @Override
    public String toString() {
        return value;
    }
    
}
