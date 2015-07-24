/*
 * MessageTest: This is a test message service library.
 * Copyright (C) 2007 2015 Burntjam
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
 * ServiceException.java
 */

// package path
package com.rift.coad.daemon.messageservice.service.test;

// java imports
import java.io.Serializable;

/**
 * The service exception.
 *
 * @author Brett Chaldecott
 */
public class ServiceException extends java.lang.Exception implements 
        Serializable {
    
    /**
     * Creates a new instance of <code>ServiceException</code> without detail 
     * message.
     */
    public ServiceException() {
    }
    
    
    /**
     * Constructs an instance of <code>ServiceException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>ServiceException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception
     */
    public ServiceException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
