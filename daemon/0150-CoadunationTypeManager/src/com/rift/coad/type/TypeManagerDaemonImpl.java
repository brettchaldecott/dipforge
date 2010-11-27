/*
 * CoadunationTypeManage: The client library for the type manager
 * Copyright (C) 2009  Rift IT Contracting
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
 * TypeManagerDaemonImpl.java
 */

// package path
package com.rift.coad.type;

// java imports
import java.rmi.RemoteException;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.objmapping.resource.ResourceBase;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.audit.client.rdf.AuditLogger;
import com.rift.coad.audit.client.rdf.AuditTrail;

/**
 * This object is responsible for managing the types.
 *
 * @author brett chaldecott
 */
public class TypeManagerDaemonImpl implements TypeManagerDaemon {

    // class constants
    private final static String TYPE_FILE = "type_file";

    // class singletons
    private static Logger log = Logger.getLogger(TypeManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            TypeManagerDaemonImpl.class);

    // private member variables
    private String typeFile;

    /**
     * The default constructor of the type manager
     */
    public TypeManagerDaemonImpl() throws TypeManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    TypeManagerDaemonImpl.class);
            typeFile = config.getString(TYPE_FILE);
        } catch (Exception ex) {
            log.error("Failed to instantiate the type manager : " +
                    ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to instantiate the type manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for adding the type.
     * @param xml
     * @throws com.rift.coad.type.TypeManagerException
     * @throws java.rmi.RemoteException
     */
    public void addType(String xml) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.persist(xml);
            this.persist(session);
            auditLog.create("Add new type via xml").complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to add the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds a new type.
     *
     * @param resource The resource
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void addType(ResourceBase resource) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.persist(resource);
            this.persist(session);
            auditLog.create("Add new type %s",resource.toString()).
                    addData(resource).complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to add the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the type.
     *
     * @param xml This method updates the type based on the xml string
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void updateType(String xml) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.persist(xml);
            this.persist(session);
            auditLog.create("Update type information via xml.").complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to update the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the types.
     *
     * @param resource The resource
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void updateType(ResourceBase resource) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.persist(resource);
            this.persist(session);
            auditLog.create("Update a type %s",resource.toString()).
                    addData(resource).complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to update the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for deleting the type identified by the xml
     *
     * @param xml The XML that identifies the type to delete.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void deleteType(String xml) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.remove(xml);
            this.persist(session);
            auditLog.create("Delete a type identifified vai xml").complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to remove the type : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method is called to delete the type identified by the resource information.
     *
     * @param resource The resource to delete from the database.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void deleteType(ResourceBase resource) throws TypeManagerException {
        try {
            Session session = this.getSession();
            session.remove(resource);
            this.persist(session);
            auditLog.create("Delete a type %s",resource.toString()).
                    addData(resource).complete();
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to remove the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to retrieve the type from the store.
     * @return The string containing the rdf type information
     * @throws com.rift.coad.type.TypeManagerException
     */
    public String getTypes() throws TypeManagerException {
        try {
            Session session = this.getSession();
            return session.dump(RDFFormats.XML_ABBREV);
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the type information : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to get the type information : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the resource base.
     *
     * @param typeId The type of id.
     * @return The base resource to retrieve.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public ResourceBase getType(String typeId) throws TypeManagerException {
        try {
            Session session = this.getSession();
            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery("SELECT ?s WHERE { " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?typeId . " +
                    "FILTER (?typeId = ${typeId}) } ").setString("typeId", typeId)
                    .execute();
            for (SPARQLResultRow row : entries) {
                return row.get(0).cast(ResourceBase.class);
            }
        } catch (Exception ex) {
            log.error("Failed to get the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to get the type : " + ex.getMessage(),ex);

        }
        throw new TypeManagerException
                    ("Type was not found : " + typeId);
    }
    

    /**
     * This method returns the list of types scoped by the uri.
     *
     * @param uri The uri to scope the list by.
     * @return The list of resources
     * @throws com.rift.coad.type.TypeManagerException
     */
    public List<ResourceBase> listTypes(String[] uris) throws TypeManagerException {
        try {
            String query = "SELECT ?s WHERE {";
            for (String uri: uris) {
                query += "? a <" + uri + "> . ";
            }
            query += "}";
            List<SPARQLResultRow> rows = getSession().createSPARQLQuery(query).execute();
            List<ResourceBase> result = new ArrayList<ResourceBase>();
            for (SPARQLResultRow row : rows) {
                ResourceBase resource = row.get(0).cast(ResourceBase.class);
                if (resource.getAssociatedObject() != null) {
                    continue;
                }
                result.add(resource);
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to list the types : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of types that are implementations of the given basic types.
     *
     * @param basicTypes The list of basic types.
     * @return The list of types.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public List<ResourceBase> listTypesByBasicType(String[] basicTypes) throws TypeManagerException {
        try {
            Session session = this.getSession();
            String search = "SELECT ?s ?typeId WHERE { " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#IdForDataType> ?typeId . " +
                    "?s <http://www.coadunation.net/schema/rdf/1.0/base#BasicTypeId> ?basicTypeId . " +
                    "FILTER (";
            String seperator = "";
            for (String basicType : basicTypes) {

                search += seperator + " ?basicTypeId = \""+ basicType +"\" ";
                seperator = "||";
            }
            search += ") }";

            List<SPARQLResultRow> entries = session.
                    createSPARQLQuery(search).execute();
            List<ResourceBase> rows = new ArrayList<ResourceBase>();
            for (SPARQLResultRow row : entries) {
                ResourceBase resource = row.get(0).cast(ResourceBase.class);
                if (resource.getAssociatedObject() != null) {
                    continue;
                }
                rows.add(resource);
            }
            return rows;
        } catch (Exception ex) {
            log.error("Failed to list the types : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to list the types : " + ex.getMessage(),ex);

        }
    }


    /**
     * This method returns the active session for the Semantic information initialized
     * off of the data file.
     * @return The active session.
     * @throws com.rift.coad.type.TypeManagerException
     */
    private Session getSession() throws TypeManagerException {
        try {
            Session result = Basic.initSessionManager().getSession();
            File source = new File(this.typeFile);
            if (source.isFile()) {
                FileInputStream in = new FileInputStream(source);
                result.persist(in);
                in.close();
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to create a new session : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to create a new session : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to persist the changes.
     *
     * @param session The session to persist.
     * @throws com.rift.coad.type.TypeManagerException
     */
    private void persist(Session session) throws TypeManagerException {
        try {
            File outFile = new File(this.typeFile);
            FileOutputStream out = new FileOutputStream(outFile);
            session.dump(out, RDFFormats.XML_ABBREV);
            out.close();
        } catch (Exception ex) {
            log.error("Failed to persist the changes : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to persist the changes : " + ex.getMessage(),ex);
        }
    }

    

}
