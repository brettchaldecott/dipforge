/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * TransactionDirector.java
 *
 * This object is responsible for setting up the transaction manager and for
 * supplying access to the transaction manger
 */

// package path
package com.rift.coad.lib.transaction;

// java imports
import javax.transaction.UserTransaction;
import javax.transaction.TransactionManager;

// log 4 j imports
import org.apache.log4j.Logger;

// JOTM imports
import org.objectweb.jotm.Jotm;

// coadunation imports
import com.rift.coad.lib.naming.ContextManager;

/**
 * This object is responsible for setting up the transaction manager and for
 * supplying access to the transaction manger. It sets up the JOTM transaction
 * manager or Jonas Transaction manager.
 *
 * @author Brett Chaldecott
 */
public class TransactionDirector {
    
    // the user transaction context
    private final static String TRANSACTION_CONTEXT_BASE = 
            "java:comp/";
    private final static String USER_TRANSACTION_CONTEXT_BINDING = 
            "UserTransaction";
    private final static String TRANSACTION_MANAGER_CONTEXT_BINDING = 
            "TransactionManager";
    
    // logger manager reference
    private Logger log = Logger.getLogger(TransactionDirector.class.getName());
    
    // private member variables
    private static TransactionDirector singleton = null;
    private Jotm jotm = null;
    private UserTransaction userTransaction = null;
    private TransactionManager transactionManager = null;
    
    /** 
     * Creates a new instance of TransactionDirector 
     */
    public TransactionDirector() throws TransactionException {
        try {
            jotm = new Jotm(true,true);
            ContextManager contextManager = 
                    new ContextManager(TRANSACTION_CONTEXT_BASE);
            userTransaction = jotm.getUserTransaction();
            userTransaction.setTransactionTimeout(60 * 60 * 1000);
            contextManager.bind(USER_TRANSACTION_CONTEXT_BINDING,
                    userTransaction);
            transactionManager = jotm.getTransactionManager();
            contextManager.bind(TRANSACTION_MANAGER_CONTEXT_BINDING,
                    transactionManager);
        } catch (Exception ex) {
            throw new TransactionException(
                    "Failed to instanciate the transaction directory because : " 
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method inits and returns a reference to the transaction director.
     *
     * @return A reference to the Transaction Directory.
     * @exception TransactionException
     */
    public static synchronized TransactionDirector init() 
            throws TransactionException {
        if (singleton == null) {
            singleton = new TransactionDirector();
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the transaction instance.
     *
     * @return A reference to the transaction director instance.
     * @exception TransactionException
     */
    public static synchronized TransactionDirector getInstance() 
            throws TransactionException {
        if (singleton == null) {
            throw new TransactionException(
                    "The transaction director is not instanciated.");
        }
        return singleton;
    }
    
    
    /**
     * This method returns the reference to the user transaction.
     *
     * @return A reference to the user transaction object.
     */
    public UserTransaction getUserTransaction() {
        return userTransaction;
    }
    
    
    /**
     * This method returns a reference to the transaction manager.
     *
     * @return A reference to the transaction manager object.
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
    
    
    /**
     * This method stops the transaction manager.
     */
    public void stop() {
        try {
            jotm.stop();
        } catch (Exception ex) {
            log.error("Failed to shut down the transaction manager : "  + 
                    ex.getMessage(),ex);
        }
    }
}
