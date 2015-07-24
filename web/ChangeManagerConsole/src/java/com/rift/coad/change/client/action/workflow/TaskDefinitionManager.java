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
 * TaskDefinitionManager.java
 */

package com.rift.coad.change.client.action.workflow;

import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;

/**
 * This interface defines the task definition manager.
 *
 * @author brett chaldecott
 */
public interface TaskDefinitionManager {

    /**
     * This method returns the task definition managed by this piece.
     *
     * @return The task definition.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception;
}
