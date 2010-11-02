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

// log4j imports
import org.apache.log4j.Logger;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import thewebsemantic.Sparql;
import static com.hp.hpl.jena.graph.Node.ANY;
import static com.hp.hpl.jena.graph.Node.createURI;

// coadunation imports
import com.rift.coad.rdf.semantic.basic.*;
import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.Transaction;
import com.rift.coad.rdf.semantic.annotation.ManagementObjectHelper;
import com.rift.coad.rdf.semantic.annotation.MemberVariableHelper;
import com.rift.coad.rdf.semantic.annotation.NamespaceHelper;
import com.rift.coad.rdf.semantic.annotation.ObjTypeIdHelper;
import com.rift.coad.rdf.semantic.annotation.RDFTypeHelper;
import com.rift.coad.rdf.semantic.annotation.URIHelper;
import com.rift.coad.rdf.semantic.query.BasicQuery;
import com.rift.coad.rdf.semantic.query.BasicSPARQLQuery;
import com.rift.coad.rdf.semantic.resource.BasicResource;

/**
 * This object represents a basic session.
 *
 * @author brett chaldecott
 */
public class BasicSession implements Session {
    // class singletons
    private static Logger log = Logger.getLogger(BasicSession.class);

    // private member variables
    private Model config;
    private RDF2Bean configBeanReader;
    private Model store;
    private Bean2RDF storeBeanWritter;
    private RDF2Bean storeBeanReader;
    private com.rift.coad.rdf.semantic.Transaction transaction;


    /**
     * This constructor sets the model configuration and store information.
     *
     * @param config The store that the configuration will be pulled from.
     * @param store The store that the changes will be made to.
     */
    public BasicSession(Model config, Model store) throws Exception {
        this.config = config;
        this.store = store;
        if (config != null) {
            configBeanReader = new RDF2Bean(config);
        }
        storeBeanReader = new RDF2Bean(store);
        storeBeanWritter = new Bean2RDF(store);
        transaction = new BasicTransaction(store);
    }


    /**
     * This constructor sets the model configuration and store information.
     *
     * @param config The store that the configuration will be pulled from.
     * @param store The store that the changes will be made to.
     * @param transaction The transaction to manage this object
     */
    public BasicSession(Model config, Model store, com.rift.coad.rdf.semantic.Transaction transaction)
            throws Exception {
        this.config = config;
        this.store = store;
        if (config != null) {
            configBeanReader = new RDF2Bean(config);
        }
        storeBeanReader = new RDF2Bean(store);
        storeBeanWritter = new Bean2RDF(store);
        this.transaction = transaction;
    }


