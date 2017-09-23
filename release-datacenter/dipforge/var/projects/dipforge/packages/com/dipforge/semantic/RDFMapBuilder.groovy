/*
 * dipforge: Description
 * Copyright (C) Mon Sep 18 08:42:18 UTC 2017 owner 
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
 * RDFMapBuilder.groovy
 * @author admin
 */

package com.dipforge.semantic


import java.net.URI;
import java.util.Date;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;


class RDFMapBuilder {
    
    def classDef
    Session session
    static def log = Logger.getLogger("com.dipforge.semantic.RDFMapBuilder"); 
    def dataMap = [:] 
    
    
    /**
     * The constructor of the RDF type builder.
     */
    public RDFMapBuilder(def classDef, Session session) {
        this.classDef = classDef
        this.session = session
        createMapProperties(classDef)
    }
    
    /**
     * This method returns the type instance
     */
    public def getDataMap() {
        return dataMap
    }
    
    /**
     * This method returns the string value of the class
     */
    public String toXML() {
        Session session = XMLSemanticUtil.getSession()
        processResource(session)
        return session.dumpXML();
    }
    
    /**
     * This method returns the uri of this object
     */
    public String _getUri() {
        URI uri = new URI(classDef.getURI().toString() + '/' + dataMap.id);
        return uri.toString();
    }
    
