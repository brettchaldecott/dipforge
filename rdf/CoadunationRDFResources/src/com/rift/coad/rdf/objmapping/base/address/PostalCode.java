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
 * PostalCode.java
 */

// the package path
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
 * This object is responsible for representing the postal code.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("ZipCode")
public class PostalCode extends AddressCode {
    
    public String value;

    
    /**
     * The default constructor.
     */
    public PostalCode() {
    }

    
    /**
     * The constructor that sets the value of this object.
     * 
     * @param value The value of this object.
     */
    public PostalCode(String value) {
        this.value = value;
    }
    
    
    
    /**
     * This method returns the internal value.
     * @return The string containing the internal value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }

    
    /**
     * The set value method.
     * 
     * @param value
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#AddressCodeValue")
    @Override
    public void setValue(String value) throws ObjException {
        this.value = value;
    }

    
    /**
     * The unique identifier for this object.
     * 
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return value;
    }
    

    /**
     * This method determines if the object are equal.
     * @param obj The object to perform the equals comparison on.
     * @return TRUE if equal, FALSE if not.
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
        final PostalCode other = (PostalCode) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * The hash code value for this object.
     * 
     * @return TRUE if equal false if not.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    
    /**
     * The to string value
     * 
     * @return The to string value.
     */
    @Override
    public String toString() {
        return value;
    }
    
    
    
}
