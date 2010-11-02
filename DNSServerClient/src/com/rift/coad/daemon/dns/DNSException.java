/*
 * DNS: The dns server interface
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
 * DNSServerMBean.java
 */

// package path
package com.rift.coad.daemon.dns;


/**
 * The dns server exception.
 *
 * @author brett chaldecott
 */
public class DNSException extends java.lang.Exception
        implements java.io.Serializable {
    
    /**
     * Creates a new instance of <code>DNSException</code> without detail message.
     */
    public DNSException() {
    }
    
    
    /**
     * Constructs an instance of <code>DNSException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DNSException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>DNSException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DNSException(String msg, Exception ex) {
        super(msg,ex);
    }
}
