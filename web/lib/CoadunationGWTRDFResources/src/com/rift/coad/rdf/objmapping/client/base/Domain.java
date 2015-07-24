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
 * Domain.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base;


// the semantic information
import com.rift.coad.rdf.objmapping.client.exception.ObjException;

/**
 * This object manages the domain information.
 * 
 * @author brett chaldecott
 */
public class Domain extends DataType {
    private String value;

    /**
     * The domain name value.
     */
    public Domain() {
    }


    /**
     * This constructor sets the domain value.
     *
     * @param value The string value.
     * @throws ObjException
     */
    public Domain(String value) throws ObjException {
        validate(value);
        this.value = value;
    }


    /**
     * This method will retrieve the value for the object.
     *
     * @return The getter method.
     */
    public String getValue() {
        return value;
    }


    /**
     * The setter value method.
     *
     * @param value The value to set.
     * @throws ObjException
     */
    public void setValue(String value) throws ObjException {
        validate(value);
        this.value = value;
    }

    
    /**
     * This method is responsible for performing validation of the data string.
     *
     * @param value The string to perform the validation on.
     * @throws ObjException
     */
    private void validate(String value) throws ObjException {
        if ((value != null) && (!value.equals("")) && !(value.matches("[a-zA-Z0-9.-]+"))) {
            throw new ObjException("The value is not a correctly formated domain name.");
        }
    }

    
    /**
     * The equals operator responsible for checking if the specified object is 
     * equal to the current object 
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if the objects are equal, FALSE if they are not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Domain other = (Domain) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code value.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value of this object.
     *
     * @return The string value of this object.
     */
    @Override
    public String toString() {
        return this.value;
    }




}
