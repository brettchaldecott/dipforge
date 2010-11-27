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
package com.rift.coad.rdf.semantic.query.sparql;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// jena imports
import com.hp.hpl.jena.rdf.model.Model;

// coadunation imports
import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.SPARQLResultEntry;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;

/**
 * This object represents a sparql result row
 *
 * @author brett chaldecott
 */
public class BasicSPARQLResultRow implements SPARQLResultRow {
    // private member variables
    private Session session;
    private Model config;
    private Model store;
    private List<String> columns = new ArrayList<String>();
    private Map<String,SPARQLResultEntry> entries = new HashMap<String,SPARQLResultEntry>();

    
    /**
     * This constructors sets up the necessary member variables.
     * 
     * @param session The reference to the session variable.
     * @param config The configuration object reference.
     * @param store The store the information is retrieved from.
     */
    public BasicSPARQLResultRow(Session session, Model config, Model store) {
        this.session = session;
        this.config = config;
        this.store = store;
    }


    /**
     * The size of the result set.
     *
     * @return This method returns the size of the SPARQL result row.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public int size() throws QueryException {
        return columns.size();
    }


    /**
     * This method returns the list of columns for this row.
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String[] getColumns() throws QueryException {
        return columns.toArray(new String[0]);
    }

    /**
     * This method adds a new column to the sparql result entry.
     *
     * @param name The name of the record.
     * @param entry The record entry.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public void addColumn(String name,SPARQLResultEntry entry) throws QueryException {
        columns.add(name);
        entries.put(name, entry);
    }

    
    /**
     * This method returns the result entry identified by the name.
     *
     * @param name This method returns the entry identified by the name.
     * @return The result set.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLResultEntry get(String name) throws QueryException {
        if (this.entries.containsKey(name)) {
            return this.entries.get(name);
        }
        throw new QueryException("The key [" + name + "] was not found in the store");
    }

    /**
     * This method retrieves the entry identified by the index.
     *
     * @param index The index that identifies the entry
     * @return The reference to the result entry.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public SPARQLResultEntry get(int index) throws QueryException {
        if (this.columns.size() <= index) {
            throw new QueryException("The index is out of range.");
        }
        return this.entries.get(this.columns.get(index));
    }

}
