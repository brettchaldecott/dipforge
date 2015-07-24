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
package com.rift.coad.rdf.objmapping.base.address;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.AddressCode;
import com.rift.coad.rdf.objmapping.exception.ObjException;


/**
 * This is the zip code object.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("ZipCode")
public class ZipCode extends AddressCode {
    
    public String value;
    
    
    /**
     * The required default constructor.
     */
    public ZipCode() {
    }
    
    
    /**
     * The required default constructor.
     */
    public ZipCode(String value) throws ObjException {
        if (value.startsWith("http://")) {
            this.value = null;
        } else {
            validate(value);
            this.value = value;
        }
    }
    
    
    /**
     * This method returns the value of the zip code
     * @return
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value.
     * 
     * @param value
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    @Override
    public void setValue(String value) throws ObjException {
        validate(value);
        this.value = value;
    }
    
    
    /**
     * The unique if for this object.
     * 
     * @return The unique string id for this object.
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
        if (value != null && !(value.matches("[0-9]{5}"))
                && !(value.matches("[0-9]{5}-[0-9]{4}"))) {
            
            throw new ObjException("The zip code value is incorrect, must be a " +
                    "correctly formated Zip or Zip+4");
        }
    }
    
    
    /**
     * The equals operator for the zip code.
     * 
     * @param obj The object to peform the comparison on.
     * @return The return value.
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
        final ZipCode other = (ZipCode) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * The hash code value.
     * 
     * @return The new hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    
    /**
     * The method that returns the string value of this object.
     * 
     * @return The string value of this object
     */
    @Override
    public String toString() {
        return value;
    }
    
    
    
}
