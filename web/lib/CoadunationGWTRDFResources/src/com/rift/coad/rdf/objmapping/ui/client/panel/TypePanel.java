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
 * TypeManager.java
 */

// the type panel
package com.rift.coad.rdf.objmapping.ui.client.panel;


// java imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import java.util.ArrayList;
import java.util.List;

// smart gwt imports
import com.smartgwt.client.widgets.layout.HLayout;

// imports
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.ui.client.property.PropertyEditor;
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.rift.coad.rdf.objmapping.ui.client.tree.ResourceTreeGrid;
import com.rift.coad.rdf.objmapping.ui.client.tree.stack.TypeStack;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import java.util.Iterator;
import java.util.Map;

/**
 * The type panel.
 *
 * @author brett chaldecott
 */
public class TypePanel extends HLayout {

    // private member variables
    private ResourceBase resource;
    private TypeStack typeStack;
    private ResourceTreeGrid treeGrid;
    private PropertyEditor propertyEditor;

    /**
     * This constructor is responsible for setting up the type panel.
     *
     * @param resource The resource information.
     */
    public TypePanel(ResourceBase resource) {
        this.resource = resource;
        setHeight100();
        setWidth100();
        typeStack = new TypeStack();
        typeStack.setShowResizeBar(true);
        addMember(typeStack);
        treeGrid = new ResourceTreeGrid(resource);
        treeGrid.addLeafClickHandler(new LeafClickHandler() {

            public void onLeafClick(LeafClickEvent event) {
                propertyEditor.setTreeNode((DataTypeTreeNode)event.getLeaf());
            }

        });
        treeGrid.addFolderClickHandler(new FolderClickHandler() {

            public void onFolderClick(FolderClickEvent event) {
                propertyEditor.setTreeNode((DataTypeTreeNode)event.getFolder());
            }

        });
        addMember(treeGrid);
        propertyEditor = new PropertyEditor(treeGrid);
        addMember(propertyEditor);

    }


    /**
     * This method returns the resource information
     *
     * @return This method returns the resource.
     */
    public ResourceBase getResource() {
        resource.setAttributes(
                getTypes(treeGrid.getTree().getChildren(treeGrid.getTree().getRoot()),
                treeGrid.getEntries()));
        return resource;
    }


    /**
     * This method returns the list of nodes
     * @param nodes
     * @return
     */
    private DataType[] getTypes(TreeNode[] nodes, Map<String,DataType> entries) {
        List<DataType> types = new ArrayList<DataType>();
        String output = "";
        for (int index = 0; index < nodes.length; index++) {
            DataTypeTreeNode dataNode = (DataTypeTreeNode)nodes[index];
            output += dataNode.getName() + ":" + dataNode.getNodeID() + ",";
            if (dataNode.getType() != null) {
                String id = getID(dataNode.getType(), entries);
                if (id == null) {
                    continue;
                }
                DataType typeInfo =
                        ((DataTypeTreeNode)treeGrid.getTree().findById(id)).getType();
                if (typeInfo instanceof
                        com.rift.coad.rdf.objmapping.client.resource.ResourceBase) {
                    com.rift.coad.rdf.objmapping.client.resource.ResourceBase resource =
                            (com.rift.coad.rdf.objmapping.client.resource.ResourceBase)typeInfo;
                    resource.setAttributes(getTypes(treeGrid.getTree().getChildren(dataNode),entries));
                }
                types.add(dataNode.getType());
            }

        }
        //SC.say("Number of attributes is [" + output + "] tree : " + treeGrid.getTree().toString());
        return types.toArray(new DataType[0]);
    }


    /**
     * This method returns the basic type for this object.
     * 
     * @return The basic type for this object.
     */
    public String getBasicType() {
        return resource.getBasicType();
    }



    /**
     * This method returns the id of the type.
     * @param typeInfo
     * @param entries
     * @return
     */
    private String getID(DataType typeInfo, Map<String,DataType> entries) {
        for (Iterator<String> iter = entries.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            DataType value = entries.get(key);
            if (value.getBasicType().equals(typeInfo.getBasicType())) {
                entries.remove(key);
                return key;
            }
        }
        return null;
    }
}
