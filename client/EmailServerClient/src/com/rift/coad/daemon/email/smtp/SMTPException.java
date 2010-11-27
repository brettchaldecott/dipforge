/*
 * Email Server: The email server interface
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
 * SMTPException.java
 */

// package path
package com.rift.coad.daemon.email.smtp;

/**
 * This exception is thrown when there is a problem with the server.
 *
 * @author brett chaldecott
 */
public class SMTPException extends java.lang.Exception implements 
        java.io.Serializable{
    
    /**
     * Creates a new instance of <code>SMTPException</code> without detail
     * message.
     */
    public SMTPException() {
    }
    
    
    /**
     * Constructs an instance of <code>SMTPException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SMTPException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>SMTPException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception that caused this problem.
     */
    public SMTPException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
