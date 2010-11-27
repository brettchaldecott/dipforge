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
 * PieceCall.java
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
import com.rift.coad.change.rdf.objmapping.client.change.task.EmbeddedBlock;

/**
 * This object represents an embedded statement.
 *
 * @author brett chaldecott
 */
public class PieceEmbedded  extends DroppedPiece implements PropertyFactory, TaskDefinitionManager {

    /**
     * The default constructor
     */
    public PieceEmbedded() {
        super(Icons.EMBEDDED);
    }


    /**
     * The constructor that takes the embedded information.
     *
     * @param embedded The embedded block
     */
    public PieceEmbedded(EmbeddedBlock embedded) {
        super(Icons.EMBEDDED);
    }

    
    /**
     * This method returns the property panel information.
     *
     * @return The link to the property panel information.
     */
    public Canvas getPropertyPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     * This method returns the task definition.
     *
     * @return This method returns the task definition.
     */
    public ActionTaskDefinition getTaskDefinition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
