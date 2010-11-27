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
package com.rift.coad.catalog.client.rdf;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;



/**
 * This object represents an entry in the catalog.
 *
 * @author brett chaldecott
 */
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
            id = IDGenerator.getId();
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
            id = IDGenerator.getId();
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
     * This method returns the id of this catalog entry.
     *
     * @return The id of the catalog entry
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id of the cataloge entry.
     *
     * @param id The id of the catalog entry.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method gets the external id.
     *
     * @return The string containing the external id.
     */
    public String getExternalId() {
        return externalId;
    }


    /**
     * This method sets the external id.
     * 
     * @param externalId The external id to tie to.
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * This method returns the name of the catalog entry.
     *
     * @return The string containing the name of the catalog entry
     */
    public String getName() {
        return name;
    }

    
    /**
     * This method sets the name of this catalog entry.
     * 
     * @param name The name of the catalog entry.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * The description of the catalog entry.
     *
     * @return The string containing the catalog description.
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * This method sets the description of the catalog entry.
     * 
     * @param description The descrption of the catalog.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the list of items for the catalog entry.
     * 
     * @return The list of items
     */
    public DataType getType() {
        return type;
    }


    /**
     * This method sets the items for the catalog.
     *
     * @param items The items that
     */
    public void setType(DataType type) {
        this.type = type;
    }

    
    /**
     * This method returns the dependancies managed by this object.
     * 
     * @return The list of dependancies.
     */
    public CatalogEntry[] getDependancies() {
        return dependancies;
    }

    
    /**
     * This method sets the dependancies for this object.
     * 
     * @param dependancies The list of dependancies.
     */
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
    


    /**
     * The default constructor
     */
    public static CatalogEntry create() {
        CatalogEntry entry = new CatalogEntry();
        entry.setBasicType(Constants.CATALOG_ENTRY);
        entry.setIdForDataType(Constants.CATALOG_ENTRY);
        return entry;
    }


    /**
     * The create method
     *
     * @param name The name of the catalog entry.
     * @param description The description of the entry.
     */
    public static CatalogEntry create(String name, String description) {
        CatalogEntry entry = create();
        entry.setName(name);
        entry.setDescription(description);
        return entry;
    }


    /**
     * This create method
     * @param id
     * @param name
     * @param description
     */
    public static CatalogEntry create(String id, String name, String description) {
        CatalogEntry entry = create();
        entry.setId(id);
        entry.setName(name);
        entry.setDescription(description);
        return entry;
    }


    /**
     * The create method
     *
     * @param id
     * @param externalId
     * @param name
     * @param description
     * @param items
     * @param dependancies
     */
    public static CatalogEntry create(String id, String externalId, String name, String description,
            DataType type, CatalogEntry[] dependancies) {
        CatalogEntry entry = create();
        entry.setId(id);
        entry.setExternalId(externalId);
        entry.setName(name);
        entry.setDescription(description);
        entry.setType(type);
        entry.setDependancies(dependancies);
        return entry;
    }

    
}
