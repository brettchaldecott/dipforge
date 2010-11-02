/*
 * AuditTrail: The audit trail log object.
 * Copyright (C) 2009  Rift IT Contracting
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


// package name space.
package com.rift.coad.audit.client;

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
import com.rift.coad.rdf.objmapping.audit.LogEntry;
import com.rift.coad.audit.Constants;
import com.rift.coad.lib.security.SessionManager;

/**
 * This object is responsbile for the audit log events
 *
 * @author brett chaldecott
 */
public class AuditLogger {

    // class singletons
    private static Logger log = Logger.getLogger(AuditLogger.class);

    // private member variables.
    private String source;

    /**
     * This constructor sets up the audit logger.
     *
     * @param source The source for all the log events.
     */
    private AuditLogger(String source) {
        this.source = source;
    }


    /**
     * This method returns a new instance of the logger using the string source value.
     *
     * @param source The string source value.
     * @return The audit trail logger object.
     */
    public static AuditLogger getLogger(String source) {
        return new AuditLogger(source);
    }


    /**
     * This method returns an audit logger that will log for the given source object.
     *
     * @param source The source package.
     * @return The audit trail object created by this call.
     */
    public static AuditLogger getLogger(Class source) {
        return new AuditLogger(source.getName());
    }


    /**
     * This method is called to addd an audit event for a completed task.
     *
     * @param correlationId The id for the correlation of audit entries.
     * @param externalId The external id.
     * @param format The format of the request information.
     * @param args The arguments to log agents this entry.
     */
    public void complete(String correlationId, String externalId, String format, String... args) {
        log(source, LogEntry.Status.COMPLETE,
            correlationId, externalId, String.format(format, (Object[])args));
    }


    /**
     * This method is called to add an audit entry of type info.
     *
     * @param correlationId The id for the correlation of audit entries.
     * @param externalId The external id.
     * @param format The format of the request information.
     * @param args The arguments to log agents this entry.
     */
    public void info(String correlationId, String externalId, String format, String... args) {
        log(source, LogEntry.Status.INFO,
            correlationId, externalId, String.format(format, (Object[])args));
    }


    /**
     * This method is called to add an audit entry of type failure.
     *
     * @param correlationId The id for the correlation of audit entries.
     * @param externalId The external id.
     * @param format The format of the request information.
     * @param args The arguments to log agents this entry.
     */
    public void failure(String correlationId, String externalId, String format, String... args) {
        log(source, LogEntry.Status.FAILURE,
            correlationId, externalId, String.format(format, (Object[])args));
    }


    /**
     * This method is called to add an audit entry of type critical.
     *
     * @param correlationId The id for the correlation of audit entries.
     * @param externalId The external id.
     * @param format The format of the request information.
     * @param args The arguments to log agents this entry.
     */
    public void critical(String correlationId, String externalId, String format, String... args) {
        log(source, LogEntry.Status.CRITICAL_FAILURE,
            correlationId, externalId, String.format(format, (Object[])args));
    }
    
    
    /**
     * This method makes the audit log call.
     * 
     * @param source The source for the call.
     * @param status The status of the log event.
     * @param correlationId The correlation id.
     * @param externalId The external id.
     * @param request The request id.
     */
    private void log(String source, LogEntry.Status status,
            String correlationId, String externalId, String request) {
        try {
            String user = "Unknown";
            try {
                user = SessionManager.getInstance().getSession().getUser().getName();
            } catch (Exception ex) {
                log.info("Failed to get the user information : " + ex.getMessage(),ex);
            }
            List<String> services = new ArrayList<String>();
            services.add(Constants.SERVICE);
            AuditTrailLoggerAsync logger = (AuditTrailLoggerAsync)RPCMessageClient.createOneWay(
                    source, AuditTrailLogger.class, AuditTrailLoggerAsync.class, services, false);
            logger.logEvent(new LogEntry(source,status.name(),user,
                    correlationId == null? "" : correlationId,
                    externalId == null? "" : externalId,request));
        } catch (Throwable ex) {
            log.error("Failed to log an event : " + ex.getMessage(),ex);
        }
    }
}


