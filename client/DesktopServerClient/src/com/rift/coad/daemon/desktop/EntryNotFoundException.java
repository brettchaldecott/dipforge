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
 * EntryNotFoundException.java
 */

// package path
package com.rift.coad.daemon.desktop;

// java imports
import java.io.Serializable;

/**
 * This exception is thrown when the specified entry is not found.
 * 
 * @author brett chaldecott
 */
public class EntryNotFoundException extends DesktopException {

    /**
     * Creates a new instance of <code>EntryNotFoundException</code> without detail message.
     */
    public EntryNotFoundException() {
    }


    /**
     * Constructs an instance of <code>EntryNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EntryNotFoundException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>EntryNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of the exception
     */
    public EntryNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
