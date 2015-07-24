/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * TypeManager.java
 */

// package path
package com.rift.coad.rdf.objmapping.util.client.type;

/**
 * This exception is thrown when there is an exceptions with types.
 *
 * @author brett chaldecott
 */
public class TypeException extends Exception {

    /**
     * Creates a new instance of <code>TypeException</code> without detail message.
     */
    public TypeException() {
    }


    /**
     * Constructs an instance of <code>TypeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TypeException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>TypeException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The exception
     */
    public TypeException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
