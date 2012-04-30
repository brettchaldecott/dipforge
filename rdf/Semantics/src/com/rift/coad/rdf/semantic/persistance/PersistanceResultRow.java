/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

import com.rift.coad.rdf.semantic.QueryException;
import com.rift.coad.rdf.semantic.types.DataType;

/**
 * This method object contains the persistant result row information.
 *
 * @author brett chaldecott
 */
public interface PersistanceResultRow {
    /**
     * This method returns the number of columns that are
     * @return
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public int size() throws PersistanceQueryException;


    /**
     * This method returns the names of the columns in this result row.
     *
     * @return The string list containing the name of the columns.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public String[] getColumns() throws PersistanceQueryException;


    /**
     * This method returns true if the object being referenced is a basic type.
     * 
     * @return This method returns true if this is a basic type.
     * @param name The name of the column to retrieve the result for.
     * @throws PersistanceQueryException 
     */
    public boolean isBasicType(String name) throws PersistanceQueryException;
    
    
    /**
     * This method returns TRUE if the item is a basic type.
     * 
     * @param index The index of the item.
     * @return TRUE if this is a basic type.
     * @throws PersistanceQueryException 
     */
    public boolean isBasicType(int index) throws PersistanceQueryException;
    
    
    /**
     * This method retrieves the URI of the object.
     * 
     * @param name The name of the column to retrieve the URI for.
     * @return The data type information object.
     * @throws PersistanceQueryException 
     */
    public DataType getType(String name) throws PersistanceQueryException;
    
    
    /**
     * This method returns the string containing the type uri.
     * 
     * @param index The index of the type.
     * @return The reference to the type object
     * @throws PersistanceQueryException 
     */
    public DataType getType(int index) throws PersistanceQueryException;
    
    
    /**
     * This method returns the first sparql result entry identified by the name.
     * If there are multiple entries attached to a name use the index method or getGroup
     * method instead.
     *
     * @param name The name of entry ot retrieve.
     * @return The first result object attached to the given name.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T get(Class<T> t, String name) throws PersistanceQueryException;

    
    /**
     * This method returns the entry identified by the index value.
     * @param index The index number.
     * @return The identifier fo the index.
     * @throws com.rift.coad.rdf.semantic.QueryException
     */
    public <T> T get(Class<T> t, int index) throws PersistanceQueryException;
}
