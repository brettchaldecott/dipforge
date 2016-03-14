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
package com.rift.coad.rdf.semantic.persistance.jena;

import org.apache.jena.rdf.model.*;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceQuery;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.persistance.PersistanceTransaction;
import com.rift.coad.rdf.semantic.persistance.PersistanceUnknownException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import org.apache.log4j.Logger;

/**
 * This interface defines the method to manage the perstance to the backend data
 * store.
 *
 * @author brett chaldecott
 */
public class JenaPersistanceSession implements PersistanceSession {

    // private static variables
    private static Logger log = Logger.getLogger(JenaPersistanceSession.class);
    // private member variables
    private JenaModelWrapper jenaModel;
    private JenaPersistanceTransaction transaction;

    /**
     * The protected constructor of the jena persistance session object.
     * 
     * @param jenaModel The model the perform the operations on.
     * @param storeType The type of store that is being used.
     */
    protected JenaPersistanceSession(JenaModelWrapper jenaModel, JenaStoreType storeType) {
        this.jenaModel = jenaModel;
        transaction = new JenaPersistanceTransaction(jenaModel,storeType);
    }
    
    /**
     * The protected constructor of the jena persistance session object.
     * 
     * @param jenaModel The model to perform the operations on.
     * @param storeType The type of store to utilize.
     * @param lock The type of lock to utilize on this object,
     */
    protected JenaPersistanceSession(JenaModelWrapper jenaModel,
            SessionManager.SessionLock lock) {
        this.jenaModel = jenaModel;
        transaction = new JenaPersistanceTransaction(jenaModel,lock);
    }

    
    /**
     * This method returns a reference to the transaction.
     *
     * @return The reference to the transaction.
     * @throws PersistanceException
     */
    public PersistanceTransaction getTransaction() throws PersistanceException {
        return transaction;
    }

