/*
 * Jython Daemon: The jython client libraries.
 * Copyright (C) 2006-2007  Rift IT Contracting
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
 * JythonDaemonException.java
 */

package com.rift.coad.daemon.jython;

/**
 * The jython daemon exception
 * 
 * @author glynn / brett chaldecott
 */
public class JythonDaemonException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>JythonEmbedException</code> without detail message.
     */
    public JythonDaemonException() {
    }
    
    
    /**
     * Constructs an instance of <code>JythonEmbedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public JythonDaemonException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>JythonEmbedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public JythonDaemonException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
