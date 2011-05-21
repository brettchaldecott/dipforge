/*
 * GroovyLib: The goovy environment manager
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
 * GroovyEnvironmentManager.java
 */

package com.rift.dipforge.groovy.lib;

import com.rift.dipforge.groovy.lib.reflect.GroovyReflectionUtil;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This object is responsible for managing the groovy environment.
 *
 * @author brett chaldecott
 */
public class GroovyEnvironmentManager {

    // class singleton.
    private static GroovyEnvironmentManager singleton = null;
    private static Logger log = Logger.getLogger(GroovyEnvironmentManager.class);

    // private member variables.
    private String dipLibPath;
    private String basePath;
    private String libDir;
    private String[] subdirs;
    private String[] libsdir;
    
    private Map<ContextInfo,GroovyExecuter> executerMap = new HashMap<ContextInfo,GroovyExecuter>();
    
    /**
     * The groovy environment manager.
     *
     * @param dipLibPath The dip lib paths.
     * @param basePath The base paths.
     * @param libDir The lib Directory.
     * @param subdirs The sub directories.
     * @param libsdir The libs directory.
     * @throws GroovyEnvironmentException
     */
    private GroovyEnvironmentManager(String dipLibPath, String basePath,String libDir,
            String[] subdirs, String[] libsdir) throws GroovyEnvironmentException {
        this.dipLibPath = dipLibPath;
        this.basePath = basePath;
        this.libDir = libDir;
        this.subdirs = subdirs;
        this.libsdir = libsdir;
    }


    /**
     * This object is responsible for setting up the groovy environment
     *
     * @param basePaths The base paths.
     * @param subdirs The sub dirs.
     * @param libsdir The library directories.
     * @return And instance of the groovy environment singleton.
     * @throws GroovyEnvironmentException
     */
    public static synchronized GroovyEnvironmentManager init(String dipLibPath, String basePath,
            String libDir, String[] subdirs, String[] libsdir) throws GroovyEnvironmentException {
        return singleton = new GroovyEnvironmentManager(dipLibPath,basePath,libDir,subdirs, libsdir);
    }


    /**
     * This method returns the reference to the groovy environment.
     *
     * @return The singleton reference to the groovy environment manager.
     * @throws GroovyEnvironmentException
     */
    public static synchronized GroovyEnvironmentManager getInstance() throws GroovyEnvironmentException {
        if (singleton == null) {
            throw new GroovyEnvironmentException("The groovy environment has not been setup.");
        }
        return singleton;
    }



    /**
     * This method returns the reference to the groovy executer.
     *
     * @param context The context identifying url.
     * @return The reference to the environment executer.
     * @throws GroovyEnvironmentException
     */
    public GroovyExecuter getExecuter(ContextInfo context) throws GroovyEnvironmentException {
        GroovyExecuter executer = executerMap.get(context);
        if (executer != null && !executer.checkForChanges()) {
            return executer;
        }
        executer = new GroovyExecuter(context, dipLibPath, basePath, libDir, subdirs, libsdir);
        executerMap.put(context, executer);
        return executer;
    }


    
}
