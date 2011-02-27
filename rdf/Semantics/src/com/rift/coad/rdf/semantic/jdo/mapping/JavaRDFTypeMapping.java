/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo.mapping;

import com.rift.coad.rdf.semantic.annotation.helpers.LocalNameHelper;
import com.rift.coad.rdf.semantic.annotation.helpers.NamespaceHelper;
import com.rift.coad.rdf.semantic.jdo.obj.JDODataType;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import java.util.Calendar;

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
            } else if (javaType.equals(Integer.class) || javaType.equals(int.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_INT);
            } else if (javaType.equals(Long.class) || javaType.equals(long.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_LONG);
            } else if (javaType.equals(Double.class) || javaType.equals(double.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DOUBLE);
            } else if (javaType.equals(Float.class) || javaType.equals(float.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_FLOAT);
            } else if (javaType.equals(Byte.class) || javaType.equals(byte.class)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_BYTE);
            } else if (Calendar.class.isAssignableFrom(javaType)) {
                return XSDDataDictionary.getTypeByName(XSDDataDictionary.XSD_DATE);
            }
            return new JDODataType(new NamespaceHelper(javaType).getNamespace(),
                    new LocalNameHelper(javaType).getLocalName());
        } catch (Exception ex) {
            throw new MappingException("Failed to return the RDF type : " +
                    ex.getMessage(),ex);
        }
    }
}
