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
 * OntologyManagerFactory.java
 */

// package space
package com.rift.coad.rdf.semantic.ontology;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * This object is responsible for managing the ontology factory.
 *
 * @author brett chaldecott
 */
public class OntologyManagerFactory {

    /**
     * This method returns the ontology manager information.
     *
     * @param properties
     * @return
     * @throws PersistanceException
     */
    public static synchronized OntologyManager init(Properties properties)
            throws OntologyException {
        try {
            // retrieve the manager class.
            String manager = properties.getProperty(
                    OntologyConstants.ONTOLOGY_MANAGER_CLASS);
            if (manager == null) {
                throw new OntologyException("The [" +
                        OntologyConstants.ONTOLOGY_MANAGER_CLASS +
                        "] class name has not been supplied");
            }
            Class managerClassRef = Class.forName(manager);
            Constructor constructor = managerClassRef.getConstructor(Properties.class);
            OntologyManager managerInstance =
                    (OntologyManager)constructor.newInstance(properties);
            return managerInstance;
        } catch (OntologyException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new OntologyException(
                    "Failed to instanciate persistance manager exception : " +
                    ex.getMessage(),ex);
        }

    }
}
