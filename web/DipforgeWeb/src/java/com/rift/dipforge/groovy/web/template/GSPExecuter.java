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
package com.rift.dipforge.groovy.web.template;

import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyClassLoader;
import com.rift.dipforge.groovy.lib.reflect.GroovyReflectionUtil;
import com.rift.dipforge.groovy.lib.servlet.DipforgeServlet;
import com.rift.dipforge.groovy.web.template.GSPEnvironmentException;
import groovy.lang.Closure;
import groovy.servlet.AbstractHttpServlet;
import groovy.servlet.ServletBinding;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
public class GSPExecuter {
    
    // class constants
    private final static String GSP_INIT = "gsp_init";
    private final static String DEFAULT_GSP_INIT = "dipforge/views/init.gsp";
    private final static String ENABLE_TEMPLATE_CACHE = "enable_template_cache";
    private final static boolean DEFAULT_ENABLE_TEMPLATE_CACHE = false;
    
    /**
     * A simple cache template original copied from the groovy source but modified
     * to fit the dipforge requirements.
     */
    private class TemplateCacheEntry {

        private Date date;
        private long hit;
        private long lastModified;
        private long length;
        private Object template;

        public TemplateCacheEntry(File file, Object template) {
            this(file, template, false); // don't get time millis for sake of speed
        }

        public TemplateCacheEntry(File file, Object template, boolean timestamp) {
            if (timestamp) {
                this.date = new Date(System.currentTimeMillis());
            } else {
                this.date = null;
            }
            this.hit = 0;
            this.lastModified = file.lastModified();
            this.length = file.length();
            this.template = template;
        }

        /**
         * Checks the passed file attributes against those cached ones.
         *
         * @param file Other file handle to compare to the cached values.
         * @return <code>true</code> if all measured values match, else <code>false</code>
         */
        public boolean validate(File file) {
            if (file.lastModified() != this.lastModified) {
                return false;
            }
            if (file.length() != this.length) {
                return false;
            }
            hit++;
            return true;
        }

        /**
         * Retrieve the template information
         * 
         * @return
         */
        public Object getTemplate() {
            return template;
        }

        /**
         * The to string operator
         *
         * @return The to string operator.
         */
        public String toString() {
            if (date == null) {
                return "Hit #" + hit;
            }
            return "Hit #" + hit + " since " + date;
        }
    }
    
    
    // class singletons
    private Logger log = Logger.getLogger(GSPExecuter.class);
    // private member variables
    private ContextInfo context;
    private String dipLibPath;
    private String basePath;
    private String webDir;
    private String libDir;
    private String initPath;
    private String[] subdirs;
    private String[] libsdir;
    private Object groovyShellRef;
    private Object templateEngine;
    private boolean enableTemplateCache;
    private GroovyClassLoader classLoader;
    private Map<String, File[]> directoryCache = new HashMap<String, File[]>();
    private Map<String, TemplateCacheEntry> templateCache = new HashMap<String, TemplateCacheEntry>();

