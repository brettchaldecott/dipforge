/*
 * OntologyRepositoryClient: The client of the ontology repository.
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
 * RepositoryEventListener.java
 */

package com.rift.coad.ontology;


// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface is called to manage the events associated with the ontology.
 * 
 * @author brett chaldecott
 */
public interface RepositoryEventListener extends Remote {
    
    /**
     * This method is called to load a new ontology entry.
     * 
     * @param name The name of the new ontology entry.
     * @throws java.rmi.RemoteException
     */
    public void load(String name) throws RemoteException;
    
    
    /**
     * This method is called to reload the ontolgy.
     * 
     * @param name The name of the ontology that is affected by the reload.
     * @throws RemoteException
     */
    public void reload(String name) throws RemoteException;
    
    
    /**
     * This method is called to unload a new ontology entry.
     * 
     * @param name The name of the new ontology entry.
     * @throws java.rmi.RemoteException
     */
    public void unload(String name) throws RemoteException;
    

}
