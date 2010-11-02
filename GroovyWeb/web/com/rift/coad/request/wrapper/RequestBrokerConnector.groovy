/*
 * Thu Feb 11 09:05:51 SAST 2010
 * RequestBrokerConnector.groovy
 * @author admin
 */

package com.rift.coad.request.wrapper

import com.rift.coad.rdf.objmapping.base.DataType
import com.rift.coad.change.rdf.objmapping.change.Request
import com.rift.coad.request.rdf.RequestInfo
import com.rift.coad.request.RequestBrokerDaemon
import com.rift.coad.request.RequestBrokerException
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.rdf.semantic.Session
import java.util.Date

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