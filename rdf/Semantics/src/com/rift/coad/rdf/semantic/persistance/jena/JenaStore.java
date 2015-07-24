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
 * JenaStore.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

// package path
import com.hp.hpl.jena.rdf.model.Model;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;

/**
 * The interface that defines the jena store common wrapped methods.
 *
 * @author brett chaldecott
 */
public interface JenaStore {

    /**
     * Get the data model required by the jena data store.
     *
     * @return The reference to the data model.
     * @throws PersistanceException
     */
    public JenaModelWrapper getModule() throws PersistanceException;


    /**
     * This method is called to close down the store
     * @throws PersistanceException
     */
    public void close() throws PersistanceException;
    
    
    /**
     * This method returns the type of store being utilized.
     * 
     * @return The enum containing the store type.
     */
    public JenaStoreType getType();
}
