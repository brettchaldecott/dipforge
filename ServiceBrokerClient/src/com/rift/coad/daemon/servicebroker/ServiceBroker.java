/*
 * Service Broker: The class responsible for supplying the service broker interface.
 * Copyright (C) 2006-2007  Rift IT Contracting
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
 * ServiceBroker.java
 */

package com.rift.coad.daemon.servicebroker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The purpose of this Daemon is to allow the registration of services within a
 * database which allows the JNDI's for those services to be retrieved.
 *
 * @author Glynn Chaldecott
 */
public interface ServiceBroker extends Remote {
    
    /**
     * The jndi url for the service broker.
     */
    public final static String JNDI_URL = "ServiceBroker";
    
    
    /**
     * This method is used to register a service and its JNDI on the database. 
     * It will also pass the service to it's parent.
     *
     * @param JNDI This is a String containing the JNDI of the service.
     * @param services This is a List containing Strings by which the daemon is 
     *          linked to the services it provides.
     */
    public void registerService(String JNDI, List services) throws 
            RemoteException, ServiceBrokerException;
    
    /**
     * This method is used to retrieve the JNDI for a service by searching for 
     * the supplied Strings within the database.
     *
     * @param services This is a List containing Strings by which the service
     *          can be identified.
     * @return Returns the necessary JNDI.
     */
    public String getServiceProvider(List services) throws RemoteException, 
            ServiceBrokerException;
    
    /**
     * This method is used to retrieve multiple JNDI values from the database.
     *
     * @param services This is a List containing Strings by which the service
     *          can be identified.
     * @return Returns a list of multiple JNDI's.
     */
    public List getServiceProviders(List services) throws RemoteException,
            ServiceBrokerException;
    
    /**
     * This method is used to remove a service from the database.
     *
     * @param JNDI This is a string containing the JNDI of the service you wish 
     *          to remove.
     * @param services This is a List of the services that are linked to that 
     *          JNDI.
     */
    public void removeServiceProviders(String JNDI, List services) throws 
            RemoteException, ServiceBrokerException;
    
}
