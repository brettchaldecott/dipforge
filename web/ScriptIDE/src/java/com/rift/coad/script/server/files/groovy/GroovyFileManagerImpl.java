/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * GroovyFileManager.java
 */


package com.rift.coad.script.server.files.groovy;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.script.broker.TemplateVariables;
import com.rift.coad.script.broker.client.rdf.RDFScriptInfo;
import com.rift.coad.script.client.files.groovy.GroovyFileManager;
import com.rift.coad.script.client.files.groovy.GroovyFileManagerException;
import com.rift.coad.script.client.files.groovy.GroovyFileSuffixLookup;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author brett
 */
public class GroovyFileManagerImpl extends RemoteServiceServlet implements
        GroovyFileManager {
    
    // class constants
    public static final String GROOVY_BASE = "groovy_base";
    public static final String GROOVY_TEMPLATE_DIR = "groovy_template_dir";
    public static final String GROOVY_TEMPLATE_PREFIX = "groovy_template_file_prefix";
    public static final String GROOVY_TEMPLATE_PREFIX_DEFAULT = "groovy_template";

    // class singletons
    private static Logger log = Logger.getLogger(GroovyFileManagerImpl.class);

    // private member variables
    private File base = null;
    private Map<String,File> scopes = new HashMap<String,File>();
    private String templateDir;
    private String templatePrefix;


    public GroovyFileManagerImpl() throws GroovyFileManagerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    GroovyFileManagerImpl.class);
            base = new File(config.getString(GROOVY_BASE));
            loadScopes(base, "");
            templateDir = config.getString(GROOVY_TEMPLATE_DIR);
            templatePrefix = config.getString(GROOVY_TEMPLATE_PREFIX,
                    GROOVY_TEMPLATE_PREFIX_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to instantiate the groovy manager : " +
                    ex.getMessage(),ex);
            throw new GroovyFileManagerException
                    ("Failed to instantiate the groovy manager : " +
                    ex.getMessage());
        }
    }



    /**
     * This method returns the list of scopes
     *
     * @return The list of scopes.
     * @throws com.rift.coad.script.client.files.php.PHPFileManagerException
     */
    public List<String> listScopes() throws GroovyFileManagerException {
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
     * @throws com.rift.coad.script.client.files.groovy.GroovyFileManagerException
     */
    public List<RDFScriptInfo> listFiles(String scope) throws GroovyFileManagerException {
        if (!this.scopes.containsKey(scope)) {
            throw new GroovyFileManagerException("The scope [" + scope + "] does not exist");
        }
        File dir = this.scopes.get(scope);
        File[] files = dir.listFiles();
        List<RDFScriptInfo> result = new ArrayList<RDFScriptInfo>();
        for (File file : files) {
            if (file.isFile() && !file.getName().equals("..") && !file.getName().equals(".")) {
                result.add(new RDFScriptInfo(scope, file.getName(), new Date(file.lastModified())));
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
    public void createFile(String type, String scope, String name) throws GroovyFileManagerException {
        try {
            if (type.equals(com.rift.coad.script.client.files.groovy.Constants.FILE_TYPES[0])) {
                addScope(scope);
            } else {
                addScript(scope, name + "." + GroovyFileSuffixLookup.getSuffix(type));
            }
        } catch (Exception ex) {
            log.error("Failed to create the file : " + ex.getMessage(),ex);
            throw new GroovyFileManagerException("Failed to create the file : " +
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
    public void deleteFile(String scope, String fileName) throws GroovyFileManagerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                log.error("The scope [" + scope + "] does not exist");
                throw new GroovyFileManagerException
                        ("The scope [" + scope + "] does not exist");
            } else {
                path = this.scopes.get(scope);
            }
            File target = new File(path,fileName);
            target.delete();
        } catch (GroovyFileManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the script : " + ex.getMessage(),ex);
            throw new GroovyFileManagerException
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
                        scope = currentScope + "." + scope;
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
    private void addScope(String scope) throws GroovyFileManagerException {
        try{
            if (this.scopes.containsKey(scope)) {
                log.error("The scope already exists : " + scope);
                throw new GroovyFileManagerException
                        ("The scope already exists : " + scope);
            }
            File path = new File(this.base,scope.replaceAll("[.]", "/"));
            if (!path.exists() && !path.mkdirs()) {
                log.error("Failed to add the scope : " + scope);
                throw new GroovyFileManagerException
                        ("Failed to add the scope : " + scope);
            }
            scopes.put(scope, path);
        } catch (GroovyFileManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the scope : " + ex.getMessage(),ex);
            throw new GroovyFileManagerException
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
    private void addScript(String scope, String fileName) throws GroovyFileManagerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                addScope(scope);
                path = this.scopes.get(scope);
            } else {
                path = this.scopes.get(scope);
            }

            File[] files = path.listFiles(new GroovyFileFilter(fileName));
            if (files.length != 0) {
                log.error("Attempting to add duplicate file : " + fileName);
                throw new GroovyFileManagerException
                        ("Attempting to add duplicate file : " + fileName);
            }
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);


            File target = new File(path,fileName);
            String contents = getTemplate(scope,fileName,suffix);
            java.io.FileOutputStream out = new java.io.FileOutputStream(target);
            out.write(contents.getBytes());
            out.close();
        } catch (GroovyFileManagerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a script to the system : " + ex.getMessage(),ex);
            throw new GroovyFileManagerException
                    ("Failed to add a script to the system : " + ex.getMessage(),ex);
        }

    }


    /**
     * This method returns the contents of the specified template file.
     *
     * @param suffix The string containing the suffix name for the template file.
     * @return The string containing the template file information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    private String getTemplate(String scope, String fileName, String suffix) throws GroovyFileManagerException {
        try {
            GroovyTemplateHelper template = new GroovyTemplateHelper(new File(this.templateDir,
                    templatePrefix + "." + suffix).getPath());
            Map<String,String> values = new HashMap<String,String>();

            // setup the variables
            values.put(TemplateVariables.AUTHOR, SessionManager.getInstance().
                    getSession().getUser().getName());
            values.put(TemplateVariables.DATE, new Date().toString());
            values.put(TemplateVariables.FILE_NAME,fileName);
            values.put(TemplateVariables.SCOPE,scope);
            values.put(TemplateVariables.PATH,scope.replaceAll("[.]","/") +
                    File.separator + fileName);
            values.put(TemplateVariables.NAME,fileName.substring(0,fileName.lastIndexOf(".")));

            template.setParameters(values);
            return template.parse();
        } catch (Exception ex) {
            log.error("Failed to read in the template : " + ex.getMessage(),ex);
            throw new GroovyFileManagerException
                    ("Failed to read in the template : " + ex.getMessage(),ex);
        }
    }
}
