/*
 * CoadunationRDFResources: The rdf resource object mappings.
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
 * GeneralPropertyPanel.java
 */

// package path
package com.rift.coad.rdf.objmapping.ui.client.property;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * The general property panel.
 *
 * @author brett chaldecott
 */
public class GeneralPropertyPanel extends DynamicForm implements PropertyInterface {

    // private member variables
    private StaticTextItem basicType;
    private TextItem idForDataType;
    private TextItem dataName;

    /**
     * The default constructor
     */
    public GeneralPropertyPanel() {
        basicType = new StaticTextItem();
        basicType.setTitle("Name");

        idForDataType = new TextItem();
        idForDataType.setTitle("Type");

        dataName = new TextItem();
        dataName.setTitle("Name");

        setFields(new FormItem[]{basicType, idForDataType, dataName});

    }

    /**
     * This method is called to set the value of the property fields.
     *
     * @param treeNode The tree node.
     */
    public void setValue(DataTypeTreeNode treeNode) {
        DataType type = treeNode.getType();
        basicType.setValue(type.getBasicType());
        dataName.setValue(type.getDataName());
        idForDataType.setValue(type.getIdForDataType());
    }


    /**
     * This method sets the value within the tree node.
     *
     * @param treeNode The tree node.
     */
    public void getValue(DataTypeTreeNode treeNode) {
        DataType type = treeNode.getType();
        type.setDataName((String) dataName.getValue());
        type.setIdForDataType((String)idForDataType.getValue());
        treeNode.setName((String) dataName.getValue());
    }
}
