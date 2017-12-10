/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.rdf.types.mapping.ParameterMapping;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ubuntu
 */
public class PythonEngineWrapper {
    
    // private member variables
    private static Logger log = Logger.getLogger(PythonEngineWrapper.class);

    // get the dipforge home directory
    private static String DIPFORGE_HOME = System.getenv("DIPFORGE_HOME");
    
    // private member variables
    private JepEnv jepEnv;
    private String project;
    private String includePath;
    private ClassLoader parent;
    private ClassLoader loader;
    
    protected PythonEngineWrapper(JepEnv jepEnv, String project, ClassLoader loader) throws PythonWrapperException {
        try {
            this.jepEnv = jepEnv;
            this.project = project;
            this.includePath = String.format("%s%s%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    project,
                    File.separator,
                    Constants.INCLUDE_PATH);
            this.includePath += File.pathSeparator;
            this.includePath += String.format("%s%s%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    project,
                    File.separator,
                    Constants.SERVICES_PATH);

            this.parent = Thread.currentThread().getContextClassLoader();
            this.loader = loader;
        } catch (Exception ex) {
            log.error("Failed to create the python engine wrapper : " + ex.getMessage(),ex);
            throw new PythonWrapperException
                    ("Failed to create the python engine wrapper : " + ex.getMessage(),ex);
        }
    }
    
    public Object execute(String script) throws PythonWrapperException {
        
        // reset the class loader
        Object jepObject = null;
        try {
            Thread.currentThread().setContextClassLoader(loader);
            
            Class classRef = loader.loadClass("jep.Jep");
            Constructor constructor = classRef.getConstructor(boolean.class,String.class,ClassLoader.class);
            jepObject = constructor.newInstance(false,this.includePath,loader);
            
            Method runScript = jepObject.getClass().getMethod("runScript",String.class);
            runScript.invoke(jepObject,script);
            
            Method getValue = jepObject.getClass().getMethod("getValue",String.class);
            return getValue.invoke(jepObject,"out");
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new PythonWrapperException("Failed to execute the script : " + ex.getMessage(),ex);
        } finally {
            closeObject(jepObject);
            Thread.currentThread().setContextClassLoader(parent);
        }
    }
    
    
    public Object execute(MethodMapping methodMapping, List<Object> values) throws PythonWrapperException {
        
        // reset the class loader
        Object jepObject = null;
        try {
            Thread.currentThread().setContextClassLoader(loader);
            
            Class classRef = loader.loadClass("jep.Jep");
            Constructor constructor = classRef.getConstructor(boolean.class,String.class,ClassLoader.class);
            jepObject = constructor.newInstance(false,this.includePath,loader);
            
            // seperate script into 
            
            Method eval = jepObject.getClass().getMethod("eval",String.class);
            eval.invoke(jepObject,String.format("import %s", methodMapping.getClassName()));
            
            Method set = jepObject.getClass().getMethod("set",String.class, Object.class);
            StringBuilder arguments = new StringBuilder();
            String argumentSep = "";
            for (int index = 0; index < methodMapping.getParameters().size(); index++) {
                ParameterMapping parameter = methodMapping.getParameters().get(index);
                Object value = values.get(index);
                arguments.append(parameter.getName()).append(argumentSep);
                set.invoke(jepObject,parameter.getName(),value);
                argumentSep = ",";
            }
            
            eval.invoke(jepObject,String.format("output = %s.%s(%s)", 
                    methodMapping.getClassName(),methodMapping.getMethodName(),
                    arguments.toString()));
            
            Method getValue = jepObject.getClass().getMethod("getValue",String.class);
            return getValue.invoke(jepObject,"output");
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new PythonWrapperException("Failed to execute the script : " + ex.getMessage(),ex);
        } finally {
            closeObject(jepObject);
            Thread.currentThread().setContextClassLoader(parent);
        }
    }
    
    
    private void closeObject(Object jepObject) {
        try {
            if (jepObject == null) {
                return;
            }
            Method method = jepObject.getClass().getMethod("close");
            method.invoke(jepObject);
        } catch (Exception ex) {
            log.error("Failed to execute the wrapper close : " + ex.getMessage(),ex);
        }
    }
}
