/*
 * RDBUserManagerClient: The client of the RDB User Manager
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
 * RDBUserManagementException.java
 */

// package path
package com.rift.coad.daemon.rdbusermanager;

/**
 * This exception is thrown when there is an error with the rdb user management.
 * 
 * @author brett chaldecott
 */
public class RDBUserManagementException extends Exception {

    /**
     * Creates a new instance of <code>RDBUserManagementException</code> without detail message.
     */
    public RDBUserManagementException() {
    }


    /**
     * Constructs an instance of <code>RDBUserManagementException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RDBUserManagementException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of 
     * <code>RDBUserManagementException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     * @param ex The cause of this exception.
     */
    public RDBUserManagementException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
