/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * ResourceManagerAsync.java
 */


// package path
package com.rift.coad.change.client.action.workflow.piece.resource;


// 
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async interface for the resource manager.
 *
 * @author brett chaldecott
 */
public interface ResourceManagerAsync {
    /**
     * This method returns a list of types.
     *
     * @return The list of resources.
     * @throws com.rift.coad.change.client.action.workflow.piece.resource.ResourceException
     */
    public void listTypes(AsyncCallback callback);
}
