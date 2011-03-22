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
 * AuditLogger.java
 */

package com.rift.coad.audit.client.rdf;

// java imports
import com.rift.coad.audit.AuditTrailLogger;
import java.util.ArrayList;
import java.util.List;

// log4j object
import org.apache.log4j.Logger;

// coadunation message service
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;

// audit trail imports
import com.rift.coad.audit.AuditTrailLoggerAsync;
import com.rift.coad.audit.Constants;
import com.rift.coad.audit.dto.LogEntry;
import com.rift.coad.lib.security.SessionManager;


/**
 * This object is responsible for acting as the audit trail entry.
 *
 * @author brett chaldecott
 */
public class AuditTrail {
    // class singletons
    private static Logger log = Logger.getLogger(AuditLogger.class);

    // private member variables.
    private LogEntry entry = null;

    /**
     * This constructor of the audit trail object.
     *
     * @param source The source of audit trail.
     * @param request The request 
     */
    protected AuditTrail(String source, String request) {
        this.entry = new LogEntry();
        entry.setSource(source);
        entry.setRequest(request);
    }


    /**
     * This method sets the correlation id for the log entry.
     * @param correlationId
     * @return The string containing the correlation id.
     */
    public AuditTrail setCorrelationId(String correlationId) {
        this.entry.setCorrelationId(correlationId);
        return this;
    }


    /**
     * This method sets the exteranl id for the audit trail entry.
     * @param externalId The external id.
     * @return
     */
    public AuditTrail setExternalId(String externalId) {
        this.entry.setExternalId(externalId);
        return this;
    }

    /**
     * This method adds data item to the audit trail lgo entry.
     * @param data This data item to add.
     * @return The reference to the current audit trail item.
     */
    public AuditTrail addData(Object data) {
        this.entry.getAssociated().add(entry);
        return this;
    }

    /**
     * This method is called to deal with a complete audit trail event.
     */
    public void complete() {
        entry.setStatus(LogEntry.Status.COMPLETE.name());
        this.log();
    }


    /**
     * This method is called to mark an audit trail entry as info.
     */
    public void info() {
        entry.setStatus(LogEntry.Status.INFO.name());
        this.log();
    }


    /**
     * This method is called to mark an audit entry as failure.
     */
    public void failure() {
        entry.setStatus(LogEntry.Status.FAILURE.name());
        this.log();
    }


    /**
     * This method is called to mark an audit trail entry as a critical failure.
     */
    public void critical() {
        entry.setStatus(LogEntry.Status.CRITICAL_FAILURE.name());
        this.log();
    }


    /**
     * This method makes the audit log call.
     */
    private void log() {
        try {
            String user = "Unknown";
            try {
                 user = SessionManager.getInstance().getSession().getUser().getName();
            } catch (Exception ex) {
                log.info("Failed to get the user information : " + ex.getMessage(),ex);
            }
            entry.setUser(user);
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            AuditTrailLoggerAsync logger = (AuditTrailLoggerAsync)RPCMessageClient.createOneWay(
                    entry.getSource(), AuditTrailLogger.class, AuditTrailLoggerAsync.class, services, false);
            logger.logEvent(entry);
        } catch (Throwable ex) {
            log.error("Failed to log an event : " + ex.getMessage(),ex);
        }
    }
}
