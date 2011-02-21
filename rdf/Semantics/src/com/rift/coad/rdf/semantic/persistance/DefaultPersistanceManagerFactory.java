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
 * PersistanceManagerFactory.java
 */

// package interface
package com.rift.coad.rdf.semantic.persistance;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * This class is responsible for constructing the default persistance manager.
 * @author brett chaldecott
 */
public class DefaultPersistanceManagerFactory {

    // class constants
    public final static String DEFAULT_PERSISTANCE_CLASS =
            "com.rift.coad.rdf.semantic.persistance.jena.JenaPersistanceManager";

    /**
     * This method returns the persistance manager information.
     *
     * @param properties
     * @return
     * @throws PersistanceException
     */
    public static synchronized PersistanceManager init()
            throws PersistanceException {
        try {
            Class managerClassRef = Class.forName(DEFAULT_PERSISTANCE_CLASS);
            Constructor constructor = managerClassRef.getConstructor(Properties.class);
            PersistanceManager managerInstance =
                    (PersistanceManager)constructor.newInstance(new Properties());
            return managerInstance;
        } catch (Throwable ex) {
            throw new PersistanceException(
                    "Failed to instanciate persistance manager exception : " +
                    ex.getMessage(),ex);
        }

    }
}
