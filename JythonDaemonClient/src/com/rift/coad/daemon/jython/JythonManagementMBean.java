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
package com.rift.coad.daemon.jython;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;


/**
 * This is the management bean interface.
 * 
 * @author brett chaldecott
 */
public interface JythonManagementMBean extends Remote {
    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of jython manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this jython manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of jython manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this jython manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the jython manager.
     *
     * @return The string containing the description of the jython manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of jython manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this jython manager implementation.")
    public String getDescription() throws RemoteException;
    

    /**
     * This method is called when a user wants to run a stored script. It will 
     * then return the requested value.
     *
     * @param name This is the name of the script that a user wishes to run. 
     * Please note that on a windows installation this will need to include 
     * the file extension ".py".
     * @return The string containing the result
     */
    @MethodInfo(description="This method runs the script and returns the output from it.")
    @Version(number="1.0")
    @Result(description="The string containing the output from the script.")
    public String runScript(@ParamInfo(name="name",
            description="The name of the script to run.")String name) 
            throws RemoteException, JythonDaemonException;
    
    
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
    @MethodInfo(description="This method runs the script and returns the output from it.")
    @Version(number="1.0")
    @Result(description="The string containing the output from the script.")
    public String runScript(@ParamInfo(name="name",
            description="The name of the script to run.")String name,
            @ParamInfo(name="argument",
            description="The string argument to the script.")String argument,
            @ParamInfo(name="argumentValue",
            description="The string argumentValue to the script.")String argumentValue)
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
    @MethodInfo(description="This method registers the script so that it can be run.")
    @Version(number="1.0")
    public void registerScript(@ParamInfo(name="file",
            description="The contents of the file to register.")String file,
            @ParamInfo(name="name",
            description="The name of the script to register.")String name) 
            throws RemoteException, JythonDaemonException;
    
    
    /**
     * This method returns a list of scripts.
     *
     * @return An array of script names.
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    @MethodInfo(description="This method returns a list of all the registered scripts.")
    @Version(number="1.0")
    @Result(description="The list of registered scripts.")
    public List listScripts() throws RemoteException, JythonDaemonException;
    
    
    /**
     * This method retrieves the specified script
     *
     * @return A string containing the script value
     * @param name The name of the script to retrieve
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    @MethodInfo(description="This method retrieves the specified script.")
    @Version(number="1.0")
    @Result(description="The string containing the script value.")
    public String getScript(@ParamInfo(name="name",
            description="The name of the script to retrieve.")String name)
            throws RemoteException, JythonDaemonException;
    
    
    /**
     * This method will remove the script with the given name.
     *
     * @param name The name of the script to remove
     * @throws RemoteException
     * @throws JythonDaemonException
     */
    @MethodInfo(description="This method removes the specified script.")
    @Version(number="1.0")
    public void removeScript(@ParamInfo(name="name",
            description="The name of the script to remove.")String name)
            throws RemoteException, JythonDaemonException;
}
