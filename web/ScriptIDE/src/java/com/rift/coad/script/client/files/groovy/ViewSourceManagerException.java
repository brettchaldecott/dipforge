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
 * ViewSourceManagerException.java
 */

package com.rift.coad.script.client.files.groovy;

/**
 * This exception is thrown when there is a problem with the view source method.
 *
 * @author brett chaldecott
 */
public class ViewSourceManagerException extends Exception implements java.io.Serializable {

    /**
     * Creates a new instance of <code>ViewSourceManagerException</code> without detail message.
     */
    public ViewSourceManagerException() {
    }


    /**
     * Constructs an instance of <code>ViewSourceManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ViewSourceManagerException(String msg) {
        super(msg);
    }
}
