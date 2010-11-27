/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * TypeCache.java
 */

// package path
package com.rift.coad.script.client.type;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.smartgwt.client.util.SC;
import java.util.List;

/**
 * This object manages the cache of types for the frontend
 *
 * @author brett chaldecott
 */
public class TypeCache implements AsyncCallback {

    // class singletons
    private static TypeCache singleton = null;

    // private member variables
    private List<ResourceBase> resources;

    /**
     * The default constructor of the type cache.
     */
    private TypeCache() {
        ResourceManagerConnector.getService().listTypes(this);
    }
    
    /**
     * This method returns a singleton reference for the type cache.
     *
     * @return The reference to the type cache.
     */
    public synchronized static TypeCache getInstance() throws ResourceException {
        if (singleton == null) {
            throw new ResourceException("Must initialize the type cache.");
        }
        return singleton;
    }


    /**
     * This method is called
     */
    public synchronized static void init() {
        singleton = new TypeCache();
    }


    /**
     * Handle any exceptions if they are thrown.
     *
     * @param caught The exception that has been caught.
     */
    public void onFailure(Throwable caught) {
        SC.say("Failed to retrieve the list of types : " + caught.getMessage());
    }


    /**
     * This method handles the successfull call.
     *
     * @param result The result of the successful call.
     */
    public void onSuccess(Object result) {
        this.resources = (List<ResourceBase>)result;
    }


    /**
     * This method returns a list of resources.
     * 
     * @return This method returns the list of resources
     */
    public List<ResourceBase> getResources() {
        return resources;
    }




}
