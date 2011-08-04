/*
 * 0200-ProjectManager: The project manager implentation.
 * Copyright (C) 2011  Rift IT Contracting
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
 * ProjectFactoryException.java
 */


package com.rift.dipforge.project.factory;

/**
 *
 * @author brettc
 */
public class ProjectFactoryException extends Exception {

    /**
     * Creates a new instance of <code>ProjectFactoryException</code> without detail message.
     */
    public ProjectFactoryException() {
    }


    /**
     * Constructs an instance of <code>ProjectFactoryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ProjectFactoryException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>ProjectFactoryException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause The cause of the exception
     */
    public ProjectFactoryException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
