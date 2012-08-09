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
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
            } catch (Exception ex) {
                configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def instance = create(session,type);
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
     * @param uri The uri of the entry to retrieve.
     */
    static def getFromStore(String uri) {
        try {
            Class configClass = null
            try {
                configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
            } catch (Exception ex) {
                configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            Resource resource = session.get(Resource.class,uri)
            
            PersistanceIdentifier typeIdentifier = 
                    PersistanceIdentifier.getInstance(RDFConstants.SYNTAX_NAMESPACE,
                    RDFConstants.TYPE_LOCALNAME);
            def typeResource = resource.getProperty(OntologyClass.class,
                    typeIdentifier.toURI().toString());
            def result = create(session,typeResource.getURI().toString());
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
    static def getFromXML(String xml, String uri) {
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
            def result = create(session,typeResource.getURI().toString());
                
            result.builder.populateType(resource);
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
                configClass = Class.forName("com.rift.dipforge.rdf.store.RDFConfig");
            } catch (Exception ex) {
                configClass = Class.forName("com.rift.coad.groovy.RDFConfig");
            }
            Session session = SemanticUtil.getInstance(configClass).getSession();
            def records = session.createSPARQLQuery(query).execute()
            log.debug("The number of records is " + records.size())
            
            def result = []
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
}
