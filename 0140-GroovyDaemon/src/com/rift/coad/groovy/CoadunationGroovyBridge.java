/*
 * GroovyDaemon: The coadunation groovy daemon
 * Copyright (C) 2010  Rift IT Contracting
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
 * CoadunationGroovyBridge.java
 */


package com.rift.coad.groovy;

// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports


/**
 * This class is designed to bridge the gap between Coadunation environment and
 * the groovy environment.
 *
 * @author brett chaldecott
 */
public class CoadunationGroovyBridge {

    // class singletons
    private static Logger log = Logger.getLogger(CoadunationGroovyBridge.class);

    // private member variables
    private URLClassLoader parent;
    private Object gse;


    /**
     * This constructor sets up the coadunation groovy bridge.
     *
     * @param dir The coadunation groovy bridge.
     * @throws com.rift.coad.groovy.BridgeException
     */
    public CoadunationGroovyBridge(String dir, String scriptBase) throws BridgeException {
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            List<URL> paths = generateGroovyLibPath(dir);
            parent = new CoadunationGroovyClassLoader(paths.toArray(new URL[0]),
                    current);

            parent.clearAssertionStatus();
            Thread.currentThread().setContextClassLoader(parent);
            Class ref = parent.loadClass("groovy.util.GroovyScriptEngine");
            String[] path = new String[] {scriptBase};
            Constructor constructor = ref.getConstructor(path.getClass(),ClassLoader.class);
            gse = constructor.newInstance(path,parent);
            // force the recompile of dependancy classes
            Method method = gse.getClass().getMethod("getGroovyClassLoader");
            Object groovyClassLoader = method.invoke(gse);
            method = getMethod(groovyClassLoader, "setShouldRecompile", boolean.class);
            if (method != null) {
                method.invoke(groovyClassLoader, true);
            } else {
                log.error("Failed to set the should recompile flag");
            }
        } catch (Exception ex) {
            log.error("Failed to instanticte the bridge : " + ex.getMessage(),ex);
            throw new BridgeException(
                    "Failed to instanticte the bridge : " + ex.getMessage(),ex);
        } finally {
            Thread.currentThread().setContextClassLoader(current);
        }
    }


    /**
     * This method invokes a script in the groovy environment and returns the result.
     *
     * @param script The script path.
     * @param parameters The parameters for the script.
     * @return The result.
     * @throws com.rift.coad.groovy.BridgeException
     */
    public Object executeScript(String script, DataType ... parameters) throws BridgeException {
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(parent);
            Object binding = parent.loadClass("groovy.lang.Binding").newInstance();
            // bind the parameters
            Method bindMethod = getMethod(binding,"setProperty", String.class,Object.class);
            for (DataType parameter : parameters) {
                log.info("Adding the bind parameter named : " + parameter.getDataName());
                bindMethod.invoke(binding, parameter.getDataName(),parameter);
            }
            
            Method run = gse.getClass().getMethod("run", String.class,binding.getClass());
            return run.invoke(gse, script,binding);
        } catch (Throwable ex) {
            log.error("Failed to execute the groovy script : " + ex.getMessage(),ex);
            throw new BridgeException
                    ("Failed to execute the groovy script : " + ex.getMessage());
        } finally {
            Thread.currentThread().setContextClassLoader(current);
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


    /**
     * This method returns the requested method.
     *
     * @param ref The reference to the object containing the method.
     * @param methodName
     * @return
     * @throws com.rift.coad.groovy.BridgeException
     */
    private Method getMethod(Object ref, String methodName, Class ... parameters) throws BridgeException {
        try {
            return ref.getClass().getMethod(methodName, parameters);
        } catch (Exception ex) {
            // ignore
        }
        for (Class classRef : ref.getClass().getClasses()) {
            try {
                return classRef.getMethod(methodName, parameters);
            } catch (Exception ex) {
                // ignore
            }
        }
        return null;
    }

}
