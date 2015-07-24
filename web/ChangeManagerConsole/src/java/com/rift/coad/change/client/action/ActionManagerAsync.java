/*
 * ChangeControlManager: The manager for the change events.
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
 * ActionManager.java
 */

package com.rift.coad.change.client.action;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;


/**
 * This is the asynchronous version of the action manager.
 *
 * @author brett chaldecott
 */
public interface ActionManagerAsync {
    
    /**
     * This method returns a list of actions.
     *
     * @return The list of actions.
     */
    public void listActions(AsyncCallback callback);


    /**
     * This method adds a new action.
     *
     * @param action The string containing the action information.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void addAction(String action,AsyncCallback callback);


    /**
     * This method is responsible for removing the specified action.
     *
     * @param action The string containing the action information.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void removeAction(String action,AsyncCallback callback);

    /**
     * This method is  the aynchronous version of the get action definition method.
     * 
     * @param objectId The object id to dentify the action by.
     * @param action The name of the action attached to the object id.
     * @param asyncCallback The asyn cal back.
     */
    public void getActionDefinition(String objectId, String action, AsyncCallback asyncCallback);


    /**
     * This is the asynchronous version of the save action definition method.
     *
     * @param definition The definition to add.
     * @param asyncCallback The call back object.
     */
    public void saveActionDefinition(ActionDefinition definition, AsyncCallback asyncCallback);

    
}
