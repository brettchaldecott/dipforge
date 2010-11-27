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
 * Stock.java
 */

// package path
package com.rift.coad.rdf.objmapping.inventory;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.rdf.objmapping.base.Name;
import com.rift.coad.rdf.objmapping.resource.ResourceBase;

// rdf imports
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;



/**
 * This object is responsible for managing a stock item.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/inventory#")
@RdfType("Stock")
public class Stock extends ResourceBase {

    // private member variables
    private String id;
    private String name;

    /**
     * The default constructor
     */
    public Stock() {
    }


    /**
     * This constructor sets up all the attributes and values.
     *
     * @param attributes The list of attributes associated with this stock item.
     * @param id The id of stock item.
     * @param name The name of the stock item.
     */
    public Stock(DataType[] attributes, String id, String name) {
        super(attributes);
        this.id = id;
        this.name = name;
    }


    /**
     * This method returns the name of the object.
     *
     * @return The name of the object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method is responsible for returning the stock id.
     *
     * @return The id of the stock item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#StockId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the stock item.
     *
     * @param id The new id for the stock item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#StockId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the name for the stock item.
     *
     * @return The name of the stock item.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#StockName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the stock item.
     *
     * @param name The name of the stock item
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/inventory#StockName")
    public void setName(String name) {
        this.name = name;
    }



}
