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
 * WorkflowFactory.java
 */


// package path
package com.rift.coad.change.client.action.workflow;

import com.rift.coad.gwt.lib.client.console.ConsolePanel;
import com.rift.coad.gwt.lib.client.console.PanelFactory;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This object is responsible for managing the display of the workflows.
 *
 * @author brett chaldecott
 */
public class WorkflowFactory implements PanelFactory {


    /**
     * This object manages the workflow.
     */
    public class WorkflowPanel extends ConsolePanel implements ClickHandler {

        // private member variables
        private IButton button;
        private WorkflowCanvas workflowCanvas;

        /**
         * This constructor sets up the private member variables
         *
         * @param actionName The name of the action.
         * @param type The type of the action.
         */
        public WorkflowPanel() {
        }


        /**
         * This method returns the newly created canvas.
         *
         * @return The canvas.
         */
        @Override
        public Canvas getViewPanel() {
            VLayout layout = new VLayout();
            layout.setWidth100();
            layout.setHeight100();
            workflowCanvas = new WorkflowCanvas(type,actionName);
            workflowCanvas.setAction(actionName);
            workflowCanvas.setType(type);
            workflowCanvas.setWidth100();
            workflowCanvas.setHeight100();
            layout.addMember(workflowCanvas);

            VLayout spacer = new VLayout();
            spacer.setWidth("*");
            spacer.setHeight(10);
            layout.addMember(spacer);

            button = new IButton("Save");
            button.addClickHandler(this);
            layout.addMember(button);
            return layout;
        }

        /**
         * This method returns the name of this workflow entry
         * @return
         */
        @Override
        public String getName() {
            return type + ":" + actionName;
        }


        /**
         * This method handles the click events.
         *
         * @param event The click.
         */
        public void onClick(ClickEvent event) {
            workflowCanvas.saveActionDefinition();
        }

        
        /**
         * Return the reference to the workflow canvas.
         * 
         * @return The reference to the workflow canvas.
         */
        public WorkflowCanvas getWorkflowCanvas() {
            return workflowCanvas;
        }



    }

    private WorkflowPanel panel = null;
    private String actionName;
    private String type;


    /**
     * This constructor sets up the workflow factory.
     *
     * @param actionName The name of the action.
     * @param type The type of the action.
     */
    public WorkflowFactory(String actionName, String type) {
        this.actionName = actionName;
        this.type = type;
    }


    /**
     * This method is responsible for creating the workflow factory entry.
     *
     * @return The reference to the workflow factory.
     */
    public Canvas create() {
        if (panel != null) {
            return panel;
        }
        panel = new WorkflowPanel();
        return panel;
    }

    
    /**
     * This method returns the panel
     * 
     * @return The reference to the panel.
     */
    public WorkflowPanel getPanel() {
        return panel;
    }





    /**
     * The is method returns an id for the workflow factory instance.
     *
     * @return The string containing the id of the workflow factory instance.
     */
    public String getID() {
        return type + ":" + actionName;
    }


    /**
     * The description of the workflow factory.
     *
     * @return The string containing the description of the workflow factory.
     */
    public String getDescription() {
        return "Workflow manager for [" + type + ":" + actionName + "]";
    }


    
}
