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
 * Hardware.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.inventory;

// jena bean imports
import com.rift.coad.rdf.objmapping.client.base.DataType;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.Name;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;

/**
 * The hardware information objects.
 *
 * @author brett chaldecott
 */
public class Hardware extends ResourceBase{

    private int id;
    private String name;

    /**
     * The default constructor
     */
    public Hardware() {
    }


    /**
     * The constructor that sets all the attributes.
     *
     * @param attributes The list of attributes.
     * @param id The id of the hardware.
     * @param name The name of the hardware.
     */
    public Hardware(DataType[] attributes, int id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * This method returns the id of this object.
     * 
     * @return The id of this object.
     */
    public int getId() {
        return id;
    }


    /**
     * This method sets the id.
     *
     * @param id The id for the inventory object.
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * This method returns the name of the hardware
     *
     * @return The name of this hardware item
     */
    public String getName() {
        return name;
    }


    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


}
