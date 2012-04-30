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

import com.rift.coad.rdf.semantic.types.DataType;

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
     * This method returns true if the object being referenced is a basic type.
     * 
     * @return This method returns true if this is a basic type.
     * @param name The name of the column to retrieve the result for.
     * @throws QueryException 
     */
    public boolean isBasicType(String name) throws QueryException;
    
    
    /**
     * This method returns TRUE if the item is a basic type.
     * 
     * @param index The index of the item.
     * @return TRUE if this is a basic type.
     * @throws QueryException 
     */
    public boolean isBasicType(int index) throws QueryException;
    
    
    /**
     * This method retrieves the URI of the object.
     * 
     * @param name The name of the column to retrieve the URI for.
     * @return The data type information object.
     * @throws QueryException 
     */
    public DataType getType(String name) throws QueryException;
    
    
    /**
     * This method returns the string containing the type uri.
     * 
     * @param index The index of the type.
     * @return The reference to the type object
     * @throws QueryException 
     */
    public DataType getType(int index) throws QueryException;
    
    
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
    public <T> T get(Class<T> type, String name) throws QueryException;

    /**
     * This method returns the entry identified by the index value.
     * @param index
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T get(Class<T> type,int index) throws QueryException;

}
