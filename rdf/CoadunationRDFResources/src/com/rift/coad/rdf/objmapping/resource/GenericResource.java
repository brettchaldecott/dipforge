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
 * GenericResource.java
 */

// package path
package com.rift.coad.rdf.objmapping.resource;

// imports
import com.rift.coad.rdf.objmapping.base.DataType;

// the web semantic
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents the generic resource base.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/resource#")
@RdfType("GenericResource")
public class GenericResource extends ResourceBase {

    // private member variables
    private String id;
    private String name;
    
    /**
     * The default constructor for the generic resource.
     */
    public GenericResource() {
    }


    /**
     * This constructor is responsible for setting the attributes and values.
     *
     * @param attributes The list of attributes.
     * @param id The id of the resource.
     * @param name The name of the resource.
     */
    public GenericResource(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * This method returns the id of the generic resource.
     *
     * @return
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method sets the id of the resource.
     *
     * @return The string containing the id of the object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/resource#ResourceId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the is responsible for setting the resource id.
     *
     * @param id The id of the resource.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/resource#ResourceId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The string containing the name of the resource.
     *
     * @return The string containing the name of the resource.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/resource#ResourceName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the resource.
     *
     * @param name The name of the resource.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/resource#ResourceName")
    public void setName(String name) {
        this.name = name;
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
        final GenericResource other = (GenericResource) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString();
    }




}
