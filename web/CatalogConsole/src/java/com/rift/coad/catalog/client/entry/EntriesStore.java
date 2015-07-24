/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  2015 Burntjam
 *
 * EntriesStore.java
 */

// package path
package com.rift.coad.catalog.client.entry;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.catalog.client.rdf.CatalogEntry;
import com.smartgwt.client.util.SC;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This object is responsible for storing the catagories entries.
 *
 * @author brett chaldecott
 */
public class EntriesStore {


    /**
     * The entries list handler
     */
    public class EntriesListHandler implements AsyncCallback {

        /**
         * The default constructor for the entries listner handler.
         */
        public EntriesListHandler() {
        }


        /**
         * This method deals with a failure
         *
         * @param caught The exception that has been caught.
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to load the entries list : " + caught.getMessage());
        }


        /**
         * This method deals with a success.
         *
         * @param result The result.
         */
        public void onSuccess(Object result) {
            CatalogEntry[] entriesArray = (CatalogEntry[])result;
            for (CatalogEntry entry : entriesArray) {
                entryIds.add(entry.getId());
                entries.put(entry.getId(), entry);
            }
        }

    }


    
    // class singletons
    private static EntriesStore singleton = null;

    // private member variables
    private List<String> entryIds = new ArrayList<String>();
    private Map<String,CatalogEntry> entries = new HashMap<String,CatalogEntry>();
    

    /**
     * The private constructor
     */
    public EntriesStore() {
        EntriesRPCInterfaceConnector.getService().getEntries(new EntriesListHandler());
    }


    /**
     * This method initializes the store entries.
     */
    public static void init() {
        if (singleton == null) {
            singleton = new EntriesStore();
        }
    }


    /**
     * This method returns instance of the entries store.
     *
     * @return The reference to teh entries tore instance.
     */
    public static EntriesStore getInstance() {
        return singleton;
    }


    /**
     * The list of entries.
     *
     * @return The list catalog entries.
     */
    public Map<String,CatalogEntry> getEntries() {
        return entries;
    }
    
    
    /**
     * This method returns the catalog entry.
     * 
     * @param id The id of the catalog entry.
     * @return The catalog entry object reference.
     */
    public CatalogEntry getEntry(String id) {
        return entries.get(id);
    }


    /**
     * This method is called to remove the entrie identified by the id.
     *
     * @param id The id to remove.
     */
    public void removeEntry(String id) {
        entries.remove(id);
        entryIds.remove(id);
    }


    /**
     * This method is used to update the entries.
     *
     * @param entry The entry to update.
     */
    public void updateEntry(CatalogEntry entry) {
        if (!entries.containsKey(entry.getId())) {
            entries.put(entry.getId(), entry);
            entryIds.add(entry.getId());
        } else {
            entries.put(entry.getId(), entry);
        }
    }


    /**
     * The list of catalog ids.
     * @return
     */
    public List<String> getEntryIds() {
        return entryIds;
    }



}
