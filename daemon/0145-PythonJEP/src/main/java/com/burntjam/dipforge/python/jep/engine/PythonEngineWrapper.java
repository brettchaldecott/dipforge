/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

import com.rift.coad.rdf.types.mapping.MethodMapping;
import com.rift.coad.rdf.types.mapping.ParameterMapping;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.rdf.semantic.Query;
import com.rift.coad.rdf.semantic.Resource;
import com.rift.coad.rdf.semantic.SPARQLQuery;
import com.rift.coad.rdf.semantic.SPARQLResultRow;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.rdf.semantic.types.XSDDataDictionary;
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
    private final static String DIPFORGE_HOME = System.getenv("DIPFORGE_HOME");
    private final static String OUTPUT_VALUE = "output";
    
    // private member variables
    private JepEnv jepEnv;
    private String project;
    private String dipforgeBase;
    private String projectBase;
    private String includeBase;
    private String scriptBase;
    private String includePath;
    private ClassLoader parent;
    private ClassLoader loader;
    
    protected PythonEngineWrapper(JepEnv jepEnv, String project, ClassLoader loader) throws PythonWrapperException {
        try {
            this.jepEnv = jepEnv;
            this.project = project;
            this.projectBase = String.format("%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    project);
            this.includePath = this.includeBase = String.format("%s%s%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    project,
                    File.separator,
                    Constants.INCLUDE_PATH);
            this.includePath += File.pathSeparator;
            this.scriptBase = String.format("%s%s%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    project,
                    File.separator,
                    Constants.SERVICES_PATH);
            this.includePath += this.scriptBase;
            this.includePath += File.pathSeparator;
            this.dipforgeBase = String.format("%s%s%s%s%s%s%s",
                    DIPFORGE_HOME,
                    File.separator,
                    Constants.PROJECTS_DIR,
                    File.separator,
                    Constants.DIPFORGE_PROJECT,
                    File.separator,
                    Constants.INCLUDE_PATH);
            this.includePath += this.dipforgeBase;
            log.info("The include path is : " + this.includePath);
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
            String executeScript = this.projectBase + File.separator + script;
            log.info("Execute the script " + executeScript);
            runScript.invoke(jepObject,executeScript);
            
            Method getValue = jepObject.getClass().getMethod("getValue",String.class);
            Object result = getValue.invoke(jepObject,OUTPUT_VALUE);
            log.info("The result is : " + result.toString());
            return result;
        } catch (Throwable ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new PythonWrapperException("Failed to execute the script : " + ex.getMessage(),ex);
        } finally {
            closeObject(jepObject);
            Thread.currentThread().setContextClassLoader(parent);
        }
    }
    
    
    public Object execute(MethodMapping methodMapping, List<Object> parameters) throws PythonWrapperException {
        
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
                ParameterMapping parameterMapping = methodMapping.getParameters().get(index);
                Object parameter = parameters.get(index);
                Object value = null;
                if (parameter instanceof RequestData) {
                    value = ((RequestData) parameter).getData();
                } else {
                    value = parameter;
                }
                arguments.append(argumentSep).append(parameterMapping.getName());
                set.invoke(jepObject,parameterMapping.getName(),value);
                argumentSep = ",";
            }
            
            eval.invoke(jepObject,String.format(OUTPUT_VALUE + " = %s.%s(%s)", 
                    methodMapping.getClassName(),methodMapping.getMethodName(),
                    arguments.toString()));
            
            Method getValue = jepObject.getClass().getMethod("getValue",String.class);
            Object result = getValue.invoke(jepObject,OUTPUT_VALUE);
            
            if (!XSDDataDictionary.isBasicTypeByURI(methodMapping.getReturnType())) {
                result = getRequestData(result.toString(), methodMapping.getReturnType());
            }
            
            log.info("The result of the call is : " + result.toString());
            return result;
        } catch (Throwable ex) {
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
    
    
    private RequestData getRequestData(String data, String dataType) throws PythonWrapperException {
        try {
            Session sourceSession = XMLSemanticUtil.getSession();
            sourceSession.persist(data);
            SPARQLQuery query = sourceSession.createSPARQLQuery(
                    String.format("SELECT ?o WHERE { ?o a  <%s> . }", dataType)
            );
            List<SPARQLResultRow> rows = query.execute();
            if (rows.size() == 0) {
                return null;
            }
            Resource resource = rows.get(0).get(Resource.class,"o");
            String uri = resource.getURI().toString();
            int lastSlash = uri.lastIndexOf("/");
            int hashIndex = uri.lastIndexOf("#");
            String id = uri.substring(lastSlash + 1);
            String name = uri.substring(hashIndex,lastSlash);
            return new RequestData(id, dataType, data, name);
        } catch (Exception ex) {
            log.error("Failed to retrieve the request data : " + ex.getMessage(),ex);
            throw new PythonWrapperException
                    ("Failed to retrieve the request data : " + ex.getMessage(),ex);
        }
        
    }
}
