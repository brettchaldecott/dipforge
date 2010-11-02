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
 * BinWhileBlock.java
 */

// package path
package com.rift.coad.script.client.files;

// java imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.util.client.type.TypeHelper;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import java.util.List;

/**
 * This object represents a parameter canvas.
 *
 * @author brett chaldecott
 */
public class ParameterCanvas extends Canvas {


    /**
     * This class manages the add parameter functionality.
     */
    public class AddParameterHandler implements ClickHandler {

        /**
         * This method handles the onclick action.
         *
         * @param event The event to deal with.
         */
        public void onClick(ClickEvent event) {
            Object value = mtypeName.getValue();
            if (value == null) {
                SC.say("The [type] of the variable must be set");
                return;
            }
            String type = value.toString();
            value = mparameterName.getValue();
            if (value == null) {
                SC.say("The [name] of the variable must be set");
                return;
            }
            String name = value.toString();

            // check for duplicate entry
            ListGridRecord[] records = parameterTypeGrid.getRecords();
            for (ListGridRecord record : records) {
                if (record.getAttributeAsString("Name").equals(name)) {
                    SC.say("The variable by the name of [" + name + "] already exists.");
                    return;
                }
            }

            // add the records
            ListGridRecord record = new ListGridRecord();
            record.setAttribute("Type", type);
            record.setAttribute("Name", name);
            record.setAttribute("Value", value);
            parameterTypeGrid.addData(record);
            mtypeName.clearValue();
            mparameterName.clearValue();
            
            update.setDisabled(true);
            delete.setDisabled(true);
        }

    }


    /**
     * This object is responsible for handling the 
     */
    public class UpdateParameterHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            Object value = mtypeName.getValue();
            if (value == null) {
                SC.say("The [type] of the variable must be set");
                return;
            }
            String type = value.toString();
            value = mparameterName.getValue();
            if (value == null) {
                SC.say("The [name] of the variable must be set");
                return;
            }
            String name = value.toString();
            ListGridRecord[] records = parameterTypeGrid.getRecords();
            for (int index = 0; index < records.length; index++) {
                ListGridRecord record = records[index];
                if (record.getAttributeAsString("Name").equals(name) && (recordIndex != index)) {
                    SC.say("The variable by the name of [" + name + "] already exists.");
                    return;
                }
            }
            records[recordIndex].setAttribute("Type", type);
            records[recordIndex].setAttribute("Name", name);
            parameterTypeGrid.setRecords(records);

            mtypeName.clearValue();
            mparameterName.clearValue();
            
