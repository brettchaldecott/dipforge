/*
 * 0047-CoadunationCRMServer: The CRM server.
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
 * StorageManager.java
 */


// package path
package com.rift.coad.crm.rdf;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;


/**
 * This interface is responsible for describing the storage manager methods.
 * 
 * @author brett chaldecott
 */
public interface StorageManager {
    
    /**
     * This method returns the name of the storage manager.
     * 
     * @return The string containing the name of the storge manager.
     */
    public String getName();
    
    
    /**
     * This method returns the status of the storage manager.
     * 
     * @return The string containing the status of the storage manager.
     */
    public String getStatus();
    
    
    /**
     * This method returns a reference to the model attached to the storage.
     * 
     * @return a model attached to the storage.
     */
    public Model getModel();
    
    /**
     * This method is called to close the storage manager.
     */
    public void close();
}
