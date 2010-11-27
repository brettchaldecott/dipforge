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
 * DefaultEngine.java
 */

// package path
package com.rift.coad.rdf.semantic.sdb;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.store.DatasetStore;
import com.rift.coad.rdf.semantic.query.engine.EngineException;
import com.rift.coad.rdf.semantic.query.engine.QueryEngine;

/**
 *
 * @author brett
 */
public class SDBQueryEngine implements QueryEngine {
    
    // private member variables
    private Store store;
    
    /**
     * This constructor sets up the store reference.
     * 
     * @param store The store reference
     */
    public SDBQueryEngine(Store store) {
        this.store = store;
    }
    
    
    /**
     * This method executes the query on the given store.
     * 
     * @param query The query to execute.
     * @return The result set.
     * @throws com.rift.coad.rdf.semantic.query.engine.EngineException
     */
    public ResultSet execute(String query) throws EngineException {
        try {
            Dataset ds = DatasetStore.create(store);
            QueryExecution qe = QueryExecutionFactory.create(query, ds);
            return qe.execSelect();
        } catch (Exception ex) {
            throw new EngineException("Failed to execute the query : " +
                    ex.getMessage(),ex);
        }
    }

}
