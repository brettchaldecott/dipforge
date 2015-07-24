/*
 * ScriptBroker: The script broker client library.
 * Copyright (C) 2009  2015 Burntjam
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
 * ScriptDistributionDaemon.java
 */

package com.rift.coad.script.broker;

// java imports
import com.rift.coad.script.broker.rdf.RDFScriptChangeSet;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// rdf imports
import com.rift.coad.script.broker.rdf.RDFScriptInfo;

/**
 * This interface defines the distribution daemon interface.
 *
 * @author brett chaldecott
 */
public interface ScriptDistributionDaemon extends Remote {

    /**
     * This method returns a list of all the changes to the script store.
     *
     * @return The list of changes
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptChangeSet> listChangeSets() throws ScriptBrokerException, RemoteException;


    /**
     * This method distributes the local changes.
     *
     * @param changes The list of local changes.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void distributeLocalChanges(
            RDFScriptChangeSet changeSet) throws ScriptBrokerException, RemoteException;


    /**
     * This method is called to notify the script distribution daemon of the fact that
     * there are changes.
     *
     * @param hostname The host name the changes are attached to.
     * @param changes The list of changes to sync.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void acceptChanges(String hostname,
            List<RDFScriptInfo> changes) throws ScriptBrokerException, RemoteException;
}
