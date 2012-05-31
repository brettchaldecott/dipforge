/*
 * AuditTrailConsole: The audit trail console for the audit trail.
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
 * LogQueryLookup.java
 */

package com.rift.coad.audit.gwt.console.client.query;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * The lookup tool for the query object.
 *
 * @author brett chaldecott
 */
public class LogQueryLookup {

    /**
     * This method is called to retrieve the service information
     *
     * @return The reference to the query object.
     */
    public static LogQueryAsync getService(){
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        LogQueryAsync service = (LogQueryAsync) GWT.create(LogQuery.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getHostPageBaseURL() + "query/logquery";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
