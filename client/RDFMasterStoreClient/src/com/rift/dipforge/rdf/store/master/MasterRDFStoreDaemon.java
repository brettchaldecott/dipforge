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

package com.rift.dipforge.rdf.store.master;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The rdf store
 *
 * @author brett chaldecott
 */
public interface MasterRDFStoreDaemon extends Remote {


    /**
     * The persist method used to update the store.
     *
     * @param action The action to perform on the store.
     * @param rdfXML The xml store.
     * @throws MasterRDFStoreException
     * @throws RemoteException
     */
    public void persist(String action, String rdfXML) throws MasterRDFStoreException,
            RemoteException;
}
