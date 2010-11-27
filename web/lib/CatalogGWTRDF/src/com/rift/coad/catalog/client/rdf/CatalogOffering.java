/*
 * CatalogClient: The catalog client.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * CatalogOffering.java
 */

// package path
package com.rift.coad.catalog.client.rdf;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;


/**
 * The cata log offering.
 *
 * @author brett chaldecott
 */
public class CatalogOffering extends DataType {

    // package path
    private String id;
    private String externalId;
    private String name;
    private String description;
    private CatalogEntry[] entries;
    private CatalogOffering[] offerings;


    /**
     * The default constructor
     */
    public CatalogOffering() {
        try {
            id = IDGenerator.getId();
        } catch (Exception ex) {
            // ignore
        }
    }


    /**
     * The constructor that sets up the name and description.
     * 
     * @param name
     * @param description
     */
    public CatalogOffering(String name, String description) {
        try {
            id = IDGenerator.getId();
        } catch (Exception ex) {
            // ignore
        }
        this.name = name;
        this.description = description;
    }


    /**
     * The constructor that provides the external id, name and description.
     *
     * @param externalId
     * @param name
     * @param description
     */
    public CatalogOffering(String externalId, String name, String description) {
        try {
            id = IDGenerator.getId();
        } catch (Exception ex) {
            // ignore
        }
        this.externalId = externalId;
        this.name = name;
        this.description = description;
    }


    /**
     * The constructor that sets all parameters.
     *
     * @param id The id of catalog offering.
     * @param externalId The external id to another system.
     * @param name The name of the entry.
     * @param description The description of the entry.
     * @param entries The number of entries.
     */
    public CatalogOffering(String id, String externalId, String name,
            String description, CatalogEntry[] entries) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.description = description;
        this.entries = entries;
    }


    /**
     * This method gets the id for the catalog offering.
     *
     * @return The string containing the id.
     */
    public String getId() {
        return id;
    }


    /**
     * This method sets the id for the catalog offering.
     *
     * @param id The id for the catalog offering.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the external id.
     *
     * @return The string containing the external id.
     */
    public String getExternalId() {
        return externalId;
    }


    /**
     * The setter for the external id.
     *
     * @param externalId The external id.
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * The getter for the name.
     *
     * @return The string containing the name.
     */
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the catalog offering.
     *
     * @param name The name of the catalog offering.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method gets the description.
     *
     * @return The string containing the description.
     */
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description
     *
     * @param description The string containing the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the list of entries.
     *
     * @return The list of entries
     */
    public CatalogEntry[] getEntries() {
        return entries;
    }


    /**
     * The setter for the list of entries.
     *
     * @param entries The list of entries.
     */
    public void setEntries(CatalogEntry[] entries) {
        this.entries = entries;
    }


    /**
     * This method handles the offerings.
     *
     * @return The offerings.
     */
    public CatalogOffering[] getOfferings() {
        return offerings;
    }


    /**
     * The list of joined offerings.
     *
     * @param offerings The list of offerings
     */
    public void setOfferings(CatalogOffering[] offerings) {
        this.offerings = offerings;
    }




    
    /**
     * This method returns true if the values are equal, and false if not.
     * 
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
        final CatalogOffering other = (CatalogOffering) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for this object.
     *
     * @return The hashcode for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    /**
     * This method returns the string value
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }


    
    /**
     * The default constructor
     */
    public static CatalogOffering create() {
        CatalogOffering offering = new CatalogOffering();
        offering.setBasicType(Constants.CATALOG_OFFERING);
        offering.setIdForDataType(Constants.CATALOG_OFFERING);
        return offering;
    }


    /**
     * The constructor that sets up the name and description.
     *
     * @param name
     * @param description
     */
    public static CatalogOffering create(String name, String description) {
        CatalogOffering offering = create();
        offering.setName(name);
        offering.setDescription(description);
        return offering;
    }


    /**
     * The constructor that provides the external id, name and description.
     *
     * @param externalId
     * @param name
     * @param description
     */
    public static CatalogOffering create(String externalId, String name, String description) {
        CatalogOffering offering = create();
        offering.setExternalId(externalId);
        offering.setName(name);
        offering.setDescription(description);
        return offering;
    }


    /**
     * The constructor that sets all parameters.
     *
     * @param id The id of catalog offering.
     * @param externalId The external id to another system.
     * @param name The name of the entry.
     * @param description The description of the entry.
     * @param entries The number of entries.
     */
    public static CatalogOffering create(String id, String externalId, String name,
            String description, CatalogEntry[] entries) {
        CatalogOffering offering = create();
        offering.setId(id);
        offering.setExternalId(externalId);
        offering.setName(name);
        offering.setDescription(description);
        offering.setEntries(entries);
        return offering;
    }
}
