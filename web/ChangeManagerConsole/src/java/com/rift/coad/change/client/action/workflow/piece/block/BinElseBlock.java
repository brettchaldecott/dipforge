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
 * BinElseBlock.java
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
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.Else;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * The bin else block
 *
 * @author brett chaldecott
 */
public class BinElseBlock extends BinBlock implements TaskDefinitionManager {

    // private member variables
    private Else elseBlock;
    private DynamicForm form;
    private TextItem name;
    private TextItem description;


    /**
     * The constructor of the 
     */
    public BinElseBlock(WorkflowCanvas canvas) {
        super(Icons.ELSE, canvas);

        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * This is the else block.
     *
     * @param canvas The canvas that everything is attached to.
     * @param elseVariable The else variable containing the information
     */
    public BinElseBlock(WorkflowCanvas canvas, Else elseBlock) throws Exception {
        super(Icons.ELSE, canvas, elseBlock);

        this.elseBlock = elseBlock;

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(elseBlock.getName());
        description = new TextItem();
        description.setName("Description");
        description.setValue(elseBlock.getDescription());
    }


    /**
     * This method returns a new canvas.
     *
     * @return The reference to the bin plock property canvas.
     */
    @Override
    public Canvas getPropertyPanel() {
        if (form != null) {
            return form;
        }

        form = new DynamicForm();
        try {

            form.setFields(new FormItem[]{name,description});
        } catch (Exception ex) {
            SC.say("Failed to create the property panel : " + ex.getMessage());
        }
        return form;
    }


    /**
     * This method returns the task definition
     *
     * @return The reference to the task definition.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (elseBlock != null) {
                Canvas[] members = getFlowBar().getMembers();
                ActionTaskDefinition parent = processCanavasEntries(members);
                elseBlock.setChild(parent);
                return elseBlock;
            }
            throw new Exception("The Else block has not been configured.");
        }

        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        Else elseBlock = Else.createInstance();
        if (this.elseBlock != null) {
            elseBlock.setTaskDefinitionId(this.elseBlock.getTaskDefinitionId());
        }
        
        elseBlock.setName(this.name.getValue().toString());
        elseBlock.setDescription(this.description.getValue().toString());
        elseBlock.setChild(parent);
        elseBlock.setParameters(new DataType[0]);
        this.elseBlock = elseBlock;

        return elseBlock;
    }
}
