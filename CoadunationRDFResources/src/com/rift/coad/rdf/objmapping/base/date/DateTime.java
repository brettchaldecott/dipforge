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
package com.rift.coad.rdf.objmapping.base.date;

// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.exception.ObjException;
import java.util.Date;

// semantic imports
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

// coadunation rdf imports
import com.rift.coad.rdf.objmapping.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.base.RDFDate;

/**
 * This object represents a date object.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("RDFDateTime")
public class DateTime extends RDFDate implements ComparisonOperators {

    // private member variables
    private Date value;

    /**
     * The default constructor
     */
    public DateTime() {
        value = new Date();
    }


    /**
     * This constructor sets up the date value.
     *
     * @param value The new value.
     */
    public DateTime(Date value) {
        this.value = value;
    }


    @Override
    public String getObjId() {
        if (value == null) {
            return "";
        }
        return value.toString();
    }


    /**
     * This method retrieves the date time value.
     *
     * @return The date time value
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFDateTimeValue")
    @Identifier()
    public Date getValue() {
        return value;
    }


    /**
     * This method sets the date time value.
     *
     * @param value The date time value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFDateTimeValue")
    public void setValue(Date value) {
        this.value = value;
    }

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
        final DateTime other = (DateTime) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    /**
     * This method
     * @return
     */
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


    /**
     * This method returns true if the current value is less than the supplied value.
     *
     * @param value The value to perform the comparison on.
     * @return TRUE if less than, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean lessThan(DataType value) throws ObjException {
        if (value == null) {
            throw new java.lang.NullPointerException("Comparitive value null");
        }
        if (!(value instanceof DateTime)) {
            throw new ObjException("Invalid comparison");
        }
        DateTime other = (DateTime)value;
        return this.value.before(other.value);
    }


    /**
     * This method returns true if the the current value is greater than the supplied value.
     *
     * @param value The value to perform the comparison on.
     * @return TRUE if greater than, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean greaterThan(DataType value) throws ObjException {
        if (value == null) {
            throw new java.lang.NullPointerException("Compartive value null");
        }
        if (!(value instanceof DateTime)) {
            throw new ObjException("Invalid comparison");
        }
        DateTime other = (DateTime)value;
        return this.value.after(other.value);
    }

    
    /**
     * This method is used to check if the values are equal.
     *
     * @param value The object to perform the comparison on.
     * @return TRUE if the values are equal, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean equalValue(DataType value) throws ObjException {
        if (value == null) {
            throw new java.lang.NullPointerException("Compartive value null");
        }
        if (!(value instanceof DateTime)) {
            throw new ObjException("Invalid comparison");
        }
        DateTime other = (DateTime)value;
        return this.value.equals(other);
    }

}
