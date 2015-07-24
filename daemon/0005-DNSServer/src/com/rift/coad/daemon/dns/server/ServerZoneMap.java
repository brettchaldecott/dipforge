/*
 * DNSServer: The dns server implementation.
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
 * ServerZoneMap.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// log4j imports
import org.apache.log4j.Logger;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.xbill.DNS.Name;
import org.xbill.DNS.Zone;

/**
 * This object is responsible for handling the zone map in memory.
 *
 * @author brett chaldecott
 */
public class ServerZoneMap {
    
    // class singleton
    private static Logger log = Logger.getLogger(ServerZoneMap.class);
    
    // private member variables
    private Map zones = new ConcurrentHashMap();
    
    /**
     * Creates a new instance of ServerZoneMap
     */
    public ServerZoneMap() {
        
    }
    
    
    /**
     * This method adds a server zone to the server zone map.
     */
    public void addZone(ServerZone zone) throws ServerException {
        try {
            Name origin = null;
            if (zone.getZoneName() != null) {
                origin = Name.fromString(zone.getZoneName(), Name.root);
            }
            if (zones.containsKey(origin)) {
                throw new ServerException("The zone [" + zone.getZoneName() +
                        "] is already in the zone list.");
            }
            zones.put(origin,zone);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add a zone : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a server zone to the server zone map.
     *
     * @param zone The name of the zone.
     * @exception ServerException
     */
    public void removeZone(String zoneName) throws ServerException {
        try {
            Name origin = Name.fromString(zoneName, Name.root);
            if (!zones.containsKey(origin)) {
                throw new ServerException("The zone [" + zoneName +
                        "] cannot be removed.");
            }
            zones.remove(origin);
        } catch (ServerException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to remove a zone : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method finds the best local zone, whether that be a master or slave
     * zone file. This is a recursive function call.
     *
     * @return The zone that best matches the query or null.
     * @param name The name to find.
     */
    public ServerZone findBestZone(Name name) {
        ServerZone foundZone = (ServerZone) zones.get(name);
        if (foundZone != null) {
            return foundZone;
        }
        // run through all the zone segments and find the matching entry
        if (name.labels() > 0) {
            return findBestZone(new Name(name, 1));
        }
        // found no matching zone on this server
        return null;
    }
    
    
    /**
     * This method finds the best local zone, whether that be a master or slave
     * zone file. This is a recursive function call.
     *
     * @return The zone that best matches the query or null.
     * @param name The name to find.
     * @exception ServerException
     */
    public ServerZone findBestZone(String name) throws ServerException {
        if (name == null) {
            return null;
        }
        try {
            return findBestZone(Name.fromString(name, Name.root));
        } catch (Exception ex) {
            log.error("Failed to find the best zone : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to find the best zone : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the exact zone
     *
     * @return The zone that best matches the query or null.
     * @param name The name to find.
     */
    public ServerZone getZone(Name name) {
        return (ServerZone) zones.get(name);
    }
    
    /**
     * This method returns the exact zone
     *
     * @return The zone that best matches the query or null.
     * @param name The name to find.
     */
    public ServerZone getZone(String name) throws ServerException {
        if (name == null) {
            return null;
        }
        try {
            return getZone(Name.fromString(name, Name.root));
        } catch (Exception ex) {
            log.error("Failed to find the zone : " + ex.getMessage(),ex);
            throw new ServerException(
                    "Failed to find the zone : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the status information for the zone map.
     */
    public String getStatus() {
        return "Total Zones : " + zones.size() + "\n";
    }
    
    
    /**
     * This method lists the zones managed by this server.
     *
     * @return The list of zones.
     * @param type The type of zones [1: Primary, 2: Secondary, 3: All]
     * @exception DNSException
     */
    public List listZones(int type) throws ServerException {
        List zoneList = new ArrayList();
        for (Iterator iter = zones.keySet().iterator(); iter.hasNext();) {
            ServerZone zone = (ServerZone)zones.get(iter.next());
            switch (type) {
                case 1:
                    if (zone.getConfig().getPrimary()) {
                        zoneList.add(zone.getZoneName());
                    }
                    break;
                case 2:
                    if (!zone.getConfig().getPrimary()) {
                        zoneList.add(zone.getZoneName());
                    }
                    break;
                default:
                    zoneList.add(zone.getZoneName());
                    break;
            }
        }
        return zoneList;
    }
}