    /**
     * The constructor of the groovy script engine.
     *
     * @param groovyScriptEngine The script engine.
     * @param classLoader The class loader.
     */
    public GSPExecuter(ContextInfo context, String dipLibPath, String basePath, String webDir,
            String libDir, String[] subdirs, String[] libsdir) throws GSPEnvironmentException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    GSPExecuter.class);
            
            this.context = context;
            this.dipLibPath = dipLibPath;
            this.basePath = basePath;
            this.webDir = webDir;
            this.libDir = libDir;
            this.subdirs = subdirs;
            this.libsdir = libsdir;
            this.initPath = config.getString(GSP_INIT, DEFAULT_GSP_INIT);
            this.enableTemplateCache = config.getBoolean(ENABLE_TEMPLATE_CACHE, 
                    DEFAULT_ENABLE_TEMPLATE_CACHE);
            initTemplateEngineScriptEngine();
        } catch (GSPEnvironmentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new GSPEnvironmentException("Failed to init the GSP executer : " +
                    ex.getMessage(),ex);
        }
    }

    /**
     * This method is called to execute a script on behalf of a servlet.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @param servlet The servlet.
     * @throws GSPEnvironmentException
     */
    public void executeGSP(HttpServletRequest request,
            HttpServletResponse response, DipforgeServlet servlet)
            throws GSPEnvironmentException, java.io.IOException {
        // Get the gsp path from the request - include aware (GROOVY-815)
        String gspUri = this.context.stripContext(servlet.getScriptUri(request));

        // Run the script
        ServletContext servletContext = servlet.getServletContext();
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {

            File templatePath = generateFullPath(gspUri);

            Object template = getTemplate(templatePath);

            // Set up the script context
            Class bindingClass = classLoader.loadClass("groovy.servlet.ServletBinding");
            Class requestClass = classLoader.loadClass("javax.servlet.http.HttpServletRequest");
            Class responseClass = classLoader.loadClass("javax.servlet.http.HttpServletResponse");
            Class servletContextClass = classLoader.loadClass("javax.servlet.ServletContext");
            Constructor bindingConstructor = bindingClass.getConstructor(
                    requestClass, responseClass, servletContextClass);
            Object binding = bindingConstructor.newInstance(request, response, servletContext);


            //
            // Prepare the response buffer content type _before_ getting the writer.
            // and set status code to ok
            //
            response.setContentType(servlet.CONTENT_TYPE_TEXT_HTML
                    + "; charset=" + servlet.getEncoding());
            response.setStatus(HttpServletResponse.SC_OK);


            // setup the output stream
            Method getVariable = binding.getClass().getMethod("getVariable", String.class);
            Writer out = (Writer) getVariable.invoke(binding, "out");
            if (out == null) {
                out = response.getWriter();
            }


            // retrieve the variables from the binding
            Method getVariables = binding.getClass().getMethod("getVariables");
            Object variables = getVariables.invoke(binding);

            // retrieve the groovy executer
            Class groovyExecuterClass = classLoader.loadClass("groovy.text.GroovyTemplateExecuter");
            Constructor groovyExecuterConstructor = groovyExecuterClass.
                    getConstructor(classLoader.loadClass("groovy.text.Template"), Writer.class);
            Object groovyExecuter = groovyExecuterConstructor.newInstance(template,out);

            Method groovyMakeExecute = groovyExecuter.getClass().getMethod("make", Map.class);
            groovyMakeExecute.invoke(groovyExecuter, variables);

            StringBuffer sb = new StringBuffer(100);
            sb.append("\n<!-- Generated by GSP TemplateServlet -->\n");
            out.write(sb.toString());

        } catch (InvocationTargetException exception) {
            Throwable runtimeException = exception.getTargetException();
            StringBuffer error = new StringBuffer("GSP Servlet Error: ");
            error.append(" script: '");
            error.append(gspUri);
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
                    error.append(" GSP not found, sending 404.");
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
            StringBuffer error = new StringBuffer("GSP Servlet Error: ");
            error.append(" script: '");
            error.append(gspUri);
            error.append("': ");
            Throwable e = runtimeException.getCause();
            /*
             * Null cause?!
             */
            if (e == null) {
                error.append(" GSP processing failed.");
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
     * @throws GSPEnvironmentException
     */
    private void initTemplateEngineScriptEngine() throws GSPEnvironmentException {
        //ClassLoader current = Thread.currentThread().getContextClassLoader();
        ClassLoader current = this.getClass().getClassLoader();
        try {
            directoryCache.clear();
            List<URL> paths = generateGroovyLibPath(libsdir);
            paths.addAll(generateGroovyLibPath(basePath + File.separator +
                    context.getPath() + File.separator + this.libDir));
            paths.addAll(generateSourceDirectories(basePath, context.getPath()));
            paths.addAll(generateSourceDirectories(this.dipLibPath));

            log.info("Setup the groovy class loader up with the following path [" + paths.toString() + "]");
            classLoader = new GroovyClassLoader(paths.toArray(new URL[0]),
                    current);
            
            classLoader.clearAssertionStatus();
            Thread.currentThread().setContextClassLoader(classLoader);
            
            // setup the groovy shell so that class dependancies will be recompiled if they change
            Class groovyShell = classLoader.loadClass("groovy.lang.GroovyShell");
            Constructor groovyShellConstructor = groovyShell.getConstructor();
            groovyShellRef = groovyShellConstructor.newInstance();
            Method method = groovyShellRef.getClass().getMethod("getClassLoader");
            Object groovyClassLoader = method.invoke(groovyShellRef);
            method = GroovyReflectionUtil.getMethod(groovyClassLoader, "setShouldRecompile", Boolean.class);
            if (method != null) {
                log.info("##### Set should recompile to true");
                method.invoke(groovyClassLoader, new Boolean(true));
            } else {
                log.error("##### Failed to set the should recompile flag");
            }
            
            // init the script environment
            Class ref = classLoader.loadClass("groovy.text.SimpleTemplateEngine");
            Constructor constructor = ref.getConstructor(groovyShell);
            templateEngine = constructor.newInstance(groovyShellRef);


        } catch (GSPEnvironmentException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to instanticte the groovy environment : " + ex.getMessage(), ex);
            throw new GSPEnvironmentException(
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
    public boolean checkForChanges() throws GSPEnvironmentException {
        return checkDirectoryList(libsdir);
    }

    /**
     * This method loops through the base path list.
     *
     * @param paths The list of paths.
     * @return The reference to the base path list.
     * @throws GroovyEnvironmentExeption
     */
    private boolean checkDirectoryList(String[] paths) throws GSPEnvironmentException {
        for (String path : paths) {
            File directory = new File(path);
            if (!directory.isDirectory()) {
                log.info("The directory path [" + path + "] is invalid.");
                throw new GSPEnvironmentException("The directory path [" + path + "] is invalid.");
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
     * This method returns the generated groovy lib path for the specified lib directory within the
     * context of this gsp servlet.
     *
     * @param libDirectory The lib directory.
     * @return The
     * @throws GSPEnvironmentException
     */
    private List<URL> generateGroovyLibPath(String libDirectory) throws GSPEnvironmentException {
        if (new File(libDirectory).isDirectory()) {
            return generateGroovyLibPath(new String[] {libDirectory});
        }
        return new ArrayList<URL>();
    }


    /**
     * This method returns a directory contain the groovy lib paths.
     *
     * @param groovyLibPath The file path.
     * @return The generated path.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    private List<URL> generateGroovyLibPath(String[] libDirectories) throws GSPEnvironmentException {
        try {
            List<URL> paths = new ArrayList<URL>();
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
            throw new GSPEnvironmentException("Failed to generate a groovy lib path : " + ex.getMessage());
        }
    }

    /**
     * This method builds a path using a path and a sub path.
     * @param path The path
     * @param subpath The sub path.
     * @return The list of directories.
     * @throws GSPEnvironmentException
     */
    private List<URL> generateSourceDirectories(String path, String subpath) throws GSPEnvironmentException {
        return generateSourceDirectories(new File(path, subpath));
    }

    /**
     * This method takes a string and generates a path.
     *
     * @param path The path to check.
     * @return The list of script directories.
     * @throws GSPEnvironmentException
     */
    private List<URL> generateSourceDirectories(String path) throws GSPEnvironmentException {
        return generateSourceDirectories(new File(path));
    }

    /**
     * This method generates the source directories.
     *
     * @param basePaths The list of base paths that contains script directories.
     * @return The list of paths.
     * @throws GSPEnvironmentException
     */
    private List<URL> generateSourceDirectories(File path) throws GSPEnvironmentException {
        try {
            List<URL> resultPaths = new ArrayList<URL>();
            for (String subdir : subdirs) {
                File subdirectory = new File(path, subdir);
                if (!subdirectory.isDirectory()) {
                    continue;
                }
                resultPaths.add(subdirectory.toURI().toURL());
            }
            return resultPaths;
        } catch (Throwable ex) {
            log.error("Failed to generate the source paths : " + ex.getMessage(), ex);
            throw new GSPEnvironmentException("Failed to source the script paths : " + ex.getMessage(), ex);
        }
    }

    /**
     * This method generates the appropriate relative path to the context and sub directory.
     *
     * @param path The path to the file.
     * @return The relative path.
     * @throws GSPEnvironmentException
     */
    private File generateFullPath(String relativePath) throws GSPEnvironmentException {
        File path = new File(basePath + File.separator + this.context.getPath()
                + File.separator + webDir + File.separator + relativePath);
        if (path.isFile()) {
            return path;
        }
        // fall back to the master lib directory
        path = new File(this.dipLibPath + File.separator + webDir + File.separator + relativePath);
        if (path.isFile()) {
            return path;
        }
        throw new GSPEnvironmentException(
                "Failed to generate the full path relative file ["
                + relativePath + "] was not found [" + path.getPath() + "]");

    }

    /**
     * This method is responsible for getting the template
     * @param templatePath
     * @return The reference to the template
     * @throws GSPEnvironmentException
     */
    private Object getTemplate(File templatePath) throws GSPEnvironmentException {
        try {
            if (enableTemplateCache && 
                    this.templateCache.containsKey(templatePath.getPath())) {
                TemplateCacheEntry entry = this.templateCache.get(templatePath.getPath());
                if (entry.validate(templatePath)) {
                    return entry.getTemplate();
                }
            }
            // prepend the dipforge init script
            File initTemplateFile = new File(this.basePath,this.initPath);
            StringBuilder templateSource = new StringBuilder();
            if (initTemplateFile.exists()) {
                log.info("Read in the template file :" + initTemplateFile.getPath());
                FileInputStream in = new FileInputStream(initTemplateFile);
                byte[] buffer = new byte[(int)initTemplateFile.length()];
                in.read(buffer);
                templateSource.append(new String(buffer));
            } else {
                log.info("#### Template file [" + initTemplateFile.getPath() + 
                        "] does not exist");
            }
            // read in the template
            FileInputStream in = new FileInputStream(templatePath);
            byte[] buffer = new byte[(int)templatePath.length()];
            in.read(buffer);
            templateSource.append(new String(buffer));
            
            Method createTemplateMethod = templateEngine.getClass().getMethod("createTemplate", String.class);
            Object template = createTemplateMethod.invoke(templateEngine, templateSource.toString());
            if (this.enableTemplateCache) {
                this.templateCache.put(templatePath.getPath(), new TemplateCacheEntry(templatePath, template));
            }
            return template;
        } catch (Exception ex) {
            log.error("Failed to retrieve the template for the file ["
                    + templatePath.getPath() + "] because : " + ex.getMessage(), ex);
            throw new GSPEnvironmentException("Failed to retrieve the template for the file ["
                    + templatePath.getPath() + "] because : " + ex.getMessage(), ex);
        }
    }
}
