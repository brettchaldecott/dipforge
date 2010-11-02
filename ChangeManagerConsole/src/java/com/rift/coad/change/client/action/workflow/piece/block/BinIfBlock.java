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
 * BinIfBlock.java
 */

package com.rift.coad.change.client.action.workflow.piece.block;

// smart gwt improts
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;


// coadunation imports
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.PieceBin;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.WorkflowCanvas;
import com.rift.coad.change.client.action.workflow.piece.BinBlock;
import com.rift.coad.change.client.action.workflow.piece.block.logical.LogicCanvas;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.Else;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.If;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * The bin if block
 *
 * @author brett chaldecott
 */
public class BinIfBlock extends BinBlock implements TaskDefinitionManager {

    // private member variables
    private If ifBlock;
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private LogicCanvas expression;


    /**
     * The constructor of the 
     */
    public BinIfBlock(WorkflowCanvas canvas) {
        super(Icons.IF,canvas);

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * This constructor sets up the if block.
     *
     * @param canvas The reference to the canvas.
     * @param ifBlock The if block.
     */
    public BinIfBlock(WorkflowCanvas canvas, If ifBlock, PieceBin bin)
            throws Exception {
        super(Icons.IF,canvas, ifBlock);
        this.ifBlock = ifBlock;

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(ifBlock.getName());
        description = new TextItem();
        description.setName("Description");
        description.setValue(ifBlock.getDescription());

        // add this to the flow block of the parent rather than relying on the
        // BinBlock to handle this. There is an exclude rule in the BinBlock
        // to handle this particular exlusion.
        bin.getFlowBar().addMember(this);

        // walk the list of else blocks attached.
        Else elseBlock = ifBlock.getElseBlock();
        while(elseBlock != null) {
            BinBlock currentCanvas;
            if (elseBlock instanceof ElseIf) {
                currentCanvas = new BinElseIfBlock(canvas,(ElseIf)elseBlock);
                elseBlock = ((ElseIf)elseBlock).getElseBlock();
            } else {
                currentCanvas = new BinElseBlock(canvas,elseBlock);
                elseBlock = null;
            }
            currentCanvas.setBin(bin);
            bin.getFlowBar().addMember(currentCanvas);
            currentCanvas.addClickHandler(canvas);
            currentCanvas.addShowContextMenuHandler(bin);
        }
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

            if (ifBlock == null) {
                expression = new LogicCanvas(null,this.getBin());
            } else {
                expression = new LogicCanvas(ifBlock.getExpression(),this.getBin());
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
     * @throws Exception
     */
    @Override
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (ifBlock != null) {
                Canvas[] members = getFlowBar().getMembers();
                ActionTaskDefinition parent = processCanavasEntries(members);
                ifBlock.setChild(parent);
                return ifBlock;
            }
            throw new Exception("The If block has not been configured.");
        }

        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        If ifBlock = If.createInstance();
        if (this.ifBlock != null) {
            ifBlock.setTaskDefinitionId(this.ifBlock.getTaskDefinitionId());
        }
        ifBlock.setName(this.name.getValue().toString());
        ifBlock.setDescription(this.description.getValue().toString());
        ifBlock.setChild(parent);
        ifBlock.setExpression(expression.getExpression());
        ifBlock.setParameters(new DataType[0]);
        this.ifBlock = ifBlock;
        
        return ifBlock;
    }
}
