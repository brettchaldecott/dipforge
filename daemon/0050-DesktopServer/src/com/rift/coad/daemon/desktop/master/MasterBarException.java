/*
 * DesktopServer: The server responsible for managing the general desktop resources.
 * Copyright (C) 2008  Rift IT Contracting
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
 * MenuParser.java
 */

// packag path
package com.rift.coad.daemon.desktop.master;

/**
 * This object
 * @author brett chaldecott
 */
public class MasterBarException extends Exception {

    /**
     * Creates a new instance of <code>MasterBarException</code> without detail message.
     */
    public MasterBarException() {
    }


    /**
     * Constructs an instance of <code>MasterBarException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MasterBarException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>MasterBarException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The cause of this exception
     */
    public MasterBarException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
