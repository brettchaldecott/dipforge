/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
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
 * ScopeMenu.java
 */

// package path
package com.rift.coad.script.client.files.php;

// smart gwt imports
import com.rift.coad.gwt.lib.client.console.NavigationDataCallback;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author brett chaldecott
 */
public class ScopeMenu extends Menu implements ItemClickHandler {

    // private member variables
    private NavigationDataCallback callback;
    
    /**
     * The default constructor for the scope menu.
     */
    public ScopeMenu(NavigationDataCallback callback) {
        this.callback = callback;
        List<MenuItem> items = new ArrayList<MenuItem>();

        MenuItem createEntry = new MenuItem("Create");
        items.add(createEntry);

        setItems(items.toArray(new MenuItem[0]));

        addItemClickHandler(this);
    }




    /**
     * 
     * @param event
     */
    public void onItemClick(ItemClickEvent event) {
        if (event.getItem().getTitle().equalsIgnoreCase("Create")) {
            new CreateFileFactory(callback).createWindow();
        }
    }

}
