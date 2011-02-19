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
 * JenaOntologyClass.java
 */

package com.rift.coad.rdf.semantic.ontology.jena;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyException;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.types.DataTypeException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This interface defines the methods to manipulate the ontology.
 *
 * @author brett chaldecott
 */
public class JenaOntologyClass implements OntologyClass {

    // class static variables
    private static Logger log = Logger.getLogger(JenaOntologyClass.class);

    // private member variables
    private OntClass jenaOntologyClass;


    /**
     * This constructor sets up the ontology class.
     * 
     * @param jenaOntologyClass
     */
    public JenaOntologyClass(OntClass jenaOntologyClass) {
        this.jenaOntologyClass = jenaOntologyClass;
    }



    /**
     * This method returns the URI to the object.
     *
     * @return This method returns the URI.
     * @throws DataTypeException
     */
    public URI getURI() throws DataTypeException {
        try {
            return new URI(jenaOntologyClass.getURI());
        } catch (Exception ex) {
            log.error("Failed to retrieve the uri : " + ex.getMessage(),ex);
            throw new DataTypeException
                    ("Failed to retrieve the uri : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the name space.
     *
     * @return The string containing the name space.
     * @throws DataTypeException
     */
    public String getNamespace() throws DataTypeException {
        return jenaOntologyClass.getNameSpace();
    }


    /**
     * This method returns the local name.
     *
     * @return The string containing the local name.
     * @throws DataTypeException
     */
    public String getLocalName() throws DataTypeException {
        return jenaOntologyClass.getLocalName();
    }


    /**
     * This method adds a new property.
     *
     * @param property This method adds a new property to the ontology.
     * @throws OntologyException
     */
    public void addProperty(OntologyProperty property) throws OntologyException {
        if (!(property instanceof JenaOntologyProperty)) {
            log.error("Incompatibal type expected [" +
                    JenaOntologyProperty.class.getName() + "] got [" +
                    property.getClass().getName() + "]");
            throw new OntologyException
                    ("Incompatibal type expected [" +
                    JenaOntologyProperty.class.getName() + "] got [" +
                    property.getClass().getName() + "]");
        }
        try {
            JenaOntologyProperty ontologyProperty = (JenaOntologyProperty)property;
            ontologyProperty.getJenaOntProperty().addDomain(jenaOntologyClass);
        } catch (Exception ex) {
            log.error("Failed to add the property : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to add the property : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of properties.
     *
     * @return The list of ontology properties associated with this object.
     * @throws OntologyException
     */
    public List<OntologyProperty> listProperties() throws OntologyException {
        try {
            ExtendedIterator<OntProperty> iterator = jenaOntologyClass.listDeclaredProperties();
            List<OntologyProperty> properties = new ArrayList<OntologyProperty>();
            while (iterator.hasNext()) {
                OntProperty ontProperty = iterator.next();
                properties.add(new JenaOntologyProperty(ontProperty));
            }
            return properties;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of properties : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to retrieve the list of properties : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the property identified by the URI.
     *
     * @param uri The URI to remove.
     * @throws OntologyException
     */
    public void removeProperty(URI uri) throws OntologyException {
        try {
            OntProperty property = jenaOntologyClass.getOntModel().getOntProperty(uri.toString());
            property.removeDomain(jenaOntologyClass);
        } catch (Exception ex) {
            log.error("Failed to retrieve the ontology property : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to retrieve the ontology property : " + ex.getMessage(),ex);
        }
    }

}
