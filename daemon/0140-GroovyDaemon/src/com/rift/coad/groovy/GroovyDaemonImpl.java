/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
 * Copyright (C) 2009  Rift IT Contracting
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
 * GroovyDaemon.java
 */


// package path
package com.rift.coad.groovy;

// java imports
import java.rmi.RemoteException;
import java.util.List;

// log 4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
//import groovy.lang.Binding;
//import groovy.lang.Script;
//import groovy.util.GroovyScriptEngine;
//import groovy.lang.Binding;
//import groovy.util.GroovyScriptEngine;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


/**
 * This object manages the groovy instance.
 *
 * @author brett chaldecott
 */
public class GroovyDaemonImpl implements GroovyDaemon {

    // class constants
    private static final String SCRIPT_BASE = "script_base";
    private static final String SCRIPT_SUFFIX = "groovy";
    private static final String GROOVY_LIB_DIR = "groovy_lib_dir";


    // singleton member variables
    private static Logger log = Logger.getLogger(GroovyDaemonImpl.class);

    // private member variables.
    private String path;
    private CoadunationGroovyBridge bridge;
//    private GroovyScriptEngine gse;
//    private URLClassLoader loader;
    
    /**
     * The default constructor for the groovy daemon.
     */
    public GroovyDaemonImpl() throws GroovyDaemonException {
//        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(GroovyDaemonImpl.class);
            path = config.getString(SCRIPT_BASE);
            bridge = new CoadunationGroovyBridge(config.getString(GROOVY_LIB_DIR),
                    path);
//            List<URL> urls = generateGroovyLibPath(config.getString(GROOVY_LIB_DIR));
//            System.out.println("###########################################################");
//            System.out.println(urls);
//            System.out.println("###########################################################");
//
//            loader = new URLClassLoader(urls.toArray(new URL[0]),
//                    Thread.currentThread().getContextClassLoader());
//
//            Thread.currentThread().setContextClassLoader(loader);
//            Class ref = loader.loadClass("groovy.util.GroovyScriptEngine");
//            String[] root = new String[] {path};
//            Constructor constructor = ref.getConstructor(root.getClass(),ClassLoader.class);
//            gse = (GroovyScriptEngine)constructor.newInstance(root,loader);
//            gse.getGroovyClassLoader().setShouldRecompile(true);
//

        } catch (Exception ex) {
            log.error("Failed to log instanciate the groovy daemon : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to log instanciate the groovy daemon : " +
                    ex.getMessage(),ex);
        }
//        finally {
//            Thread.currentThread().setContextClassLoader(current);
//        }
    }


    /**
     * This method lists the scripts.
     *
     * @return The list of scripts that can be executed.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public List<String> listScripts() throws GroovyDaemonException, RemoteException {
        return findScripts(new File(this.path), "");
    }


    /**
     * This method executes the
     * @param scriptPath The path to the script.
     * @return The reference to the script.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public String execute(String scriptPath) throws GroovyDaemonException {
//        ClassLoader current = Thread.currentThread().getContextClassLoader();
//        try {
//            Thread.currentThread().setContextClassLoader(loader);
//            Binding binding = new Binding();
//            return gse.run(scriptPath, binding).toString();
//        } catch (Throwable ex) {
//            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
//            throw new GroovyDaemonException
//                    ("Failed to execute the groovy script : " + ex.getMessage());
//        } finally {
//            Thread.currentThread().setContextClassLoader(current);
//        }
        try {
            return bridge.executeScript(scriptPath).toString();
        } catch (Throwable ex) {
            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to execute the groovy script : " + ex.getMessage());
        }
    }

    
    /**
     * This method is called to
     *
     * @param script The script to execute.
     * @param parameters The parameters to use.
     * @return The resulting data type.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public DataType execute(String scriptPath, DataType[] parameters) throws GroovyDaemonException {
//        ClassLoader current = Thread.currentThread().getContextClassLoader();
//        try {
//            Thread.currentThread().setContextClassLoader(loader);
//            Binding binding = new Binding();
//            for (DataType parameter : parameters) {
//                binding.setProperty(parameter.getDataName(), parameter);
//            }
//            return (DataType)gse.run(scriptPath, binding);
//        } catch (Exception ex) {
//            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
//            throw new GroovyDaemonException
//                    ("Failed to execute the groovy script : " + ex.getMessage());
//        } finally {
//            Thread.currentThread().setContextClassLoader(current);
//        }
        try {
            return (DataType)bridge.executeScript(scriptPath,parameters);
        } catch (Throwable ex) {
            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to execute the groovy script : " + ex.getMessage());
        }
    }


    /**
     * This method returns a list of all the scripts.
     *
     * @param path The path to the script scripts.
     * @param dir The current directory.
     * @return The result list.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    private List<String> findScripts(File path, String dir) throws GroovyDaemonException {
        try {
            File[] files = path.listFiles();
            List<String> result = new ArrayList<String>();
            for (File file : files) {
                if (file.isDirectory()) {
                    result.addAll(findScripts(file,dir +  file.getName() + File.separator));
                } else if (file.getName().endsWith(SCRIPT_SUFFIX)) {
                    result.add(dir + file.getName());
                }
            }
            return result;
        } catch (GroovyDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of downloads : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to retrieve the list of downloads : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a directory contain the groovy lib paths.
     *
     * @param groovyLibPath The file path.
     * @return The generated path.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    private List<URL> generateGroovyLibPath(String groovyLibPath) throws BridgeException {
        try {
            List<URL> paths = new ArrayList<URL>();
            File[] files = new File(groovyLibPath).listFiles();
            for (File file : files) {
                if (!file.isFile()) {
                    continue;
                }
                if (file.getName().endsWith("jar")) {
                    paths.add(file.toURI().toURL());
                }
            }
            return paths;
        } catch (Exception ex) {
            log.error("Failed to generate a groovy lib path : " + ex.getMessage());
            throw new BridgeException
                    ("Failed to generate a groovy lib path : " + ex.getMessage());
        }
    }
    
}
