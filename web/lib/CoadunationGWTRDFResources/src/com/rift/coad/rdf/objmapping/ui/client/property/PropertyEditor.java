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

// package path
package com.rift.coad.rdf.objmapping.ui.client.property;

// smart gwt import
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.rift.coad.rdf.objmapping.ui.client.tree.ResourceTreeGrid;
import com.smartgwt.client.util.SC;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * This object represents the property infomration for the types.
 *
 * @author brett chaldecott
 */
public class PropertyEditor extends VLayout {




    public class ButtonHandler implements ClickHandler {

        public void onClick(ClickEvent event) {
            DataTypeTreeNode treeNode = (DataTypeTreeNode)treeGrid.getTree().findById(nodeId);
            form.getValue(treeNode);
            treeGrid.redraw();
        }


    }
    
    // private member variables
    private PropertyInterface form;
    private IButton button;
    private ResourceTreeGrid treeGrid;
    private DataTypeTreeNode treeNode;
    private ButtonHandler handler;
    private String type;
    private String nodeId;


    /**
     * The constructor of the property editor.
     */
    public PropertyEditor(ResourceTreeGrid grid) {
        this.treeGrid = grid;
        setWidth(150);
        setHeight100();
        
    }



    /**
     * This method sets the tree node that has been selected.
     *
     * @param treeNode The tree node that has been clicked on.
     */
    public void setTreeNode(DataTypeTreeNode treeNode) {
        try {
            if (treeNode.getType() == null) {
                return;
            } else if (treeNode.getType().getBasicType().equals(
                    "com.rift.coad.rdf.objmapping.base.str.ValidatedString")) {
                form = new VStringPropertyPanel();
                form.setValue(treeNode);
            } else if (treeNode.getType() != null) {
                form = new GeneralPropertyPanel();
                form.setValue(treeNode);
            } else {
                return;
            }
            this.removeMembers(this.getMembers());
            //this.clear();
            nodeId = treeNode.getNodeID();
            this.addMember((DynamicForm)form);
            button = new IButton("Apply Changes");
            button.addClickHandler(new ButtonHandler());
            this.addMember(button);
            this.draw();

        } catch (Exception ex) {
            SC.say("Error : " + ex.getMessage());
        }
    }
}
