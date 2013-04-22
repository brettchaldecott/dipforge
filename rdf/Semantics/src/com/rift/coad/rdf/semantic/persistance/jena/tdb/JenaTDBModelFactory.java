/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rift.coad.rdf.semantic.persistance.jena.tdb;

import com.hp.hpl.jena.rdf.model.Model;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStore;
import com.rift.coad.rdf.semantic.persistance.jena.JenaStoreType;
import java.util.Properties;

/**
 *
 * @author brettc
 */
public class JenaTDBModelFactory implements JenaStore {


    /**
     * This method creates a new instance of the xml model factory.
     *
     * @param properties The properties.
     * @return The reference to the new store.
     * @throws PersistanceException
     */
    public static JenaStore createInstance(Properties properties)
            throws PersistanceException {
        throw new UnsupportedOperationException("TDB store Not supported yet.");
    }

    public Model getModule() throws PersistanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() throws PersistanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /**
     * This method is called to determine the type of store type being utilized.
     * 
     * @return The enum definingthe type of store.
     */
    public JenaStoreType getType() {
        return JenaStoreType.TDB;
    }
    
    
    

}
