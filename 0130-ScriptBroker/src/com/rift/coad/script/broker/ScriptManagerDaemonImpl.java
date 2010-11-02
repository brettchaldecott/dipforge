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
 * ScriptManagerDaemonImpl.java
 */

// package path
package com.rift.coad.script.broker;

// java imports
import com.rift.coad.lib.bean.BeanRunnable;
import java.rmi.RemoteException;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

// log4j imports
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.script.broker.rdf.RDFScriptInfo;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentMonitor;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.lib.security.SessionManager;


/**
 * The implementation of the script manager.
 *
 * @author brett chaldecott
 */
public class ScriptManagerDaemonImpl implements ScriptManagerDaemon, BeanRunnable {

    // class constants
    private static final String SCRIPT_BASE = "script_base";
    private static final String SCRIPT_TEMPLATE_DIR = "template_dir";
    private static final String SCRIPT_TEMPLATE_PREFIX = "template_file_prefix";
    private static final String SCRIPT_TEMPLATE_PREFIX_DEFAULT = "script_template";

    // class singletons
    private static Logger log = Logger.getLogger(ScriptManagerDaemonImpl.class);
    
    // private member variables
    private File base = null;
    private Map<String,File> scopes = new HashMap<String,File>();
    private ThreadStateMonitor monitor = new ThreadStateMonitor();
    private String templateDir;
    private String templatePrefix;


