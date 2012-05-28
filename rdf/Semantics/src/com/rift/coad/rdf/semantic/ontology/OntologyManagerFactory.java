/*
 * Semantics: The semantic library for coadunation os
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
 * OntologyManagerFactory.java
 */

// package space
package com.rift.coad.rdf.semantic.ontology;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This object is responsible for managing the ontology factory.
 *
 * @author brett chaldecott
 */
public class OntologyManagerFactory {

    // private member variables
    public static String IGNORE_CACHE = "ontology_ignore_cache";
    public static String CACHE_TIMEOUT = "ontonogy_factory_timeout";
    public static long DEFAULT_CACHE_TIMEOUT = 1000 * 5;
    
    
    /**
     * This object wrapps the factory
     */
    public static class FactoryCacheEntry {
        private OntologyManager manager;
        private Date creation = new Date();

        public FactoryCacheEntry(OntologyManager manager) {
            this.manager = manager;
        }

        public OntologyManager getManager() {
            return manager;
        }
        
        
        /**
         * This method returns true if the object is expired.
         * @param timeout
         * @return TRUE if expired
         */
        public boolean isExpired(long timeout) {
            Date currentTime = new Date();
            if ((currentTime.getTime() - timeout) > this.creation.getTime()) {
                return true;
            }
            return false;
        }
        
        
    }
    
    
    /**
     * The factory cache manager.
     */
    public static class FactoryCache {
        
        // private member variables
        private Map<Properties,FactoryCacheEntry> entries = new HashMap<Properties,FactoryCacheEntry>(); 
        
        /**
         * The constructor
         */
        public FactoryCache() {
            
        }
        
        
        /**
         * This method attempts to get the ontology manager instance for the 
         * properties
         * @param properties The list of propeties
         * @return 
         */
        public OntologyManager getOntologyManager(Properties properties) {
            FactoryCacheEntry result = null;
            if ((result = entries.get(properties)) != null) {
                long timeout = DEFAULT_CACHE_TIMEOUT;
                if (properties.containsKey(CACHE_TIMEOUT)) {
                    timeout = Long.parseLong(properties.getProperty(CACHE_TIMEOUT));
                }
                if (!result.isExpired(timeout)) {
                    return result.getManager();
                }
            }
            return null;
        }
        
        
        /**
         * This method puts a new ontology manager.
         * 
         * @param properties The properties to perform the lookup against in future
         * @param manager The manager
         */
        public void putOntologyManager(Properties properties, OntologyManager manager) {
            entries.put(properties, new FactoryCacheEntry(manager));
        }
    }
    
    private static FactoryCache factoryCache = null;
    
    static {
        factoryCache = new FactoryCache();
    }
    
    
    /**
     * This method returns the ontology manager information.
     *
     * @param properties
     * @return
     * @throws PersistanceException
     */
    public static synchronized OntologyManager init(Properties properties)
            throws OntologyException {
        try {
            OntologyManager managerInstance = null;
            if (!properties.containsKey(IGNORE_CACHE)) {
                System.out.println("Use the cached ontology manager.");
                managerInstance = factoryCache.getOntologyManager(properties);
                if (managerInstance != null) {
                    return managerInstance;
                }
            }
            
            // retrieve the manager class.
            String manager = properties.getProperty(
                    OntologyConstants.ONTOLOGY_MANAGER_CLASS);
            if (manager == null) {
                throw new OntologyException("The [" +
                        OntologyConstants.ONTOLOGY_MANAGER_CLASS +
                        "] class name has not been supplied");
            }
            
            Class managerClassRef = Class.forName(manager);
            Constructor constructor = managerClassRef.getConstructor(Properties.class);
            managerInstance =
                    (OntologyManager)constructor.newInstance(properties);
            if (!properties.contains(IGNORE_CACHE)) {
                factoryCache.putOntologyManager(properties, managerInstance);
                
            }
            return managerInstance;
        } catch (OntologyException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new OntologyException(
                    "Failed to instanciate persistance manager exception : " +
                    ex.getMessage(),ex);
        }

    }
}
