/*
 * CatalogRDF: The rdf for the the catalog
 * Copyright (C) 2010  Rift IT Contracting
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
 * CatalogEntry.java
 */

// package
package com.rift.coad.catalog.rdf;


// coadunation imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * This object represents an entry in the catalog.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#")
@RdfType("CatalogEntry")
public class CatalogEntry extends DataType {

    // private member variables
    private String id;
    private String externalId;
    private String name;
    private String description;
    private DataType type;
    private CatalogEntry[] dependancies;
    

    /**
     * The default constructor
     */
    public CatalogEntry() {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }

    
    /**
     * This constructor sets the name and the description.
     * 
     * @param name The name of the catalog entry.
     * @param description The description of the entry.
     */
    public CatalogEntry(String name, String description) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
        this.name = name;
        this.description = description;
    }


    /**
     * This constructor 
     * @param id
     * @param name
     * @param description
     */
    public CatalogEntry(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    /**
     * The constuctor
     *
     * @param id
     * @param externalId
     * @param name
     * @param description
     * @param items
     * @param dependancies
     */
    public CatalogEntry(String id, String externalId, String name, String description,
            DataType type, CatalogEntry[] dependancies) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.dependancies = dependancies;
    }




    /**
     * This method returns the id of this object.
     * @return
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method returns the id of this catalog entry.
     *
     * @return The id of the catalog entry
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryId")
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the cataloge entry.
     *
     * @param id The id of the catalog entry.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method gets the external id.
     *
     * @return The string containing the external id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryExternalId")
    public String getExternalId() {
        return externalId;
    }


    /**
     * This method sets the external id.
     * 
     * @param externalId The external id to tie to.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryExternalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * This method returns the name of the catalog entry.
     *
     * @return The string containing the name of the catalog entry
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryName")
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of this catalog entry.
     * 
     * @param name The name of the catalog entry.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryName")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The description of the catalog entry.
     *
     * @return The string containing the catalog description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryDescription")
    public String getDescription() {
        return description;
    }

    
    /**
     * This method sets the description of the catalog entry.
     * 
     * @param description The descrption of the catalog.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryDescription")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the list of items for the catalog entry.
     * 
     * @return The list of items
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryType")
    public DataType getType() {
        return type;
    }


    /**
     * This method sets the items for the catalog.
     *
     * @param items The items that
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryType")
    public void setType(DataType type) {
        this.type = type;
    }

    
    /**
     * This method returns the dependancies managed by this object.
     * 
     * @return The list of dependancies.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryDependancies")
    public CatalogEntry[] getDependancies() {
        return dependancies;
    }

    
    /**
     * This method sets the dependancies for this object.
     * 
     * @param dependancies The list of dependancies.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntryDependancies")
    public void setDependancies(CatalogEntry[] dependancies) {
        this.dependancies = dependancies;
    }


    /**
     * The equals operator.
     *
     * @param obj The object to perform the equals operation on.
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
        final CatalogEntry other = (CatalogEntry) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the object.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string representation of this object.
     *
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return "ID:" + id + "\nName:" + name + "\nDescription:" + description;
    }
    


    
}
