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
 * VStringPropertyPanel.java
 */

package com.rift.coad.rdf.objmapping.ui.client.property;

import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 *
 * @author brett
 */
public class VStringPropertyPanel extends DynamicForm implements PropertyInterface {

    // private member variables
    private StaticTextItem basicType;
    private TextItem idForDataType;
    private TextItem dataName;
    private TextItem regex;

    /**
     * The default constructor
     */
    public VStringPropertyPanel() {
        basicType = new StaticTextItem();
        basicType.setTitle("Name");

        idForDataType = new TextItem();
        idForDataType.setTitle("Type");

        dataName = new TextItem();
        dataName.setTitle("Name");

        regex = new TextItem();
        regex.setTitle("Regex");

        setFields(new FormItem[]{basicType, idForDataType, dataName, regex});
    }


    /**
     * This method sets the value of the tree node.
     *
     * @param treeNode The tree node value
     */
    public void setValue(DataTypeTreeNode treeNode) {
        com.rift.coad.rdf.objmapping.client.base.str.ValidatedString type =
                        (com.rift.coad.rdf.objmapping.client.base.str.ValidatedString)treeNode.getType();
        basicType.setValue(type.getBasicType());
        dataName.setValue(type.getDataName());
        idForDataType.setValue(type.getIdForDataType());
        regex.setValue(type.getRegex());

    }


    /**
     * This method retrieves the value.
     *
     * @param treeNode The node value.
     */
    public void getValue(DataTypeTreeNode treeNode) {
        com.rift.coad.rdf.objmapping.client.base.str.ValidatedString type =
                        (com.rift.coad.rdf.objmapping.client.base.str.ValidatedString)treeNode.getType();
        type.setDataName((String) dataName.getValue());
        type.setIdForDataType((String)idForDataType.getValue());
        type.setRegex((String)regex.getValue());
        treeNode.setName((String) dataName.getValue());
    }

}
