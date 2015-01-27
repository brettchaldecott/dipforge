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
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.SDBStoreWrapper;
import com.hp.hpl.jena.sdb.util.StoreUtils;

import java.lang.management.ManagementFactory;
import javax.management.ObjectName;

import com.rift.coad.rdf.semantic.persistance.PersistanceConstants;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaEscaperFactory;
import com.rift.coad.rdf.semantic.persistance.jena.JenaModelWrapper;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreType;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author brett chaldecott
 */
public class JenaSDBModelFactory implements JenaStore {

    // logger
    private static Logger log = Logger.getLogger(JenaSDBModelFactory.class);
    // private member variables
    private Store store = null;
    private Model dataStore;
    private JenaSDBKeepAlive keepAlive = null;

    private JenaSDBModelFactory(Properties prop) throws PersistanceException {
        try {
            // synchronize on the MBeanServer to solve the race condition
            // between the various threads
            synchronized (ManagementFactory.getPlatformMBeanServer()) {
                // This is a nasty work around to remove mbeans to prevent
                // clashes on mbean services
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=SystemInfo"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Context"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Engine"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }

                String sdbConfigPath = prop.getProperty(PersistanceConstants.STORE_CONFIGURATION_FILE);
                if (sdbConfigPath == null) {
                    throw new PersistanceException("The configuration file ["
                            + PersistanceConstants.STORE_CONFIGURATION_FILE
                            + "] must be set for the SDB store.");
                }
                // read in the SDB data information
                store = SDBFactory.connectStore(sdbConfigPath);

                // This is a nasty work around to remove mbeans to prevent
                // clashes on mbean services
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=SystemInfo"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Context"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Engine"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }

                // format an empty store
                if (!StoreUtils.isFormatted(store)) {
                    store.getTableFormatter().create();
                }
                dataStore = SDBFactory.connectDefaultModel(store);
                JenaEscaperFactory.getInstance().setEscaper(dataStore,
                        new JenaSDBEscaper());

                // check if this database type requires a keep alive
                log.info("The database type is : "
                        + store.getDatabaseType().getName());
                if (store.getDatabaseType().getName().equals(
                        DatabaseType.MySQL.getName())) {
                    log.info("The database requires keep alive");
                    keepAlive = new JenaSDBKeepAlive(dataStore);
                    keepAlive.start();
                }

                // This is a nasty work around to remove mbeans to prevent
                // clashes on mbean services
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=SystemInfo"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Context"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
                try {
                    ManagementFactory.getPlatformMBeanServer().
                            unregisterMBean(new ObjectName(
                            "com.hp.hpl.jena.sparql.system:type=Engine"));
                } catch (Exception ex) {
                    // ignore
                    log.error("Failed to remove reference : " + ex.getMessage());
                }
            }
        } catch (PersistanceException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to instantiate the Jena SDB store : "
                    + ex.getMessage(), ex);
            throw new PersistanceException("Failed to instantiate the Jena SDB store : "
                    + ex.getMessage(), ex);
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
    public JenaModelWrapper getModule() throws PersistanceException {
        return new JenaModelWrapperSDB(dataStore);
    }

    /**
     * This method closes the jena sdb model
     *
     * @throws PersistanceException
     */
    public void close() throws PersistanceException {
        try {
            SDBStoreWrapper.close(store);
        } catch (Exception ex) {
            // ignore
        }
        try {
            if (keepAlive != null) {
                keepAlive.terminate();
            }
        } catch (Exception ex) {
            // ignore
        }
    }

    /**
     * This method is called to determine the type of store.
     *
     * @return The type of store being utilized.
     */
    public JenaStoreType getType() {
        return JenaStoreType.SDB;
    }
}
