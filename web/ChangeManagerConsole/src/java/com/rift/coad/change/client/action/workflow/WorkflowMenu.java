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
 * WorkflowMenu.java
 */


// package path
package com.rift.coad.change.client.action.workflow;

// smart gwt imports
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import java.util.ArrayList;
import java.util.List;


/**
 * The object that represents and manages a workflow.
 *
 * @author brett chaldecott
 */
public class WorkflowMenu extends Menu implements ItemClickHandler {

    private WorkflowFactory.WorkflowPanel panel;

    /**
     * This constructor sets up the workflow menu.
     *
     * @param canvas The canvas.
     */
    public WorkflowMenu(WorkflowFactory.WorkflowPanel panel) {
        this.panel = panel;

        List<MenuItem> items = new ArrayList<MenuItem>();

        MenuItem dependanciesEntry = new MenuItem("Dependancies");
        items.add(dependanciesEntry);

        setItems(items.toArray(new MenuItem[0]));
        
        addItemClickHandler(this);
    }
    


    /**
     * This method handles the onclick event.
     *
     * @param event The event to handle.
     */
    public void onItemClick(ItemClickEvent event) {
        if (event.getItem().getTitle().equalsIgnoreCase("Dependancies")) {
            new DependancyFactory(panel).createWindow();
        }
        
    }

}
