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
    private String[] basePaths;
    private String[] subdirs;
    private String[] libsdir;
    private Object groovyScriptEngine;
    private GroovyClassLoader classLoader;


    private Map<String,File[]> directoryCache = new HashMap<String, File[]>();

    /**
     * The groovy environment manager.
     *
     * @param basePaths The base paths.
     * @param subdirs The sub directories.
     * @param libsdir The libs directory.
     * @throws GroovyEnvironmentException
     */
    private GroovyEnvironmentManager(String[] basePaths, String[] subdirs, String[] libsdir) throws GroovyEnvironmentException {
        this.basePaths = basePaths;
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
    public static synchronized GroovyEnvironmentManager init(String[] basePaths,
            String[] subdirs, String[] libsdir) throws GroovyEnvironmentException {
        return singleton = new GroovyEnvironmentManager(basePaths, subdirs, libsdir);
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
     * @return The reference to the environment executer.
     * @throws GroovyEnvironmentException
     */
    public GroovyExecuter getExecuter() throws GroovyEnvironmentException {
        manageGroovyScriptEngine();
        return new GroovyExecuter(groovyScriptEngine,classLoader);
    }


    /**
     * This method is used to return the groovy script engine.
     *
     * @return The object reference.
     * @throws GroovyEnvironmentException
     */
    private void manageGroovyScriptEngine() throws GroovyEnvironmentException {
        if (!checkForChanges()) {
            return;
        }

        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            List<URL> paths = generateGroovyLibPath(libsdir);
            classLoader = new GroovyClassLoader(paths.toArray(new URL[0]),
                    current);

            classLoader.clearAssertionStatus();
            Thread.currentThread().setContextClassLoader(classLoader);
            Class ref = classLoader.loadClass("groovy.util.GroovyScriptEngine");
            String[] path = generateScriptDirectories(basePaths).toArray(new String[0]);
            Constructor constructor = ref.getConstructor(path.getClass(),ClassLoader.class);
            groovyScriptEngine = constructor.newInstance(path,classLoader);
            // force the recompile of dependancy classes
            Method method = groovyScriptEngine.getClass().getMethod("getGroovyClassLoader");
            Object groovyClassLoader = method.invoke(groovyScriptEngine);
            method = GroovyReflectionUtil.getMethod(groovyClassLoader, "setShouldRecompile", boolean.class);
            if (method != null) {
                method.invoke(groovyClassLoader, true);
            } else {
                log.error("Failed to set the should recompile flag");
            }
        } catch (GroovyEnvironmentException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to instanticte the groovy environment : " + ex.getMessage(),ex);
            throw new GroovyEnvironmentException(
                    "Failed to instanticte the groovy environment : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(current);
        }
    }


    /**
     * This method returns true if there are any changes in the directories that
     * the groovy script engine runs through.
     *
     * @return boolean TRUE there are changes, FALSE if not.
     *
     */
    private boolean checkForChanges() throws GroovyEnvironmentException {
        if (checkDirectoryList(basePaths) || checkDirectoryList(libsdir)) {
            return true;
        }
        return false;
    }


    /**
     * This method loops through the base path list.
     *
     * @param paths The list of paths.
     * @return The reference to the base path list.
     * @throws GroovyEnvironmentExeption
     */
    private boolean checkDirectoryList(String[] paths) throws GroovyEnvironmentException {
        for (String path: paths) {
            File directory = new File(path);
            if (!directory.isDirectory()) {
                log.info("The directory path [" + path + "] is invalid.");
                throw new GroovyEnvironmentException("The directory path [" + path + "] is invalid.");
            }
            File[] currentFiles = directory.listFiles();
            File[] oldFiles = directoryCache.get(path);
            if (oldFiles == null) {
                return true;
            }
            for (File currentFile: currentFiles) {
                if (!findFile(oldFiles, currentFile)) {
                    return true;
                }
            }
            for (File oldFile: oldFiles) {
                if (!findFile(currentFiles, oldFile)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * This method performs a search for files in the file path.
     *
     * @param fileList The file list to perform the search on.
     * @param searchPath The search file.
     * @return True if found.
     */
    private boolean findFile(File[] fileList, File searchFile) {
        for (File file: fileList) {
            if (file.equals(searchFile)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method returns a directory contain the groovy lib paths.
     *
     * @param groovyLibPath The file path.
     * @return The generated path.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    private List<URL> generateGroovyLibPath(String[] libDirectories) throws GroovyEnvironmentException {
        try {
            List<URL> paths = new ArrayList<URL>();
            for (String groovyLibPath: libDirectories) {
                File[] files = new File(groovyLibPath).listFiles();
                for (File file : files) {
                    if (!file.isFile()) {
                        continue;
                    }
                    if (file.getName().endsWith("jar")) {
                        paths.add(file.toURI().toURL());
                    }
                }
            }
            return paths;
        } catch (Exception ex) {
            log.error("Failed to generate a groovy lib path : " + ex.getMessage());
            throw new GroovyEnvironmentException
                    ("Failed to generate a groovy lib path : " + ex.getMessage());
        }
    }


    /**
     * This method generates the script directories.
     *
     * @param basePaths The list of base paths that contains script directories.
     * @return The list of paths.
     * @throws GroovyEnvironmentException
     */
    private List<String> generateScriptDirectories(String[] basePaths) throws GroovyEnvironmentException {
        try {
            List<String> resultPaths = new ArrayList<String>();
            for (String basePath: basePaths) {
                File[] directories = new File(basePath).listFiles();
                for (File directory: directories) {
                    for (String subdir: subdirs) {
                        File subdirectory = new File(directory, subdir);
                        if (!subdirectory.isDirectory()) {
                            continue;
                        }
                        resultPaths.add(subdirectory.getPath());
                    }
                }
            }
            return resultPaths;
        } catch (Throwable ex) {
            log.error("Failed to generate the script paths : " + ex.getMessage(),ex);
            throw new GroovyEnvironmentException
                    ("Failed to generate the script paths : " + ex.getMessage(),ex);
        }
    }
}
