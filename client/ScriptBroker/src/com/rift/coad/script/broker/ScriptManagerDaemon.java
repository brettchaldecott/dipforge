/*
 * ScriptBroker: The script broker daemon.
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
 * ScriptManagerDaemon.java
 */

package com.rift.coad.script.broker;


// java imports
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface defines the methods that
 *
 * @author brett chaldecott
 */
public interface ScriptManagerDaemon extends Remote {

    /**
     * This method returns the list of scopes managed by the script manager.
     *
     * @return The list of scopes that are managed by this broker.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<String> listScopes() throws ScriptBrokerException, RemoteException;


    /**
     * This method returns the list of scripts that are identified by this scope.
     *
     * @param scope The scope to pull the list for.
     * @return The list of scripts
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptInfo> listScripts(String scope) throws ScriptBrokerException, RemoteException;


    /**
     * This method is responsible for adding a scope to the list of scopes for the script manager.
     *
     * @param scope The scope to add.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void addScope(String scope) throws ScriptBrokerException, RemoteException;


    /**
     * This method adds a script to the system.
     *
     * @param scope The scope the script is being added to.
     * @param fileName The name of the file.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void addScript(String scope, String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method returns the list of revisions for a given script.
     * @param scope The scope of the script.
     * @param fileName The name of the script.
     * @return The list of script information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptInfo> listScriptRevisions(String scope, String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method is responsible for retrieving the script identified by the scope and name. It will be
     * the currently active script within a system.
     *
     * @param scope The scope that the script is in.
     * @param fileName The file name of the script within the scope.
     * @return The string containing the script information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public String getScript(String scope, String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method returns the script identified by the script info
     *
     * @param scriptInfo The script info used to identify the object.
     * @return The string containing the script information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public String getScript(RDFScriptInfo scriptInfo) throws ScriptBrokerException, RemoteException;


    /**
     * This method is responsible for updating the script.
     *
     * @param scope The scope to update.
     * @param fileName The name of the file.
     * @param contents The contents to update.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void updateScript(String scope, String fileName, String contents) throws ScriptBrokerException, RemoteException;


    /**
     * This method updates the script information.
     *
     * @param scriptInfo The script information to update.
     * @param contents The contents of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void updateScript(RDFScriptInfo scriptInfo, String contents) throws ScriptBrokerException, RemoteException;


    /**
     * This method removes the script identified by the scope and file name.
     *
     * @param scope The scope of the script.
     * @param fileName The file name of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void removeScript(String scope, String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method is used to remove the script identified by the script info object.
     *
     * @param scriptInfo The info used to identify the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void removeScript(RDFScriptInfo scriptInfo) throws ScriptBrokerException, RemoteException;
}
