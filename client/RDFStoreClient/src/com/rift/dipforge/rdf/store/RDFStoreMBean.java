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

// package path
package com.rift.dipforge.rdf.store;

import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The rdf store mbean
 *
 * @author brett chaldecott
 */
public interface RDFStoreMBean extends Remote {

    /**
     * This method returns the name of the tomcat daemon.
     */
    @MethodInfo(description="This method returns the name of the tomcat daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the name.")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the tom cat daemon.
     */
    @MethodInfo(description="This method returns the description of the tom cat daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the description")
    public String getDescription() throws RemoteException;


    /**
     * This method returns the version of this daemon.
     */
    @MethodInfo(description="This method returns the version of this daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the version")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the stats information for the store.
     *
     * @return The string containing the stats information for this store.
     * @throws RDFStoreException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the version of this daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the version")
    public String getStats() throws RDFStoreException, RemoteException;
}
