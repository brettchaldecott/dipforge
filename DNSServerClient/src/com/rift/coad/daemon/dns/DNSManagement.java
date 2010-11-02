/*
 * DNS: The dns server interface
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
 * DNSManagement.java
 */

package com.rift.coad.daemon.dns;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the dns management inteface.
 *
 * @author brett chaldecott
 */
public interface DNSManagement extends Remote {
    /**
     * This method creates a new zone.
     *
     * @return The reference to the newly created zone.
     * @param zoneName The name of the zone to remove.
     * @throws DNSException
     * @throws RemoteException
     */
    public ZoneManagement createZone(String zoneName, SOARecord soa, 
            List records) throws DNSException, RemoteException;
    
    
    /**
     * This method adds a secondary zone to the server.
     *
     * @return The newly created secondary zone.
     * @param zoneName The name of the zone to create a secondary for.
     * @param remote The remote point for the name server to retrieve the zone 
     * form.
     *
     * @throws DNSException
     * @throws RemoteException
     */
    public ZoneManagement createSecondaryZone(String zoneName, String remote) throws 
            DNSException, RemoteException;
    
    
    /**
     * This method retrieves an existing zone.
     *
     * @return The reference to the zone.
     * @param zoneName The name of the zone to retrieve.
     * @throws DNSException
     * @throws RemoteException
     */
    public ZoneManagement getZone(String zoneName) throws DNSException, 
            RemoteException;
    
    
    /**
     * This method removes the specified zone.
     *
     * @param zoneName The name of the zone to remove.
     * @throws DNSException
     * @throws RemoteException
     */
    public void removeZone(String zoneName) throws DNSException,
            RemoteException;
}
