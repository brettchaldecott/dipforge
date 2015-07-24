/*
 * LeviathanScriptEngine: The implementation of the Leviathan script engin.
 * Copyright (C) 2012  2015 Burntjam
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
 * InvalidTypeException.java
 */

// the package path
package com.rift.dipforge.ls.engine.internal;

// the imports
import com.rift.dipforge.ls.engine.EngineException;

/**
 * This exception is thrown if there is an invalid type used in the engine.
 * 
 * @author brett chaldecott
 */
public class InvalidTypeException extends EngineException {

    /**
     * Creates a new instance of
     * <code>InvalidType</code> without detail message.
     */
    public InvalidTypeException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidType</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidTypeException(String msg) {
        super(msg);
    }
}
