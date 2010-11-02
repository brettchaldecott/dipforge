/*
 * AuditTrailServer: The audit trail server.
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
 * AuditTrailException.java
 */

// package path
package com.rift.coad.audit.webservice;

// java imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.util.connection.ConnectionManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;

// log4j imports
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * This is the implementation.
 *
 * @author brett chaldecott
 */
public class AuditTrailLoggerImpl implements AuditTrailLogger {

    // private member variables
    private static Logger log = Logger.getLogger(AuditTrailLoggerImpl.class);

    /**
     * The default constructor.
     */
    public AuditTrailLoggerImpl() {
    }


    /**
     * This method is called to log a new audit trail entry.
     * 
     * @param hostname The name of the host that the log event is from.
     * @param source The source for the log entry.
     * @param user The user for the log entry.
     * @param status The status of the log entry.
     * @param correlationId The correlation id for the log entry.
     * @param externalId The external reference for the log entry.
     * @param request The request identifying the log entry.
     * @throws com.rift.coad.audit.webservice.AuditTrailException
     * @throws java.rmi.RemoteException
     */
    public void log(String hostname, String source, String user, String status,
            String correlationId, String externalId, String request) throws
            AuditTrailException {
        try {
            com.rift.coad.audit.AuditTrailLogger logger = (com.rift.coad.audit.AuditTrailLogger)ConnectionManager.getInstance().
                    getConnection(com.rift.coad.audit.AuditTrailLogger.class, "java:comp/env/bean/audit/AuditTrailLogger");
            logger.logEvent(new com.rift.coad.rdf.objmapping.audit.LogEntry(
                    hostname, source, user, new Date(),status, correlationId, externalId, request,
                    new ArrayList<DataType>()));
        } catch (Exception ex) {
            log.error("Failed to log the audit trail event : " + ex.getMessage(),ex);
            throw throwAuditTrailException(
                    "Failed to log the audit trail event : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method wrapps the throwing of the rss client exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @return AuditTrailException
     */
    private AuditTrailException throwAuditTrailException(String message, Throwable ex) {
        AuditTrailException exception = new AuditTrailException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}
