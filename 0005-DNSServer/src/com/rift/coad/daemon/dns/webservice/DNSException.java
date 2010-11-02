/*
 * EMailServer: The email server interface
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
package com.rift.coad.daemon.dns.webservice;


/**
 * The dns server exception.
 *
 * @author brett chaldecott
 */
public class DNSException extends java.lang.Exception
        implements java.io.Serializable {
    
    // public member variables
    public String message = null;
    public String cause = null;
    
}
