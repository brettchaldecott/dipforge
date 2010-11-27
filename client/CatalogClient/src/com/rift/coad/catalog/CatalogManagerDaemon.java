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
 * CatalogManagerDaemon.java
 */


// package path
package com.rift.coad.catalog;

import com.rift.coad.catalog.rdf.CatalogEntry;
import com.rift.coad.catalog.rdf.CatalogOffering;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The catalog manager daemon.
 *
 * @author brett chaldecott
 */
public interface CatalogManagerDaemon extends Remote {
    
    /**
     * This method adds the entry to the catalog.
     *
     * @param entry The entry to add to th catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void addCatalogEntry(CatalogEntry entry) throws CatalogException, RemoteException;

    
    /**
     * This method is called to upate a catalog entry.
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void updateCatalogEntry(CatalogEntry entry) throws CatalogException, RemoteException;
    
    
    /**
     * This method is called to remove a entry from a catalog.
     *
     * @param id The id to remove.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void removeCatalogEntry(String catalogId) throws CatalogException, RemoteException;


    /**
     * This method lists the catalog entries.
     *
     * @return The list of catalog entries.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public List<String> listCatalogEntries() throws CatalogException, RemoteException;
    

    /**
     * This method returns the catalog entry.
     *
     * @param catalogId The id of the catalog entry.
     * @return The reference to the catalog entry.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public CatalogEntry getCatalogEntry(String catalogId) throws CatalogException, RemoteException;


    /**
     * This method returns the list of catalog entries
     *
     * @return The array of catalog entries.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public CatalogEntry[] getCatalogEntries() throws CatalogException, RemoteException;

    
    /**
     * This method adds the entry to the catalog.
     *
     * @param entry The entry to add to th catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void addCatalogOffering(CatalogOffering offering) throws CatalogException, RemoteException;


    /**
     * This method is called to upate a catalog entry.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void updateCatalogOffering(CatalogOffering offering) throws CatalogException, RemoteException;


    /**
     * This method is called to remove a offering from a catalog.
     *
     * @param id The id to remove.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public void removeCatalogOffering(String offeringId) throws CatalogException, RemoteException;


    /**
     * This method lists the catalog offerings.
     *
     * @return The list of catalog offerings.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public List<String> listCatalogOfferings() throws CatalogException, RemoteException;

    /**
     * This method gets the catalog offerings.
     *
     * @return An array of catalog offerings.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public CatalogOffering[] getCatalogOfferings() throws CatalogException, RemoteException;


    /**
     * This method returns the catalog information.
     *
     * @param offeringId The id of the offering to return.
     * @return The reference to the catalog.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public CatalogOffering getCatalogOffering(String offeringId) throws CatalogException, RemoteException;

}
