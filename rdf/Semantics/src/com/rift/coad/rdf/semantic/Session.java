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
import com.rift.coad.rdf.semantic.ontology.OntologySession;
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
     * This method returns a reference to the ontology session object.
     * 
     * @return The reference to the ontology session.
     * @throws SessionException 
     */
    public OntologySession getOntologySession() throws SessionException;
    
    
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
    public <T> T persist(T object) throws SessionException;


    /**
     * This method dumps the session information in the format specified to the output string.
     *
     * @return The string containing the dump RDF model.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public String dumpXML() throws SessionException;


    /**
     * This method dumps the RDF module to the output string.
     *
     * @param out The output stream to dump the model to.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void dumpXML(OutputStream out) throws SessionException;


    /**
     * This method is called to remove the object.
     * @param obj The object to remove the the store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public <T> T remove(T target) throws SessionException;


    /**
     * This method is called to remove the specified entries from the store.
     *
     * @param rdf The rdf containing the information to remove from the store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(String rdf) throws SessionException;


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
    public <T> T get(Class <T> c, Serializable identifier) throws SessionException, UnknownEntryException;
    
    
    /**
     * This method returns the disconnected object. This method always deep copies.
     * 
     * @param <T> The reference to the diconnected object
     * @param c The object type to return.
     * @param value The value.
     * @return The result of the call
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public <T> T disconnect(Class<T> c, Object value) throws
            SessionException, UnknownEntryException;
    
    
    /**
     * This method returns a disconnected object. It will deep copy the object
     * told to.
     * 
     * @param <T> The type of object to return.
     * @param c The type.
     * @param value The value
     * @param deapCopy If TRUE deep copy.
     * @return The reference to the disconnected object.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public <T> T disconnect(Class<T> c, Object value, boolean deepCopy) throws
            SessionException, UnknownEntryException;
    
    
    /**
     * This method returns the true if the object can be found.
     * 
     * @param c The class type to look for.
     * @param identifier The identifier for the class.
     * @return TRUE if it exists. FALSE if not.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public boolean contains(Class c, Serializable identifier)
            throws SessionException, UnknownEntryException;
    
    
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
