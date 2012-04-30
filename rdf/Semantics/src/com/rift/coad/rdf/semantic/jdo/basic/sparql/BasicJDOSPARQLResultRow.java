/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2011  Rift IT Contracting
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
 * BasicJDOSPARQLQuery.java
 */

package com.rift.coad.rdf.semantic.jdo.basic.sparql;

import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.jdo.basic.BasicJDOProxyFactory;
import com.rift.coad.rdf.semantic.ontology.OntologySession;
import com.rift.coad.rdf.semantic.persistance.PersistanceQueryException;
import com.rift.coad.rdf.semantic.persistance.PersistanceResource;
import com.rift.coad.rdf.semantic.persistance.PersistanceResultRow;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.types.DataType;
import com.rift.coad.rdf.semantic.util.ClassTypeInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The implementation of the basic jdo sparql result row
 *
 * @author brett chaldecott
 */
public class BasicJDOSPARQLResultRow implements SPARQLResultRow {

    // package path
    private PersistanceSession persistanceSession;
    private OntologySession ontologySession;
    private PersistanceResultRow resultRow;


    /**
     * The result row.
     *
     * @param persistanceSession The persistance session.
     * @param ontologySession The ontology session.
     * @param resultRow The result row.
     */
    public BasicJDOSPARQLResultRow(PersistanceSession persistanceSession,
            OntologySession ontologySession, PersistanceResultRow resultRow) {
        this.persistanceSession = persistanceSession;
        this.ontologySession = ontologySession;
        this.resultRow = resultRow;
    }



    /**
     * This method returns the size of this result.
     *
     * @return The size of the query.
     * @throws QueryException
     */
    public int size() throws QueryException {
        try {
            return resultRow.size();
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to determine the size of the "
                    + "result set : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns true if the object being referenced is a basic type.
     * 
     * @return This method returns true if this is a basic type.
     * @param name The name of the column to retrieve the result for.
     * @throws QueryException 
     */
    public boolean isBasicType(String name) throws QueryException {
        try {
            return resultRow.isBasicType(name);
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to check if this is a basic type "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns TRUE if the item is a basic type.
     * 
     * @param index The index of the item.
     * @return TRUE if this is a basic type.
     * @throws QueryException 
     */
    public boolean isBasicType(int index) throws QueryException {
        try {
            return resultRow.isBasicType(index);
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to check if this is a basic type "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves the URI of the object.
     * 
     * @param name The name of the column to retrieve the URI for.
     * @return The data type information object.
     * @throws QueryException 
     */
    public DataType getType(String name) throws QueryException {
        try {
            return resultRow.getType(name);
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to get the type information "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the string containing the type uri.
     * 
     * @param index The index of the type.
     * @return The reference to the type object
     * @throws QueryException 
     */
    public DataType getType(int index) throws QueryException {
        try {
            return resultRow.getType(index);
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to get the type information "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The names of the columns
     *
     * @return The names of the columns.
     * @throws QueryException
     */
    public String[] getColumns() throws QueryException {
        try {
            return resultRow.getColumns();
        } catch (PersistanceQueryException ex) {
            throw new QueryException("Failed to retrieve the columns : "
                    + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the required type.
     *
     * @param <T> The class type being dealt with.
     * @param type The type.
     * @param name The name
     * @return The resulting class instance matching the type.
     * @throws QueryException
     */
    public <T> T get(Class<T> type, String name) throws QueryException {
        try {
            if (ClassTypeInfo.isBasicType(type)) {
                return resultRow.get(type, name);
            } else {
                return BasicJDOProxyFactory.createJDOProxy(type,
                    persistanceSession, resultRow.get(
                    PersistanceResource.class, name), ontologySession);
            }
        } catch (Exception ex) {
            throw new QueryException("Failed to retrieve the column [" + name
                    + "] because: " + ex.getMessage(),ex);
        }
    }


    /**
     * Failed to retrieve the result entry
     *
     * @param <T> The parameter type for the generic
     * @param type The type
     * @param index The index
     * @return The index.
     * @throws QueryException
     */
    public <T> T get(Class<T> type, int index) throws QueryException {
        try {
            if (ClassTypeInfo.isBasicType(type)) {
                return resultRow.get(type, index);
            } else {
                return BasicJDOProxyFactory.createJDOProxy(type,
                    persistanceSession, resultRow.get(
                    PersistanceResource.class, index), ontologySession);
            }
        } catch (Exception ex) {
            throw new QueryException("Failed to retrieve the column [" + index
                    + "] because: " + ex.getMessage(),ex);
        }
    }

}
