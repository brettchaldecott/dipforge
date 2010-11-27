/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * QueryEngine.java
 */

// package path
package com.rift.coad.rdf.semantic.query.engine;

// result set
import com.hp.hpl.jena.query.ResultSet;

/**
 * The definition of the query engine interface. This is jena specific and works
 * around the differences with JENA, SDB and TDB.
 *
 * @author brett chaldecott
 */
public interface QueryEngine {
    /**
     * This method performs the query using the appropriate data store.
     *
     * @param query The query to execute
     * @return The resulting result set.
     * @throws EngineException
     */
    public ResultSet execute(String query) throws EngineException;
}
