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
 * DataType.java
 */

// package path
package com.rift.coad.rdf.semantic.types;

// imports
import java.net.URI;

/**
 * This method defines the data type object.
 *
 * @author brett chaldecott
 */
public interface DataType {

    /**
     * This method returns the uri of the object.
     *
     * @return The reference to the uri.
     */
    public URI getURI() throws DataTypeException;


    /**
     * The string containing the namespace information.
     *
     * @return The string containing the namespace.
     */
    public String getNamespace() throws DataTypeException;


    /**
     * This method sets the local name.
     *
     * @return The string containing the local name.
     */
    public String getLocalName() throws DataTypeException;

}
