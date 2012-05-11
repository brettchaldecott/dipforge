/*
 * RDFStoreClient: The rdf store daemon client.
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
 * RDFStoreDaemon.java
 */

package com.rift.dipforge.rdf.store;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The rdf store
 *
 * @author brett chaldecott
 */
public interface RDFStoreDaemonAsync extends Remote {

    /**
     * The store actions
     */
    enum StoreAction {
        PERSIST,
        REMOVE
    };


    /**
     * The persist method used to update the store.
     *
     * @param action The action to perform on the store.
     * @param rdfXML The xml store.
     * @throws RDFStoreException
     * @throws RemoteException
     */
    public void persist(String action, String rdfXML) throws RDFStoreException,
            RemoteException;
}
