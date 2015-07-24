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
 * Address.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.exception.ObjException;


/**
 * This object represent an address
 * 
 * @author brett chaldecott
 */
public class Address extends DataType {

    private String id;
    private String value;
    private AddressCode code;
    private Country country;
    
    
    /**
     * The default constructor
     *
     * @throws ObjException
     */
    public Address() throws ObjException {
    }
    
    
    /**
     * This constructor is responsible for setting all private member variables.
     * 
     * @param value The value for the address.
     * @param code The code for the address.
     * @throws ObjException
     */
    public Address(String id, String value, AddressCode code) throws ObjException {
        this.id = id;
        this.value = value;
        this.code = code;
    }


    /**
     * The id of this address object.
     * 
     * @return The id of this address object.
     */
    public String getId() {
        return id;
    }


    /**
     * Set the id of the address object.
     *
     * @param id The new id of the address object.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the unique id for this object.
     * 
     * @return The string containing the unique id for this object.
     */
    public String getObjId() {
        return this.id;
    }



    
    /**
     * The value.
     * @return The string value.
     */
    public String getValue() {
        return value;
    }

    
    /**
     * This method sets the value.
     * 
     * @param value The new value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * The address code.
     * @return The address code.
     */
    public AddressCode getCode() {
        return code;
    }
    
    
    /**
     * This method sets the address code value.
     * 
     * @param code The new code.
     */
    public void setCode(AddressCode code) {
        this.code = code;
    }
    
    
    /**
     * This method returns the country information.
     * 
     * @return This method returns the country.
     */
    public Country getCountry() {
        return country;
    }

    
    /**
     * This method sets the country.
     * 
     * @param country The country reference.
     */
    public void setCountry(Country country) {
        this.country = country;
    }
    
    
    /**
     * The equals operation.
     * @param obj The object to perform the comparison on.
     * @return The return result.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code for this object.
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }


    /**
     * The string value for the address
     *
     * @return The string value for the address.
     */
    @Override
    public String toString() {
        return id + ":" + value + ":" + this.code.toString() + ":" +
                this.country.toString();
    }




}
