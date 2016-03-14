/*
 * 0250-CoadunationCRMServer: The CRM server.
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
 * ModelManager.java
 */

package com.rift.coad.crm.rdf;

// java imports

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

// jena and sdb imports
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sdb.SDBFactory;
import org.apache.jena.sdb.Store;

/**
 * This object is responsible for managing the Jena RDF modeller.
 * 
 * @author brett chaldecott
 */
public class ModelManager {
    
    // class constants
    private final static String STORAGE_TYPE = "storage_type";
    
    // class singleton variables
    private static Logger log = Logger.getLogger(ModelManager.class);
    private static ModelManager singleton = null;
    
    // private member variables.
    private StorageManager storageManager;
    
    /**
     * The default private constructor.
     */
    private ModelManager() throws RDFException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    ModelManager.class);
            storageManager = (StorageManager)Class.forName(
                    config.getString(STORAGE_TYPE)).newInstance();
        } catch (Throwable ex) {
            log.error("Failed to create the model manager : " + ex.getMessage(),ex);
            throw new RDFException(
                    "Failed to create the model manager : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the model manager instance.
     * 
     * @return The reference to the model manager instance.
     */
    public synchronized static ModelManager getInstance() throws RDFException {
        if (singleton == null) {
            singleton = new ModelManager();
        }
        return singleton;
    }

    
    /**
     * This method returns a reference to the model object.
     * 
     * @return The reference to the model object.
     */
    public Model getModel() {
        return storageManager.getModel();
    }
    
    
    /**
     * This method returns the type of storage being used by this object.
     * 
     * @return The string containing the storage type.
     */
    public String getStorageType() {
        return storageManager.getName();
    }
    
    
    /**
     * This method returns the status of the storage manager.
     * 
     * @return This method returns the status of the storage.
     */
    public String getStorageStatus() {
        return storageManager.getStatus();
    }
    
    
    /**
     * This method is called to close the opened model class.
     */
    public void closeModel() {
        try {
            storageManager.close();
        } catch (Exception ex) {
            log.error("Failed to close the storage manager : " + 
                    ex.getMessage(),ex);
        }
    }
}
