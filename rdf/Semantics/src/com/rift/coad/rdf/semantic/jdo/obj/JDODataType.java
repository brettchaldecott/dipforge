/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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

package com.rift.coad.rdf.semantic.jdo.obj;

import com.rift.coad.rdf.semantic.types.*;
import java.net.URI;

/**
 * The JDO data type implementation.
 *
 * @author brett chaldecott
 */
public class JDODataType implements DataType {

    private String namespace;
    private String localName;


    /**
     * This method sets the XSD data type information.
     *
     * @param uri The uri type information.
     */
    public JDODataType(URI uri) {
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
    public JDODataType(String namespace, String localName) {
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

}
