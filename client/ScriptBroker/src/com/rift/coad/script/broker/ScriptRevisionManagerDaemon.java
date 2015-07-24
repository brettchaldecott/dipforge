/*
 * ScriptBroker: The script broker client libraries
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
 * ScriptRevisionManagerDaemon.java
 */


// the package path
package com.rift.coad.script.broker;

// java imports
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import com.rift.coad.script.broker.rdf.RDFScriptChangeInfo;
import com.rift.coad.script.broker.rdf.RDFScriptChangeSet;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


import com.rift.coad.script.broker.rdf.RDFScriptInfo;


/**
 * This interface discribes the methods to manage the distribution
 *
 * @author brett chaldecott
 */
public interface ScriptRevisionManagerDaemon extends Remote {

    /**
     * This method returns the revision information.
     *
     * @param script The script to get the revision information for.
     * @return The reference to the RDF revision information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws com.rift.coad.script.broker.UnknownEntryException
     * @throws java.rmi.RemoteException
     */
    public RDFRevisionInfo getRevision(RDFScriptInfo script) throws ScriptBrokerException,
            UnknownEntryException, RemoteException;


    /**
     * This method sets the revision information associated with specified script.
     * @param script The script to set the revision information for.
     * @param revision The revision.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void setRevision(RDFScriptInfo script, RDFRevisionInfo revision) throws ScriptBrokerException, RemoteException;


    /**
     * This method lists the script revisions for the scope
     * @param path The path under which the revision information has to be retrieved.
     * @param fileName The file name.
     * @return The list of rdf script information objects for this path and file name.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptInfo> listScriptRevisions(String path, String fileName) throws
            ScriptBrokerException, RemoteException;

    /**
     * This method returns a list of all the changes to the script store.
     * @return The list of changes
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptChangeInfo> listLocalChanges() throws ScriptBrokerException, RemoteException;


    /**
     * This method adds the changes.
     *
     * @param action The type of change.
     * @param change The change.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public RDFScriptChangeInfo addChange(String action, RDFScriptInfo change) throws ScriptBrokerException, RemoteException;


    /**
     * This method commits the changes
     * 
     * @param changes The changes that have to be committed
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public RDFScriptChangeSet commitChanges(List<RDFScriptChangeInfo> changes, String message)
            throws ScriptBrokerException, RemoteException;


    /**
     * This method lists the change sets in desending order
     *
     * @return The list of change sets.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptChangeSet> listChangeSets()
            throws ScriptBrokerException, RemoteException;
}
