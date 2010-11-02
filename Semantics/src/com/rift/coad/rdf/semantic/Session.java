/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * Session.java
 */

// package path
package com.rift.coad.rdf.semantic;

// java imports
import java.io.Serializable;
import java.io.InputStream;
import java.io.OutputStream;

// coaduntion imports
import com.rift.coad.rdf.semantic.session.UnknownEntryException;

/**
 * This object represents a session object.
 *
 * @author brett chaldecott
 */
public interface Session {
    /**
     * This method creates a new transaction for this session.
     *
     * @return The transaction to manage this session.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Transaction getTransaction() throws SessionException;

    /**
     * This method is called to persist the stream changes to this semantic session.
     *
     * @param in The input stream containing the changes to persist to this session.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void persist(InputStream in) throws SessionException;


    /**
     * This method is called to persist the RDF information to the session.
     *
     * @param rdf The string containing the rdf information to persist.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void persist(String rdf) throws SessionException;


    /**
     * The object to persist to the modle.
     * @param obj
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Resource persist(Object obj) throws SessionException;


    /**
     * This method dumps the session information in the format specified to the output string.
     *
     * @param format The format to dump the RDF model in.
     * @return The string containing the dump RDF model.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public String dump(String format) throws SessionException;

    /**
     * This method dumps the RDF module to the output string.
     *
     * @param out The output stream to dump the model to.
     * @param format The format of the output string to dump.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void dump(OutputStream out, String format) throws SessionException;


    /**
     * This method is called to remove the object.
     * @param obj The object to remove the the store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(Object obj) throws SessionException;


    /**
     * This method is called to remove the specified entries from the store.
     *
     * @param rdf The rdf containing the information to remove from the store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(String rdf) throws SessionException;


    /**
     * This method returns an instance of the specified object type.
     *
     * @param c The class type to perfrom the operation on.
     * @param objectType The type to retrieve from the database.
     * @return An instance of the requested type.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T getType(Class<T> c, String objectType) throws SessionException, UnknownEntryException;


    /**
     * This method returns the object identified by the information passed in.
     * @param <T> The type.
     * @param c The class to perform the cast to.
     * @param objectType The object type instance that the relevant search information will be retrieved from.
     * @param identifier The unique identifier to perform the search for.
     * @return The result of the get.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T get(Class<T> c, Object objectType,  Serializable identifier) throws SessionException, UnknownEntryException;


    /**
     * This method returns the specified object type identified by the identifier.
     *
     * @param c The class type that is getting loaded.
     * @param objectType The unquie object identifier to retrieve the identifier from the store for.
     * @param identifier The identifier to uniquely retrieve the object for.
     * @return The object to retrieve.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T get(Class<T> c, String objectType,  Serializable identifier) throws SessionException, UnknownEntryException;


    /**
     * This method returns a reference to the identified resource.
     * @param c The class type the resource is getting retrieved for.
     * @param objectType The object type information for the resource.
     * @param identifier The unique identifier for the object.
     * @return The resource to retrieve.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public Resource getResource(Class c, Object objectType,  Serializable identifier) throws SessionException, UnknownEntryException;


    /**
     * This method retrieves the resource identfied by the object type.
     * @param c
     * @param objectType
     * @param identifier
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public Resource getResource(Class c, String objectType,  Serializable identifier) throws SessionException, UnknownEntryException;


    /**
     * This method is responsible for initing the object query.
     *
     * @param c The class type to initialize.
     * @param objectName The name of the object to init.
     * @return The initialized object.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T createInstance(Class<T> c, String objectType) throws SessionException, UnknownEntryException;


    /**
     * This method is responsible for creating a new query that can be execute on the data store.
     *
     * @param queryString The query to execute on the data store.
     * @return The query object.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Query createQuery(String queryString) throws SessionException;


    /**
     * This method creates a SPARQL query to execute on the model.
     *
     * @param queryString The SPARQL query to execute.
     * @return The query object that will execute the query.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public SPARQLQuery createSPARQLQuery(String queryString) throws SessionException;
    
}
