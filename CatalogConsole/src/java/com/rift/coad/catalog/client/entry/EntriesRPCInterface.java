/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * EntriesStore.java
 */


// the package path
package com.rift.coad.catalog.client.entry;

// 
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.catalog.client.rdf.CatalogEntry;
import java.util.List;

/**
 * This interface defines the methods to access the category entries information.
 *
 * @author brett chaldecott
 */
public interface EntriesRPCInterface extends RemoteService{

    /**
     * This method returns a list of all the entries.
     *
     * @return The list of entries.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public List<String> listEntries() throws EntriesException;


    /**
     * This method retrieves the catalog identified by the id.
     *
     * @param id The id of the catalog.
     * @return The reference to the catalog.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public CatalogEntry getEntry(String id) throws EntriesException;


    /**
     * This method returns the list of entries.
     *
     * @return The list of entries.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public CatalogEntry[] getEntries() throws EntriesException;


    /**
     * This method adds a new entry.
     *
     * @param entry The entry to add to the catalog.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void addEntry(CatalogEntry entry) throws EntriesException;


    /**
     * The update entry.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void updateEntry(CatalogEntry entry) throws EntriesException;


    /**
     * This method removes the entry.
     *
     * @param id The id of the entry to remove.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void removeEntry(String id) throws EntriesException;
}
