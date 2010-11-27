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
 * BasicQuery.java
 */

// package path
package com.rift.coad.rdf.semantic.query;

// java imports
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.io.ByteArrayInputStream;

// jena bean imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import thewebsemantic.Bean2RDF;
import thewebsemantic.RDF2Bean;
import thewebsemantic.Sparql;

// sqljep imports
import org.medfoster.sqljep.Parser;

// coadunation imports
import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.Session;


/**
 * This object implements the query interface
 * @author brett
 */
public class BasicQuery implements Query {

    // private member variables
    private Session session;
    private Model config;
    private Model store;
    private String queryString;

    /**
     * The session that this query is attached to.
     *
     * @param session The session this query is attached to.
     * @param config The configuration model.
     * @param store The data store model
     * @param store The query that will be executed on the store model
     */
    public BasicQuery(Session session, Model config, Model store, String queryString) {
        this.session = session;
        this.config = config;
        this.store = store;
        this.queryString = queryString;
    }


    /**
     * This method sets the string return result.
     *
     * @param index The index of the variable to replace in the query string.
     * @param value
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public Query setString(int index, String value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setString(String parameter, String value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setInteger(int index, Integer value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setInteger(String parameter, Integer value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setLong(int index, Long value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setLong(String parameter, Long value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setFloat(int index, Float value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setFloat(String parameter, Float value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setDouble(int index, Double value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setDouble(String parameter, Double value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setDate(int index, Date value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setDate(String parameter, Date value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setUri(int index, URI value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query setUri(String parameter, URI value) throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List execute() throws QueryException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
