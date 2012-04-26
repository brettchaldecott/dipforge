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


/**
 * This object provides the access to the RDF layer.
 * 
 * @author brett chaldecott
 */
class RequestHandler {
    
    String action
    def data
    def dependancies
    
    /**
     * The request wrapper constructor.
     */
    RequestHandler (String action, def data) {
        this.action = action
        this.data = data
        this.dependancies = []
    }
    
    
    /**
     * This constructor sets all the parameters.
     */
    RequestHandler (String action, def data, def dependancies) {
        this.action = action
        this.data = data;
        this.dependancies = dependancies;
    }
    
    
    /**
     * This method makes the request onto the request broker
     */
    def makeRequest() {
        RequestBrokerConnector connector = new RequestBrokerConnector();
        
        
    }
}