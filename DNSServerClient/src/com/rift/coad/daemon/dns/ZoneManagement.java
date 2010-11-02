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

// package path
package com.rift.coad.daemon.dns;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This object is responsible for managing a zone object.
 *
 * @author brett chaldecott
 */
public interface ZoneManagement extends Remote {
    
    /**
     * This flag will return true if this zone is a primary.
     */
    public boolean isPrimary() throws RemoteException;
    
    
    /**
     * This method returns the name of the zone.
     *
     * @return The string containing the zone name.
     * @throw RemoteException
     */
    public String getName() throws RemoteException;
    
    
    /**
     * This method gets the host for the zone
     *
     * @return The string containing the host information.
     * @throws RemoteException
     */
    public SOARecord getSOA() throws DNSException, RemoteException;
    
    
    /**
     * This method updates the soa record.
     *
     * @param record The soa record.
     * @throws RemoteException
     */
    public void updateSOA(SOARecord record) throws DNSException, 
            RemoteException;
    
    
    /**
     * This method returns the list of records for the dns server.
     *
     * @return The list of records for this zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public List getRecords() throws DNSException, RemoteException;
    
    
    /**
     * This method is responsible for updating the records in the dns server.
     *
     * @param records The records to update.
     * @throws DNSException
     * @throws RemoteException
     */
    public void updateRecords(List records) throws DNSException, 
            RemoteException;
    
}