            update.setDisabled(true);
            delete.setDisabled(true);
        }

    }


    /**
     * The delete parameter handler.
     */
    public class DeleteParameterHandler implements ClickHandler {

        /**
         * This method is called to delete the entry
         *
         * @param event The event to be delt with.
         */
        public void onClick(ClickEvent event) {
            ListGridRecord[] records = parameterTypeGrid.getRecords();
            ListGridRecord[] updatedRecords = new ListGridRecord[records.length - 1];
            int pos = 0;
            for (int index = 0; index < records.length; index++) {
                if (index == recordIndex) {
                    continue;
                } else {
                    updatedRecords[pos] = records[index];
                    pos++;
                }
            }
            parameterTypeGrid.setRecords(updatedRecords);

            mtypeName.clearValue();
            mparameterName.clearValue();
            
            update.setDisabled(true);
            delete.setDisabled(true);
        }

    }


    // private member variables
    private ListGrid parameterTypeGrid = null;

    // form values
    private SelectItem mtypeName;
    private TextItem mparameterName;
    private TextItem mvalue;

    // form buttons
    private Button add;
    private Button update;
    private Button delete;

    // record information
    private int recordIndex;

    /**
     * The constructor of the parameter canvas.
     */
    public ParameterCanvas(DataType[] parameters) {
        try {
            VLayout layout = new VLayout();
            layout.setIsGroup(true);
            layout.setGroupTitle("Custom Parameters");

            // parameters
            parameterTypeGrid = new ListGrid();
            parameterTypeGrid.setTitleField("Parameter List");
            parameterTypeGrid.setHeight(100);
            parameterTypeGrid.setAlternateRecordStyles(true);
            parameterTypeGrid.setWidth(410);
            ListGridField typeName = new ListGridField("Type", "type");
            ListGridField parameterName = new ListGridField("Name", "name");
            parameterTypeGrid.setFields(typeName,parameterName);
            parameterTypeGrid.setAutoFetchData(false);
            parameterTypeGrid.setShowFilterEditor(false);
            parameterTypeGrid.addRecordClickHandler(new RecordClickHandler() {
                public void onRecordClick(RecordClickEvent event) {
                    Record record = event.getRecord();
                    recordIndex = event.getRecordNum();
                    mtypeName.setValue(record.getAttributeAsString("Type"));
                    mparameterName.setValue(record.getAttributeAsString("Name"));
                    
                    update.setDisabled(false);
                    delete.setDisabled(false);
                }
            });


            layout.addMember(parameterTypeGrid);


            // parameter form
            DynamicForm form = new DynamicForm();
            
            this.mtypeName = new SelectItem("Type");
            this.mtypeName.setValueMap(TypeManager.getTypesForGroup(TypeManager.NONE_SCOPE_GROUPING[0]));

            this.mparameterName = new TextItem("Name");
            
            form.setFields(new FormItem[]{this.mtypeName,this.mparameterName});

            layout.addMember(form);


            HLayout buttonLayout = new HLayout();

            add = new Button("Add");
            add.addClickHandler(new AddParameterHandler());
            buttonLayout.addMember(add);
            update = new Button("Update");
            update.addClickHandler(new UpdateParameterHandler());
            update.setDisabled(true);
            buttonLayout.addMember(update);
            delete = new Button("Delete");
            delete.setDisabled(true);
            delete.addClickHandler(new DeleteParameterHandler());
            buttonLayout.addMember(delete);
            
            
            layout.addMember(buttonLayout);


            this.addChild(layout);

            setParameters(parameters);
        } catch (Exception ex) {
            SC.say("Failed to setup the parameter canvas : " + ex.getMessage());
        }
    }

    /**
     * This method sets the parameters
     *
     * @param parameters The new set of parameters
     */
    public void setParameters(DataType[] parameters) {
        if (parameters == null) {
            return;
        }
        for (DataType parameter : parameters) {
            ListGridRecord record = new ListGridRecord();
            record.setAttribute("Type", parameter.getBasicType());
            record.setAttribute("Name", parameter.getDataName());
            parameterTypeGrid.addData(record);

        }
    }


    /**
     * This method sets the parameters
     *
     * @param parameters The new set of parameters
     */
    public void setParameters(List<DataType> parameters) {
        if (parameters == null) {
            return;
        }
        for (DataType parameter : parameters) {
            ListGridRecord record = new ListGridRecord();
            record.setAttribute("Type", parameter.getBasicType());
            record.setAttribute("Name", parameter.getDataName());
            parameterTypeGrid.addData(record);

        }
    }


    /**
     * This method returns the parameters.
     *
     * @return The list of parameters.
     */
    public DataType[] getParameters() {
        try {
            ListGridRecord[] records = parameterTypeGrid.getRecords();
            DataType[] parameters = new DataType[records.length];
            for (int index = 0; index < records.length; index++) {
                ListGridRecord record = records[index];
                // set the value to the name to work around a list problem
                // that exists in the java script
                DataType type = TypeManager.getType(record.getAttributeAsString("Type"));
                type.setDataName(record.getAttributeAsString("Name"));
                parameters[index] = type;
            }
            return parameters;
        } catch (Exception ex) {
            SC.say("Failed to retrieve the parameters : " + ex.getMessage());
            return null;
        }
    }

}
