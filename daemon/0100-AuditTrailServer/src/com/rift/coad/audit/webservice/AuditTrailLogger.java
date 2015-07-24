/*
 * AuditTrail: The audit trail log object.
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
 * AuditLogger.java
 */


package com.rift.coad.audit.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This object represents the audit trail
 *
 * @author brett chaldecott
 */
public interface AuditTrailLogger extends Remote {
    /**
     * This method adds a new log entry.
     *
     * @param hostname The host that the log is coming from.
     * @param source The source of the log event.
     * @param user The name of the user.
     * @param status The status of the log.
     * @param correlationId The correlation id for the log entry.
     * @param externalId The external id for the log entry.
     * @param request The request id.
     * @throws com.rift.coad.audit.webservice.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public void log(String hostname,String source,String user,String status,
            String correlationId, String externalId, String request) throws AuditTrailException, RemoteException;
}
