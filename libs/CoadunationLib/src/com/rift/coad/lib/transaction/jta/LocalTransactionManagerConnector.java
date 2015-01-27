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
        LocalTransactionManager manager = managers.get(loader);
        if (manager == null) {
            manager = new LocalTransactionManager();
            managers.put(loader, manager);
        }
        return manager;
    }
}
