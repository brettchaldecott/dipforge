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
 * BinElseIfBlock.java
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
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.ElseIf;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * The bin else if block
 *
 * @author brett chaldecott
 */
public class BinElseIfBlock extends BinBlock implements TaskDefinitionManager {

    // private member variables
    private ElseIf elseIf;
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private LogicCanvas expression;


    /**
     * The constructor of the 
     */
    public BinElseIfBlock(WorkflowCanvas canvas) {
        super(Icons.ELSE_IF, canvas);

        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * The constructor of the bin else if block.
     *
     * @param canvas The canvas reference.
     * @param elseIf The else if block.
     */
    public BinElseIfBlock(WorkflowCanvas canvas,ElseIf elseIf) throws Exception {
        super(Icons.ELSE_IF, canvas, elseIf);
        this.elseIf = elseIf;

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(elseIf.getName());
        description = new TextItem();
        description.setName("Description");
        description.setValue(elseIf.getDescription());
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

            if (elseIf == null) {
                expression = new LogicCanvas(null,this.getBin());
            } else {
                expression = new LogicCanvas(elseIf.getExpression(),this.getBin());
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
     * @return The task definition.
     * @throws java.lang.Exception
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (elseIf != null) {
                Canvas[] members = getFlowBar().getMembers();
                ActionTaskDefinition parent = processCanavasEntries(members);
                elseIf.setChild(parent);
                return elseIf;
            }
            throw new Exception("The Else If block has not been configured.");
        }

        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        ElseIf elseIf = ElseIf.createInstance();
        if (this.elseIf != null) {
            elseIf.setTaskDefinitionId(this.elseIf.getTaskDefinitionId());
        }
        elseIf.setName(this.name.getValue().toString());
        elseIf.setDescription(this.description.getValue().toString());
        elseIf.setChild(parent);
        elseIf.setExpression(expression.getExpression());
        elseIf.setParameters(new DataType[0]);
        this.elseIf = elseIf;

        return elseIf;
    }
}
