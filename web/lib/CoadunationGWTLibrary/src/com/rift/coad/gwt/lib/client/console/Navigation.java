/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
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
 * Console.java
 */
package com.rift.coad.gwt.lib.client.console;

// smart gwt imports
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderOpenedEvent;
import com.smartgwt.client.widgets.tree.events.FolderOpenedHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;

/**
 * The navigation object that appears on the left hand side of the of the
 * console.
 *
 * @author brett chaldecott
 */
public class Navigation extends TreeGrid {

    // private member variables
    private Tree tree;

    /**
     * The default constructor of the navigation tree grid
     */
    public Navigation(String title, Console console, NavigationDataHandler handler) {
        setWidth100();
        setHeight100();
        setCustomIconProperty("icon");
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);
        setNodeIcon("silk/application_view_list.png");
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setLoadDataOnDemand(false);
        setCanSort(false);

        TreeGridField field = new TreeGridField();
        field.setCanFilter(true);
        field.setName("name");
        field.setTitle("<b>"+title+"</b>");
        setFields(field);

        // create the tree
        tree = new NavigationTree(console, handler);

        // set the data for the navigation
        this.addFolderOpenedHandler(new FolderOpenedHandler() {

            public void onFolderOpened(FolderOpenedEvent event) {
                TreeNode node = event.getNode();
                if (tree.hasChildren(node) && tree.isOpen(node)) {
                    tree.closeFolder(node);
                } else {
                    tree.openFolder(node);
                }
            }

        });


        setData(tree);

    }
    
}
