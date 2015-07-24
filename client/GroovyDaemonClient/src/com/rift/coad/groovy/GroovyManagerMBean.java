/*
 * GroovyDaemonClient: The client libraries for the groovy data mapper.
 * Copyright (C) 2009  2015 Burntjam
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
 * GroovyManagerMBean.java
 */

// package path
package com.rift.coad.groovy;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


// coadunation annotations
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.ParamInfo;

/**
 * The management daemon for groovy
 *
 * @author brett chaldecott
 */
public interface GroovyManagerMBean extends Remote {

    /**
     * This method returns the version information for the type manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of type manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this type manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of type manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this type manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the type manager.
     *
     * @return The string containing the description of the type manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of type manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this type manager implementation.")
    public String getDescription() throws RemoteException;



    /**
     * This method is called to execute the given script path.
     *
     * @param scriptPath The path to the script
     * @return The results of executing the script.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method executes the script identified by the name.")
    @Version(number="1.0")
    @Result(description="The string containing the result of the execution.")
    public String execute(
            @ParamInfo(name="project",description="The project the script is in.")String project,
            @ParamInfo(name="scriptPath",description="The path to the script.")String scriptPath)
            throws GroovyDaemonException, RemoteException;

    
    /**
     * This method is called to execute the script identified by the path supplying the xml
     * parameters.
     *
     * @param scriptPath The path to the script to excute.
     * @param xmlParameters The xml parameters to execute.
     * @return The xml result.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method executes the script identified by the name.")
    @Version(number="1.0")
    @Result(description="The string containing the result of the execution.")
    public String executeMethod(
            @ParamInfo(name="methodId",description="The id of the method to execute.")String methodId,
            @ParamInfo(name="xmlParameters",description="Parameters in xml.")String xmlParameters)
            throws GroovyDaemonException, RemoteException;
    
    
    /**
     * This method returns the stats information for the store.
     *
     * @return The string containing the stats information for this store.
     * @throws GroovyDaemonException
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the version of this daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the version")
    public String getStats() throws GroovyDaemonException, RemoteException;
}
