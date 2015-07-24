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
 * ActionsFactory.java
 */

// package path
package com.rift.coad.change.client.action;

// smart gwt imports
import com.smartgwt.client.widgets.Canvas;


// console import
import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.List;

/**
 * This object is responsible for instanciating the action 
 *
 * @author brett chaldecott
 */
public class ActionsFactory implements PanelFactory {

    /**
     * This object is responsible for displaying the action information.
     */
    public class ActionsPanel extends ConsolePanel implements ClickHandler {

        private ListGrid actionGrid = null;
        private IButton deleteButton = null;
        private TextItem action = null;
        private IButton addButton = null;


        /**
         * The default constructor.
         */
        public ActionsPanel() {
        }


        /**
         * This method returns the view panel
         *
         * @return The reference to the panel
         */
        @Override
        public Canvas getViewPanel() {

            VLayout canvas = new VLayout();
            canvas.setAlign(Alignment.CENTER);

            actionGrid = new ListGrid();
            actionGrid.setTitleField("Action List");
            actionGrid.setHeight(300);
            actionGrid.setAlternateRecordStyles(true);
            actionGrid.setSelectionType(SelectionStyle.SIMPLE);
            actionGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);


            actionGrid.setWidth(410);
            ListGridField actionName = new ListGridField("Name", "name", 410 - 29);
            actionGrid.setFields(actionName);
            actionGrid.setAutoFetchData(false);
            actionGrid.setShowFilterEditor(false);
            setGridInfo();
            canvas.addMember(actionGrid);

            HLayout bottomLayout = new HLayout();

            deleteButton = new IButton("Delete");
            deleteButton.addClickHandler(this);
            bottomLayout.addMember(deleteButton);

            final DynamicForm form = new DynamicForm();
            action = new TextItem();
            action.setRequired(true);
            action.setTitle("Action");
            form.setFields(action);
            bottomLayout.addMember(form);

            addButton = new IButton("Add");
            addButton.addClickHandler(this);
            bottomLayout.addMember(addButton);

            canvas.addMember(bottomLayout);
            
            return canvas;

        }


        /**
         * This method returns the name of the action manager.
         *
         * @return The string containing then name of this panel
         */
        @Override
        public String getName() {
            return "ActionManager";
        }

        /**
         * Set the grid information
         */
        private void setGridInfo() {
            List<String> actions = ActionsCache.getInstance().getActions();
            ListGridRecord[] records = new ListGridRecord[actions.size()];
            for (int index = 0; index < actions.size(); ++index) {
                records[index] = new ListGridRecord();
                records[index].setAttribute("Name", actions.get(index));

            }
            actionGrid.setData(records);
        }


        /**
         * Handle the onclick action
         *
         * @param event The event
         */
        public void onClick(ClickEvent event) {
            if (event.getSource().equals(deleteButton)) {
                ListGridRecord[] records = actionGrid.getSelection();
                for (ListGridRecord record : records) {
                    ActionsCache.getInstance().removeAction(record.getAttributeAsString("Name"));
                }
                setGridInfo();
            } else if (event.getSource().equals(addButton)) {
                ActionsCache.getInstance().addAction(action.getValue().toString());
                action.setValue("");
                setGridInfo();
            }
        }


        
        
    }

    /**
     * The default constructor
     */
    public ActionsFactory() {
    }


    /**
     * This method returns the newly created canvas
     * 
     * @return The reference to the newly created canvas
     */
    public Canvas create() {
        return new ActionsPanel();
    }


    /**
     * The id of the canvas.
     * @return
     */
    public String getID() {
        return "ActionsCanvasFactory";
    }


    /**
     * This method return a description of the action factory.
     *
     * @return The description of the action factory
     */
    public String getDescription() {
        return "Actions Factory Manager";
    }

}
