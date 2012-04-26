/*
 * dipforge: Description
 * Copyright (C) Fri Apr 20 09:28:56 SAST 2012 owner 
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
 * RequestBrokerConnector.groovy
 */

package com.dipforge.request

// imports
import com.rift.coad.change.request.Request
import com.rift.coad.request.RequestInfo
import com.rift.coad.request.RequestBrokerDaemon
import com.rift.coad.request.RequestBrokerException
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.rdf.semantic.Session
import java.util.Date

/**
 * The request broker connector.
 * 
 * @author Brett Chaldecott
 */
class RequestBrokerConnector {

    RequestBrokerConnector() {
    }
    
    /**
     * Retrieve a connection to the broker.
     */
    def getBroker() {
        return (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,"request/RequestBrokerDaemon");
    }
    
    
    /**
     * This method returns true if the request is active
     */
    def getRequest(Request request) {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,"request/RequestBrokerDaemon")
            return daemon.getRequest(request.getId())
        } catch (RequestBrokerException ex) {
            if (ex.getMessage().contains("No such request")) {
                request.setStatus("COMPLETE")
                return request
            } else if (ex.getMessage().contains("does not exist")) {
                request.setStatus("STATUS UNKNOWN")
                return request
            }
        }
    }
}