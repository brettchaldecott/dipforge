/*
 * Semantics: The semantic library for dipforge
 * Copyright (C) 2015  2015 Burntjam
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
 * DipforgeTDBTransactionManager.java
 */

// package path
package com.rift.coad.rdf.semantic.transaction;


// java imports
import java.lang.reflect.Method;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAResource;

// apache imports
import org.apache.log4j.Logger;

/**
 *
 * @author brett chaldecott
 */
public class DipforgeTDBTransactionManager extends TransactionManager {
    
    private static Logger log = Logger.getLogger(DipforgeTDBTransactionManager.class);

    // the transaction manager
    private javax.transaction.TransactionManager jtaTransManager = null;

    
    /**
     * Get a reference to the Dipforge objects via reflection or fall back
     * to jndi
     */
    public DipforgeTDBTransactionManager() {
        jtaTransManager = getTransactionManager();
    }

    
    
    @Override
    public synchronized void enlist(XAResource resource) throws TransactionManagerException {
        try {
            javax.transaction.TransactionManager jtaTransManager = this.jtaTransManager;
            if (jtaTransManager == null || jtaTransManager.getTransaction() == null) {
                jtaTransManager = getTransactionManager();
            }
            if (jtaTransManager != null) {
                jtaTransManager.getTransaction().enlistResource(resource);
            }
        } catch (Exception ex) {
            throw new TransactionManagerException("Failed to enlist the resource : " + ex.getMessage(),ex);
        }
    }
    
    
    private javax.transaction.TransactionManager getTransactionManager() {
        try {
            try {
                Class dipforgeLocalTransactionManager = 
                        Thread.currentThread().getContextClassLoader().loadClass("com.rift.coad.lib.transaction.jta.LocalTransactionManagerConnector");
                Method method = dipforgeLocalTransactionManager.getMethod("getTransactionManager");
                return (javax.transaction.TransactionManager)method.invoke(null);
            } catch (Exception ex) {
                log.error("Failed to find the class : " + ex.getMessage(),ex);
                ex.printStackTrace();
                Context context = new InitialContext();
                return (javax.transaction.TransactionManager)context.
                        lookup("java:comp/TransactionManager");
            } 
        } catch (Exception ex) {
            log.error("Failed to retrieve the transaction manager : " + 
                    ex.getMessage(),ex);
            return null;
        }
    }
}
