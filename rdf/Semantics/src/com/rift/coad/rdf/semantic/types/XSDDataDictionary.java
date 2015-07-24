/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  2015 Burntjam
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
 * XSDDataDictionary.java
 */

package com.rift.coad.rdf.semantic.types;

/**
 * This class is responsible for providing a means to access XSD data types.
 *
 * @author brett chaldecott
 */
public class XSDDataDictionary {

    public static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    public static final String XSD_STRING = "string";
    public static final String XSD_BOOLEAN = "boolean";
    public static final String XSD_FLOAT = "float";
    public static final String XSD_DOUBLE = "double";
    public static final String XSD_DECIMAL = "decimal";
    public static final String XSD_DURATION = "duration";
    public static final String XSD_INTEGER = "integer";
    public static final String XSD_LONG = "long";
    public static final String XSD_INT = "int";
    public static final String XSD_SHORT = "short";
    public static final String XSD_BYTE = "byte";
    public static final String XSD_DATE = "date";
    public static final String XSD_DATE_TIME = "dateTime";

    public static DataType getTypeByName(String name) throws DataTypeException {
        if (name.equalsIgnoreCase(XSD_STRING)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_STRING);
        } else if (name.equalsIgnoreCase(XSD_BOOLEAN)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_BOOLEAN);
        } else if (name.equalsIgnoreCase(XSD_FLOAT)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_FLOAT);
        } else if (name.equalsIgnoreCase(XSD_DOUBLE)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_DOUBLE);
        } else if (name.equalsIgnoreCase(XSD_DURATION)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_DURATION);
        } else if (name.equalsIgnoreCase(XSD_INTEGER)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_INTEGER);
        } else if (name.equalsIgnoreCase(XSD_LONG)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_LONG);
        } else if (name.equalsIgnoreCase(XSD_INT)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_INT);
        } else if (name.equalsIgnoreCase(XSD_SHORT)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_SHORT);
        } else if (name.equalsIgnoreCase(XSD_BYTE)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_BYTE);
        } else if (name.equalsIgnoreCase(XSD_DATE)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_DATE);
        } else if (name.equalsIgnoreCase(XSD_DATE_TIME)) {
            return new XSDDataType(XSD_NAMESPACE,XSD_DATE_TIME);
        } else {
            throw new DataTypeException("The type [" + name + "]is unknown");
        }
    }
    
    
    /**
     * This method returns true if the name is for a basic type
     * 
     * @param name The name of the type.
     * @return The boolean result.
     * @throws DataTypeException 
     */
    public static boolean isBasicTypeByName(String name) throws DataTypeException {
        if (name.equalsIgnoreCase(XSD_STRING)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_BOOLEAN)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_FLOAT)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_DOUBLE)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_DURATION)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_INTEGER)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_LONG)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_INT)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_SHORT)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_BYTE)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_DATE)) {
            return true;
        } else if (name.equalsIgnoreCase(XSD_DATE_TIME)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns the type identified by the URI.
     *
     * @param uri The URI of the type to retrieve.
     * @return The reference to the data type.
     * @throws DataTypeException
     */
    public static DataType getTypeByURI(String uri) throws DataTypeException {
        String[] tokens = uri.split("#");
        if (tokens.length < 2) {
            throw new DataTypeException("The uri [" + uri + "] is not formed correctly.");
        }
        String[] names = tokens[1].split("/");
        return getTypeByName(names[0]);
    }
    
    
    /**
     * This method returns the type identified by the URI.
     *
     * @param uri The URI of the type to retrieve.
     * @return The reference to the data type.
     * @throws DataTypeException
     */
    public static boolean isBasicTypeByURI(String uri) throws DataTypeException {
        String[] tokens = uri.split("#");
        if (tokens.length < 2) {
            throw new DataTypeException("The uri [" + uri + "] is not formed correctly.");
        }
        String[] names = tokens[1].split("/");
        return isBasicTypeByName(names[0]);
    }

}
