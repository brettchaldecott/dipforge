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

// package space
package com.rift.coad.rdf.semantic.persistance;

// java imports
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This factory is responsible for instanciating the appopriate persitance manager.
 *
 * @author brett chaldecott
 */
public class PersistanceManagerFactory {
    
    /**
     * This method returns the persistance manager information.
     * 
     * @param properties
     * @return
     * @throws PersistanceException
     */
    public static synchronized PersistanceManager init(Properties properties)
            throws PersistanceException {
        try {
            // retrieve the manager class.
            String manager = properties.getProperty(
                    PersistanceConstants.PERSISTANCE_MANAGER_CLASS);
            if (manager == null) {
                throw new PersistanceException("The [" +
                        PersistanceConstants.PERSISTANCE_MANAGER_CLASS + 
                        "] class name has not been supplied");
            }
            Class managerClassRef = Class.forName(manager);
            Constructor constructor = managerClassRef.getConstructor(Properties.class);
            PersistanceManager managerInstance =
                    (PersistanceManager)constructor.newInstance(properties);
            return managerInstance;
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new PersistanceException(
                    "Failed to instanciate persistance manager exception : " +
                    ex.getMessage(),ex);
        }
    
    }

}
