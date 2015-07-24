/*
 * OfficeSuite: The implementation of the office product suite.
 * Copyright (C) 2010  2015 Burntjam
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
 * DocumentManagerImpl.java
 */


// package path
package com.rift.coad.office.server.documents;

// java import
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// gwt import
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j import
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.ThreadsPermissionContainerAccessor;
import com.rift.coad.office.client.documents.DocumentFileSuffixLookup;
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.office.client.documents.DocumentManager;
import com.rift.coad.office.client.documents.DocumentManagerException;
import com.rift.coad.office.server.events.LogEvent;


/**
 * The implementation of the document manager.
 *
 * @author brett chaldecott
 */
public class DocumentManagerImpl extends RemoteServiceServlet implements
        DocumentManager {
    
    // class constants
    public static final String INSTALLATION_BASE = "installation_base";
    public static final String DOCUMENT_BASE = "document_base";
    
    
    // class singletons
    private static Logger log = Logger.getLogger(DocumentManagerImpl.class);

    // private member variables
    private String installationBase;
    private String documentBase;
    private File base = null;
    private Map<String,File> scopes = new HashMap<String,File>();
    private String templateDir;
    private String templatePrefix;


    /**
     * The document manager.
     *
     *
     * @throws com.rift.coad.office.client.documents.DocumentManagerException
     */
    public DocumentManagerImpl() throws DocumentManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    DocumentManagerImpl.class);
            installationBase = config.getString(INSTALLATION_BASE);
            documentBase = config.getString(DOCUMENT_BASE);
            base = new File(installationBase,documentBase);
            loadScopes(base, "");
        } catch (Exception ex) {
            log.error("Failed to instantiate the document manager : " +
                    ex.getMessage(),ex);
            throw new DocumentManagerException
                    ("Failed to instantiate the document manager : " +
                    ex.getMessage());
        }
    }


    /**
     * This method returns the base uri for the documents.
     *
     * @return The string containing the base uri for the documents.
     * @throws com.rift.coad.office.client.documents.DocumentManagerException
     */
    public String getHttpBaseUri() throws DocumentManagerException {
        return FileManagerConstants.BASE_FILE_URL + "/" + documentBase;
    }

    
    /**
     * This method returns the list of scopes
     *
     * @return The list of scopes.
     * @throws com.rift.coad.office.client.documents.DocumentManagerException
     */
    public List<String> listScopes() throws DocumentManagerException {
        List<String> scopes = new ArrayList<String>();
        for (Iterator<String> iter = this.scopes.keySet().iterator(); iter.hasNext();) {
            scopes.add(iter.next());
        }
        return scopes;
    }


    /**
     * This method lists the files
     * @param scope
     * @return
     * @throws com.rift.coad.office.client.documents.DocumentManagerException
     */
    public List<String> listFiles(String scope) throws DocumentManagerException {
        if (!this.scopes.containsKey(scope)) {
            throw new DocumentManagerException("The scope [" + scope + "] does not exist");
        }
        File dir = this.scopes.get(scope);
        File[] files = dir.listFiles();
        log.info("List the files for [" + dir.getPath() + "] contains number : " + files.length);
        List<String> result = new ArrayList<String>();
        for (File file : files) {
            if (file.isFile() && !file.getName().equals("..") && !file.getName().equals(".")) {
                result.add(file.getName());
            }
        }
        return result;
    }


    /**
     * This method is called to create a file.
     *
     * @param type The type of file
     * @param scope The scope of the file.
     * @param name The name of the file.
     * @throws com.rift.coad.script.client.files.php.PHPFileManagerException
     */
    public void createFile(String type, String scope, String name) throws DocumentManagerException {
        try {
            if (type.equals(com.rift.coad.office.client.documents.Constants.FILE_TYPES[0])) {
                addScope(scope);
                
            } else {
                addFile(scope, name + "." + DocumentFileSuffixLookup.getSuffix(type));
            }
            
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new DocumentManagerException("Failed to create the file : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to delete the file.
     *
     * @param scope The scope to delete file from.
     * @param fileName The name of the file to delete.
     * @throws com.rift.coad.script.client.files.php.PHPFileManagerException
     */
    public void deleteFile(String scope, String fileName) throws DocumentManagerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                log.error("The scope [" + scope + "] does not exist");
                throw new DocumentManagerException
                        ("The scope [" + scope + "] does not exist");
            } else {
                path = this.scopes.get(scope);
            }
            File target = new File(path,fileName);
            target.delete();
            LogEvent.getLog().logEvent("Remove File", "/" + documentBase + "/" + target.getPath(),
                        String.format("[Remove] file [%s/%s] from the document share.", scope, fileName),
                        FileManagerConstants.BASE_DIRECTORY_URL + "/" + documentBase + "/" + 
                        scope + "/" + fileName);
        } catch (DocumentManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the script : " + ex.getMessage(),ex);
            throw new DocumentManagerException
                    ("Failed to remove the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is called to load the scope information.
     *
     * @param directory The current directory being accessed
     * @param currentScope The current scope of the object.
     */
    private void loadScopes(File directory, String currentScope) {
        boolean add = false;
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && !add) {
                scopes.put(currentScope, directory);
                continue;
            } else if (file.isDirectory() && !file.getName().equals("WEB-INF") &&
                    !file.getName().equals("META-INF")) {
                if (!file.getName().equals(".") || !file.getName().equals("..")) {
                    String scope = file.getName();
                    if (currentScope.length() > 0) {
                        scope = currentScope + "/" + scope;
                    }
                    loadScopes(file,scope);
                }
            }
        }
    }


    /**
     * This method is called to add a new scope.
     *
     * @param scope The scope to add.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    private void addScope(String scope) throws DocumentManagerException {
        try{
            if (this.scopes.containsKey(scope)) {
                log.error("The scope already exists : " + scope);
                throw new DocumentManagerException
                        ("The scope already exists : " + scope);
            }
            File path = new File(this.base,scope);
            if (!path.exists() && !path.mkdirs()) {
                log.error("Failed to add the scope : " + scope);
                throw new DocumentManagerException
                        ("Failed to add the scope : " + scope);
            }
            LogEvent.getLog().logEvent("Add", "/" + documentBase + "/" + scope,
                        String.format("[Added] directory [%s] to document share.", scope),
                        FileManagerConstants.BASE_DIRECTORY_URL + "/" + documentBase + "/" + scope);
            scopes.put(scope, path);

        } catch (DocumentManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the scope : " + ex.getMessage(),ex);
            throw new DocumentManagerException
                        ("Failed to add the scope : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method adds a script to the store.
     *
     * @param scope The scope the script is within.
     * @param fileName The name of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    private void addFile(String scope, String fileName) throws DocumentManagerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                addScope(scope);
                path = this.scopes.get(scope);
            } else {
                path = this.scopes.get(scope);
            }

            File[] files = path.listFiles(new DocumentFileFilter(fileName));
            if (files.length != 0) {
                log.error("Attempting to add duplicate file : " + fileName);
                throw new DocumentManagerException
                        ("Attempting to add duplicate file : " + fileName);
            }
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);


            File target = new File(path,fileName);
            String contents = "";
            java.io.FileOutputStream out = new java.io.FileOutputStream(target);
            out.write(contents.getBytes());
            out.close();
            LogEvent.getLog().logEvent("Add", "/" + documentBase + "/" + scope + "/" + fileName,
                        String.format("[Added] file [%s/%s] to document share.", scope,fileName),
                        FileManagerConstants.BASE_FILE_URL + "/" + documentBase + "/" +
                        scope + "/" + fileName);
        } catch (DocumentManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a script to the system : " + ex.getMessage(),ex);
            throw new DocumentManagerException
                    ("Failed to add a script to the system : " + ex.getMessage(),ex);
        }

    }


    
}
