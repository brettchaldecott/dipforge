/*
 * RSSReaderClient: The RSS Reader.
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
 * RSSClientException.java
 */


package com.rift.coad.daemon.rss;

// imports
import java.io.Serializable;

/**
 *
 * @author brett
 */
public class RSSClientException extends Exception implements Serializable {

    /**
     * Creates a new instance of <code>RSSClientException</code> without detail message.
     */
    public RSSClientException() {
    }


    /**
     * Constructs an instance of <code>RSSClientException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RSSClientException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>RSSClientException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RSSClientException(String msg, Throwable ex) {
        super(msg,ex);
    }
    
}
