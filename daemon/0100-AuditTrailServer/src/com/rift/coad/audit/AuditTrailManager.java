/*
 * AuditTrail: The audit trail server
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
 * AuditTrailManager.java
 */


// package path
package com.rift.coad.audit;

// java imports
import com.rift.coad.audit.dto.LogEntry;
import java.rmi.RemoteException;
import java.util.List;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The implementation of the audit trail manager.
 *
 * @author brett chaldecott
 */
public class AuditTrailManager implements AuditTrailManagerMBean {

    private static Logger log = Logger.getLogger(AuditTrailManager.class);

    /**
     * This method returns the version information for the audit trail server.
     *
     * @return The string containing the version information.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the audit trail manager.
     *
     * @return The string containing the name of the audit trail manager.
     */
    public String getName() {
        return this.getClass().getName();
    }

    /**
     * This method returns the description of the audit trail manager.
     *
     * @return The string containing the description of the audit trail manager.
     */
    public String getDescription() {
        return "Audit trail manager";
    }

    /**
     * This method lists the hosts that have been registered with the audit trail manager.
     *
     * @return The list of hosts.
     * @throws com.rift.coad.audit.AuditTrailException
     */
    public List<String> listHosts() throws AuditTrailException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "java:comp/env/bean/audit/AuditTrailServer");
            return server.listHosts();
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of hosts : " + ex.getMessage(),ex);
            throw new AuditTrailException
                ("Failed to retrieve the list of hosts : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method lists the sourcs attached to this host.
     *
     * @param host The host that the source is attached to.
     * @return The list of sources.
     * @throws com.rift.coad.audit.AuditTrailException
     */
    public List<String> listSources(String host) throws AuditTrailException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "java:comp/env/bean/audit/AuditTrailServer");
            return server.listSources(host);
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of sources : " + ex.getMessage(),ex);
            throw new AuditTrailException
                ("Failed to retrieve the list of sources : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method performs a query on the audit trail logs.
     *
     * @param hostname The name of the host.
     * @param source The source the logs are attached to.
     * @param user The user the logs are attached to.
     * @param status The status to sought the information by.
     * @param correlationId The correlation id for the messages.
     * @param externalId The external reference for the message.
     * @param maxRows The max rows.
     * @return The result.
     * @throws com.rift.coad.audit.AuditTrailException
     */
    public List<LogEntry> query(String hostname, String source, String user, 
            String status, String correlationId, String externalId, int maxRows)
            throws AuditTrailException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "java:comp/env/bean/audit/AuditTrailServer");
            String statusValue = "";
            if (!status.equals("")) {
                statusValue = LogEntry.Status.valueOf(status).name();
            }
            return server.queryAuditTrail(new AuditTrailFilter(hostname,source,
                    user,statusValue,correlationId,externalId,maxRows));
        } catch (Exception ex) {
            log.error("Failed to perform the query : " + ex.getMessage(),ex);
            throw new AuditTrailException
                ("Failed to perform the query : " + ex.getMessage(),ex);
        }
    }

}
