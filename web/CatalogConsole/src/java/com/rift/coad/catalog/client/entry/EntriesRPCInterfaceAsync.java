/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * EntriesStore.java
 */

package com.rift.coad.catalog.client.entry;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.catalog.client.rdf.CatalogEntry;


/**
 * This object is the async interface.
 *
 * @author brett chaldecott
 */
public interface EntriesRPCInterfaceAsync {

    
    /**
     * The async version of the list entries method.
     * 
     * @param asyncCallback The list of async call back methods.
     */
    public void listEntries(AsyncCallback asyncCallback);

    
    /**
     * This async get entry method.
     * 
     * @param id The id of the entry to retrieve.
     * @param asyncCallback The async call back.
     */
    public void getEntry(java.lang.String id, AsyncCallback asyncCallback);


    /**
     * The async version of the list entries method.
     *
     * @param asyncCallback The list of async call back methods.
     */
    public void getEntries(AsyncCallback asyncCallback);


    /**
     * This method adds the entry.
     *
     * @param entry The entry to add.
     * @param asyncCallback The call back method.
     */
    public abstract void addEntry(com.rift.coad.catalog.client.rdf.CatalogEntry entry, AsyncCallback asyncCallback);


    /**
     * This method updates the entry.
     *
     * @param entry The entry to update.
     * @param asyncCallback The call back object.
     */
    public abstract void updateEntry(com.rift.coad.catalog.client.rdf.CatalogEntry entry, AsyncCallback asyncCallback);


    /**
     * This method removes the entry from the catalog.
     *
     * @param id The id of the entry to remove.
     * @param asyncCallback
     */
    public abstract void removeEntry(java.lang.String id, AsyncCallback asyncCallback);

    


}
