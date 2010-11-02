/*
 * ChangeControlManager: The manager for the change events.
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
 * ActionManager.java
 */

// package path
package com.rift.coad.change.client.action;

// java imports
import java.util.List;


// gwt imports
import com.google.gwt.user.client.rpc.RemoteService;
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;

/**
 * This interface defines the methods need to access the actions.
 *
 * @author brett chaldecott
 */
public interface ActionManager extends RemoteService{
    /**
     * This method returns a list of actions.
     *
     * @return The list of actions.
     */
    public List<String> listActions() throws ActionException;


    /**
     * This method adds a new action.
     *
     * @param action The string containing the action information.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void addAction(String action) throws ActionException;


    /**
     * This method is responsible for removing the specified action.
     *
     * @param action The string containing the action information.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void removeAction(String action) throws ActionException;



    /**
     * This method returns the action definition identifed by the object id and action.
     *
     * @param objectId The id of the object type to retrieve the action defintion for.
     * @param action The action to retrieve the action defintion for.
     * @return The reference to the action definition.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public ActionDefinition getActionDefinition(String objectId, String action) throws ActionException;


    /**
     * This method save the action defintion.
     *
     * @param definition The definition to add.
     * @throws com.rift.coad.change.client.action.ActionException
     */
    public void saveActionDefinition(ActionDefinition definition) throws ActionException;


}
