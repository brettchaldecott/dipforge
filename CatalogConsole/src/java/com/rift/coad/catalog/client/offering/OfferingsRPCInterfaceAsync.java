/*
 * OfferingsRPCInterfaceAsync.java
 *
 * Created on 20 July 2010, 4:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.catalog.client.offering;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.catalog.client.rdf.CatalogOffering;


/**
 * The offering rpce interface.
 *
 * @author brett chaldecott
 */
public interface OfferingsRPCInterfaceAsync {

    /**
     * This method gets the offering.
     * 
     * @param asyncCallback The async call back.
     * @throws com.rift.coad.catalog.client.offering.OfferingException
     */
    public void getOfferings(AsyncCallback<CatalogOffering[]> asyncCallback);


    /**
     * This is the ascnyc method for the adding of offerings.
     *
     * @param entry The offering to be added.
     * @param asyncCallback The call back object.
     */
    public abstract void addOffering(com.rift.coad.catalog.client.rdf.CatalogOffering offering, AsyncCallback asyncCallback);


    /**
     * This method is responsible for updating an existing offering.
     * @param offering The offering to be udated.
     * @param asyncCallback The object containing the results of the call.
     */
    public abstract void updateOffering(com.rift.coad.catalog.client.rdf.CatalogOffering offering, AsyncCallback asyncCallback);


    /**
     * This method removes and offering.
     *
     * @param id The id of the offering to remove.
     * @param asyncCallback The object containing the result of the call.
     */
    public abstract void removeOffering(java.lang.String id, AsyncCallback asyncCallback);
    
}
