/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * FileManagerImpl.java
 */


// package path
package com.rift.coad.script.server.files;

// java imports
import java.util.List;
import java.io.File;

// gwt import
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import com.rift.coad.daemon.jython.JythonManagementMBean;
import com.rift.coad.groovy.GroovyManagerMBean;
import com.rift.coad.rdf.objmapping.util.RDFCopy;
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.script.broker.ScriptManagerDaemon;
import com.rift.coad.script.client.files.FileManager;
import com.rift.coad.script.client.files.FileManagerException;
import com.rift.coad.script.client.files.FileSuffixLookup;
import com.rift.coad.util.connection.ConnectionManager;
import java.util.ArrayList;

/**
 *
 * @author brett
 */
public class FileManagerImpl extends RemoteServiceServlet implements
        FileManager {

    // private member variables
    private static Logger log = Logger.getLogger(FileManagerImpl.class);

    /**
     * The default constructor for the file manager.
     */
    public FileManagerImpl() {
    }
    

    /**
     * This method returns the list of scopes
     *
     * @return The list of scopes.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public List<String> listScopes() throws FileManagerException {
        try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            List<String> result = daemon.listScopes();
            log.info("Scopes : " + result.toString());
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the names spaces : " + ex.getMessage(),ex);
            throw new FileManagerException("Failed to list the names spaces : " +
                    ex.getMessage());
        }
    }


    /**
     * This method returns list of files.
     *
     * @param scope The scope to perform the search on.
     * @return The list of files.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public List<com.rift.coad.script.broker.client.rdf.RDFScriptInfo> listFiles(String scope) throws FileManagerException {
        try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            List<com.rift.coad.script.broker.rdf.RDFScriptInfo> initialResult = daemon.listScripts(scope);
            log.info("Initial result : " + initialResult.toString());
            com.rift.coad.script.broker.client.rdf.RDFScriptInfo[] resultArray =
                    (com.rift.coad.script.broker.client.rdf.RDFScriptInfo[])RDFCopy.copyToClientArray(
                    initialResult.toArray(new com.rift.coad.script.broker.rdf.RDFScriptInfo[0]));
            List<com.rift.coad.script.broker.client.rdf.RDFScriptInfo> result =
                    new ArrayList<com.rift.coad.script.broker.client.rdf.RDFScriptInfo>();
            for (com.rift.coad.script.broker.client.rdf.RDFScriptInfo entry : resultArray) {
                result.add(entry);
            }
            log.info("Result : " + result.toString());
            return result;
        } catch (Exception ex) {
            log.error("Failed to list the files : " + ex.getMessage(),ex);
            throw new FileManagerException("Failed to list the files : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to create the file.
     *
     * @param type The type of file to create.
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public void createFile(String type, String scope, String name)
            throws FileManagerException {
       try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            if (type.equals(com.rift.coad.script.client.files.Constants.FILE_TYPES[0])) {
                daemon.addScope(scope);
            } else {
                daemon.addScript(scope, name + "." + FileSuffixLookup.getSuffix(type));
            }
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new FileManagerException("Failed to create the file : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to delete a file.
     *
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public void deleteFile(String scope, String name)
            throws FileManagerException {
       try {
            ScriptManagerDaemon daemon = (ScriptManagerDaemon)
                    ConnectionManager.getInstance().getConnection(ScriptManagerDaemon.class,
                    "script/ManagementDaemon");
            daemon.removeScript(scope, name);
       } catch (Exception ex) {
           log.error("Failed to delete the file : " + ex.getMessage(),ex);
            throw new FileManagerException("Failed to delete the file : " +
                    ex.getMessage());
       }
    }
    
    
    /**
     * This method executes the file.
     * 
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @return The return path.
     * @throws com.rift.coad.script.client.files.FileManagerException
     */
    public String executeFile(String scope, String name) throws FileManagerException {
        String suffix = FileSuffixLookup.getSuffixForName(name);
        String path = scope.replaceAll("[.]", "/") + File.separator + name;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[0])) {
            return executeGroovy(path);
        } else if (suffix.equals(com.rift.coad.script.client.files.Constants.FILE_SUFFIXES[1])) {
            return executePython(path);
        }
        log.error("Request to execute on unsupported file type [" + scope + "][" + name + "]");
        throw new FileManagerException
                ("Request to execute on unsupported file type [" + scope + "][" + name + "]");
    }


    /**
     * This method executes the the groovy script.
     *
     * @param path The path to the script to execute.
     * @return The results of the execution.
     */
    private String executeGroovy(String path) {
        try {
            GroovyManagerMBean mbean = (GroovyManagerMBean)
                    ConnectionManager.getInstance().getConnection(GroovyManagerMBean.class,
                    "groovy/Management");
            return mbean.execute(path);
        } catch (Exception ex) {
            log.info("Failed to execute the groovy script [" + path + "] : " + ex.getMessage());
            return ex.getMessage();
        }
    }


    /**
     * This method is called to execute the python call.
     *
     * @param path path to the file to execute
     * @return The results of the script being executed.
     */
    private String executePython(String path) {
        try {
            JythonManagementMBean mbean = (JythonManagementMBean)
                    ConnectionManager.getInstance().getConnection(JythonManagementMBean.class,
                    "jython/Management");
            return mbean.runScript(path);
        } catch (Exception ex) {
            log.info("Failed to execute the python script [" + path + "] : " + ex.getMessage());
            return ex.getMessage();
        }
    }
}
