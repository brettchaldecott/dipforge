/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * BasicSPARQLQuery.java
 */

package com.rift.coad.rdf.semantic.query.sparql;

/**
 * This exception is thrown when there is an error with the coadunation SPARQL handling.
 *
 * @author brett chaldecott
 */
public class SPARQLException extends Exception {

    /**
     * Creates a new instance of <code>SPARQLException</code> without detail message.
     */
    public SPARQLException() {
    }


    /**
     * Constructs an instance of <code>SPARQLException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SPARQLException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>SPARQLException</code> with the specified detail message.
     * @param msg the detail message.
     * @param ex the cause of this exception.
     */
    public SPARQLException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
