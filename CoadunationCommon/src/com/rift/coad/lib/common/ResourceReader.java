/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * ResourceReader.java
 *
 * This class is responsible for reading in string based resources stored within
 * a loaded class path.
 */

// package name
package com.rift.coad.lib.common;

// imports
import com.rift.coad.lib.common.*;
import java.lang.ClassLoader;
import java.io.*;
import java.net.URL;


/**
 * This object is responsible for loading in text resources stored within the
 * the class path.
 *
 * @author Brett Chaldecott
 */
public class ResourceReader {
    
    // the classes private member variables
    private String path = null;
    private String document = null;
    
    /**
     * Creates a new instance of ResourceReader
     *
     * @param path The path to the resource to read.
     */
    public ResourceReader(String path) throws Exception {
        this.path = path;
        loadDocument(getClass().getClassLoader());
    }
    
    
    /**
     * Creates a new instance of ResourceReader
     *
     * @param path The path to the resource to read.
     */
    public ResourceReader(String path,ClassLoader classLoader) 
            throws Exception {
        this.path = path;
        loadDocument(classLoader);
    }
    
    
    /**
     * This method returns the path to the resource that has been loaded by this
     * class.
     *
     * @return The string containing the path to load in.
     */
    public String getPath() {
        return path;
    }
    
    
    /**
     * This method will return the document that has been loaded in by this
     * object.
     *
     * @return The string containing the loaded document.
     */
    public String getDocument() {
        return document;
    }
    
    
    /**
     * This method the the resource into memory.
     *
     * @param classLoader The class loader reference.
     */
    private void loadDocument(ClassLoader classLoader) throws Exception {
        try {
            // set up the file object
            InputStreamReader reader = new InputStreamReader(classLoader.
                    getResourceAsStream(path));
            BufferedReader buffReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            char[] buffer = new char[1024];
            int length = 0;
            while (-1 != (length = buffReader.read(buffer))) {
                stringBuffer.append(buffer,0,length);
            }
            reader.close();
            document = stringBuffer.toString();
        } catch (Exception ex){
            throw new Exception(
                    "Failed to load the file [" + path + "]",ex);
        }
    }
}