    /**
     * This method creates a new transaction for this session.
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public com.rift.coad.rdf.semantic.Transaction getTransaction() throws SessionException {
        return transaction;
    }


    /**
     * This method is responsible for persisting an input string
     *
     * @param in The input stream to persist.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void persist(InputStream in) throws SessionException {
        try {
            store.read(in, null);
        } catch (Exception ex) {
            log.error("Failed to persist the store information :" + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the store information :" + ex.getMessage(),ex);
        }
    }


    /**
     * This method persists an rdf string to the store.
     * 
     * @param rdf The string containing the RDF information to persist
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void persist(String rdf) throws SessionException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(rdf.getBytes());
            store.read(in, null);
            in.close();
        } catch (Exception ex) {
            log.error("Failed to persist the store information :" + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the store information :" + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is responsible for persisting the supplied object to the RDF store.
     *
     * @param obj The object to persist.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public com.rift.coad.rdf.semantic.Resource persist(Object obj) throws SessionException {
        try {
            return new BasicResource(store,storeBeanWritter,storeBeanReader,
                    this.storeBeanWritter.save(obj));
        } catch (Exception ex) {
            log.error("Failed to persist the store information :" + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to persist the store information :" + ex.getMessage(),ex);
        }
    }


    /**
     * This method dumps the store contents to string.
     * @param format
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public String dump(String format) throws SessionException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.store.write(out, format);
            return out.toString();
        } catch (Exception ex) {
            log.error("Failed to dump the contents of the string : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to dump the contents of the string : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for dumping the contents of this session to the input stream.
     *
     * @param out The output stream to write to.
     * @param format The format of the output.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void dump(OutputStream out, String format) throws SessionException {
        try {
            this.store.write(out, format);
        } catch (Exception ex) {
            log.error("Failed to dump the contents of the string : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to dump the contents of the string : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method is responsible for removing the specified object from the store.
     * @param obj The object to remove from the store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(Object obj) throws SessionException {
        try {
            storeBeanWritter.delete(obj);
        } catch (Exception ex) {
            log.error("Failed to remove the object from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the object from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the specified RDF information.
     *
     * @param rdf The string containing the RDF information
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void remove(String rdf) throws SessionException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(rdf.getBytes());
            store.remove(ModelFactory.createDefaultModel().read(in, null));
            in.close();
        } catch (Exception ex) {
            log.error("Failed to remove the rdf from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to remove the rdf from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns an instance of the specified object type.
     *
     * @param c The class type to perfrom the operation on.
     * @param objectType The type to retrieve from the database.
     * @return An instance of the requested type.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T getType(Class<T> c, String objectType) throws SessionException, UnknownEntryException {
        try {
            Object obj = this.configBeanReader.load(String.format("%s%s/%s/",
                    new NamespaceHelper(c).getNamespace(),
                    new RDFTypeHelper(c).getRdfType(),
                    objectType));
            if (obj != null) {
                return c.cast(obj);
            }
        } catch (thewebsemantic.NotFoundException ex) {
            // ignore
        } catch (Exception ex) {
            log.error("Failed to retrieve the type information from the config : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to retrieve the type information from the config : " + ex.getMessage(),ex);
        }
        // fall back to a basic type
        if (c.getName().equals(objectType)) {
            try {
                return c.newInstance();
            } catch (Exception ex) {
                log.error("Could not create a new instance of the object because : "
                        + ex.getMessage(),ex);
                throw new SessionException
                        ("Could not create a new instance of the object because : "
                        + ex.getMessage(),ex);
            }
        }
        throw new UnknownEntryException("The object of type [" + c.getName() + "]["
                + objectType+ "] could not be found");
    }


    /**
     * This method returns the basic session information.
     *
     * @param <T> This method is responsible for returning the requested object.
     * @param c The type of information.
     * @param objectType The object type to retrieve.
     * @param identifier The identifier for this object.
     * @return The reference to the object extracted from the data store.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T get(Class<T> c, Object objectType, Serializable identifier)
            throws SessionException, UnknownEntryException {
        try {
            return c.cast(this.storeBeanReader.load(URIHelper.getURI(c, objectType, identifier)));
        } catch (thewebsemantic.NotFoundException ex) {
            log.error("The bean was not found in the store : " + ex.getMessage(),ex);
            throw new UnknownEntryException
                    ("The bean was not found in the store : " + ex.getMessage(),ex);
        } catch (Exception ex) {
            log.error("Failed to load the bean from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to load the bean from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for retrieving the appropriate object from the database.
     *
     * @param objectName This is the name of the object to retrieve.
     * @param identifier The unique identifer of the object.
     * @return The object that has been retrieved.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T get(Class<T> c, String objectType, Serializable identifier) throws SessionException, UnknownEntryException {
        try {
            return c.cast(this.storeBeanReader.load(URIHelper.getURI(c, objectType, identifier)));
        } catch (thewebsemantic.NotFoundException ex) {
            log.error("The bean was not found in the store : " + ex.getMessage(),ex);
            throw new UnknownEntryException
                    ("The bean was not found in the store : " + ex.getMessage(),ex);
        } catch (Exception ex) {
            log.error("Failed to load the bean from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to load the bean from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the basic session information.
     *
     * @param <T> This method is responsible for returning the requested object.
     * @param c
     * @param objectType
     * @param identifier
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public com.rift.coad.rdf.semantic.Resource getResource(Class c, Object objectType,
            Serializable identifier) throws SessionException, UnknownEntryException {
        try {
            String uri = URIHelper.getURI(c, objectType, identifier);
            if (!exists(uri)) {
                throw new UnknownEntryException(String.format(
                        "The entry [%s] does not exist in this store",uri));
            }
            return new BasicResource(store,storeBeanWritter,storeBeanReader,
                    store.getResource(uri));
        } catch (UnknownEntryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the resource from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to get the resource from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the required resource from the store.
     *
     * @param c The class type to retrieve.
     * @param objectType The object type to retrieve the resource for.
     * @param identifier The identifier.
     * @return The results.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public com.rift.coad.rdf.semantic.Resource
            getResource(Class c, String objectType, Serializable identifier)
            throws SessionException, UnknownEntryException {
        try {
            String uri = URIHelper.getURI(c, objectType, identifier);
            if (!exists(uri)) {
                throw new UnknownEntryException(String.format(
                        "The entry [%s] does not exist in this store",uri));
            }
            return new BasicResource(store,storeBeanWritter,storeBeanReader,store.getResource(uri));
        } catch (UnknownEntryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the resource from the store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to get the resource from the store : " + ex.getMessage(),ex);
        }
    }
    

    /**
     * This method returns an instance of the specified type of object.
     *
     * @param <T> The type of object use.
     * @param c The class to perform the cast ot.
     * @param objectType The identifier of the object type to create an instance of.
     * @return An instance of the object type.
     * @throws com.rift.coad.rdf.semantic.SessionException
     * @throws com.rift.coad.rdf.semantic.session.UnknownEntryException
     */
    public <T> T createInstance(Class<T> c, String objectType) throws SessionException, UnknownEntryException {
        return getType(c,objectType);
    }


    /**
     * This object creates a new query object.
     *
     * @param queryString The query string to execute on the model
     * @return The reference to the query interface.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Query createQuery(String queryString) throws SessionException {
        return new BasicQuery(this, config, store, queryString);
    }

    /**
     * This method returns the SPARQL query 
     * @param queryString
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public SPARQLQuery createSPARQLQuery(String queryString) throws SessionException {
        try {
            return new BasicSPARQLQuery(this, config, store, queryString);
        } catch (Exception ex) {
            log.error("Failed to create a new query :" + ex.getMessage(),ex);
            throw new SessionException(
                    "Failed to create a new query :" + ex.getMessage(),ex);
        }
    }

    /**
     * This method returns true if the model contains the specified entry.
     *
     * @param uri The string containing the uri of the object.
     * @return The result of the query.
     */
    private boolean exists(String uri) throws SessionException {
        try {
            return store.getGraph().contains(createURI(uri), ANY, ANY);
        } catch (Exception ex) {
            log.error("Failed to check if the entry does exist : " + ex.getMessage(),ex);
            throw new SessionException(
                    "Failed to check if the entry does exist : " + ex.getMessage(),ex);
        }
    }
    
}
