/*
 * ChangeControlRDF: The rdf information for the change control system.
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
 * ActionInfo.java
 */


package com.rift.coad.change.rdf.objmapping.change;

// the rdf base import
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * The information about a specific action.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/change#")
@RdfType("ActionInfo")
public class ActionInfo extends DataType {
    private String name;
    private String description;


    /**
     * The default constructor.
     */
    public ActionInfo() {
    }


    /**
     * This constructor is responsible for setting the name and description.
     *
     * @param name The name of the action.
     * @param description The description.
     */
    public ActionInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * This method returns the id
     *
     * @return The object id.
     */
    @Override
    public String getObjId() {
        return name;
    }


    /**
     * This method returns the description of this object.
     *
     * @return The description of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Description")
    public String getDescription() {
        return description;
    }


    /**
     * The setter for the description of this object.
     *
     * @param description The string containing the description of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Description")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The getter for the name of this object.
     *
     * @return The string containing the name of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Name")
    @Identifier()
    public String getName() {
        return name;
    }


    /**
     * The setter for the name of this action.
     *
     * @param name The name of this object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/change#Name")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The equals operator.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        // call the parent method.
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionInfo other = (ActionInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code for this object.
     * 
     * @return The calculated hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    /**
     * The method that returns a string representation of this object.
     * @return
     */
    @Override
    public String toString() {
        return this.name + "," +  this.description;
    }




}
