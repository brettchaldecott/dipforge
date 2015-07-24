/*
 * AuditTrailConsole: The audit trail console for the audit trail.
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
 * LogQueryAsync.java
 */

// package path
package com.rift.coad.audit.gwt.console.client.query;

// call back object.
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This is the asynchronious interface for the log query object.
 *
 * @author brett chaldecott
 */
public interface LogQueryAsync {

    /**
     * The asynchronious version of the log query object.
     *
     * @param hostname The host name for the query.
     * @param source The source of the query.
     * @param user The users.
     * @param status The status.
     * @param correlationId
     * @param externalId The external id.
     * @param maxRows The max rows to return.
     * @param callback The callback id.
     */
    public void myMethod(String hostname, String source, String user,
            String status, String correlationId, String externalId, int maxRows,
            AsyncCallback callback);
    
}
