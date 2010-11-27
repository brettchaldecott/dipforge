/*
 * CoadunationGWTRDFResources: The rdf resource object mappings.
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
 * ResourceManager.java
 */

package com.rift.coad.rdf.objmapping.ui.client.tree.resource;

// smart gwt imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.smartgwt.client.widgets.tree.Tree;

// resource base import
import com.rift.coad.rdf.objmapping.client.resource.ResourceBase;
import com.rift.coad.rdf.objmapping.ui.client.tree.DataTypeTreeNode;
import com.rift.coad.rdf.objmapping.ui.client.tree.type.TypeManager;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.TreeNode;
import java.util.Map;

/**
 * This object is responsible for returning the tree structures for a given
 * resource.
 *
 * @author brett chaldecott
 */
public class ResourceManager {
    private static int count = 10000;
    /**
     * This object constructs the tree for the given resource.
     *
     * @param tree The tree to construct the tree for.
     * @param resource The resource that the tree will be retrieved for.
     * @param entries The n
     */
    public static void getTree(Tree tree, ResourceBase resource, Map<String,DataType> entries) {
        DataTypeTreeNode[] nodes = null;
        if (resource instanceof com.rift.coad.rdf.objmapping.client.organisation.Organisation) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.person.User) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Forename", "firstnames", "root", "rdf/system-users.png", true, "",false),
                            new DataTypeTreeNode("Surname", "surname", "root", "rdf/system-users.png", true, "",false),
                            new DataTypeTreeNode("Username", "username", "root", "rdf/system-users.png", true, "",false),
                            new DataTypeTreeNode("Password", "password", "root", "rdf/system-lock-screen.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.person.Person) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("First Names", "firstnames", "root", "rdf/system-users.png", true, "",false),
                            new DataTypeTreeNode("Surname", "surname", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.resource.GenericResource) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.service.IPService) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Hostname", "hostname", "root", "rdf/network-server.png", true, "",false),
                            new DataTypeTreeNode("Name", "Name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.service.SoftwareService) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Hostname", "hostname", "root", "rdf/network-server.png", true, "",false),
                            new DataTypeTreeNode("Name", "Name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Hardware) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Host) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Hostname", "hostname", "root", "rdf/network-server.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Inventory) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Network) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Rack) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Software) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        } else if (resource instanceof com.rift.coad.rdf.objmapping.client.inventory.Stock) {
            nodes = new DataTypeTreeNode[] {
                            new DataTypeTreeNode("Id", "id", "root", "rdf/x-office-address-book.png", true, "",false),
                            new DataTypeTreeNode("Name", "name", "root", "rdf/system-users.png", true, "",false)};
        }

        tree.setData(nodes);
        TreeNode currentNode = tree.findById("root");
        addNodes(tree, resource, currentNode, entries);
    }


    /**
     * This method adds the nodes to the tree.
     *
     * @param tree The tree to add.
     * @param resource The resources.
     * @param currentNode The current node
     */
    private static void addNodes(Tree tree, ResourceBase resource, TreeNode currentNode, Map<String,DataType> entries) {
        if (resource.getAttributes()!= null) {
            for (DataType data : resource.getAttributes()) {
                try {
                    DataTypeTreeNode[] newNodes = TypeManager.getTree(data);
                    if (newNodes[0].getType() != null) {
                        try {
                            newNodes[0].setNodeID(data.getBasicType() + (++count));
                            newNodes[0].setID(data.getBasicType() + count);
                            entries.put(data.getBasicType() + count, data);
                        } catch (Exception ex) {
                            SC.say("Failed to set the node id value : " + ex.getMessage());
                        }
                    }
                    tree.addList(newNodes, currentNode);
                    if (data instanceof ResourceBase) {
                        addNodes(tree, (ResourceBase)data, newNodes[0], entries);
                    }
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
    }
}
