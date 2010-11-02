/*
 * OfferingsRPCInterfaceImpl.java
 *
 * Created on 20 July 2010, 4:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.catalog.server.offering;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.catalog.CatalogManagerDaemon;
import com.rift.coad.catalog.client.offering.OfferingException;
import com.rift.coad.catalog.client.offering.OfferingsRPCInterface;
import com.rift.coad.catalog.client.rdf.CatalogOffering;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.util.connection.ConnectionManager;
import org.apache.log4j.Logger;

/**
 * This class providees access to the offerings information.
 *
 * @author brett chaldecott
 */
public class OfferingsRPCInterfaceImpl extends RemoteServiceServlet implements
        OfferingsRPCInterface {

    // private member variables
    private static Logger log = Logger.getLogger(OfferingsRPCInterfaceImpl.class);

    /**
     * The default constructor.
     */
    public OfferingsRPCInterfaceImpl() {
    }
    
    
    /**
     * This method returns the offering information.
     * 
     * @return The reference to the offerings.
     * @throws com.rift.coad.catalog.client.offering.OfferingException
     */
    public CatalogOffering[] getOfferings() throws OfferingException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            return (CatalogOffering[])RDFCopy.copyToClientArray(daemon.getCatalogOfferings());
        } catch (Exception ex) {
            log.error("Failed to list the offerings : " + ex.getMessage(),ex);
            throw new OfferingException
                ("Failed to list the offerings : " + ex.getMessage());
        }
    }


    /**
     * This method adds the offering information.
     *
     * @param entry The reference to the offering.
     * @throws com.rift.coad.catalog.client.offering.OfferingException
     */
    public void addOffering(CatalogOffering entry) throws OfferingException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.addCatalogOffering((com.rift.coad.catalog.rdf.CatalogOffering)RDFCopy.copyFromClient(entry));
        } catch (Exception ex) {
            log.error("Failed to add an offering : " + ex.getMessage(),ex);
            throw new OfferingException
                ("Failed to add an offering : " + ex.getMessage());
        }
    }


    /**
     * This method updates the offering information.
     * @param entry The entry to update the offering on.
     * @throws com.rift.coad.catalog.client.offering.OfferingException
     */
    public void updateOffering(CatalogOffering entry) throws OfferingException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.updateCatalogOffering((com.rift.coad.catalog.rdf.CatalogOffering)RDFCopy.copyFromClient(entry));
        } catch (Exception ex) {
            log.error("Failed to update an offering : " + ex.getMessage(),ex);
            throw new OfferingException
                ("Failed to update an offering : " + ex.getMessage());
        }
    }


    /**
     * This method removes the offering.
     *
     * @param id The id of the offering to remove.
     * @throws com.rift.coad.catalog.client.offering.OfferingException
     */
    public void removeOffering(String id) throws OfferingException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.removeCatalogOffering(id);
        } catch (Exception ex) {
            log.error("Failed to remove an offering : " + ex.getMessage(),ex);
            throw new OfferingException
                ("Failed to remove an offering : " + ex.getMessage());
        }
    }
}
