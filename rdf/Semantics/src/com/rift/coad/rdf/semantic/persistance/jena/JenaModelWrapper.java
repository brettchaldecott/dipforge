/*
 * Semantics: The semantic library for dipforge
 * Copyright (C) 2015  2015 Burntjam
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
 * JenaModelWrapper.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

// imports for the jena libraries
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.Lock;

/**
 * This object is a wrapper of the wrapped model. It acts as a visitor to the
 * model for certain transaction methods.
 * 
 * @author brett chaldecott
 */
public interface JenaModelWrapper {
    
    
    /**
     * This method is called to retrieve the wrapped model
     * @return 
     */
    public Model getModel();
    
    
    /**
     * This method is called when entering a critical lock section
     * 
     * @param lock The type of critical lock to apply.
     */
    public void enterCriticalSection(boolean lock);
    
    
    /**
     * This method is used to begin the lock on the object
     */
    public void begin();
    
    
    /**
     * This method is called to abort the changes
     */
    public void abort();
    
    
    /**
     * The method to commit the changes.
     * 
     * @return The commit method
     */
    public void commit();
    
    
    /**
     * The method to leave the critcal section
     */
    public void leaveCriticalSection();
    
}
