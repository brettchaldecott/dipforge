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
 * Phone.java
 */

package com.rift.coad.rdf.objmapping.base;


import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

/**
 * This object represents the phone information.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("Phone")
public class Phone extends DataType {
    private String value;
    
    
    /**
     * The required default contructor.
     */
    public Phone() {
    }
    
    
    /**
     * 
     * @param value
     */
    public Phone(String value) {
        this.value = value;
    }
    
    
    /**
     * This method sets the value of the phone.
     * 
     * @return The value of the phone
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PhoneValue")
    @Identifier()
    public String getValue() {
        return value;
    }

    
    /**
     * This method gets the value.
     * 
     * @param value The value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PhoneValue")
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns the object id.
     * 
     * @return The unique id for this object.
     */
    @Override
    public String getObjId() {
        return value;
    }

    
    /**
     * The equals operator.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if the objects are equal.
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
        final Phone other = (Phone) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * The hash code value.
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of this object.
     * 
     * @return The new string value.
     */
    @Override
    public String toString() {
        return value;
    }
    
    
    
    
}
