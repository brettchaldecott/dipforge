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
 * BasicSessionManager.java
 */

package com.rift.coad.rdf.semantic.session;

import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.jdo.JDOManager;
import com.rift.coad.rdf.semantic.jdo.JDOManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologyManager;
import com.rift.coad.rdf.semantic.ontology.OntologyManagerFactory;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceManagerFactory;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * The session manager interface.
 *
 * @author brett chaldecott
 */
public class BasicSessionManager implements SessionManager {

    // class singletons
    private Logger log = Logger.getLogger(BasicSessionManager.class);

    // private member variables
    private PersistanceManager persistanceManager;
    private OntologyManager ontologyManager;
    private JDOManager jdoManager;

    /**
     * The basic session manager.
     *
     * @param properties The properties for the session.
     */
    public BasicSessionManager(Properties properties) throws SessionException {
        try {
            persistanceManager = PersistanceManagerFactory.init(properties);
            ontologyManager = OntologyManagerFactory.init(properties);
            jdoManager = JDOManagerFactory.init(properties);
        } catch (Exception ex) {
            throw new SessionException(
                    "Failed to instanciate the basic session manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the version information for the basic session manager.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1.1";
    }


    /**
     * This method returns the string containing the name information.
     *
     * @return The string containing the name information.
     */
    public String getName() {
        return "BasicSessionManager";
    }


    /**
     * This method returns the description of the basic session manager.
     *
     * @return The description of the basic session manager.
     */
    public String getDescription() {
        return "The basic session manager";
    }


    /**
     * This method returns an instance of a new session object.
     *
     * @return The reference to the session object to retrieve.
     * @throws SessionException
     */
    public Session getSession() throws SessionException {
        try {
            PersistanceSession peristanceSession = persistanceManager.getSession();
            OntologySession ontologySession = ontologyManager.getSession();
            return new BasicSession(peristanceSession,ontologySession,
                    jdoManager.getSession(peristanceSession, ontologySession));
        } catch (Exception ex) {
            log.error("Failed to get a session : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to get a session : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns an instance of a new session object.
     *
     * @param lock The lock type to use.
     * @return The reference to the session object to retrieve.
     * @throws SessionException
     */
    public Session getSession(SessionManager.SessionLock lock) throws SessionException {
        try {
            PersistanceSession peristanceSession = persistanceManager.getSession(lock);
            OntologySession ontologySession = ontologyManager.getSession();
            return new BasicSession(peristanceSession,ontologySession,
                    jdoManager.getSession(peristanceSession, ontologySession));
        } catch (Exception ex) {
            log.error("Failed to get a session : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to get a session : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to reload the ontology information.
     * 
     * @param properties The properties to reload the ontology with.
     * @throws SessionException 
     */
    public void reloadOntology(Properties properties) throws SessionException {
        try {
            ontologyManager = OntologyManagerFactory.init(properties);
        } catch (Exception ex) {
            log.error("Failed to reload the ontology : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to reload the ontology : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to shut down the session manager.
     *
     * @throws SessionException
     */
    public void shutdown() throws SessionException {
        try {
            persistanceManager.close();
        } catch (Exception ex) {
            log.error("Failed to shutdown : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to shutdown : " + ex.getMessage(),ex);
        }
    }

}
