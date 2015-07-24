/*
 * Semantics: The semantic library for coadunation os
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
 * TransactionManager.java
 */

package com.rift.coad.rdf.semantic.transaction;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This object manages the access to the transaction manager. It assumes there will only ever be one type of
 * transaction object per environment.
 *
 * @author brett chaldecott
 */
public abstract class TransactionManager {

    // private class constants
    private final static String DEFAULT = "default";
    private final static String TDB = "jena_store_type";
    
    // the class singleton value.
    private static Map<String,TransactionManager> singletons = new ConcurrentHashMap<>();

    /**
     * This singlon method will attempt to instanciate the approriate transaction manager
     * defending on configuration
     */
    public static synchronized TransactionManager getInstance(Properties properties) throws TransactionManagerException {
        if (getStoreType(properties).equalsIgnoreCase("TDB")) {
            TransactionManager manager = singletons.get(TDB);
            if (manager == null) {
                manager = new DipforgeTDBTransactionManager();
                singletons.put(TDB, manager) ;
            }
            return manager;
        } else {
            TransactionManager manager = singletons.get(DEFAULT);
            if (manager == null) {
                manager = new DefaultTransactionManager();
                singletons.put(DEFAULT, manager);
            }
            return manager;
        }
    }
    
    
    /**
     * This singlon method will attempt to instanciate the approriate transaction manager
     * defending on configuration
     * 
     * @param key The key to identify the transaction manager.
     * @return The reference to the transaction manager
     */
    public static synchronized TransactionManager getInstance(String key) throws TransactionManagerException {
        return TransactionManager.singletons.get(key);
    }

    
    /**
     * This method is called to set the transaction manager.
     *
     * @param singleton The new transaction manager that should be set as the singleton.
     */
    public static synchronized void setTransactionManager(TransactionManager manager) {
        TransactionManager.singletons.put(DEFAULT, manager);
    }

    /**
     * This method is called to set the transaction manager.
     *
     * @param key The key to set the transaction manager.
     * @param singleton The new transaction manager that should be set as the singleton.
     */
    public static synchronized void setTransactionManager(String key, TransactionManager manager) {
        TransactionManager.singletons.put(key, manager);
    }

    /**
     * This method is called to enlist the specified resource in a transaction.
     *
     * @param resource The resource to enlist in the transaction.
     * @throws com.rift.coad.rdf.semantic.transaction.TransactionManagerException
     */
    public abstract void enlist(javax.transaction.xa.XAResource resource) throws TransactionManagerException;
    
    
    /**
     * This method processes the store type
     * 
     * @param properties The list of properties.
     * @return The store type.
     */
    private static String getStoreType(Properties properties) {
        if (properties == null) {
            return "DEFAULT";
        }
        if (properties.containsKey(TDB)) {
            return properties.getProperty(TDB);
        }
        return "DEFAULT";
    }
}
