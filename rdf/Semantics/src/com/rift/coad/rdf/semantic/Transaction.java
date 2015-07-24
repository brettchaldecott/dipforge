/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
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
 * Transaction.java
 */

package com.rift.coad.rdf.semantic;

/**
 * This interface defines the transaction interface provided by the semantic objects.
 *
 * @author brett chaldecott
 */
public interface Transaction {
    /**
     * This object is responsible for creating a new transaction.
     *
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void begin() throws TransactionException;


    /**
     * This method is responsible for rolling back the changes made to the semantic session.
     *
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void rollback() throws TransactionException;


    /**
     * This method commits the changes to the transaction.
     *
     * @throws com.rift.coad.rdf.semantic.TransactionException
     */
    public void commit() throws TransactionException;

}
