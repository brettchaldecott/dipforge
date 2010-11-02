/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * SDBSessionManager.java
 */

package com.rift.coad.rdf.semantic.sdb;

// java imports
import java.net.URL;

// log4j imports
import org.apache.log4j.Logger;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import java.util.Date;

// coadunation imports
import com.hp.hpl.jena.sdb.store.SDBStoreWrapper;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SessionException;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.query.engine.EngineManager;
import com.rift.coad.rdf.semantic.session.BasicSession;

/**
 * This object is responsible for managing the session for a Jena instance
 * connected to SDB.
 * 
 * @author brett chaldecott
 */
public class SDBSessionManager implements SessionManager {

    // class static member variables
    private static Logger log = Logger.getLogger(SDBSessionManager.class);

    // private member variables
    private Store store = null;
    private Model config = null;
    private Model dataStore = null;
    private String configPath = null;
    private Date timestamp = new Date();
    private SDBTransaction transaction = null;

    /**
     * This constructor is responsible for creating an SDB session manager using
     * the configuration information passed in.
     * @param configPath The path to the configuration data used by 
     * @param sdbConfigPath The path to the configuration object.
     */
    public SDBSessionManager(String configPath, String sdbConfigPath) throws SDBException {
        try {
            // load the configuration
            config = ModelFactory.createDefaultModel();
            config.read(configPath);
            this.configPath = configPath;

            // read in the SDB data information
            store = SDBFactory.connectStore(sdbConfigPath);
            try {
                store.getSize();
            } catch (Exception ex) {
                store.getTableFormatter().create();
            }
            dataStore = SDBFactory.connectDefaultModel(store);
            EngineManager.getInstance().addEngine(dataStore,
                    new SDBQueryEngine(store));

            // create the transaction object on the SDB store
            transaction = new SDBTransaction(dataStore);
        } catch (Exception ex) {
            log.error("Failed to initialize the SDB session manager : " + ex.getMessage(),ex);
            throw new SDBException
                    ("Failed to initialize the SDB session manager : " + ex.getMessage(),ex);
        }
    }


    /**
     * This constructor is responsible for creating an SDB session manager using
     * the configuration information passed in.
     * @param configPath The path to the configuration data used by
     * @param sdbConfigPath The path to the configuration object.
     */
    public SDBSessionManager(URL configPath, URL sdbConfigPath) throws SDBException {
        try {
            // load the configuration
            config = ModelFactory.createDefaultModel();
            config.read(configPath.toString());
            this.configPath = configPath.toString();

            // read in the SDB data information
            store = SDBFactory.connectStore(sdbConfigPath.toString());
            if (store.getSize() == 0) {
                store.getTableFormatter().create();
            }
            dataStore = SDBFactory.connectDefaultModel(store);
            EngineManager.getInstance().addEngine(dataStore,
                    new SDBQueryEngine(store));

            // create the transaction object on the SDB store
            transaction = new SDBTransaction(dataStore);
        } catch (Exception ex) {
            log.error("Failed to initialize the SDB session manager : " + ex.getMessage(),ex);
            throw new SDBException
                    ("Failed to initialize the SDB session manager : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the version information for this driver.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of this object.
     *
     * @return The string containing the name of this object.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description of this session manager.
     *
     * @return The string containing the description of this object.
     */
    public String getDescription() {
        return "SDB Session Manager";
    }


    /**
     * This method returns new session object attached to the current transaction.
     *
     * @return The reference to the current session.
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public Session getSession() throws SessionException {
        try {
            // re-read the based on the expirty time
            synchronized(config) {
                // re-read the configuration information for this model
                if ((new Date().getTime() - this.timestamp.getTime()) > 30 * 1000) {
                    config = ModelFactory.createDefaultModel();
                    config.read(this.configPath);
                    this.timestamp = new Date();
                }
            }

            return new BasicSession(config,this.dataStore,transaction);
        } catch (Exception ex) {
            log.error("Failed to retrieve a session reference : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to retrieve a session reference : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method
     * @throws com.rift.coad.rdf.semantic.SessionException
     */
    public void shutdown() throws SessionException {
        try {
            // close down the store
            SDBStoreWrapper.close(store);

            // clear out references
            store = null;
            config = null;
            dataStore = null;
        } catch (Exception ex) {
            log.error("Failed to close the data store : " + ex.getMessage(),ex);
            throw new SessionException
                    ("Failed to close the data store : " + ex.getMessage(),ex);
        }
    }
}
