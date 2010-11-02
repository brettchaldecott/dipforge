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
 * DependancyFactory.java
 */


package com.rift.coad.change.client.action.workflow;

import com.rift.coad.change.client.action.workflow.WorkflowFactory.WorkflowPanel;
import com.rift.coad.change.client.action.workflow.piece.resource.ResourceCache;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.List;

/**
 * The dependancy factory.
 *
 * @author brett chaldecott
 */
public class DependancyFactory {

    /**
     * This object represents the panel information
     */
    public class DependancyPanel extends Canvas {
        protected ListGrid parameterTypeGrid = null;


        /**
         * The default constructor for the dependancy panel.
         */
        public DependancyPanel() {

            // dynamic form
            VLayout verticalLayout = new VLayout();
            verticalLayout.setWidth100();
            verticalLayout.setHeight100();
            
            // parameters
            DynamicForm form = new DynamicForm();
            parameterTypeGrid = new ListGrid();
            parameterTypeGrid.setTitleField("Parameter List");
            parameterTypeGrid.setHeight(300);
            parameterTypeGrid.setAlternateRecordStyles(true);
            parameterTypeGrid.setSelectionType(SelectionStyle.SIMPLE);
            parameterTypeGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
            parameterTypeGrid.setWidth(340);
            ListGridField actionName = new ListGridField("Name", "name", 340 - 29);
            parameterTypeGrid.setFields(actionName);
            parameterTypeGrid.setAutoFetchData(false);
            parameterTypeGrid.setShowFilterEditor(false);

            // the list of parameters
            CanvasItem item = new CanvasItem();
            item.setColSpan(2);
            item.setName("Dependancies");
            item.setCanvas(parameterTypeGrid);

            // set the fields for the form
            form.setFields(item);
            verticalLayout.addMember(form);


            HLayout hlayout = new HLayout();
            hlayout.setWidth100();
            hlayout.setPadding(5);
            Button applyButton = new Button("Apply");
            applyButton.setPadding(5);
            applyButton.addClickHandler(new ClickHandler() {

                    public void onClick(ClickEvent event) {
                        handleApply();
                    }
                });
            hlayout.addMember(applyButton);

            Button cancelButton = new Button("Cancel");
            cancelButton.setPadding(5);
            cancelButton.addClickHandler(new ClickHandler() {

                    public void onClick(ClickEvent event) {
                        handleCancel();
                    }
                });
            hlayout.addMember(cancelButton);
            
            verticalLayout.addMember(hlayout);

            
            this.addChild(verticalLayout);

            // set the information
            setInfo();
        }


        /**
         * This method sets the grid information
         */
        private void setInfo() {
            try {
                List<ResourceBase> resources = ResourceCache.getInstance().getResources();
                ListGridRecord[] records = new ListGridRecord[resources.size()];
                String[] types = new String[resources.size()];
                String type = panel.getWorkflowCanvas().getType();
                for (int index = 0; index < resources.size(); ++index) {
                    if (type.equals(resources.get(index).getIdForDataType())) {
                        continue;
                    }
                    ListGridRecord record = new ListGridRecord();
                    record.setAttribute("Name", resources.get(index).getIdForDataType());
                    parameterTypeGrid.addData(record);
                    if (isSelected(resources.get(index).getIdForDataType())) {
                        parameterTypeGrid.selectRecord(record);
                    }
                }

            } catch (Exception ex) {
                SC.say("Failed to set the list of types : " + ex.getMessage());
            }
        }

        /**
         * This method is called to check if a type is selected.
         *
         * @param name The name of the type to perform the check on.
         * @return TRUE if selected, FALSE if not.
         */
        private boolean isSelected(String name) {
            String[] types = panel.getWorkflowCanvas().getDependancies();
            if (types == null) {
                return false;
            }
            for (String type : types) {
                if (name.equals(type)) {
                    return true;
                }
            }
            return false;
        }


        /**
         * This method is called to handle the update call.
         */
        private void handleApply() {
            ListGridRecord[] record = parameterTypeGrid.getSelection();
            if ((record == null) || (record.length == 0)) {
                panel.getWorkflowCanvas().setDependancies(null);
                return;
            }
            String[] dependancies = new String[record.length];
            for (int index = 0; index < record.length; index++) {
                dependancies[index] = record[index].getAttributeAsString("Name");
            }
            panel.getWorkflowCanvas().setDependancies(dependancies);
            win.setVisible(false);
        }


        /**
         * This method is called to cancel the changes.
         */
        private void handleCancel() {
            win.setVisible(false);
        }

    }


    // private member variables
    private WorkflowFactory.WorkflowPanel panel;
    private Window win = null;

    public DependancyFactory(WorkflowPanel panel) {
        this.panel = panel;
    }



    /**
     * This method is called to create the window that the mapping information will be
     * presented in.
     */
    public void createWindow() {
        if (win == null) {
            win = new Window();
            win.setTitle("Dependencies");
            win.setKeepInParentRect(true);
            win.setWidth(500);
            win.setHeight(400);
            int windowTop = 40;
            int windowLeft = panel.getWidth()- (win.getWidth() + 20);
            win.setLeft(windowLeft);
            win.setTop(windowTop);
            win.setCanDragReposition(true);
            win.setCanDragResize(true);
            win.setMembersMargin(5);

            win.addItem(new DependancyPanel());

            panel.addChild(win);
        }
        win.draw();
    }

}
