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
 * SHAPassword.java
 */


package com.rift.coad.rdf.objmapping.base.password;

import com.rift.coad.rdf.objmapping.base.Password;
import com.rift.coad.rdf.objmapping.utils.HashUtil;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This class is responsible for persisting a password using MD5.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("SHAPassword")
public class SHAPassword extends Password {

    private final static String FORMAT = "SHA{%s}";
    private final static String REGEX = "SHA[{]{1}[A-Za-z0-9+/=]+[}]{1}";

    // the private member variables.
    public String id;
    public String value;

    /**
     * Default constructor
     */
    public SHAPassword() {
    }

    /**
     * This constructor sets the id and value
     * @param id
     * @param value
     */
    public SHAPassword(String id, String value) {
        this.id = id;
        this.setValue(value);
    }


    /**
     * The id of this object.
     *
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the id of this password.
     *
     * @return The string containing the unique id of this password.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SHAPasswordId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the the sha object.
     *
     * @param id The id of the sha password.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#SHAPasswordId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This gets the password value.
     *
     * @return The string containing the password value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    @Override
    public String getValue() {
        return this.value;
    }


    /**
     * This method sets the password value.
     *
     * @param value
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#PasswordValue")
    @Override
    public void setValue(String value) {
        if ((value != null) && !value.matches(REGEX)) {
            this.value = String.format(FORMAT,HashUtil.sha(value));
        } else {
            this.value = value;
        }
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
        final SHAPassword other = (SHAPassword) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }




}
