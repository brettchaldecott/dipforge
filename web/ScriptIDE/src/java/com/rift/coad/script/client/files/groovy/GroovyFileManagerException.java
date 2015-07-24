/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * GroovyFileManager.java
 */

package com.rift.coad.script.client.files.groovy;

/**
 * This exception is 
 *
 * @author brett chaldecott
 */
public class GroovyFileManagerException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>GroovyFileManagerException</code> without detail message.
     */
    public GroovyFileManagerException() {
    }


    /**
     * Constructs an instance of <code>GroovyFileManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public GroovyFileManagerException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>GroovyFileManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public GroovyFileManagerException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
