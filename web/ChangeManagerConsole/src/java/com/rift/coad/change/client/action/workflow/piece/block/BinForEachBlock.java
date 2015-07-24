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
 * BinForEachBlock.java
 */

package com.rift.coad.change.client.action.workflow.piece.block;

// smart gwt improts
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;


// coadunation imports
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.WorkflowCanvas;
import com.rift.coad.change.client.action.workflow.piece.BinBlock;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.ForEach;

/**
 * The bin for each block
 *
 * @author brett chaldecott
 */
public class BinForEachBlock extends BinBlock implements TaskDefinitionManager {

    /**
     * The constructor of the 
     */
    public BinForEachBlock(WorkflowCanvas canvas) {
        super(Icons.FOR_EACH, canvas);
    }


    /**
     * The constructor that sets all the values.
     *
     * @param canvas The canvas.
     * @param each Each value.
     */
    public BinForEachBlock(WorkflowCanvas canvas, ForEach each) throws Exception {
        super(Icons.FOR_EACH, canvas, each);
    }


    /**
     * This method returns a new canvas.
     *
     * @return The reference to the bin plock property canvas.
     */
    @Override
    public Canvas getPropertyPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
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