    /**
     * This method is called to persist the stream changes to this semantic session.
     *
     * @param in The input stream containing the changes to persist to this session.
     * @throws PersistanceException
     */
    public void persist(InputStream in) throws PersistanceException {
        try {
            Model tempStore = ModelFactory.createDefaultModel();
            tempStore.read(in, null);
            
            for (ResIterator node = tempStore.listSubjects(); node.hasNext();) {
                RDFNode rdfNode = node.next();
                if (!rdfNode.isResource()) {
                    continue;
                }
                if (!(rdfNode.asResource().listProperties().toList().size() > 1)) {
                    log.debug("####### The rdf node has no properties ignoring it [" + rdfNode.toString() + "]");
                    continue;
                }
                if (jenaModel.getModel().contains(rdfNode.asResource(), null)) {
                    log.debug("####### The remove the node value is [" + rdfNode.toString() + "]");
                    jenaModel.getModel().removeAll(rdfNode.asResource(), null, null);
                }
            }
            // add all statements
            jenaModel.getModel().add(tempStore.listStatements().toList());
            in.close();
        } catch (Exception ex) {
            log.error("Failed to persist the stream : " + ex.getMessage(), ex);
            throw new PersistanceException("Failed to persist the stream : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method is called to persist the RDF information to the session.
     *
     * @param rdf The string containing the rdf information to persist.
     * @throws PersistanceException
     */
    public void persist(String rdf) throws PersistanceException {
        try {
            // this method attempts to remove old conflicting properties before
            // persisting. this is to work around a bug with the SDB libraries
            log.debug("###### Persisting the RDF");
            ByteArrayInputStream in = new ByteArrayInputStream(rdf.getBytes());
            persist(in);
        } catch (Exception ex) {
            log.error("Failed to persist the stream : " + ex.getMessage(), ex);
            throw new PersistanceException("Failed to persist the stream : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns true if the resource pointed to by the uri exists.
     *
     * @param uri The resource uri
     * @return TRUE if there is a resource identified by the URI, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasResource(URI uri) throws
            PersistanceException {
        try {
            return jenaModel.getModel().containsResource(
                    jenaModel.getModel().getResource(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to determine if the resource exists : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to determine if the resource exists : "
                    + ex.getMessage(), ex);
        }
    }
    

    /**
     * This method returns true if there is a resource identified by the URI and FALSE if there is not.
     *
     * @param identifier The resource identifier.
     * @return TRUE if found, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasResource(PersistanceIdentifier identifier) throws
            PersistanceException {
        try {
            return jenaModel.getModel().containsResource(
                    jenaModel.getModel().getResource(identifier.toURI().toString()));
        } catch (Exception ex) {
            log.error("Failed to determine if the resource exists : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to determine if the resource exists : "
                    + ex.getMessage(), ex);
        }
    }
    

    /**
     * This method returns the resource information.
     *
     * @param uri The uri for the resource.
     * @return The reference to the resource.
     * @throws PersisanceException
     */
    public PersistanceResource getResource(URI uri) throws PersistanceException,
            PersistanceUnknownException {
        try {
            if (!jenaModel.getModel().containsResource(
                    jenaModel.getModel().getResource(uri.toString()))) {
                log.error("The resource [" + uri.toString() + "] does not exist.");
                throw new PersistanceUnknownException(
                        "The resource [" + uri.toString() + "] does not exist.");
            }
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().getResource(uri.toString()));
        } catch (PersistanceUnknownException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to retrieve the resource because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the resource identified by the persistance identifer.
     *
     * @param uri The identifier of the resource to retrieve from the store.
     * @return The reference to the newly retrieved resource.
     * @throws PersistanceException
     */
    public PersistanceResource getResource(PersistanceIdentifier identifier)
            throws PersistanceException, PersistanceUnknownException {
        try {
            if (!jenaModel.getModel().containsResource(
                    jenaModel.getModel().getResource(identifier.toURI().toString()))) {
                log.error("The resource [" + identifier.toString() + "] does not exist.");
                throw new PersistanceUnknownException(
                        "The resource [" + identifier.toString() + "] does not exist.");
            }
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().getResource(identifier.toURI().toString()));
        } catch (PersistanceUnknownException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to retrieve the resource because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method creates a new resource.
     *
     * @param The URI that identifies the new resource
     * @return The reference to the newly created resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(URI uri)
            throws PersistanceException {
        try {
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().createResource(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to create the resource because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method creates a new resource.
     *
     * @param identifier The identifier for the new resource.
     * @return The reference to the newly created resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(PersistanceIdentifier identifier)
            throws PersistanceException {
        try {
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().createResource(identifier.toURI().toString()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to create the resource because : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method creates a resource linked to the given type.
     *
     * @param uri The uri of the resource to link to.
     * @param resourceType The resource type.
     * @return The reference to the persistance resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(URI uri, PersistanceResource resourceType)
            throws PersistanceException {
        try {
            JenaPersistanceResource jenaResource = (JenaPersistanceResource)resourceType;
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().createResource(uri.toString(),jenaResource.getResource()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to create the resource because : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method creates a new resource identified by the identifier and ties it to a given resource type.
     *
     * @param identifier The identifier of the resource.
     * @param resourceType The resource type.
     * @return The reference to the newly created persistence resource.
     * @throws PersistanceException
     */
    public PersistanceResource createResource(PersistanceIdentifier identifier, PersistanceResource resourceType)
            throws PersistanceException {
        try {
            JenaPersistanceResource jenaResource = (JenaPersistanceResource)resourceType;
            return new JenaPersistanceResource(jenaModel.getModel(),
                    jenaModel.getModel().createResource(identifier.toURI().toString(),jenaResource.getResource()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to create the resource because : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method remoes a new resource.
     *
     * @param The URI that identifies the resource
     * @throws PersistanceException
     */
    public void removeResource(URI uri)
            throws PersistanceException {
        try {
            Resource resource = jenaModel.getModel().createResource(uri.toString());
            jenaModel.getModel().removeAll(resource, null, null).
                    removeAll(resource, null, null);
        } catch (Exception ex) {
            log.error("Failed to remove the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to remove the resource because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method removes a resource.
     *
     * @param identifier The identifier for the resource.
     * @throws PersistanceException
     */
    public void removeResource(PersistanceIdentifier identifier)
            throws PersistanceException {
        try {
            Resource resource = jenaModel.getModel().createResource(
                    identifier.toURI().toString());
            jenaModel.getModel().removeAll(resource, null, null).
                    removeAll(resource, null, resource);
        } catch (Exception ex) {
            log.error("Failed to remove the resource because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to remove the resource because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method removes the RDF identifed by the rdf string.
     *
     * @param rdf The string containing the RDF to remove.
     * @throws PersistanceException
     */
    public void removeRDF(String rdf) throws PersistanceException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(rdf.getBytes());
            Model tempStore = ModelFactory.createDefaultModel();
            tempStore.read(in, null);
            for (ResIterator node = tempStore.listSubjects(); node.hasNext();) {
                RDFNode rdfNode = node.next();
                if (!rdfNode.isResource()) {
                    continue;
                }
                if (!(rdfNode.asResource().listProperties().toList().size() > 1)) {
                    log.debug("####### The rdf node has no properties ignoring it [" + rdfNode.toString() + "]");
                    continue;
                }
                if (jenaModel.getModel().contains(rdfNode.asResource(), null)) {
                    log.debug("####### The remove the node value is [" + rdfNode.toString() + "]");
                    jenaModel.getModel().removeAll(rdfNode.asResource(), null, null);
                }
            }
            in.close();
        } catch (Exception ex) {
            log.error("Failed to remove the rdf from the store : " + ex.getMessage(), ex);
            throw new PersistanceException("Failed to remove the rdf from the store : " + ex.getMessage(), ex);
        }
    }

    /**
     * The method removes the RDF information identified by the stream.
     *
     * @param out The stream containing the RDF information to remove.
     * @throws PersistanceException
     */
    public void removeRDF(InputStream in) throws PersistanceException {
        try {
            Model tempStore = ModelFactory.createDefaultModel();
            tempStore.read(in, null);
            jenaModel.getModel().remove(tempStore);
        } catch (Exception ex) {
            log.error("Failed to remove the rdf from the store : " + ex.getMessage(), ex);
            throw new PersistanceException("Failed to remove the rdf from the store : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the query interface object.
     *
     * @param queryStr The query string.
     * @return The resulting query string.
     * @throws PersistanceQueryException
     */
    public PersistanceQuery createQuery(String queryStr)
            throws PersistanceQueryException {
        return new JenaPersistanceQuery(jenaModel.getModel(), queryStr);
    }

    /**
     * This method dumps the model to an xml format.
     * 
     * @return The string containing an XML dump.
     * @throws PersistanceQueryException
     */
    public String dumpXML() throws PersistanceException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            jenaModel.getModel().write(out);
            return out.toString();
        } catch (Exception ex) {
            log.error("Failed to dump the store to xml because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to dump the store to xml because : "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * This method dumps the model to an xml format.
     *
     * @return The string containing an XML dump.
     * @throws PersistanceQueryException
     */
    public void dumpXML(OutputStream out) throws PersistanceException {
        try {
            jenaModel.getModel().write(out);
        } catch (Exception ex) {
            log.error("Failed to dump the store to xml because : "
                    + ex.getMessage(), ex);
            throw new PersistanceException(
                    "Failed to dump the store to xml because : "
                    + ex.getMessage(), ex);
        }
    }
}
