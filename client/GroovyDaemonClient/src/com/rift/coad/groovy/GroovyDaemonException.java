/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
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
 * GroovyManagerMBean.java
 */

package com.rift.coad.groovy;

/**
 * The exception that is thrown when there is a problem with the groovy daemon.
 *
 * @author brett chaldecott
 */
public class GroovyDaemonException extends Exception implements java.io.Serializable {

    private String stringStack;
    
    /**
     * Creates a new instance of <code>GroovyDaemonException</code> without detail message.
     */
    public GroovyDaemonException() {
    }


    /**
     * Constructs an instance of <code>GroovyDaemonException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public GroovyDaemonException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>GroovyDaemonException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The cause of this exception.
     */
    public GroovyDaemonException(String msg, Throwable ex) {
        super(msg,ex);
    }

    
    /**
     * Constructs an instance of <code>GroovyDaemonException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex The cause of this exception.
     */
    public GroovyDaemonException(String msg, String stringStack) {
        super(msg);
        this.stringStack = stringStack;
    }
    
    
    /**
     * The getter for the string stack method.
     * 
     * @return The string stack
     */
    public String getStringStack() {
        return stringStack;
    }

    
    /**
     * The setter for the string stack.
     * 
     * @param stringStack The reference to the new string stack
     */
    public void setStringStack(String stringStack) {
        this.stringStack = stringStack;
    }
    
    
    
}
