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
 * ScriptManagerDaemonImpl.java
 */


// package path
package com.rift.coad.script.broker;


// java imports
import java.rmi.RemoteException;
import java.util.List;

// log4j impots
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * This object is the implementation of the script manager.
 *
 * @author brett chaldecott
 */
public class ScriptManager implements ScriptManagerMBean {

    // logger
    private static Logger log = Logger.getLogger(ScriptManager.class);

    /**
     * The default constructor for the script manager.
     */
    public ScriptManager() {
    }

    
    /**
     * This method returns the version information for this object.
     *
     * @return This method returns the version information for this object.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the script manager.
     *
     * @return The string containing the name of this script.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns a description of this object.
     *
     * @return The string containing the description of this object.
     */
    public String getDescription() {
        return "The Script Manager";
    }


    /**
     * This method returns the list of scopes managed by this server.
     *
     * @return The list of scopes.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<String> listScopes() throws ScriptBrokerException {
        try {
            return ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class, "script/ManagementDaemon")).listScopes();
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to list the types : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to list the types : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of scripts.
     *
     * @param scope The scope that the scripts will be tied to.
     * @return The list of scripts.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptInfo> listScripts(String scope) throws ScriptBrokerException {
        try {
            return ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class, "script/ManagementDaemon")).listScripts(scope);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to list the scripts : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to list the scripts : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of revisions.
     *
     * @param scope The scope that the objec resides in.
     * @param fileName The name of the file.
     * @return The list of scripts.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<RDFScriptInfo> listScriptRevisions(String scope, String fileName) throws ScriptBrokerException, RemoteException {
        try {
            return ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class, "script/ManagementDaemon")).listScriptRevisions(scope,fileName);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to list the revisions : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to list the revisions : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method adds the script information.
     *
     * @param scope The scope the script will be added to.
     * @param fileName The name of the script to add.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void addScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon")).addScript(scope,fileName);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to add the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method retrieves the script from the script broker.
     *
     * @param scope The string containing the scope information.
     * @param fileName The name of the file.
     * @return
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public String getScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            return ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon")).getScript(scope,fileName);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to get the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to get the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the script information.
     * @param scope The string containing the scope the script belongs to.
     * @param fileName The name of the file within the scope.
     * @param contents The contents of the file
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     * @throws java.rmi.RemoteException
     */
    public void updateScript(String scope, String fileName, String contents) throws ScriptBrokerException, RemoteException {
        try {
            ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon")).updateScript(scope,fileName,contents);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to upate the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the specified script.
     *
     * @param scope The scope the script is attached to.
     * @param fileName The name of the file within the scope to remove.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void removeScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            ((ScriptManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon")).removeScript(scope,fileName);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to remove the script : " + ex.getMessage(),ex);
        }
    }


}
