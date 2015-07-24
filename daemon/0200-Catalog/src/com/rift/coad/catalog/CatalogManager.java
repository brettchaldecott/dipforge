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
 * CatalogManager.java
 */

// package path
package com.rift.coad.catalog;

// java imports
import com.rift.coad.audit.client.AuditLogger;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.semantic.config.Basic;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This object is the management object for the catalog.
 *
 * @author brett chaldecott
 */
public class CatalogManager implements CatalogManagerMBean {

    // class singletons variables
    private static Logger log = Logger.getLogger(CatalogManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            CatalogManagerDaemonImpl.class);


    /**
     * This method returns the version information.
     *
     * @return The containing the version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the of the catalog manager.
     *
     * @return The name of the catalog manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * The description of this object.
     *
     * @return The string containing the description of this object.
     */
    public String getDescription() {
        return "The catalog manager.";
    }


    /**
     * This method adds a catalog entry extracting the information from the XML.
     *
     * @param xml The xml to extract the information from.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void addCatalogEntryFromXML(String xml) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(CatalogManagerDaemonImpl.class).getSession();
            session.persist(xml);
            auditLog.complete(null,null,"Added catalog entry from an XML definition [%s]",xml);
        } catch (Exception ex) {
            log.error("Failed to added catalog entry from an XML definition : " + ex.getMessage(), ex);
            throw new CatalogException(
                    "Failed to added catalog entry from an XML definition : " + ex.getMessage());
        }
    }


    /**
     * This method is called to update the catalog entry from xml.
     *
     * @param xml The string containing the XML information.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void updateCatalogEntryFromXML(String xml) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(CatalogManagerDaemonImpl.class).getSession();
            session.persist(xml);
            auditLog.complete(null,null,"Updated catalog entry from an XML definition [%s]",xml);
        } catch (Exception ex) {
            log.error("Failed to update catalog entry from an XML definition : " + ex.getMessage(), ex);
            throw new CatalogException(
                    "Failed to update catalog entry from an XML definition : " + ex.getMessage());
        }
    }


    /**
     * This method removes the catalog entry.
     *
     * @param catalogId The id of the catalog entry to remove.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void removeCatalogEntry(String catalogId) throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            daemon.removeCatalogEntry(catalogId);
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to remove the catalog entry : " + ex.getMessage());
        }
    }


    /**
     * This method lists the catagories.
     *
     * @return The list of catagories.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public List<String> listCatalogEntries() throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            return daemon.listCatalogEntries();
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of catalog entries : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to retrieve a list of catalog entries : " + ex.getMessage());
        }
    }


    /**
     * This method returns the catalog entry as xml.
     *
     * @param catalogId The id of the catalog entry to retrieve.
     * @return The string containing the catalog entry information.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public String getCatalogEntryAsXML(String catalogId) throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            Session result = Basic.initSessionManager().getSession();
            result.persist(daemon.getCatalogEntry(catalogId));
            return result.dump(RDFFormats.XML_ABBREV);
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the catalog entry : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to retrieve the catalog entry : " + ex.getMessage());
        }
    }


    /**
     * This method adds a catalog offering from xml.
     *
     * @param xml The xml containing the catalog offering
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void addCatalogOfferingFromXML(String xml) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(CatalogManagerDaemonImpl.class).getSession();
            session.persist(xml);
            auditLog.complete(null,null,"Added a catalog offering from an XML definition [%s]",xml);
        } catch (Exception ex) {
            log.error("Failed to added a catalog offering from an XML definition : " + ex.getMessage(), ex);
            throw new CatalogException(
                    "Failed to added a catalog offering from an XML definition : " + ex.getMessage());
        }
    }

    
    /**
     * This method updates the catalog offering information.
     *
     * @param xml Thexml containing the information
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void updateCatalogOffering(String xml) throws CatalogException {
        try {
            Session session = SemanticUtil.getInstance(CatalogManagerDaemonImpl.class).getSession();
            session.persist(xml);
            auditLog.complete(null,null,"Updated a catalog offering from an XML definition [%s]",xml);
        } catch (Exception ex) {
            log.error("Failed to update a catalog offering from an XML definition : " + ex.getMessage(), ex);
            throw new CatalogException(
                    "Failed to update a catalog offering from an XML definition : " + ex.getMessage());
        }
    }


    /**
     * This method removes the catalog offering.
     *
     * @param offeringId The string containing the id of the offering.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public void removeCatalogOffering(String offeringId) throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            daemon.removeCatalogOffering(offeringId);
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the catalog offering : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to remove the catalog offering : " + ex.getMessage());
        }
    }


    /**
     * This method returns the list of catalogs.
     *
     * @return The list of catalog offerings.
     * @throws com.rift.coad.catalog.CatalogException
     */
    public List<String> listCatalogOfferings() throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            return daemon.listCatalogOfferings();
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the catalog offering : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to remove the catalog offering : " + ex.getMessage());
        }
    }


    /**
     *
     * @param offeringId
     * @return
     * @throws com.rift.coad.catalog.CatalogException
     */
    public String getCatalogOfferingAsXML(String offeringId) throws CatalogException {
        try {
            CatalogManagerDaemon daemon = (CatalogManagerDaemon)ConnectionManager.getInstance().
                    getConnection(CatalogManagerDaemon.class,
                    "java:comp/env/bean/catalog/CatalogManagerDaemon");
            Session result = Basic.initSessionManager().getSession();
            result.persist(daemon.getCatalogOffering(offeringId));
            return result.dump(RDFFormats.XML_ABBREV);
        } catch (CatalogException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the catalog offering : " + ex.getMessage(),ex);
            throw new CatalogException
                ("Failed to retrieve the catalog offering : " + ex.getMessage());
        }
    }

}
