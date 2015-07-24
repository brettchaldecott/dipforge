/*
 * CoadunationLib: The coaduntion library.
 * Copyright (C) 2015 2015 Burntjam
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
 * TransactionManagerConnector.java
 */

package com.rift.coad.lib.transaction;

import com.rift.coad.lib.transaction.jta.LocalTransactionManagerConnector;
import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

/**
 *  
 * 
 * @author brett chaldecott
 */
public class TransactionManagerConnector {
   
    // class constrants
    private final static String GLOBAL_TRANSACTION_JNDI = "java:comp/TransactionManager";
    
    
    /**
     * This method returns the transaction manager reference.
     * 
     * @param transactionManagerType Either a global transaction manager or a Local transaction manager.
     * @return The reference to the transaction manager.
     * @throws TransactionException 
     */
    public static TransactionManager getTransactionManager(TransactionManagerType transactionManagerType)
            throws TransactionException {
        try {
            if (transactionManagerType == TransactionManagerType.GLOBAL) {
                return (TransactionManager)(new InitialContext().lookup(GLOBAL_TRANSACTION_JNDI));
            } else if (transactionManagerType == TransactionManagerType.LOCAL) {
                return LocalTransactionManagerConnector.getTransactionManager();
            } else {
                throw new TransactionException("Unknown transaction manager");
            }
        } catch (TransactionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TransactionException(
                    "Failed to retrieve a transaction manager : " + 
                            ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the transaction manager type by string name.
     * 
     * @param name The name of the transaction manage type.
     * @return 
     * @throws TransactionException 
     */
    public static TransactionManager getTransactionManager(String name)
            throws TransactionException {
        boolean found = false;
        for(TransactionManagerType value: TransactionManagerType.values()) {
            if (value.toString().equals(name)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new TransactionException("The transaction manager type [" +
                    name + "] doesnt exist");
        }
        return getTransactionManager(TransactionManagerType.valueOf(name));
    }
}
