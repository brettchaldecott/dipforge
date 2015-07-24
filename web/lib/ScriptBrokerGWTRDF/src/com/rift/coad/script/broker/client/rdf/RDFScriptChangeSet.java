/*
 * ScriptBrokerRDF: The rdf information for the script broker
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
 * RDFScriptChangeSet.java
 */


// package path
package com.rift.coad.script.broker.client.rdf;

// java import
import java.util.Date;

// data types
import com.rift.coad.rdf.objmapping.client.base.DataType;


/**
 * This object represents a script change set.
 *
 * @author brett chaldecott
 */
public class RDFScriptChangeSet extends DataType {

    private String id;
    private String name;
    private String description;
    private Date created;

    /**
     * The constructor of the rdf script change set.
     */
    public RDFScriptChangeSet() {
        created = new Date();
    }

    /**
     * This constructor sets the name and description of the change set.
     *
     * @param name This method contains the name of the change set.
     * @param description The description of the change set.
     */
    public RDFScriptChangeSet(String name, String description) {
        this.name = name;
        this.description = description;
        this.created = new Date();
    }




    /**
     * This constructor sets up all values.
     *
     * @param id The id of this object.
     * @param name The name of the change set.
     * @param description The description of this changes set.
     * @param created The date it was created.
     */
    public RDFScriptChangeSet(String id, String name, String description, Date created) {
       this.id = id;
       this.name = name;
       this.description = description;
       this.created = created;
    }


    /**
     * This method returns the created date for the change set.
     *
     * @return The created date.
     */
    public Date getCreated() {
        return created;
    }


    /**
     * This method sets the created date for the change set.
     *
     * @param created The created time.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    
    /**
     * The description of the change set.
     * 
     * @return The string containtining the description of the change set.
     */
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description for the change set
     *
     * @param description The string containing the description for the change set.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The setter for the change set id.
     *
     * @return The id for the change set.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the change set.
     *
     * @param id The id of the change set.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the name of the script change set.
     *
     * @return The string containing the name of the change set.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the change set.
     *
     * @param name The string containing the name of the change set.
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * This method performs the equals comparison on the object.
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFScriptChangeSet other = (RDFScriptChangeSet) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the script change set.
     *
     * @return This method returns the integer hash value.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * The to string method
     *
     * @return The string containing the description of this object.
     */
    @Override
    public String toString() {
        return "[id: " + id + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Created: " + created + "]";
    }




}
