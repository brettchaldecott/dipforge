/*
 * Jython: The jython daemon
 * Copyright (C) 2008  2015 Burntjam
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
 * JythonManagementImpl.java
 */

// package path
package com.rift.coad.daemon.jython.webservice;

// java imports
import com.rift.coad.daemon.jython.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// log4j imports
import org.apache.log4j.Logger;

// python imports
import org.python.util.PythonInterpreter;


/**
 * This object is responsible for managing the jython daemon.
 * 
 * @author brett chaldecott
 */
public class JythonManagementImpl implements JythonManagement {
    
    // class constants
    private final static String JYTHON_DAEMON = 
            "java:comp/env/bean/jython/Daemon";
    
    
    // private member variables
    private static Logger log = Logger.getLogger(JythonManagementImpl.class);
    private JythonDaemon jythonDaemon = null;
    private String scriptLocal = null;
    private String tmpLocation = null;
    
    /**
     * The constructor of the jython management object.
     */
    public JythonManagementImpl() throws JythonDaemonException{
        try {
            com.rift.coad.lib.configuration.Configuration coadConfig =
                    com.rift.coad.lib.configuration.ConfigurationFactory.
                    getInstance().getConfig(com.rift.coad.daemon.jython.
                    JythonDaemonImpl.class);
            System.setProperty("python.home",
                    coadConfig.getString("python_home"));
            scriptLocal = coadConfig.getString("script_location");
            tmpLocation = coadConfig.getString("coadunation_temp");
        } catch (Exception ex) {
            log.error("Failed to set jython properties :" +ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to set jython properties :" + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value.
     *
     * @param name This is the name of the script that a user wishes to run. 
     * Please note that on a windows installation this will need to include 
     * the file extension ".py".
     * @return The string containing the result
     */
    public String runScript(String name) throws JythonDaemonException {
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "] does not exist.");
                throw throwJythonDaemonException(
                        "The script file [" + name + "] does not exist.");
            }
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            inter.setOut(output);
            inter.setErr(output);
            inter.execfile(fis);
            return output.toString();
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve and run script :"+ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to retrieve and run script :" + ex,ex);
        }
    }

    
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value. A user can also specify value for 
     * variables within the script.
     *
     * @param name This is the name of the script that a user wishes to run. 
     * Please note that on a windows installation this will need to include 
     * the file extension ".py".
     * @param arguments The string value argument.
     * @return Returns a value from the script.
     */
    public String runScript(String name, String argument, String argumentValue) 
            throws 
            JythonDaemonException {
        try {
            File scriptFile = new File(scriptLocal, name);
            if (!scriptFile.exists() || !scriptFile.isFile()) {
                log.error("The script file [" + name + "] does not exist.");
                throw throwJythonDaemonException(
                        "The script file [" + name + "] does not exist.");
            }
            FileInputStream fis = new FileInputStream(scriptFile);
            PythonInterpreter inter = new PythonInterpreter();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            inter.setOut(output);
            inter.setErr(output);
            inter.set(argument,argumentValue);
            inter.execfile(fis);
            return output.toString();
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve and run script :"+ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to retrieve and run script :" + ex,ex);
        }
    }
    
    
    /**
     * This script is called in order to register a new script within 
     * Coadunation.
     *
     * @param script This is a string containing the script that will be 
     *          inserted in a python file.
     * @param name This is what the script will be called as well as what the 
     * python file will be named. Please note that on a windows installation 
     * this will need to include the file extension ".py".
     */
    public void registerScript(String file, String name) throws 
            JythonDaemonException {
        try {
            JythonDaemon jythonDaemon = getJythonDaemon();
            jythonDaemon.registerScript(file.getBytes(), name);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to register the script : " + ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to register the script : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list of scripts.
     *
     * @return An array of script names.
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public String[] listScripts() throws RemoteException, JythonDaemonException {
        try {
            JythonDaemon jythonDaemon = getJythonDaemon();
            return jythonDaemon.listScripts();
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to list the script : " + ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to register the script : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method will remove the script with the given name.
     *
     * @return The string containing the value of the script.
     * @param name The name of the script to remove
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public String getScript(String name) throws JythonDaemonException {
        try {
            JythonDaemon jythonDaemon = getJythonDaemon();
            return jythonDaemon.getScript(name);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to retrieve the scripts : " + ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to retrieve the scripts : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will remove the script with the given name.
     *
     * @param name The name of the script to remove
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public void removeScript(String name) throws JythonDaemonException {
        try {
            JythonDaemon jythonDaemon = getJythonDaemon();
            jythonDaemon.removeScript(name);
        } catch (JythonDaemonException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove the scripts : " + ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to remove the scripts : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the jython object.
     */
    private JythonDaemon getJythonDaemon() throws JythonDaemonException {
        try {
            if (jythonDaemon != null) {
                return jythonDaemon;
            }
            // resolve the local reference for the timer
            Context context = new InitialContext();
            jythonDaemon = (JythonDaemon)context.lookup(this.JYTHON_DAEMON);
            return jythonDaemon;
        } catch (Throwable ex) {
            log.error("Failed to connect to the jython object : " + 
                    ex.getMessage(),ex);
            throw throwJythonDaemonException(
                    "Failed to connect to the jython object : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @return The reference to the jython daemon exception
     * @param message The message to put in the exception
     */
    private JythonDaemonException throwJythonDaemonException(
            String message) {
        JythonDaemonException exception = new JythonDaemonException();
        exception.message = message;
        exception.cause = message;
        return exception;
    }
    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @return The reference to the jython daemon exception
     * @param message The message to put in the exception
     * @param ex The exception stack.
     */
    private JythonDaemonException throwJythonDaemonException(
            String message, Throwable ex) {
        JythonDaemonException exception = new JythonDaemonException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}
