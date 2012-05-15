/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicJDOSession.java
 */

// package path
package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.jdo.JDOSession;
import com.rift.coad.rdf.semantic.jdo.basic.sparql.BasicJDOSPARQLQuery;
import com.rift.coad.rdf.semantic.jdo.obj.ClassInfo;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.session.UnknownEntryException;
import com.rift.coad.rdf.semantic.util.ClassURIBuilder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import org.apache.log4j.Logger;

/**
 * The basic jdo session.
 *
 * @author brett chaldecott
 */
public class BasicJDOSession implements JDOSession {

    // class singletons
    private static Logger log = Logger.getLogger(BasicJDOSession.class);

    // private member variables
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;


    /**
     * The basic jdo session implementation of the basic jdo session.
     *
     * @param persistanceSession The persistance session
     * @param ontologySession The ontology session.
     */
    public BasicJDOSession(PersistanceSession persistanceSession, OntologySession ontologySession) {
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
    }


    /**
     * The reference to the persistance.
     *
     * @param in The input stream.
     * @throws SessionException
     */
    public void persist(InputStream in) throws SessionException {
        try {
            persistanceSession.persist(in);
        } catch (Exception ex) {
            log.error("Failed to persist the jdo session : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the jdo session : " + ex.getMessage(),ex);
        }
    }


