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
 * SDBException.java
 */

// package path
package com.rift.coad.rdf.semantic.sdb;

/**
 * This exception is thrown when there is an error there is an error with the sdb
 * objects.
 *
 * @author brett chaldecott
 */
public class SDBException extends Exception {

    /**
     * Creates a new instance of <code>SDBException</code> without detail message.
     */
    public SDBException() {
    }


    /**
     * Constructs an instance of <code>SDBException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SDBException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>SDBException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex the cause of the exception.
     */
    public SDBException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
