/*
 * ChangeControlManager: The manager for the change events.
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
 * QueryException.java
 */


// package path
package com.rift.coad.change.client.resources;


/**
 * 
 *
 * @author brett chaldecott
 */
public class QueryException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>QueryException</code> without detail message.
     */
    public QueryException() {
    }


    /**
     * Constructs an instance of <code>QueryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public QueryException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>QueryException</code> with the specified detail message.
     * @param message the detail message.
     * @param cause The cause of the current situation.
     */
    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }



}
