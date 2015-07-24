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
 * OntologyManagerFactory.java
 */
// package space
package com.rift.coad.rdf.semantic.ontology;

import java.util.Properties;

/**
 * This object is responsible for managing the ontology factory.
 *
 * @author brett chaldecott
 */
public class DefaultOntologyManagerFactory {

    /**
     * This method returns the default ontology manager information.
     *
     * @return
     * @throws PersistanceException
     */
    public static synchronized OntologyManager init()
            throws OntologyException {
        try {
            Properties ontologyProperties = new Properties();
            ontologyProperties.put(OntologyConstants.ONTOLOGY_MANAGER_CLASS,
                    "com.rift.coad.rdf.semantic.ontology.jena.JenaOntologyManager");
            return OntologyManagerFactory.init(ontologyProperties);
        } catch (OntologyException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new OntologyException(
                    "Failed to instanciate persistance manager exception : "
                    + ex.getMessage(), ex);
        }

    }
}
