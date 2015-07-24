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
 * OntologyManager.java
 */


// the package path.
package com.rift.coad.rdf.semantic.ontology.jena;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.rift.coad.rdf.semantic.ontology.OntologyClass;
import com.rift.coad.rdf.semantic.ontology.OntologyException;
import com.rift.coad.rdf.semantic.ontology.OntologyProperty;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.ontology.OntologyTransaction;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This interface defines the ontology information.
 *
 * @author brett chaldecott
 */
public class JenaOntologySession implements OntologySession {

    // class static member variables
    private static Logger log = Logger.getLogger(JenaOntologySession.class);

    // private member variables
    private OntModel jenaOntModel;
    private JenaOntologyTransaction transaction;


    /**
     * This constructor sets the ontology model.
     *
     * @param jenaOntModel The ontology model.
     */
    public JenaOntologySession(OntModel jenaOntModel) throws OntologyException {
        this.jenaOntModel = jenaOntModel;
        transaction = new JenaOntologyTransaction(jenaOntModel);
    }

    
    /**
     * This method returns a reference to the ontology transaction.
     * 
     * @return The reference to the ontology transaction.
     * @throws OntologyException
     */
    public OntologyTransaction getTransaction() throws OntologyException {
        return transaction;
    }


    /**
     * This method creates an ontology property for the specified uri.
     *
     * @param uri The uri for the ontology property.
     * @return The reference to the newly created ontology.
     * @throws OntologyException
     */
    public OntologyProperty createProperty(URI uri) throws OntologyException {
        OntProperty property = jenaOntModel.getOntProperty(uri.toString());
        if (property != null) {
            throw new OntologyException(
                    "The ontology already contains the property : " + uri.toString());
        }
        property = jenaOntModel.createOntProperty(uri.toString());
        return new JenaOntologyProperty(null,property);
    }


    /**
     * This method is called to return a reference to the specified ontology property.
     *
     * @param uri The uri for the ontology property.
     * @return The reference to the retrieved property.
     * @throws OntologyException
     */
    public OntologyProperty getProperty(URI uri) throws OntologyException {
        OntProperty property = jenaOntModel.getOntProperty(uri.toString());
        if (property == null) {
            throw new OntologyException(
                    "The ontology does not contain the uri : " + uri.toString());
        }
        return new JenaOntologyProperty(null,property);
    }


    /**
     * This method returns true true if the property is found.
     *
     * @param uri The URI of the property to find.
     * @return TRUE if found, FALSE if not.
     * @throws OntologyException
     */
    public boolean hasProperty(URI uri) throws OntologyException {
        try {
            OntProperty property = jenaOntModel.getOntProperty(uri.toString());
            if (property == null) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            throw new OntologyException(
                    "Failed to check for the uri [" + uri.toString() + "] because :"
                    + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the property.
     *
     * @param uri The uri.
     * @throws OntologyException
     */
    public void removeProperty(URI uri) throws OntologyException {
        OntProperty property = jenaOntModel.getOntProperty(uri.toString());
        if (property == null) {
            throw new OntologyException(
                    "The ontology does not contain the uri : " + uri.toString());
        }
        try {
            property.remove();
        } catch (Exception ex) {
            log.error("Failed to remove the property because : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to remove the property because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method creates  new ontology class identified by the uri.
     *
     * @param uri The uri to create
     * @return The reference to the ontology class.
     * @throws OntologyException
     */
    public OntologyClass createClass(URI uri) throws OntologyException {
        OntClass ontClass = jenaOntModel.getOntClass(uri.toString());
        if (ontClass !=  null) {
            throw new OntologyException(
                    "The ontology already contains the uri : " + uri.toString());
        }
        try {
            return new JenaOntologyClass(jenaOntModel.createClass(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to remove the property because : " + ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to remove the property because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the ontology class.
     *
     * @param uri The URI ontology class.
     * @return The reference to the retrieved ontology class.
     * @throws OntologyException
     */
    public OntologyClass getClass(URI uri) throws OntologyException {
        OntClass ontClass = 
                jenaOntModel.getOntClass(uri.toString());
        if (ontClass == null) {
            throw new OntologyException(
                    "The ontology does not contains the uri : " + uri.toString());
        }
        try {
            return new JenaOntologyClass(jenaOntModel.getOntClass(uri.toString()));
        } catch (Exception ex) {
            log.error("Failed to retrieve the ontology class because : " +
                    ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to retrieve the ontology class because : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns true if the ontology contains the identified class.
     *
     * @param uri The uri of the class to check for.
     * @return TRUE if found, FALSE if not.
     * @throws OntologyException
     */
    public boolean hasClass(URI uri) throws OntologyException {
        try {
            OntClass ontClass = jenaOntModel.getOntClass(uri.toString());
            if (ontClass == null) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            throw new OntologyException(
                    "Failed to check for the uri [" + uri.toString() + "] because :"
                    + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the class identified by the URI.
     *
     * @param uri The URI to remove.
     * @throws OntologyException
     */
    public void removeClass(URI uri) throws OntologyException {
        OntClass ontClass = jenaOntModel.getOntClass(uri.toString());
        if (!jenaOntModel.contains(ontClass, null)) {
            return;
        }
        try {
            ontClass.remove();
        } catch (Exception ex) {
            log.error("Failed to retrieve the ontology class because : " +
                    ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to retrieve the ontology class because : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method dumps the ontology session to xml string.
     *
     * @return The string to dump.
     * @throws OntologyException
     */
    public String dumpXML() throws OntologyException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            jenaOntModel.write(out);
            return out.toString();
        } catch (Exception ex) {
            log.error("Failed to dump the ontology to xml because : "
                    + ex.getMessage(), ex);
            throw new OntologyException(
                    "Failed to dump the ontology to xml because : "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * This method lists the classes.
     *
     * @return The list of cles
     * @throws OntologyException
     */
    public List<OntologyClass> listClasses() throws OntologyException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ExtendedIterator<OntClass> classes = jenaOntModel.listClasses();
            List<OntologyClass> result = new ArrayList<OntologyClass>();
            for (; classes.hasNext(); ){
                result.add(new JenaOntologyClass(classes.next()));
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to dump the ontology to xml because : "
                    + ex.getMessage(), ex);
            throw new OntologyException(
                    "Failed to dump the ontology to xml because : "
                    + ex.getMessage(), ex);
        }
    }
}
