/*
 * OntologyRepository: The ontology repository.
 * Copyright (C) 2009  2015 Burntjam
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

package com.rift.coad.ontology.webservice;

// java imports
import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.ontology.RepositoryManagerDaemon;

/**
 * The implementation of the ontology repository web service
 * 
 * @author brett chaldecott
 */
public class RepositoryManagerImpl implements RepositoryManager {
    
    
    // class constants
    public final String REPOSITORY_MANAGER_DAEMON_JNDI =
            "java:comp/env/bean/ontology/RepositoryManagerDaemon";
    
    
    // class singletons
    private static Logger log = Logger.getLogger(RepositoryManager.class);
    
    
    /**
     * The default constructor of the repository.
     */
    public RepositoryManagerImpl() {
        
    }

    
    /**
     * This method returns the list of ontologies as an array.
     * @return
     * @throws com.rift.coad.ontology.webservice.RepositoryException
     */
    public String[] listOntologies() throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            return daemon.listOntologies().toArray(new String[0]);
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of ontologies : " + ex.getMessage(),ex);
            throw throwRepositoryException(
                    "Failed to retrieve the list of ontologies : " + ex.getMessage(),ex);
        }

    }
    
    
    /**
     * This method adds an entry to the ontology.
     * 
     * @param name The entry to add to the ontology.
     * @param contents The contents of the ontology
     * @throws com.rift.coad.ontology.webservice.RepositoryException
     */
    public void addOntology(String name, String contents) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.addOntology(name, contents);
        } catch (Throwable ex) {
            log.error("Failed to add an entry to the repository : " + ex.getMessage(),ex);
            throw throwRepositoryException(
                    "Failed to add an entry to the repository : " + ex.getMessage(),ex);
        }

    }
    
    
    /**
     * This method updates the entries in the ontology.
     * 
     * @param name The name of the ontology entry to update
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.webservice.RepositoryException
     */
    public void updateOntology(String name, String contents) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.updateOntology(name, contents);
        } catch (Throwable ex) {
            log.error("Failed to update an entry to the repository : " + ex.getMessage(),ex);
            throw throwRepositoryException(
                    "Failed to update an entry to the repository : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method deletes the entry from the ontology.
     * 
     * @param name The name of the ontology entry to delete.
     * @throws com.rift.coad.ontology.webservice.RepositoryException
     */
    public void deleteOntology(String name) throws RepositoryException {
        try {
            RepositoryManagerDaemon daemon = (RepositoryManagerDaemon)
                    ConnectionManager.getInstance().getConnection(
                    RepositoryManagerDaemon.class,REPOSITORY_MANAGER_DAEMON_JNDI);
            daemon.deleteOntology(name);
        } catch (Throwable ex) {
            log.error("Failed to delete the entry from the repository : " + ex.getMessage(),ex);
            throw throwRepositoryException(
                    "Failed to delete the entry from the repository : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @return The reference to the jython daemon exception
     * @param message The message to put in the exception
     * @param ex The exception stack.
     */
    private RepositoryException throwRepositoryException(
            String message, Throwable ex) {
        RepositoryException exception = new RepositoryException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}
