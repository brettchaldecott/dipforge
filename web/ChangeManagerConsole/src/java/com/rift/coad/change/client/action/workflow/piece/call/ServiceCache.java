/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  2015 Burntjam
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
 * DataMapperManagerAsync.java
 */

// package path
package com.rift.coad.change.client.action.workflow.piece.call;

// imports
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.change.client.action.workflow.piece.PieceCall;
import com.smartgwt.client.util.SC;

/**
 * This is the implementation of the service call back handler.
 *
 * @author brett chaldecott
 */
public class ServiceCache implements AsyncCallback {

    // class singletons
    private static ServiceCache singleton = null;

    // private member variables
    private String[] services;
    
    /**
     * This constructor sets the internal call reference.
     *
     * @param call The reference to the call back object.
     */
    private ServiceCache() {
        DataMapperManagerConnector.getService().listServices(this);
    }

    
    /**
     * This method returns the reference to the given instance.
     * 
     * @return This reference to the service cache.
     */
    public static ServiceCache getInstance() {
        if (singleton == null) {
            singleton = new ServiceCache();
        }
        return singleton;
    }


    /**
     * This method handles failure result.
     *
     * @param caught The exception to handle
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to retrieve the list of services : " + caught.getMessage());
    }

    
    /**
     * This method handles the successfull result.
     *
     * @param result The result.
     */
    public void onSuccess(Object result) {
        this.services = (String[])result;
    }


    /**
     * This method returns the list of services contained in this cache.
     * 
     * @return The list of services.
     */
    public String[] getServices() {
        return services;
    }




}
