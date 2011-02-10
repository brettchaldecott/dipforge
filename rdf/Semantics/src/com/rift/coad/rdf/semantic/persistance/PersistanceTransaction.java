/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * PersistanceSession.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance;

/**
 * This interface defines the methods to manage a transaction on behalf of a
 * persistance session.
 *
 * @author brett chaldecott
 */
public interface PersistanceTransaction {

    /**
     * This method is called to being the transaction scope.
     * 
     * @throws PersistanceException
     */
    public void begin() throws PersistanceException;
    
    
    /**
     * This method is responsible for commiting the transaction.
     *
     * @throws PersistanceException
     */
    public void commit() throws PersistanceException;


    /**
     * This method is responsible for rolling back the transactional changes.
     * 
     * @throws PersistanceException
     */
    public void rollback() throws PersistanceException;


    /**
     * Returns true if a transaction has been started for this object.
     *
     * @return Returns true if a transaction has been started for this object.
     * @throws PersistanceException
     */
    public boolean isInTransaction() throws PersistanceException;
}
