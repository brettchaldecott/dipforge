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
 * BasicSPARQLQuery.java
 */

// package path
package com.rift.coad.rdf.semantic.query;

// java imports
import java.net.URI;
import java.util.Date;
import java.util.List;

// log4j import
import org.apache.log4j.Logger;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;

// coadunation import
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.query.sparql.SPARQLTokenParser;
import com.rift.coad.rdf.semantic.util.DateHelper;

/**
 * This object is responsible for performing the basic query on a model.
 *
 * @author brett chaldecott
 */
public class BasicSPARQLQuery implements SPARQLQuery {

    // singleton member variables
    private static Logger log = Logger.getLogger(BasicSPARQLQuery.class);

    // private member variables
    private Session session;
    private Model config;
    private Model store;
    private SPARQLTokenParser parser;

    /**
     * This constructor sets up the basic sparql query object.
     *
     * @param session The session to reference.
     * @param config The model the configuration information is retrieved from.
     * @param store The model the data is stored in.
     * @param queryString The query to execute on the database.
     */
    public BasicSPARQLQuery(Session session, Model config, Model store, String queryString) throws QueryException {
        this.session = session;
        this.config = config;
        this.store = store;
        try {
            this.parser = new SPARQLTokenParser(queryString);
        } catch (Exception ex) {
            log.error("Failed to parse the query string : " + ex.getMessage(),ex);
            throw new QueryException(
                    "Failed to parse the query string : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets a string value identified by the given index value.
     * @param index The index that identifies this parameter.
     * @param value The value to replace.
     * @return This method sets the string value.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setString(int index, String value) throws QueryException {
        try {
            parser.setIndexValue(index, value);
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the string index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the string index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the string value for the given parameter
     *
     * @param parameter The named parameter to replace
     * @param value The value to replace it with.
     * @return The result of the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setString(String parameter, String value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value);
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified string value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified string value : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method sets the value identified by the index.
     *
     * @param index The index to replace
     * @param value The value to replace.
     * @return The reference to this query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setInteger(int index, Integer value) throws QueryException {
        try {
            parser.setIndexValue(index, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the integer index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the integer index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the parameter value identified by the name.
     *
     * @param parameter The name of the parameter to replace.
     * @param value The value to replace in the string.
     * @return The reference to this query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setInteger(String parameter, Integer value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified integer value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified integer value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the long value identified by the index.
     *
     * @param index The index of the value to set.
     * @param value The new value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setLong(int index, Long value) throws QueryException {
        try {
            parser.setIndexValue(index, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the long index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the long index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the long value of the query.
     *
     * @param parameter The identifier of the parameter.
     * @param value The new value of the parameter.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setLong(String parameter, Long value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified long value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified long value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the float value identified by the index.
     * @param index The index that identifies the float value.
     * @param value The new float value.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setFloat(int index, Float value) throws QueryException {
        try {
            parser.setIndexValue(index, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the float index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the float index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the value identified by the parameter.
     * @param parameter The named parameter.
     * @param value The new value.
     * @return The reference to the current query
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setFloat(String parameter, Float value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified float value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified float value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the double value identified by the index.
     * @param index The index that identifies this properties.
     * @param value The value to replace.
     * @return The reference to the current query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setDouble(int index, Double value) throws QueryException {
        try {
            parser.setIndexValue(index, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the double index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the double index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the parameter value identifed by the parameter name.
     * @param parameter The name of the parameter to set.
     * @param value The new double value
     * @return The reference to the SPARQL query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setDouble(String parameter, Double value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified double value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified double value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the date value 
     *
     * @param index The index for the date value.
     * @param value The new value to set.
     * @return The reference to the current object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setDate(int index, Date value) throws QueryException {
        try {
            parser.setIndexValue(index, new DateHelper(value).toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the date index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the date index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets a date value for a query.
     *
     * @param parameter The parameter for the query.
     * @param value The result value.
     * @return The reference to the current object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setDate(String parameter, Date value) throws QueryException {
        try {
            parser.setKeyValue(parameter, new DateHelper(value).toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified date value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified date value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets a uri value indicated by the index.
     * @param index The index that defines the parameter to set.
     * @param value The uri value to place in the search.
     * @return The return result from the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setUri(int index, URI value) throws QueryException {
        try {
            parser.setIndexValue(index, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the uri index value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the uri index value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the uri parameter value based on the named parameter.
     *
     * @param parameter The name of the parameter to set.
     * @param value The new value of the parameter.
     * @return The reference to the sparql query object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLQuery setUri(String parameter, URI value) throws QueryException {
        try {
            parser.setKeyValue(parameter, value.toString());
            return this;
        } catch (Exception ex) {
            log.error("Failed to set the parameter identified uri value : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to set the parameter identified uri value : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method executes the query.
     * @return The result list
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public List<SPARQLResultRow> execute() throws QueryException {
        try {
            //return new SPARQLList(session, config, store, this.parser.generateQuery());
            return null;
        } catch (Exception ex) {
            log.error("Failed to execute the query : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to execute the query : " + ex.getMessage(),ex);
        }
    }
    

    
}
