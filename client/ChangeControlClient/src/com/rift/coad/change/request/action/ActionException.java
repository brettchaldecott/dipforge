/*
 * ChangeControlManager: The manager for the change events.
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
 * ActionFactoryManager.java
 */

// package path
package com.rift.coad.change.request.action;

// java serialization
import java.io.Serializable;


/**
 * This exception is thrown when the action instance has a problem.
 *
 * @author brett chaldecott
 */
public class ActionException extends Exception implements Serializable {

    /**
     * Creates a new instance of <code>ActionException</code> without detail message.
     */
    public ActionException() {
    }


    /**
     * Constructs an instance of <code>ActionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ActionException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>ActionException</code> with the specified detail message.
     * 
     * @param msg The description of the exception
     * @param ex The cause of the exception
     */
    public ActionException(String msg, Throwable ex) {
        super(msg);
    }
}
