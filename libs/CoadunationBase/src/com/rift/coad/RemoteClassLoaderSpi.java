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
package com.rift.coad;

// java imports
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.RMIClassLoaderSpi;

/**
 * This object implements the remote class loader for coadunation.
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
     * @param codeBase The code base for this object.
     * @param interfaces The interface array to be loaded.
     * @param default The class loader that would have been used by the JVM.
     * @exception MalformedURLException
     * @exception ClassNotFoundException
     */
    public Class loadProxyClass (String codebase, String[] interfaces, 
            ClassLoader defaultLoader) throws MalformedURLException,
            ClassNotFoundException {
        // use the thread context class loader.
        return delegate.loadProxyClass(codebase,interfaces, 
                Thread.currentThread().getContextClassLoader());
    }
    
    
    /**
     * This method returns a reference to the loaded class using the thread
     * context class loader.
     *
     * @return The class loaded from the appropriate class loader.
     * @param codebase The code base to load from.
     * @param name The name of the class to load.
     * @param defaultLoader The loader that would have been used by the jvm
     * @exception MalformedURLException
     * @exception ClassNotFoundException
     */
    public Class loadClass(String codebase, String name, 
            ClassLoader defaultLoader) throws MalformedURLException,
            ClassNotFoundException {
        // use the thread context class loader.
        return delegate.loadClass(codebase,name, 
                Thread.currentThread().getContextClassLoader());
    }
    
    
    /**
     * This method returns the appropriate class loader for the specified code
     * base.
     *
     * @return The reference to the required class loader.
     * @param codebase The code base for the class loader.
     * @exception MalformedURLException
     */
    public ClassLoader getClassLoader(String codebase) throws 
            MalformedURLException {
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
}
