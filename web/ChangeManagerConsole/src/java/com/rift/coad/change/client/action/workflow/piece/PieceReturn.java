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
 * PieceReturn.java
 */


// package path
package com.rift.coad.change.client.action.workflow.piece;

// smart gwt imports
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;

// coadunation imports
import com.rift.coad.change.client.action.workflow.DroppedPiece;
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.PropertyFactory;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.rdf.objmapping.client.change.Return;

/**
 * This object represents a return statement.
 *
 * @author brett chaldecott
 */
public class PieceReturn  extends DroppedPiece implements PropertyFactory, TaskDefinitionManager {

    // private member variables
    private Return returnVariable;

    /**
     * The default constructor
     */
    public PieceReturn() {
        super(Icons.RETURN);
    }


    /**
     * This constructor sets the return variable.
     *
     * @param returnVariable The reference to the return variable.
     */
    public PieceReturn(Return returnVariable) {
        super(Icons.RETURN);
    }

    
    /**
     * This method returns the property panel information.
     *
     * @return The link to the property panel information.
     */
    public Canvas getPropertyPanel() {
        return null;
    }


    /**
     * This method returns the task definition.
     *
     * @return The task definition.
     */
    public ActionTaskDefinition getTaskDefinition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
