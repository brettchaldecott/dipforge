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

package com.rift.coad.rdf.semantic.query.engine;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * This object implements the default engine query against the supplied model.
 *
 * @author brett chaldecott
 */
public class DefaultEngine implements QueryEngine {
    
    // private member variables
    private Model store;

    /**
     * The constructor that sets up the default engine.
     *
     * @param store The reference to the store.
     */
    public DefaultEngine(Model store) {
        this.store = store;
    }
    
    /**
     * The method returns a 
     * @param query
     * @return
     * @throws com.rift.coad.rdf.semantic.query.engine.EngineException
     */
    public ResultSet execute(String queryString) throws EngineException {
        try {
            Query query = QueryFactory.create(queryString);
            QueryExecution executioner = QueryExecutionFactory.create(query, store);
            return executioner.execSelect();
        } catch (Exception ex) {
            throw new EngineException("Failed to perform the query : " +
                    ex.getMessage(),ex);
        }
    }

}
