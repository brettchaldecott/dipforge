/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicSession.java
 */

// the package path
package com.rift.coad.rdf.semantic.session;

// java imports
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.net.URI;

// log4j imports
import org.apache.log4j.Logger;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
//import thewebsemantic.Bean2RDF;
//import thewebsemantic.RDF2Bean;
//import thewebsemantic.Sparql;
import static com.hp.hpl.jena.graph.Node.ANY;
import static com.hp.hpl.jena.graph.Node.createURI;
import com.rift.coad.rdf.semantic.*;

// coadunation imports
import com.rift.coad.rdf.semantic.basic.*;
import com.rift.coad.rdf.semantic.annotation.helpers.NamespaceHelper;
import com.rift.coad.rdf.semantic.annotation.helpers.LocalNameHelper;
import com.rift.coad.rdf.semantic.jdo.JDOSession;
import com.rift.coad.rdf.semantic.jdo.basic.sparql.BasicJDOSPARQLQuery;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.resource.BasicResource;
import com.rift.coad.rdf.semantic.util.BeanCopy;
import java.net.URI;

/**
 * This object represents a basic session.
 *
 * @author brett chaldecott
 */
public class BasicSession implements Session {
    // class singletons
    private static Logger log = Logger.getLogger(BasicSession.class);

    // private member variables
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;
    private JDOSession jdoSession;
    private Transaction transaction;


    /**
     * This constructor sets up a new
     *
     * @param persistanceSession
     * @param ontologySession
     */
    public BasicSession(PersistanceSession persistanceSession, OntologySession ontologySession,
            JDOSession jdoSession)
            throws SessionException {
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
        this.jdoSession = jdoSession;
        try {
            transaction = new BasicTransaction(persistanceSession,ontologySession);
        } catch (Exception ex) {
            log.error("Failed to instanciate the basic session : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to instanciate the basic session : " + ex.getMessage(),ex);
        }
    }


    /**
     * The transaction object
     *
     * @return The reference to the transaction object
     * @throws SessionException
     */
    public Transaction getTransaction() throws SessionException {
        return transaction;
    }

    
    /**
     * This method returns a reference to the ontology session object.
     * 
     * @return The reference to the ontology session.
     * @throws SessionException 
     */
    public OntologySession getOntologySession() throws SessionException {
        return this.ontologySession;
    }
    
    
    /**
     * This method is called to persist a stream
     *
     * @param in The input stream to persist.
     * @throws SessionException
     */
    public void persist(InputStream in) throws SessionException {
        try {
            jdoSession.persist(in);
        } catch (Exception ex) {
            log.error("Failed to persist the stream : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to persist the stream : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method persists the value.
     *
     * @param rdf The rdf value to persist.
     * @throws SessionException
     */
    public void persist(String rdf) throws SessionException {
        try {
            jdoSession.persist(rdf);
        } catch (Exception ex) {
            log.error("Failed to persist the rdf : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to persist the rdf : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method is used to persist the object.
     *
     * @param <T> The object type to persist and return.
     * @param object The value to persist
     * @return The reference to the object.
     * @throws SessionException
     */
    public <T> T persist(T object) throws SessionException {
        try {
            return jdoSession.persist(object);
        } catch (Exception ex) {
            log.error("Failed to persist the rdf : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to persist the rdf : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method dumps the xml.
     *
     * @return The xml that is being dumped.
     * @throws SessionException
     */
    public String dumpXML() throws SessionException {
        try {
            return jdoSession.dumpXML();
        } catch (Exception ex) {
            log.error("Failed to dump the xml : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to dump the xml : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method dumps the xml to an output stream.
     *
     * @param out The output stream.
     * @throws SessionException
     */
    public void dumpXML(OutputStream out) throws SessionException {
        try {
            jdoSession.dumpXML(out);
        } catch (Exception ex) {
            log.error("Failed to dump the xml : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to dump the xml : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the target.
     * 
     * @param <T> The object type to remove.
     * @param target The target to remove.
     * @return The removed object
     * @throws SessionException
     */
    public <T> T remove(T target) throws SessionException {
        try {
            return jdoSession.remove(target);
        } catch (Exception ex) {
            log.error("Failed to remove the object : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to remove the object : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the data identified by the rdf.
     *
     * @param rdf The rdf to remove
     * @throws SessionException
     */
    public void remove(String rdf) throws SessionException {
        try {
            jdoSession.removeRDF(rdf);
        } catch (Exception ex) {
            log.error("Failed to remove the rdf : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to remove the rdf : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method gets the session.
     *
     * @param <T> The type of object being delt with.
     * @param c The class to return
     * @param identifier The identifier.
     * @return The result object.
     * @throws SessionException
     * @throws UnknownEntryException
     */
    public <T> T get(Class<T> c, Serializable identifier) throws SessionException, UnknownEntryException {
        try {
            return jdoSession.get(c,identifier);
        } catch (Exception ex) {
            log.error("Failed to remove the rdf : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to remove the rdf : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    
    
    /**
     * This method is called t o create the resource identified by the URI
     * 
     * @param identifier The uri identifier
     * @return The resource reference.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public Resource createResource(URI identifier) throws SessionException, UnknownEntryException {
        try {
            return jdoSession.createResource(identifier);
        } catch (Exception ex) {
            log.error("Failed to create the resource : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to create the resource: " +
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to create the resource identified by the string.
     * 
     * @param typeURI The type uri
     * @param identifier The identifier of the class.
     * @return The reference to the instance.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public Resource createResource(URI typeURI, URI itentifier) 
            throws SessionException, UnknownEntryException {
        try {
            return jdoSession.createResource(typeURI,itentifier);
        } catch (Exception ex) {
            log.error("Failed to create the resource : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to create the resource: " +
                    ex.getMessage(),ex);
        }
    }
    
    
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
            SessionException, UnknownEntryException {
        return disconnect(c, value, true);
    }
    
    
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
            SessionException, UnknownEntryException {
        try {
            if (!c.isInstance(value)) {
                throw new SessionException("The types do not match");
            }
            return BeanCopy.copy(c, value);
        } catch (Exception ex) {
            log.error(
                    "Failed to disconnect the rdf object and return a copy : " +
                    ex.getMessage(),ex);
            throw new SessionException(
                    "Failed to disconnect the rdf object and return a copy : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This returns true if the object can be found.
     * 
     * @param c The class to find.
     * @param identifier The identifier.
     * @return TRUE if found.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public boolean contains(Class c, Serializable identifier) 
            throws SessionException, UnknownEntryException {
        try {
            return jdoSession.contains(c,identifier);
        } catch (Exception ex) {
            log.error("Failed to check if the object exists : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to check if the object exists : " +
                    ex.getMessage(),ex);
        }
    }

    /**
     * This method creates a new query.
     *
     * @param queryString The query string
     * @return The resulting query object.
     * @throws SessionException
     */
    public Query createQuery(String queryString) throws SessionException {
        try {
            return jdoSession.createQuery(queryString);
        } catch (Exception ex) {
            log.error("Failed to create the query : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to create the query : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     *
     * @param queryString
     * @return
     * @throws SessionException
     */
    public SPARQLQuery createSPARQLQuery(String queryString) throws SessionException {
        try {
            return jdoSession.createSPARQLQuery(queryString);
        } catch (Exception ex) {
            log.error("Failed to create the query : " +
                    ex.getMessage(),ex);
            throw new SessionException("Failed to create the query : " +
                    ex.getMessage(),ex);
        }
    }

   
    
}
