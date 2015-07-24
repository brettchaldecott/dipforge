/*
 * OntologyRepositoryClient: The client of the ontology repository.
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
 * RepositoryManagerMBean.java
 */

package com.rift.coad.ontology;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;


/**
 * This is the interface defines the means to manage the repository.
 * 
 * @author brett chaldecott
 */
public interface RepositoryManagerMBean extends Remote {
    
    /**
     * This method returns the version information for this object.
     * 
     * @return The string containing the version information for this object.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Get the version information for this object.")
    @Version(number="1.0")
    @Result(description="The string containing the version information.")
    public String getVersion() throws RepositoryException,RemoteException;
    
    
    /**
     * This method returns the name of this object.
     * @return The string containing the name of the repository.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The name of this repository.")
    @Version(number="1.0")
    @Result(description="The string containing the name of this object.")
    public String getName() throws RepositoryException, RemoteException;
    
    
    /**
     * This method returns the description of this object.
     * @return The string containing the description
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The description of the repository.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this object.")
    public String getDescription() throws RepositoryException, RemoteException;
    
    
    /**
     * This method lists the ontologies in the repository.
     * 
     * @return The list of string identifiers for ontologies.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns a list of the ontologies.")
    @Version(number="1.0")
    @Result(description="The list of ontologies.")
    public List<String> listOntologies() throws RepositoryException, RemoteException;
    
    
    
    /**
     * This method adds a new ontology to the repository.
     * 
     * @param name The name of the ontology to add.
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method adds a new entry to the repository.")
    @Version(number="1.0")
    public void addOntology(
            @ParamInfo(name="name",description="The name of the ontology")String name, 
            @ParamInfo(name="contents",description="The contents of the ontology")String contents) 
            throws RepositoryException, RemoteException; 
    
    
    /**
     * This method updates the ontology.
     * 
     * @param name The name of the ontology to update.
     * @param contents The contents of the ontology.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method updates an existing ontology.")
    @Version(number="1.0")
    public void updateOntology(
            @ParamInfo(name="name",description="The name of the ontology")String name, 
            @ParamInfo(name="contents",description="The contents of the ontology")String contents)
            throws RepositoryException, RemoteException;
    
    
    /**
     * This method delete the ontology entry.
     * 
     * @param name The name of the ontology to delete.
     * @throws com.rift.coad.ontology.RepositoryException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method deletes an existing ontology.")
    @Version(number="1.0")
    public void deleteOntology(
            @ParamInfo(name="name",description="The name of the ontology")String name)
            throws RepositoryException, RemoteException;
}
