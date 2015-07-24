/*
 * Semantics: The semantic library for coadunation os
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
 * Query.java
 */

// package path
package com.rift.coad.rdf.semantic;

// java imports
import java.util.Date;
import java.util.List;
import java.net.URI;

/**
 * This interface defines the semantic query interface.
 *
 * @author brett chaldecott
 */
public interface Query {
    /**
     * This method sets the string parameters for the query.
     * 
     * @param index The index for the query
     * @param value The string value for the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setString(int index, String value) throws QueryException;


    /**
     * The string setter for the named parameter.
     *
     * @param parameter The name of the parameter to set.
     * @param value The new value for the parameter.
     * @return The return result for the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setString(String parameter, String value) throws QueryException;


    /**
     * The setter for the index identified parameter.
     *
     * @param index The index of the parameter.
     * @param value The new value of the parameter.
     * @return The query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setInteger(int index, Integer value) throws QueryException;


    /**
     * The setter for the integer identified parameter.
     *
     * @param parameter The parameter identified by the name.
     * @param value The new value for the parameter.
     * @return The reference to the query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setInteger(String parameter, Integer value) throws QueryException;


    /**
     * The setter for the index defined long value.
     *
     * @param index The index of the parameter.
     * @param value The new long value for the column
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setLong(int index, Long value) throws QueryException;


    /**
     * The setter for the named parameter.
     *
     * @param parameter The parameter identified by name.
     * @param value The new long value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setLong(String parameter, Long value) throws QueryException;


    /**
     * The setter for the float identified index parameter.
     *
     * @param index The parameter identified by the index.
     * @param value The new float value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setFloat(int index, Float value) throws QueryException;


    /**
     * This method sets the named parameter float value.
     *
     * @param parameter The name of the parameter to set the value for.
     * @param value The new value of the parameter.
     * @return The reference to the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setFloat(String parameter, Float value) throws QueryException;


    /**
     * The setter for the index identified double value.
     *
     * @param index The index of the parameter that has to be set.
     * @param value The value to set.
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setDouble(int index, Double value) throws QueryException;


    /**
     * The setter for the named double parameter value.
     *
     * @param parameter The name of the parameter to set.
     * @param value The double value of the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setDouble(String parameter, Double value) throws QueryException;


    /**
     * The setter for the date parameter identified by index.
     *
     * @param index The index to identify the value.
     * @param value The date value to set.
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setDate(int index, Date value) throws QueryException;


    /**
     * This method sets the date value for the parameter.
     *
     * @param parameter name of the date value..
     * @param value The new date value.
     * @return The query result object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setDate(String parameter, Date value) throws QueryException;

    /**
     * The setter for the uri parameter identified by index.
     *
     * @param index The index of the uri.
     * @param value The value of the parameter.
     * @return The query result.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setUri(int index, URI value) throws QueryException;


    /**
     * The setter for the named uri parameter.
     *
     * @param parameter The name of the parameter.
     * @param value The new value for the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setUri(String parameter, URI value) throws QueryException;

    
    /**
     * This method returns the list of result.
     *
     * @return The result of the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public List execute() throws QueryException;
    
}
