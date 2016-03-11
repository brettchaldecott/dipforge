/*
 * CoadunationBase: The base for a Coadunation instance.
 * Copyright (C) 2006  2015 Burntjam
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
 * RemoteClassLoaderSpi.java
 *
 * This object implements the remote class loader.
 */

// package path
package com.rift.coad.client.interceptor.iiop;

// java imports
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.RMIClassLoaderSpi;

/**
 * This object implements the remote class loader for coadunation. This is here
 * purely for debug purposes. Do not use in a production environment.
 *
 * @author Brett Chaldecott
 */
public class RemoteClassLoaderSpi extends RMIClassLoaderSpi {
    
    // private member variables
    private RMIClassLoaderSpi delegate = 
            RMIClassLoader.getDefaultProviderInstance();
    
    /**
     * Creates a new instance of RemoteClassLoaderSpi 
     */
    public RemoteClassLoaderSpi() {
    }
    
    
    /**
     * Load a proxy class to be used by RMI.
     *
     * @return The reference to the required proxy class.
     * @param codebase The code base for this object.
     * @param interfaces The interface array to be loaded.
     * @param defaultLoader The class loader that would have been used by the JVM.
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     */
    public Class loadProxyClass (String codebase, String[] interfaces, 
            ClassLoader defaultLoader) throws MalformedURLException,
            ClassNotFoundException {
        // use the thread context class loader.
        System.out.println("Load a proxy class [" + codebase + "]");
        return delegate.loadProxyClass(codebase,interfaces, defaultLoader);
    }
    
    
    /**
     * This method returns a reference to the loaded class using the thread
     * context class loader.
     *
     * @return The class loaded from the appropriate class loader.
     * @param codebase The code base to load from.
     * @param name The name of the class to load.
     * @param defaultLoader The loader that would have been used by the jvm
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     */
    public Class loadClass(String codebase, String name, 
            ClassLoader defaultLoader) throws MalformedURLException,
            ClassNotFoundException {
        // use the thread context class loader.
        if (defaultLoader == null) {
            System.out.println("Load a class [" + codebase + "] name [" +
                    name + "] defaultLoader [null]");
        } else {
            System.out.println("Load a class [" + codebase + "] name [" +
                    name + "] defaultLoader ["+
                    defaultLoader.getClass().getName()+"]");
        }
        System.out.println("Thread class loader : " + Thread.currentThread().
                getContextClassLoader().getClass().getName());
        Class result = null;
        try {
            result = delegate.loadClass(codebase,name, Thread.currentThread().
                    getContextClassLoader());
        } catch (MalformedURLException ex) {
            System.out.println("Failed to narrow : " + ex.getMessage());
            ex.printStackTrace(System.out);
            throw ex;
        } catch (ClassNotFoundException ex) {
            System.out.println("Failed to narrow : " + ex.getMessage());
            ex.printStackTrace(System.out);
            throw ex;
        }
        try {
            if (null == result && (codebase != null)) {
                ClassLoader loader = getClassLoader(codebase);
                System.out.println("Retrieve the result and enumerate through them");
                loadClasses(loader, Thread.currentThread().getContextClassLoader(), 
                        loader.getResources("/"));
            } else if (result != null){
                System.out.println("The class was loaded");
            } else {
                System.out.println("Nothing loaded and the class is null [" + 
                        codebase + "]");
            }
        } catch (Exception ex) {
            System.out.println("Caught an exception in the main load class : "
                    + ex.getMessage());
            ex.printStackTrace(System.out);
        }
        return result;
    }
    
    
    /**
     * This method returns the appropriate class loader for the specified code
     * base.
     *
     * @return The reference to the required class loader.
     * @param codebase The code base for the class loader.
     * @throws MalformedURLException
     */
    public ClassLoader getClassLoader(String codebase) throws 
            MalformedURLException {
        System.out.println("Get class loader for : " + codebase);
        URL url = new URL(codebase);
        if (url.getProtocol().equals("http")) {
            return new URLClassLoader(new URL[] {url});
        }
        return delegate.getClassLoader(codebase);
    }
    
    
    /**
     * This method returns the annotation for the specified class
     *
     * @return The string containing the path to the class.
     * @param cl The class to look up for.
     */
    public String getClassAnnotation(Class cl) {
        String annotation = null;
        try {
            annotation = delegate.getClassAnnotation(cl);
        } catch (Throwable ex) {
            annotation = System.getProperty("java.rmi.server.codebase");
        }
        return annotation;
    }
    
    
    /**
     * This method is called to load the entries into a class loader from a stub
     * code object.
     *
     * @param source The source to loader from.
     * @param target The target to loade the entries into.
     * @param entries The entries to load in.
     */
    private void loadClasses(ClassLoader source, ClassLoader target, 
            Enumeration entries) {
        try {
            while(entries.hasMoreElements()) {
                URL url = (URL)entries.nextElement();
                System.out.println(url.toURI().toString());
            }
        } catch (Exception ex) {
            System.out.println("Caught an exception : " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
