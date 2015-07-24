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
 * DataTypeTreeNode.java
 */

package com.rift.coad.rdf.objmapping.ui.client.tree;

import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 *
 * @author brett
 */
public class DataTypeTreeNode extends TreeNode {
    // private member variables
    private DataType type;

    /**
     * The constructor that sets the default values.
     *
     * @param name The name of the data type.
     * @param icon The path
     * @param isFolder TRUE if this node is a folder.
     */
    public DataTypeTreeNode(String name, String icon, boolean isFolder) {
        this(name, null, null, icon, null, true, "", isFolder);
    }


    /**
     * The constructor that sets the default values.
     *
     * @param name The name of the data type.
     * @param icon The path
     * @param type The data type.
     * @param isFolder TRUE if this node is a folder.
     * @param children The list of children.
     */
    public DataTypeTreeNode(String name, String icon, DataType type, boolean isFolder,
            DataTypeTreeNode... children) {
        this(name, null, null, icon, type, true, "", isFolder, children);
    }
    

    /**
     * The constructor that sets the default values.
     *
     * @param name The name of the data type.
     * @param nodeID The node id.
     * @param parentNodeID The parent node id.
     * @param icon The path
     * @param enabled True if this node is enabled.
     * @param idSuffix The id suffix for this node.
     * @param isFolder TRUE if this node is a folder.
     */
    public DataTypeTreeNode(String name, String nodeID, String parentNodeID,
            String icon, boolean enabled, String idSuffix, boolean isFolder) {
        this(name, nodeID, parentNodeID, icon, null, enabled, idSuffix, isFolder);
    }


    /**
     * The constructor that sets the default values.
     *
     * @param name The name of the data type.
     * @param nodeID The node id.
     * @param parentNodeID The parent node id.
     * @param icon The path
     * @param enabled True if this node is enabled.
     * @param idSuffix The id suffix for this node.
     * @param isFolder TRUE if this node is a folder.
     * @param children The list of children.
     */
    public DataTypeTreeNode(String name, String nodeID, String parentNodeID,
            String icon, boolean enabled, String idSuffix, boolean isFolder,
            DataTypeTreeNode... children) {
        this(name, nodeID, parentNodeID, icon, null, enabled, idSuffix, isFolder, children);
    }


    /**
     * This constructor sets up the data type node.
     *
     * @param name The name of the data type.
     * @param nodeID The node id.
     * @param parentNodeID The parent node id.
     * @param icon The icon.
     * @param type The type information.
     * @param enabled If the node is enabled.
     * @param idSuffix The id suffix.
     * @param isFolder This is folder
     */
    public DataTypeTreeNode(String name, String nodeID, String parentNodeID,
            String icon, DataType type, boolean enabled, String idSuffix, boolean isFolder) {
        if (enabled) {
            setName(name);
        } else {
            setName("<span style='color:808080'>" + name + "</span>");
        }
        if (nodeID != null) {
            setNodeID(nodeID.replace("-", "_") + idSuffix);
        }
        if (parentNodeID != null) {
            setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
        }
        setIcon(icon);
        setType(type);
        this.setIsFolder(isFolder);
    }


    /**
     * This constructor sets up the data type node.
     *
     * @param name The name of the data type.
     * @param nodeID The node id.
     * @param parentNodeID The parent node id.
     * @param icon The icon.
     * @param type The type information.
     * @param enabled If the node is enabled.
     * @param idSuffix The id suffix.
     * @param isFolder This is folder
     * @param children The children nodes.
     */
    public DataTypeTreeNode(String name, String nodeID, String parentNodeID,
            String icon, DataType type, boolean enabled, String idSuffix, boolean isFolder,
            DataTypeTreeNode... children) {
        if (enabled) {
            setName(name);
        } else {
            setName("<span style='color:808080'>" + name + "</span>");
        }
        if (nodeID != null) {
            setNodeID(nodeID.replace("-", "_") + idSuffix);
        }
        if (parentNodeID != null) {
            setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
        }
        setIcon(icon);
        setType(type);
        setAttribute("children", children);
        this.setIsFolder(isFolder);
    }


    /**
     * This method sets the data type for this object.
     *
     * @param type The data type for this object.
     */
    public void setType(DataType type) {
        setAttribute("type",type);
    }


    /**
     * This method returns the type information for this object.
     *
     * @return The string containing the type information.
     */
    public DataType getType() {
        return (DataType) getAttributeAsObject("type");
    }


    /**
     * This method sets the node id.
     *
     * @param value Its the node id value.
     */
    public void setNodeID(String value) {
        setAttribute("nodeID", value);
    }

    public String getNodeID() {
        return getAttribute("nodeID");
    }

    public void setParentNodeID(String value) {
        setAttribute("parentNodeID", value);
    }

    public String getParentNodeId() {
        return getAttribute("parentNodeID");
    }

    public void setName(String name) {
        setAttribute("name", name);
    }

    public String getName() {
        return getAttributeAsString("name");
    }

    public void setIcon(String icon) {
        setAttribute("icon", icon);
    }

    public String getIcon() {
        return getAttributeAsString("icon");
    }

    public void setIsOpen(boolean isOpen) {
        setAttribute("isOpen", isOpen);
    }

    public void setIconSrc(String iconSrc) {
        setAttribute("iconSrc", iconSrc);
    }

    public String getIconSrc() {
        return getAttributeAsString("iconSrc");
    }
}
