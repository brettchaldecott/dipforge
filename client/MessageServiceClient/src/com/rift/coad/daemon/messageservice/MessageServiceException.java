/*
 * MessageQueueClient: The message queue client library
 * Copyright (C) 2007  Rift IT Contracting
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
 * MessageServiceException.java
 */

// package path
package com.rift.coad.daemon.messageservice;

// java imports
import java.lang.Exception;
import java.io.Serializable;

/**
 * This exception is the parent exception to all exceptions thrown by the
 * messaging service.
 *
 * @author Brett Chaldecott
 */
public class MessageServiceException extends Exception implements Serializable {
    
    /**
     * Creates a new instance of <code>MessageServiceException</code> without 
     * detail message.
     */
    public MessageServiceException() {
    }
    
    
    /**
     * Constructs an instance of <code>MessageServiceException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MessageServiceException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>MessageServiceException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack to throw.
     */
    public MessageServiceException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
