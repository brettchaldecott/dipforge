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
 * JenaStoreTypes.java
 */


package com.rift.coad.rdf.semantic.persistance.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.rift.coad.rdf.semantic.SessionManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceException;
import com.rift.coad.rdf.semantic.persistance.PersistanceManager;
import com.rift.coad.rdf.semantic.persistance.PersistanceSession;
import com.rift.coad.rdf.semantic.persistance.jena.sdb.JenaSDBModelFactory;
import com.rift.coad.rdf.semantic.persistance.jena.tdb.JenaTDBModelFactory;
import com.rift.coad.rdf.semantic.persistance.jena.xml.JenaXMLModelFactory;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * The implementation of the jena persistance manager.
 * 
 * @author brett chaldecott
 */
public class JenaPersistanceManager implements PersistanceManager {

    private static Logger log = Logger.getLogger(JenaPersistanceManager.class);

    // private member variables
    private JenaStore store;


    /**
     * This constructor is responsible for createing the object based on the
     * property information passed in.
     *
     * @param properties The properties to pass in.
     */
    public JenaPersistanceManager(Properties properties) throws PersistanceException {
        String jenaStoreType = properties.getProperty(JenaStoreTypes.JENA_STORE_TYPE);
        if (jenaStoreType == null) {
            throw new PersistanceException("The property [" + JenaStoreTypes.JENA_STORE_TYPE +
                    "] was not supplied it must be suppiled with one of the following [" +
                    JenaStoreTypes.SDB + "," +
                    JenaStoreTypes.TDB + "," +
                    JenaStoreTypes.XML + "]");
        }
        if (jenaStoreType.equals(JenaStoreTypes.SDB)) {
            store = JenaSDBModelFactory.createInstance(properties);
        } else if (jenaStoreType.equals(JenaStoreTypes.TDB)) {
            store = JenaTDBModelFactory.createInstance(properties);
        } else if (jenaStoreType.equals(JenaStoreTypes.XML)) {
            store = JenaXMLModelFactory.createInstance(properties);
        } else {
            throw new PersistanceException("The property [" + JenaStoreTypes.JENA_STORE_TYPE +
                    "] was contained an unknown store type must be one of the following [" +
                    JenaStoreTypes.SDB + "," +
                    JenaStoreTypes.TDB + "," +
                    JenaStoreTypes.XML + "]");
        }
    }





    /**
     * The name of the persitance manager.
     *
     * @return The name of the persistance manager.
     */
    public String getName() {
        return "JenaPersistanceManager";
    }


    /**
     * The type of rdf store managed through this implementation.
     *
     * @return The string containing the rdf store information.
     */
    public String getRDFStore() {
        return "Jena";
    }


    /**
     * This method returns the version of the persistance manager.
     *
     * @return The string containing the version of the persistance manager.
     */
    public String getVersion() {
        return "1.0.1";
    }


    /**
     * This method returns the persistance session.
     *
     * @return The reference to the persistance session.
     * @throws PersistanceException
     */
    public PersistanceSession getSession() throws PersistanceException {
        return new JenaPersistanceSession(store.getModule(),store.getType());
    }
    
    
    /**
     * This method returns the persistance session.
     *
     * @param lock The type of lock to utilize on this session.
     * @return The reference to the persistance session.
     * @throws PersistanceException
     */
    public PersistanceSession getSession(SessionManager.SessionLock lock) 
            throws PersistanceException {
        return new JenaPersistanceSession(store.getModule(),lock);
    }
    
    /**
     * This method is responsible for closing the jena persistance manager.
     *
     * @throws PersistanceException
     */
    public void close() throws PersistanceException {
        try {
            store.close();
        } catch (Exception ex) {
            log.error("Failed to close the jena peristance manager becaues : " +
                    ex.getMessage());
        }
    }
    
}
