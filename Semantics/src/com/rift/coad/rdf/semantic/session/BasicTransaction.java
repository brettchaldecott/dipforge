/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicTransaction.java
 */

// package path
package com.rift.coad.rdf.semantic.session;

// log4j imports
import org.apache.log4j.Logger;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;

// javax transaction manager.
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

// coadunation imports
import com.rift.coad.rdf.semantic.TransactionException;
import com.rift.coad.rdf.semantic.Transaction;
import com.rift.coad.rdf.semantic.transaction.TransactionManager;

/**
 * The transaction that manages the basic transaction.
 *
 * @author brett chaldecott
 */
public class BasicTransaction implements com.rift.coad.rdf.semantic.Transaction, XAResource {
    // static member variables
    private static Logger log = Logger.getLogger(BasicTransaction.class);

    // private member variables
    private Model store;
    private boolean enlisted = false;
    
    /**
     * The constructor that sets the store reference.
     * @param store
     */
    public BasicTransaction(Model store) throws TransactionException {
        this.store = store;

        try {
            TransactionManager.getInstance().enlist(this);
        } catch (Exception ex) {
            log.error("Failed to enlist this transaction : " + ex.getMessage(),ex);
            throw new TransactionException
                    ("Failed to enlist this transaction : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method starts a new transaction
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void begin() throws TransactionException {
        if (store.supportsTransactions() && !enlisted) {
            store.begin();
        }
    }

    /**
     * This method is called to rollback the changes to the current model.
     *
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void rollback() throws TransactionException {
        if (store.supportsTransactions() && !enlisted) {
            store.abort();
        }
    }


    /**
     * This object is called to commit the changes to the basic transaction object.
     *
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void commit() throws TransactionException {
        if (store.supportsTransactions() && !enlisted) {
            store.commit();
        }
    }


    /**
     * This method is called by the JTA transaction manager to commit changes to enlisted transaction managers.
     *
     * @param arg0 The identifier of this transaction.
     * @param arg1
     * @throws javax.transaction.xa.XAException
     */
    public void commit(Xid arg0, boolean arg1) throws XAException {
        if (store.supportsTransactions()) {
            store.commit();
        }
    }


    /**
     * This method is called when a transaction is ended and delisted.
     * @param arg0 The id of the transaction that has ended and is being delisted.
     * @param arg1
     * @throws javax.transaction.xa.XAException
     */
    public void end(Xid arg0, int arg1) throws XAException {
        
    }


    /**
     * This method is called to forget the changes made to  transaction.
     * @param arg0 The id of the transactions.
     * @throws javax.transaction.xa.XAException
     */
    public void forget(Xid arg0) throws XAException {
        
    }


    /**
     * This method returns the transaction timeout.
     *
     * @return The integer containing the timeout for the transaction.
     * @throws javax.transaction.xa.XAException
     */
    public int getTransactionTimeout() throws XAException {
        return 0;
    }


    /**
     * This method returns true if the resource is the same.
     * @param arg0
     * @return
     * @throws javax.transaction.xa.XAException
     */
    public boolean isSameRM(XAResource arg0) throws XAException {
        if (arg0 == this) {
            return true;
        }
        return false;
    }


    /**
     * This method is called to prepare a transaction.
     * @param arg0 The id of the transaction to prepare.
     * @return
     * @throws javax.transaction.xa.XAException
     */
    public int prepare(Xid arg0) throws XAException {
        return XAResource.XA_OK;
    }


    /**
     * This method is called to recover a transaction.
     * @param arg0 The arguments.
     * @return The xid of the transaction that have been recoved.
     * @throws javax.transaction.xa.XAException
     */
    public Xid[] recover(int arg0) throws XAException {
        return null;
    }

    /**
     * This method is called to rollback a transaction.
     *
     * @param arg0 The id of the transaction to roll back.
     * @throws javax.transaction.xa.XAException
     */
    public void rollback(Xid arg0) throws XAException {
        if (store.supportsTransactions()) {
            store.abort();
        }
    }


    /**
     * This method sets the transaction timeout time.
     * @param arg0 The timeout value.
     * @return TRUE if the timeout has been set, FALSE if not.
     * @throws javax.transaction.xa.XAException
     */
    public boolean setTransactionTimeout(int arg0) throws XAException {
        return true;
    }

    /**
     * This method is called when a transaction is started.
     *
     * @param id The id of the transaction.
     * @param arg1
     * @throws javax.transaction.xa.XAException
     */
    public void start(Xid arg0, int arg1) throws XAException {
        enlisted = true;
        if (store.supportsTransactions()) {
            store.begin();
        }
    }



}
