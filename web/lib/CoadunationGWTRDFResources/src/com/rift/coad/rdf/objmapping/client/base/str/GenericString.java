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
 * ValidatedString.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base.str;

// rdf imports
import com.rift.coad.rdf.objmapping.client.base.Str;

/**
 * This object represents a generic string.
 *
 * @author brett chaldecott
 */
public class GenericString extends Str {

    // private member variables
    private String value;

    /**
     * The default constructor
     */
    public GenericString() {
    }


    /**
     * This method sets the value of the generic string object.
     * 
     * @return This method returns the value of the string
     */
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the generic string.
     *
     * @param value The value of the generic string.
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    @Override
    public boolean equals(Object obj) {
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
        return super.toString();
    }



}
