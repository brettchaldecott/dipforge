/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * JenaStoreTypes.java
 */

package com.rift.coad.rdf.semantic.persistance.jena.sdb;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;

import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.query.engine.EngineManager;
import com.rift.coad.rdf.semantic.sdb.SDBQueryEngine;
import java.util.Properties;

/**
 *
 * @author brett chaldecott
 */
public class JenaSDBModelFactory implements JenaStore {

    // private member variables
    private Store store = null;
    private Model dataStore;

    private JenaSDBModelFactory(Properties prop) throws PersistanceException {
        try {
            String sdbConfigPath = prop.getProperty(PersistanceConstants.STORE_CONFIGURATION_FILE);
            if (sdbConfigPath != null) {
                throw new PersistanceException("The configuration file [" +
                        PersistanceConstants.STORE_CONFIGURATION_FILE +
                        "] must be set for the SDB store.");
            }
            // read in the SDB data information
            store = SDBFactory.connectStore(sdbConfigPath);
            if (store.getSize() == 0) {
                store.getTableFormatter().create();
            }
            dataStore = SDBFactory.connectDefaultModel(store);
            EngineManager.getInstance().addEngine(dataStore,
                    new SDBQueryEngine(store));
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new PersistanceException("Failed to instantiate the Jena SDB store : "
                    + ex.getMessage(),ex);
        }
    }

    /**
     * This method creates a new instance of the xml model factory.
     *
     * @param properties The properties.
     * @return The reference to the new store.
     * @throws PersistanceException
     */
    public static JenaStore createInstance(Properties properties)
            throws PersistanceException {
        return new JenaSDBModelFactory(properties);
    }

    /**
     * This method returns the model reference.
     *
     * @return The reference to the model.
     * @throws PersistanceException
     */
    public Model getModule() throws PersistanceException {
        return dataStore;
    }


    /**
     * This method closes the jena sdb model
     * 
     * @throws PersistanceException
     */
    public void close() throws PersistanceException {
        try {
            store.close();
        } catch (Exception ex) {
            // ignore
        }
    }

}
