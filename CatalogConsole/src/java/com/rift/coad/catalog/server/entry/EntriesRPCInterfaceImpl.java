/*
 * EntriesRPCInterfaceImpl.java
 *
 * Created on 19 April 2010, 9:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.catalog.server.entry;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.catalog.CatalogManagerDaemon;
import com.rift.coad.catalog.client.entry.EntriesException;
import com.rift.coad.catalog.client.entry.EntriesRPCInterface;
import com.rift.coad.catalog.client.rdf.CatalogEntry;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The entries rpc interface implementation.
 *
 * @author brett chaldecott
 */
public class EntriesRPCInterfaceImpl extends RemoteServiceServlet implements
        EntriesRPCInterface {
    // private member variables
    private static Logger log = Logger.getLogger(EntriesRPCInterfaceImpl.class);


    /**
     * The constructor of the entries RPC interface.
     */
    public EntriesRPCInterfaceImpl() {
        
    }


    /**
     * This method returns a list of all the entries.
     *
     * @return The list of entries to retrieve.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public List<String> listEntries() throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            return daemon.listCatalogEntries();
        } catch (Exception ex) {
            log.error("Failed to list the entries : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to list the entries : " + ex.getMessage());
        }
    }


    /**
     * This method gets an entry.
     *
     * @param id The id of the entry to retrieve.
     * @return The reference to the entry.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public CatalogEntry getEntry(String id) throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            return (CatalogEntry)RDFCopy.copyToClient(daemon.getCatalogEntry(id));
        } catch (Exception ex) {
            log.error("Failed to get the entry : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to get the entry : " + ex.getMessage());
        }
    }


    /**
     * This method returns a list of all the entries.
     *
     * @return The list of entries to retrieve.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public CatalogEntry[] getEntries() throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            return (CatalogEntry[])RDFCopy.copyToClientArray(daemon.getCatalogEntries());
        } catch (Exception ex) {
            log.error("Failed to list the entries : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to list the entries : " + ex.getMessage());
        }
    }


    /**
     * This method adds a new category entry.
     *
     * @param entry The entry to add to the catalog
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void addEntry(CatalogEntry entry) throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.addCatalogEntry((com.rift.coad.catalog.rdf.CatalogEntry)RDFCopy.copyFromClient(entry));
        } catch (Exception ex) {
            log.error("Failed to add the entry : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to add the entry : " + ex.getMessage());
        }
    }


    /**
     * This method updates the entry.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void updateEntry(CatalogEntry entry) throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.updateCatalogEntry(
                    (com.rift.coad.catalog.rdf.CatalogEntry)RDFCopy.copyFromClient(entry));
        } catch (Exception ex) {
            log.error("Failed to update the entry : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to update the entry : " + ex.getMessage());
        }
    }


    /**
     * This entry removes the entry specified by the id.
     *
     * @param id The id of the entry to remove
     * @throws com.rift.coad.catalog.client.entry.EntriesException
     */
    public void removeEntry(String id) throws EntriesException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class, "catalog/CatalogManagerDaemon");
            daemon.removeCatalogEntry(id);
        } catch (Exception ex) {
            log.error("Failed to remove the entry : " + ex.getMessage(),ex);
            throw new EntriesException
                ("Failed to remove the entry : " + ex.getMessage());
        }
    }
}
