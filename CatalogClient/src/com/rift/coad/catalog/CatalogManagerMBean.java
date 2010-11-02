/*
 * CatalogClient: The catalog client.
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
 * CatalogManagerMBean.java
 */

package com.rift.coad.catalog;

import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import com.rift.coad.catalog.rdf.CatalogEntry;
import com.rift.coad.catalog.rdf.CatalogOffering;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This is the mx bean interface for the catalog manager.
 * 
 * @author brett chaldecott
 */
public interface CatalogManagerMBean extends Remote {
    /**
     * This method returns the version information for the catalog manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of catalog manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this catalog manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of catalog manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this catalog manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the catalog manager.
     *
     * @return The string containing the description of the catalog manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of catalog manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this catalog manager implementation.")
    public String getDescription() throws RemoteException;

    
    /**
     * This method adds the entry to the catalog.
     *
     * @param entry The entry to add to th catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Adds a catalog entry from xml.")
    @Version(number="1.0")
    public void addCatalogEntryFromXML(
            @ParamInfo(name="XML",
            description="The name of the new type.")String xml)
            throws CatalogException, RemoteException;


    /**
     * This method is called to upate a catalog entry.
     * @param xml The xml to update.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Updates a catalog entry from xml.")
    @Version(number="1.0")
    public void updateCatalogEntryFromXML(
            @ParamInfo(name="XML",
            description="The name of the new type.")String xml)
            throws CatalogException, RemoteException;


    /**
     * This method is called to remove a entry from a catalog.
     *
     * @param catalogId The id to remove.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Removes a catalog entry.")
    @Version(number="1.0")
    public void removeCatalogEntry(
            @ParamInfo(name="catalogId",
            description="The id of the catalog entry to remove.")String catalogId)
            throws CatalogException, RemoteException;


    /**
     * This method lists the catalog entries.
     *
     * @return The list of catalog entries.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the list of entries defined in the catalog.")
    @Version(number="1.0")
    @Result(description="The list containing the ids of entries defined in the catalog.")
    public List<String> listCatalogEntries() throws CatalogException, RemoteException;


    /**
     * This method returns the catalog entry.
     *
     * @param catalogId The id of the catalog entry.
     * @return The reference to the catalog entry.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The requested catalog entry as XML.")
    @Version(number="1.0")
    @Result(description="The string containing the XML definition for the catalog entry.")
    public String getCatalogEntryAsXML(
            @ParamInfo(name="catalogId",
            description="The id of the catalog entry to retrieve.")String catalogId)
            throws CatalogException, RemoteException;


    /**
     * This method adds the entry to the catalog.
     *
     * @param entry The entry to add to th catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Add a catalog offering from XML.")
    @Version(number="1.0")
    public void addCatalogOfferingFromXML(
            @ParamInfo(name="XML",
            description="The xml encapsulating the new offering.")String xml)
            throws CatalogException, RemoteException;


    /**
     * This method is called to upate a catalog entry.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Update a catalog offering from XML.")
    @Version(number="1.0")
    public void updateCatalogOffering(
            @ParamInfo(name="XML",
            description="The xml encapsulating the updated offering.")String xml)
            throws CatalogException, RemoteException;


    /**
     * This method is called to remove a offering from a catalog.
     *
     * @param id The id to remove.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Update a catalog offering from XML.")
    @Version(number="1.0")
    public void removeCatalogOffering(String offeringId) throws CatalogException, RemoteException;



    /**
     * This method lists the catalog offerings.
     *
     * @return The list of catalog offerings.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the list of entries defined in the catalog.")
    @Version(number="1.0")
    @Result(description="The list containing the ids of entries defined in the catalog.")
    public List<String> listCatalogOfferings() throws CatalogException, RemoteException;


    /**
     * This method returns the catalog information.
     *
     * @param offeringId The id of the offering to return.
     * @return The reference to the catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the Catalog Offering as XML.")
    @Version(number="1.0")
    @Result(description="The string containing the catalog offering.")
    public String getCatalogOfferingAsXML(
            @ParamInfo(name="offeringId",
            description="The id of the offering entry to retrieve.")String offeringId)
            throws CatalogException, RemoteException;
}
