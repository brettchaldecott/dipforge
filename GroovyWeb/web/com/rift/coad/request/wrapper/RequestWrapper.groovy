/*
 * Wed Feb 10 15:59:52 SAST 2010
 * RequestWrapper.groovy
 * @author brett chaldecott
 */

// package path
package com.rift.coad.request.wrapper

import com.rift.coad.rdf.objmapping.base.DataType
import com.rift.coad.change.rdf.objmapping.change.Request
import com.rift.coad.request.RequestBrokerDaemon
import com.rift.coad.request.RequestBrokerException
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.rdf.semantic.Session
import java.util.Date


/**
 * This object wrapps the calls onto the request object.
 */
class RequestWrapper {
    String action
    DataType dataType;
    DataType[] dependancies;
    
    /**
     * The request wrapper constructor.
     */
    RequestWrapper (String action, DataType dataType) {
        this.action = action
        this.dataType = dataType
        dependancies = new DataType[0]
    }
    
    
    /**
     * This constructor sets all the parameters.
     */
    RequestWrapper (String action, DataType dataType, DataType[] dependancies) {
        this.action = action
        this.dataType = dataType;
        this.dependancies = dependancies;
    }
    
    /**
     * This method makes the request on to the request broker.
     */
    def makeRequest(Session session) {
        Request request = new Request(action)
        request.setData(dataType.clone())
        request.setStart(new Date())
        request.setDependancies(dependancies)
        RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,"request/RequestBrokerDaemon");
        daemon.createRequest(request);
        request.getData().setAssociatedObject(request.getId())
        session.persist(request);
    }
}

