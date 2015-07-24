/*
 * OntologyRepositoryClient: The ontology repository client.
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
 * RepositoryManagerDaemon.java
 */

package com.rift.coad.ontology;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines daemon methods to manage the repository.
 * 
 * @author brett chaldecott
 */
public interface RepositoryManagerDaemon extends Remote {
    
    
    /**
     * This method lists the ontologies in the repository.
     * 
     * @return The list of string identifiers for ontologies.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    public List<String> listOntologies() throws RepositoryException, RemoteException;
    
    
    /**
     * This method adds a new ontology to the repository.
     * 
     * @param name The name of the ontology to add.
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    public void addOntology(String name, String contents) throws RepositoryException, RemoteException; 
    
    
    /**
     * This method updates the ontology.
     * 
     * @param name The name of the ontology to update.
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    public void updateOntology(String name, String contents) throws RepositoryException, RemoteException;
    
    
    /**
     * This method delete the ontology entry.
     * 
     * @param name The name of the ontology to delete.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    public void deleteOntology(String name) throws RepositoryException, RemoteException;
}
