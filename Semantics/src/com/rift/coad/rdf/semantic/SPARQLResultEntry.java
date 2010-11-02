/*
 * Semantics: The semantic library for coadunation os
 * Copyright (C) 2009  Rift IT Contracting
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
 * SPARQLResultEntry.java
 */

package com.rift.coad.rdf.semantic;

/**
 * This interface represents the result of an sql query.
 *
 * @author brett chaldecott
 */
public interface SPARQLResultEntry {

    /**
     * This method returns the name of the column this entry is attached to.
     *
     * @return The string containing the name of this result.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String getName() throws QueryException;

    
    /**
     * This method returns the first object within the result.
     *
     * @param <T> The type to cast the object to.
     * @param c The type of object.
     * @return An intance of specified object type.
     * @throws com.rift.coad.rdf.semantic.Query
     */
    public <T> T cast(Class<T> c) throws QueryException;


    /**
     * This method returns the resource information.
     *
     * @return The resource object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Resource getResource() throws QueryException;
}
