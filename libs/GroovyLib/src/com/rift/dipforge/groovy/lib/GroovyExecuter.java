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
import com.rift.dipforge.groovy.lib.servlet.DipforgeServlet;
import groovy.lang.Closure;
import groovy.servlet.AbstractHttpServlet;
import groovy.servlet.ServletBinding;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The implementation of the groovy executer.
 *
 * @author brett chaldecott
 */
public class GroovyExecuter {

    // class singletons
    private Logger log = Logger.getLogger(GroovyExecuter.class);
    // private member variables
    private ContextInfo context;
    private String dipLibPath;
    private String basePath;
    private String[] subdirs;
    private String[] libsdir;
    private Object groovyScriptEngine;
    private GroovyClassLoader classLoader;
    private Map<String, File[]> directoryCache = new HashMap<String, File[]>();

    /**
     * The constructor of the groovy script engine.
     *
     * @param groovyScriptEngine The script engine.
     * @param classLoader The class loader.
     */
    protected GroovyExecuter(ContextInfo context, String dipLibPath, String basePath,
            String[] subdirs, String[] libsdir) throws GroovyEnvironmentException {
        this.context = context;
        this.dipLibPath = dipLibPath;
        this.basePath = basePath;
        this.subdirs = subdirs;
        this.libsdir = libsdir;
        initGroovyScriptEngine();
    }

