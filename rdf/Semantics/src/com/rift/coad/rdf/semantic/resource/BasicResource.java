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
 * BasicResource.java
 */

// package path
package com.rift.coad.rdf.semantic.resource;

// java imports
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
//import thewebsemantic.Bean2RDF;
//import thewebsemantic.RDF2Bean;

// semantic imports
import com.rift.coad.rdf.semantic.ResourceException;
import com.rift.coad.rdf.semantic.annotation.URIHelper;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The basic resource implementation.
 *
 * @author brett chaldecott
 */
public class BasicResource implements com.rift.coad.rdf.semantic.Resource {

    // class singletons
    private static Logger log = Logger.getLogger(BasicResource.class);

    // private member variables
    private Model store;
    //private Bean2RDF storeBeanWritter;
    //private RDF2Bean storeBeanReader;
    private Resource jenaResource;


    /**
     * The reference to the resoure.
     *
     * @param store The reference to the model that stores all the information.
     * @param storeBeanWritter The writer responsible for storing the information.
     * @param storeBeanReader The reader responsible for storing the information.
     * @param jenaResource The resource responsible for storing the information.
     */
    //public BasicResource(Model store, Bean2RDF storeBeanWritter,
    //        RDF2Bean storeBeanReader, Resource jenaResource) {
    //    this.store = store;
    //    this.storeBeanWritter = storeBeanWritter;
    //    this.storeBeanReader = storeBeanReader;
    //    this.jenaResource = jenaResource;
    //}



    /**
     * This method is called to update the reference information
     *
     * @param reference The reference to the object to update.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void update(Object reference) throws ResourceException {
        //try {
        //    storeBeanWritter.save(reference);
        //} catch (Exception ex) {
        //    log.error("Failed to update the resource : " + ex.getMessage(),ex);
        //    throw new ResourceException
        //            ("Failed to update the resource : " + ex.getMessage(),ex);
        //}
    }


    /**
     * This method returns the requested object.
     *
     * @param <T> The type of object being requested.
     * @param t The class type to cast the result to.
     * @return The return type.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public <T> T get(Class<T> t) throws ResourceException {
        //try {
        //    return t.cast(storeBeanReader.load(this.jenaResource.getURI()));
        //} catch (Exception ex) {
        //    log.error("Failed to get the resource : " + ex.getMessage(),ex);
        //    throw new ResourceException
        //            ("Failed to get the resource : " + ex.getMessage(),ex);
        //}
        return null;
    }


    /**
     * This method is responsible for adding a property to the resource.
     * @param url The url for the resource.
     * @param obj The object to bind to the resource.
     * @return The reference to the newly created resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public com.rift.coad.rdf.semantic.Resource
            addProperty(String url, Object obj) throws ResourceException {
        //try {
        //    Resource jenaResource = this.storeBeanWritter.save(obj);
        //    return new BasicResource(
        //            store,storeBeanWritter,storeBeanReader,
        //            this.jenaResource.addProperty(this.store.createProperty(url),
        //            jenaResource));
        //} catch (Exception ex) {
        //    log.error("Failed to add the property to the resource : " +
        //            ex.getMessage(),ex);
        //    throw new ResourceException
        //            ("Failed to add the property to the resource : " +
        //            ex.getMessage(),ex);
        //}
        return null;
    }


    /**
     * This method is responsible for adding a property to the resource.
     * @param url The url for the resource.
     * @param The resource to add
     * @return The reference to the newly created resource.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public com.rift.coad.rdf.semantic.Resource
            addProperty(String url, com.rift.coad.rdf.semantic.Resource resource)
            throws ResourceException {
        try {
            if (!(resource instanceof BasicResource)) {
                log.error("This resource is not of type BasicResource but of type : " +
                        resource.getClass().getName());
                throw new ResourceException
                        ("This resource is not of type BasicResource but of type : " +
                        resource.getClass().getName());
            }
            BasicResource br = (BasicResource)resource;
            //return new BasicResource(
            //        store,storeBeanWritter,storeBeanReader,
            //        this.jenaResource.addProperty(this.store.createProperty(url),
            //        br.jenaResource));
            return null;
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the property to the resource : " +
                    ex.getMessage(),ex);
            throw new ResourceException
                    ("Failed to add the property to the resource : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the given property from the resource.
     * @param url The url of the property that the object type must be removed for.
     * @param objectType The object type to remove.
     * @param identifier  The identifier of the object to remove.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void removeProperty(String url, Object objectType, Serializable identifier) throws ResourceException {
        try {
            StmtIterator iterator =
                    this.jenaResource.listProperties(this.store.getProperty(url));
            String uri = URIHelper.getURI(objectType, identifier);
            while (iterator.hasNext()) {
                Statement statement = iterator.nextStatement();
                if (statement.getResource().getURI().equals(uri)) {
                    statement.remove();
                    break;
                }
            }
        } catch (Exception ex) {
            log.error("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
            throw new ResourceException
                    ("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
        }
    }

    /**
     * This method removes the property from the resource identified by the resource reference.
     *
     * @param url The url of the property to remove
     * @param resource The resource to remove.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public void removeProperty(String url, com.rift.coad.rdf.semantic.Resource resource) throws ResourceException {
        try {
            StmtIterator iterator =
                    this.jenaResource.listProperties(this.store.getProperty(url));
            if (!(resource instanceof BasicResource)) {
                log.error("This resource is not of type BasicResource but of type : " +
                        resource.getClass().getName());
                throw new ResourceException
                        ("This resource is not of type BasicResource but of type : " +
                        resource.getClass().getName());
            }
            BasicResource br = (BasicResource)resource;
            while (iterator.hasNext()) {
                Statement statement = iterator.nextStatement();
                if (statement.getResource().getURI().equals(br.jenaResource.getURI())) {
                    statement.remove();
                    break;
                }
            }
        } catch (ResourceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
            throw new ResourceException
                    ("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * The basic resource object.
     *
     * @param url The url of property to list resources for.
     * @return The list of resources.
     * @throws com.rift.coad.rdf.semantic.ResourceException
     */
    public List<com.rift.coad.rdf.semantic.Resource>
            listProperties(String url) throws ResourceException {
        try {
            List<com.rift.coad.rdf.semantic.Resource> result = new ArrayList
                    <com.rift.coad.rdf.semantic.Resource>();
            StmtIterator iterator =
                    this.jenaResource.listProperties(this.store.getProperty(url));
            while (iterator.hasNext()) {
                Statement statement = iterator.nextStatement();
                //result.add(new BasicResource(store,storeBeanWritter,storeBeanReader,
                //    statement.getResource()));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the properties : " + ex.getMessage(),ex);
            throw new ResourceException
                    ("Failed to list the properties : " + ex.getMessage(),ex);
        }
    }

}
