/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance;

/**
 *
 * @author brett chaldecott
 */
public interface PersistanceManager {
    /**
     * The name of the persitance manager.
     *
     * @return The name of the persistance manager.
     */
    public String getName();


    /**
     * The type of rdf store managed through this implementation.
     *
     * @return The string containing the rdf store information.
     */
    public String getRDFStore();


    /**
     * This method returns the version of the persistance manager.
     *
     * @return The string containing the version of the persistance manager.
     */
    public String getVersion();


    /**
     * This method returns the persistance session.
     *
     * @return The reference to the persistance session.
     * @throws PersistanceException
     */
    public PersistanceSession getSession() throws PersistanceException;


    /**
     * This method is called to close down the persistance manager, releaseing
     * all consumed resources.
     * 
     * @throws PersistanceException
     */
    public void close() throws PersistanceException;
}
