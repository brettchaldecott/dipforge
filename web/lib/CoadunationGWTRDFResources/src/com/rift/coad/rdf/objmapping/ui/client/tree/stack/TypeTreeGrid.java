/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * TypeTreeGrid.java
 */

// package path
package com.rift.coad.rdf.objmapping.ui.client.tree.stack;

// the tree grid
import com.rift.coad.rdf.objmapping.ui.client.tree.*;
import com.rift.coad.rdf.objmapping.ui.client.tree.type.TypeManager;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.tree.TreeGrid;

/**
 * The tree grid that manages the displaying of a type information
 *
 * @author brett chaldecott
 */
public class TypeTreeGrid extends TreeGrid {

    private ResourceTree tree;


    /**
     * This constructor setups the type trees based on the information passed in.
     *
     * @param types The types to construct.
     * @throws StackException
     */
    public TypeTreeGrid(String[] types) throws StackException {
        try {
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

            setCanReorderRecords(false);
            setCanAcceptDroppedRecords(false);
            setCanDragRecordsOut(true);
            setDragDataAction(DragDataAction.COPY);

            // TODO: implement a drag start handler to copy the data type getting dragged
            //this.addDragStartHandler(handler)

            // create the tree and set the data
            tree = new ResourceTree();
            for (String type : types) {
                try {
                    TypeManager.getTree(tree, type);
                } catch (Exception ex) {
                    // ignore
                }
            }
            setData(tree);
        } catch (Exception ex) {
            throw new StackException("Failed to create the tree stack");
        }
    }
}
