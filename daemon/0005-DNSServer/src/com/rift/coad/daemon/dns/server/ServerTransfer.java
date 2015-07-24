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
 * ServerTransferQueue.java
 */

// package path
package com.rift.coad.daemon.dns.server;

/**
 * This object represents the interface for the server transfer object.
 *
 * @author brett chaldecott
 */
public interface ServerTransfer extends Comparable {
    
    /**
     * This method retrieves the zone name managed by this server object.
     *
     * @return The zone object.
     */
    public String getZoneName();
    
    
    /**
     * This method returns true if the zone requires a refresh.
     *
     * @return TRUE if the zone requires a refresh.
     */
    public boolean requiresRefresh();
    
    
    /**
     * This method returns true if the object has expired.
     *
     * @return TRUE if the zone has expired.
     */
    public boolean isExpired();
    
    
    /**
     * This method gets called to perform the transfer of the zone.
     *
     * @exception ServerException
     */
    public void performTransfer() throws ServerException;
    
    
    /**
     * This method returns the time until the zone expires.
     */
    public long getTimeUntilRefresh();
}
