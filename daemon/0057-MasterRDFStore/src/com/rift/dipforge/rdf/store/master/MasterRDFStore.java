/*
 * MasterRDFStoreClient: The master RDF store interface
 * Copyright (C) 2012  2015 Burntjam
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
 * MasterRDFStoreDaemon.java
 */

// package path
package com.rift.dipforge.rdf.store.master;

import com.rift.coad.util.connection.ConnectionManager;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 * The implementation of the MasterRDFStore mbean.
 * 
 * @author brett chaldecott
 */
public class MasterRDFStore implements MasterRDFStoreMBean {

    
    // class singletons.
    public static Logger log = Logger.getLogger(MasterRDFStore.class);
    
    
    /**
     * The default constructor
     */
    public MasterRDFStore() {
    }
    
    
    
    
    /**
     * This method returns the name of the store.
     * 
     * @return The string containing the name of the master store.
     * @throws RemoteException 
     */
    @Override
    public String getName() throws RemoteException {
        return this.getClass().getName();
    }

    
    /**
     * This method returns the description of the master store.
     * 
     * @return The string containing the description.
     * @throws RemoteException 
     */
    @Override
    public String getDescription() throws RemoteException {
        return "Master RDF Store";
    }

    
    /**
     * The version information.
     * 
     * @return
     * @throws RemoteException 
     */
    @Override
    public String getVersion() throws RemoteException {
        return "1.0";
    }

    
    /**
     * This method returns the stats of the store.
     * 
     * @return The stats in the store.
     */
    @Override
    public String getStats() {
        return MasterRDFStoreStatsManager.getInstance().getStats();
    }

    
    /**
     * This method is called to persist the changes.
     * 
     * @param action The action to call.
     * @param rdfXML
     * @throws MasterRDFStoreException
     * @throws RemoteException 
     */
    @Override
    public void persist(String action, String rdfXML) throws MasterRDFStoreException {
        try {
            MasterRDFStoreDaemon server = (MasterRDFStoreDaemon)ConnectionManager.getInstance().
                    getConnection(MasterRDFStoreDaemon.class, "java:comp/env/bean/rdf/MasterRDFStoreDaemon");
            server.persist(action, rdfXML);
        } catch (Exception ex) {
            log.error("Failed to persist the store information : " + ex.getMessage(),ex);
            throw new MasterRDFStoreException
                ("Failed to persist the store information : " + ex.getMessage(),ex);
        }
    }
    
}
