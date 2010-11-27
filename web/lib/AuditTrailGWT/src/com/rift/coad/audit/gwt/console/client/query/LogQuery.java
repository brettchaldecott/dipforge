/*
 * AuditTrailConsole: The audit trail console.
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
 * LogQuery.java
 */

// package path
package com.rift.coad.audit.gwt.console.client.query;

// gwt imports
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.rdf.objmapping.client.audit.LogEntry;

/**
 * This interface defines the log query interface.
 *
 * @author brett chaldecott
 */
public interface LogQuery extends RemoteService {


    /**
     * This method returns a list of the log entries that match the query
     * information.
     *
     * @param hostname The hostname the query is being performed for.
     * @param source The source of the query.
     * @param user The user the query is attached to.
     * @param status The status of the query entry.
     * @param correlationId The correlation id.
     * @param externalId The external id.
     * @param maxRows The max rows.
     * @return The array of results.
     * @throws com.rift.coad.audit.gwt.console.client.query.QueryException
     */
    public LogEntry[] myMethod(String hostname, String source, String user,
            String status, String correlationId, String externalId, int maxRows) throws QueryException;
}
