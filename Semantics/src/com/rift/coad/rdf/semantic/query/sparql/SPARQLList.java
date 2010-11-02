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
 * SPARQLList.java
 */

// package path
package com.rift.coad.rdf.semantic.query.sparql;

// java imports
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// log4j imports
import org.apache.log4j.Logger;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.QuerySolution;

// coadunation imports
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.SPARQLResultEntry;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.query.engine.EngineManager;


/**
 * This object manages a list of SPARQL row items.
 *
 * @author brett chaldecott
 */
public class SPARQLList extends ArrayList<SPARQLResultRow> {

    // class singletons
    private static Logger log = Logger.getLogger(SPARQLList.class);

    // private member variables
    private Session session;
    private Model config;
    private Model store;


    /**
     * The constructor of the SPARQL list object.
     *
     * @param session The reference to the session object.
     * @param config The reference to the configuration store.
     * @param store The reference to the data store.
     * @param query The string containing the query.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLList(Session session, Model config, Model store, String queryString) throws QueryException {
        try {
            ResultSet resultSet = EngineManager.getInstance().getEngine(store).execute(queryString);
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                BasicSPARQLResultRow row = new BasicSPARQLResultRow(session,config,store);
                for (Iterator iter = solution.varNames(); iter.hasNext();) {
                    String name = (String)iter.next();
                    BasicSPARQLResultEntry entry = new BasicSPARQLResultEntry(
                            session, config, store, name, solution);
                    row.addColumn(name, entry);
                }
                this.add(row);
            }
        } catch (Exception ex) {
            log.error("Failed to execute the query : " + ex.getMessage(),ex);
            throw new QueryException
                    ("Failed to execute the query : " + ex.getMessage(),ex);
        }
    }





}
