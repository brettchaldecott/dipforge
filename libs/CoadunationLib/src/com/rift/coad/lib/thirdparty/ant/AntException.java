/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * AntException.java
 *
 * The exception that gets thrown if an error occurs with ant.
 */

package com.rift.coad.lib.thirdparty.ant;

/**
 * The exception that gets thrown if an error occurs with ant.
 *
 * @author Brett Chaldecott
 */
public class AntException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>AntException</code> without detail message.
     */
    public AntException() {
    }
    
    
    /**
     * Constructs an instance of <code>AntException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AntException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>AntException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack.
     */
    public AntException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
