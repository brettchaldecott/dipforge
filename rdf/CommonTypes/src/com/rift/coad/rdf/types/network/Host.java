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
 * Host.java
 */

// package path
package com.rift.coad.rdf.types.network;

// annotations
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;

/**
 * The hosts.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/Network")
@LocalName("Host")
public class Host {

    // private member variables
    private String name;
    private String description;

    
    /**
     * The default constructor
     */
    public Host() {
    }

    
    /**
     * This method returns the description
     *
     * @return The description of the object.
     */
    @PropertyLocalName("description")
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description
     *
     * @param description The description of the host.
     */
    @PropertyLocalName("description")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The getter for the name.
     *
     * @return The of the host.
     */
    @Identifier()
    @PropertyLocalName("Name")
    public String getName() {
        return name;
    }


    /**
     * This method to set the name
     *
     * @param name The name to set.
     */
    @PropertyLocalName("Name")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The equals operator.
     *
     * @param obj The object to perform the comparison on.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Host other = (Host) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the host.
     *
     * @return The host hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 67 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Host{" + "name=" + name + "description=" + description + '}';
    }


    

}
