/*
 * JythonDaemonClient: The client libraries for the groovy data mapper.
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
 * JythonMappingManagerDaemon.java
 */


// package path
package com.rift.coad.daemon.jython;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// rdf imports
import com.rift.coad.datamapperbroker.rdf.DataMapperMethod;


/**
 * This interface represents the data mapping management interface.
 *
 * @author brett chaldecott
 */
public interface JythonMappingManagerDaemon extends Remote {
    
    /**
     * This method returns a list of all the data mapper methods.
     * 
     * @return The list of data mapper methods managed by this object.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    public List<DataMapperMethod> listMethods() throws JythonDaemonException, RemoteException;


    /**
     * This method adds the specified method to the data mapper.
     *
     * @param method This method adds a new data mapping.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    public void addMethod(DataMapperMethod method) throws JythonDaemonException, RemoteException;


    /**
     * This method updates the method data mapping.
     *
     * @param method The new data mapping for a method.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    public void updateMethod(DataMapperMethod method) throws JythonDaemonException, RemoteException;


    /**
     * This method is called to remove the method identified by the object.
     *
     * @param method The method to remove.
     * @throws com.rift.coad.daemon.jython.JythonDaemonException
     * @throws java.rmi.RemoteException
     */
    public void removeMethod(DataMapperMethod method) throws JythonDaemonException, RemoteException;

}
