/*
 * 0200-Catalog: The catalog engine.
 * Copyright (C) 2010  2015 Burntjam
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
 * CatalogManagerDaemonImpl.java
 */

// package path
package com.rift.coad.catalog;


import com.rift.coad.audit.client.AuditLogger;
import com.rift.coad.catalog.rdf.CatalogEntry;
import com.rift.coad.catalog.rdf.CatalogOffering;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The implementation of the catalog manager daemon.
 *
 * @author brett chaldecott
 */
public class CatalogManagerDaemonImpl implements CatalogManagerDaemon, BeanRunnable {

    // class singletons variables
    private static Logger log = Logger.getLogger(CatalogManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            CatalogManagerDaemonImpl.class);

    // Private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();


    /**
     * The default constructor.
     */
    public CatalogManagerDaemonImpl() {
        
    }


    /**
     * This method adds an entry to the catalog.
     *
     * @param entry
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void addCatalogEntry(CatalogEntry entry) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();

            session.persist(entry);
            auditLog.complete(null,null,"Added the catalog entry [%s]", entry.toString());
        } catch (Exception ex) {
            log.error("Failed to add the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to add the catalog entry : " + ex.getMessage());
        }
    }


    /**
     * This method updates the catalog entry.
     *
     * @param entry The entry to update.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void updateCatalogEntry(CatalogEntry entry) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();

            session.persist(entry);
            auditLog.complete(null,null,"Updated the catalog entry [%s]", entry.toString());
        } catch (Exception ex) {
            log.error("Failed to update the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to update the catalog entry : " + ex.getMessage());
        }
    }


    /**
     * This method removes the catalog entry.
     *
     * @param catalogId The entry in the catalog to remove
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void removeCatalogEntry(String catalogId) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            CatalogEntry entry = session.get(CatalogEntry.class,
                    CatalogEntry.class.getName(), catalogId);
            session.remove(entry);
            auditLog.complete(null,null,"Remove the catalog entry [%s]", entry.toString());
        } catch (Exception ex) {
            log.error("Failed to remove the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to remove the catalog entry : " + ex.getMessage());
        }
    }

    /**
     * This method lists the catalog entries.
     *
     * @return The list of catalog entries.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public List<String> listCatalogEntries() throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntry> .}").
                    execute();
            List<String> catalogEntries = new ArrayList<String>();
            for (SPARQLResultRow row : entries) {
                CatalogEntry entry = row.get(0).cast(CatalogEntry.class);
                catalogEntries.add(entry.getId());
            }
            return catalogEntries;

        } catch (Exception ex) {
            log.error("Failed to list the catalog entries : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to list the catalog entries : " + ex.getMessage());
        }
    }


    /**
     * This method returns a catalog entry.
     *
     * @param catalogId The id of the catalog entry.
     * @return The reference to the retrieve catalog entry.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public CatalogEntry getCatalogEntry(String catalogId) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            return session.get(CatalogEntry.class,
                    CatalogEntry.class.getName(), catalogId);
        } catch (Exception ex) {
            log.error("Failed to get the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to get the catalog entry : " + ex.getMessage());
        }
    }



    /**
     * This method lists the catalog entries.
     *
     * @return The list of catalog entries.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public CatalogEntry[] getCatalogEntries() throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogEntry> .}").
                    execute();
            List<CatalogEntry> catalogEntries = new ArrayList<CatalogEntry>();
            for (SPARQLResultRow row : entries) {
                CatalogEntry entry = row.get(0).cast(CatalogEntry.class);
                catalogEntries.add(entry);
            }
            return catalogEntries.toArray(new CatalogEntry[0]);

        } catch (Exception ex) {
            log.error("Failed to list the catalog entries : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to list the catalog entries : " + ex.getMessage());
        }
    }


    /**
     * Add the catalog offering.
     *
     * @param offering The catalog offering.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void addCatalogOffering(CatalogOffering offering) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();

            session.persist(offering);
            auditLog.complete(null,null,"Added the catalog offering entry [%s]", offering.toString());
        } catch (Exception ex) {
            log.error("Failed to Added the catalog offering entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed Added the catalog offering entry : " + ex.getMessage());
        }
    }


    /**
     * This method updates the catalog offering.
     *
     * @param offering The offering to update.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void updateCatalogOffering(CatalogOffering offering) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();

            session.persist(offering);
            auditLog.complete(null,null,"Updated the catalog offering entry [%s]", offering.toString());
        } catch (Exception ex) {
            log.error("Failed to update the catalog offering entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed update the catalog offering entry : " + ex.getMessage());
        }
    }


    /**
     * This method removes the catalog offering entry.
     *
     * @param offeringId The id of the catalog offering to remove.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void removeCatalogOffering(String offeringId) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            CatalogOffering entry = session.get(CatalogOffering.class,
                    CatalogOffering.class.getName(), offeringId);
            session.remove(entry);
            auditLog.complete(null,null,"Remove the catalog offering entry [%s]", entry.toString());
        } catch (Exception ex) {
            log.error("Failed to remove the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to remove the catalog entry : " + ex.getMessage());
        }
    }


    /**
     * This method lists the catalog offerings.
     *
     * @return The list of catalog offering ids.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public List<String> listCatalogOfferings() throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOffering> .}").
                    execute();
            List<String> catalogOfferings = new ArrayList<String>();
            for (SPARQLResultRow row : entries) {
                CatalogOffering entry = row.get(0).cast(CatalogOffering.class);
                catalogOfferings.add(entry.getId());
            }
            return catalogOfferings;

        } catch (Exception ex) {
            log.error("Failed to list the catalog offerings : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to list the catalog offerings : " + ex.getMessage());
        }
    }


    /**
     * This method gets the catalog offerings.
     *
     * @return An array of catalog offerings.
     * @throws com.rift.coad.catalog.CatalogException
     * @throws java.rmi.RemoteException
     */
    public CatalogOffering[] getCatalogOfferings() throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            List<SPARQLResultRow> entries = session.createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s a <http://www.coadunation.net/schema/rdf/1.0/cataloginfo#CatalogOffering> .}").
                    execute();
            List<CatalogOffering> catalogEntries = new ArrayList<CatalogOffering>();
            for (SPARQLResultRow row : entries) {
                CatalogOffering entry = row.get(0).cast(CatalogOffering.class);
                catalogEntries.add(entry);
            }
            return catalogEntries.toArray(new CatalogOffering[0]);

        } catch (Exception ex) {
            log.error("Failed to list the catalog offerings : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to list the catalog offerings : " + ex.getMessage());
        }
    }


    /**
     * This method returns a catalog offering.
     *
     * @param offeringId The id of the catalog offering to remove.
     * @return The catalog offering to remove.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public CatalogOffering getCatalogOffering(String offeringId) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(
                    CatalogManagerDaemonImpl.class).getSession();
            return session.get(CatalogOffering.class,
                    CatalogOffering.class.getName(), offeringId);
        } catch (Exception ex) {
            log.error("Failed to get the catalog offering : " + ex.getMessage(),ex);
            throw new CatalogException
                    ("Failed to get the catalog offering : " + ex.getMessage());
        }
    }


    /**
     * The method that manages the back ground process.
     */
    public void process() {

        while(!state.isTerminated()) {

            // wait indefinitly
            state.monitor();
        }
        try {
            SemanticUtil.closeInstance(CatalogManagerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to close the semantic session manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     *  Terminate the processing
     */
    public void terminate() {
        state.terminate(true);
    }

}
