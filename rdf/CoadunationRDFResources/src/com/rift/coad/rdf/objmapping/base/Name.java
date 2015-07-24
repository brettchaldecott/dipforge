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
 * Name.java
 */

package com.rift.coad.rdf.objmapping.base;


import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;


/**
 * This object represents a name.
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("Name")
public class Name extends DataType {
    private String value;

    /**
     * default contructor
     */
    public Name() {
    }
    
    
    /**
     * This contructor sets the value of the name
     * @param value
     */
    public Name(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns the value of the name
     * 
     * @return The value of the name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#NameValue")
    @Identifier()
    public String getValue() {
        return value;
    }
    
    
    /**
     * The value contained by this object.
     * 
     * @param value The contained by this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#NameValue")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This method returns the unique identifier for this object.
     * 
     * @return The string containing the unique identifier for this object.
     */
    @Override
    public String getObjId() {
        return value;
    }
    
    
    /**
     * This method checks to see if the object are equal.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if the values are equals FALSE if not.
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
        final Name other = (Name) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This method checks the hash code.
     * 
     * @return The integer hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of the name.
     * 
     * @return The string containing the name value.
     */
    @Override
    public String toString() {
        return super.toString();
    }
    
    
    
    
    
}
