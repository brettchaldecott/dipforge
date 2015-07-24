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
 * BinWhileBlock.java
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
import com.rift.coad.change.client.action.workflow.piece.block.logical.LogicCanvas;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.WhileLoop;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * The bin while block
 *
 * @author brett chaldecott
 */
public class BinWhileBlock extends BinBlock implements TaskDefinitionManager {

    // private member variables
    private WhileLoop whileBlock;
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private LogicCanvas expression;


    /**
     * The constructor of the 
     */
    public BinWhileBlock(WorkflowCanvas canvas) {
        super(Icons.WHILE, canvas);

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * This constructor sets the while loop.
     *
     * @param canvas The canvas reference.
     * @param loop The loop reference
     */
    public BinWhileBlock(WorkflowCanvas canvas, WhileLoop whileBlock) throws Exception {
        super(Icons.WHILE, canvas, whileBlock);

        // the while block
        this.whileBlock = whileBlock;

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(whileBlock.getName());
        description = new TextItem();
        description.setName("Description");
        description.setValue(whileBlock.getDescription());
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

            if (whileBlock == null) {
                expression = new LogicCanvas(null,this.getBin());
            } else {
                expression = new LogicCanvas(whileBlock.getExpression(),this.getBin());
            }

            CanvasItem item = new CanvasItem();
            item.setCanvas(expression);
            item.setName("Expression");

            form.setFields(new FormItem[]{name,description, new SpacerItem(),
                item});
        } catch (Exception ex) {
            SC.say("Failed to create the property panel : " + ex.getMessage());
        }

        return form;
    }


    /**
     * This method returns the task definition.
     *
     * @return The reference to the task definition.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (whileBlock != null) {
                Canvas[] members = getFlowBar().getMembers();
                ActionTaskDefinition parent = processCanavasEntries(members);
                whileBlock.setChild(parent);
                return whileBlock;
            }
            throw new Exception("The While block has not been configured.");
        }

        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        
        WhileLoop whileBlock = WhileLoop.createInstance();
        if (this.whileBlock != null) {
            whileBlock.setTaskDefinitionId(this.whileBlock.getTaskDefinitionId());
        }
        whileBlock.setName(this.name.getValue().toString());
        whileBlock.setDescription(this.description.getValue().toString());
        whileBlock.setChild(parent);
        whileBlock.setExpression(expression.getExpression());
        whileBlock.setParameters(new DataType[0]);
        this.whileBlock = whileBlock;

        return whileBlock;
    }
}
