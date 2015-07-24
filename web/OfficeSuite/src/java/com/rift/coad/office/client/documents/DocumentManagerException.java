/*
 * OfficeSuite: The Office Suite for Coadunation OS.
 * Copyright (C) 2010  2015 Burntjam
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
 * DocumentManagerException.java
 */


// package path
package com.rift.coad.office.client.documents;

/**
 * This exception is thrown when there are problems with the document management.
 *
 * @author brett
 */
public class DocumentManagerException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>DocumentManagerException</code> without detail message.
     */
    public DocumentManagerException() {
    }


    /**
     * Constructs an instance of <code>DocumentManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DocumentManagerException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>DocumentManagerException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause the cause of the problem.
     */
    public DocumentManagerException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
