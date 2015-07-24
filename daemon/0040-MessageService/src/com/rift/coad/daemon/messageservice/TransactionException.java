/*
 * MessageService: The message service daemon
 * Copyright (C) 2006  2015 Burntjam
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
 * TransactionException.java
 */

package com.rift.coad.daemon.messageservice;

/**
 *
 * @author mincemeat
 */
public class TransactionException extends MessageServiceException {
    
    /**
     * Creates a new instance of <code>TransactionException</code> without 
     * detail message.
     */
    public TransactionException() {
    }
    
    
    /**
     * Constructs an instance of <code>TransactionException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TransactionException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>TransactionException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception that caused this state.
     */
    public TransactionException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
