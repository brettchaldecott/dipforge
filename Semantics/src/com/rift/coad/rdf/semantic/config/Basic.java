/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * Basic.java
 */

// the package path
package com.rift.coad.rdf.semantic.config;

// java imports
import java.net.URL;

// coadunation imports
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.basic.BasicSessionManager;

/**
 * This object is responsible for
 *
 * @author brett chaldecott
 */
public class Basic {

    /**
     * The default init session manager method. It does not setup and rdf type configuration.
     *
     * @return A reference to the session manager.
     */
    public static SessionManager initSessionManager() {
        return new BasicSessionManager();
    }

    /**
     * This method returns a new instance of the basic session manager using the rdf config string as
     * the source of the bean configuration information.
     *
     * @param rdfConfig The entity configuration required by the session session manager
     * @return The reference to the session manager using the rdf configuration store.
     */
    public static SessionManager initSessionManager(String rdfConfig) throws Exception {
        return new BasicSessionManager(rdfConfig);
    }


    /**
     * This method constructs a new instance of the session manager using the configuration url passed
     * in as the source of the bean configuration.
     *
     * @param configUrl The url pointing at the the source of the bean configuration information.
     * @return The url containing the configuration information.
     */
    public static SessionManager initSessionManager(URL configUrl) throws Exception {
        return new BasicSessionManager(configUrl);
    }


}
