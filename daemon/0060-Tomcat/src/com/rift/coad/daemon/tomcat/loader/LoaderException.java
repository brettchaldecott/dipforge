/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2011  2015 Burntjam
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
 * LoaderException.java
 */

package com.rift.coad.daemon.tomcat.loader;

/**
 * The loader exception that gets thrown when there is a problem with the loader.
 *
 * @author brett chaldecott
 */
public class LoaderException extends Exception {

    /**
     * Creates a new instance of <code>LoaderException</code> without detail message.
     */
    public LoaderException() {
    }


    /**
     * Constructs an instance of <code>LoaderException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LoaderException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>LoaderException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public LoaderException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
