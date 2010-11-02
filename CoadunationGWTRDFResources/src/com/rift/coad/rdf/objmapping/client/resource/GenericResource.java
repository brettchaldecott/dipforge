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
 * GenericResource.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.resource;

// imports
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object represents the generic resource base.
 *
 * @author brett chaldecott
 */
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
     * This method sets the id of the resource.
     *
     * @return The string containing the id of the object.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the is responsible for setting the resource id.
     *
     * @param id The id of the resource.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The string containing the name of the resource.
     *
     * @return The string containing the name of the resource.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the resource.
     *
     * @param name The name of the resource.
     */
    public void setName(String name) {
        this.name = name;
    }

}
