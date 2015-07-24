/*
 * CoadunationCRMClient: The CRM client library
 * Copyright (C) 2008  2015 Burntjam
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
 * CRMException.java
 */

// package path
package com.rift.coad.crm;

/**
 * The exception that gets thrown when there is a problem with the crm system.
 * @author brett chaldecott
 */
public class CRMException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>CRMException</code> without detail message.
     */
    public CRMException() {
    }


    /**
     * Constructs an instance of <code>CRMException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CRMException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>CRMException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of the exception.
     */
    public CRMException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
