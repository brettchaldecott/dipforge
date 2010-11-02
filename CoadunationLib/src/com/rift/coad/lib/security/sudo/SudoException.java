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
 * SudoException.java
 *
 * This exception will get thrown when there is an error while handling the a
 * sudo command.
 */

package com.rift.coad.lib.security.sudo;

/**
 * This exception will get thrown when there is an error while handling the a
 * sudo command.
 *
 * @author Brett Chaldecott
 */
public class SudoException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>SudoException</code> without detail message.
     */
    public SudoException() {
    }
    
    
    /**
     * Constructs an instance of <code>SudoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SudoException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>SudoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack.
     */
    public SudoException(String msg,Throwable ex) {
        super(msg,ex);
    }
}
