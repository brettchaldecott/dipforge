/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.lib.transaction.jta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 * This object is responsible for the transaction managers associated with various
 * class loader contexts.
 * 
 * @author brett chaldecott
 */
public class LocalTransactionManagerConnector {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(LocalTransactionManagerConnector.class.getName());
    
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
        // Find the first manager with a transaction
        ClassLoader currentLoader = loader;
        LocalTransactionManager manager = null;
        while (currentLoader != null) {
            manager = managers.get(currentLoader);
            try {
                if (manager != null && manager.getTransaction() != null) {
                    return manager;
                }
            } catch (Exception ex) {
                // failed to 
                log.info("Failed to retrieve the transaction manager " + 
                        ex.getMessage());
            }
            currentLoader = currentLoader.getParent();
        }
        
        // There is no active transaction with any manager
        // fall back to the first manager.
        manager = null;
        currentLoader = loader;
        while (currentLoader != null) {
            manager = managers.get(currentLoader);
            try {
                if (manager != null) {
                    return manager;
                }
            } catch (Exception ex) {
                // failed to 
                log.info("Failed to retrieve the transaction manager " + 
                        ex.getMessage());
            }
            currentLoader = currentLoader.getParent();
        }
        
        // check if a manager was found for the class loader
        // if not found a new transaction will be created on demand
        manager = new LocalTransactionManager();
        managers.put(loader, manager);
        return manager;
    }
}
