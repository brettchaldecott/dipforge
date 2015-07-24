/*
 * RevisionManager: The revision manager client library
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
 * RDFTag.java
 */

package com.rift.coad.revision.client.rdf;

// java imports
import java.util.Date;


// data types
import com.rift.coad.rdf.objmapping.client.base.DataType;

/**
 * This object represents a tag.
 *
 * @author brett chaldecott
 */
public class RDFTag extends DataType {

    private String tag;
    private Date created;
    private String description;
    private String author;

    /**
     * The default constructor.
     */
    public RDFTag() {
    }


    /**
     * This constructor sets all member variables except the created date which is set to
     * the current time.
     *
     * @param tag The string containing the tag name.
     * @param description The description of the tag.
     * @param author The author of the changes.
     */
    public RDFTag(String tag, String description, String author) {
        this.tag = tag;
        this.created = new Date();
        this.description = description;
        this.author = author;
    }


    /**
     * This constructor sets all the internal member variables.
     *
     * @param tag The tag name
     * @param created The created date attached to the file.
     * @param description The description of the changes.
     * @param author The author of the changes.
     */
    public RDFTag(String tag, Date created, String description, String author) {
        this.tag = tag;
        this.created = created;
        this.description = description;
        this.author = author;
    }


    /**
     * This method sets the name of the author.
     *
     * @return The string containing the name of the author
     */
    public String getAuthor() {
        return author;
    }


    /**
     * This method sets the author information for the tag.
     *
     * @param author The string containing the author information for the tag.
     */
    public void setAuthor(String author) {
        this.author = author;
    }


    /**
     * The created date for the rdf tag.
     *
     * @return The date that the tag was created.
     */
    public Date getCreated() {
        return created;
    }


    /**
     * This method sets the tag created date.
     *
     * @param created The created date attached to the file.
     */
    public void setCreated(Date created) {
        this.created = created;
    }


    /**
     * This method returns the description of the tag.
     *
     * @return The tag description.
     */
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the tag description.
     *
     * @param description The tag description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the tag name information.
     *
     * @return The string containing the tag name information.
     */
    public String getTag() {
        return tag;
    }


    /**
     * This method sets the tag name information.
     *
     * @param tag The string containing the tag name information.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }


    /**
     * This method performs the equals operation.
     *
     * @param obj The object containing the equals operation.
     * @return TRUE if equal, FALSE if not.
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
        final RDFTag other = (RDFTag) obj;
        if ((this.tag == null) ? (other.tag != null) : !this.tag.equals(other.tag)) {
            return false;
        }
        return true;
    }


    /**
     * The hash code for the object.
     *
     * @return The hash code for the object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.tag != null ? this.tag.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method returns the string representation of this object.
     *
     * @return The string representation of this object
     */
    @Override
    public String toString() {
        return "[Tag: " + tag + "\n" +
            "Created: " + created + "\n" +
            "Description: "  + description + "\n" +
            "Author: " + author + "]\n";
    }


    
}
