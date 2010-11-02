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
 * Software.java
 */


package com.rift.coad.rdf.objmapping.inventory;

// semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

// coadunation imports
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.base.DataType;


/**
 * The software object used to store the data.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Software")
public class Software extends ResourceBase {

    // private member variables
    private String id;
    private String name;

    /**
     * The default constructor of the software object.
     */
    public Software() {
    }


    /**
     * This constructor is responsible for setting the id and and names values.
     *
     * @param attributes The attributes to associate with the software object.
     * @param id The id of the object.
     * @param name The name of the software.
     */
    public Software(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * The id of the object.
     *
     * @return The id of the object.
     */
    @Override
    public String getObjId() {
        return id;
    }

    /**
     * This method returns the id of the software item.
     *
     * @return The id of the software item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#SoftwareId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the software id.
     *
     * @param id The id for the software.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#SoftwareId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the name of the software
     *
     * @return The string containing the name of the software.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#SoftwareName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the software item.
     *
     * @param name The name of the software item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#SoftwareName")
    public void setName(String name) {
        this.name = name;
    }



}
