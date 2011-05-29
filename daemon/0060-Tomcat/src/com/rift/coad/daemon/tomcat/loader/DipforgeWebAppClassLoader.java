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
 * DipforgeWebAppClassLoader.java
 */


package com.rift.coad.daemon.tomcat.loader;

import java.io.File;
import java.io.InputStream;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.log4j.Logger;

/**
 * The implementation of a customer class loader.
 *
 * @author brett chaldecott
 */
public class DipforgeWebAppClassLoader extends  WebappClassLoader {

    // class singletons
    private static Logger log = Logger.getLogger(DipforgeWebAppClassLoader.class);

    
    // private member variables
    private ClassLoader[] loaders = null;


    /**
     * The dip
     */
    public DipforgeWebAppClassLoader() {
    }

    /**
     * 
     * @param parent
     */
    public DipforgeWebAppClassLoader(ClassLoader parent) {
        super(parent);
    }


    /**
     * This method is called to start the dipforge web class loader
     *
     * @throws LifecycleException
     */
    @Override
    public void start() throws LifecycleException {
        super.start();
        log.info("Start the context : " + this.getContextName());

    }




    /**
     * This method attempts to find the specified class.
     *
     * @param name The name of the class to perform the search for.
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        try {
            return super.findClass(name);
        } catch (ClassNotFoundException ex) {
            // ignore
        }
        return getClass(name);
    }

    /**
     * This method returns the reference to the loaded class.
     * @param name The name of the class to search for.
     * @return The name of the class.
     * @throws ClassNotFoundException
     */
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException ex) {
            // ignore
        }
        return getClass(name);
    }


    /**
     * This method loads the classes.
     *
     * @param name The name of the class to load.
     * @param resolve
     * @return The load class method.
     * @throws ClassNotFoundException
     */
    @Override
    public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException ex) {
            // ignore
        }
        return getClass(name);
    }


    /**
     * This method returns the reference to the class identified by the name.
     * @param name The name to search for.
     * @return The reference to the class if found.
     * @throws ClassNotFoundException
     */
    private Class getClass(String name) throws ClassNotFoundException {
        try {
            ClassLoader[] loaders = getLoaders();
            for (ClassLoader loader: loaders) {
                try {
                    return loader.loadClass(name);
                } catch (ClassNotFoundException ex) {
                    // ignore and continue
                }
            }
        } catch (ClassNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to find the class [" + name + "] because :" +
                    ex.getMessage(),ex);
            throw new ClassNotFoundException
                    ("Failed to find the class [" + name + "] because :" +
                    ex.getMessage(),ex);
        }
        throw new ClassNotFoundException("Failed to find the class :" + name);
    }


    /**
     * Retrieve the loaders.
     *
     * @return The list of loaders
     * @throws Exception
     */
    private ClassLoader[] getLoaders() throws Exception {
        try {
            if (loaders != null) {
                return loaders;
            }
            loaders = ContextClassLoaderLookup.getInstance().
                    getDependanciesForContext(this.getContextName());
            return loaders;
        } catch (Exception ex) {
            log.error("Failed to retrieve the class loaders : " + ex.getMessage(),ex);
            throw new ClassNotFoundException("Failed to find the loaders : " +
                    ex.getMessage(),ex);
        }
    }
}
