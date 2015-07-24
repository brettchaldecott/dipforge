/*
 * CoadunationTypeManagerConsole: The console for managing the types.
 * Copyright (C) 2009  2015 Burntjam
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
 * ResourceCreationFactory.java
 */

package com.rift.coad.type.manager.client.type.factory;

/**
 * This exception is thrown when there is an error with the factory.
 *
 * @author brett chaldecott
 */
public class FactoryException extends Exception {

    /**
     * Creates a new instance of <code>FactoryException</code> without detail message.
     */
    public FactoryException() {
    }


    /**
     * Constructs an instance of <code>FactoryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FactoryException(String msg) {
        super(msg);
    }
}
