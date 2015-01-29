/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.lib.transaction.jta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This object is responsible for the transaction managers associated with various
 * class loader contexts.
 * 
 * @author brett chaldecott
 */
public class LocalTransactionManagerConnector {
    
    private static Map<ClassLoader,LocalTransactionManager> managers = 
            new ConcurrentHashMap<>();
    
    
    /**
     * The private constructor
     */
    private LocalTransactionManagerConnector() {
        
    }
    
    /**
     * This method is responsible for getting a reference to the transaction manager
     * 
     * @return The reference to the transaction manager for this class loader
     */
    public static synchronized LocalTransactionManager getTransactionManager() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        
        // walk the parent class loader to find a transaction loader
        ClassLoader currentLoader = loader;
        LocalTransactionManager manager = managers.get(loader);
        while (manager == null && currentLoader.getParent() != null) {
            currentLoader = currentLoader.getParent();
            manager = managers.get(currentLoader);
        }
        
        // check if a manager was found for the class loader
        // if not found a new transaction will be created on demand
        if (manager == null) {
            manager = new LocalTransactionManager();
            managers.put(loader, manager);
        }
        return manager;
    }
}
