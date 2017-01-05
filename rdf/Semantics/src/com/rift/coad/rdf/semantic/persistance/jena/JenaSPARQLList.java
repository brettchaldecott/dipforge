/*
 * CoaduntionSemantics: The semantic library for coadunation os
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
 * JenaSPARQLList.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

// java imports
import com.rift.coad.rdf.semantic.query.sparql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// log4j imports
import org.apache.log4j.Logger;

// jena imports
import org.apache.jena.rdf.model.Model;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.QuerySolution;

// coadunation imports
import org.apache.jena.sdb.store.DatasetStore;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResultRow;
import com.rift.coad.rdf.semantic.persistance.jena.http.HttpModel;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;


/**
 * This object manages a list of SPARQL row items.
 *
 * @author brett chaldecott
 */
public class JenaSPARQLList extends ArrayList<PersistanceResultRow> {

    // class singletons
    private static Logger log = Logger.getLogger(JenaSPARQLList.class);

    // private member variables
    private Model store;


    /**
     * The constructor of the SPARQL list object.
     *
     * @param store The reference to the data store.
     * @param query The string containing the query.
     * @throws PersistanceQueryException
     */
    public JenaSPARQLList(Model store, String queryString) throws 
            PersistanceQueryException {
        try {
            Query query = QueryFactory.create(queryString);
            // check if an http model is being used
            ResultSet resultSet = null;
            if (store instanceof HttpModel) {
                // perform an http sqarql query to retrieve the results
                HttpModel httpModel = (HttpModel)store;
                QueryExecution executioner = QueryExecutionFactory.sparqlService(httpModel.getServiceUrl(),query);
                resultSet = executioner.execSelect();
            } else {
                // use a local native store.
                QueryExecution executioner = null;
                executioner = QueryExecutionFactory.create(query, store);
                resultSet = executioner.execSelect();
            }
            //ResultSet resultSet = executioner.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                JenaPersistanceResultRow row = new JenaPersistanceResultRow(store,solution);
                this.add(row);
            }
        } catch (Exception ex) {
            log.error("Failed to execute the query : " + ex.getMessage(),ex);
            throw new PersistanceQueryException
                    ("Failed to execute the query : " + ex.getMessage(),ex);
        }
    }





}
