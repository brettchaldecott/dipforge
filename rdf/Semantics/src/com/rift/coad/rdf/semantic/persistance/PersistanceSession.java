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
 * PersistanceSession.java
 */

// package scope
package com.rift.coad.rdf.semantic.persistance;

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
    public PersistanceTransaction beginTransaction() throws 
            PersistanceException, PersistanceUnknownException;


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
}
