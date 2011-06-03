/*
 * Tomcat: The deployer for the tomcat daemon
 * Copyright (C) 2011  Rift IT Contracting
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
 * ContextClassLoaderLookup.java
 */


// package path
package com.rift.coad.daemon.tomcat.loader;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.deployment.DeploymentLoader.ClassLoaderLookup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * The context class loader lookup.
 *
 * @author brett chaldecott
 */
public class ContextClassLoaderLookup {
    
    // The class singletons
    private static ContextClassLoaderLookup singleton = null;
    private static Logger log = Logger.getLogger(ContextClassLoaderLookup.class);

    // private member variables
    private String path;
    private Properties contextProperties = new Properties();
    
    /**
     * The context class loader lookup.
     */
    private ContextClassLoaderLookup() throws LoaderException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    ContextClassLoaderLookup.class);
            path = config.getString(LoaderConstants.LOADER_CONTEXT_PROPERTIES);
            readProperties();
        } catch (Exception ex) {
            log.error("Failed to instanciate the context loader : " +
                    ex.getMessage(),ex);
            throw new LoaderException
                    ("Failed to instanciate the context loader : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the context class loader lookup.
     *
     * @return The reference to the instance.
     */
    public static synchronized ContextClassLoaderLookup getInstance()
            throws LoaderException {
        if (singleton == null) {
            singleton = new ContextClassLoaderLookup();
        }
        return singleton;
    }


    /**
     * This method adds a new context to the class loader.
     *
     * @param context The context.
     * @param dependancies
     */
    public void addContext(String context, File[] dependancies) {
        context = context.replace('/','.');
        StringBuffer dependancyList = new StringBuffer();
        String sep = "";
        for (File dependancy : dependancies) {
            dependancyList.append(sep).append(dependancy.getName());
            sep = ",";
        }
        contextProperties.put(context,
                dependancyList.toString());
        writeProperties();
    }

    
    /**
     * This method returns the context list
     * 
     * @param context The context list
     * @return The context list
     */
    public String[] getContext(String context) {
        context = context.replace('/','.');
        if (!contextProperties.containsKey(context)) {
            return null;
        }
        return contextProperties.getProperty(context).split(",");
    }


    /**
     * The reference to the list of dependancies for this context
     * 
     * @param context The dependancies context
     * @return Return an array of class loaders.
     */
    public ClassLoader[] getDependanciesForContext(String context) 
            throws LoaderException {
        context = context.replace('/','.');
        String[] dependancyNames = getContext(context);
        if (dependancyNames == null) {
            return new ClassLoader[0];
        }
        List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
        for (String dependancyName : dependancyNames) {
            ClassLoader loader = ClassLoaderLookup.getInstance().
                    getClassLoaderByFileName(dependancyName);
            if (loader == null) {
                log.info("The context [" + context +
                        "] does not have all its dependancies loaded [" +
                        dependancyName + "]");
                throw new LoaderException("The context [" + context +
                        "] does not have all its dependancies loaded [" +
                        dependancyName + "]");
            }
            classLoaders.add(loader);
        }
        return classLoaders.toArray(new ClassLoader[0]);
    }


    /**
     * This method removes the context
     *
     * @param context The context to remove.
     */
    public void removeContext(String context) {
        context = context.replace('/','.');
        contextProperties.remove(context);
        writeProperties();
    }



    /**
     * This method reads the properties
     */
    private void readProperties() {
        try {
            File path = new File(this.path);
            if (!path.exists()) {
                log.info("No context properties to load");
                return;
            }
            FileInputStream in = new FileInputStream(
                        path);
            contextProperties.load(in);
            in.close();
        } catch (Exception ex) {
            log.error("Failed to read the properties because : " + ex.getMessage(),ex);
        }
    }


    /**
     * The write properties
     */
    private void writeProperties() {
        try {
            File path = new File(this.path);
            FileOutputStream out = new FileOutputStream(path);
            contextProperties.store(out, "The context properties");
        } catch (Exception ex) {
            log.error("Failed to write the propeties because : " + ex.getMessage(),ex);
        }
    }

}
