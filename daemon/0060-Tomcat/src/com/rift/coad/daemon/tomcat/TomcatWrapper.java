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
 * TomcatWrapper.java
 */

// package path
package com.rift.coad.daemon.tomcat;

// java imports
import com.rift.coad.daemon.tomcat.war.TomcatXMLContext;
import com.rift.coad.daemon.tomcat.war.WarUtils;
import java.net.InetAddress;
import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// log 4 j imports
import org.apache.log4j.Logger;

// configuration
import com.rift.coad.lib.common.FileUtil;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;

// tomcat imports
import com.rift.coad.lib.naming.NamingDirector;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.Loader;
import org.apache.catalina.loader.StandardClassLoader;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.Container;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.security.SecurityClassLoad;
import org.apache.catalina.startup.*;
import org.apache.tomcat.util.modeler.Registry;

/**
 * This object is responsible for wrappping the tomcat startup
 *
 * @author brett chaldecott
 */
public class TomcatWrapper {
    
    // logger
    private static Logger log = Logger.getLogger(TomcatWrapper.class.getName());
    private static String TOMCAT_DEPLOY_DIR = "/webapps";
    private static String TOMCAT_CONF_DIR = "/conf";
    private static String TOMCAT_MANAGER = "manager";
    private static String TOMCAT_ROOT = "ROOT";
    private static String TOMCAT_SERVER_CONFIG = "server.xml";
    private static String TOMCAT_WEB_CONFIG = "web.xml";
    private static String DEPLOYMENT_MAPPING_PROPERTIES = "deployment_mapping.properties";
    
    
    // private member variables
    private String defaultHost = null;
    private String basePath = null;
    
    // default host and root context
    private Catalina tomcat = null;
    private ClassLoader masterLoader = null;
    private ClassLoader childLoader = null;
    private MBeanServer mBeanServer = null;
    private ObjectName oName = null;
    private Properties deploymentMapping = null;
    
