/*
 * JythonDaemonClient: The client libraries for the jython data mapper.
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
 * JythonMappingManagerMBean.java
 */

// package path
package com.rift.coad.daemon.jython;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;


/**
 * This interface defines the methods that are responsible for managing the groovy mappings.
 *
 * @author brett chaldecott
 */
public interface JythonMappingManagerMBean extends Remote {

    /**
     * This method returns the version information for the mapping manager.
     *
     * @return The string containing the version information.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the version of mapping manager")
    @Version(number="1.0")
    @Result(description="The string containing the version of this mapping manager")
    public String getVersion() throws RemoteException;


    /**
     * This method returns the name of the
     *
     * @return This method returns the name of this daemon.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the name of mapping manager implementation")
    @Version(number="1.0")
    @Result(description="The string containing the name of this mapping manager implementation")
    public String getName() throws RemoteException;


    /**
     * This method returns the description of the mbean mapping manager.
     *
     * @return The string containing the description of the mapping manager.
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="Returns the description of mapping manager implementation.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this mapping manager implementation.")
    public String getDescription() throws RemoteException;


    /**
     * This method returns the data mappings managed by this object.
     *
     * @return The xml containing the full data mappings.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method retrieves the data mappings for this daemon.")
    @Version(number="1.0")
    @Result(description="The string containing the rdf xml for the data mappings.")
    public String exportDataMappingsInXML() throws JythonDaemonException, RemoteException;


    /**
     * This method is called to import mappings from an xml file.
     *
     * @param xml The file to import the mappings from.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method imports the data mappings from XML.")
    @Version(number="1.0")
    @Result(description="This method imports the data mappings from xml")
    public void importDataMappingFromXML(
            @ParamInfo(name="xml",description="RDF xml for import")String xml)
             throws JythonDaemonException, RemoteException;

    
    /**
     * This method is called to delete a data mapping.
     * 
     * @param name The name of the data mapping to remove.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    public void deleteDataMapping(
            @ParamInfo(name="name",description="The name of the data mapping to remove")String name)
            throws JythonDaemonException, RemoteException;
}
