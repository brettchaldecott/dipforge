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
 * RDFDate.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.base.date;

// java imports
import java.util.Date;

// coadunation rdf imports
import com.rift.coad.rdf.objmapping.client.base.RDFDate;

/**
 * This object represents a date object.
 *
 * @author brett chaldecott
 */
public class DateTime extends RDFDate {

    // private member variables
    private Date value;

    /**
     * The default constructor
     */
    public DateTime() {
    }


    /**
     * This constructor sets up the date value.
     *
     * @param value The new value.
     */
    public DateTime(Date value) {
        this.value = value;
    }


    /**
     * This method retrieves the date time value.
     *
     * @return The date time value
     */
    public Date getValue() {
        return value;
    }


    /**
     * This method sets the date time value.
     *
     * @param value The date time value.
     */
    public void setValue(Date value) {
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
        final DateTime other = (DateTime) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
