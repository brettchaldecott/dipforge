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
 * EARDeployEntry.java
 */

// package path
package com.rift.coad.daemon.tomcat.ear;

import com.rift.coad.daemon.tomcat.loader.ContextClassLoaderLookup;
import com.rift.coad.daemon.tomcat.war.TomcatXMLContext;
import com.rift.coad.daemon.tomcat.war.WarUtils;
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.common.JarUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * This object is responsible for managing the ear deployment.
 *
 * @author brett chaldecott
 */
public class EARDeployEntry {

    // class singleton
    private static Logger log = Logger.getLogger(EARDeployEntry.class);

    // private member variables
    private String path = null;
    private File file = null;
    private File deployDir = null;
    private File tempDir = null;
    private File earConfigDir;
    private long lastModified = 0;
    private File filesPath = null;
    private File contextFilePath = null;


    /**
     * The constructor of the deploy entry.
     *
     * @param file The reference to the file object.
     */
    public EARDeployEntry(File file, File deployDir, File tempDir, File earConfigDir) {
        path = file.getPath();
        this.file = file;
        this.deployDir = deployDir;
        this.tempDir = tempDir;
        this.earConfigDir = earConfigDir;
        lastModified = file.lastModified();
    }

    /**
     * This constructor generates the deployment entry.
     *
     * @param entry The string to process.
     */
    public EARDeployEntry(String entry)  {
        String[] values = entry.split(",");
        path = values[0];
        file = new File(path);
        lastModified = Long.parseLong(values[1]);
        filesPath = new File(values[2]);
        contextFilePath = new File(values[3]);
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

    
    /**
     * The string containing the path to the context file path.
     * 
     * @return The string containing the context file path information
     */
    public File getContextFilePath() {
        return contextFilePath;
    }


    /**
     * This method returns the file path.
     *
     * @return The string containing the file path.
     */
    public File getFilesPath() {
        return filesPath;
    }


    /**
     * This method returns true if the file exists and has not changed.
     * 
     * @return TRUE if the file exists and has not changed.
     */
    public boolean isValid() {
        if (!file.exists()) {
            return false;
        }
        if (lastModified != file.lastModified()) {
            return false;
        }
        return true;
    }

    
    /**
     * This method is responsible for deploying the file.
     */
    public void deploy() throws EARException {
        try {
            // create the tmp directory
            File tempDir = createEarDirectory(this.tempDir);

            // create the config directory
            File configDir = createEarDirectory(this.earConfigDir);

            // unzip the file
            JarUtil.extract(file,tempDir);

            // create the files properties file
            Properties fileProperties = new Properties();
            copyFiles(tempDir,".jar", fileProperties);

            // create the context file
            Properties contextProperties = createContextEntries(tempDir, fileProperties);

            // set the ear file list name
            filesPath = writeProperties(configDir, EARConstants.EAR_FILE_LIST_NAME,
                    fileProperties);
            contextFilePath = writeProperties(configDir,
                    EARConstants.CONTEXT_FILE_LIST_NAME,
                    contextProperties);
            
        } catch (EARException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to deploy the ear because :" + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to deploy the ear because :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for undeploying it.
     */
    public void unDeploy() throws EARException {
        try {
            Properties fileProperties = readProperties(filesPath);
            Properties contextProperties = readProperties(contextFilePath);

            // remove context entries
            for (Object key: contextProperties.keySet()){
                String context = contextProperties.getProperty(key.toString());
                ContextClassLoaderLookup.getInstance().removeContext(context);
            }

            // remove files
            for (Object key: fileProperties.keySet()){
                File file = new File(fileProperties.getProperty(key.toString()));
                file.delete();
            }

            // remove the configuration files
            filesPath.delete();
            contextFilePath.delete();

        } catch (Exception ex) {
            log.error("Failed to undeploy the ear because :" + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to undeploy the ear because :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * The string representation of this object.
     *
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return path + "," + lastModified + "," + filesPath.getPath()
                + "," + contextFilePath.getPath();
    }


    /**
     * This method is called to create the temp directory for the ear file.
     *
     * @return The reference to the file.
     * @throws EARException
     */
    private File createEarDirectory(File directory) throws EARException {
        try {
            String dirName = file.getName().replace('.', '_');
            File subDirectory = new File(directory,dirName);
            if (!subDirectory.exists()) {
                subDirectory.mkdirs();
            }
            return subDirectory;
        } catch (Exception ex) {
            log.error("Failed to create the directory for [" +
                    file.getPath() + "] because : " + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to create the directory for [" +
                    file.getPath() + "] because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method creates the files properties.
     *
     * @param tempDir The temporary directory
     * @return The file that the list of files has been placed in.
     * @throws EARException
     */
    private void copyFiles(File tempDir, String suffix,
            Properties fileProperties)  throws EARException {
        try {
            File[] fileList = FileUtil.filter(tempDir.listFiles(),suffix);
            int count = 1;
            for (File file: fileList) {
                String fileName = file.getName();
                File targetFile = new File(deployDir,fileName);
                fileProperties.put("file."+ count++, targetFile.getPath());
                FileUtil.copyFile(file, targetFile);
            }
        } catch (Exception ex) {
            log.error("Filed to create the files list properties file : " +
                    ex.getMessage(),ex);
            throw new EARException
                    ("Filed to create the files list properties file : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method creates the context entries.
     *
     * @param tempDir The temporary directory.
     * @return The list of properties.
     */
    private Properties createContextEntries(File tempDir, Properties fileProperties) throws EARException {
        try {
            Properties properties = new Properties();
            File[] jarFiles = FileUtil.filter(tempDir.listFiles(),".jar");
            File[] warFiles = FileUtil.filter(tempDir.listFiles(),".war");

            int count = 1;
            for (File warFile: warFiles) {
                String fileName = warFile.getName();
                String context = WarUtils.getWarContext(warFile);
                ContextClassLoaderLookup.getInstance().addContext(context, jarFiles);
                String key = "ware.file."+ count++;
                properties.put(key, context);
                File targetFile = new File(deployDir,fileName);
                fileProperties.put(key, targetFile.getPath());
                FileUtil.copyFile(warFile, targetFile);
            }
            
            return properties;
        } catch (Exception ex) {
            log.error("Filed to create the context entries file : " +
                    ex.getMessage(),ex);
            throw new EARException
                    ("Filed to create the context entries file : " +
                    ex.getMessage(),ex);
        }
    }


    


    /**
     * This method writes the properties files out.
     *
     * @param baseDir The base directory.
     * @param file The file name.
     * @param properties The properties to write out.
     * @return
     * @throws EARException
     */
    private File writeProperties(File baseDir, String file,
            Properties properties) throws EARException {
       File  propertiesPath = new File(baseDir,file);
       writeProperties(propertiesPath,properties);
       return propertiesPath;
    }


    /**
     * The write properties
     */
    private void writeProperties(File path, Properties properties)
            throws EARException {
        try {
            FileOutputStream out = new FileOutputStream(path);
            properties.store(out, "Configuration Properties");
        } catch (Exception ex) {
            log.error("Failed to write the propeties because : " + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to write the propeties because : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method reads the properties
     */
    private Properties readProperties(File path) throws EARException {
        try {
            if (!path.exists()) {
                log.info("No properties file [" + path.getPath() + "] does not exist.");
                throw new EARException
                    ("No properties file [" + path.getPath() + "] does not exist.");
            }
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(
                        path);
            properties.load(in);
            in.close();
            return properties;
        } catch (EARException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to read the properties because : " + ex.getMessage(),ex);
            throw new EARException
                    ("Failed to read the properties because : " + ex.getMessage(),ex);
        }
    }
}
