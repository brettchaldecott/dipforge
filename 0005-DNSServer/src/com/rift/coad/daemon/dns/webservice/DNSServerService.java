/*
 * DNSServer: The DNS server web service.
 * Copyright (C) 2008  Rift IT Contracting
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
 * DNSServerMBean.java
 */


// package path
package com.rift.coad.daemon.dns.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * This is the management interface for the dns server.
 *
 * @author brett chaldecott
 */
public interface DNSServerService extends Remote {
    
    /**
     * This method returns the version of the dns server.
     *
     * @return The string contain the version information.
     * @exception RemoteException
     */
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name ofe the dns server.
     *
     * @return The string containing the name of the dns server.
     * @exception RemoteException
     */
    public String getName() throws RemoteException;
    
    
    /**
     * This method the description of the server.
     *
     * @return The string containing the description of the dns server.
     * @exception RemoteException
     */
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns the string containing the status of the dns server.
     *
     * @return The string containing the status of the dns server.
     * @exception DNSException
     * @exception RemoteException
     */
    public String getStatus() throws DNSException, RemoteException;
    
    
    /**
     * This method lists the zones managed by this server.
     *
     * @return The list of zones.
     * @param type The type of zones [1: Primary, 2: Secondary, 3: All]
     * @exception DNSException
     * @exception RemoteException
     */
    public String[] listZones(int type) throws DNSException, RemoteException;
    
    
    /**
     * This method is responsible for creating a zone in the dns server
     *
     * @param zoneName The name of the new zone to create.
     * @param zone The zone contents.
     * @exception DNSException
     * @exception RemoteException
     */
    public void createZone(String zoneName, String zone) throws DNSException,
            RemoteException;
    
    
    /**
     * This method adds a secondary zone to the server.
     *
     * @param zoneName The name of the zone to create a secondary for.
     * @param remote The remote point for the name server to retrieve the zone 
     * form.
     *
     * @throws DNSException
     * @throws RemoteException
     */
    public void createSecondaryZone(String zoneName, String remote) throws 
            DNSException, RemoteException;
    
    
    /**
     * This method is responsible for getting a zone from the dns server
     *
     * @param zone The zone contents.
     * @param zoneName The name of the zone to retrieve.
     * @exception DNSException
     * @exception RemoteException
     */
    public String getZone(String zoneName) throws DNSException,
            RemoteException;
    
    
    /**
     * This method is responsible for updating a zone in the dns server
     *
     * @param zoneName The name of the zone to update.
     * @param zone The zone contents.
     * @exception DNSException
     * @exception RemoteException
     */
    public void updateZone(String zoneName, String zone) throws DNSException,
            RemoteException;
    
    
    /**
     * This method is responsible for removing a zone from the dns server
     *
     * @param zoneName The name of the zone to remove.
     * @exception DNSException
     * @exception RemoteException
     */
    public void removeZone(String zoneName) throws DNSException,
            RemoteException;
    
    
    
}
