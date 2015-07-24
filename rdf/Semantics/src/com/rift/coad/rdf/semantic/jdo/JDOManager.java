/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * JDOManager.java
 */


package com.rift.coad.rdf.semantic.jdo;

import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;

/**
 * The interface definition for the JDO Manager.
 * 
 * @author brett chaldecott
 */
public interface JDOManager {
    /**
     * This method returns the name of the ontology manager.
     *
     * @return The string containing the name of the ontology manager.
     * @throws OntologyException
     */
    public String getName() throws JDOException;


    /**
     * This method returns the description of the ontology manager.
     *
     * @return The string containing the description of the ontology manager.
     * @throws OntologyException
     */
    public String getDescription() throws JDOException;


    /**
     * This method returns the version information about the ontology manager.
     *
     * @return This method returns the version information for the ontology.
     * @throws OntologyException
     */
    public String getVersion() throws JDOException;


    /**
     * This method returns the session information
     *
     * @return The reference to the ontology session information.
     * @throws OntologyException
     */
    public JDOSession getSession(PersistanceSession persistanceSession,
            OntologySession ontologySession) throws JDOException;
}
