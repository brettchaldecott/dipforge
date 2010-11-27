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
 * BinBlock.java
 */

package com.rift.coad.change.client.action.workflow.piece;

// smart gwt improts
import com.rift.coad.change.client.action.workflow.ActionDefinitionHelper;
import com.rift.coad.change.client.action.workflow.ParamInfo;
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;

// coaduantion imports
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.ParameterCanvas;
import com.rift.coad.change.client.action.workflow.PieceBin;
import com.rift.coad.change.client.action.workflow.PropertyFactory;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.WorkflowCanvas;
import com.rift.coad.change.rdf.objmapping.client.change.ActionDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.task.Block;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.Else;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.ElseIf;
import com.rift.coad.change.rdf.objmapping.client.change.task.logic.If;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import java.util.ArrayList;
import java.util.List;

/**
 * This object represents a bin block.
 *
 * @author brett chaldecott
 */
public class BinBlock extends PieceBin implements PropertyFactory, TaskDefinitionManager {

    // parameters
    private DataType[] parameters;

    // form information
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private ParameterCanvas parameterCanvas;

    // private current block
    private Block block;


    /**
     * The default constructor for the bin block
     */
    public BinBlock(WorkflowCanvas canvas) {
        super(Icons.BLOCK, canvas);
        name = new TextItem("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * This constructor sets the canvas and the block reference.
     *
     * @param canvas
     * @param block
     */
    public BinBlock(WorkflowCanvas canvas, Block block) throws Exception {
        super(Icons.BLOCK, canvas);
        name = new TextItem("Name");
        name.setValue(block.getName());
        description = new TextItem("Description");
        description.setValue(block.getDescription());
        parameters = block.getParameters();
        this.block = block;

        if (block.getChild() != null) {
            ActionDefinitionHelper.walkDefinition(canvas, getFlowBar(), block.getChild(),this);
        }
    }

    
    /**
     * The bin block.
     *
     * @param icon
     */
    public BinBlock(String icon,WorkflowCanvas canvas) {
        super(icon, canvas);
    }


    /**
     * This constructor sets the canvas and the block reference.
     *
     * @param canvas
     * @param block
     */
    public BinBlock(String icon, WorkflowCanvas canvas, Block block) throws Exception {
        super(icon, canvas);
        name = new TextItem("Name");
        name.setValue(block.getName());
        description = new TextItem("Description");
        description.setValue(block.getDescription());
        parameters = block.getParameters();
        this.block = block;

        if (block.getChild() != null) {
            ActionDefinitionHelper.walkDefinition(canvas, getFlowBar(), block.getChild(),this);
        }
    }
    
    /**
     * This method returns a new canvas.
     *
     * @return The reference to the bin plock property canvas.
     */
    public Canvas getPropertyPanel() {
        if (form != null) {
            return form;
        }
        form = new DynamicForm();

        parameterCanvas = createParameterCanvas();
        CanvasItem canvasItem = new CanvasItem("Parameters");
        canvasItem.setCanvas(parameterCanvas);


        form.setFields(new FormItem[] {name,description,canvasItem});

        return form;
    }


    /**
     * The method returns the task definition for the bin block.
     *
     * @return The reference to the task definition.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        Block masterBlock = block;
        if (form != null) {
            masterBlock = Block.createInstance();
            masterBlock.setName(this.name.getValue().toString());
            masterBlock.setDescription(this.description.getValue().toString());
            masterBlock.setParameters(parameterCanvas.getParameters());
        }
        masterBlock.setChild(parent);
        return masterBlock;
    }


    /**
     * This method returns the list of parameters
     *
     * @return The list of parameters.
     */
    @Override
    public List<ParamInfo> getParameters() {
        List<ParamInfo> list = new ArrayList<ParamInfo>();
        if (parameterCanvas == null && parameters != null) {
            for (DataType parameter : parameters) {
                list.add(new ParamInfo(parameter.getIdForDataType(),parameter.getDataName()));
            }
        } else if (parameterCanvas != null) {
            DataType[] parameters = parameterCanvas.getParameters();
            for (DataType parameter : parameters) {
                list.add(new ParamInfo(parameter.getIdForDataType(),parameter.getDataName()));
            }
        }
        
        return list;
    }


    /**
     * This method sets the parameters for the canvas.
     *
     * @param parameters The list of parameters.
     */
    public void setParameters(DataType[] parameters) {
        this.parameters = parameters;
    }


    /**
     * This method is method returns the parameter canvas.
     *
     * @return The reference to the parameter canvas.
     */
    public ParameterCanvas createParameterCanvas() {
        parameterCanvas = new ParameterCanvas(parameters);
        return parameterCanvas;
    }
    

    /**
     * This method returns the reference to the parameter canvas.
     *
     * @return The reference to the canvas.
     */
    public ParameterCanvas getParameterCanvas() {
        return parameterCanvas;
    }


    /**
     * This method is called to process the canvas entries.
     *
     * @param members The members to process.
     * @return The parent block.
     */
    protected ActionTaskDefinition processCanavasEntries(Canvas[] members)
            throws Exception{
        ActionTaskDefinition parent = null;
        ActionTaskDefinition currentTask = null;
        If ifBlock = null;
        ElseIf currentElseIf = null;
        String definitions = "";
        String errors = "";
        boolean hasErrors = false;
        for (Canvas member : members) {
            try {
                ActionTaskDefinition task = ((TaskDefinitionManager)member).getTaskDefinition();
                definitions += "[" + task.getTaskDefinitionId() + "]";
                if (currentTask == null) {
                    parent = task;
                    currentTask = task;
                } else if (!(task instanceof Else)) {
                    currentTask.setNext(task);
                    currentTask = task;
                }

                // the for loop processing
                if (task instanceof If) {
                    ifBlock = (If)task;
                } else if (task instanceof Else) {
                    if (ifBlock == null) {
                        throw new Exception("The block is not correctly formated" +
                                " Else block must attache to an if block");
                    }
                    Else elseBlock = (Else)task;
                    if (currentElseIf != null) {
                        currentElseIf.setElseBlock(elseBlock);
                        if (elseBlock instanceof ElseIf) {
                            currentElseIf = (ElseIf)task;
                        }
                    } else if (task instanceof ElseIf) {
                        currentElseIf = (ElseIf)task;
                        ifBlock.setElseBlock(elseBlock);
                    } else {
                        ifBlock.setElseBlock(elseBlock);
                    }

                    if (!(task instanceof ElseIf)) {
                        ifBlock = null;
                        currentElseIf = null;
                    }
                    continue;
                }

                
            } catch (Exception ex) {
                errors += "[" + ex.getMessage() + "]";
                hasErrors = true;
            }
        }
        if (hasErrors) {
            throw new Exception(errors);
        }
        return parent;
    }

    

}
