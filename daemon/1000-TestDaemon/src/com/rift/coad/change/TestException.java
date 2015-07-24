/*
 * TestDaemons: This implements the test daemons.
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
 * TestException.java
 */

package com.rift.coad.change;

import com.rift.coad.datamapperbroker.*;

/**
 * This object represents a test.
 *
 * @author brett chaldecott
 */
public class TestException extends Exception {

    /**
     * Creates a new instance of <code>TestException</code> without detail message.
     */
    public TestException() {
    }


    /**
     * Constructs an instance of <code>TestException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TestException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>TestException</code> with the specified detail message.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public TestException(String message, Throwable cause) {
        super(message, cause);
    }


    
}
