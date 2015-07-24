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
 * Hardware.java
 */

// package path
package com.rift.coad.rdf.objmapping.inventory;

// jena bean imports
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.RdfType;
import thewebsemantic.Namespace;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.Name;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import thewebsemantic.Identifier;
import thewebsemantic.RdfProperty;

/**
 * The hardware information objects.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Hardware")
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
     * This method returns the id of the object.
     *
     * @return The string containing the id of this object.
     */
    @Override
    public String getObjId() {
        return "" + id;
    }
    
    
    /**
     * This method returns the id of this object.
     * 
     * @return The id of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#HardwareId")
    @Identifier()
    public int getId() {
        return id;
    }


    /**
     * This method sets the id.
     *
     * @param id The id for the inventory object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#HardwareId")
    public void setId(int id) {
        this.id = id;
    }


    /**
     * This method returns the name of the hardware
     *
     * @return The name of this hardware item
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#HardwareName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the hardware
     *
     * @param name The name of the hardware to set.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#HardwareName")
    public void setName(String name) {
        this.name = name;
    }


}
