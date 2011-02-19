/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * JDOSession.java
 */

package com.rift.coad.rdf.semantic.jdo;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * This object is responsible for
 *
 * @author brett chaldecott
 */
public class JDOManagerFactory {
    /**
     * This method returns the ontology manager information.
     *
     * @param properties
     * @return
     * @throws PersistanceException
     */
    public static synchronized JDOManager init(Properties properties)
            throws JDOException {
        try {
            // retrieve the manager class.
            String manager = properties.getProperty(
                    JDOConstants.JDO_MANAGER_CLASS);
            if (manager == null) {
                manager = JDOConstants.DEFAULT_JDO_CLASS;
            }
            Class managerClassRef = Class.forName(manager);
            Constructor constructor = managerClassRef.getConstructor(Properties.class);
            JDOManager managerInstance =
                    (JDOManager)constructor.newInstance(properties);
            return managerInstance;
        } catch (Throwable ex) {
            throw new JDOException(
                    "Failed to instanciate jdo manager exception : " +
                    ex.getMessage(),ex);
        }

    }
}
