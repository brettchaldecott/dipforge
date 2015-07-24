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
 * PieceCall.java
 */

// package path
package com.rift.coad.change.client.action.workflow.piece.call;

// imports
import com.rift.coad.change.client.action.workflow.ParamInfoFilter;
import com.rift.coad.change.client.action.workflow.PieceBin;
import com.rift.coad.change.client.action.workflow.piece.resource.ResourceCache;
import com.rift.coad.change.rdf.objmapping.client.change.task.Call;
import com.rift.coad.datamapperbroker.client.rdf.DataMapperMethod;
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * The parameter mapping canvas information
 *
 * @author brett chaldecott
 */
public class ParameterMappingCanvas extends DynamicForm {



    /**
     * This object handles
     */
    public class ParameterRowHandler implements RecordClickHandler {

        /**
         * This method is called to handle the on record click event.
         *
         * @param event The event to process.
         */
        public void onRecordClick(RecordClickEvent event) {
            try {
                Record record = event.getRecord();

                String type = record.getAttributeAsString("Target");
                if (ResourceCache.getInstance().isSystemResource(type)) {
                    target.clearValue();
                    target.setDisabled(true);
                    source.clearValue();
                    source.setDisabled(true);
                    return;
                }

                type = type.substring(0,type.indexOf(" "));


                recordIndex = event.getRecordNum();
                target.setValue(record.getAttributeAsString("Target"));
                source.setValueMap(ParamInfoFilter.convertParameterToArray(
                        ParamInfoFilter.filterParameters(bin.listParameters(),
                        type)));
                if (record.getAttributeAsString("Source") != null) {
                    source.setValue(record.getAttributeAsString("Source"));
                } else {
                    source.clearValue();
                }

                target.setDisabled(false);
                source.setDisabled(false);
            } catch (Exception ex) {
                SC.say("Failed to handle the event : " + ex.getMessage());
            }
        }
    }

    /**
     * This object handles the source change events.
     */
    public class SourceChangedHandler implements ChangedHandler {

        /**
         * This method deals with the changed events.
         *
         * @param event The event to deal with
         */
        public void onChanged(ChangedEvent event) {
            if (event.getValue() == null) {
                ListGridRecord[] records = parameterTypeGrid.getRecords();
                records[recordIndex].setAttribute("Source", (String)null);
                parameterTypeGrid.setRecords(records);
            } else {
                ListGridRecord[] records = parameterTypeGrid.getRecords();
                records[recordIndex].setAttribute("Source", event.getValue().toString());
                parameterTypeGrid.setRecords(records);
            }
        }

    }


    // parameters
    private PieceBin bin;
    private ListGrid parameterTypeGrid;
    private TextItem target;
    private SelectItem source;
    private int recordIndex;

    /**
     * The default constructor
     */
    public ParameterMappingCanvas(PieceBin bin, Call call) {
        this.bin = bin;
        setIsGroup(true);
        setGroupTitle("Parameters");

        parameterTypeGrid = new ListGrid();
        parameterTypeGrid.setTitleField("Parameter List");
        parameterTypeGrid.setHeight(100);
        parameterTypeGrid.setAlternateRecordStyles(true);
        parameterTypeGrid.setWidth(450);
        ListGridField target = new ListGridField("Target", "target");
        target.setWidth(350);
        ListGridField source = new ListGridField("Source", "source");
        source.setWidth(100);

        parameterTypeGrid.setFields(target, source);
        parameterTypeGrid.setAutoFetchData(false);
        parameterTypeGrid.setShowFilterEditor(false);
        parameterTypeGrid.setDisabled(true);

        parameterTypeGrid.addRecordClickHandler(new ParameterRowHandler());


        CanvasItem item = new CanvasItem();
        item.setName("Config");
        item.setCanvas(parameterTypeGrid);

        this.target = new TextItem();
        this.target.setName("Target");
        this.target.setDisabled(true);
        this.target.setWidth(450);

        this.source = new SelectItem();
        this.source.setName("Source");
        this.source.setDisabled(true);
        this.source.setWidth(450);
        this.source.addChangedHandler(new SourceChangedHandler());

        setFields(item, this.target, this.source);


        if (call != null) {
            setMethod(call);
        }

    }

    /**
     * This sets the method information
     *
     * @param method
     */
    public void setMethod(DataMapperMethod method) {
        //parameterTypeGrid.setDisabled(false);
        for (DataType parameter : method.getAttributes()) {
            ListGridRecord record = new ListGridRecord();
            if (!ResourceCache.getInstance().isSystemResource(parameter.getIdForDataType())) {
                record.setAttribute("Target", parameter.getIdForDataType() + " " +
                        parameter.getDataName());
                
            } else {
                record.setAttribute("Target", parameter.getIdForDataType());
                record.setAttribute("Source", parameter.getDataName());
            }

            parameterTypeGrid.addData(record);
        }
    }


    /**
     * This sets the method information
     *
     * @param method
     */
    private void setMethod(Call call) {
        //parameterTypeGrid.setDisabled(false);
        DataMapperMethod method = call.getDataMapperMethod();

        for (int index = 0; index < method.getAttributes().length; index++) {
            DataType parameter = method.getAttributes()[index];
            ListGridRecord record = new ListGridRecord();
            if (!ResourceCache.getInstance().isSystemResource(parameter.getIdForDataType())) {
                record.setAttribute("Target", parameter.getIdForDataType() + " " +
                        parameter.getDataName());
                record.setAttribute("Source", call.getParameters()[index]);
            } else {
                record.setAttribute("Target", parameter.getIdForDataType());
                record.setAttribute("Source", parameter.getDataName());
            }

            parameterTypeGrid.addData(record);
        }
    }


    /**
     * This method returns a list of parameters
     * @return The list of parameters.
     */
    public String[] getParameters() throws Exception {
        ListGridRecord[] records = parameterTypeGrid.getRecords();
        List<String> parameters = new ArrayList<String>();
        for (ListGridRecord record : records) {
            if (record.getAttribute("Source") == null) {
                throw new Exception("Must provide a name mapping for parameters in a call [" +
                        record.getAttribute("Target") + "]");
            }
            parameters.add(record.getAttribute("Source"));
        }
        return parameters.toArray(new String[0]);
    }
    

    /**
     * The clear method
     */
    public void clearMethod() {
        //parameterTypeGrid.setDisabled(true);
        Record[] records = parameterTypeGrid.getRecords();
        for (Record record : records) {
            parameterTypeGrid.removeData(record);
        }

        this.target.clearValue();
        this.target.setDisabled(true);

        this.source.clearValue();
        this.source.setDisabled(true);
    }
}
