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
 * BaseClassLoader.java
 *
 * The class responsible for loading the libraries required to run a Coadunation
 * instance.
 */


// the package path
package com.rift.coad;

// java imports
import java.net.URLClassLoader;
import java.net.URL;
import java.io.File;
import java.util.Vector;

/**
 * The class responsible for loading the libraries required to run a Coadunation
 * instance.
 *
 * @author Brett Chaldecott
 */
public class BaseClassLoader extends URLClassLoader {
    
    // the vector containing the loaded urls
    private Vector urls = new Vector();
    
    
    /** 
     * Creates a new instance of BaseClassLoader
     */
    public BaseClassLoader(URL[] urls,ClassLoader parent) {
        super(urls,parent);
        for (int index = 0; index < urls.length; index++) {
            this.urls.add(urls[index]);
        }
    }
    
    
    /**
     * This method will add a jar to the search path
     *
     * @param path The path to the jar.
     * @exception CoadException
     */
    public void addLib(String path) throws CoadException {
        try {
            File file = new File(path);
            if (file.isFile() == false) {
                throw new CoadException("The path [" + path 
                        + "] does not point to a valid file.");
            }
            URL url = file.toURL();
            
            // synchronize on the list
            synchronized (urls) {
                for (int index = 0; index < urls.size(); index++) {
                    URL loadedURL = (URL)urls.get(index);
                    if (loadedURL.equals(url)) {
                        // already loaded
                        return;
                    }
                }
                urls.add(url);
            }
            
            // load into the path
            addURL(url);
        } catch (CoadException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CoadException("Failed to load file [" + path 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
    
}
