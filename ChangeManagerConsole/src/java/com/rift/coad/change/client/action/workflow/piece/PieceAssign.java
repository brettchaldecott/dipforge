/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2010  Rift IT Contracting
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
 * PieceAssign.java
 */

package com.rift.coad.change.client.action.workflow.piece;

import com.rift.coad.change.client.action.workflow.DroppedPiece;
import com.rift.coad.change.client.action.workflow.Icons;
import com.rift.coad.change.client.action.workflow.ParamInfoFilter;
import com.rift.coad.change.client.action.workflow.PropertyFactory;
import com.rift.coad.change.client.action.workflow.TaskDefinitionManager;
import com.rift.coad.change.rdf.objmapping.client.change.ActionTaskDefinition;
import com.rift.coad.change.rdf.objmapping.client.change.task.Assign;
import com.rift.coad.rdf.objmapping.util.client.id.IDGenerator;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

/**
 * This object is responsible for managing the information around a piece.
 *
 * @author brett chaldecott
 */
public class PieceAssign extends DroppedPiece implements PropertyFactory, TaskDefinitionManager {

    // private member variables
    private TextItem name;
    private TextItem description;
    
    private DynamicForm form;
    private SelectItem sourceName;
    private SelectItem tName;
    private Assign assign;


    /**
     * This method sets up the icon information.
     */
    public PieceAssign() {
        super(Icons.ASSIGN);
        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(IDGenerator.getId());
        description = new TextItem();
        description.setName("Description");
        description.setValue(IDGenerator.getId());
    }


    /**
     * This method sets up the icon information.
     */
    public PieceAssign(Assign assign) {
        super(Icons.ASSIGN);
        this.assign = assign;

        // get the services
        name = new TextItem();
        name.setName("Name");
        name.setValue(assign.getName());
        description = new TextItem();
        description.setName("Description");
        description.setValue(assign.getDescription());
    }


    /**
     * This method returns the property panel information.
     *
     * @return The reference to the propery panel created to manage this object.
     */
    public Canvas getPropertyPanel() {
        if (form != null) {
            return form;
        }
        form = new DynamicForm();

        // return type
        sourceName = new SelectItem();
        sourceName.setName("Source");
        sourceName.setValueMap(ParamInfoFilter.convertParameterToArray(
                this.getBin().listParameters()));
        if (assign != null) {
            sourceName.setValue(assign.getSource());
        }


        // jndi reference
        tName = new SelectItem();
        tName.setName("Target");
        tName.setValueMap(ParamInfoFilter.convertParameterToArray(
                this.getBin().listParameters()));
        if (assign != null) {
            tName.setValue(assign.getTarget());
        }

        // set the fields
        form.setFields(new FormItem[]{name,description, new SpacerItem(),
            sourceName,tName});

        return form;
    }


    /**
     * This method returns the action task definition.
     *
     * @return The task definition.
     * @throw Exception
     */
    public ActionTaskDefinition getTaskDefinition() throws Exception {
        if (form == null) {
            if (assign != null) {
                return assign;
            }
            throw new Exception("The assignment has not been configured.");
        }
        if (sourceName.getValue() == null) {
            throw new Exception("The source name must be set in an assignment");
        }
        if (tName.getValue() == null) {
            throw new Exception("The target name must be set in an assignment");
        }
        Assign assign = Assign.createInstance(this.name.getValue().toString(),
                this.description.getValue().toString(),
                this.tName.getValue().toString(),
                this.sourceName.getValue().toString());
        if (this.assign != null) {
            assign.setTaskDefinitionId(this.assign.getTaskDefinitionId());
        }
        this.assign = assign;
        return assign;
    }
    
}
