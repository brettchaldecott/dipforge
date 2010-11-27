/*
 * OntologyRepository: The ontology repository.
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
 * RepositoryManager.java
 */

package com.rift.coad.ontology;

// java imports
import java.rmi.RemoteException;
import java.util.List;

// import log4j
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;


/**
 * The management bean responsible for managing the respository
 * 
 * @author brett chaldecott
 */
public class RepositoryManager implements RepositoryManagerMBean {
    
    // class constants
    public final String REPOSITORY_MANAGER_DAEMON_JNDI =
            "java:comp/env/bean/ontology/RepositoryManagerDaemon";
    
    
    // class singletons
    private static Logger log = Logger.getLogger(RepositoryManager.class);
    
    
    /**
     * The default constructor
     */
    public RepositoryManager() {
        
    }
    
    
    /**
     * This method return the version information.
     * @return The version information
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public String getVersion() throws RepositoryException {
        return "1.0";
    }
    
    
    /**
     * This method returns the name of this object.
     * @return The string containing the name of the repository manager.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public String getName() throws RepositoryException {
        return this.getClass().getName();
    }
    
    
    /**
     * This method returns the description of this object.
     * @return The description of the repository.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public String getDescription() throws RepositoryException {
        return "The Ontology manager";
    }

    
    /**
     * This method returns a list of ontologies
     * @return
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public List<String> listOntologies() throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            return daemon.listOntologies();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of ontologies : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to retrieve the list of ontologies : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for adding an entry to the ontology.
     * @param name The name of the entry to add.
     * @param contents The contents.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void addOntology(String name, String contents) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.addOntology(name, contents);
        } catch (Throwable ex) {
            log.error("Failed to add an entry to the repository : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to add an entry to the repository : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for updating the ontology.
     * 
     * @param name The name of the ontology to update.
     * @param contents The contents of an ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void updateOntology(String name, String contents) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.updateOntology(name, contents);
        } catch (Throwable ex) {
            log.error("Failed to update an entry to the repository : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to update an entry to the repository : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to delete an entry from the ontology.
     * @param name The name of the ontology to delete.
     * @throws com.rift.coad.ontology.RepositoryException
     */
    public void deleteOntology(String name) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.deleteOntology(name);
        } catch (Throwable ex) {
            log.error("Failed to delete the entry from the repository : " + ex.getMessage(),ex);
            throw new RepositoryException(
                    "Failed to delete the entry from the repository : " + ex.getMessage(),ex);
        }
    }

}
