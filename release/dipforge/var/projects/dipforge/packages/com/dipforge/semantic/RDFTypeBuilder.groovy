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
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;

class RDFTypeBuilder {
    
    def typeInstance
    
    
    /**
     * The constructor of the RDF type builder.
     */
    public RDFTypeBuilder(def classDef) {
        typeInstance = new Expando()
        this.classDef = classDef
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
     * This method is called to create the tdf type 
     * 
     * @param classDef The class definition for this object.
     */
    private void createStandardMethods(def classDef) {
        typeInstance.builder = this
        typeInstance.classDef = classDef
        
        
        
    }
    
    
    /**
     * This method creates the type methods.
     * 
     * @param classDef The class definition
     */
    private void createTypeMethods(def classDef) {
        def classProperties = classDef.listProperties()
        
        for (classProperty in classProperties) {
            def propertyName = classProperty.getLocalname()
            def upperPropertyName = propertyName.substring(0,1).toUpperCase() + 
                propertyName.substring(1)
            if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BOOLEAN)))) {
                typeInstance."${propertyName}" = false
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_FLOAT)))) {
                typeInstance."${propertyName}" = 0.0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DOUBLE)))) {
                typeInstance."${propertyName}" = 0.0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DECIMAL)))) {
                typeInstance."${propertyName}" = 0.0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INTEGER)))) {
                typeInstance."${propertyName}" = 0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_LONG)))) {
                typeInstance."${propertyName}" = 0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_INT)))) {
                typeInstance."${propertyName}" = 0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_SHORT)))) {
                typeInstance."${propertyName}" = 0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_BYTE)))) {
                typeInstance."${propertyName}" = 0
            } else if (propertyName.getType().equals(
                XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.getTypeByName(
                    XSDDataDictionary.XSD_DATE)))) {
                typeInstance."${propertyName}" = new Date()
            }
            
            
            // add the getter and the setter
            typeInstance."get${upperPropertyName}" = {->
                return "${propertyName}"
            }
            
            // add the getter and the setter
            typeInstance."set${upperPropertyName}" = {_local_variable->
                "${propertyName}" = _local_variable
            }
        }
    }
}


