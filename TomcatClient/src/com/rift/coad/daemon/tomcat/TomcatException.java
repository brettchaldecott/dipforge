/*
 * Tomcat: The deployer for the tomcat daemon
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
 * TomcatException.java
 */

// package path
package com.rift.coad.daemon.tomcat;

// java imports
import java.lang.Exception;
import java.io.Serializable;

/**
 * This is the parent exception for all exceptions thrown by the tomcat
 * deployer.
 *
 * @author Brett Chaldecott
 */
public class TomcatException extends Exception implements Serializable {
    
    /**
     * Creates a new instance of <code>TomcatException</code> without 
     * detail message.
     */
    public TomcatException() {
    }
    
    
    /**
     * Constructs an instance of <code>TomcatException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TomcatException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>TomcatException</code> with the 
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack to throw.
     */
    public TomcatException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
