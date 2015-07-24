/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * RDFURIHelper.java
 */


// package path
package com.rift.coad.rdf.semantic.util;

import java.net.URI;

/**
 * The URI helper.
 *
 * @author brett chaldecott
 */
public class RDFURIHelper {
    // class constants
    private final static String URI_SEPERATOR = "#";

    // private member variables
    private URI uri;
    private String namespace;
    private String localName;

    /**
     * The constructor that sets up the internal uri
     *
     * @param uri The string containing the uri.
     */
    public RDFURIHelper(String uri) throws UtilException {
        try {
            this.uri = new URI(uri);
            String[] elements = uri.split(URI_SEPERATOR);
            if (elements.length < 2) {
                throw new UtilException("The uri [" + uri +
                        "] is not correctly formatted");
            }
            namespace = elements[0];
            localName = elements[1];
        } catch (UtilException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UtilException("Failed to instantiate uri helper : " +
                    ex.getMessage(),ex);
        }
    }

    /**
     * This constructor takes the namespace and local name.
     * 
     * @param namespace The namespace
     * @param localName The localname
     */
    public RDFURIHelper(String namespace, String localName) throws UtilException {
        this.namespace = namespace;
        this.localName = localName;
        try {
            this.uri = new URI(namespace + URI_SEPERATOR + localName);
        } catch (Exception ex) {
            throw new UtilException("Failed to instantiate the URI helper : " +
                    ex.getMessage(),ex);
        }
    }




    /**
     * The reference to the local name
     *
     * @return The string containing the local name from the uri.
     */
    public String getLocalName() {
        return localName;
    }


    /**
     * This method returns the namespace information.
     *
     * @return The string containing the namespace information.
     */
    public String getNamespace() {
        return namespace;
    }


    /**
     * The reference to the URI.
     *
     * @return The URI.
     */
    public URI getUri() {
        return uri;
    }



    
}
