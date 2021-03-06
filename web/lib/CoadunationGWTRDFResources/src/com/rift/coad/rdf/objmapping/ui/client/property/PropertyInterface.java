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
 * PropertyInterface.java
 */


// package path
package com.rift.coad.rdf.objmapping.ui.client.property;

// java imports
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;

/**
 * This object sets the values on the property interface.
 *
 * @author brett chaldecott
 */
public interface PropertyInterface {

    /**
     * This method sets the value of the tree node.
     * 
     * @param treeNode The value of the tree node.
     */
    public void setValue(DataTypeTreeNode treeNode);


    /**
     * This method retrieves the value from the property editor.
     *
     * @param treeNode
     */
    public void getValue(DataTypeTreeNode treeNode);

}
