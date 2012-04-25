/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.jdo;

import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.session.UnknownEntryException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;

/**
 * The definition of the JDO session.
 * 
 * @author brett chaldecott
 */
public interface JDOSession {
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
    public <T> T persist(T source) throws SessionException;


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
     * @param uri The uri information.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(URI uri) throws SessionException;


    /**
     * This method removes the RDF identifed by the rdf string.
     *
     * @param rdf The string containing the RDF to remove.
     * @throws SessionException
     */
    public void removeRDF(String rdf) throws SessionException;


    /**
     * The method removes the RDF information identified by the stream.
     *
     * @param in The stream to remove.
     * @throws SessionException
     */
    public void removeRDF(InputStream in) throws SessionException;
    
    
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
     * This method creates the object.
     * 
     * @param <T> The reference to the object.
     * @param c The class.
     * @param identifier The identifier.
     * @return The object.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public Resource createResource(URI typeURI, URI itentifier) 
            throws SessionException, UnknownEntryException;
    
    
    /**
     * This method returns true if the store contains the specified class.
     * 
     * @param c The class to identify
     * @param identifier The idnetifier.
     * @return TRUE if found, FALSE if not.
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
