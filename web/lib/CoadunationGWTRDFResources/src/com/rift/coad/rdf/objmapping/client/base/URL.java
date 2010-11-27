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
 * URL.java
 */


package com.rift.coad.rdf.objmapping.client.base;

/**
 * This object represents a url.
 * 
 * @author brett chaldecott
 */
public class URL extends DataType {
    
    private String value;

    
    /**
     * The default constructor.
     */
    public URL() {
    }

    
    /**
     * This constructor sets the values appropriatly.
     * 
     * @param value The new value.
     */
    public URL(String value) {
        this.value = value;
    }

    
    /**
     * The getter for the value.
     * 
     * @return The object containing the value.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * The setter for the value.
     * 
     * @param value The new value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    /**
     * The equals operator.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final URL other = (URL) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }
    
    
    /**
     * This operator returns the hash code value of this object.
     * 
     * @return The integer containing the hash code value of this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of this object.
     * 
     * @return The string value of the URI.
     */
    @Override
    public String toString() {
        return value.toString();
    }
    
    
    
    
    
    
}
