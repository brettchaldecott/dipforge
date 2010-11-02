/*
 * CoadunationUtil: The coadunation util library.
 * Copyright (C) 2007  Rift IT Contracting
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
 * ConnectionException.java
 */

package com.rift.coad.util.connection;

/**
 * The name being looked up does not exist.
 *
 * @author Brett Chaldecott
 */
public class NameNotFound extends ConnectionException {
    
    /**
     * Creates a new instance of <code>NameNotFound</code> without detail 
     * message.
     */
    public NameNotFound() {
    }
    
    
    /**
     * Constructs an instance of <code>NameNotFound</code> with the specified 
     * detail message.
     *
     * @param msg the detail message.
     */
    public NameNotFound(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>NameNotFound</code> with the specified 
     * detail message.
     *
     * @param msg the detail message.
     * @param ex The exception being thrown.
     */
    public NameNotFound(String msg,Exception ex) {
        super(msg,ex);
    }
}
