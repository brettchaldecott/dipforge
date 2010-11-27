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
 * SPARQLResultRow.java
 */

// the package path
package com.rift.coad.rdf.semantic;

/**
 * This interface represents an sql result set row.
 *
 * @author brett chaldecott
 */
public interface SPARQLResultRow {
    
    /**
     * This method returns the number of columns that are 
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public int size() throws QueryException;


    /**
     * This method returns the names of the columns in this result row.
     * 
     * @return The string list containing the name of the columns.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String[] getColumns() throws QueryException;


    /**
     * This method returns the first sparql result entry identified by the name.
     * If there are multiple entries attached to a name use the index method or getGroup
     * method instead.
     *
     * @param name The name of entry ot retrieve.
     * @return The first result object attached to the given name.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLResultEntry get(String name) throws QueryException;

    /**
     * This method returns the entry identified by the index value.
     * @param index
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLResultEntry get(int index) throws QueryException;

}
