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
 * OntologyManager.java
 */


// the package path.
package com.rift.coad.rdf.semantic.ontology;

import java.net.URI;

/**
 * This interface defines the ontology information.
 *
 * @author brett chaldecott
 */
public interface OntologySession {

    
    /**
     * This method returns a reference to the ontology transaction.
     * 
     * @return The reference to the ontology transaction.
     * @throws OntologyException
     */
    public OntologyTransaction getTransaction() throws OntologyException;


    /**
     * This method creates an ontology property for the specified uri.
     *
     * @param uri The uri for the ontology property.
     * @return The reference to the newly created ontology.
     * @throws OntologyException
     */
    public OntologyProperty createProperty(URI uri) throws OntologyException;


    /**
     * This method is called to return a reference to the specified ontology property.
     *
     * @param uri The uri for the ontology property.
     * @return The reference to the retrieved property.
     * @throws OntologyException
     */
    public OntologyProperty getProperty(URI uri) throws OntologyException;


    /**
     * This method removes the property.
     *
     * @param uri The uri.
     * @throws OntologyException
     */
    public void removeProperty(URI uri) throws OntologyException;


    /**
     * This method creates  new ontology class identified by the uri.
     *
     * @param uri The uri to create
     * @return The reference to the ontology class.
     * @throws OntologyException
     */
    public OntologyClass createClass(URI uri) throws OntologyException;

    /**
     * This method retrieves the ontology class.
     *
     * @param uri The URI ontology class.
     * @return The reference to the retrieved ontology class.
     * @throws OntologyException
     */
    public OntologyClass getClass(URI uri) throws OntologyException;


    /**
     * This method is responsible for removing the class identified by the URI.
     *
     * @param uri The URI to remove.
     * @throws OntologyException
     */
    public void removeClass(URI uri) throws OntologyException;


    /**
     * This method dumps the ontology session to xml string.
     *
     * @return The string to dump.
     * @throws OntologyException
     */
    public String dumpXML() throws OntologyException;
}
