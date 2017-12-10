/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.burntjam.dipforge.python.jep.engine;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author brett chaldecott
 */
public class PythonEngineManager {

    // singleton variables
    private static PythonEngineManager singleton = null;
    
    // member variables
    private JepEnv jepEnv;
    private Map<String,ClassLoader> projectLoaders = new ConcurrentHashMap<>();
    
    
    private PythonEngineManager() throws PythonWrapperException {
        jepEnv = new JepEnv();
    }
    
    
    public synchronized static PythonEngineManager getInstance() 
        throws PythonWrapperException {
        if (singleton == null) {
            singleton = new PythonEngineManager();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the project wrapper.
     * 
     * @param project
     * @return 
     */
    public synchronized PythonEngineWrapper getWrapper(String project) throws PythonWrapperException {
        try {
            ClassLoader projectLoader = projectLoaders.get(project);
            if (project == null) {
                URL[] urls = { (URL)(jepEnv.getJarPath().toURL())};
                projectLoader = new URLClassLoader(
                        urls,Thread.currentThread().getContextClassLoader());
                projectLoaders.put(project,projectLoader);
            }
            return new PythonEngineWrapper(jepEnv,project,projectLoader);
        } catch (Exception ex) {
            throw new PythonWrapperException("Failed to retrieve the wrapper : " + ex.getMessage(),ex);
        }
    }
}
