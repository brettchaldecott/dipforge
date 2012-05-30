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
            if (XSDDataDictionary.isBasicTypeByURI(classProperty.getType().getURI().toString())) {
                if (typeInstance."${propertyName}" == null) {
                    continue;
                }
                log.debug("URI [" + classProperty.getURI().toString() + "][" + 
                    typeInstance."${propertyName}" + "]")
                resource.addProperty(classProperty.getURI().toString(),typeInstance."${propertyName}")
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
                                    item.builder.classDef.getURI() + "/" + item.getId()))
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
                                typeInstance."${propertyName}".builder.classDef.getURI() + "/" + typeInstance."${propertyName}".getId()))
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
            } else {
                def properties = resource.listProperties(classProperty.getURI().toString())
                if (properties.size() == 1) {
                    typeInstance."${propertyName}".builder.populateType(
                        resource.getProperty(Resource.class,
                        classProperty.getURI().toString()));
                } else if (properties.size() == 1) {
                    typeInstance."${propertyName}" = []
                    for (def prop : properties) {
                        def arrayInstance = RDF.create(classProperty.getType().getURI().toString())
                        arrayInstance.builder.populateType(prop.get(Resource.class));
                        typeInstance."${propertyName}".add(arrayInstance)
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
            } else {
                typeInstance."${propertyName}" = 
                    RDF.create(classProperty.getType().getURI().toString())
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


