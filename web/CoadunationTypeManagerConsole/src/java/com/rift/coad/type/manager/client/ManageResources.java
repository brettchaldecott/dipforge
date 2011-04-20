/*
 * CoadunationTypeManagerConsole: The type management console.
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
 * ManageResources.java
 */

package com.rift.coad.type.manager.client;
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.type.manager.client.dto.ResourceDefinition;
import java.util.List;

/**
 * This object is responsible for managing the resources.
 *
 * @author brett chaldecott
 */
public interface ManageResources extends RemoteService{

    
    /**
     * This method is called to add a new type.
     * 
     * @param base The base type to add.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void addType(ResourceDefinition base) throws ManageResourcesException;


    /**
     * This method updates the type information.
     *
     * @param base The base type to update.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void updateType(ResourceDefinition base) throws ManageResourcesException;


    /**
     * This method retrieves the resource identified by the name.
     *
     * @param name The name of the resource to retrieve.
     * @return The resource base.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public ResourceDefinition getType(String name) throws ManageResourcesException;
    
    
    /**
     * This meethod is called to delete the identified type.
     *
     * @param name The name to delete.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public void deleteType(String name) throws ManageResourcesException;


    /**
     * This method returns the list of types associated with base types.
     *
     * @param baseTypes The list of base types to retrieve.
     * @return The array of base types.
     * @throws com.rift.coad.type.manager.client.ManageResourcesException
     */
    public List<ResourceDefinition> listTypes(String[] baseTypes) throws ManageResourcesException;
}
