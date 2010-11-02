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
import com.rift.coad.datamapper.DataMapperException;
import com.rift.coad.rdf.objmapping.base.DataType;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The daemon that controls the groovy environment.
 *
 * @author brett chaldecott
 */
public interface GroovyDaemon extends Remote {


    /**
     * This method lists the scripts that are within the groovy daemons access.
     *
     * @return The list of scripts.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     * @throws java.rmi.RemoteException
     */
    public List<String> listScripts() throws GroovyDaemonException, RemoteException;
    
    
    /**
     * This method is called to execute the given script path.
     * 
     * @param scriptPath The path to the script
     * @return The results of executing the script.
     * @throws com.rift.coad.groovy.GroovyDaemonException
     * @throws java.rmi.RemoteException
     */
    public String execute(String scriptPath) throws GroovyDaemonException, RemoteException;
    

    /**
     * This method executes the
     *
     * @param scriptPath The path to the script.
     * @param parameters The parameter for the request.
     * @return The return result.
     * @throws com.rift.coad.datamapper.DataMapperException
     * @throws java.rmi.RemoteException
     */
    public DataType execute(String scriptPath, DataType[] parameters)
            throws GroovyDaemonException, RemoteException;


    

}
