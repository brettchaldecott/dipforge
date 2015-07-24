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
 * BinForBlock.java
 */

package com.rift.coad.change.client.action.workflow.piece.block;

// smart gwt improts
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.smartgwt.client.widgets.Canvas;


// coadunation imports
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.ParamInfo;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.client.action.workflow.WorkflowCanvas;
import com.rift.coad.change.client.action.workflow.piece.BinBlock;
import com.rift.coad.change.rdf.objmapping.client.change.task.loop.ForLoop;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.base.number.RDFInteger;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.rift.coad.rdf.objmapping.util.client.type.TypeHelper;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import java.util.ArrayList;
import java.util.List;

/**
 * The bin for block
 *
 * @author brett chaldecott
 */
public class BinForBlock extends BinBlock implements TaskDefinitionManager {

    // private member variables
    private ForLoop forBlock;
    private DynamicForm form;
    private TextItem name;
    private TextItem description;
    private TextItem indexName;
    private TextItem indexValue;
    private TextItem endName;
    private TextItem endValue;
    private TextItem incrementName;
    private TextItem incrementValue;


    /**
     * The constructor of the 
     */
    public BinForBlock(WorkflowCanvas canvas) {
        super(Icons.FOR, canvas);

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());


        indexName = new TextItem();
        indexName.setName("Index Name");
        indexName.setValue("index");

        indexValue = new TextItem();
        indexValue.setName("Index Value");
        indexValue.setValue("0");


        endName = new TextItem();
        endName.setName("End Name");
        endName.setValue("end");

        endValue = new TextItem();
        endValue.setName("End Value");

        incrementName = new TextItem();
        incrementName.setName("Increment Name");
        incrementName.setValue("increment");

        incrementValue = new TextItem();
        incrementValue.setName("Increment Value");
        incrementValue.setValue("1");



    }


    /**
     * The constructor that sets the for loop information
     *
     * @param canvas The canvas reference.
     * @param forLoop The reference to the for loop.
     */
    public BinForBlock(WorkflowCanvas canvas, ForLoop forBlock) throws Exception {
        super(Icons.FOR, canvas, forBlock);
        try {
            this.forBlock = forBlock;

            // get the services
            name = new TextItem();
            name.setName("Name");
            name.setValue(forBlock.getName());
            description = new TextItem();
            description.setName("Description");
            description.setValue(forBlock.getDescription());

            RDFInteger index = (RDFInteger)forBlock.getIndex();
            indexName = new TextItem();
            indexName.setName("Index Name");
            indexValue = new TextItem();
            indexValue.setName("Index Value");
            if (index != null) {
                indexName.setValue(index.getDataName());
                indexValue.setValue(index.getValue());
            }

            endName = new TextItem();
            endName.setName("End Name");
            endValue = new TextItem();
            endValue.setName("End Value");
            RDFInteger end = (RDFInteger)forBlock.getEnd();
            if (end != null) {
                endName.setValue(end.getDataName());
                endValue.setValue(end.getValue());
            }

            incrementName = new TextItem();
            incrementName.setName("Increment Name");
            incrementValue = new TextItem();
            incrementValue.setName("Increment Value");
            RDFInteger increment = (RDFInteger)forBlock.getIncrement();
            if (increment != null) {
                incrementName.setValue(increment.getDataName());
                incrementValue.setValue(increment.getValue());
            }
        } catch (Exception ex) {
            throw new Exception ("Failed to instanciate a For loop because : " + ex.getMessage());
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

        form.setFields(new FormItem[] {name,description,new SpacerItem(),
                this.indexName,this.indexValue,new SpacerItem(),
                this.endName,this.endValue,new SpacerItem(),
                this.incrementName,this.incrementValue});

        return form;
    }


    /**
     * This method returns the task definition.
     *
     * @return The task definition manager.
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (this.forBlock != null) {
                Canvas[] members = getFlowBar().getMembers();
                ActionTaskDefinition parent = processCanavasEntries(members);
                forBlock.setChild(parent);
                return forBlock;
            }
            throw new Exception("The For block has not been configured.");
        }

        Canvas[] members = getFlowBar().getMembers();
        ActionTaskDefinition parent = processCanavasEntries(members);

        ForLoop forBlock = ForLoop.createInstance();
        if (this.forBlock != null) {
            forBlock.setTaskDefinitionId(this.forBlock.getTaskDefinitionId());
        }
        forBlock.setName(this.name.getValue().toString());
        forBlock.setDescription(this.description.getValue().toString());
        forBlock.setChild(parent);
        forBlock.setParameters(new DataType[0]);

        if ((indexValue.getValue() == null) || (indexName.getValue() == null)  ||
                (endValue.getValue() == null) || (endName.getValue() == null) ||
                (incrementValue.getValue() == null) || (incrementName.getValue() == null)) {
            throw new Exception("Must provide values for all for loop information");
        }
        DataType type = TypeHelper.getTypeFromString(
                TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                indexValue.getValue().toString());
        type.setDataName(indexName.getValue().toString());
        forBlock.setIndex(type);

        type = TypeHelper.getTypeFromString(
                TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                endValue.getValue().toString());
        type.setDataName(endName.getValue().toString());
        forBlock.setEnd(type);

        type = TypeHelper.getTypeFromString(
                TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                incrementValue.getValue().toString());
        type.setDataName(incrementName.getValue().toString());
        forBlock.setIncrement(type);

        this.forBlock = forBlock;
        return forBlock;
    }


    /**
     * This method returns the list of parameters
     *
     * @return The list of parameters.
     */
    @Override
    public List<ParamInfo> getParameters() {
        List<ParamInfo> list = new ArrayList<ParamInfo>();
        try {
            if (form == null) {
                if (forBlock != null) {
                    list.add(new ParamInfo(
                            TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                            forBlock.getIndex().getDataName()));
                    list.add(new ParamInfo(
                            TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                            forBlock.getEnd().getDataName()));
                    list.add(new ParamInfo(
                            TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                            forBlock.getIncrement().getDataName()));
                    return list;
                } else {
                    return list;
                }
            }

            list.add(new ParamInfo(
                    TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                    this.indexName.getValue().toString()));
            list.add(new ParamInfo(
                    TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                    this.endName.getValue().toString()));
            list.add(new ParamInfo(
                    TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0])[1],
                    this.incrementName.getValue().toString()));

            return list;
        } catch (Exception ex) {
            // ignore
        }
        return list;
    }
}
