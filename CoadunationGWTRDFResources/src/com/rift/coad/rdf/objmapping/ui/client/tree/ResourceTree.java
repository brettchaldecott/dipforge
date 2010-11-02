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
 * TypeManager.java
 */

// package path
package com.rift.coad.rdf.objmapping.ui.client.tree;

// smart gwt imports
import com.smartgwt.client.widgets.tree.Tree;

// coadunation imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.types.TreeModelType;

/**
 * This object represents a resource tree
 *
 * @author brett chaldecott
 */
public class ResourceTree extends Tree {
    /**
     * This constructor sets up the type information.
     * 
     * @param type The type.
     */
    public ResourceTree() {
        this.setModelType(TreeModelType.PARENT);
        this.setNameProperty("name");
        this.setOpenProperty("isOpen");
        this.setIdField("nodeID");
        this.setParentIdField("parentNodeID");
        this.setRootValue("root");
        //this.setShowRoot(true);

    }
}
