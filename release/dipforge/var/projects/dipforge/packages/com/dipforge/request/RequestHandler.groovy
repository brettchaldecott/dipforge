/*
 * dipforge: Description
 * Copyright (C) Fri Apr 20 07:55:38 SAST 2012 owner 
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
 * RequestHandler.groovy
 */

package com.dipforge.request


// imports
import com.rift.coad.lib.common.RandomGuid;
import com.rift.coad.change.request.Request;
import com.rift.coad.change.request.RequestData;
import com.rift.coad.change.request.RequestEvent;


/**
 * This object provides the access to the RDF layer.
 * 
 * @author brett chaldecott
 */
class RequestHandler {
    
    String project
    String action
    def data
    def dependancies
    
    
    /**
     * The request wrapper constructor.
     * 
     * @param project The name of the project this request is being makde from.
     * @param action The action that is being performed on this data
     * @param data The data
     */
    RequestHandler (String project, String action, def data) {
        this.project = project
        this.action = action
        this.data = data
        this.dependancies = []
    }
    
    
    /**
     * This constructor sets all the parameters.
     * 
     * @param project The name of the project
     * @param action The name of the action.
     * @param data The data object.
     * @param dependancies The list of dependancies.
     */
    RequestHandler (String project, String action, def data, def dependancies) {
        this.project = project
        this.action = action
        this.data = data;
        this.dependancies = dependancies;
    }
    
    
    /**
     * This method returns a reference to the new request handler
     */
    public static RequestHandler getInstance(String project, String action, def data) {
        return new RequestHandler (project, action, data)
    }
    
    
    /**
     * This method returns a reference to the new request handler
     */
    public static RequestHandler getInstance(String project, String action, def data, def dependancies) {
        return new RequestHandler (project, action, data, dependancies)
    }
    
    
    /**
     * This method makes the request onto the request broker
     */
    def makeRequest() {
        RequestBrokerConnector connector = new RequestBrokerConnector();
        
        RequestData requestData = new RequestData(this.data.builder.classDef.getURI().toString(),
                this.data.toXML(), data.builder.classDef.getLocalName())
        Request request = new Request(RandomGuid.getInstance().getGuid(), 
                project, requestData, action, new java.util.Date(), null,
                new java.util.ArrayList<RequestEvent>())
        if (dependancies.size() > 0) {
            java.util.List<RequestData> dependancyList = new java.util.ArrayList<RequestData>();
            for (dependance in dependancies) {
                dependancyList.add(new RequestData(dependance.builder.classDef.getURI().toString(),
                        dependance.toXML(), dependance.builder.classDef.getLocalName()))
            }
            requestData.setData(dependancyList)
        }
        connector.getBroker().createRequest(request) 
    }
}