/*
 * RDFStoreClient: The rdf store daemon client.
 * Copyright (C) 2011  2015 Burntjam
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
 * RDFStoreMBean.java
 */

package com.rift.dipforge.rdf.store;

import java.rmi.RemoteException;

/**
 * The rdf store information.
 *
 * @author brett chaldecott
 */
public class RDFStore implements RDFStoreMBean {

    /**
     * The default constructor for the RDF store.
     */
    public RDFStore() {
        RDFStoreStatsManager.getInstance();
    }

    
    /**
     * This method returns the name of the RDF Store.
     *
     * @return This method returns the name of this entity.
     * @throws RemoteException
     */
    public String getName() throws RemoteException {
        return RDFStore.class.getName();
    }


    /**
     * This method returns the description of this store.
     *
     * @return The description of this store.
     * @throws RemoteException
     */
    public String getDescription() throws RemoteException {
        return "The RDF store.";
    }


    /**
     * This method returns the version information.
     *
     * @return The version information.
     * @throws RemoteException
     */
    public String getVersion() throws RemoteException {
        return "1.0";
    }


    /**
     * This method returns the stats information for the rdf store.
     *
     * @return The string containing the stats information.
     * @throws RDFStoreException
     * @throws RemoteException
     */
    public String getStats() throws RDFStoreException, RemoteException {
        return RDFStoreStatsManager.getInstance().getStats();
    }

}
