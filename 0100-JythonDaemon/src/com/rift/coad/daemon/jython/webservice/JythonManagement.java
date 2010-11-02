/*
 * Jython Daemon: The jython client libraries.
 * Copyright (C) 2008 Rift IT Contracting
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
 * JythonDaemonException.java
 */

// package path
package com.rift.coad.daemon.jython.webservice;

// java imports
import java.rmi.RemoteException;


/**
 * This is the management bean interface.
 * 
 * @author brett chaldecott
 */
public interface JythonManagement {
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value.
     *
     * @param name This is the name of the script that a user wishes to run. 
     * Please note that on a windows installation this will need to include 
     * the file extension ".py".
     * @return The string containing the result
     */
    public String runScript(String name) throws RemoteException,
            JythonDaemonException;
    
    
    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value. A user can also specify value for 
     * variables within the script.
     *
     * @param name This is the name of the script that a user wishes to run. 
     * Please note that on a windows installation this will need to include 
     * the file extension ".py".
     * @param arguments The string value argument.
     * @param argumentValue The argument value.
     * @return Returns a value from the script.
     */
    public String runScript(String name,String argument,String argumentValue)
            throws RemoteException, JythonDaemonException;
    
    
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
    public void registerScript(String file,String name) 
            throws RemoteException, JythonDaemonException;
    
    
    /**
     * This method returns a list of scripts.
     *
     * @return An array of script names.
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public String[] listScripts() throws RemoteException, JythonDaemonException;
    
    
    /**
     * This method retrieves the specified script
     *
     * @return A string containing the script value
     * @param name The name of the script to retrieve
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public String getScript(String name) throws RemoteException,
            JythonDaemonException;
    
    
    /**
     * This method will remove the script with the given name.
     *
     * @param name The name of the script to remove
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    public void removeScript(String name) throws RemoteException,
            JythonDaemonException;
}
