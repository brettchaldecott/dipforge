/*
 * DesktopServerClient: The client interface to the desktop server.
 * Copyright (C) 2008  2015 Burntjam
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
 * DesktopException.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;

/**
 * The exceptions that is thrown when there is an error with the desktop server.
 * 
 * @author brett chaldecott
 */
public class DesktopException extends Exception implements Serializable {

    /**
     * Creates a new instance of <code>DesktopException</code> without detail message.
     */
    public DesktopException() {
    }


    /**
     * Constructs an instance of <code>DesktopException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public DesktopException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>DesktopException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     * @param ex The exception that generated the error.
     */
    public DesktopException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
