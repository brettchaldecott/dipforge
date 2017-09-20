/*
 * Dipforge: Common libraries.
 * Copyright (C) Thu Nov 03 14:05:39 SAST 2011 Rift IT Contracting
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
 * RDF.groovy 
 * @author brett chaldecott
 */

package com.dipforge.semantic


import groovy.json.*;
import org.apache.log4j.Logger;
import java.net.URI;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.RDFConstants;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;


/**
 * This object provides the access to the RDF layer.
 * 
 * @author brett chaldecott
 */
class RDF {
    // private member variables    
    static def log = Logger.getLogger("com.dipforge.semantic.RDF");
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def create(String type) {
        return createOrm(type)
    }
    
        
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def createOrm(String type) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def instance = createOrm(session,type);
            return instance
        } catch (Exception ex) {
            log.error("Failed to create the type [${type}] because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def createMap(String type) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def instance = createMap(session,type);
            return instance
        } catch (Exception ex) {
            log.error("Failed to create the type [${type}] because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def create(Session session, String type) {
        return createOrm(session,type)
    }
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def createOrm(Session session, String type) {
        try {
            def ontology = session.getOntologySession()
            def classDef = ontology.getClass(new URI(type))
            def typeBuilder = new RDFTypeBuilder(classDef,session)
            def instance = typeBuilder.getTypeInstance()
            return instance
        } catch (Exception ex) {
            log.error("Failed to create the type [${type}] because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param type The URI of the type.
     */
    static def createMap(Session session, String type) {
        try {
            def ontology = session.getOntologySession()
            def classDef = ontology.getClass(new URI(type))
            def typeBuilder = new RDFMapBuilder(classDef,session)
            def instance = typeBuilder.getDataMap()
            return instance
        } catch (Exception ex) {
            log.error("Failed to create the type [${type}] because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param uri The uri of the entry to retrieve.
     */
    static def getFromStore(String uri) {
        return getOrmFromStore(uri)
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param uri The uri of the entry to retrieve.
     */
    static def getOrmFromStore(String uri) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }

            Session session = SemanticUtil.getInstance(configClass).getSession();
            Resource resource = session.get(Resource.class,uri)
            
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            def typeResource = resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            def result = createOrm(session,typeResource.getURI().toString());
            result.builder.populateType(resource);
            return result
        } catch (Exception ex) {
            log.error("Failed to create the type because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param uri The uri of the entry to retrieve.
     */
    static def getMapFromStore(String uri) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }

            Session session = SemanticUtil.getInstance(configClass).getSession();
            Resource resource = session.get(Resource.class,uri)
            
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            def typeResource = resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            def result = createMap(session,typeResource.getURI().toString());
            result.__builder.populateData(resource);
            return result
        } catch (Exception ex) {
            log.error("Failed to create the type because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param xml The xml to extract the type from
     * @param uri The uri of the entry to retrieve
     */
    static def getFromXML(String xml, String uri) {
        return getOrmFromXML(xml, uri)
    }
    
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param xml The xml to extract the type from
     * @param uri The uri of the entry to retrieve
     */
    static def getOrmFromXML(String xml, String uri) {
        try {
            Session session = XMLSemanticUtil.getSession()
            session.persist(xml)
            Resource resource = session.get(Resource.class,uri)
            def properties = resource.listProperties()
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            def typeResource = resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            def result = createOrm(session,typeResource.getURI().toString());
                
            result.builder.populateType(resource);
            return result
        } catch (Exception ex) {
            log.error("Failed to create the type because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    /**
     * This method is responsible for creating a new type object identified by
     * the uri.
     * 
     * @return The referenced to the new type
     * @param xml The xml to extract the type from
     * @param uri The uri of the entry to retrieve
     */
    static def getMapFromXML(String xml, String uri) {
        try {
            Session session = XMLSemanticUtil.getSession()
            session.persist(xml)
            Resource resource = session.get(Resource.class,uri)
            def properties = resource.listProperties()
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            def typeResource = resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            def result = createMap(session,typeResource.getURI().toString());
                
            result.__builder.populateData(resource);
            return result
        } catch (Exception ex) {
            log.error("Failed to create the type because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def query(String query) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def records = session.createSPARQLQuery(query).execute()
            log.debug("The number of records is " + records.size())
            
            def result = []
            if (records.size() > 0) {
                
                for (record in records) {
                    def row = []
                    for (int i = 0; i < record.size(); i++) {
                        def dataType = record.getType(i)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.add(record.get(String.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.add(record.get(Boolean.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.add(record.get(Float.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.add(record.get(Double.class,i))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.add(record.get(Integer.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.add(record.get(Long.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.add(record.get(Integer.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.add(record.get(Short.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.add(record.get(Byte.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.add(record.get(Date.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.add(record.get(Date.class,i))
                        } else {
                            def rdfResult = createOrm(session,record.getType(i).getURI().toString());
                            Resource resource = record.get(Resource.class,i)
                            rdfResult.builder.populateType(resource);
                            row.add(rdfResult)
                        }
                    } 
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeQuery(String query) {
        return executeOrmQuery(query)
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeOrmQuery(String query) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def records = session.createSPARQLQuery(query).execute()
            log.debug("The number of records is " + records.size())
            
            def result = []
            if (records.size() > 0) {
                
                for (record in records) {
                    def row = [:]
                    log.debug("The number of columns " + record.size())
                
                    for (columnName in record.getColumns()) {
                        log.debug("Attempt to retrieve the column name [${columnName}]")
                        def dataType = record.getType(columnName)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.put(columnName,record.get(String.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.put(columnName,record.get(Boolean.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.put(columnName,record.get(Float.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.put(columnName,record.get(Double.class,columnName))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.put(columnName,record.get(Long.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.put(columnName,record.get(Short.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.put(columnName,record.get(Byte.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else {
                            def rdfResult = createOrm(session,record.getType(columnName).getURI().toString());
                            Resource resource = record.get(Resource.class,columnName)
                            rdfResult.builder.populateType(resource);
                            row.put(columnName,rdfResult)
                        }
                    }
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeMapQuery(String query) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def records = session.createSPARQLQuery(query).execute()
            log.debug("The number of records is " + records.size())
            
            def result = []
            if (records.size() > 0) {
                
                for (record in records) {
                    def row = [:]
                    log.debug("The number of columns " + record.size())
                
                    for (columnName in record.getColumns()) {
                        log.debug("Attempt to retrieve the column name [${columnName}]")
                        def dataType = record.getType(columnName)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.put(columnName,record.get(String.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.put(columnName,record.get(Boolean.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.put(columnName,record.get(Float.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.put(columnName,record.get(Double.class,columnName))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.put(columnName,record.get(Long.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.put(columnName,record.get(Short.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.put(columnName,record.get(Byte.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else {
                            def rdfResult = createMap(session,record.getType(columnName).getURI().toString());
                            Resource resource = record.get(Resource.class,columnName)
                            rdfResult.__builder.populateData(resource);
                            row.put(columnName,rdfResult)
                        }
                    }
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def queryWithColumns(String query) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.master.MasterRDFStoreDaemonImpl");
            } catch (Exception ex1) {
                try {
                    configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
                } catch (Exception ex2) {
                    configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
                }
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def records = session.createSPARQLQuery(query).execute()
            log.debug("The number of records is " + records.size())
            
            def result = []
            if (records.size() > 0) {
                def firstRecord = records.get(0)
                def columnName = []
                for (column in firstRecord.getColumns()) {
                    columnName.add(column)
                }
                result.add(columnName)
                
                for (record in records) {
                    def row = []
                    log.debug("The number of columns " + record.size())
                
                    for (int i = 0; i < record.size(); i++) {
                        def dataType = record.getType(i)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.add(record.get(String.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.add(record.get(Boolean.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.add(record.get(Float.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.add(record.get(Double.class,i))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.add(record.get(Integer.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.add(record.get(Long.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.add(record.get(Integer.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.add(record.get(Short.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.add(record.get(Byte.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.add(record.get(Date.class,i))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.add(record.get(Date.class,i))
                        } else {
                            def rdfResult = create(session,record.getType(i).getURI().toString());
                            Resource resource = record.get(Resource.class,i)
                            rdfResult.builder.populateType(resource);
                            row.add(rdfResult)
                        }
                    }
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def queryXML(String xml, String query) {
        try {
            Session session = XMLSemanticUtil.getSession()
            session.persist(xml)
            def records = session.createSPARQLQuery(query).execute()
            def result = []
            for (record in records) {
                def row = []
                for (int i = 0; i < record.size(); i++) {
                    def dataType = record.getType(i)
                    def dataTypeURI = dataType.getURI().toString()
                    if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_STRING).getURI().toString())) {
                        row.add(record.get(String.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                        row.add(record.get(Boolean.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                        row.add(record.get(Float.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                        row.add(record.get(Double.class,i))
                    } /*else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                        typeInstance."${propertyName}" = resource.getProperty(Double.class,
                            classProperty.getURI().toString())
                    } */else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                        row.add(record.get(Integer.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_LONG).getURI().toString())) {
                        row.add(record.get(Long.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_INT).getURI().toString())) {
                        row.add(record.get(Integer.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                        row.add(record.get(Short.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                        row.add(record.get(Byte.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE).getURI().toString())) {
                        row.add(record.get(Date.class,i))
                    } else if (dataTypeURI.equals(
                            XSDDataDictionary.getTypeByName(
                            XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                        row.add(record.get(Date.class,i))
                    } else {
                        def rdfResult = create(session,record.getType(i).getURI().toString());
                        Resource resource = record.get(Resource.class,i)
                        rdfResult.builder.populateType(resource);
                        row.add(rdfResult)
                    }
                }
                result.add(row)
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeQueryXML(String xml, String query) {
        return executeOrmQueryXML(xml, query);
    }
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeOrmQueryXML(String xml, String query) {
        try {
            Session session = XMLSemanticUtil.getSession()
            session.persist(xml)
            def records = session.createSPARQLQuery(query).execute()
            def result = []
            if (records.size() > 0) {
                
                for (record in records) {
                    def row = [:]
                    log.debug("The number of columns " + record.size())
                
                    for (columnName in record.getColumns()) {
                        log.debug("Attempt to retrieve the column name [${columnName}]")
                        def dataType = record.getType(columnName)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.put(columnName,record.get(String.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.put(columnName,record.get(Boolean.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.put(columnName,record.get(Float.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.put(columnName,record.get(Double.class,columnName))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.put(columnName,record.get(Long.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.put(columnName,record.get(Short.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.put(columnName,record.get(Byte.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else {
                            def rdfResult = createOrm(session,record.getType(columnName).getURI().toString());
                            Resource resource = record.get(Resource.class,columnName)
                            rdfResult.builder.populateType(resource);
                            row.put(columnName,rdfResult)
                        }
                    }
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    
    /**
     * This method executes a query and returns the results of that query.
     * 
     * @return The results of the query.
     * @param The query string.
     */
    static def executeMapQueryXML(String xml, String query) {
        try {
            Session session = XMLSemanticUtil.getSession()
            session.persist(xml)
            def records = session.createSPARQLQuery(query).execute()
            def result = []
            if (records.size() > 0) {
                
                for (record in records) {
                    def row = [:]
                    log.debug("The number of columns " + record.size())
                
                    for (columnName in record.getColumns()) {
                        log.debug("Attempt to retrieve the column name [${columnName}]")
                        def dataType = record.getType(columnName)
                        def dataTypeURI = dataType.getURI().toString()
                        if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_STRING).getURI().toString())) {
                            row.put(columnName,record.get(String.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                            row.put(columnName,record.get(Boolean.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                            row.put(columnName,record.get(Float.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                            row.put(columnName,record.get(Double.class,columnName))
                        } /*else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                            typeInstance."${propertyName}" = resource.getProperty(Double.class,
                                classProperty.getURI().toString())
                        } */else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_LONG).getURI().toString())) {
                            row.put(columnName,record.get(Long.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_INT).getURI().toString())) {
                            row.put(columnName,record.get(Integer.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                            row.put(columnName,record.get(Short.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                            row.put(columnName,record.get(Byte.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else if (dataTypeURI.equals(
                                XSDDataDictionary.getTypeByName(
                                XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                            row.put(columnName,record.get(Date.class,columnName))
                        } else {
                            def rdfResult = createMap(session,record.getType(columnName).getURI().toString());
                            Resource resource = record.get(Resource.class,columnName)
                            rdfResult.__builder.populateData(resource);
                            row.put(columnName,rdfResult)
                        }
                    }
                    result.add(row)
                }
            }
            return result
        } catch (Exception ex) {
            log.error("Failed to perform the query because : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    /**
     * This is a means to deap copy the information required by the frontend GSP page by pre-loading the links
     * 
     * @param data The data to initialize with deap copys
     * @param variables The list of variables on the data to deap copy.
     */
    static def deapCopy(def data, def variables) {
        try {
            if (data instanceof java.util.ArrayList) {
                data.each { row ->
                    row.each { item ->
                        if (item instanceof Expando) {
                            variables.each { var ->
                                def names = var.toString().split("[.]")
                                deapCopyCallMethods(item, names)
                            }
                        }
                    }
                }
            } else {
                // this section assumes that the data is a single element
                def names = []
                if (variables instanceof java.util.ArrayList) {
                    names = variables[0].toString().split("[.]")
                } else {
                    names = variables.toString().split("[.]")
                }
                deapCopyCallMethods(data, names)
            }
            
        } catch (Exception ex) {
            log.error("Failed to perform a deap copy : " + ex.getMessage(),ex);
            throw ex;
        }
    }
    
    
    static def deapCopyCallMethods(def data, def variablePath) {
        if (variablePath.size() == 0) {
            return null
        }
        def name = variablePath[0]
        def methodName = "get" + 
            name.substring(0,1).toUpperCase() + 
            name.substring(1)
        if (!(data instanceof java.util.ArrayList)) {
            data = [data]
        }
        data.each { item -> 
            if (item.getProperty(methodName) != null) {
                log.debug("#####################Found the method : " + methodName);
                try {
                    def subData = item.invokeMethod(methodName,null)
                    if (variablePath.size() > 1) {
                        deapCopyCallMethods(subData,variablePath[1..(variablePath.size()-1)])
                    }
                } catch (Exception ex) {
                    log.error("Failed to deap copy " + methodName + ": " + ex.getMessage(),ex);
                    // ignore
                }
            }
        }
    }
}
