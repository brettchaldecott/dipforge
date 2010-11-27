/*
 * CoadunationTypeManagerConsole: The type management console.
 * Copyright (C) 2010  Rift IT Contracting
 *
 * Offeringstore.java
 */

// package path
package com.rift.coad.catalog.client.offering;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.catalog.client.rdf.CatalogOffering;
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
public class OfferingsStore {


    /**
     * The entries list handler
     */
    public class OfferingsListHandler implements AsyncCallback {

        /**
         * The default constructor for the entries listner handler.
         */
        public OfferingsListHandler() {
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
            CatalogOffering[] offeringArray = (CatalogOffering[])result;
            for (CatalogOffering offering : offeringArray) {
                offeringsIds.add(offering.getId());
                offerings.put(offering.getId(), offering);
            }
        }

    }


    
    // class singletons
    private static OfferingsStore singleton = null;

    // private member variables
    private List<String> offeringsIds = new ArrayList<String>();
    private Map<String,CatalogOffering> offerings = new HashMap<String,CatalogOffering>();
    

    /**
     * The private constructor
     */
    public OfferingsStore() {
        OfferingsRPCInterfaceConnector.getService().getOfferings(new OfferingsListHandler());
    }


    /**
     * This method initializes the store entries.
     */
    public static void init() {
        if (singleton == null) {
            singleton = new OfferingsStore();
        }
    }


    /**
     * This method returns instance of the entries store.
     *
     * @return The reference to teh entries tore instance.
     */
    public static OfferingsStore getInstance() {
        return singleton;
    }


    /**
     * The list of entries.
     *
     * @return The list catalog entries.
     */
    public Map<String,CatalogOffering> getOfferings() {
        return offerings;
    }
    
    
    /**
     * This method returns the catalog entry.
     * 
     * @param id The id of the catalog entry.
     * @return The catalog entry object reference.
     */
    public CatalogOffering getEntry(String id) {
        return offerings.get(id);
    }


    /**
     * This method is called to remove the entrie identified by the id.
     *
     * @param id The id to remove.
     */
    public void removeOffering(String id) {
        offerings.remove(id);
        offeringsIds.remove(id);
    }


    /**
     * This method is used to update the entries.
     *
     * @param entry The entry to update.
     */
    public void updateOffering(CatalogOffering entry) {
        if (!offerings.containsKey(entry.getId())) {
            offerings.put(entry.getId(), entry);
            offeringsIds.add(entry.getId());
        } else {
            offerings.put(entry.getId(), entry);
        }
    }


    /**
     * The list of catalog ids.
     * @return
     */
    public List<String> getOfferingsIds() {
        return offeringsIds;
    }



}
