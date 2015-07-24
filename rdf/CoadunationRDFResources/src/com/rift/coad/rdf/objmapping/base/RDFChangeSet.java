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
 * RDFChangeSet.java
 */


// package path
package com.rift.coad.rdf.objmapping.base;

import java.util.Date;
import thewebsemantic.Identifier;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;


/**
 * This object represents a script change set.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("RDFChangeSet")
public class RDFChangeSet extends DataType {

    private String id;
    private Date changeDate;
    private String description;
    private DataType[] changes;

    /**
     * The default constructor
     */
    public RDFChangeSet() {
    }

    
    /**
     * This constructor sets all the member variables excluding the change list.
     * 
     * @param id The id of the change set.
     * @param changeDate The change date.
     * @param description The description.
     */
    public RDFChangeSet(String id, Date changeDate, String description) {
        this.id = id;
        this.changeDate = changeDate;
        this.description = description;
    }


    /**
     * This constructor sets the member variables
     *
     * @param id The id of the RDF change set.
     * @param changeDate The change date.
     * @param description The description.
     * @param changes The changes
     */
    public RDFChangeSet(String id, Date changeDate, String description, DataType[] changes) {
        this.id = id;
        this.changeDate = changeDate;
        this.description = description;
        this.changes = changes;
    }




    /**
     * This method returns the id of the
     * @return
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the ID of the change object.
     *
     * @return The id of the change set.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the change store.
     *
     * @param id The id of the change.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method retrieves the description information.
     *
     * @return The string containing the description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeDescription")
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description of the rdf change set.
     *
     * @param description The string containing the description of the change set.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeDescription")
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * This method returns the change date.
     * 
     * @return The object containing the change date information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeDate")
    public Date getChangeDate() {
        return changeDate;
    }


    /**
     * This method returns the change date.
     * 
     * @return The object containing the change date information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeDate")
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }



    /**
     * This method returns the change information.
     *
     * @return The object containing the change date information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeList")
    public DataType[] getChanges() {
        return changes;
    }


    /**
     * This method sets the change list
     *
     * @param changes
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#RDFChangeList")
    public void setChanges(DataType[] changes) {
        this.changes = changes;
        setAssociatedObject(changes);
    }


    /**
     * This method adds a change entry to the change set.
     *
     * @param change The change entry to add.
     */
    public void addChange(DataType change) {
        if (change == null) {
            return;
        }
        setAssociatedObject(change);
        if (this.changes == null) {
            this.changes = new DataType[] {change};
        } else {
            DataType[] changes = new DataType[this.changes.length + 1];
            for (int index = 0; index < this.changes.length; index++) {
                changes[index] = this.changes[index];
            }
            changes[this.changes.length] = change;
            this.changes = changes;
        }
    }


    /**
     * This method sets the associated object type.
     *
     * @param changes The changes.
     */
    private void setAssociatedObject(DataType[] changes) {
        if (changes != null) {
            for (DataType change : changes) {
                setAssociatedObject(change);
            }
        }
    }


    /**
     * This method sets the associated object for the change object supplied.
     *
     * @param change The change entry.
     */
    private void setAssociatedObject(DataType change) {
        if (change != null && change.getAssociatedObject() == null) {
            change.setAssociatedObject(this.getIdForDataType() + "." + id);
        }
    }


    /**
     * This method performs an equals comparison on the objects.
     *
     * @param obj The object to perform the equals comparison on.
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
        final RDFChangeSet other = (RDFChangeSet) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for this object.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }



}
