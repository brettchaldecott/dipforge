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
 * ResourceTreeGrid.java
 */

// package path
package com.rift.coad.rdf.objmapping.ui.client.tree;

// the tree grid
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.ui.client.tree.resource.ResourceManager;
import com.rift.coad.rdf.objmapping.util.client.type.TypeManager;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.DataArrivedEvent;
import com.smartgwt.client.widgets.tree.events.DataArrivedHandler;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * The tree grid that manages the displaying of a
 *
 * @author brett chaldecott
 */
public class ResourceTreeGrid extends TreeGrid {

    private ResourceBase resource;
    private ResourceTree tree;
    private int copyCount = 1;
    private String nodeId = null;
    // this is a nasty hack to deal with the fact that during Smart GWT java script
    // serialization and deserialization of the tree map it looses track of the
    // of similar object references.
    private Map<String,DataType> entries = new HashMap<String,DataType>();

    /**
     * The constructor of the resource grid.
     *
     * @param resource The resource object.
     */
    public ResourceTreeGrid(ResourceBase resource) {
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
        
        setCanReorderRecords(true);
        setCanAcceptDroppedRecords(true);
        setCanDragRecordsOut(true);
        
        TreeGridField field = new TreeGridField();
        field.setCanFilter(true);
        field.setName("name");
        field.setTitle("<b>"+resource.getIdForDataType()+"</b>");
        setFields(field);

        // create the tree and set the data
        tree = new ResourceTree();
        ResourceManager.getTree(tree, resource, entries);
        setData(tree);
        
        this.resource = resource;

        this.addFolderDropHandler(new FolderDropHandler() {

            public void onFolderDrop(FolderDropEvent event) {

                for (TreeNode node : event.getNodes()) {
                    DataTypeTreeNode typeNode = (DataTypeTreeNode)node;
                    if (typeNode.getType() != null) {
                        try {
                            typeNode.setType(TypeManager.duplicateType(typeNode.getType()));
                            typeNode.setNodeID(typeNode.getType().getBasicType() + (++copyCount));
                            typeNode.setID(typeNode.getType().getBasicType() + copyCount);
                            entries.put(typeNode.getType().getBasicType() + copyCount, typeNode.getType());
                        } catch (Exception ex) {
                            SC.say("Failed to copy the value : " + ex.getMessage());
                        }
                    }
                }
            }

        });


        this.addFolderClickHandler(new FolderClickHandler () {

            public void onFolderClick(FolderClickEvent event) {
                DataTypeTreeNode node = (DataTypeTreeNode)event.getFolder();
                if (node.getType() != null) {
                    nodeId = node.getNodeID();
                } else {
                    nodeId = null;
                }
            }

        });

        this.addLeafClickHandler(new LeafClickHandler() {

            public void onLeafClick(LeafClickEvent event) {
                DataTypeTreeNode node = (DataTypeTreeNode)event.getLeaf();
                if (node.getType() != null) {
                    nodeId = node.getNodeID();
                } else {
                    nodeId = null;
                }
            }

        });

        this.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
                if (((event.getKeyName().contains("Delete")) ||
                        (event.getKeyName().contains("Backspace")))
                        && (nodeId != null)) {
                    entries.remove(nodeId);
                    tree.remove(tree.findById(nodeId));
                }
            }

        });


    }

    public Map<String,DataType> getEntries() {
        Map<String,DataType> entries = new HashMap<String,DataType>();
        entries.putAll(this.entries);
        return entries;
    }

}
