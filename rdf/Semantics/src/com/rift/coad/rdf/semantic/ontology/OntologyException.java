/*
 * Semantics: The semantic library for coadunation os
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
 * OntologyFactory.java
 */

package com.rift.coad.rdf.semantic.ontology;

/**
 * This class represents an ontology exception base.
 *
 * @author brett chaldecott
 */
public class OntologyException extends Exception {

    /**
     * Creates a new instance of <code>OntologyException</code> without detail message.
     */
    public OntologyException() {
    }


    /**
     * Constructs an instance of <code>OntologyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OntologyException(String msg) {
        super(msg);
    }


    /**
     * Constructs an instance of <code>OntologyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param ex the cause of the problem.
     */
    public OntologyException(String msg, Throwable ex) {
        super(msg,ex);
    }
}
