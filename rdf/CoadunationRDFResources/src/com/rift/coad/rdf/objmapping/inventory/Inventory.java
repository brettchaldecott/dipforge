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
 * Inventory.java
 */

// package path
package com.rift.coad.rdf.objmapping.inventory;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

/**
 * This object defines the base for the inventory
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Inventory")
public class Inventory extends ResourceBase {

    // private member variables
    private String id;
    private String name;


    /**
     * The default constructor.
     */
    public Inventory() {
    }
    
    
    /**
     * This constructor sets up all the member variables.
     *
     * @param attributes The attributes.
     * @param id The id of the inventory.
     * @param name The name of the inventory item.
     */
    public Inventory(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }



    /**
     * This method returns the id of the object.
     *
     * @return The id of the object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the id of the inventory.
     *
     * @return The id of the inventory.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#InventoryId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method is responsible for setting the id of the inventory object.
     *
     * @param id The id of the inventory object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#InventoryId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the name of the inventory item.
     *
     * @return The name of the inventory item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#InventoryName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the inventory item.
     *
     * @param name The name of the inventory item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#InventoryName")
    public void setName(String name) {
        this.name = name;
    }

}
