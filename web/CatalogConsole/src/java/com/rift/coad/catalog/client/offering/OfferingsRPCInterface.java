/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * EntriesStore.java
 */

// package path
package com.rift.coad.catalog.client.offering;

// imports
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.catalog.client.rdf.CatalogOffering;

/**
 * This interface provides access to the offerings.
 *
 * @author brett chaldecott
 */
public interface OfferingsRPCInterface extends RemoteService{
    /**
     * This method returns the list of entries.
     *
     * @return The list of entries.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public CatalogOffering[] getOfferings() throws OfferingException;


    /**
     * This method adds a new offering.
     *
     * @param entry The entry to add to the catalog.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void addOffering(CatalogOffering entry) throws OfferingException;


    /**
     * The update offering.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void updateOffering(CatalogOffering entry) throws OfferingException;


    /**
     * This method removes the offering.
     *
     * @param id The id of the entry to remove.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void removeOffering(String id) throws OfferingException;
}
