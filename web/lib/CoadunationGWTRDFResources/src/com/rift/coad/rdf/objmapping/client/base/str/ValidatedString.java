/*
 * CoadunationGWTRDFResources: The rdf resource object mappings.
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
package com.rift.coad.rdf.objmapping.client.base.str;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.Str;
import com.rift.coad.rdf.objmapping.client.exception.ObjException;

/**
 * This object provides the ability to perform regex validation on an object.
 *
 * @author Brett Chaldecott
 */
public class ValidatedString extends Str {

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
        if ((value != null) && !value.matches(regex)) {
            throw new ObjException("The value is invalid as it does not match the REGEX");
        }
    }


    /**
     * This method sets the regex value.
     *
     * @return The string containing the regex.
     */
    public String getRegex() {
        return regex;
    }


    /**
     * This method sets the regex value.
     *
     * @param regex The string containing the regex value.
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }


    /**
     * This method returns the value of the validated string.
     *
     * @return The value of the validated string.
     */
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the object.
     *
     * @param value This method sets the value of the
     */
    public void setValue(String value) throws ObjException {
        if (regex != null && value != null && !value.matches(regex)) {
            throw new ObjException("The new string value does not match the regex");
        }
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


}