    /**
     * The default constructor.
     */
    public ScriptManagerDaemonImpl() throws ScriptBrokerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    ScriptManagerDaemonImpl.class);
            base = new File(config.getString(SCRIPT_BASE));
            loadScopes(base, "");
            templateDir = config.getString(SCRIPT_TEMPLATE_DIR);
            templatePrefix = config.getString(SCRIPT_TEMPLATE_PREFIX,
                    SCRIPT_TEMPLATE_PREFIX_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to instantiate the script manager : " +
                    ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to instantiate the script manager : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of scopes
     *
     * @return This method returns the list of scopes.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<String> listScopes() throws ScriptBrokerException {
        List<String> result =  new ArrayList<String>();
        result.addAll(scopes.keySet());
        return result;
    }


    /**
     * This method returns a list of scripts for the scope
     *
     * @param scope The scope to search for the scripts in.
     * @return The list of script info
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptInfo> listScripts(String scope) throws ScriptBrokerException {
        if (!this.scopes.containsKey(scope)) {
            throw new ScriptBrokerException("The scope [" + scope + "] does not exist");
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
     * This method is called to add a new scope.
     *
     * @param scope The scope to add.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void addScope(String scope) throws ScriptBrokerException {
        try{
            if (this.scopes.containsKey(scope)) {
                log.error("The scope already exists : " + scope);
                throw new ScriptBrokerException
                        ("The scope already exists : " + scope);
            }
            File path = new File(this.base,scope.replaceAll("[.]", "/"));
            if (!path.exists() && !path.mkdirs()) {
                log.error("Failed to add the scope : " + scope);
                throw new ScriptBrokerException
                        ("Failed to add the scope : " + scope);
            }
            scopes.put(scope, path);
            addChange(com.rift.coad.script.broker.rdf.ScriptActions.ADD,
                    new RDFScriptInfo(scope, null, new Date(path.lastModified())));
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the scope : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
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
    public void addScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                addScope(scope);
                path = this.scopes.get(scope);
            } else {
                path = this.scopes.get(scope);
            }

            File[] files = path.listFiles(new com.rift.coad.script.broker.file.FileFilter(fileName));
            if (files.length != 0) {
                log.error("Attempting to add duplicate file : " + fileName);
                throw new ScriptBrokerException
                        ("Attempting to add duplicate file : " + fileName);
            }
            String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);


            File target = new File(path,fileName);
            String contents = getTemplate(scope,fileName,suffix);
            java.io.FileOutputStream out = new java.io.FileOutputStream(target);
            out.write(contents.getBytes());
            out.close();

            addChange(com.rift.coad.script.broker.rdf.ScriptActions.ADD,
                    new RDFScriptInfo(scope, fileName, new Date(path.lastModified())));
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a script to the system : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to add a script to the system : " + ex.getMessage(),ex);
        }

    }


    /**
     * This method returns a list of the script revisions for the file name and scope.
     * @param scope The scope.
     * @param fileName The file name.
     * @return The list of script info for the scope and filename.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public List<RDFScriptInfo> listScriptRevisions(String scope, String fileName) throws ScriptBrokerException {
        try {
            ScriptRevisionManagerDaemon revision =
                    ((ScriptRevisionManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptRevisionManagerDaemon.class,
                    "java:comp/env/bean/script/RevisionManagementDaemon"));
            return revision.listScriptRevisions(scope.replaceAll("[.]", "/"), fileName);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of revisions : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to retrieve a list of revisions : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the script information.
     *
     * @param scope The scope for the call.
     * @param fileName The file name.
     * @return The string containing the script contents.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public String getScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                log.error("The scope [" + scope + "] does not exist");
                throw new ScriptBrokerException
                        ("The scope [" + scope + "] does not exist");
            } else {
                path = this.scopes.get(scope);
            }
            File source = new File(path,fileName);
            java.io.FileInputStream in = new java.io.FileInputStream(source);
            byte[] buffer = new byte[(int)source.length()];
            in.read(buffer);
            in.close();
            return new String(buffer);
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to retrieve the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the script identified by the script information.
     * @param scriptInfo The information needed to identify a script.
     * @return The string containing the script details.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public String getScript(RDFScriptInfo scriptInfo) throws ScriptBrokerException {
        return getScript(scriptInfo.getScope(),scriptInfo.getFileName());
    }


    /**
     * This method updates ths script information.
     *
     * @param scope The string containing the script scope information.
     * @param fileName The name of the file.
     * @param contents The contents of the file.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void updateScript(String scope, String fileName, String contents) throws ScriptBrokerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                log.error("The scope [" + scope + "] does not exist");
                throw new ScriptBrokerException
                        ("The scope [" + scope + "] does not exist");
            } else {
                path = this.scopes.get(scope);
            }
            File target = new File(path,fileName);
            java.io.FileOutputStream out = new java.io.FileOutputStream(target);
            out.write(contents.getBytes());
            out.close();
            this.addChange(com.rift.coad.script.broker.rdf.ScriptActions.UPDATE,
                    new RDFScriptInfo(scope, fileName, new Date(path.lastModified())));
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to retrieve the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method updates the script information.
     *
     * @param scriptInfo The script information.
     * @param contents The contents of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void updateScript(RDFScriptInfo scriptInfo, String contents) throws ScriptBrokerException {
        updateScript(scriptInfo.getScope(), scriptInfo.getFileName(), contents);
    }


    /**
     * This method removes the script identified by the scope and file information.
     * @param scope The name of the script to remove.
     * @param fileName The file name of the script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void removeScript(String scope, String fileName) throws ScriptBrokerException {
        try {
            File path = null;
            if (!this.scopes.containsKey(scope)) {
                log.error("The scope [" + scope + "] does not exist");
                throw new ScriptBrokerException
                        ("The scope [" + scope + "] does not exist");
            } else {
                path = this.scopes.get(scope);
            }
            File target = new File(path,fileName);
            this.addChange(com.rift.coad.script.broker.rdf.ScriptActions.REMOVE,
                    new RDFScriptInfo(scope, fileName, new Date(target.lastModified())));
            target.delete();
        } catch (ScriptBrokerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the script : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to remove the script : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the script identified by the information.
     * @param scriptInfo The information need to identify and remove a script.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    public void removeScript(RDFScriptInfo scriptInfo) throws ScriptBrokerException {
        removeScript(scriptInfo.getScope(),scriptInfo.getFileName());
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
            } else if (file.isDirectory()) {
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
     * This method is called to process
     */
    public void process() {
        DeploymentMonitor.getInstance().waitUntilInitDeployComplete();
        while (!monitor.isTerminated()) {
            try {
                SemanticUtil.getInstance(ScriptManagerDaemonImpl.class);
                break;
            } catch (Exception ex) {
                log.info("Failed to instanciate the script manager : " + ex.getMessage(),ex);
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex1) {
                    // ignore
                }
            }
        }
        // wait for shut down
        while (!monitor.isTerminated()) {
            monitor.monitor();
        }
        // close down the semantic util
        try {
            SemanticUtil.closeInstance(ScriptManagerDaemonImpl.class);
        } catch (Exception ex) {
            log.error("Failed to shut down the script manager.");
        }
    }


    /**
     * The terminate method
     */
    public void terminate() {
        this.monitor.terminate(true);
    }


    /**
     * This method is responsible for adding the change to the script revision manager.
     *
     * @param action The action that is begin performed.
     * @param change The change.
     */
    private void addChange(String action, RDFScriptInfo change) throws ScriptBrokerException {
        try {
            ScriptRevisionManagerDaemon revision =
                    ((ScriptRevisionManagerDaemon)ConnectionManager.getInstance().
                    getConnection(ScriptRevisionManagerDaemon.class,
                    "java:comp/env/bean/script/RevisionManagementDaemon"));
            revision.addChange(action, change);
        } catch (Exception ex) {
            log.error("Failed to add the change : " + ex.getMessage(),ex);
            throw new ScriptBrokerException
                    ("Failed to add the change : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the contents of the specified template file.
     *
     * @param suffix The string containing the suffix name for the template file.
     * @return The string containing the template file information.
     * @throws com.rift.coad.script.broker.ScriptBrokerException
     */
    private String getTemplate(String scope, String fileName, String suffix) throws ScriptBrokerException {
        try {
            TemplateHelper template = new TemplateHelper(new File(this.templateDir,
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
            throw new ScriptBrokerException
                    ("Failed to read in the template : " + ex.getMessage(),ex);
        }
    }
}
