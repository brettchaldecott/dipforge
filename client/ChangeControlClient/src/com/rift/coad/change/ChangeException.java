/*
 * ChangeControlClient: The client library for the change control client.
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
 * ChangeManagerDaemon.java
 */

package com.rift.coad.change;

/**
 * The exception that is thrown when there is a change exception.
 *
 * @author brett chaldecott
 */
public class ChangeException extends Exception {

    /**
     * Creates a new instance of <code>ChangeException</code> without detail message.
     */
    public ChangeException() {
    }


    /**
     * Constructs an instance of <code>ChangeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ChangeException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>ChangeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The cause of the exception
     */
    public ChangeException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
