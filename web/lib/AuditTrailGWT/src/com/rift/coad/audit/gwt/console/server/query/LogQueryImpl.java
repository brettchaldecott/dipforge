/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
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
 * LogQueryImpl.java
 */

// package path
package com.rift.coad.audit.gwt.console.server.query;

// remove servlet exception
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// log4j imports
import org.apache.log4j.Logger;

// coaduantion imports
import com.rift.coad.audit.AuditTrailServer;
import com.rift.coad.audit.gwt.console.client.query.LogQuery;
import com.rift.coad.audit.gwt.console.client.query.QueryException;
import com.rift.coad.audit.gwt.console.client.query.dto.LogEntry;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.audit.AuditTrailFilter;
import com.rift.coad.web.utils.copy.BeanCopy;


/**
 * This object is responsible for performing the query on the log query object.
 *
 * @author brett chaldecott
 */
public class LogQueryImpl extends RemoteServiceServlet implements
        LogQuery {

    // class singletons
    private static Logger log = Logger.getLogger(LogQueryImpl.class);

    /**
     * The default constructor of the log query object.
     */
    public LogQueryImpl() {
    }



    public LogEntry[] myMethod(String hostname, String source, String user,
            String status, String correlationId, String externalId, int maxRows) throws QueryException {
        try {
            AuditTrailServer server = (AuditTrailServer)ConnectionManager.getInstance().
                    getConnection(AuditTrailServer.class, "audit/AuditTrailServer");
            com.rift.coad.audit.dto.LogEntry[] entries =
                    server.queryAuditTrail(new AuditTrailFilter("",source,
                    user,status,correlationId,externalId,maxRows)).toArray(
                    new com.rift.coad.audit.dto.LogEntry[0]);
            return (LogEntry[])BeanCopy.copyToArray(LogEntry.class,entries);
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of log entries: " + ex.getMessage(),ex);
            throw new QueryException
                ("Failed to retrieve the list of log entries : " + ex.getMessage());
        }
    }
    
    
}
