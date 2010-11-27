/*
 * ScriptBroker: The script broker daemon.
 * Copyright (C) 2009  Rift IT Contracting
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
 * ScriptManagerMBean.java
 */

package com.rift.coad.script.broker;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// the result information
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import java.util.List;


/**
 * The definition of the script manager mbean
 *
 * @author brett chaldecott
 */
public interface ScriptManagerMBean extends Remote {

    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;


    /**
     * This method returns the list of scopes managed by the script manager.
     *
     * @return The list of scopes that are managed by this broker.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method lists the scopes.")
    @Version(number="1.0")
    @Result(description="The list of scopes managed by this object.")
    public List<String> listScopes() throws ScriptBrokerException, RemoteException;


    /**
     * This method returns the list of scripts that are identified by this scope.
     *
     * @param scope The scope to pull the list for.
     * @return The list of scripts
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="The list of scripts for the specified scope.")
    @Version(number="1.0")
    @Result(description="The list of scripts information objects.")
    public List<RDFScriptInfo> listScripts(
            @ParamInfo(name="scope",
            description="The scope to retrieve the list from.")String scope) throws ScriptBrokerException, RemoteException;


    /**
     * This method adds a script to the system.
     *
     * @param scope The scope the script is being added to.
     * @param fileName The name of the file.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method adds a new script.")
    @Version(number="1.0")
    public void addScript(
            @ParamInfo(name="scope",
            description="The scope to add the new script to.")String scope,
            @ParamInfo(name="fileName",
            description="The name of the file within the scope.")String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method returns the list of revisions for a given script.
     * @param scope The scope of the script.
     * @param fileName The name of the script.
     * @return The list of script information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="List the script revisions.")
    @Version(number="1.0")
    @Result(description="The list of revisions on a file.")
    public List<RDFScriptInfo> listScriptRevisions(
            @ParamInfo(name="scope",
            description="The scope the file resides in.")String scope,
            @ParamInfo(name="fileName",
            description="The name of file within the scope.")String fileName) throws ScriptBrokerException, RemoteException;





    /**
     * This method is responsible for retrieving the script identified by the scope and name.
     * @param scope The scope that the script is in.
     * @param fileName The file name of the script within the scope.
     * @return The string containing the script information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method returns the script information.")
    @Version(number="1.0")
    public String getScript(
            @ParamInfo(name="scope",
            description="The scope to add the new script to.")String scope,
            @ParamInfo(name="fileName",
            description="The name of the file.")String fileName) throws ScriptBrokerException, RemoteException;


    /**
     * This method is responsible for updating the script.
     *
     * @param scope The scope to update.
     * @param fileName The name of the file.
     * @param contents The contents to update.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method updates the script information.")
    @Version(number="1.0")
    public void updateScript(
            @ParamInfo(name="scope",
            description="The scope to add the new script to.")String scope,
            @ParamInfo(name="fileName",
            description="The name of the file.")String fileName,
            @ParamInfo(name="contents",
            description="The contents of the file.")String contents)
            throws ScriptBrokerException, RemoteException;


    /**
     * This method removes the script identified by the scope and file name.
     *
     * @param scope The scope of the script.
     * @param fileName The file name of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method removes the script information.")
    @Version(number="1.0")
    public void removeScript(
            @ParamInfo(name="scope",
            description="The scope the script is attached to.")String scope,
            @ParamInfo(name="fileName",
            description="The name of the file.")String fileName) throws ScriptBrokerException, RemoteException;

}
