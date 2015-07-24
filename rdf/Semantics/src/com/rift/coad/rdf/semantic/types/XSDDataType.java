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
 * XSDDataType.java
 */

package com.rift.coad.rdf.semantic.types;

import java.net.URI;

/**
 *
 * @author brett chaldecott
 */
public class XSDDataType implements DataType {

    private String namespace;
    private String localName;


    /**
     * This method sets the XSD data type information.
     *
     * @param uri The uri type information.
     */
    protected XSDDataType(URI uri) {
        String[] tokens = uri.toString().split("#");
        this.namespace = tokens[0];
        this.localName = tokens[1];
    }

    
    /**
     * This constructor is responsible for the xsd data type information
     *
     * @param namespace The name space information.
     * @param localname The local name.
     */
    protected XSDDataType(String namespace, String localName) {
        this.namespace = namespace;
        this.localName = localName;
    }



    /**
     * This method returns the uri information.
     *
     * @return The string containing the uri.
     * @throws DataTypeException
     */
    public URI getURI() throws DataTypeException {
        try {
            return new URI(namespace + "#" + localName);
        } catch (Exception ex) {
            throw new DataTypeException("Failed to retrieve the uri : " +
                    ex.getMessage(),ex);
        }
    }

    /**
     * The getter for the namespace.
     * @return
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * This method returns the local name.
     *
     * @return The string containing the local name.
     */
    public String getLocalName() {
        return localName;
    }

    
    /**
     * This method returns true if the objects are equal.
     * 
     * @param obj The object to perform the comparison on.
     * @return TRUE if equals, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final XSDDataType other = (XSDDataType) obj;
        if ((this.namespace == null) ? (other.namespace != null) : !this.namespace.equals(other.namespace)) {
            return false;
        }
        if ((this.localName == null) ? (other.localName != null) : !this.localName.equals(other.localName)) {
            return false;
        }
        return true;
    }

    
    /**
     * This method generates a hash code based on the internal values.
     * 
     * @return The hash code to perform the comparison on.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.namespace != null ? this.namespace.hashCode() : 0);
        hash = 79 * hash + (this.localName != null ? this.localName.hashCode() : 0);
        return hash;
    }
    
    
    
}
