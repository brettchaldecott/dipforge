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
 * SDBStorageManager.java
 */

package com.rift.coad.crm.rdf.sdb;

// rdf imports
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.store.SDBStoreWrapper;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.crm.rdf.StorageManager;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This object is responsible for managing the sdb storage on behalf of the 
 * 
 * @author brett chaldecott
 */
public class SDBStorageManager implements StorageManager {
    
    // class constants
    private final static String SDB_CONFIG = "sdb_config_file";
    
    // class singletons
    private static Logger log = Logger.getLogger(SDBStorageManager.class);
    
    // private member variables
    private Store store = null;
    private Model model = null;
    
    
    /**
     * The default constructor.
     * 
     * @throws SDBException
     */
    public SDBStorageManager() throws SDBException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().getConfig(
                    SDBStorageManager.class);
            store = SDBFactory.connectStore(conf.getString(SDB_CONFIG));
            store.getTableFormatter().create();
            model = SDBFactory.connectDefaultModel(store);
        } catch (Throwable ex) {
            log.error("The SDB store manager could not be initialized : " + 
                    ex.getMessage(),ex);
            throw new SDBException(
                    "The SDB store manager could not be initialized : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the name of the sdb storage manager.
     * 
     * @return The name of this store manager
     */
    public String getName() {
        return this.getClass().getName();
    }
    
    
    /**
     * The status of the storage manager
     * 
     * @return The status of the storage manager.
     */
    public String getStatus() {
        if (store == null) {
            return "closed";
        }
        return "Initialized";
    }

    
    /**
     * The reference to the model
     * 
     * @return The model reference.
     */
    public Model getModel() {
        if (model == null) {
            log.error("Requested for a closed model");
            throw new java.lang.NullPointerException(
                    "Requested for a closed model");
        }
        return model;
    }
    
    
    /**
     * This method is called to close the storage manager.
     */
    public void close() {
        try {
            SDBStoreWrapper.close(store);
            store = null;
            model = null;
        } catch (Exception ex) {
            log.error("Failed to shut down the SDB storage");
        }
    }

}
