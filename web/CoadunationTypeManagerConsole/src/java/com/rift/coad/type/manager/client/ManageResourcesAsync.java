/*
 * CoadunationTypeManagerConsole: The type management console.
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
 * ManageResources.java
 */

package com.rift.coad.type.manager.client;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.type.manager.client.dto.ResourceDefinition;


/**
 * This object is the asynchronous verison of the resource manager.
 *
 * @author brett chaldecott
 */
public interface ManageResourcesAsync {

    /**
     * This method is called to add a new type.
     *
     * @param base The base type.
     * @param callback The call back object.
     */
    public void addType(ResourceDefinition base, AsyncCallback callback);


    /**
     * The asynchronious version of the update type information.
     *
     * @param base The base type to update.
     * @param callback The call back.
     */
    public void updateType(ResourceDefinition base, AsyncCallback callback);


    /**
     * This method retrieves the resource identified by the name.
     *
     * @param name The name of the resource to retrieve.
     * @param callback The call back.
     */
    public void getType(String name, AsyncCallback callback);


    /**
     * This method is called to delete the type.
     *
     * @param name The name of the type to delete.
     * @param callback The async call back object.
     */
    public void deleteType(String name, AsyncCallback callback);


    /**
     * This asynchronious version of the sychronious list types method.
     *
     * @param baseTypes The base types.
     * @param callback The call back method.
     */
    public void listTypes(String[] baseTypes, AsyncCallback callback);
    
}
