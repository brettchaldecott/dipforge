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
 * BasicSPARQLResultEntry.java
 */

// package path
package com.rift.coad.rdf.semantic.query.sparql;

// log4j imports
import org.apache.log4j.Logger;

// jena imports
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

// jena bean imports
import thewebsemantic.RDF2Bean;

// coadunation imports
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.SPARQLResultEntry;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.resource.BasicResource;
import com.rift.coad.rdf.semantic.util.jena.DataHelper;
import thewebsemantic.Bean2RDF;

/**
 * This object handles the result entry.
 *
 * @author brett chaldecott
 */
public class BasicSPARQLResultEntry implements SPARQLResultEntry {
    // class singleton
    private static Logger log = Logger.getLogger(BasicSPARQLResultEntry.class);

    // private member variables
    private Session session;
    private Model config;
    private Model store;
    private RDF2Bean storeReader;
    private String name;
    private QuerySolution solution;
    private Literal literalValue;
    private Resource resourceValue;
    private String uri;

    /**
     * This constructor instanciates the object and sets all the internal values.
     * @param session The reference to the session that this query result is attached to.
     * @param config The reference to the model that contains the configuration information.
     * @param store The reference to the model that contains the data.
     * @param name The name of this result entry.
     * @param literal The literal to retrieve the information from.
     */
    public BasicSPARQLResultEntry(Session session, Model config, Model store, String name, QuerySolution solution) {
        this.session = session;
        this.config = config;
        this.store = store;
        storeReader = new RDF2Bean(store);
        this.name = name;
        this.solution = solution;
        if (this.solution.get(name).isLiteral()) {
            literalValue = this.solution.getLiteral(name);
        } else if (this.solution.get(name).isURIResource()) {
            uri = this.solution.get(name).toString();
        } else {
            resourceValue = this.solution.getResource(name);
        }
    }


    /**
     * This method returns the name of the result entry.
     *
     * @return The name of the entry.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String getName() throws QueryException {
        return this.name;
    }


    /**
     * This method performs a cast on the literal value.
     *
     * @param <T> The resultant object type
     * @param c The object type to cast to.
     * @return An instance of the object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T cast(Class<T> c) throws QueryException {
        try {
            boolean basic = DataHelper.isBasic(c);
            if (basic && this.literalValue != null) {
                return c.cast(DataHelper.convertLiteral(literalValue, c));
            } else if (uri != null) {
                if (c.equals(String.class)) {
                    return c.cast(uri);
                } else if (!basic) {
                    return c.cast(storeReader.load(uri));
                }
            } else {
                if (c.equals(String.class)) {
                    return c.cast(uri);
                } else if (!basic) {
                    return c.cast(storeReader.load(this.resourceValue.getURI()));
                }
            }
            throw new QueryException("Unrecognised type : " + c.getName());
        } catch (QueryException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to read the data from the store : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to read the data from the store : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the resource information.
     *
     * @return The resource object.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public com.rift.coad.rdf.semantic.Resource getResource() throws QueryException {
        if (this.uri != null) {
            return new BasicResource(store,new Bean2RDF(store),storeReader,store.getResource(uri));
        } else if (this.resourceValue == null) {
            throw new QueryException("There is not resource value to back this object up");
        }
        return new BasicResource(store,new Bean2RDF(store),storeReader,resourceValue);
    }

    
    /**
     * This method returns the string value held by the literal.
     *
     * @return The string identifying this value.
     */
    @Override
    public String toString() {
        if (this.literalValue != null) {
            return literalValue.getString();
        } else if (this.uri != null) {
            return uri;
        } else {
            return this.resourceValue.toString();
        }
    }

    
}
