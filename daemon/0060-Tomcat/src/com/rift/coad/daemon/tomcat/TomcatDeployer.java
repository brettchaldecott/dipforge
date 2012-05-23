/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2007  Rift IT Contracting
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
 * Tomcat.java
 */

// package path
package com.rift.coad.daemon.tomcat;

// java imports
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import com.rift.coad.daemon.tomcat.TomcatWrapper;
import java.util.Properties;
import java.util.Enumeration;
        
// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.thread.BasicThread;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.UserSession;
import com.rift.coad.lib.security.user.UserStoreManagerAccessor;



/**
 * This class is responsible for deploying the tomcat daemon instance.
 *
 * @author Brett Chaldecott
 */
public class TomcatDeployer extends BasicThread {
    
    // class constants
    private final static String WEB_USERNAME = "web_default_username";
    private final static String WEB_DEFAULT_USERNAME = "guest";
    
    
    /**
     * This object contains  the deployment information for a given entry.
     */
    public class DeployEntry {
        // private member variables
        private String path = null;
        private File file = null;
        private long lastModified = 0;
        
        /**
         * The constructor of the deploy entry.
         *
         * @param file The reference to the file object.
         */
        public DeployEntry (File file) {
            path = file.getPath();
            this.file = file;
            lastModified = file.lastModified();
        }
        
        
        /**
         * The reference to the path entries.
         *
         * @return The reference to the path value.
         */
        public String getPath() {
            return path;
        }
        
        
        /**
         * This method returns a reference to h
         */
        public File getFile() {
            return file;
        }
        
        
        /**
         * This method returns the last modified date.
         *
         * @return The long containing the last modified date.
         */
        public long getLastModified() {
            return lastModified;
        }
        
        /**
         * This method sets the last modified date.
         *
         * @param lastModified The long containing the last modified date.
         */
        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }
    }
    
    // the logger reference
    protected Logger log =
            Logger.getLogger(TomcatDeployer.class.getName());
    
    // private static variables
    private static UserSession session;
    
    
    // private member variables
    private ThreadStateMonitor state = null;
    private TomcatWrapper tomcatWrapper = null;
    private Map deployedEntries = new HashMap();
    private File deploymentDir = null;
    private String defaultUserName;
    
    /**
     * Creates a new instance of TomcatDeployer
     */
    public TomcatDeployer() throws Exception {
        try {
            Configuration configuration = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            state = new ThreadStateMonitor(configuration.getLong(
                    "DeployCheckInterval",1000));
            Configuration deployConfig = ConfigurationFactory.getInstance().
                    getConfig(com.rift.coad.lib.deployment.
                    DeploymentManager.class);
            deploymentDir = new File(deployConfig.getString("directory"));
            tomcatWrapper = new TomcatWrapper();
            
            // load the deployed entries
            defaultUserName = configuration.getString(WEB_USERNAME,WEB_DEFAULT_USERNAME);
            
        } catch (Exception ex) {
            log.error("Failed to initialize the configuration because : " + 
                    ex.getMessage(),ex);
            throw new TomcatException(
                    "Failed to initialize the configuration because : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process() throws Exception {
        
        // retrieve the user permissions
        try {
            session = UserStoreManagerAccessor.getInstance().
                    getUserStoreManager().getUserInfo(defaultUserName);
        } catch (Exception ex) {
            log.error("####### Failed to get web default username : " + ex.getMessage(),ex);
        }
        
        // load the the deployed entries
        log.info("Load the deployment information");
        Properties deploymentInfo = tomcatWrapper.getDeploymentMapping();
        for (Enumeration key = deploymentInfo.keys(); key.hasMoreElements();) {
            String path = (String)key.nextElement();
            String context = deploymentInfo.getProperty(path);
            File war = new File(path);
            if (war.exists()) {
                File contextFile = this.tomcatWrapper.getContextFile(context);
                if (contextFile.exists()) {
                    DeployEntry entry = new DeployEntry(war);
                    entry.setLastModified(contextFile.lastModified());
                    deployedEntries.put(war.getPath(), entry);
                }
            } else {
                this.tomcatWrapper.unloadWar(path);
            }
        }
        
        log.info("The processor is running");
        while (!state.isTerminated()) {
            // retrieve a list of files
            File[] files = FileUtil.filter(deploymentDir.listFiles(),".war");
            Arrays.sort(files);
            
            // loop through the existing entry
            Vector oldEntries = new Vector();
            for (Iterator iter = deployedEntries.keySet().iterator(); 
                    iter.hasNext();) {
                DeployEntry entry = (DeployEntry)deployedEntries.get(
                        iter.next());
                boolean found = false;
                for (int index = 0; index < files.length; index++) {
                    if (files[index].getPath().equals(entry.getPath())) {
                        found = true;
                        if (files[index].lastModified() > 
                                entry.getLastModified()) {
                            tomcatWrapper.reloadWar(files[index]);
                            entry.setLastModified(files[index].lastModified());
                        }
                        break;
                    }
                }
                if (!found) {
                    oldEntries.add(entry.getPath());
                    tomcatWrapper.unloadWar(entry.getFile());
                }
            }
            for (Iterator iter = oldEntries.iterator(); iter.hasNext();) {
                deployedEntries.remove(iter.next());
            }
            
            
            // attempt to load
            for (int index = 0; index < files.length; index++) {
                if (!deployedEntries.containsKey(files[index].getPath())) {
                    deployedEntries.put(files[index].getPath(),
                            new DeployEntry(files[index]));
                    tomcatWrapper.loadWar(files[index]);
                }
            }
            
            // check the state
            state.monitor();
        }
        
        log.info("Stopping the engine");
        try {
            tomcatWrapper.stop();
        } catch (Exception ex) {
            log.error("Failed to stop the tomcat instance : " + ex.getMessage(),
                    ex);
        }
        
    }
    
    
    /**
     * This method is called to soft terminate the processing thread.
     */
    public void terminate() {
        state.terminate(true);
        try{
            this.join();
        } catch (Exception ex) {
            log.error("Failed to wait for tomcat :" + ex.getMessage(),ex);
        }
        log.info("Finished waiting for tomcat to shut down.");
    }

    
    /**
     * This method returns the user session information.
     */
    public static UserSession getSession() {
        return session;
    }

    
    
    
}
