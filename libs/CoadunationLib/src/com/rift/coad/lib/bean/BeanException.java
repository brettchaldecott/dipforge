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
 * BeanException.java
 *
 * This exception is thrown when an error occurs with the Coadunation Beans.
 */

package com.rift.coad.lib.bean;

/**
 * This exception is thrown when an error occurs with the Coadunation Beans.
 *
 * @author Brett Chaldecott
 */
public class BeanException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>BeanException</code> without detail message.
     *
     * @param msg The string message that describes ths exception
     */
    public BeanException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>BeanException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The throwable exception.
     */
    public BeanException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
