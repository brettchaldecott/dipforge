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
 * SessionManager.java
 */

// the package path
package com.rift.coad.rdf.semantic;

import java.util.Properties;


/**
 * This class is responsible for managing the semantic connections and information.
 *
 * @author brett chaldecott
 */
public interface SessionManager {

    /**
     * This method returns the version information for the session object.
     *
     * @return The string containing the version information for the session.
     */
    public String getVersion();


    /**
     * The string containing the name of this session manager.
     *
     * @return The string containing the name of this session.
     */
    public String getName();


    /**
     * The description of this manager.
     *
     * @return The description of this manager.
     */
    public String getDescription();

    
    /**
     * This method returns the description of the 
     * @return
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Session getSession() throws SessionException;
    
    
    /**
     * This method is called to reload the ontology information.
     * 
     * @param properties The properties to reload the ontology with.
     * @throws SessionException 
     */
    public void reloadOntology(Properties properties) throws SessionException;
    
    
    /**
     * This method is called to shut down the semantic manager depending on the backend data store.
     * 
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void shutdown() throws SessionException;
}
