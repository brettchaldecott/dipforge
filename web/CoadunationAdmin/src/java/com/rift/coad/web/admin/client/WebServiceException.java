/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * WebServiceException.java
 */

// package
package com.rift.coad.web.admin.client;

// java imports
import java.io.Serializable;

/**
 * The exception that is thrown when there is a problem with the web service
 * manager.
 *
 * @author brett chaldecott
 */
public class WebServiceException extends java.lang.Exception implements Serializable {
    
    /**
     * Creates a new instance of <code>WebServiceException</code> without detail message.
     */
    public WebServiceException() {
    }
    
    
    /**
     * Constructs an instance of <code>WebServiceException</code> with the 
     * specified detail message.
     * @param msg the detail message.
     */
    public WebServiceException(String msg) {
        super(msg);
    }
}
