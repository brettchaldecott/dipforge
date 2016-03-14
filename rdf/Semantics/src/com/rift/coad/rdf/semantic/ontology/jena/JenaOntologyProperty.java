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
 * JenaOntologyProperty.java
 */
package com.rift.coad.rdf.semantic.ontology.jena;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.iterator.ExtendedIterator;
import com.rift.coad.rdf.semantic.SemanticException;
import com.rift.coad.rdf.semantic.common.Property;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.ontology.OntologyException;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * This method represents an ontolog property.
 *
 * @author brett chaldecott
 */
public class JenaOntologyProperty implements OntologyProperty {

    // class static member variables
    private static Logger log = Logger.getLogger(JenaOntologyProperty.class);
    // private member variables
    private OntProperty jenaOntProperty;
    private OntClass jenaOntClass;

    /**
     * This constructor sets the ontology property information.
     *
     * @param jenaOntProperty The jena ontology property.
     */
    public JenaOntologyProperty(OntClass jenaOntClass,
            OntProperty jenaOntProperty) {
        this.jenaOntProperty = jenaOntProperty;
        if (jenaOntClass != null) {
            this.jenaOntClass = jenaOntClass;
        } else {
            this.jenaOntClass = (OntClass)jenaOntProperty.getDomain();
        }
    }

    /**
     * This method returns the URI of the property.
     *
     * @return The reference to the property.
     * @throws SemanticException
     */
    public URI getURI() throws SemanticException {
        try {
            return new URI(jenaOntProperty.getURI());
        } catch (Exception ex) {
            log.error("Failed to retrieve the uri : " + ex.getMessage(), ex);
            throw new SemanticException("Failed to retrieve the uri : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the name space.
     *
     * @return The string containing the name space.
     * @throws OntologyException
     */
    public String getNamespace() throws OntologyException {
        try {
            return jenaOntProperty.getNameSpace();
        } catch (Exception ex) {
            log.error("The name space could not be retrieved : " + ex.getMessage(), ex);
            throw new OntologyException("The name space could not be retrieved : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the local name.
     *
     * @return The string containing the local name.
     * @throws OntologyException
     */
    public String getLocalname() throws OntologyException {
        try {
            return jenaOntProperty.getLocalName();
        } catch (Exception ex) {
            log.error("The local name could not be retrieved : " + ex.getMessage(), ex);
            throw new OntologyException("The local name could not be retrieved : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method adds a sub property.
     *
     * @param property The property.
     * @throws OntologyException
     */
    public void addSubProperty(OntologyProperty property) throws OntologyException {
        if (!(property instanceof JenaOntologyProperty)) {
            throw new OntologyException("The property is not an instance of a "
                    + "JenaProperty and cannot therefore be made a sub property");
        }
        try {
            JenaOntologyProperty jenaOntologyProperty = (JenaOntologyProperty) property;
            jenaOntProperty.addSubProperty(jenaOntologyProperty.getJenaOntProperty());

        } catch (Exception ex) {
            log.error("The add the sub property : " + ex.getMessage(), ex);
            throw new OntologyException("The add the sub property : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method adds a sub property.
     *
     * @param property The property.
     * @throws OntologyException
     */
    public void removeSubProperty(OntologyProperty property) throws OntologyException {
        if (!(property instanceof JenaOntologyProperty)) {
            throw new OntologyException("The property is not an instance of a "
                    + "JenaProperty and cannot therefore be made a sub property");
        }
        try {
            JenaOntologyProperty jenaOntologyProperty = (JenaOntologyProperty) property;
            jenaOntProperty.removeSubProperty(jenaOntologyProperty.getJenaOntProperty());

        } catch (Exception ex) {
            log.error("Failed to remove the sub property : " + ex.getMessage(), ex);
            throw new OntologyException("Failed to the sub property : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method retrieves the jena ontology property.
     *
     * @return The string containing the jena ontology.
     */
    public OntProperty getJenaOntProperty() {
        return jenaOntProperty;
    }

    /**
     * This method sets the type label for this property.
     *
     * @param type This method sets the type label
     * @throws OntologyException
     */
    public void setType(DataType type) throws OntologyException {
        try {
            jenaOntProperty.addRDFType(jenaOntProperty.getModel().getResource(type.getURI().toString()));
        } catch (Exception ex) {
            log.error("Failed to set the type : " + ex.getMessage(), ex);
            throw new OntologyException("Failed to set the type : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the type label
     *
     * @return The string containing the type label
     * @throws OntologyException
     */
    public DataType getType() throws OntologyException {
        try {
            ExtendedIterator<Resource> iterator = jenaOntProperty.listRDFTypes(true);
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                String uri = resource.getURI();
                if (uri.equals(OntologyConstants.DATA_TYPE_PROPERTY_URI)
                        || uri.equals(OntologyConstants.ONTOLOGY_LOCATION_URIS)
                        || uri.equals(OntologyConstants.PROPERTY_NS_URI)) {
                    continue;
                }
                if (XSDDataDictionary.isBasicTypeByURI(uri)) {
                    DataType type = XSDDataDictionary.getTypeByURI(uri);
                    if (type != null) {
                        return type;
                    }
                } else {
                    OntClass ontClass = jenaOntProperty.getOntModel().getOntClass(uri);
                    if (jenaOntProperty.getOntModel().contains(ontClass, null)) {
                        return new JenaOntologyClass(ontClass);
                    }
                }
            }
            // label or type could not be found.
            return null;
        } catch (Exception ex) {
            log.error("Failed to set the type : " + ex.getMessage(), ex);
            throw new OntologyException("Failed to set the type : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method returns the range of this object.
     * 
     * @return
     * @throws OntologyException 
     */
    public DataType getRange() throws OntologyException {
        if (this.hasRange()) {
            return new JenaOntologyClass(
                    (OntClass)this.jenaOntProperty.getRange());
        }
        return null;
    }

    /**
     * This method returns true if the 
     * @return
     * @throws OntologyException 
     */
    public boolean hasRange() throws OntologyException {
        return this.jenaOntProperty.hasRange(this.jenaOntClass);
    }

    
    /**
     * This method sets the range on a given property.
     * 
     * @param range This method sets the range on a given property
     * @throws OntologyException 
     */
    public void setRange(DataType range) throws OntologyException {
        try {
            this.jenaOntProperty.setRange(
                    this.jenaOntProperty.getModel().getResource(
                    range.getURI().toString()));
        } catch (Exception ex) {
            throw new OntologyException(
                    "Failed to set the range on the property : "
                    + ex.getMessage(),ex);
        }
    }
    
}
