/*
 * AuditTrail: The audit trail log object.
 * Copyright (C) 2011  Rift IT Contracting
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
 * LogEntry.java
 */
package com.rift.coad.audit;

import com.rift.coad.audit.dto.LogEntry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author brett chaldecott
 */
public interface AuditTrailServer extends Remote {

    /**
     * This method returns a list of hosts that there are audit trail events for.
     * @return The list of audit trail hosts
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public List<String> listHosts() throws AuditTrailException, RemoteException;


    /**
     * This method lists the sources for the given host.
     *
     * @param host The list of hosts.
     * @return The method lists the sources
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public List<String> listSources(String host) throws AuditTrailException, RemoteException;

    
    /**
     * This object returns the list entries that match the audit trail filter.
     * @param filter The filter that the audit trail must match.
     * @return The list of entries.
     * @throws com.rift.coad.audit.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public List<LogEntry> queryAuditTrail(AuditTrailFilter filter) throws AuditTrailException, RemoteException;
}