    /**
     * Creates a new instance of TomcatWrapper
     */
    public TomcatWrapper() throws TomcatException {
        ClassLoader currentLoader = 
                Thread.currentThread().getContextClassLoader();
        try {
            // work around for the Apache jndi problems
            String originalPkgPefix = System.getProperty(
                    javax.naming.Context.URL_PKG_PREFIXES);
            
            // setup the class loaders
            masterLoader = createClassLoader(
                    ((URLClassLoader)currentLoader).getURLs(),currentLoader);
            childLoader = createClassLoader(
                    ((URLClassLoader)currentLoader).getURLs(),masterLoader);
            SecurityClassLoad.securityClassLoad(childLoader);
            Thread.currentThread().setContextClassLoader(childLoader);
            Configuration configuration = ConfigurationFactory.getInstance().
                    getConfig(this.getClass());
            
            // basic config
            basePath = configuration.getString("TomcatBasePath");
            
            // clear out the directory
            clearWebAppDirectory();
            
            // clear out the configuration
            clearConfigDirectory();
            
            // setup the catalina home directory
            System.setProperty("CatalinaHome",basePath);
            tomcat = new Catalina();
            tomcat.setParentClassLoader(childLoader);
            tomcat.setCatalinaHome(basePath);
            
            // start tomcat
            log.info("Starting the engine.");
            tomcat.setUseNaming(false);
            tomcat.start();
            log.info("Engine started");
            
            // force reset of naming search path as Tomcat assumes it is the
            // owner of the path
            System.setProperty(javax.naming.Context.URL_PKG_PREFIXES,
                    originalPkgPefix);
            
            // retrieve the reference to the bean server
            mBeanServer = Registry.getRegistry(null, null).getMBeanServer();
            
            // the object name
            oName = new ObjectName("Coadunation:type=Deployer,host=localhost");
            
            // load properties mapping
            deploymentMapping = new Properties();
            
            File deploymentMappingFile = new File(getTomcatBase(),
                    DEPLOYMENT_MAPPING_PROPERTIES);
            if (deploymentMappingFile.exists()) {
                FileInputStream in = new FileInputStream(
                        deploymentMappingFile);
                deploymentMapping.load(in);
                in.close();
            }
        } catch (Exception ex) {
            log.error("Failed to init tomcat : " + ex.getMessage(),ex);
            throw new TomcatException(
                    "Failed to init tomcat : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
        
    }
    
    /**
     * This method returns the tomcat base
     */
    public String getTomcatBase() {
        return basePath + TOMCAT_DEPLOY_DIR;
    }
    
    /**
     * This method returns the deployment mapping
     * 
     * @return the properties containing the deployment mapping.
     */
    public Properties getDeploymentMapping() {
        return deploymentMapping;
    }
    
    /**
     * This method returns the context file
     * 
     * @return The context file
     * @param context the context to retrieve the file for.
     */
    public File getContextFile(String context) {
        return new File(basePath + TOMCAT_DEPLOY_DIR,
                    context + ".war");
    }
    
    /**
     * This method will load the specified war file.
     * 
     * @param war The war file to load
     */
    public void loadWar(File war) {
        ClassLoader currentLoader = 
                Thread.currentThread().getContextClassLoader();
        try {
            log.info("Deploy the file [" + war.getPath() + "]");
            Thread.currentThread().setContextClassLoader(childLoader);
            String context = WarUtils.getWarContext(war);
            // check if it is deployed
            isDeployed(context);
            
            FileUtil.copyFile(war,
                    new File(basePath + TOMCAT_DEPLOY_DIR,
                    context + ".war"));
            check(context);
            this.addDeploymentMapping(war.getPath(), context);
            log.info("Deployed the file [" + war.getPath() + "]");
        } catch (Exception ex) {
            log.error("Failed to load the war file : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
    }
    
    /**
     * This method will load the specified war file.
     */
    public void reloadWar(File war) {
        ClassLoader currentLoader = 
                Thread.currentThread().getContextClassLoader();
        try {
            log.info("Redeploy the file [" + war.getPath() + "]");
            Thread.currentThread().setContextClassLoader(childLoader);
            String existingContext =(String)this.deploymentMapping.get(war.getPath());
            String context = WarUtils.getWarContext(war);
            if (existingContext.equals(context)) {
                unloadWar(war);
            } else {
                // remove the existing ware file
                new File(basePath + TOMCAT_DEPLOY_DIR,
                        context + ".war").delete();
            }
            // check if it is deployed
            FileUtil.copyFile(war,
                    new File(basePath + TOMCAT_DEPLOY_DIR,
                    context + ".war"));
            check(context);
            this.addDeploymentMapping(war.getPath(), context);
            log.info("Redeployed the file [" + war.getPath() + "]");
        } catch (Exception ex) {
            log.error("Failed to load the war file : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
    }
    
    
    /**
     * This method unloads the given war file.
     *
     * @param war The ware file object.
     */
    public void unloadWar(File war) {
        ClassLoader currentLoader = 
                Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(childLoader);
            log.info("Unload the file [" + war.getPath() + "]");
            String context = (String)this.getDeploymentMapping().get(war.getPath());
            removeDeploymentMapping(war.getPath());
            unDeployed(context);
        } catch (Exception ex) {
            log.error("Failed to unload the war file : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
    }
    
    
    /**
     * This method unloads the given war file.
     *
     * @param war The ware file object.
     */
    public void unloadWar(String path) {
        ClassLoader currentLoader = 
                Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(childLoader);
            log.info("Unload the file [" + path + "]");
            String context = (String)this.deploymentMapping.get(path);
            removeDeploymentMapping(path);
            unDeployed(context);
            log.info("Unloaded the file [" + path + "]"); 
        } catch (Exception ex) {
            log.error("Failed to unload the war file : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(currentLoader);
        }
    }
    
    
    /**
     * This method check if the specified object is deployed.
     *
     * @return True if context is deployed.
     * @param context The context to deploy.
     * @exception Exception
     */
    private boolean isDeployed(String context) throws Exception {
        String[] params = { context };
        String[] signature = { "java.lang.String" };
        Boolean result = (Boolean) mBeanServer.invoke(oName, 
                "isDeployed", params, signature);
        return result.booleanValue();
    }
    
    
    /**
     * This method check if the specified object is deployed.
     *
     * @return True if context is deployed.
     * @param context The context to deploy.
     * @exception Exception
     */
    private void check(String context) throws Exception {
        if (context.equals("/ROOT") || context.equals("ROOT")) {
            context = "";
        }
        log.info("Check the context [" + context + "]");
        String[] params = { context };
        String[] signature = { "java.lang.String" };
        mBeanServer.invoke(oName, 
                "check", params, signature);
    }
    
    
    /**
     * This method will undeploy the object in the context
     *
     * @return True if context is deployed.
     * @param context The context to deploy.
     * @exception Exception
     */
    private void unDeployed(String context) throws Exception {
        try {
            log.info("Undeploy the context [" + context + "]");
            String strippedContext = context.substring(1);
            File[] files = new File(basePath + TOMCAT_DEPLOY_DIR).listFiles();
            for (int index = 0; index < files.length; index++) {
                if (files[index].getName().equals(strippedContext) ||
                        files[index].getName().equals(strippedContext + ".war")) {
                    log.info("Delete the target [" + files[index] + "]");
                    FileUtil.delTargetRecursive(files[index]);
                }
            }
            check(context);
        } catch (Exception ex) {
            log.error("Failed to clear out the webapp directory :" +
                    ex.getMessage());
            throw new TomcatException(
                    "Failed to clear out the webapp directory :" +
                    ex.getMessage());
        }
    }
    
    
    /**
     * This method is called to stop tomcat
     */
    public void stop() {
        try {
            tomcat.stop();
        } catch (Exception ex) {
            log.error("Failed to stop tomcat : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method creates the new class loader
     */
    private ClassLoader createClassLoader(URL[] urls,ClassLoader parent) {
        try {
            String[] locations = new String[urls.length];
            Integer[] types = new Integer[urls.length];
            for (int index = 0; index < urls.length; index++) {
                locations[index] = urls[index].toString();
                types[index] = new Integer(3); 
            }
            return ClassLoaderFactory.createClassLoader
                (locations, types, parent);
        } catch (Exception ex) {
            log.error("Failed to create a new class loader : " + 
                    ex.getMessage(),ex);
            return parent;
        }
    }
    
    
    /**
     * This method clears out the web app directory.
     */
    private void clearWebAppDirectory() throws TomcatException {
        /*try {
            File[] files = new File(basePath + TOMCAT_DEPLOY_DIR).listFiles();
            for (int index = 0; index < files.length; index++) {
                if (files[index].getName().equals(TOMCAT_ROOT) || 
                        files[index].getName().equals(TOMCAT_MANAGER)) {
                    continue;
                }
                log.info("Remove the file [" + files[index] + "]");
                FileUtil.delTargetRecursive(files[index]);
            }
        } catch (Exception ex) {
            log.error("Failed to clear out the webapp directory :" +
                    ex.getMessage());
            throw new TomcatException(
                    "Failed to clear out the webapp directory :" +
                    ex.getMessage());
        }*/
    }
    
    
    /**
     * This method clears out the configuration directory.
     */
    private void clearConfigDirectory() throws TomcatException {
        /*try {
            File[] files = new File(basePath + TOMCAT_CONF_DIR).listFiles();
            for (int index = 0; index < files.length; index++) {
                if (files[index].getName().equals(TOMCAT_SERVER_CONFIG) || 
                        files[index].getName().equals(TOMCAT_WEB_CONFIG)) {
                    continue;
                }
                FileUtil.delTargetRecursive(files[index]);
            }
        } catch (Exception ex) {
            log.error("Failed to clear out the configuration directory :" +
                    ex.getMessage());
            throw new TomcatException(
                    "Failed to clear out the configuration directory :" +
                    ex.getMessage());
        }*/
    }
    
    
    /**
     * This method addes a new deployment mapping
     * 
     * @param file The file that is the souce.
     * @param context The context that was generated from the file.
     * @throws TomcatException
     */
    private void addDeploymentMapping(String file, String context) throws TomcatException {
        try {
            this.deploymentMapping.put(file, context);
            File deploymentMappingFile = new File(getTomcatBase(),
                    DEPLOYMENT_MAPPING_PROPERTIES);
            FileOutputStream out = new FileOutputStream(deploymentMappingFile);
            deploymentMapping.store(out, "deployment contents");
            out.close();
        } catch (Exception ex) {
            log.error("Failed to add the deployment mapping: " +
                    ex.getMessage());
            throw new TomcatException(
                    "Failed to add the deployment mapping : " +
                    ex.getMessage());
        }
    }
    
    /**
     * This method removes a deployment mapping
     * 
     * @param file The file to remove.
     */
    private void removeDeploymentMapping(String file) throws TomcatException {
        try {
            this.deploymentMapping.remove(file);
            File deploymentMappingFile = new File(getTomcatBase(),
                    DEPLOYMENT_MAPPING_PROPERTIES);
            FileOutputStream out = new FileOutputStream(deploymentMappingFile);
            deploymentMapping.store(out, "deployment contents");
            out.close();
        } catch (Exception ex) {
            log.error("Failed to add the deployment mapping: " +
                    ex.getMessage());
            throw new TomcatException(
                    "Failed to add the deployment mapping : " +
                    ex.getMessage());
        }
    }
}
