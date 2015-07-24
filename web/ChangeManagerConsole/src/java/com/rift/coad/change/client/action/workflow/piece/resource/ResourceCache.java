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
 * ResourceCache.java
 */

// package path
package com.rift.coad.change.client.action.workflow.piece.resource;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.smartgwt.client.util.SC;
import java.util.List;

/**
 * This object stores the resource cache information information.
 *
 * @author brett chaldecott
 */
public class ResourceCache {

    /**
     * The call back handler for the resource class.
     */
    public class ResourceCallbackHandler implements AsyncCallback {

        /**
         * Handle the failure.
         *
         * @param caught The exception that was caught.
         */
        public void onFailure(Throwable caught) {
            SC.say("Failed to retrieve the resource information : " + caught.getMessage());
        }


        /**
         * This method deals with the succesfull.
         *
         * @param result The result.
         */
        public void onSuccess(Object result) {
            resources = (List<ResourceBase>)result;
        }

    }

    // class singletons
    private static ResourceCache singleton = null;

    // private memver variables
    private List<ResourceBase> resources;

    /**
     * The default constructor
     */
    private ResourceCache() {
        ResourceManagerConnector.getService().listTypes(new ResourceCallbackHandler());
    }


    /**
     * This method returns the reference to the resource cache object.
     *
     * @return The reference to the resource cache.
     */
    public static ResourceCache getInstance() {
        if (singleton == null) {
            singleton = new ResourceCache();
        }
        return singleton;
    }

    
    /**
     * The reference to the resources.
     * 
     * @return The list of resources.
     */
    public List<ResourceBase> getResources() {
        return resources;
    }


    /**
     * This method is used to check if this is a system resource or not.
     *
     * @param type The type of system resource to perform the check against.
     * @return TRUE if this is a system resource, FALSE if not
     */
    public boolean isSystemResource(String type) {
        for (ResourceBase resource : resources) {
            if (resource.getIdForDataType().equals(type)) {
                return true;
            }
        }
        return false;
    }


}
