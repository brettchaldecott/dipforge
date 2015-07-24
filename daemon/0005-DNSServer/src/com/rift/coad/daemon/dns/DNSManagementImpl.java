/*
 * DNSServer: The coadunation dns server implementation
 * Copyright (C) 2008  2015 Burntjam
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
 * DNSManagementImpl.java
 */

package com.rift.coad.daemon.dns;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// log import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.daemon.dns.server.Server;

/**
 * This is the dns management inteface.
 *
 * @author brett chaldecott
 */
public class DNSManagementImpl implements DNSManagement {
    
    // singleton object
    private static Logger log = Logger.getLogger(DNSManagementImpl.class);
    
    /**
     * The default constructor of the dns management server
     *
     * @throws DNSException
     */
    public DNSManagementImpl() throws DNSException {
        
    }
    
    
    /**
     * This method creates a new zone.
     *
     * @return The reference to the newly created zone.
     * @param zoneName The name of the zone to remove.
     * @throws DNSException
     */
    public ZoneManagement createZone(String zoneName, SOARecord soa, 
            List records) throws DNSException {
        try {
            return new ZoneManagementImpl(Server.getInstance().
                    createZone(zoneName),soa,records);
        } catch (Exception ex) {
            log.error("Failed to create a new zone : " + 
                    ex.getMessage(),ex);
            throw new DNSException("Failed to create a new zone : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
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
    public ZoneManagement createSecondaryZone(String zoneName, String remote) throws 
            DNSException {
        try {
            return new ZoneManagementImpl(Server.getInstance().
                    createSecondaryZone(zoneName,remote));
        } catch (Exception ex) {
            log.error("Failed to create a secondary zone : " + 
                    ex.getMessage(),ex);
            throw new DNSException("Failed to create a secondary zone : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method retrieves an existing zone.
     *
     * @return The reference to the zone.
     * @param zoneName The name of the zone to retrieve.
     * @throws DNSException
     */
    public ZoneManagement getZone(String zoneName) throws DNSException {
        try {
            return new ZoneManagementImpl(Server.getInstance().
                    getServerZone(zoneName));
        } catch (Exception ex) {
            log.error("Failed to get a zone : " + 
                    ex.getMessage(),ex);
            throw new DNSException("Failed to get a secondary zone : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method removes the specified zone.
     *
     * @param zoneName The name of the zone to remove.
     * @throws DNSException
     */
    public void removeZone(String zoneName) throws DNSException {
        try {
            Server.getInstance().removeZone(zoneName);
        } catch (Exception ex) {
            log.error("Failed to remove a zone : " + 
                    ex.getMessage(),ex);
            throw new DNSException("Failed to remove a zone : " + 
                    ex.getMessage(),ex);
        }
    }
}