    /**
     * This method invokes a script in the groovy environment and returns the result.
     *
     * @param script The script path.
     * @param parameters The parameters for the script.
     * @return The result.
     * @throws com.rift.coad.groovy.BridgeException
     */
    public Object executeScript(String script, String[] parameterNames, Object[] parameters)
            throws GroovyEnvironmentException {
        if (parameterNames.length != parameters.length) {
            throw new GroovyEnvironmentException(
                    "Must supply the same number of parameters as parameter names.");
        }
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object binding = classLoader.loadClass("groovy.lang.Binding").newInstance();
            // bind the parameters
            Method bindMethod = GroovyReflectionUtil.getMethod(binding, "setProperty", String.class, Object.class);
            for (int index = 0; index < parameters.length; index++) {
                String parameterName = parameterNames[index];
                Object parameter = parameters[index];
                log.info("Adding the bind parameter named : " + parameterName);
                bindMethod.invoke(binding, parameterName, parameter);
            }

            Method run = groovyScriptEngine.getClass().getMethod("run",
                    String.class, binding.getClass());
            return run.invoke(groovyScriptEngine, script, binding);
        } catch (InvocationTargetException invocationException) {
            Throwable ex = invocationException.getTargetException();
            log.error("Failed to execute the groovy script : " + ex.getMessage(), ex);
            throw new GroovyEnvironmentException("Failed to execute the groovy script : " + ex.getMessage());

        } catch (Throwable ex) {
            log.error("Failed to execute the groovy script : " + ex.getMessage(), ex);
            throw new GroovyEnvironmentException("Failed to execute the groovy script : " + ex.getMessage());
        } finally {
            // reset the class loader
            Thread.currentThread().setContextClassLoader(current);
        }
    }

    /**
     * This method is called to execute a script on behalf of a servlet.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @param servlet The servlet.
     * @throws GroovyEnvironmentException
     */
    public void executeServletScript(HttpServletRequest request,
            HttpServletResponse response, DipforgeServlet servlet)
            throws GroovyEnvironmentException, java.io.IOException {

        // Get the script path from the request - include aware (GROOVY-815)
        String scriptUri = this.context.stripContext(servlet.getScriptUri(request));

        // Run the script
        ServletContext servletContext = servlet.getServletContext();
        ClassLoader current = Thread.currentThread().getContextClassLoader();


        try {
            Thread.currentThread().setContextClassLoader(classLoader);

            // Set it to HTML by default
            response.setContentType("text/html; charset=" + servlet.getEncoding());

            // Set up the script context
            Class bindingClass = classLoader.loadClass("groovy.servlet.ServletBinding");
            Class requestClass = classLoader.loadClass("javax.servlet.http.HttpServletRequest");
            Class responseClass = classLoader.loadClass("javax.servlet.http.HttpServletResponse");
            Class servletContextClass = classLoader.loadClass("javax.servlet.ServletContext");
            Constructor[] bindingConstructors = bindingClass.getConstructors();
            Object binding = bindingConstructors[0].newInstance(request, response, servletContext);

            // setup the servlet boot strap environment
            Class bootstrapClass = classLoader.loadClass(
                    "com.rift.dipforge.groovy.bootstrap.BoostrapClosureWrapper");
            Constructor bootstrapConstructor = bootstrapClass.getConstructor(
                    groovyScriptEngine.getClass());
            Object bootstrap = bootstrapConstructor.newInstance(groovyScriptEngine);
            Method run = bootstrap.getClass().getMethod("run",
                    String.class, binding.getClass());
            run.invoke(bootstrap, scriptUri, binding);

            /*
             * Set reponse code 200.
             */
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InvocationTargetException exception) {
            Throwable runtimeException = exception.getTargetException();
            StringBuffer error = new StringBuffer("GroovyServlet Error: ");
            error.append(" script: '");
            error.append(scriptUri);
            error.append("': ");
            Throwable e = runtimeException.getCause();
            /*
             * Null cause?!
             */
            if (e == null) {
                error.append(" Script processing failed.");
                error.append(runtimeException.getMessage());
                if (runtimeException.getStackTrace().length > 0) {
                    error.append(runtimeException.getStackTrace()[0].toString());
                }
                servletContext.log(error.toString());
                System.err.println(error.toString());
                runtimeException.printStackTrace(System.err);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error.toString());
                return;
            }
            /*
             * Resource not found.
             */
            try {
                if (e.getClass().isAssignableFrom(classLoader.loadClass("groovy.util.ResourceException"))) {
                    error.append(" Script not found, sending 404.");
                    servletContext.log(error.toString());
                    System.err.println(error.toString());
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } catch (Exception ex) {
                log.error("Failed to check the exception is not a [groovy.util.ResourceException] because : " + ex.getMessage(), ex);
            }
            /*
             * Other internal error. Perhaps syntax?!
             */
            servletContext.log("An error occurred processing the request", runtimeException);
            error.append(e.getMessage());
            if (e.getStackTrace().length > 0) {
                error.append(e.getStackTrace()[0].toString());
            }
            servletContext.log(e.toString());
            System.err.println(e.toString());
            runtimeException.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        } catch (Throwable runtimeException) {
            StringBuffer error = new StringBuffer("GroovyServlet Error: ");
            error.append(" script: '");
            error.append(scriptUri);
            error.append("': ");
            Throwable e = runtimeException.getCause();
            /*
             * Null cause?!
             */
            if (e == null) {
                error.append(" Script processing failed.");
                error.append(runtimeException.getMessage());
                if (runtimeException.getStackTrace().length > 0) {
                    error.append(runtimeException.getStackTrace()[0].toString());
                }
                servletContext.log(error.toString());
                System.err.println(error.toString());
                runtimeException.printStackTrace(System.err);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error.toString());
                return;
            }
            /*
             * Resource not found.
             */
            try {
                if (e.getClass().isAssignableFrom(classLoader.loadClass("groovy.util.ResourceException"))) {
                    error.append(" Script not found, sending 404.");
                    servletContext.log(error.toString());
                    System.err.println(error.toString());
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } catch (Exception ex) {
                log.error("Failed to check the exception is not a [groovy.util.ResourceException] because : " + ex.getMessage(), ex);
            }
            /*
             * Other internal error. Perhaps syntax?!
             */
            servletContext.log("An error occurred processing the request", runtimeException);
            error.append(e.getMessage());
            if (e.getStackTrace().length > 0) {
                error.append(e.getStackTrace()[0].toString());
            }
            servletContext.log(e.toString());
            System.err.println(e.toString());
            runtimeException.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        } finally {
            /*
             * Finally, flush the response buffer.
             */
            response.flushBuffer();

            // reset the class loader
            Thread.currentThread().setContextClassLoader(current);
        }
    }

    /**
     * This method is used to return the groovy script engine.
     *
     * @return The object reference.
     * @throws GroovyEnvironmentException
     */
    private void initGroovyScriptEngine() throws GroovyEnvironmentException {
        //ClassLoader current = Thread.currentThread().getContextClassLoader();
        ClassLoader current = this.getClass().getClassLoader();
        try {
            List<URL> paths = generateGroovyLibPath(libsdir);
            log.info("Setup the groovy class loader up with the following path [" + paths.toString() + "]");
            classLoader = new GroovyClassLoader(paths.toArray(new URL[0]),
                    current);

            classLoader.clearAssertionStatus();
            Thread.currentThread().setContextClassLoader(classLoader);
            Class ref = classLoader.loadClass("groovy.util.GroovyScriptEngine");
            List<String> pathList = generateScriptDirectories(basePath, context.getPath());
            pathList.addAll(generateScriptDirectories(this.dipLibPath));
            log.info("Setup the groovy environment for [" + pathList.toString() + "]");
            String[] path = pathList.toArray(new String[0]);
            Constructor constructor = ref.getConstructor(path.getClass(), ClassLoader.class);
            groovyScriptEngine = constructor.newInstance(path, classLoader);
            // force the recompile of dependancy classes
            Method method = groovyScriptEngine.getClass().getMethod("getGroovyClassLoader");
            Object groovyClassLoader = method.invoke(groovyScriptEngine);
            method = GroovyReflectionUtil.getMethod(groovyClassLoader, "setShouldRecompile", Boolean.class);
            if (method != null) {
                method.invoke(groovyClassLoader, new Boolean(true));
            } else {
                log.error("Failed to set the should recompile flag");
            }
        } catch (GroovyEnvironmentException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to instanticte the groovy environment : " + ex.getMessage(), ex);
            throw new GroovyEnvironmentException(
                    "Failed to instanticte the groovy environment : " + ex.getMessage(), ex);
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
    protected boolean checkForChanges() throws GroovyEnvironmentException {
        return checkDirectoryList(libsdir);
    }

    /**
     * This method loops through the base path list.
     *
     * @param paths The list of paths.
     * @return The reference to the base path list.
     * @throws GroovyEnvironmentExeption
     */
    private boolean checkDirectoryList(String[] paths) throws GroovyEnvironmentException {
        for (String path : paths) {
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
            for (File currentFile : currentFiles) {
                if (!findFile(oldFiles, currentFile)) {
                    return true;
                }
            }
            for (File oldFile : oldFiles) {
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
        for (File file : fileList) {
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
            directoryCache.clear();
            for (String groovyLibPath : libDirectories) {
                File[] files = new File(groovyLibPath).listFiles();
                directoryCache.put(groovyLibPath, files);
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
            throw new GroovyEnvironmentException("Failed to generate a groovy lib path : " + ex.getMessage());
        }
    }

    /**
     * This method builds a path using a path and a sub path.
     * @param path The path
     * @param subpath The sub path.
     * @return The list of directories.
     * @throws GroovyEnvironmentException
     */
    private List<String> generateScriptDirectories(String path, String subpath) throws GroovyEnvironmentException {
        return generateScriptDirectories(new File(path, subpath));
    }

    /**
     * This method takes a string and generates a path.
     *
     * @param path The path to check.
     * @return The list of script directories.
     * @throws GroovyEnvironmentException
     */
    private List<String> generateScriptDirectories(String path) throws GroovyEnvironmentException {
        return generateScriptDirectories(new File(path));
    }

    /**
     * This method generates the script directories.
     *
     * @param basePaths The list of base paths that contains script directories.
     * @return The list of paths.
     * @throws GroovyEnvironmentException
     */
    private List<String> generateScriptDirectories(File path) throws GroovyEnvironmentException {
        try {
            List<String> resultPaths = new ArrayList<String>();
            for (String subdir : subdirs) {
                File subdirectory = new File(path, subdir);
                if (!subdirectory.isDirectory()) {
                    continue;
                }
                resultPaths.add(subdirectory.getPath());
            }
            return resultPaths;
        } catch (Throwable ex) {
            log.error("Failed to generate the script paths : " + ex.getMessage(), ex);
            throw new GroovyEnvironmentException("Failed to generate the script paths : " + ex.getMessage(), ex);
        }
    }
}
