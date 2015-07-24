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
 * IPv4.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.ip;

// imports
import com.rift.coad.rdf.objmapping.base.IPAddress;

// the semantic information
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

/**
 * This object represents a base ip
 * 
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("IPv4")
public class IPv4 extends IPAddress {
    
    public String value;

    
    /**
     * The default constructor
     */
    public IPv4() {
        
    }

    
    /**
     * The constructor that sets the ip information.
     * 
     * @param ip The ip address
     */
    public IPv4(String value) {
        this.value = value;
    }
    
    
    
    /**
     * The ip address value.
     * 
     * @return The ip address value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IPValue")
    @Identifier()
    @Override
    public String getValue() {
        return value;
    }

    
    /**
     * The setter for the ip value.
     * 
     * @param value The value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#IPValue")
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    
    /**
     * This method returns the string id for this object.
     *  
     * @return The unique identifier for this object.
     */
    @Override
    public String getObjId() {
        return value;
    }
    
    
    /**
     * The equals operator.
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equals, FALSE if not.
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
        final IPv4 other = (IPv4) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    
    /**
     * The hash code operator.
     * 
     * @return The int hash value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * The to string operation.
     * 
     * @return The string to perform the operation on.
     */
    @Override
    public String toString() {
        return value;
    }
    
    
    

}