    /**
     * This method process the properties
     */
    private Resource processResource(Session session) {
        def id = dataMap.id;
        if (id == null) {
            throw new SemanticException("Must specify an id");
        }
        URI uri = new URI(classDef.getURI().toString() + '/' + id);
        Resource resource = session.createResource(classDef.getURI(),uri)
        def classProperties = classDef.listProperties()
        for (classProperty in classProperties) {
            def propertyName = classProperty.getLocalname()
            if (dataMap."${propertyName}" == null) {
                if (dataMap?."${propertyName}classProperty" != null) {
                    dataMap.__builder.onDemandPopulate(propertyName);
                    if (dataMap."${propertyName}" == null) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            
            if (XSDDataDictionary.isBasicTypeByURI(classProperty.getType().getURI().toString())) {
                def propertyType = classProperty.getType().getURI().toString();
                if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_STRING).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}".toString())
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Boolean || 
                        dataMap."${propertyName}".getClass().equals(boolean.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Boolean.parseBoolean(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Float || 
                        dataMap."${propertyName}".getClass().equals(float.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Float.parseFloat(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Double || 
                        dataMap."${propertyName}".getClass().equals(double.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Double.parseDouble(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Integer || 
                        dataMap."${propertyName}".getClass().equals(int.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Integer.parseInt(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_LONG).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Long || 
                        dataMap."${propertyName}".getClass().equals(long.class) ||
                        dataMap."${propertyName}" instanceof Integer || 
                        dataMap."${propertyName}".getClass().equals(int.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Long.parseLong(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_INT).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Integer || 
                        dataMap."${propertyName}".getClass().equals(int.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Integer.parseInt(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                    if ((dataMap."${propertyName}" instanceof Short) || 
                        dataMap."${propertyName}".getClass().equals(short.class)) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Short.parseShort(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                    if (dataMap."${propertyName}" instanceof Byte) {
                        resource.addProperty(classProperty.getURI().toString(),dataMap."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Byte.parseByte(dataMap."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DATE).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),(Date)dataMap."${propertyName}")
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),(Date)dataMap."${propertyName}")
                }
            } else {
                if (dataMap."${propertyName}" instanceof java.util.List) {
                    for (def item : dataMap."${propertyName}") {
                        if (classProperty.hasRange()) {
                            resource.addProperty(classProperty.getURI().toString(),
                                item.__builder.processResource(session))
                        } else {
                            resource.addProperty(classProperty.getURI().toString(),
                                session.createResource(
                                    item.__builder.classDef.getURI(),
                                    new URI(item.__builder.classDef.getURI().toString() + "/" + item.id)))
                            
                        }
                    }
                } else {
                    if (classProperty.hasRange()) {
                        resource.addProperty(classProperty.getURI().toString(),
                            (Resource)dataMap."${propertyName}".__builder.processResource(session))
                    } else {
                        def value = dataMap."${propertyName}"
                        log.debug("The property is ${propertyName} the value is [${value}]")
                        if (dataMap."${propertyName}" != null) {
                            resource.addProperty(classProperty.getURI().toString(),
                                session.createResource(
                                    dataMap."${propertyName}".__builder.classDef.getURI(),
                                    new URI(dataMap."${propertyName}".__builder.classDef.getURI().toString() + "/" + dataMap."${propertyName}".id) ))
                        }
                    }
                }
            }
        }
        return resource
    }
    
    
    /**
     * This method populates the type methods.
     * 
     * @param resource The resource information.
     */
    public void populateData(Resource resource) {
        def classProperties = classDef.listProperties()
        boolean hasId = false;
        for (classProperty in classProperties) {
            def propertyName = classProperty.getLocalname()
            if (propertyName.equalsIgnoreCase("id")) {
                hasId = true;
            }
            def propertyType = classProperty.getType().getURI().toString()
            if (!resource.hasProperty(classProperty.getURI().toString())) {
                continue
            }
            if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_STRING).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(String.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Boolean.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Float.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Double.class,
                    classProperty.getURI().toString())
            } /*else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Double.class,
                    classProperty.getURI().toString())
            } */else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Integer.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_LONG).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Long.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INT).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Integer.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Short.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Byte.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Date.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                dataMap."${propertyName}" = resource.getProperty(Date.class,
                    classProperty.getURI().toString())
            } else {
                if (classProperty.hasRange()) {
                    def properties = resource.listProperties(classProperty.getURI().toString())
                    log.debug("############### The number of properties retrieve from the store is : "
                        + properties.size())
                    if (properties.size() == 1) {
                        if (dataMap."${propertyName}" == null) {
                            dataMap."${propertyName}" = 
                                RDF.createMap(classProperty.getType().getURI().toString())
                        }
                        dataMap."${propertyName}".__builder.populateData(
                            resource.getProperty(Resource.class,
                            classProperty.getURI().toString()));
                    } else if (properties.size() > 1) {
                        dataMap."${propertyName}" = []
                        for (def prop : properties) {
                            def arrayInstance = RDF.createMap(classProperty.getType().getURI().toString())
                            arrayInstance.__builder.populateData(prop.get(Resource.class));
                            dataMap."${propertyName}".add(arrayInstance)
                        }
                    }
                } else {
                    def upperPropertyName = propertyName.substring(0,1).toUpperCase() + 
                            propertyName.substring(1)
                    
                    dataMap."${propertyName}classProperty" = classProperty;
                    dataMap."${propertyName}classResource" = resource;
                    dataMap."${propertyName}" = null
                }
            }
        }
        // strip the uri
        if (!hasId) {
            def objectURI = classDef.getURI().toString();
            dataMap."id" = objectURI.substring(objectURI.lastIndexOf("/"));
        }
        
    }
    
    
    /**
     * This method is called on demand to populate an instance.
     */
    public def onDemandPopulate(def propertyName) {
        
        if (dataMap."${propertyName}" != null) {
            return dataMap."${propertyName}"
        }
        
        Resource resource = dataMap."${propertyName}classResource"
        def classProperty = dataMap."${propertyName}classProperty"
        
        
        log.debug("############### The on demand load : "
            + properties.size())
        def properties = resource.listProperties(classProperty.getURI().toString())
        if (properties.size() == 1) {
            if (dataMap."${propertyName}" == null) {
                dataMap."${propertyName}" = 
                    RDF.createMap(classProperty.getType().getURI().toString())
            }
            dataMap."${propertyName}".__builder.populateData(
                resource.getProperty(Resource.class,
                classProperty.getURI().toString()));
        } else if (properties.size() > 1) {
            typeInstance."${propertyName}" = []
            for (def prop : properties) {
                def arrayInstance = RDF.createMap(classProperty.getType().getURI().toString())
                arrayInstance.__builder.populateData(prop.get(Resource.class));
                dataMap."${propertyName}".add(arrayInstance)
            }
        }
        return dataMap."${propertyName}"
    }
    
    
    /**
     * This method creates the type methods.
     * 
     * @param classDef The class definition
     */
    private void createMapProperties(def classDef) {
        def classProperties = classDef.listProperties()
        boolean hasId = false;
        for (classProperty in classProperties) {
            def propertyName = classProperty.getLocalname()
            log.debug("Property [" + classProperty.getURI() + "]")
            if (propertyName.equalsIgnoreCase("id")) {
                hasId = true;
            }
            def upperPropertyName = propertyName.substring(0,1).toUpperCase() + 
                propertyName.substring(1)
            def propertyType = classProperty.getType().getURI().toString()
            if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_STRING).getURI().toString())) {
                dataMap."${propertyName}" = ""
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                dataMap."${propertyName}" = false
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                dataMap."${propertyName}" = 0.0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                dataMap."${propertyName}" = 0.0
            } /*else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                dataMap."${propertyName}" = 0.0
            } */else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                dataMap."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_LONG).getURI().toString())) {
                dataMap."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INT).getURI().toString())) {
                dataMap."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                dataMap."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                dataMap."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE).getURI().toString())) {
                dataMap."${propertyName}" = new Date()
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                dataMap."${propertyName}" = new Date()
            } else {
                if (classProperty.hasRange()) {
                    dataMap."${propertyName}" = 
                        RDF.createMap(classProperty.getType().getURI().toString())
                } else {
                    dataMap."${propertyName}" = null
                }
            }
            
            
        }
        
        // generate a default id for properties
        dataMap."id" = RandomGuid.getInstance().getGuid()
        
        dataMap."__builder" = this
        
    }
}

