/*
 * ProjectUtils: The utils used by the project system
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
 * XMLMethodMappingParser.java
 */
package com.rift.dipforge.project.method;

/**
 *
 * @author brett
 */
public class XMLMethodMappingException extends Exception {

    /**
     * Creates a new instance of <code>XMLMethodMappingException</code> without detail message.
     */
    public XMLMethodMappingException() {
    }

    /**
     * Constructs an instance of <code>XMLMethodMappingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public XMLMethodMappingException(String msg) {
        super(msg);
    }

    
    /**
     * This constructor adds the previous exceptions stack to this exception.
     * 
     * @param msg The message.
     * @param cause The cause.
     */
    public XMLMethodMappingException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    
}
