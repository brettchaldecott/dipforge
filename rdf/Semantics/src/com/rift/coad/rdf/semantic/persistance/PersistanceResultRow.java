/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

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
