/*
 * Semantics: The semantic library for coadunation os
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
 * DefaultTransactionManager.java
 */

package com.rift.coad.rdf.semantic.transaction;

// java imports
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.xa.XAResource;
import org.apache.log4j.Logger;



/**
 * The default transaction manager
 * 
 * @author brett chaldecott
 */
public class DefaultTransactionManager extends TransactionManager {

    private static Logger log = Logger.getLogger(DefaultTransactionManager.class);
    
    // the transaction manager
    private javax.transaction.TransactionManager jtaTransManager = null;

    /**
     * The default contructor of the transaction manager.
     */
    public DefaultTransactionManager() {
        try {
            Context context = new InitialContext();
            jtaTransManager = (javax.transaction.TransactionManager)context.
                    lookup("java:comp/TransactionManager");
        } catch (Exception ex) {
            log.error("Failed to retrieve the transaction manager : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to enlist the transaction in the JTA managed transactions.
     *
     * @param resource The resource to add to the jta transaction.
     * @throws com.rift.coad.rdf.semantic.transaction.TransactionManagerException
     */
    @Override
    public void enlist(XAResource resource) throws TransactionManagerException {
        if (jtaTransManager != null) {
            try {
                jtaTransManager.getTransaction().enlistResource(resource);
            } catch (Exception ex) {
                throw new TransactionManagerException("Failed to enlist the resource : " + ex.getMessage(),ex);
            }
        }
    }

}
