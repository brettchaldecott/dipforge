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
 * JenaOntologyManager.java
 */

package com.rift.coad.rdf.semantic.ontology.jena;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyConstants;
import com.rift.coad.rdf.semantic.ontology.OntologyException;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import java.io.ByteArrayInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * The ontology manager interface.
 *
 * @author brett chaldecott
 */
public class JenaOntologyManager implements OntologyManager {

    // class static member variables
    private static Logger log = Logger.getLogger(JenaOntologyManager.class);

    // private member variables
    private OntModel jenaOntModel;

    /**
     * This constructor is responsible for handling the instantiation of a new
     *
     * @param properties The list of properties.
     * @throws OntologyException
     */
    public JenaOntologyManager(Properties properties) throws OntologyException {
        try {
            jenaOntModel = ModelFactory.createOntologyModel();
            if (properties.containsKey(OntologyConstants.ONTOLOGY_CONTENTS)) {
                String contents = properties.getProperty(OntologyConstants.ONTOLOGY_CONTENTS);
                ByteArrayInputStream in = new ByteArrayInputStream(contents.getBytes());
                jenaOntModel.read(in, null);
            } else if (properties.containsKey(OntologyConstants.ONTOLOGY_LOCATION_URIS)) {
                String[] uris = properties.getProperty(
                        OntologyConstants.ONTOLOGY_LOCATION_URIS).split(",");
                for (String uri : uris) {
                    jenaOntModel.read(uri.trim());
                }
            } else {
                log.error("Cannot construct the jena ontology manager because neither the "
                        + "[" + OntologyConstants.ONTOLOGY_CONTENTS + "] or the [" +
                        "[" + OntologyConstants.ONTOLOGY_LOCATION_URIS +
                        "] [" + properties.toString() + "]");
                throw new OntologyException
                        ("Cannot construct the jena ontology manager because neither the "
                        + "[" + OntologyConstants.ONTOLOGY_CONTENTS + "] or the [" +
                        "[" + OntologyConstants.ONTOLOGY_LOCATION_URIS + 
                        "] [" + properties.toString() + "]");
            }
        } catch (OntologyException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to construct the jena ontology manager : " + 
                    ex.getMessage(),ex);
            throw new OntologyException
                    ("Failed to construct the jena ontology manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the name of the ontology manager.
     *
     * @return The string containing the name of the ontology manager.
     * @throws OntologyException
     */
    public String getName() throws OntologyException {
        return "JenaOntologyManager";
    }


    /**
     * This method returns the description of the ontology manager.
     *
     * @return The string containing the description of the ontology manager.
     * @throws OntologyException
     */
    public String getDescription() throws OntologyException {
        return "The jena ontology manager";
    }


    /**
     * This method returns the version information about the ontology manager.
     *
     * @return This method returns the version information for the ontology.
     * @throws OntologyException
     */
    public String getVersion() throws OntologyException {
        return "1.0.1";
    }


    /**
     * This method returns the session information
     *
     * @return The reference to the ontology session information.
     * @throws OntologyException
     */
    public OntologySession getSession() throws OntologyException {
        return new JenaOntologySession(jenaOntModel);
    }
}
