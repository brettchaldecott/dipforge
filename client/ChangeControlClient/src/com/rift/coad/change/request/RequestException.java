/*
 * ChangeControlClient: The client library for the change control client.
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
 * RequestException.java
 */

// package path
package com.rift.coad.change.request;

/**
 * This object represents a request exception.
 *
 * @author brett chaldecott
 */
public class RequestException extends Exception {

    /**
     * Creates a new instance of <code>RequestException</code> without detail message.
     */
    public RequestException() {
    }


    /**
     * Constructs an instance of <code>RequestException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RequestException(String msg) {
        super(msg);
    }


    /**
     * The exception that sets the cause of this problem.
     *
     * @param msg The message containing the cause of this problem.
     * @param ex The cause of this exception.
     */
    public RequestException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
