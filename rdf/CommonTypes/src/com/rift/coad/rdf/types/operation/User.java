/*
 * CommonTypes: The common types
 * Copyright (C) 2011  Rift IT Contracting
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
 * Service.java
 */

package com.rift.coad.rdf.types.operation;

import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;

/**
 * The user object.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/Operation")
@LocalName("User")
public class User {

    // private member variables
    private String name;
    private String description;

    /**
     * The constructor.
     */
    public User() {
    }

    /**
     * The constructor that sets the user name information.
     * 
     * @param name The name
     */
    public User(String name) {
        this.name = name;
    }




    /**
     * This constructor sets up all the internal values.
     *
     * @param name
     * @param description
     */
    public User(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * This method returns the description.
     *
     * @return The description of the object.
     */
    @PropertyLocalName("Description")
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description
     *
     * @param description The description
     */
    @PropertyLocalName("Description")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method gets the name of the user.
     *
     * @return The name of the user.
     */
    @Identifier()
    @PropertyLocalName("Name")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name.
     * 
     * @param name This method sets the name.
     */
    @PropertyLocalName("Name")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method returns true if the object equal.
     *
     * @param obj The objects are equal.
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
        final User other = (User) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the user.
     * 
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return "User{" + "name=" + name + "description=" + description + '}';
    }


    

}
