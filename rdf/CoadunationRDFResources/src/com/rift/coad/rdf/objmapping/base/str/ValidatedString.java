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
 * ValidatedString.java
 */

// package path
package com.rift.coad.rdf.objmapping.base.str;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.ComparisonOperators;
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.Str;
import com.rift.coad.rdf.objmapping.exception.ObjException;

// semantics
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object provides the ability to perform regex validation on an object.
 *
 * @author Brett Chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("ValidatedString")
public class ValidatedString extends Str implements ComparisonOperators {

    // private member variables
    private String value;
    private String regex;

    /**
     * The default constructor
     */
    public ValidatedString() {
    }


    /**
     * This method sets the regex.
     *
     * @param regex The regex.
     */
    public ValidatedString(String regex) {
        this.regex = regex;
    }


    /**
     * This constructor instanciates the object and performs the validation on the value.
     *
     * @param value The value of the object.
     * @param regex The regex value.
     */
    public ValidatedString(String value, String regex) throws ObjException {
        this.value = value;
        this.regex = regex;
        if (value != null && !value.matches(regex)) {
            throw new ObjException("The value is invalid as it does not match the REGEX");
        }
    }


    /**
     * The id of the object.
     *
     * @return The id of the object.
     */
    @Override
    public String getObjId() {
        return value;
    }


    /**
     * This method sets the regex value.
     *
     * @return The string containing the regex.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#ValidatedRegex")
    public String getRegex() {
        return regex;
    }


    /**
     * This method sets the regex value.
     *
     * @param regex The string containing the regex value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#ValidatedRegex")
    public void setRegex(String regex) {
        this.regex = regex;
    }


    /**
     * This method returns the value of the validated string.
     *
     * @return The value of the validated string.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#ValidatedValue")
    @Identifier()
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the object.
     *
     * @param value This method sets the value of the
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#ValidatedValue")
    public void setValue(String value) throws ObjException {
        if (regex != null && value != null && !value.matches(regex)) {
            throw new ObjException("The new string value does not match the regex");
        }
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
        final ValidatedString other = (ValidatedString) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    /**
     * This method performs the equals operator on the value.
     * @param value The value to perfrm the equals on.
     * @return TRUE if equals, FALSE if not.
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean equalValue(DataType value) throws ObjException {
        if (value == null) {
            return false;
        }
        if (value instanceof GenericString) {
            return this.value.equals(GenericString.class.cast(value).getValue());
        } else if (value instanceof ValidatedString) {
            return this.value.equals(ValidatedString.class.cast(value).getValue());
        } else {
            return this.value.equals(value.toString());
        }
    }


    /**
     * This method is not supported.
     *
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean lessThan(DataType value) throws ObjException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method is not supported
     *
     * @param value
     * @return
     * @throws com.rift.coad.rdf.objmapping.exception.ObjException
     */
    public boolean greaterThan(DataType value) throws ObjException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
