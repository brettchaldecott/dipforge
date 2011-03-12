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
 * PersistanceResource.java
 */
// package path
package com.rift.coad.rdf.semantic.persistance.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceIdentifier;
import com.rift.coad.rdf.semantic.persistance.PersistanceProperty;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * The resource responsible for managing the persistance of information.
 *
 * @author brett chaldecott
 */
public class JenaPersistanceResource implements PersistanceResource {

    // class static variables
    private static Logger log = Logger.getLogger(JenaPersistanceResource.class);
    // private member variables
    private Model jenaModel;
    private Resource resource;

    /**
     * The reference to the resource.
     * 
     * @param resource The resource that is being wrapped by this object.
     */
    public JenaPersistanceResource(Model jenaModel, Resource resource) {
        this.jenaModel = jenaModel;
        this.resource = resource;
    }

    /**
     * The unique uri for this resource.
     *
     * @return The uri for this object.
     * @throws PersistanceException
     */
    public URI getURI() throws PersistanceException {
        try {
            return new URI(resource.getURI());
        } catch (URISyntaxException ex) {
            log.error("Failed to retrieve the uri : " + ex.getMessage(), ex);
            throw new PersistanceException("Failed to retrieve the uri : " + ex.getMessage(), ex);
        }
    }

    /**
     * The name space for the resource.
     *
     * @return URI name space for this resource.
     */
    public PersistanceIdentifier getPersistanceIdentifier() {
        return PersistanceIdentifier.getInstance(resource.getNameSpace(),
                resource.getLocalName());
    }

    /**
     * This method returns TRUE if the property exists.
     *
     * @param identifier The identifier of the property.
     * @return TRUE if found, FALSE if not.
     * @throws PersistanceException
     */
    public boolean hasProperty(PersistanceIdentifier identifier)
            throws PersistanceException {
        return resource.hasProperty(resource.getModel().
                getProperty(identifier.toURI().toString()));
    }

    /**
     * This method creates a new property.
     *
     * @param namespace The name space property.
     * @param localName The local name.
     * @return The reference to the newly created property.
     * @throws PersistanceException
     */
    public PersistanceProperty createProperty(PersistanceIdentifier identifier)
            throws PersistanceException {
        return new JenaPersistanceProperty(jenaModel, resource,
                resource.getModel().createProperty(identifier.toURI().toString()));
    }

    /**
     * This method lists the perstance properties attached to this resource.
     * 
     * @return The list of properties.
     * @throws PersistanceException
     */
    public List<PersistanceProperty> listProperties() throws PersistanceException {
        StmtIterator iter = resource.listProperties();
        List<PersistanceProperty> result = new ArrayList<PersistanceProperty>();
        while (iter.hasNext()) {
            Statement statement = iter.nextStatement();
            result.add(new JenaPersistanceProperty(jenaModel, statement.getPredicate(),
                    statement));
        }
        return result;
    }

    /**
     * This method returns a list of properties associated with the identifier.
     *
     * @param identifier The identifier to return properties for.
     * @return The list of identifiers.
     * @throws PersistanceException
     */
    public List<PersistanceProperty> listProperties(PersistanceIdentifier identifier)
            throws PersistanceException {
        Property property = resource.getModel().getProperty(
                identifier.toURI().toString());
        StmtIterator iter = resource.listProperties(property);
        List<PersistanceProperty> result = new ArrayList<PersistanceProperty>();
        while (iter.hasNext()) {
            result.add(new JenaPersistanceProperty(jenaModel, property, iter.next()));
        }
        return result;
    }

    /**
     * The reference to the property information.
     *
     * @param namespace The name space for the property.
     * @param localName The
     * @return The persistance property.
     * @throws PersistanceException
     */
    public PersistanceProperty getProperty(PersistanceIdentifier identifier)
            throws PersistanceException {
        Property property = resource.getModel().getProperty(
                identifier.toURI().toString());
        return new JenaPersistanceProperty(jenaModel, property,
                resource.getProperty(property));
    }

    /**
     * This method removes the property identified by the identifier.
     * 
     * @param identifier
     * @throws PersistanceException
     */
    public void removeProperty(PersistanceIdentifier identifier) throws
            PersistanceException {
        Property property = resource.getModel().getProperty(
                identifier.toURI().toString());
        resource.removeAll(property).removeAll(property);
    }

    /**
     * This method removes the property identified by the identifier.
     *
     * @param identifier The identifier.
     * @param resource The resource that has to be removed or unlinked.
     * @throws PersistanceException
     */
    public void removeProperty(PersistanceIdentifier identifier,
            PersistanceResource resource) throws PersistanceException {
        try {
            Property property = this.resource.getModel().getProperty(
                identifier.toURI().toString());
            StmtIterator iterator =
                    this.resource.listProperties(property);
            while (iterator.hasNext()) {
                Statement statement = iterator.nextStatement();
                if (statement.getResource().getURI().equals(
                        resource.getURI().toString())) {
                    statement.remove();
                    break;
                }
            }
        } catch (Exception ex) {
            log.error("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
            throw new PersistanceException
                    ("Failed to remove the property from the resource : " +
                    ex.getMessage(),ex);
        }

    }
    

    /**
     * This method retrieves a resources.
     *
     * @return The jena resource.
     */
    public Resource getResource() {
        return resource;
    }
}
