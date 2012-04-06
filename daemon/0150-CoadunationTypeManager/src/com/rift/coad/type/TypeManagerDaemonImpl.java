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
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.audit.client.AuditLogger;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import com.rift.coad.rdf.semantic.util.RDFURIHelper;
import com.rift.coad.type.dto.RDFDataType;
import com.rift.coad.type.dto.ResourceDefinition;
import java.net.URI;
import java.util.Properties;

/**
 * This object is responsible for managing the types.
 *
 * @author brett chaldecott
 */
public class TypeManagerDaemonImpl implements TypeManagerDaemon {

    // class constants
    private final static String TYPE_FILE = "type_file";
    private final static String ONTOLOGY_MANAGER_CLASS = "ontology_manager_impl";
    private final static String DEFAULT_ONTOLOGY_MANAGER_CLASS =
            "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager";
    private static final String SCHEMA_DIR = "schema_dir";

    // class singletons
    private static Logger log = Logger.getLogger(TypeManagerDaemonImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            TypeManagerDaemonImpl.class);

    // private member variables
    private String typeFile;
    private String ontologyClass;
    private String schemaDir;

    /**
     * The default constructor of the type manager
     */
    public TypeManagerDaemonImpl() throws TypeManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    TypeManagerDaemonImpl.class);
            typeFile = config.getString(TYPE_FILE);
            ontologyClass = config.getString(ONTOLOGY_MANAGER_CLASS,
                    DEFAULT_ONTOLOGY_MANAGER_CLASS);
            schemaDir = config.getString(SCHEMA_DIR);
            

        } catch (Exception ex) {
            log.error("Failed to instantiate the type manager : " +
                    ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to instantiate the type manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method adds a new type.
     *
     * @param resource The resource
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void addType(ResourceDefinition resource) throws TypeManagerException {
        try {
            OntologySession session = this.getSession(resource.getNamespace());
            URI uri = new URI(resource.getNamespace() + "#" + resource.getLocalname());
            OntologyClass ontologyClass = session.createClass(uri);

            for (String key : resource.getProperties().keySet()) {
                RDFDataType type = resource.getProperties().get(key);
                URI propertyURI = 
                        new URI(type.getNamespace() + "#" + type.getLocalName());
                OntologyProperty property = null;
                if (session.hasProperty(propertyURI)) {
                    property = session.getProperty(propertyURI);
                } else {
                    property = session.createProperty(propertyURI);
                }
                if (type.getTypeUri() != null) {
                    if (XSDDataDictionary.isBasicTypeByURI(
                            type.getTypeUri())) {
                        log.info("Set the property uri [" + propertyURI + 
                                "] to [" + type.getTypeUri() + "]");
                        property.setType(XSDDataDictionary.getTypeByURI(
                                type.getTypeUri()));
                    } else {
                        property.setType(session.getClass(
                                new URI(type.getTypeUri())));
                    }
                }
                ontologyClass.addProperty(property);
            }
            this.persist(session,resource.getNamespace());
            auditLog.complete("Add new type %s",resource.toString());
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to add the type : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the types.
     *
     * @param resource The resource
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void updateType(ResourceDefinition resource) throws TypeManagerException {
        URI uri = null; 
        try {
            OntologySession session = this.getSession(resource.getNamespace());
            uri = new URI(resource.getNamespace() + "#" + resource.getLocalname());
            log.info("Add the property [" + uri + "]");
            if (session.hasClass(uri)) {
                session.removeClass(uri);
            }
            OntologyClass ontologyClass = session.createClass(uri);
            for (String key : resource.getProperties().keySet()) {
                RDFDataType type = resource.getProperties().get(key);
                URI propertyURI = 
                        new URI(type.getNamespace() + "#" + type.getLocalName());
                log.info("Add the property [" + propertyURI + "]");
                OntologyProperty property = null;
                if (session.hasProperty(propertyURI)) {
                    property = session.getProperty(propertyURI);
                } else {
                    property = session.createProperty(propertyURI);
                }
                if (type.getTypeUri() != null) {
                    if (XSDDataDictionary.isBasicTypeByURI(
                            type.getTypeUri())) {
                        log.info("Set the property uri [" + propertyURI + 
                                "] to [" + type.getTypeUri() + "]");
                        property.setType(XSDDataDictionary.getTypeByURI(
                                type.getTypeUri()));
                    } else {
                        property.setType(session.getClass(
                                new URI(type.getTypeUri())));
                    }
                }
                ontologyClass.addProperty(property);
            }
            this.persist(session,resource.getNamespace());
            auditLog.complete("Updated a type %s",resource.toString());
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the type [" + uri + "] : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to update the type [" + uri + "] : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to delete the type identified by the resource information.
     *
     * @param resource The resource to delete from the database.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public void deleteType(ResourceDefinition resource) throws TypeManagerException {
        try {
            OntologySession session = this.getSession(resource.getNamespace());
            URI uri = new URI(resource.getNamespace() + "#" + resource.getLocalname());
            session.removeClass(uri);
            this.persist(session,resource.getNamespace());
            auditLog.complete("Remove type %s",resource.toString());
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to remove the type : " + ex.getMessage(),ex);
        }
    }



    /**
     * This method returns the resource base.
     *
     * @param typeId The type of id.
     * @return The base resource to retrieve.
     * @throws com.rift.coad.type.TypeManagerException
     */
    public ResourceDefinition getType(String uriStr) throws TypeManagerException {
        try {
            RDFURIHelper helper = new RDFURIHelper(uriStr);
            OntologySession session = this.getSession(helper.getNamespace());
            URI uri = new URI(uriStr);
            OntologyClass ontologyClass = session.getClass(uri);
            ResourceDefinition result = new ResourceDefinition();
            result.setNamespace(helper.getNamespace());
            result.setLocalname(helper.getLocalName());
            for (OntologyProperty property : ontologyClass.listProperties()) {
                PersistanceIdentifier identifier = PersistanceIdentifier.getInstance(
                        property.getNamespace(),property.getLocalname());
                RDFDataType dataType = new RDFDataType(property.getNamespace(),property.getLocalname());
                if (property.getType() != null) {
                    dataType.setTypeUri(property.getType().getURI().toString());
                }
                result.getProperties().put(identifier.toURI().toString(),dataType) ;
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to get the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to get the type : " + ex.getMessage(),ex);

        }
    }
    

    /**
     * This method returns the list of types scoped by the uri.
     *
     * @param uri The uri to scope the list by.
     * @return The list of resources
     * @throws com.rift.coad.type.TypeManagerException
     */
    public List<RDFDataType> listTypes(String[] namespaces) throws TypeManagerException {
        try {
            List<RDFDataType> types = new ArrayList<RDFDataType>();
            for (String namespace: namespaces) {
                OntologySession session = this.getSession(namespace);
                List<OntologyClass> ontClasses = session.listClasses();
                for (OntologyClass ontClass : ontClasses ) {
                    OntologyProperty property = session.getProperty(ontClass.getURI());
                    RDFDataType dataType = new RDFDataType(property.getNamespace(),property.getLocalname());
                    if (property.getType() != null) {
                        dataType.setTypeUri(property.getType().getURI().toString());
                    }
                    types.add(dataType);
                }
            }
            return types;
        } catch (Exception ex) {
            log.error("Failed to get the type : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to get the type : " + ex.getMessage(),ex);

        }
    }


    /**
     * This method returns the active session for the Semantic information initialized
     * off of the data file.
     * @return The active session.
     * @throws com.rift.coad.type.TypeManagerException
     */
    private OntologySession getSession(String namespace) throws TypeManagerException {
        try {
            Properties properties = new Properties();
            properties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                    "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
            File ontologyFile = new File(this.schemaDir,this.stripNamespace(namespace) + ".xml");
            if (ontologyFile.isFile()) {
                FileInputStream in = new FileInputStream(ontologyFile);
                byte[] buffer = new byte[(int)ontologyFile.length()];
                in.read(buffer);
                in.close();
                properties.put(OntologyConstants.ONTOLOGY_CONTENTS, new String(buffer));
            }
            OntologyManager ontologyManager =
                    (OntologyManager)OntologyManagerFactory.init(properties);
            return (OntologySession)ontologyManager.getSession();
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
    private void persist(OntologySession ontologySession,String namespace) throws TypeManagerException {
        try {
            persist(namespace, ontologySession.dumpXML());
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to persist the changes : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to persist the changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to persist the changes.
     *
     * @param namespace The namespace to persist for.
     * @param contents The contents to persist.
     * @throws com.rift.coad.type.TypeManagerException
     */
    private void persist(String namespace, String contents) throws TypeManagerException {
        try {
            File outFile = new File(this.schemaDir,this.stripNamespace(namespace) + ".xml");
            String path = outFile.getPath();
            File outDir = new File(path.substring(0,path.lastIndexOf("/")));
            outDir.mkdirs();
            FileOutputStream out = new FileOutputStream(outFile);
            out.write(contents.getBytes());
            out.close();
        } catch (Exception ex) {
            log.error("Failed to persist the changes : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Failed to persist the changes : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method attempts to load the ontology from the file.
     *
     * @return The string containing the ontology
     * @throws TypeManagerException
     */
    private String loadOntology(String namespace) throws TypeManagerException {
        try {
            File ontFile = new File(this.schemaDir,this.stripNamespace(namespace) + ".xml");
            if (ontFile.isFile()) {
                byte[] buffer = new byte[(int)ontFile.length()];
                FileInputStream in = new FileInputStream(ontFile);
                in.read(buffer);
                in.close();
                return new String(buffer);
            }
            return null;
        } catch (Exception ex) {
            log.error("Attempt to load the ontology : " + ex.getMessage(),ex);
            throw new TypeManagerException
                    ("Attempt to load the ontology : " + ex.getMessage(),ex);
        }
    }


    /**
     * The stripped namespace.
     *
     * @param namespace The name space.
     * @return The string containing the stripped value.
     */
    private String stripNamespace(String namespace) {
        String result = namespace.trim();
        if (result.startsWith("http://")) {
            result = result.substring("http://".length());
        }
        return result.substring(result.indexOf("/"));
    }


    /**
     * This method is called to import a given file
     *
     * @param namespace The name space.
     * @param xml The xml.
     * @throws TypeManagerException
     * @throws RemoteException
     */
    public void importTypes(String namespace, String xml) throws TypeManagerException {
        try {
            persist(namespace, xml);
        } catch (TypeManagerException ex) {
            log.error("Failed to import the types : " +
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to import the types : " +
                    ex.getMessage(),ex);
            throw new TypeManagerException("Failed to import the types : " + ex.getMessage(),ex);
        }
    }


    /**
     * The export of the types.
     *
     * @param namespace The namespace to export.
     * @return The string containing the export.
     * @throws TypeManagerException
     */
    public String exportTypes(String namespace) throws TypeManagerException {
        try {
            return loadOntology(namespace);
        } catch (TypeManagerException ex) {
            log.error("Failed to export the types : " +
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to export the types : " +
                    ex.getMessage(),ex);
            throw new TypeManagerException("Failed to export the types : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for dropping the ontology information associated with a namespace.
     *
     * @param namespace The namespace to drop.
     * @throws TypeManagerException
     */
    public void dropTypes(String namespace) throws TypeManagerException {
        try {
            File ontFile = new File(this.schemaDir,this.stripNamespace(namespace) + ".xml");
            if (ontFile.isFile()) {
                ontFile.delete();
            } else {
                throw new TypeManagerException("The ontology types do not exist");
            }
        } catch (TypeManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to drop the types : " +
                    ex.getMessage(),ex);
            throw new TypeManagerException("Failed to drop the types : " +
                    ex.getMessage(),ex);
        }
    }

}
