/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rift.coad.lib.deployment;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author brett chaldecott
 */
public class DeploymentClassLoader extends URLClassLoader {
    
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
//            try {
//                return systemLoader.loadClass(name);
//            } catch (Exception ex) {
//
//            }

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
            //try {
            //    return systemLoader.loadClass(name);
            //} catch (Exception ex) {
//
  //          }

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
    public DeploymentClassLoader(URL[] urls) {
        super(urls,null);
        local = new URLClassLoader(urls, this.getClass().getClassLoader());
        //systemLoader = this.getClass().getClassLoader();
    }


    /**
     *
     * @param urls
     * @param parent
     */
    public DeploymentClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, null);
        this.local = new URLClassLoader(urls,parent);
        this.parent = parent;
        //this.systemLoader = this.getClass().getClassLoader();
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

        // search the local application space to enable local classes
        // to over ride coadunation libraries.
        try {
            return local.loadClass(name);
        } catch (Throwable ex) {

        }
//        try {
//            return systemLoader.loadClass(name);
//        } catch (Exception ex) {
//
//        }

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

        // search the local application space to enable local classes
        // to over ride coadunation libraries.
        try {
            return local.loadClass(name);
        } catch (Throwable ex) {

        }
//        try {
//            return systemLoader.loadClass(name);
//        } catch (Exception ex) {
//
//        }

        // fall back to parent
        return parent.loadClass(name);
    }

}
