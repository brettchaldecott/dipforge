/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2009  Rift IT Contracting
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
 * NoMappingException.java
 */

package com.rift.coad.script.client.files.mapping;

/**
 * This exception is thrown when there is a mapping problem.
 *
 * @author brett chaldecott
 */
public class NoMappingException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>NoMappingException</code> without detail message.
     */
    public NoMappingException() {
    }


    /**
     * Constructs an instance of <code>NoMappingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoMappingException(String msg) {
        super(msg);
    }
}
