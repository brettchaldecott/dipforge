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
 * SessionManagerBuilder.java
 */

// package path
package com.rift.coad.rdf.semantic;

import com.rift.coad.rdf.semantic.session.BasicSessionManager;
import java.util.Properties;

/**
 * This object is responsible for instantiating the session manager.
 *
 * @author brett chaldecott
 */
public class SessionManagerBuilder {


    /**
     * This method instanciates a new session manager based on the properties
     * passed in.
     *
     * @param properties The list of properties.
     * @return The reference to the session manager.
     * @throws SessionException
     */
    public static SessionManager createManager(Properties properties) throws
            SessionException {
        try {
            return new BasicSessionManager(properties);
        } catch (Exception ex) {
            throw new SessionException("Failed to instantiate the session "
                    + "manager : " + ex.getMessage(),ex);
        }
    }
}
