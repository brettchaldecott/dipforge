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
 * RDFLong.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base.number;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.RDFNumber;

/**
 * This object represents a long in the database
 *
 * @author brett chaldecott
 */
public class RDFInteger extends RDFNumber {

    // private member variables
    private Integer value = (int)0;

    /**
     * The default constructor.
     */
    public RDFInteger() {
    }

    /**
     * This constructor sets the value.
     *
     * @param value The new value.
     */
    public RDFInteger(int value) {
        this.value = value;
    }


    /**
     * This method returns the long value.
     *
     * @return The long value.
     */
    public int getValue() {
        if (value == null) {
            return 0;
        }
        return value;
    }


    /**
     * This method sets the value of the long
     *
     * @param value The long value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFInteger other = (RDFInteger) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    
    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        return this.value.toString();
    }
    

}