    /**
     * The persistance method for a string.
     *
     * @param rdf The string containing the persistance information.
     * @throws SessionException
     */
    public void persist(String rdf) throws SessionException {
        try {
            persistanceSession.persist(rdf);
        } catch (Exception ex) {
            log.error("Failed to persist the jdo session : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the jdo session : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method persists the object provided and returns a reference to the
     * newly peristed object.
     *
     * @param <T> The object type to persist.
     * @param source The source object to persist.
     * @return The reference to the newly persisted object.
     * @throws SessionException
     */
    public <T> T persist(T source) throws SessionException {
        try {
            BasicJDOPersistanceHandler handler = new BasicJDOPersistanceHandler(
                    source,persistanceSession, ontologySession);
            PersistanceResource resource = handler.persist();
            return BasicJDOProxyFactory.createJDOProxy(source.getClass(), 
                    persistanceSession, resource, ontologySession);
        } catch (Exception ex) {
            log.error("Failed to persist the object : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the object : " + ex.getMessage(),ex);
        }
    }
    
    

    /**
     * The XML to dump to a string.
     *
     * @return The string containing the XML dump.
     * @throws SessionException
     */
    public String dumpXML() throws SessionException {
        try {
            return persistanceSession.dumpXML();
        } catch (Exception ex) {
            log.error("Failed to dump the xml : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to dump the xml : " + ex.getMessage(),ex);
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
            persistanceSession.dumpXML(out);
        } catch (Exception ex) {
            log.error("Failed to dump the xml : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to dump the xml : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to remove the object target.
     *
     * @param <T> The type of object to remove.
     * @param target The target
     * @return The target to remove.
     * @throws SessionException
     */
    public <T> T remove(T target) throws SessionException {
        try {
            Resource resource = (Resource)target;
            persistanceSession.removeResource(resource.getURI());
            return target;
        } catch (Exception ex) {
            log.error("Failed to remove the : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method removes the entry identifed by the uri.
     * 
     * @param uri The URI to remove.
     * @throws SessionException
     */
    public void remove(URI uri) throws SessionException {
        try {
            persistanceSession.removeResource(uri);
        } catch (Exception ex) {
            log.error("Failed to remove the resource : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the resource : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the RDF identifed by the rdf string.
     *
     * @param rdf The string containing the RDF to remove.
     * @throws SessionException
     */
    public void removeRDF(String rdf) throws SessionException {
        try {
            persistanceSession.removeRDF(rdf);
        } catch (Exception ex) {
            log.error("Failed to remove the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * The method removes the RDF information identified by the stream.
     *
     * @param out The stream containing the RDF information to remove.
     * @throws SessionException
     */
    public void removeRDF(InputStream in) throws SessionException {
        try {
            persistanceSession.removeRDF(in);
        } catch (Exception ex) {
            log.error("Failed to remove the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
        }
    }

    

    /**
     * This method returns the reference to the object.
     *
     * @param <T> The type of object.
     * @param c The class type.
     * @param identifier The identifier.
     * @return The return type.
     * @throws SessionException
     * @throws UnknownEntryException
     */
    public <T> T get(Class<T> c, Serializable identifier) throws SessionException, UnknownEntryException {
        try {
            URI resourceUri = null;
            if (Resource.class.isAssignableFrom(c) && identifier instanceof URI) {
                return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                    persistanceSession, persistanceSession.getResource((URI)identifier),
                    ontologySession);
            } if (Resource.class.isAssignableFrom(c) && identifier instanceof String) {
                return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                    persistanceSession, persistanceSession.getResource(new URI(identifier.toString())),
                    ontologySession);
            } else {
                resourceUri = ClassURIBuilder.generateClassURI(c,identifier);
            }
            return BasicJDOProxyFactory.createJDOProxy(c,
                    persistanceSession, persistanceSession.getResource(resourceUri),
                    ontologySession);
        } catch (Exception ex) {
            log.error("Failed to get the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to get the resource identified by the RDF XML : " +
                    ex.getMessage(),ex);
        }
    }
    
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
    public Resource createResource(URI identifier) 
            throws SessionException, UnknownEntryException {
        try {
            PersistanceResource resource = persistanceSession.createResource(
                    identifier);
            return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                    persistanceSession, resource,
                    ontologySession);
        } catch (Exception ex) {
            log.error("Failed to create the resource : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed create get the resource : " +
                    ex.getMessage(),ex);
        }
    }
    
    
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
    public Resource createResource(URI typeURI, URI identifer) 
            throws SessionException, UnknownEntryException {
        try {
            PersistanceResource typeResource = 
                    persistanceSession.createResource(typeURI);
            PersistanceResource resource = persistanceSession.createResource(
                    identifer,typeResource);
            return BasicJDOProxyFactory.createJDOProxy(Resource.class,
                    persistanceSession, resource,
                    ontologySession);
        } catch (Exception ex) {
            log.error("Failed to create the resource : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed create get the resource : " +
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns true if the class is found
     * 
     * @param c The class found
     * @param identifier The identifier
     * @return TRUE if found, FALSE if not.
     * @throws SessionException
     * @throws UnknownEntryException 
     */
    public boolean contains(Class c, Serializable identifier)
            throws SessionException, UnknownEntryException {
        try {
            
            URI resourceUri = null;
            if (Resource.class.isAssignableFrom(c) && identifier instanceof URI) {
                resourceUri = (URI)identifier;
            } else if (Resource.class.isAssignableFrom(c) && identifier instanceof URI) {
                throw new SessionException("If checking for a resource must provide a URI as an identifier");
            } else {
                resourceUri = ClassURIBuilder.generateClassURI(c,identifier);
            }
            return persistanceSession.hasResource(resourceUri);
        } catch (Exception ex) {
            log.error("Failed to check for the resource identified by the class type and identifer : " +
                    ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to check for the resource identified by the class type and identifer : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates a query
     *
     * @param queryString
     * @return
     * @throws SessionException
     */
    public Query createQuery(String queryString) throws SessionException {
        throw new UnsupportedOperationException("This object query is currently unsupported");
    }


    /**
     * This 
     * @param queryString
     * @return
     * @throws SessionException
     */
    public SPARQLQuery createSPARQLQuery(String queryString) throws SessionException {
        try {
            return new BasicJDOSPARQLQuery(persistanceSession,ontologySession,queryString);
        } catch (Exception ex) {
            log.error("Failed to create they query : " + ex.getMessage(),ex);
            throw new SessionException("Failed to create they query : " + ex.getMessage(),ex);
        }
    }

}
