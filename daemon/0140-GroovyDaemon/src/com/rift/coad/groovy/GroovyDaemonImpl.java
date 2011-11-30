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
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
//import groovy.lang.Binding;
//import groovy.lang.Script;
//import groovy.util.GroovyScriptEngine;
//import groovy.lang.Binding;
//import groovy.util.GroovyScriptEngine;
import com.rift.dipforge.groovy.lib.ContextInfo;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentConstants;
import com.rift.dipforge.groovy.lib.GroovyEnvironmentManager;
import com.rift.dipforge.groovy.lib.GroovyExecuter;
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

    // singleton member variables
    private static Logger log = Logger.getLogger(GroovyDaemonImpl.class);

//    private GroovyScriptEngine gse;
//    private URLClassLoader loader;
    
    /**
     * The default constructor for the groovy daemon.
     */
    public GroovyDaemonImpl() throws GroovyDaemonException {
//        ClassLoader current = Thread.currentThread().getContextClassLoader();
        try {
            Configuration configuration = ConfigurationFactory.getInstance().getConfig(
                    GroovyDaemonImpl.class);

            GroovyEnvironmentManager.init(configuration.getString(
                    GroovyEnvironmentConstants.DIPFORGE_LIB_DIR),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_BASE),
                    configuration.getString(
                    GroovyEnvironmentConstants.LIB_DIR),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_SUB_DIRECTORIES).split(","),
                    configuration.getString(
                    GroovyEnvironmentConstants.ENVIRONMENT_LIBS_DIR).split(","));


        } catch (Exception ex) {
            log.error("Failed to log instanciate the groovy daemon : " +
                    ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to log instanciate the groovy daemon : " +
                    ex.getMessage(),ex);
        }
    }


    /**
     * This method executes the
     * @param scriptPath The path to the script.
     * @return The reference to the script.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     */
    public String execute(String project, String scriptPath) throws GroovyDaemonException {
        try {
            GroovyExecuter executer = GroovyEnvironmentManager.getInstance().getExecuter(
                    new ContextInfo(project));
            return executer.executeScript(scriptPath, new String[0], new String[0]).toString();
        } catch (Exception ex) {
            log.error("Failed to execute the script : " + ex.getMessage(),ex);
            throw new GroovyDaemonException
                    ("Failed to execute the script : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * 
     * @param methodId
     * @param input
     * @return
     * @throws GroovyDaemonException
     * @throws RemoteException 
     */
    public String executeMethod(String methodId, String input)
            throws GroovyDaemonException {
        return null;
    }

    
    
}
