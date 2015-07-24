/*
 * CoadunationGWTLibrary: The default console for the coadunation applications.
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
 * NavigationTree.java
 */

// package path
package com.rift.coad.gwt.lib.client.console;

// the tree this object inherits from.
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * This object manages the the tree information on behalf of the tree grid.
 *
 * @author brett chaldecott
 */
public class NavigationTree extends Tree {

    public class DataCallback implements NavigationDataCallback {

        private TreeNode node;
        private Progressbar progress;

        /**
         * The constructor that setsup the tree node.
         * @param node The node.
         * @param progress The progress of this data call
         */
        public DataCallback(TreeNode node, Progressbar progress) {
            this.node = node;
            this.progress = progress;
        }

        /**
         *
         * @param nodes
         */
        public void addChildren(NavigationTreeNode[] nodes) {
            progress.setPercentDone(100);
            if (node != null) {
                addList(nodes, node);
                _openFolder(node);
            } else {
                setData(nodes);
            }
        }

        /**
         * This method adds the node
         *
         * @param node
         */
        public void addChild(NavigationTreeNode node) {
            console.addTreeNode(node);
        }

        /**
         * This method is called to add a new panel
         */
        public void addPanel(ConsolePanel panel) {
            console.addPanel(panel);
        }

        /**
         * This method is responsible for adding the new panel.
         *
         * @param node The new node.
         * @param panel The panel.
         */
        public void addPanel(NavigationTreeNode node, ConsolePanel panel) {
            console.addPanel(node, panel);
        }
    }

    // class singletons
    private static NavigationTree singleton = null;

    // private member variables
    private Console console;
    private NavigationDataHandler handler;
    private Progressbar progress;

    /**
     * The constructor responsible for setting up the reference to the handler.
     *
     * @param handler The reference to the handler.
     */
    public NavigationTree(Console console, NavigationDataHandler handler) {
        this.console = console;
        this.handler = handler;
        this.setModelType(TreeModelType.PARENT);
        this.setNameProperty("name");
        this.setOpenProperty("isOpen");
        this.setIdField("nodeID");
        this.setParentIdField("parentNodeID");
        this.setRootValue("root");
        //this.setShowRoot(true);
        progress = new Progressbar();
        handler.getChildNodes("root", new DataCallback(this.getRoot(), progress));


        // setup the singleton
        if (singleton == null) {
            singleton = this;
        }
    }

    /**
     * This method returns the singleton instance.
     *
     * @return The singleton that is setup or null.
     */
    public static NavigationTree getInstance() {
        return singleton;
    }

    /**
     * Override the open folder method.
     *
     * @param node The node of the tree that is being opened.
     */
    @Override
    public void openFolder(TreeNode node) {
        progress.setPercentDone(0);
        progress.setVisible(true);
        handler.getChildNodes(((NavigationTreeNode) node).getNodeID(),
                new DataCallback(node, progress));

    }

    /**
     * This method opens the tree.
     *
     * @param node The nod that is to be opened.
     */
    private void _openFolder(TreeNode node) {
        super.openFolder(node);

    }
}
