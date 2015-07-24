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
 * InvalidOperationException.java
 */
package com.rift.dipforge.ls.engine.internal;

// 
import com.rift.dipforge.ls.engine.EngineException;

/**
 * This object represents an invalid operation exception.
 * 
 * @author brett chaldecott
 */
public class InvalidOperationException extends EngineException {

    /**
     * Creates a new instance of
     * <code>InvalidOperationException</code> without detail message.
     */
    public InvalidOperationException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidOperationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidOperationException(String msg) {
        super(msg);
    }

    
    /**
     * Constructs an instance of
     * <code>InvalidOperationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param cause the cause of the message
     */
    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
