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
 * This exception is thrown when there is an exception with the transaction manager.
 * @author brett chaldecott
 */
public class TransactionManagerException extends Exception {

    /**
     * Creates a new instance of <code>TransactionManagerException</code> without detail message.
     */
    public TransactionManagerException() {
    }


    /**
     * Constructs an instance of <code>TransactionManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TransactionManagerException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>TransactionManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TransactionManagerException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
