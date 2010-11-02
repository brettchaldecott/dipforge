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
 * AuthorizationException.java
 *
 * This exception is thrown when there is an Authorization related error.
 */

package com.rift.coad.lib.security;

/**
 * This exception is thrown when there is an Authorization related error.
 *
 * @author Brett Chaldecott
 */
public class AuthorizationException extends 
        com.rift.coad.lib.security.SecurityException {
    
    /**
     * Creates a new instance of <code>AuthorizationException</code> without detail message.
     *
     * @param msg the detail message.
     */
    public AuthorizationException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>AuthorizationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The reference to the exception stack
     */
    public AuthorizationException(String msg,Throwable ex) {
        super(msg,ex);
    }
}
