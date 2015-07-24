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
 * BasicJDOManager.java
 */


package com.rift.coad.rdf.semantic.jdo.basic;

import com.rift.coad.rdf.semantic.jdo.JDOException;
import com.rift.coad.rdf.semantic.jdo.JDOManager;
import com.rift.coad.rdf.semantic.jdo.JDOSession;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import java.util.Properties;

/**
 * A basic implementation of the jdo manager.
 *
 * @author brettc
 */
public class BasicJDOManager implements JDOManager {


    /**
     * This constructor sets up the basic JDO manager.
     *
     * @param properties The properties reference by this object.
     */
    public BasicJDOManager(Properties properties) {
        
    }
    
    /**
     * This method returns the name of the basic JDO manager.
     * 
     * @return The string containing the name of the Basic JDO manager.
     * @throws JDOException
     */
    public String getName() throws JDOException {
        return "BasicJDOManager";
    }


    /**
     * The description of the Basic JDO Manager.
     *
     * @return The string containing the description of the basic JDO manager.
     * @throws JDOException
     */
    public String getDescription() throws JDOException {
        return "The Basic JDO Manager";
    }


    /**
     * This method returns the version information for this manager.
     *
     * @return The string containing he version information for this manager.
     * @throws JDOException
     */
    public String getVersion() throws JDOException {
        return "1.0.1";
    }


    /**
     *
     * @param persistanceSession
     * @param ontologySession
     * @return
     * @throws JDOException
     */
    public JDOSession getSession(PersistanceSession persistanceSession, OntologySession ontologySession) throws JDOException {
        return new BasicJDOSession(persistanceSession, ontologySession);
    }

}
