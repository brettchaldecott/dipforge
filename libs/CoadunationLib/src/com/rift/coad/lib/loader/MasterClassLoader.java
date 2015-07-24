/*
 * CoadunationLib: The coaduntion implementation library.
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
 * MasterClassLoader.java
 *
 * This singleton is responsible for acting as the master class loader. It is
 * responsible for loading classes into the global class space.
 */

// package path
package com.rift.coad.lib.loader;

// logging import
import org.apache.log4j.Logger;


// java imports
import java.lang.ClassLoader;
import java.net.URLClassLoader;
import java.net.URL;
import java.io.File;

// coadunation imports
import com.rift.coad.BaseClassLoader;

/**
 * This singleton is responsible for acting as the master class loader. It is
 * responsible for loading classes into the global class space.
 *
 * @author Brett Chaldecott
 */
public class MasterClassLoader {
    
    // the singleton variable
    private static MasterClassLoader singleton = null;
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(MasterClassLoader.class.getName());
    
    // the refeference 
    private URLClassLoader classLoader = null;
    
    
    /**
     * Creates a new instance of MasterClassLoader
     *
     * @exception LoaderException
     */
    private MasterClassLoader() throws LoaderException {
        try {
            classLoader = (URLClassLoader)this.getClass().getClassLoader();
        } catch (Exception ex) {
            throw new LoaderException(
                    "Failed to retrieve the parent class loader : " + 
                    ex.getMessage(),ex);
        }
    }
    
    /**
     * This method returns an instance of the MasterClass Loader wrapper object.
     */
    public static synchronized MasterClassLoader init() throws
            LoaderException {
        if (singleton == null) {
            singleton = new MasterClassLoader();
        }
        return singleton;
    }
    
    
    /**
     * This method returns an instance of the MasterClass Loader wrapper object.
     */
    public static synchronized MasterClassLoader getInstance() throws
            LoaderException {
        if (singleton == null) {
            singleton = new MasterClassLoader();
        }
        return singleton;
    }
    
    
    /**
     * This method is responsible for adding a 
     */
    public void addLib(String path) throws LoaderException {
        try {
            if (classLoader instanceof BaseClassLoader) {
                ((BaseClassLoader)classLoader).addLib(path);
            } else {
                log.error(
                        "This object has not been loaded by a BaseClassLoader");
            }
        } catch (Exception ex) {
            throw new LoaderException("Failed to add the library [" + path +
                    "] because : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the reference to the url class loader.
     *
     * @return The referencd to the class loader.
     */
    public ClassLoader getLoader() {
        return classLoader;
    }
}
