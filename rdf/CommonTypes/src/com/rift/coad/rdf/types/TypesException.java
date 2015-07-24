/*
 * CommonTypes: The common types
 * Copyright (C) 2011  2015 Burntjam
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
 * MethodMapping.java
 */
package com.rift.coad.rdf.types;

/**
 * This exception is thrown when an exception occurs with the type system.
 * 
 * @author brett chaldecott
 */
public class TypesException extends Exception {

    /**
     * Creates a new instance of <code>TypesException</code> without detail message.
     */
    public TypesException() {
    }

    /**
     * Constructs an instance of <code>TypesException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TypesException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>TypesException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of this exception
     */
    public TypesException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
