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
package com.rift.coad.rdf.semantic.persistance.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceQuery;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.persistance.PersistanceTransaction;
import java.io.ByteArrayOutputStream;
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
    private Model jenaModel;
    private JenaPersistanceTransaction transaction;

    /**
     * The protected constructor of the jena persistance session object.
     * 
     * @param jenaModel
     */
    protected JenaPersistanceSession(Model jenaModel) {
        this.jenaModel = jenaModel;
        transaction = new JenaPersistanceTransaction(jenaModel);
    }



    /**
     * This method returns a reference to the newly created transaction.
     *
     * @return The reference to the transaction.
     * @throws PersistanceException
     */
    public PersistanceTransaction beginTransaction() throws PersistanceException {
        return transaction;
    }


    /**
     * This method returns the resource information.
     *
     * @param uri The uri for the resource.
     * @return The reference to the resource.
     * @throws PersisanceException
     */
    public PersistanceResource getResource(URI uri) throws PersistanceException {
        try {
            return new JenaPersistanceResource(
                    jenaModel.getResource(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to retrieve the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to retrieve the resource because : " +
                    ex.getMessage(),ex);
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
            throws PersistanceException {
        try {
            return new JenaPersistanceResource(
                    jenaModel.getResource(identifier.toURI().toString()));
        } catch (Exception ex) {
            log.error("Failed to retrieve the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to retrieve the resource because : " +
                    ex.getMessage(),ex);
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
            return new JenaPersistanceResource(
                    jenaModel.createResource(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to create the resource because : " +
                    ex.getMessage(),ex);
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
            return new JenaPersistanceResource(
                    jenaModel.createResource(identifier.toURI().toString()));
        } catch (Exception ex) {
            log.error("Failed to create the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to create the resource because : " +
                    ex.getMessage(),ex);
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
            Resource resource = jenaModel.createResource(uri.toString());
            jenaModel.removeAll(resource, null, null).
                    removeAll(resource, null, resource);
        } catch (Exception ex) {
            log.error("Failed to remove the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to remove the resource because : " +
                    ex.getMessage(),ex);
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
            Resource resource = jenaModel.createResource(
                    identifier.toURI().toString());
            jenaModel.removeAll(resource, null, null).
                    removeAll(resource, null, resource);
        } catch (Exception ex) {
            log.error("Failed to remove the resource because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to remove the resource because : " +
                    ex.getMessage(),ex);
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
        return new JenaPersistanceQuery(jenaModel, queryStr);
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
            jenaModel.write(out);
            return out.toString();
        } catch (Exception ex) {
            log.error("Failed to dump the store to xml because : " +
                    ex.getMessage(),ex);
            throw new PersistanceException(
                    "Failed to dump the store to xml because : " +
                    ex.getMessage(),ex);
        }
    }
}
