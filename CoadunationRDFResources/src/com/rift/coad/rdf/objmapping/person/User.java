/*
 * 0250-CoadunationCRMServer: The CRM server.
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
 * User.java
 */


package com.rift.coad.rdf.objmapping.person;

// rdf imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.Password;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * The user object.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/person#")
@RdfType("User")
public class User extends Person {
    // private member variables
    private String username;
    private Password password;

    /**
     * The default constructor
     */
    public User() {
    }

    /**
     * Set all the properties for this user object.
     *
     * @param attributes The list of attributes associated with this object.
     * @param firstNames
     * @param surname
     * @param username
     */
    public User(DataType[] attributes, String firstNames, String surname,
            String username, Password password) {
        super(attributes, username, firstNames, surname);
        this.username = username;
        this.password = password;
    }


    /**
     * Set all the properties for this user object.
     *
     * @param attributes The list of attributes associated with this object.
     * @param id
     * @param firstNames
     * @param surname
     * @param username
     */
    public User(DataType[] attributes, String id, String firstNames, String surname,
            String username, Password password) {
        super(attributes, id, firstNames, surname);
        this.username = username;
        this.password = password;
    }


    /**
     * This method returns the id of the object.
     *
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return username;
    }


    /**
     * The unquie username used to authenticate this user.
     *
     * @return The string containing the username.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Username")
    @Identifier()
    public String getUsername() {
        return username;
    }

    
    /**
     * The unique name of this user.
     * @param username
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Username")
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * This method gets the password for this user.
     *
     * @return The object containing the password value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Password")
    public Password getPassword() {
        return password;
    }


    /**
     * This method sets the password of the user object.
     *
     * @param password The new password value.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/person#Password")
    public void setPassword(Password password) {
        this.password = password;
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
        final User other = (User) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString();
    }




}
