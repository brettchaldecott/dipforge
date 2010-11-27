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
 * Network.java
 */

// package path
package com.rift.coad.rdf.objmapping.client.inventory;

// semantic imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.base.Name;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;


/**
 * This object represents a network object.
 *
 * @author brett chaldecott
 */
public class Network extends ResourceBase {

    // private member variables.
    private String id;
    private String name;

    /**
     * The default constructor of the network object.
     */
    public Network() {
    }


    /**
     * This constructor sets up the id and name values
     * @param attributes
     * @param id
     * @param name
     */
    public Network(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * This method returns the id of the network object
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * This method is responsible for setting the id of the network.
     *
     * @param id The id of the network.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * The name of the network.
     *
     * @return This method returns the name of the network.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the network.
     *
     * @param name The name of the network.
     */
    public void setName(String name) {
        this.name = name;
    }


}
