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
 * Rack.java
 */

// package information.
package com.rift.coad.rdf.objmapping.inventory;

// semantic imports
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

// coadunation import
import com.rift.coad.rdf.objmapping.resource.ResourceBase;


/**
 * This object represents rack space.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Rack")
public class Rack extends ResourceBase {

    // private member variables
    private String id;
    private String name;

    /**
     * The default constructor
     */
    public Rack() {
    }


    /**
     * Setup all the resource values.
     *
     * @param attributes The list of attributes to store.
     * @param id The id of the rack.
     * @param name The name of the rack.
     */
    public Rack(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }




    /**
     * This method returns the id of the rack.
     *
     * @return The id of the object.
     */
    @Override
    public String getObjId() {
        return id;
    }
    

    /**
     * This method returns the id of the rack
     *
     * @return The id of the rack item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#RackId")
    @Identifier()
    public String getId() {
        return id;
    }
    

    /**
     * This method sets the id of the rack.
     *
     * @param id The id of the rack.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#RackId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     *
     * @return
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#RackName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the rack.
     *
     * @param name The name of the rack item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#RackName")
    public void setName(String name) {
        this.name = name;
    }

}
