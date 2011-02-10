/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * This interface provides method to manipulate a query.
 *
 * @author brett chaldecott
 */
public interface PersistanceQuery {
    /**
     * This method sets the string parameters for the query.
     *
     * @param index The index for the query
     * @param value The string value for the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setString(int index, String value) throws PersistanceQueryException;


    /**
     * The string setter for the named parameter.
     *
     * @param parameter The name of the parameter to set.
     * @param value The new value for the parameter.
     * @return The return result for the query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setString(String parameter, String value) throws PersistanceQueryException;


    /**
     * The setter for the index identified parameter.
     *
     * @param index The index of the parameter.
     * @param value The new value of the parameter.
     * @return The query object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setInteger(int index, Integer value) throws PersistanceQueryException;


    /**
     * The setter for the integer identified parameter.
     *
     * @param parameter The parameter identified by the name.
     * @param value The new value for the parameter.
     * @return The reference to the query object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setInteger(String parameter, Integer value) throws PersistanceQueryException;


    /**
     * The setter for the index defined long value.
     *
     * @param index The index of the parameter.
     * @param value The new long value for the column
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setLong(int index, Long value) throws PersistanceQueryException;


    /**
     * The setter for the named parameter.
     *
     * @param parameter The parameter identified by name.
     * @param value The new long value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setLong(String parameter, Long value) throws PersistanceQueryException;


    /**
     * The setter for the float identified index parameter.
     *
     * @param index The parameter identified by the index.
     * @param value The new float value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setFloat(int index, Float value) throws PersistanceQueryException;


    /**
     * This method sets the named parameter float value.
     *
     * @param parameter The name of the parameter to set the value for.
     * @param value The new value of the parameter.
     * @return The reference to the query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setFloat(String parameter, Float value) throws PersistanceQueryException;


    /**
     * The setter for the index identified double value.
     *
     * @param index The index of the parameter that has to be set.
     * @param value The value to set.
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setDouble(int index, Double value) throws PersistanceQueryException;


    /**
     * The setter for the named double parameter value.
     *
     * @param parameter The name of the parameter to set.
     * @param value The double value of the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setDouble(String parameter, Double value) throws PersistanceQueryException;


    /**
     * The setter for the date parameter identified by index.
     *
     * @param index The index to identify the value.
     * @param value The date value to set.
     * @return The reference to the current query object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setDate(int index, Date value) throws PersistanceQueryException;


    /**
     * This method sets the date value for the parameter.
     *
     * @param parameter name of the date value..
     * @param value The new date value.
     * @return The query result object.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setDate(String parameter, Date value) throws PersistanceQueryException;

    /**
     * The setter for the uri parameter identified by index.
     *
     * @param index The index of the uri.
     * @param value The value of the parameter.
     * @return The query result.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setUri(int index, URI value) throws PersistanceQueryException;


    /**
     * The setter for the named uri parameter.
     *
     * @param parameter The name of the parameter.
     * @param value The new value for the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public PersistanceQuery setUri(String parameter, URI value) throws PersistanceQueryException;


    /**
     * This method returns the list of result.
     *
     * @return The result of the query.
     * @throws com.rift.coad.rdf.semantic.PersistanceQueryException
     */
    public List<PersistanceResultRow> execute() throws PersistanceQueryException;
}
