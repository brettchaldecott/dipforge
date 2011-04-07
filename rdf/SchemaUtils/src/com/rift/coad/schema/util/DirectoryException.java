/*
 * SchemaUtils: Utilities implemented for the RDF schema.
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
 * DirectoryUtil.java
 */

package com.rift.coad.schema.util;

/**
 * This exception is thrown from the directory exception.
 *
 * @author brett chaldecott
 */
public class DirectoryException extends Exception {

    /**
     * Creates a new instance of <code>DirectoryException</code> without detail message.
     */
    public DirectoryException() {
    }


    /**
     * Constructs an instance of <code>DirectoryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DirectoryException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>DirectoryException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of the exception
     */
    public DirectoryException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
