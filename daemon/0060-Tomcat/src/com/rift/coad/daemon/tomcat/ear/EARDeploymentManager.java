/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2011  2015 Burntjam
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
 * EARDeploymentManager.java
 */

package com.rift.coad.daemon.tomcat.ear;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 * This object manages the ear deployment process.
 *
 * @author brett chaldecott
 */
public class EARDeploymentManager {
    
    
    // class singleton
    private static EARDeploymentManager singleton = null;
    private static Logger log = Logger.getLogger(EARDeploymentManager.class);
    
    // private member variables
    private String earPropertiesFile;
    private File deploymentDir = null;
    private File tempDir = null;
    private File configDir = null;
    private Map<File,EARDeployEntry> entries = new ConcurrentHashMap<File,EARDeployEntry>();


    /**
     * The default constructor for the ear deployment manager.
     *
     * @throws EARException
     */
    private EARDeploymentManager() throws EARException {
        try {
            Configuration deployConfig = ConfigurationFactory.getInstance().
                    getConfig(com.rift.coad.lib.deployment.
                    DeploymentManager.class);
            deploymentDir = new File(deployConfig.getString("directory"));

            Configuration deployLoaderConfig = ConfigurationFactory.getInstance().
                    getConfig(com.rift.coad.lib.deployment.
                    DeploymentLoader.class);
            tempDir = new File(deployLoaderConfig.getString("temp_dir"));

            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    EARDeploymentManager.class);
            earPropertiesFile = config.getString(EARConstants.EAR_DEPLOYMENT_PROPERTIES);
            configDir = new File(
                    config.getString(EARConstants.EAR_CONFIG_DIR));
            entries = deserializeEntriesMap(new File(earPropertiesFile));
        } catch (Exception ex) {
            log.error("Failed to instanciate the ear deployment manager :" +
                    ex.getMessage(),ex);
            throw new EARException("Failed to instanciate the ear deployment manager :" +
                    ex.getMessage(),ex);
        }
    }

    
    /**
     * This method returns the reference to the instance.
     *
     * @return The reference to the ear deployment instance
     */
    public static synchronized EARDeploymentManager getInstance()
            throws EARException {
        if (singleton == null) {
            singleton = new EARDeploymentManager();
        }
        return singleton;
    }


    /**
     * This method returns true if the method contains the specified entry.
     *
     * @param entryPath The entry path.
     * @return True if found, FALSE if not.
     * @throws EARException
     */
    public boolean contains(File entryPath) throws EARException {
        return entries.containsKey(entryPath);
    }

    
    /**
     * This method returns the list of deployed ears.
     * @return
     * @throws EARException
     */
    public List<EARDeployEntry> listEntries() throws EARException {
        List<EARDeployEntry> result = new ArrayList<EARDeployEntry>();
        result.addAll(this.entries.values());
        return result;
    }
    
    /**
     * This method returns an entry from the deploy map identified by the file path.
     * 
     * @param entryPath The identify of the file to retrieve
     * @return The reference to the deploy entry.
     * @throws EARException
     */
    public EARDeployEntry getEntry(File entryPath) throws EARException {
        return entries.get(entryPath);
    }


    /**
     * This method adds an entry to be identified by the file path.
     *
     * @param entryPath The path of the file.
     * @return The reference to the newly created entry.
     * @throws EARException
     */
    public EARDeployEntry deployEntry(File entryPath) throws EARException {
        try {
            EARDeployEntry result = new EARDeployEntry(entryPath, deploymentDir,
                    tempDir, configDir);
            result.deploy();
            entries.put(result.getFile(), result);
            serializeEntriesMap(entries);
            return result;
        } catch (Exception ex) {
            log.error("Failed to add the entry because : " + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to add the entry because : " + ex.getMessage(),ex);
        }
    }
    
    /**
     * This method is called to undeploy an entry
     * @param entryPath The entry to undeploy.
     * 
     * @throws EARException The ear exception
     */
    public void undeployEntry(File entryPath) throws EARException {
        EARDeployEntry entry = entries.get(entryPath);
        if (entry == null) {
            return;
        }
        entry.unDeploy();
        entries.remove(entryPath);
        serializeEntriesMap(entries);
    }


    /**
     * This method returns the deserialized entries from the properties file.
     *
     * @param earProprtiesFile The ear properties file.
     * @return
     * @throws EARException
     */
    private Map<File,EARDeployEntry> deserializeEntriesMap(File earPropertiesFile)
            throws EARException {
        Map<File,EARDeployEntry> result = new ConcurrentHashMap<File,EARDeployEntry>();
        if (!earPropertiesFile.exists()) {
            return result;
        }
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(
                        earPropertiesFile);
            properties.load(in);
            in.close();
            for (Object key: properties.keySet()) {
                EARDeployEntry entry = new EARDeployEntry(
                        properties.getProperty(key.toString()));
                result.put(entry.getFile(), entry);
            }
            
        } catch (Exception ex) {
            log.error("Failed to deserialize the entries map : "  + 
                    ex.getMessage(),ex);
            throw new EARException
                    ("Failed to deserialize the entries map : "  + 
                    ex.getMessage(),ex);
        }
        return result;
    }


    /**
     * This method is called to serialize the entries map
     *
     * @param map The entries to serialize.
     * @throws EARException
     */
    private void serializeEntriesMap(Map<File,EARDeployEntry>  entries)
            throws EARException {
        try {
            Properties properties = new Properties();
            for (File file: entries.keySet()) {
                EARDeployEntry entry = entries.get(file);
                properties.put(file.getName(), entry.toString());
            }
            FileOutputStream out = new FileOutputStream(
                        earPropertiesFile);
            properties.store(out, "EAR Configuration file");
            out.close();
        } catch (Exception ex) {
            log.error("Failed to serialize the entries map : "  +
                    ex.getMessage(),ex);
            throw new EARException
                    ("Failed to serialize the entries map : "  +
                    ex.getMessage(),ex);
        }
    }
}
