/*
 * CoadunationUtil: The coadunation util library.
 * Copyright (C) 2007  2015 Burntjam
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
 * Connection.java
 */

// package path
package com.rift.coad.util.connection;

/**
 * This object defines the connection interface that will be used to manage a
 * connection to a object.
 *
 * @author Brett Chaldecott
 */
public interface Connection {
    /**
     * This object returns the connection to the object.
     *
     * @return The retrieve connection to the object.
     * @param type The type of object to narrow.
     * @exception ConnectionException
     * @exception java.lang.ClassCastException
     */
    public Object getConnection(Class type) throws ConnectionException, 
            java.lang.ClassCastException;
}
