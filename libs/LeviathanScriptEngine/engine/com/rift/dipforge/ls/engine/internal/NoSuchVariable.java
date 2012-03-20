/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  Rift IT Contracting
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
 * NoSuchVariable.java
 */

// package path
package com.rift.dipforge.ls.engine.internal;

import com.rift.dipforge.ls.engine.EngineException;

/**
 * The definition of the no such variable exception
 * 
 * @author brett chaldecott
 */
public class NoSuchVariable extends EngineException {

    /**
     * Creates a new instance of
     * <code>NoSuchVariable</code> without detail message.
     */
    public NoSuchVariable() {
    }

    /**
     * Constructs an instance of
     * <code>NoSuchVariable</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchVariable(String msg) {
        super(msg);
    }

    
    /**
     * This exception sets the no such variable exception.
     * 
     * @param message The message cause.
     * @param cause The cause of the exception.
     */
    public NoSuchVariable(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
