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
 * PersistanceSession.java
 */

// package scope
package com.rift.coad.rdf.semantic.persistance;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * This interface defines the method to manage the perstance to the backend data
 * store.
 *
 * @author brett chaldecott
 */
public interface PersistanceSession {

    /**
     * This method returns a reference to the newly created transaction.
     *
     * @return The reference to the transaction.
     * @throws PersistanceException
     * @throws PersistanceUnknownException
     */
    public PersistanceTransaction getTransaction() throws
            PersistanceException, PersistanceUnknownException;


    /**
     * This method is called to persist the stream changes to this semantic session.
     *
     * @param in The input stream containing the changes to persist to this session.
     * @throws PersistanceException
     */
    public void persist(InputStream in) throws PersistanceException;


    /**
     * This method is called to persist the RDF information to the session.
     *
     * @param rdf The string containing the rdf information to persist.
     * @throws PersistanceException
     */
    public void persist(String rdf) throws PersistanceException;
    

    /**
     * This method returns true if the resource pointed to by the uri exists.
     *
     * @param uri The resource uri
     * @return TRUE if there is a resource identified by the URI, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasResource(URI uri) throws
            PersistanceException;


    /**
     * This method returns true if there is a resource identified by the URI and FALSE if there is not.
     *
     * @param identifier The resource identifier.
     * @return TRUE if found, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasResource(PersistanceIdentifier identifier) throws
            PersistanceException;


    /**
     * This method returns the resource information.
     *
     * @param uri The uri for the resource.
     * @return The reference to the resource.
     * @throws PersisanceException
     * @throws PersistanceUnknownException
     */
    public PersistanceResource getResource(URI uri) throws 
            PersistanceException, PersistanceUnknownException;

    /**
     * This method returns the resource identified by the persistance identifer.
     *
     * @param uri The identifier of the resource to retrieve from the store.
     * @return The reference to the newly retrieved resource.
     * @throws PersistanceException
     */
    public PersistanceResource getResource(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method creates a new resource.
     *
     * @param The URI that identifies the new resource
     * @return The reference to the newly created resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(URI uri)
            throws PersistanceException;


    /**
     * This method creates a new resource.
     *
     * @param identifier The identifier for the new resource.
     * @return The reference to the newly created resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method creates a resource linked to the given type.
     *
     * @param uri The uri of the resource to link to.
     * @param resourceType The resource type.
     * @return The reference to the persistance resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(URI uri, PersistanceResource resourceType)
            throws PersistanceException;


    /**
     * This method creates a new resource identified by the identifier and ties it to a given resource type.
     *
     * @param identifier The identifier of the resource.
     * @param resourceType The resource type.
     * @return The reference to the newly created persistence resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(PersistanceIdentifier identifier, PersistanceResource resourceType)
            throws PersistanceException;



    /**
     * This method remoes a new resource.
     *
     * @param The URI that identifies the resource
     * @throws PersistanceException
     */
    public void removeResource(URI uri)
            throws PersistanceException;


    /**
     * This method removes a resource.
     *
     * @param identifier The identifier for the resource.
     * @throws PersistanceException
     */
    public void removeResource(PersistanceIdentifier identifier)
            throws PersistanceException;


    /**
     * This method removes the RDF identifed by the rdf string.
     *
     * @param rdf The string containing the RDF to remove.
     * @throws PersistanceException
     */
    public void removeRDF(String rdf) throws PersistanceException;


    /**
     * The method removes the RDF information identified by the stream.
     *
     * @param out The stream containing the RDF information to remove.
     * @throws PersistanceException
     */
    public void removeRDF(InputStream in) throws PersistanceException;

    
    /**
     * This method returns the query interface object.
     *
     * @param queryStr The query string.
     * @return The resulting query string.
     * @throws PersistanceQueryException
     */
    public PersistanceQuery createQuery(String queryStr)
            throws PersistanceQueryException;


    /**
     * This method returns a dump of the store in XML.
     *
     * @return The contents of the store in XML format.
     * @throws PersistanceException
     */
    public String dumpXML() throws PersistanceException;


    /**
     * This method dumps the model to an xml format.
     *
     * @return The string containing an XML dump.
     * @throws PersistanceQueryException
     */
    public void dumpXML(OutputStream out) throws PersistanceException;
}
