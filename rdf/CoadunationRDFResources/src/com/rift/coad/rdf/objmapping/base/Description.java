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
 * Description.java
 */

package com.rift.coad.rdf.objmapping.base;

// package id
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;
import thewebsemantic.Identifier;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.RandomGuid;

/**
 * This object represents a description
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/base#")
@RdfType("Description")
public class Description extends DataType {
    // class singletons
    private static Logger log = Logger.getLogger(DataType.class);

    // the id for the description
    private String id;
    private String value;

    
    /**
     * The default constructor
     */
    public Description() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            log.fatal("Failed to generate a unique id: " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The constructor that sets the description informtion.
     * 
     * @param id The identifier of this object.
     * @param value The value stored within the object.
     */
    public Description(String id, String value) {
        this.id = id;
        this.value = value;
    }
    
    
    /**
     * This is the id of the description.
     * 
     * @return The id of the description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DescriptionId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * This method sets the id value.
     *
     * @param id The id of the
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DescriptionId")
    public void setId(String id) {
        this.id = id;
    }
    
    
    /**
     * The unique identifier for this object
     * @return
     */
    @Override
    public String getObjId() {
        return id;
    }
    

    /**
     * The getter that retrieves the value within this object.
     *
     * @return This method returns the value within.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DescriptionValue")
    public String getValue() {
        return value;
    }


    /**
     * This method sets the value of the description.
     *
     * @param value The value of the description
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/base#DescriptionValue")
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns true if the objects are equal.
     *
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Description other = (Description) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * This method returns the new hash code value.
     *
     * @return The integer  containing the hash code value.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
    /**
     * This method returns the string value of the description.
     * 
     * @return The string containing the value.
     */
    @Override
    public String toString() {
        return this.value;
    }
    

    

}
