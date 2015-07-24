/*
 * CatalogClient: The catalog client.
 * Copyright (C) 2010  2015 Burntjam
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
 * CatalogOffering.java
 */

// package path
package com.rift.coad.catalog.rdf;

// coadunation import
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.rdf.objmapping.base.DataType;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

/**
 * The cata log offering.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#")
@RdfType("CatalogOffering")
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
            id = RandomGuid.getInstance().getGuid();
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
            id = RandomGuid.getInstance().getGuid();
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
            id = RandomGuid.getInstance().getGuid();
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
     * This method returns the object id.
     *
     * @return The id of the object.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This method gets the id for the catalog offering.
     *
     * @return The string containing the id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingId")
    public String getId() {
        return id;
    }


    /**
     * This method sets the id for the catalog offering.
     *
     * @param id The id for the catalog offering.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the external id.
     *
     * @return The string containing the external id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingExternalId")
    public String getExternalId() {
        return externalId;
    }


    /**
     * The setter for the external id.
     *
     * @param externalId The external id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingExternalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }


    /**
     * The getter for the name.
     *
     * @return The string containing the name.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingName")
    public String getName() {
        return name;
    }


    /**
     * This method sets the name of the catalog offering.
     *
     * @param name The name of the catalog offering.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingName")
    public void setName(String name) {
        this.name = name;
    }


    /**
     * This method gets the description.
     *
     * @return The string containing the description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingDescription")
    public String getDescription() {
        return description;
    }


    /**
     * This method sets the description
     *
     * @param description The string containing the description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingDescription")
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * This method returns the list of entries.
     *
     * @return The list of entries
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingEntries")
    public CatalogEntry[] getEntries() {
        return entries;
    }


    /**
     * The setter for the list of entries.
     *
     * @param entries The list of entries.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingEntries")
    public void setEntries(CatalogEntry[] entries) {
        this.entries = entries;
    }


    /**
     * This method handles the offerings.
     *
     * @return The offerings.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingOfferings")
    public CatalogOffering[] getOfferings() {
        return offerings;
    }


    /**
     * The list of joined offerings.
     *
     * @param offerings The list of offerings
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOfferingOfferings")
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


    
    
}
