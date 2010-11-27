/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * XMLConfigurationException.java
 *
 * RMIException.java
 *
 * Created on November 8, 2006, 6:35 AM
 *
 * Copyright November 8, 2006 Rift Marketing CC
 *
 * The RMI exception thrown when ever there is an error while dealing with the
 * RMI or TIE classes.
 */

package com.rift.coad.lib.deployment.rmi;

/**
 * The RMI exception thrown when ever there is an error while dealing with the
 * RMI or TIE classes.
 *
 * @author mincemeat
 */
public class RMIException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>RMIException</code> without detail message.
     */
    public RMIException() {
    }
    
    
    /**
     * Constructs an instance of <code>RMIException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RMIException(String msg) {
        super(msg);
    }
    
    
    /**
     * Constructs an instance of <code>RMIException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex The exception stack.
     */
    public RMIException(String msg, Throwable ex) {
        super(msg);
    }
}
