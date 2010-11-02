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
 * PHPFileManagerException.java
 */


package com.rift.coad.script.client.files.php;

/**
 * This exception is thrown when there is a problem with the file manager.
 *
 * @author brett chaldecott
 */
public class PHPFileManagerException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>PHPFileManagerException</code> without detail message.
     */
    public PHPFileManagerException() {
    }


    /**
     * Constructs an instance of <code>PHPFileManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PHPFileManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>PHPFileManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public PHPFileManagerException(String msg, Throwable cause) {
        super(msg);
    }
}
