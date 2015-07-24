/*
 * GroovyDaemon: The coadunation groovy daemon
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
 * CoadunationGroovyClassLoader.java
 */

package com.rift.dipforge.groovy.lib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * The class loader for coadunation groovy applications. It works in the
 * following search order
 * <ol>
 *     <li>System
 *     <li>Local
 *     <li>Parent
 * </ol>
 *
 * @author brett chaldecott
 */
public class GroovyClassLoader extends URLClassLoader {

    // internal class loader
    public class InternalClassLoader extends URLClassLoader {

        public InternalClassLoader(URL[] urls) {
            super(urls);
        }

        public InternalClassLoader(URL[] urls, ClassLoader parent) {
            super(urls,parent);
        }


        /**
         *
         * @param name
         * @return
         * @throws java.lang.ClassNotFoundException
         */
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            // search system first to make sure that the J2SE classes are
            // not over run
            try {
                return systemLoader.loadClass(name);
            } catch (Exception ex) {

            }

            // fall back to parent
            return parent.loadClass(name);
        }

        /**
         * This method defers the to the main load class method.
         *
         * @param name The name of the class
         * @param resolve
         * @return
         * @throws java.lang.ClassNotFoundException
         */
        @Override
        protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            // search system first to make sure that the J2SE classes are
            // not over run
            try {
                return systemLoader.loadClass(name);
            } catch (Exception ex) {

            }

            // fall back to parent
            return parent.loadClass(name);
        }

    }

    // private member variables
    private ClassLoader systemLoader;
    private ClassLoader parent;
    private URLClassLoader local;
    
    /**
     * This constructor sets up the groovy class loader environment.
     * 
     * @param urls URLS.
     */
    public GroovyClassLoader(URL[] urls) {
        super(urls);
        local = new URLClassLoader(urls, new InternalClassLoader(urls,this.getClass().getClassLoader()));
        systemLoader = ClassLoader.getSystemClassLoader();
    }


    /**
     *
     * @param urls
     * @param parent
     */
    public GroovyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.local = new URLClassLoader(urls,new InternalClassLoader(urls,parent));
        this.parent = parent;
        this.systemLoader = ClassLoader.getSystemClassLoader();
    }

    
    /**
     * This method defers to the load class method.
     * 
     * @param name The name of the class to look for.
     * @return The results of the search.
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return loadClass(name);
    }


    /**
     *
     * @param name
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // search system first to make sure that the J2SE classes are
        // not over run
        try {
            return systemLoader.loadClass(name);
        } catch (Exception ex) {

        }

        // search the local application space to enable local classes
        // to over ride coadunation libraries.
        try {
            return local.loadClass(name);
        } catch (Exception ex) {

        }

        // fall back to parent
        return parent.loadClass(name);
    }

    /**
     * This method defers the to the main load class method.
     *
     * @param name The name of the class
     * @param resolve
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // search system first to make sure that the J2SE classes are
        // not over run
        try {
            return systemLoader.loadClass(name);
        } catch (Exception ex) {

        }

        // search the local application space to enable local classes
        // to over ride coadunation libraries.
        try {
            return local.loadClass(name);
        } catch (Exception ex) {

        }
        // fall back to parent
        return parent.loadClass(name);
    }




}
