/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.mapping;

import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;

/**
 * This object is responsible for returning mapping information between Java and
 * RDF types.
 *
 * @author brett chaldecott
 */
public class JavaRDFTypeMapping {

    /**
     * This method returns the RDF type information uri.
     *
     * @param javaType The type information.
     * @return The string containing the type uri for the type passed in.
     * @throws MappingException
     */
    public static DataType getRDFTypeURI(Class javaType) throws MappingException {
        try {
            if (javaType.equals(String.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_STRING);
            }
            return null;
        } catch (Exception ex) {
            throw new MappingException("Failed to return the RDF type : " +
                    ex.getMessage(),ex);
        }
    }
}
