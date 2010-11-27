/*
 * MessageQueueClient: The message queue client library
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
 * InvalidMessageType.java
 */

package com.rift.coad.daemon.messageservice;

/**
 * This exception is thrown when a message is refered to incorrectly. A RPC
 * message is used where a TEXT message should be, or a Service message is
 * used when a point-to-point message should have been.
 *
 * @author Brett Chaldecott
 */
public class InvalidMessageType extends MessageServiceException {
    
    /**
     * Creates a new instance of <code>InvalidMessageType</code> without detail
     * message.
     */
    public InvalidMessageType() {
    }
    
    
    /**
     * Constructs an instance of <code>InvalidMessageType</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidMessageType(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>InvalidMessageType</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception that caused this condition.
     */
    public InvalidMessageType(String msg, Throwable ex) {
        super(msg,ex);
    }
}
