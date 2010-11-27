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
 * IdNumber.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base.id;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.UniqueId;


/**
 * The id number of the object.
 * 
 * @author brett chaldecott
 */
public class IdNumber extends UniqueId {
    private String value;

    
    /**
     * The default constructor.
     */
    public IdNumber() {
    }
    
    
    /**
     * This constructor sets the value internal.
     * 
     * @param value The value maintained by this object.
     */
    public IdNumber(String value) {
        this.value = value;
    }

    /**
     * The getter and setter for the id number value.
     * 
     * @return The value contained within.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value of the id number.
     * 
     * @param value The new value.
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * This method returns true if the object passed in is equal to this object.
     * @param obj The object to perform the comparison on.
     * @return The return value.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdNumber other = (IdNumber) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This method returns the hash code for this object.
     * 
     * @return The integer value.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
