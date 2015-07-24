/*
 * DataMapperBrokerMBean: The data mapper broker client interface.
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
 * DataMapperBrokerDaemon.java
 */

package com.rift.coad.datamapperbroker;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

// data mapper method
import com.rift.coad.rdf.types.mapping.MethodMapping;
import java.util.List;

/**
 * This interface defines the methods that control the data mapper broker information.
 *
 * @author brett chaldecott
 */
public interface DataMapperBrokerDaemon extends Remote {
    
    
    /**
     * This method is responsible for registering the given methods against the specified service id.
     *
     * @param serviceId The service id.
     * @param methods
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    public void register(List<MethodMapping> methods) throws
            DataMapperBrokerException, RemoteException;

    
    /**
     * This method lists the jndi bindings this data mapper broker can
     * communicate with.
     * 
     * @return This method returns a list of JNDI bindings
     * @throws DataMapperBrokerException
     * @throws RemoteException 
     */
    public List<String> listJNDIBindings() throws
            DataMapperBrokerException, RemoteException;
    

    /**
     * This method returns the list of methods attached to a given service id.
     * 
     * @param serviceId The id of the service.
     * @return The array of data mappers.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    public List<MethodMapping> listMethods(String jndi) throws
            DataMapperBrokerException, RemoteException;

    
    /**
     * This method returns a list of 
     * @param jndi
     * @param project
     * @param className
     * @return
     * @throws DataMapperBrokerException
     * @throws RemoteException 
     */
    public List<MethodMapping> listMethods(String jndi,String project, 
            String className) throws DataMapperBrokerException, RemoteException;
    
    /**
     * This method returns a list of 
     * @param service The service name
     * @param project The project
     * @param className The class name
     * @return The list of methods.
     * @throws DataMapperBrokerException
     * @throws RemoteException 
     */
    public List<MethodMapping> listMethodsByService(String service,String project, 
            String className) throws DataMapperBrokerException, RemoteException;
    
    
    /**
     * This method returns a reference to the data mapper method.
     *
     * @param methodId The id of the method to retrieve.
     * @return The reference to the specified method.
     * @throws com.rift.coad.datamapperbroker.DataMapperBrokerException
     * @throws java.rmi.RemoteException
     */
    public MethodMapping getMethod(String methodId) throws
            DataMapperBrokerException, RemoteException;
}
