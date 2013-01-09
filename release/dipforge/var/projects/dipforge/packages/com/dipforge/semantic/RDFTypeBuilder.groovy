/*
 * dipforge: Description
 * Copyright (C) Sun Nov 06 15:32:47 SAST 2011 Rift IT 
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
 * RDFTypeBuilder.groovy
 * @author Brett Chaldecott
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

class RDFTypeBuilder {
    
    def typeInstance
    def classDef
    Session session
    static def log = Logger.getLogger("com.dipforge.semantic.RDFTypeBuilder"); 
    def dataType
    
    
    /**
     * The constructor of the RDF type builder.
     */
    public RDFTypeBuilder(def classDef, Session session) {
        typeInstance = new Expando()
        this.classDef = classDef
        this.session = session
        createStandardMethods(classDef)
        createTypeMethods(classDef)
    }
    
    
    /**
     * This method returns the type instance
     */
    public def getTypeInstance() {
        return typeInstance
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
        URI uri = new URI(classDef.getURI().toString() + '/' + typeInstance.getId());
        return uri.toString();
    }
    
    /**
     * This method process the properties
     */
    private Resource processResource(Session session) {
        def id = typeInstance.getId();
        if (id == null) {
            throw new SemanticException("Must specify an id");
        }
        URI uri = new URI(classDef.getURI().toString() + '/' + id);
        Resource resource = session.createResource(classDef.getURI(),uri)
        def classProperties = classDef.listProperties()
        for (classProperty in classProperties) {
            def propertyName = classProperty.getLocalname()
            if (typeInstance."${propertyName}" == null) {
                if (typeInstance?."${propertyName}classProperty" != null) {
                    typeInstance.builder.onDemandPopulate(
                                typeInstance."${propertyName}classResource", 
                                typeInstance."${propertyName}classProperty");
                    if (typeInstance."${propertyName}" == null) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            
            if (XSDDataDictionary.isBasicTypeByURI(classProperty.getType().getURI().toString())) {
                log.info("#### URI [" + classProperty.getURI().toString() + "][" + 
                    typeInstance."${propertyName}" + "]")
                def propertyType = classProperty.getType().getURI().toString();
                if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_STRING).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}".toString())
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Boolean || 
                        typeInstance."${propertyName}".getClass().equals(boolean.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Boolean.parseBoolean(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Float || 
                        typeInstance."${propertyName}".getClass().equals(float.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Float.parseFloat(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Double || 
                        typeInstance."${propertyName}".getClass().equals(double.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Double.parseDouble(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Integer || 
                        typeInstance."${propertyName}".getClass().equals(int.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Integer.parseInt(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_LONG).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Long || 
                        typeInstance."${propertyName}".getClass().equals(long.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Long.parseLong(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_INT).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Integer || 
                        typeInstance."${propertyName}".getClass().equals(int.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Integer.parseInt(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                    if ((typeInstance."${propertyName}" instanceof Short) || 
                        typeInstance."${propertyName}".getClass().equals(short.class)) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Short.parseShort(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                    if (typeInstance."${propertyName}" instanceof Byte) {
                        resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            Byte.parseByte(typeInstance."${propertyName}"))
                    }
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DATE).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),(Date)typeInstance."${propertyName}")
                } else if (propertyType.equals(
                        XSDDataDictionary.getTypeByName(
                        XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                    resource.addProperty(classProperty.getURI().toString(),(Date)typeInstance."${propertyName}")
                }
            } else {
                if (typeInstance."${propertyName}" instanceof java.util.List) {
                    for (def item : typeInstance."${propertyName}") {
                        if (classProperty.hasRange()) {
                            resource.addProperty(classProperty.getURI().toString(),
                                item.builder.processResource(session))
                        } else {
                            resource.addProperty(classProperty.getURI().toString(),
                                session.createResource(
                                    item.builder.classDef.getURI(),
                                    new URI(item.builder.classDef.getURI().toString() + "/" + item.getId())))
                            
                        }
                    }
                } else {
                    if (classProperty.hasRange()) {
                        resource.addProperty(classProperty.getURI().toString(),
                            (Resource)typeInstance."${propertyName}".builder.processResource(session))
                    } else {
                        resource.addProperty(classProperty.getURI().toString(),
                            session.createResource(
                                typeInstance."${propertyName}".builder.classDef.getURI(),
                                new URI(typeInstance."${propertyName}".builder.classDef.getURI().toString() + "/" + typeInstance."${propertyName}".getId())))
                    }
                }
            }
        }
        return resource
    }
    
    
    /**
     * This method is called to create the tdf type 
     * 
     * @param classDef The class definition for this object.
     */
    private void createStandardMethods(def classDef) {
        typeInstance.builder = this
        typeInstance.classDef = classDef
        
        
        // add the getter and the setter
        typeInstance."toXML" = {_local_variable->
            return typeInstance.builder.toXML()
        }
        
        // get the uri for this object
        typeInstance."_getUri" = {_local_variable->
            return typeInstance.builder._getUri()
        }
    }
    
    /**
     * This method populates the type methods.
     * 
     * @param resource The resource information.
     */
    public void populateType(Resource resource) {
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
                typeInstance."${propertyName}" = resource.getProperty(String.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Boolean.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Float.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Double.class,
                    classProperty.getURI().toString())
            } /*else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Double.class,
                    classProperty.getURI().toString())
            } */else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Integer.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_LONG).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Long.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INT).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Integer.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Short.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Byte.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Date.class,
                    classProperty.getURI().toString())
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                typeInstance."${propertyName}" = resource.getProperty(Date.class,
                    classProperty.getURI().toString())
            } else {
                if (classProperty.hasRange()) {
                    def properties = resource.listProperties(classProperty.getURI().toString())
                    log.debug("############### The number of properties retrieve from the store is : "
                        + properties.size())
                    if (properties.size() == 1) {
                        if (typeInstance."${propertyName}" == null) {
                            typeInstance."${propertyName}" = 
                                RDF.create(classProperty.getType().getURI().toString())
                        }
                        typeInstance."${propertyName}".builder.populateType(
                            resource.getProperty(Resource.class,
                            classProperty.getURI().toString()));
                    } else if (properties.size() > 1) {
                        typeInstance."${propertyName}" = []
                        for (def prop : properties) {
                            def arrayInstance = RDF.create(classProperty.getType().getURI().toString())
                            arrayInstance.builder.populateType(prop.get(Resource.class));
                            typeInstance."${propertyName}".add(arrayInstance)
                        }
                    }
                } else {
                    def upperPropertyName = propertyName.substring(0,1).toUpperCase() + 
                            propertyName.substring(1)
                    
                    typeInstance."${propertyName}classProperty" = classProperty;
                    typeInstance."${propertyName}classResource" = resource;
                    // add the getter and the setter
                    typeInstance."get${upperPropertyName}" = {->
                        if (typeInstance."${propertyName}" == null) {
                            typeInstance.builder.onDemandPopulate(
                                typeInstance."${propertyName}classResource", 
                                typeInstance."${propertyName}classProperty")
                        }
                        return typeInstance."${propertyName}"
                    }
                }
            }
        }
        // strip the uri
        if (!hasId) {
            def objectURI = classDef.getURI().toString();
            typeInstance."id" = objectURI.substring(objectURI.lastIndexOf("/"));
        }
        
    }
    
    /**
     * This method is called on demand to populate an instance.
     */
    private void onDemandPopulate(Resource resource, def classProperty) {
        log.info("############### The on demand load : "
            + properties.size())
        def propertyName = classProperty.getLocalname()
        def properties = resource.listProperties(classProperty.getURI().toString())
        if (properties.size() == 1) {
            if (typeInstance."${propertyName}" == null) {
                typeInstance."${propertyName}" = 
                    RDF.create(classProperty.getType().getURI().toString())
            }
            typeInstance."${propertyName}".builder.populateType(
                resource.getProperty(Resource.class,
                classProperty.getURI().toString()));
        } else if (properties.size() > 1) {
            typeInstance."${propertyName}" = []
            for (def prop : properties) {
                def arrayInstance = RDF.create(classProperty.getType().getURI().toString())
                arrayInstance.builder.populateType(prop.get(Resource.class));
                typeInstance."${propertyName}".add(arrayInstance)
            }
        }
    }
    
    /**
     * This method creates the type methods.
     * 
     * @param classDef The class definition
     */
    private void createTypeMethods(def classDef) {
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
                typeInstance."${propertyName}" = ""
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BOOLEAN).getURI().toString())) {
                typeInstance."${propertyName}" = false
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_FLOAT).getURI().toString())) {
                typeInstance."${propertyName}" = 0.0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DOUBLE).getURI().toString())) {
                typeInstance."${propertyName}" = 0.0
            } /*else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DECIMAL).getURI().toString())) {
                typeInstance."${propertyName}" = 0.0
            } */else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INTEGER).getURI().toString())) {
                typeInstance."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_LONG).getURI().toString())) {
                typeInstance."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INT).getURI().toString())) {
                typeInstance."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_SHORT).getURI().toString())) {
                typeInstance."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BYTE).getURI().toString())) {
                typeInstance."${propertyName}" = 0
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE).getURI().toString())) {
                typeInstance."${propertyName}" = new Date()
            } else if (propertyType.equals(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE_TIME).getURI().toString())) {
                typeInstance."${propertyName}" = new Date()
            } else {
                if (classProperty.hasRange()) {
                    typeInstance."${propertyName}" = 
                        RDF.create(classProperty.getType().getURI().toString())
                } else {
                    typeInstance."${propertyName}" = null
                }
            }
            
            // add the getter and the setter
            typeInstance."get${upperPropertyName}" = {->
                return typeInstance."${propertyName}"
            }
            
            // add the getter and the setter
            typeInstance."set${upperPropertyName}" = {_local_variable->
                typeInstance."${propertyName}" = _local_variable
            }
            
        }
        
        if (!hasId) {
            typeInstance."id" = ""
            // add the getter and the setter
            typeInstance."getId" = {->
                return typeInstance."id"
            }
            
            // add the getter and the setter
            typeInstance."setId" = {_local_variable->
                typeInstance."id" = _local_variable
            }
        }
        
        // generate a default id for properties
        typeInstance."id" = RandomGuid.getInstance().getGuid()
    }
    
    
    
}


