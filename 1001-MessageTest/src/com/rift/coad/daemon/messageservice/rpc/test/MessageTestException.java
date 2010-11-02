/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 Rift IT Contracting
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
 * MessageTestException.java
 */

// package path
package com.rift.coad.daemon.messageservice.rpc.test;

// java imports
import java.io.Serializable;

/**
 * The definition of the message test exception.
 *
 * @author Brett Chaldecott
 */
public class MessageTestException extends java.lang.Exception implements 
        Serializable {
    
    /**
     * Creates a new instance of <code>MessageTestException</code> without 
     * detail message.
     */
    public MessageTestException() {
    }
    
    
    /**
     * Constructs an instance of <code>MessageTestException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MessageTestException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>MessageTestException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The error that caused this problem.
     */
    public MessageTestException(String msg,Throwable ex) {
        super(msg,ex);
    }
}
