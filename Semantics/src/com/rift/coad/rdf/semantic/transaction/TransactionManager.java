/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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

/**
 * This object manages the access to the transaction manager. It assumes there will only ever be one type of
 * transaction object per environment.
 *
 * @author brett chaldecott
 */
public abstract class TransactionManager {

    // the class singleton value.
    private static TransactionManager singleton = null;

    /**
     * This
     */
    public static synchronized TransactionManager getInstance() throws TransactionManagerException {
        if (singleton == null) {
            singleton = new DefaultTransactionManager();
        }
        return singleton;
    }

    
    /**
     * This method is called to set the transaction manager.
     *
     * @param singleton The new transaction manager that should be set as the singleton.
     */
    public static synchronized void setTransactionManager(TransactionManager singleton) {
        TransactionManager.singleton = singleton;
    }


    /**
     * This method is called to enlist the specified resource in a transaction.
     *
     * @param resource The resource to enlist in the transaction.
     * @throws com.rift.coad.rdf.semantic.transaction.TransactionManagerException
     */
    public abstract void enlist(javax.transaction.xa.XAResource resource) throws TransactionManagerException;
    
}
