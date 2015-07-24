/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * GeneratorException.java
 */

package com.rift.coad.rdf.semantic.jdo.generator;

/**
 * This exception is thrown when there is a problem with the generator.
 *
 * @author brett chaldecott
 */
public class GeneratorException extends Exception {

    /**
     * Creates a new instance of <code>GeneratorException</code> without detail message.
     */
    public GeneratorException() {
    }


    /**
     * Constructs an instance of <code>GeneratorException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public GeneratorException(String msg) {
        super(msg);
    }


    /**
     * This is constructor takes the cause of the problem.
     *
     * @param msg The message to add to the exception.
     * @param cause The cause of the exception
     */
    public GeneratorException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
