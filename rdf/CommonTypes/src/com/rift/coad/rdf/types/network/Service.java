/*
 * CommonTypes: The common types
 * Copyright (C) 2011  2015 Burntjam
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


package com.rift.coad.rdf.types.network;

// imports
import com.rift.coad.rdf.semantic.annotation.Identifier;
import com.rift.coad.rdf.semantic.annotation.LocalName;
import com.rift.coad.rdf.semantic.annotation.Namespace;
import com.rift.coad.rdf.semantic.annotation.PropertyLocalName;

/**
 * This class represents a service.
 *
 * @author brett chaldecott
 */
@Namespace("http://dipforge.sourceforge.net/schema/rdf/1.0/common/NetworkService")
@LocalName("Service")
public class Service {

    // private member variables
    private String id;
    private String description;


    /**
     * The default constructor
     */
    public Service() {
    }


    /**
     * This method sets the service id.
     *
     * @param id The id of the service.
     */
    public Service(String id) {
        this.id = id;
    }




    /**
     * This constructor sets up all the internal values.
     *
     * @param id The id of the service
     * @param description The description of the service.
     */
    public Service(String id, String description) {
        this.id = id;
        this.description = description;
    }


    /**
     * This method returns the description of the object.
     *
     * @return The string containing the description of the object.
     */
    @PropertyLocalName("Description")
    public String getDescription() {
        return description;
    }


    /**
     * This method returns the description of the object.
     *
     * @param description The description.
     */
    @PropertyLocalName("Description")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the name of the service.
     *
     * @return The string containing the name of the service.
     */
    @Identifier()
    @PropertyLocalName("id")
    public String getId() {
        return id;
    }


    /**
     * The setter for the id of the service.
     *
     * @param name The name of the service.
     */
    @PropertyLocalName("id")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns true if the values are equal.
     *
     * @param obj The object to perform the equals comparison on.
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
        final Service other = (Service) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the object.
     *
     * @return The hash code for the object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value of the object.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Service{" + "id=" + id + "description=" + description + '}';
    }


    


}
