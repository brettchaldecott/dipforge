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
 * MD5Password.java
 */


package com.rift.coad.rdf.objmapping.client.base.password;

import com.rift.coad.rdf.objmapping.client.base.Password;

/**
 * This class is responsible for persisting a password using MD5.
 *
 * @author brett chaldecott
 */
public class MD5Password extends Password {
    
    // the private member variables.
    public String id;
    public String value;

    /**
     * Default constructor
     */
    public MD5Password() {
    }

    /**
     *
     * @param id
     * @param value
     */
    public MD5Password(String id, String value) {
        this.id = id;
        this.value = value;
    }

    
    /**
     * This method returns the id of this password.
     *
     * @return The string containing the unique id of this password.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the the md5 object.
     *
     * @param id The id of the MD5 password.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This gets the password value.
     *
     * @return The string containing the password value.
     */
    @Override
    public String getValue() {
        return this.value;
    }


    /**
     * This method sets the password value.
     * @param value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }



}
