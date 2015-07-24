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
import com.rift.coad.change.client.action.workflow.ActionDefinitionHelper;
import com.smartgwt.client.widgets.Canvas;


// coadunation imports
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.ParamInfo;
import com.rift.coad.change.client.action.workflow.ParameterCanvas;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.WorkflowCanvas;
import com.rift.coad.change.client.action.workflow.piece.BinBlock;
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.ActionInfo;
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import java.util.ArrayList;
import java.util.List;


/**
 * The bin while block
 *
 * @author brett chaldecott
 */
public class BinWorkflowBlock extends BinBlock {

    // private member variables
    private ActionDefinition definition = null;
    private String type;
    private String action;
    private WorkflowCanvas workflowCanvas;
    private Block workflowBlock;

    // form information
    private DynamicForm form;
    private TextItem name;
    private TextItem description;

    // display information
    private boolean display = false;




    /**
     * The constructor of the 
     */
    public BinWorkflowBlock(WorkflowCanvas canvas,String type, String action) {
        super(canvas);
        this.type = type;
        this.action = action;
        this.workflowCanvas = canvas;


        name = new TextItem("Name");
        name.setValue(type + "." + action);
        description = new TextItem("Description");
        description.setValue(type + " " + action);
        
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

        CanvasItem canvasItem = new CanvasItem("Parameters");
        canvasItem.setCanvas(createParameterCanvas());


        form.setFields(new FormItem[] {name,description,canvasItem});

        display = true;

        return form;
    }


    /**
     * This method is called to set the definition for this flow using the action definition.
     *
     * @param definition The action definition.
     */
    public void setDefinition(ActionDefinition definition) {
        try {
            if (!definition.getDataTypeId().equals(type) ||
                    !definition.getActionInfo().getName().equals(action)) {
                SC.say("Incorrect action definition");
                return;
            }
            this.definition = definition;
            ActionTaskDefinition currentTask = definition.getParent();
            Block block = null;
            try {
                block = (Block)currentTask;
                workflowBlock = block;
                currentTask = block.getChild();
            } catch (Exception ex) {
                SC.say("Failed to convert the block [" + currentTask.getClass().getName()
                        + "] : " + ex.getMessage());
            }
            try {
                if (block != null) {
                    // set the form fields
                    name.setValue(block.getName());
                    description.setValue(block.getDescription());

                    // set the parameter canvas if it is not null
                    setParameters(block.getParameters());
                    if (getParameterCanvas() != null) {
                        getParameterCanvas().setParameters(block.getParameters());
                    }
                }
            } catch (Exception ex) {
                SC.say("Failed to set the parameters : " + ex.getMessage());
            }
            ActionDefinitionHelper.walkDefinition(this.workflowCanvas, getFlowBar(),
                    currentTask,this);
        } catch (Exception ex) {
            SC.say("Failed to load the action definition : " + ex.getMessage());
        }
    }


    /**
     * This method returns the definition.
     *
     * @return The definition for this object.
     */
    public ActionDefinition getDefinition() throws Exception {
        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        Block masterBlock = this.workflowBlock;
        if (this.display == true) {
            masterBlock = Block.createInstance();
            masterBlock.setName(this.name.getValue().toString());
            masterBlock.setDescription(this.description.getValue().toString());
            masterBlock.setParameters(getParameterCanvas().getParameters());
        }
        masterBlock.setChild(parent);
        parent = masterBlock;
        ActionDefinition definition = ActionDefinition.createInstance(this.type,
                ActionInfo.createInstance(this.action,this.action), parent);
        definition.setDataTypeId(type);
        if (this.definition != null) {
            definition.setDependancyData(this.definition.getDependancyData());
        }
        this.definition = definition;

        //this.workflowCanvas.;
        return definition;
    }

}
