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
 * Organisation.java
 */

package com.rift.coad.rdf.objmapping.client.organisation;

// the web semantic import
import com.rift.coad.rdf.objmapping.client.base.DataType;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.Name;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;

/**
 * This object represents the base of an organisation.
 * 
 * @author brett chaldecott
 */
public class Organisation extends ResourceBase {
    private String id;
    private String name;
    
    
    /**
     * The default constructor.
     */
    public Organisation() {
        
    }

    
    /**
     * The default organisation.
     * 
     * @param id The id of the entity
     * @param name The name of the organisation
     */
    public Organisation(String id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * This constructor sets the supper attributes value.
     *
     * @param attributes The list of attributes for 
     * @param id
     * @param name
     */
    public Organisation(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * This method returns the id of the organisation.
     *
     * @return The id of the organisation.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the organisation.
     *
     * @param id This method sets the id of the organisation.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    
    /**
     * This method returns the name of the organisation.
     *
     * @return The name of the organisation.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the organisation.
     *
     * @param name The new name of the organisation.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * The equals operator responsible for generating the
     *
     * @param obj The object to perform the equals operation on.
     * @return TRUE if the object are equals, FALSE if they are not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organisation other = (Organisation) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }


    /**
     * This object contains
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value of this object.
     *
     * @return The string value of this object.
     */
    @Override
    public String toString() {
        return id + ":" + name;
    }





}
